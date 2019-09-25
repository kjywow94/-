var myArtworkView = Vue.component('MyArtworkView', {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="마이페이지" description="지갑을 생성하거나 작품을 업로드 할 수 있습니다."></v-breadcrumb>
            <div class="container">
                <v-mypage-nav></v-mypage-nav>
                <div class="row">
                    <div class="col-md-12 text-right">
                        <router-link to="/works/create" class="btn btn-outline-secondary">내 작품 등록하기</router-link>
                    </div>
                </div>
                <div id="my-artwork" class="row">
                    <div class="col-md-12 mt-5">
                        <h4>보유 중</h4>
                        <div v-if="ownPageArtworks.length > 0">
                            <div class="row">
                                <div class="col-md-3 artwork" v-for="item in ownPageArtworks">
                                    <div class="card">
                                        <div class="card-body">
                                            <img :src="item.imgData">
                                            <h4 class="text-overflow">{{ item["이름"] }}</h4>
                                            <p v-if="item['설명'] != null" class="text-overflow">{{ item["설명"] }}</p>
                                            <p v-if="item['설명'] == null">-</p>
                                            <router-link :to="{ name: 'work.detail', params: { id: item['id'] } }" class="btn btn-block btn-secondary">자세히보기</router-link>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-12 col-md-8 mt-3" v-if="ownPageArtworks.length == 0">
                                    <div class="alert alert-warning">보유중인 작품이 없습니다.</div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-12 text-center">
                                    <nav class="bottom-pagination">
                                        <ul class="pagination">
                                            <li class="page-item" v-bind:class="{disabled: ownPage == 1}"><a class="page-link" @click="movePage(1)">맨 앞</a></li>
                                            <li class="page-item"v-bind:class="{disabled: ownPage == 1}"><a class="page-link" @click="prevPage()" v-if="false">이전</a></li>
                                            <li v-for = "p in ownPageArr" class="page-item" v-bind:class="{active: ownPage == p}"><a class="page-link" @click="movePage(p)">{{p}}</a></li>
                                            <li class="page-item"v-bind:class="{disabled: ownPage == ownMaxPage}"><a class="page-link" @click="nextPage()" v-if="false">다음</a></li>
                                            <li class="page-item" v-bind:class="{disabled: ownPage == ownMaxPage}"><a class="page-link" @click="movePage(ownMaxPage)">맨 뒤</a></li>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 mt-5">
                        <h4>경매 중</h4>
                        <div v-if="auctions.length > 0">
                            <div class="row">
                                <div class="col-md-3 artwork" v-for="item in auctions">
                                    <div class="card">
                                        <div class="card-body">
                                            <img :src="item.imgData">
                                            <h4>{{ item['작품정보']['이름'] }}</h4>
                                            <span class="badge badge-success">경매 진행중</span>
                                            <router-link :to="{ name: 'auction.detail', params: { id: item['id'] }}" class="btn btn-block btn-secondary mt-3">자세히보기</router-link>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12 text-center">
                                    <nav class="bottom-pagination">
                                        <ul class="pagination">
                                            <li class="page-item" v-bind:class="{disabled: auctionPage == 1}"><a class="page-link" @click="movePage(1)">맨 앞</a></li>
                                            <li class="page-item"v-bind:class="{disabled: auctionPage == 1}"><a class="page-link" @click="prevPage()" v-if="false">이전</a></li>
                                            <li v-for = "p in ownPageArr" class="page-item" v-bind:class="{active: auctionPage == p}"><a class="page-link" @click="movePage(p)">{{p}}</a></li>
                                            <li class="page-item"v-bind:class="{disabled: auctionPage == ownMaxPage}"><a class="page-link" @click="nextPage()" v-if="false">다음</a></li>
                                            <li class="page-item" v-bind:class="{disabled: auctionPage == ownMaxPage}"><a class="page-link" @click="movePage(ownMaxPage)">맨 뒤</a></li>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        </div>

                        <div class="row">    
                            <div class="col-sm-12 col-md-8 mt-3" v-if="auctions.length == 0">
                                <div class="alert alert-warning">진행중인 경매 목록이 없습니다.</div>
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
            auctions: [],
            ownPage: 1,
            ownMaxPage: 0,
            ownPageArr: [],
            ownPageArtworks: [],
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
        nextpage() {
            this.ownPage += 1;
            this.movePage(this.ownPage);
        },
        prevPage() {
            this.ownPage -= 1;
            this.movePage(this.ownPage);
        },
        movePage(p) {
            this.ownPage = p;
            let min = this.artworks.length;
            if (min > 4 * this.ownPage)
                min = 4 * this.ownPage;
            this.ownPageArtworks = [];
            for(var i = (this.ownPage - 1) * 4 ; i < min ; i++){
                this.ownPageArtworks.push(this.artworks[i]); 
            }
            this.ownPageArr = [];
            for (var i = -5; i < 0; i++) {
                if (this.ownPage + i > 0)
                    this.ownPageArr.push(this.ownPage + i);
            }
            for (var i = 0; i < 5; i++) {
                if (this.ownPage + i > this.ownMaxPage)
                    break;
                this.ownPageArr.push(this.ownPage + i);
            }
        }
    },
    mounted: function () {
        var scope = this;
        var userId = this.sharedStates.user.id;
        workService.findWorksByOwner(userId, function (data) {
            scope.artworks = data;
            scope.ownMaxPage = parseInt(scope.artworks.length / 4);
            if (scope.artworks.length % 4 > 0)
                scope.ownMaxPage += 1;
            scope.movePage(scope.ownPage);
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
