var adminArtworkManageView = Vue.component('AdminArtWorkManageView', {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="관리자페이지" description="회원과 작품을 관리할 수 있습니다." titleImg="assets/images/auction_title.gif"></v-breadcrumb>
            <div class="container">
                <v-admin-nav></v-admin-nav>
                <div id="my-artwork" class="row">
                    <div class="col-md-12 mt-5">
                        <h4>작품정보</h4>
                        <div>
                            <div class="row" v-if="ownPageArtworks.length > 0">
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th scope="col">이름</th>
                                        <th scope="col">이메일</th>
                                        <th scope="col">가입일시</th>
                                        <th scope="col">비밀번호</th>
                                        <th scope="col">권한</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <th scope="row">1</th>
                                        <td>Mark</td>
                                        <td>Otto</td>
                                        <td>@mdo</td>
                                    </tr>
                                    <tr>
                                    <th scope="row">2</th>
                                    <td>Jacob</td>
                                    <td>Thornton</td>
                                    <td>@fat</td>
                                    </tr>
                                    <tr>
                                    <th scope="row">3</th>
                                    <td>Larry</td>
                                    <td>the Bird</td>
                                    <td>@twitter</td>
                                    </tr>
                                </tbody>
                            </table>
                             
                                
                            </div>
                            <div class="col-sm-12 col-md-8 mt-3" v-if="ownPageArtworks.length == 0"> 
                            <div class="alert alert-warning">보유중인 작품이 없습니다.</div>
                        </div>
                            <div class="row">
                                <div class="col-md-12 text-center">
                                    <nav class="bottom-pagination">
                                        <ul class="pagination" v-if="artworks.length > 0">
                                            <li class="page-item" v-bind:class="{disabled: ownPage == 1}"><a class="page-link" @click="movePage(1, '보유')">맨 앞</a></li>
                                            <li v-for = "p in ownPageArr" class="page-item" v-bind:class="{active: ownPage == p}"><a class="page-link" @click="movePage(p, '보유')">{{p}}</a></li>
                                            <li class="page-item" v-bind:class="{disabled: ownPage == ownMaxPage}"><a class="page-link" @click="movePage(ownMaxPage, '보유')">맨 뒤</a></li>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `,
    data() {
        return {
            sharedStates: store.state,
            artworks: [],
            ownPage: 1,
            ownMaxPage: 0,
            ownPageArr: [],
            ownPageArtworks: [],

            auctions: [],
            auctionPage: 1,
            auctionMaxPage: 0,
            auctionPageArr: [],
            auctionPageArtworks: []
        }
    },
    methods: {
        calculateDate(date) {
            var now = new Date();
            var endDate = new Date(date);
            var diff = endDate.getTime() - now.getTime();
            
            // 만약 종료일자가 지났다면 "경매 마감"을 표시한다.
            if (diff < 0) {
                return "경매 마감";
            } else {
                // UNIX Timestamp를 자바스크립트 Date객체로 변환한다.
                var d = new Date(diff);
                var days = d.getDate();
                var hours = d.getHours();
                var minutes = d.getMinutes();

                return "남은시간: " + days + "일 " + hours + "시간 " + minutes + "분";
            }
        },
        movePage(p, kind) {
            let page = p;
            let pageArr = [];
            let curPageArr = [];
            let min, maxPage, data;
            if(kind == '보유') {
                min = this.artworks.length;
                maxPage = this.ownMaxPage;
                data = this.artworks;
            }else {
                min = this.auctions.length;
                maxPage = this.auctionMaxPage;
                data = this.auctions;
                console.log(data)
            }

            if (min > 4 * page)
                min = 4 * page;       

            for(var i = (page - 1) * 4 ; i < min ; i++){
                pageArr.push(data[i]); 
            }
            for (var i = -5; i < 0; i++) {
                if (page + i > 0)
                    curPageArr.push(page + i);
            }
            for (var i = 0; i < 5; i++) {
                if (page + i > maxPage)
                    break;
                curPageArr.push(page + i);
            }
            if(kind == '보유') {
                this.ownPageArtworks = pageArr;
                this.ownPageArr = curPageArr;
                this.ownPage = page;
            }else {
                this.auctionPageArtworks = pageArr;
                this.auctionPageArr = curPageArr;
                this.auctionPage = page;
            }
        }
    },
    mounted: function () {
        let scope = this;
        let userId = this.sharedStates.user.id;
        workService.findWorksByOwner(userId, function (data) {
            scope.artworks = data;
            if(scope.artworks == undefined){
                scope.artworks = [];
            }
            scope.ownMaxPage = parseInt(scope.artworks.length / 4);
            if (scope.artworks.length % 4 > 0)
                scope.ownMaxPage += 1;
            scope.movePage(scope.ownPage, '보유');
        });

        /**
         * TODO 1. 회원의 작품 목록을 가져옵니다.
         * Backend와 API 연동합니다.
         * 작품 마다 소유권 이력을 보여줄 수 있어야 합니다.
         */
        // 여기에 작성하세요.
        auctionService.findAllByUser(userId, function (data) {
            var result = data;
            // 각 경매별 작품 정보를 불러온다.
            function fetchData(start, end) {
                if (start == end) {
                    scope.auctions = result;
                    scope.auctionMaxPage = parseInt(scope.auctions.length / 4);
                    if(scope.auctions.length % 4 > 0)
                        scope.auctionMaxPage += 1;
                    scope.movePage(scope.auctionPage, '경매');
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
        /**
         * TODO 2. 회원의 경매 목록을 가져옵니다.
         * Backend와 API 연동합니다.
         * 경매 중인 작품 마다 소유권 이력을 보여줄 수 있어야 합니다.
         */
        // 여기에 작성하세요.
    }
})
