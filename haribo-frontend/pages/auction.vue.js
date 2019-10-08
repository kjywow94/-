var auctionView = Vue.component('AuctionView', {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="경매 참여하기" description="경매 중인 작품을 보여줍니다." titleImg="assets/images/auction_title.gif"></v-breadcrumb>
            <div id="auction-list" class="container">
                <div class="row">
                    <div class="col-md-12">
                    <div class="input-group">
                            <input type="text" v-model="search" @keydown="keyEvt" v-on:keyup.enter="searchFcn()" class="form-control" placeholder="작품명 입력">
                                <button class="btn signaure-btn" type="button" @click="searchFcn()">검색</button>
                            <span class="col-md-4 text-right">
                            <router-link :to="{ name: 'auction.regsiter' }" class="btn btn-outline-secondary">경매 생성하기</router-link>

                        </span>
                    </div><!-- /input-group -->
                    </div>
                </div>
                <div class="col-sm-12 col-md-12 mt-3" v-if="auctions.length == 0">
                <div v-if="loading" class="alert alert-warning">데이터를 불러오는 중입니다...</div>
                    <div v-if="isSearching && !loading" class="alert alert-warning">등록된 경매가 없습니다. 가장 먼저 경매를 등록해 보세요!</div>
                    <div v-if="!isSearching && !loading" class="alert alert-warning">검색된 경매가 없습니다.
                        <button class="btn signaure-btn pull-right"type="button" @click="showAll"> 전체 목록 조회</button>
                    </div>
                            </div>
                <div class="row" v-if="auctions.length > 0">
                    <div class="col-md-3 auction" v-for="item in pageAuctions">
                        <div class="card bg-grey">
                        <div class = "text-left list-inline" style="position: absolute; padding-left:15px; padding-top: 10px">
                            <span v-if = "item['종료임박']" class="badge badge-warning">종료임박</span>
                            <span v-if = "item['입찰횟수'] >= 10" class="badge badge-danger">HOT</span>
                            <span v-if = "item['입찰횟수'] == 0 && item['남은시간'] != '경매 마감'" class="badge badge-info">입찰자없음</span>
                            <span v-if = "item['입찰횟수'] == 0 && item['남은시간'] == '경매 마감'" class="badge badge-info">유찰</span>
                            <span class="badge"><br></span>
                        </div>
                            <div class="card-body">
                                <img :src="item.imgData">
                                <hr>
                                <h4 class="text-overflow">{{ item['작품정보']['이름'] }}</h4>
                                <p v-bind:class="{'text-danger': item['종료임박']}">{{item['남은시간']}}</p>
                                <hr>
                                <router-link :to="{ name: 'auction.detail', params: { id: item['id'] }}" class="btn btn-block btn-secondary">자세히보기</router-link>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 text-center">
                        <nav class="bottom-pagination">
                            <ul class="pagination" v-if="auctions.length > 0">
                                <li class="page-item"v-bind:class="{disabled: page == 1}"><a class="page-link" @click="movePage(1)">맨 앞</a></li>
                                    <li v-for = "p in pageArr" class="page-item"v-bind:class="{active: page == p}"><a class="page-link" @click="movePage(p)">{{p}}</a></li>
                                <li class="page-item"v-bind:class="{disabled: page == maxPage}"><a class="page-link" @click="movePage(maxPage)">맨 뒤</a></li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    `,
    data() {
        return {
            auctions: [],
            maxPage: 0,
            page: 1,
            pageArr: [],
            pageAuctions: [],
            interval: null,
            search: "",
            isSearching: false,
            loading: false
        }
    },
    methods: {
        countBid(){
            let scope = this;
            for(let i = 0 ; i < this.pageAuctions.length ; i++){
                let idx = scope.pageAuctions[i]['id'];
                auctionService.countBidById(idx , function(result){
                    scope.pageAuctions[i]['입찰횟수'] = result;
                });
            }
        },
        calculateDate(start, end) {
            var now = new Date();
            var startDate = new Date(start);
            var endDate = new Date(end);

            if (now < startDate) {
                return "경매 대기";
            }

            var diff = (endDate - now);

            // 만약 종료일자가 지났다면 "경매 마감"을 표시한다.
            if (diff < 0) {
                return "경매 마감";
            } else {
                // UNIX Timestamp를 자바스크립트 Date객체로 변환한다.
                var delta = Math.abs(endDate - now) / 1000;
                var days = Math.floor(delta / 86400);
                delta -= days * 86400;

                var hours = Math.floor(delta / 3600) % 24;
                delta -= hours * 3600;

                var minutes = Math.floor(delta / 60) % 60;
                delta -= minutes * 60;

                var seconds = parseInt(delta % 60);
                
                return days + "일 " + hours + "시간 " + minutes + "분 " + seconds + "초";
            }
        },
        
        nextPage(){
            this.page += 1;
            this.movePage(this.page)
        },
        prevPage(){
            this.page -= 1;
            this.movePage(this.page)
        },
        movePage(p){
            clearInterval(this.interval);
            this.page = p;
            var min = this.auctions.length;
            if(min > 8 * this.page)
                min = 8 * this.page;
            this.pageAuctions = [];
            for(var i = (this.page - 1) * 8 ; i < min ; i++){
                this.pageAuctions.push(this.auctions[i]); 
                
            }
            this.countBid();
            
            for(let i = 0 ; i < this.pageAuctions.length ; i++){
                this.pageAuctions[i]['남은시간'] = this.calculateDate(this.pageAuctions[i]['시작일시'], this.pageAuctions[i]['종료일시']); 
                this.pageAuctions[i]['종료임박'] = false;
                if(this.pageAuctions[i]['남은시간'].startsWith("0일 0시간")){
                    this.pageAuctions[i]['종료임박'] = true;
                }
            }
            this.interval = setInterval(function () {
                for(let i = 0 ; i < this.pageAuctions.length ; i++){
                    this.pageAuctions[i]['남은시간'] = this.calculateDate(this.pageAuctions[i]['시작일시'], this.pageAuctions[i]['종료일시']); 
                    this.pageAuctions[i]['종료임박'] = false;
                    if(this.pageAuctions[i]['남은시간'].startsWith("0일 0시간")){
                        this.pageAuctions[i]['종료임박'] = true;
                    }
                }
            }.bind(this), 1000);             
            this.pageArr = [];
            for(var i = -5 ; i < 0 ; i++){
                if(this.page + i > 0 )
                    this.pageArr.push(this.page + i);
            }
            for(var i = 0 ; i < 5 ; i++){
                if(this.page + i > this.maxPage )
                    break;
                this.pageArr.push(this.page + i);
            }
            
        },
        keyEvt(){
            //추천검색어 출력을 위한 메소드 작성중
            let scope = this;
            
            for(let i = 0 ; i < scope.auctions.length ; i++){
                if(scope.auctions[i]['작품정보']['이름'].replace(/(\s*)/g, "").indexOf(scope.search) >= 0){


                }
            }
        },
        searchFcn(){
            this.loading = true;
            let keyword = this.search.replace(/(\s*)/g, "") ;
            let scope = this;
            auctionService.findAll(function (data) {
                let tmp = [];
                var result = data;
                if(result == undefined){
                    result = [];
                }
                // 각 경매별 작품 정보를 불러온다.
                function fetchData(start, end) {
                    if (start == end) {
                        this.loading = false;
                        scope.maxPage = parseInt(scope.auctions.length / 8);
                        if(scope.auctions.length % 8 > 0)
                            scope.maxPage += 1; 
                        scope.movePage(1);
                    } else {
                        
                        var id = result[start]['경매작품id'];
                        workService.findById(id, function (work) {
                            result[start]['입찰횟수'] = 0;
                            result[start]['작품정보'] = work;
                            result[start]['남은시간'] = scope.calculateDate(result[start]['시작일시'], result[start]['종료일시']);
                            if(result[start]['작품정보']['이름'].replace(/(\s*)/g, "").indexOf(keyword) >= 0){
                                scope.auctions.push(result[start]);
                            }
                            fetchData(start + 1, end);
                        });
                    }
                }
                scope.auctions = [];
                fetchData(0, result.length);
                
            });
            
            scope.maxPage = parseInt(scope.auctions.length / 8);
                if (scope.auctions.length % 8 > 0)
                    scope.maxPage += 1;
                scope.movePage(1);
            if(keyword == ""){
                this.isSearching = false;
            }else{
                this.isSearching = true;
            }
            
        },
        showAll(){
            this.search="";
            this.searchFcn();
        }
    },
    mounted: function () {
        this.showAll();
        
        
    }
});