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
                <div class="row">
                    <div class="col-md-3 auction" v-for="item in pageAuctions">
                        <div class="card">
                            <div class="card-body">
                                <img src="./assets/images/artworks/artwork1.jpg">
                                <h4>{{ item['작품정보']['이름'] }}</h4>
                                <p>{{ calculateDate(item['시작일시'],item['종료일시']) }}</p>
                                <router-link :to="{ name: 'auction.detail', params: { id: item['id'] }}" class="btn btn-block btn-secondary">자세히보기</router-link>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 text-center">
                        <nav class="bottom-pagination">
                            <ul class="pagination">
                                <li class="page-item"v-bind:class="{disabled: page == 1}"><a class="page-link" @click="movePage(1)">맨 앞</a></li>
                                
                                    <li v-for = "p in pageArr" class="page-item"v-bind:class="{active: page == p}"><a class="page-link" @click="movePage(p)">{{p}}</a></li>
                                </v-for>
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
            pageAuctions: []
        }
    },
    methods: {
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
                var days = endDate.getDate() - now.getDate();
                var hours = endDate.getHours() - now.getHours();
                var minutes = endDate.getMinutes() - now.getMinutes();

                return "남은시간: " + days + "일 " + hours + "시간 " + minutes + "분";
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
            this.page = p;
            var min = this.auctions.length;
            if(min > 8 * this.page)
                min = 8 * this.page;
            this.pageAuctions = [];
            for(var i = (this.page - 1) * 8 ; i < min ; i++){
                this.pageAuctions.push(this.auctions[i]); 
            }
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
                        result[start]['작품정보'] = work;
                        fetchData(start + 1, end);
                    });
                }
            }
            fetchData(0, result.length);
            
        });
    }
});