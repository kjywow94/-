var auctionView = Vue.component('AuctionView', {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="경매 참여하기" description="경매 중인 작품을 보여줍니다."></v-breadcrumb>
            <div id="auction-list" class="container">
                <div class="row">
                    <div class="col-md-12 text-right">
                        <router-link :to="{ name: 'auction.regsiter' }" class="btn btn-outline-secondary">경매 생성하기</router-link>
                    </div>
                </div>
                <div class="col-sm-12 col-md-12 mt-3" v-if="auctions.length == 0">
                                <div class="alert alert-warning">등록된 경매가 없습니다. 가장 먼저 경매를 등록해 보세요!</div>
                            </div>
                <div class="row" v-if="auctions.length > 0">
                    <div class="col-md-3 auction" v-for="item in pageAuctions">
                        <div class="card">
                        <div class = "text-left list-inline" style="position: absolute; padding-left:15px; padding-top: 10px">
                            <span v-if = "item['종료임박']" class="badge badge-warning">종료임박</span>
                            <span v-if = "item['입찰횟수'] >= 10" class="badge badge-danger">HOT</span>
                            <span class="badge"><br></span>
                        </div>
                            <div class="card-body">
                                <img :src="item.imgData">
                                <h4 class="text-overflow">{{ item['작품정보']['이름'] }}</h4>
                                <p v-bind:class="{'text-danger': item['종료임박']}">{{item['남은시간']}}</p>
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
            auctions: [{'종료임박': false, '입찰횟수': 0}],
            maxPage: 0,
            page: 1,
            pageArr: [],
            pageAuctions: [],
            interval: null,
            isRed: false
        }
    },
    methods: {
        async countBid(){
            let scope = this;
            for(let i = 0 ; i < this.pageAuctions.length ; i++){
                let idx = scope.pageAuctions[i]['id'];
                auctionService.countBidById(idx , async function(result){
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
                if(Math.floor(delta) < 3600){
                    this.isRed = true;
                }

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
            

            this.interval = setInterval(function () {
                for(let i = 0 ; i < this.pageAuctions.length ; i++){
                    this.pageAuctions[i]['남은시간'] = this.calculateDate(this.pageAuctions[i]['시작일시'], this.pageAuctions[i]['종료일시']); 
                    this.pageAuctions[i]['종료임박'] = false;
                    // this.pageAuctions[i]['입찰횟수'] = 0;
                    if(this.isRed){
                        
                        this.pageAuctions[i]['종료임박'] = true;
                        this.isRed = false;
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
            
        }
    },
    mounted: function () {
        var scope = this;

        auctionService.findAll(function (data) {
            var result = data;
            if(result == undefined){
                result = [];
            }
            // 각 경매별 작품 정보를 불러온다.
            function fetchData(start, end) {
                if (start == end) {
                    scope.auctions = result;
                    scope.maxPage = parseInt(scope.auctions.length / 8);
                    if(scope.auctions.length % 8 > 0)
                        scope.maxPage += 1; 
                    scope.movePage(scope.page);
                } else {
                    var id = result[start]['경매작품id'];
                    workService.findById(id, function (work) {
                        result[start]['입찰횟수'] = 0;
                        result[start]['작품정보'] = work;
                        result[start]['남은시간'] = scope.calculateDate(result[start]['시작일시'], result[start]['종료일시']);
                        fetchData(start + 1, end);
                    });
                }
            }
            fetchData(0, result.length);
            
        });
    }
});