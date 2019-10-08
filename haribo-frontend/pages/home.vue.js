var homeView = Vue.component("Home", {
    template: `
        <div>
            <v-nav></v-nav>
            <div id="main-overview" class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="tain">
                        <div style="height=200px weight=200px">
                        <div class="bd-example">
                            <div id="carouselExampleCaptions" class="carousel slide" data-ride="carousel">
                                <ol class="carousel-indicators">
                                <li data-target="#carouselExampleCaptions" data-slide-to="0" class="active"></li>
                                <li data-target="#carouselExampleCaptions" data-slide-to="1"></li>
                                <li data-target="#carouselExampleCaptions" v-for="item in auctions" data-slide-to="item+2"></li>
                                </ol>
                                <div class="carousel-inner">
                                <div class="carousel-item active">
                                    <div class="">
                                        <img src="assets/images/11.jpg" class="d-block w-100" alt="..."  style="max-width: 100%; height: 100%;">
                                        <div class="carousel-caption1 d-none d-md-block">
                                            <div class="box1">
                                                <div style="padding:20px;">
                                                    <p style="margin-bottom: -0.9rem;">2019 경매위탁</p>
                                                    <p style="font-size: 30px; margin-bottom: 0rem;">INVITATION TO CONSIGN</p>
                                                    <p style="margin-top: 1rem; margin-bottom: -1rem; font-size: 6px;">
                                                    <p style="color: #fd7e14; font-size: 20px;">애장품의 판매, 보라코인에서 도와드립니다.</p>
                                                    위영웅 “커피마시는 로로” 한국 미술품 최고 낙찰가 기록 경신 (2018년)<br>
                                                    천보라선생의 '윙크하는 로로' 도자기 최고 낙찰가 기록 경신(2019년)<br>
                                                    미술품 최고가 기록들 서울옥션 경매와 함께 했습니다.<br>
                                                    <br> 
                                                    보라코인과 함께 소중한 작품의 가치 확인 해 보세요.<br>
                                                    문의: kim@boraaction.com<br>
                                                    </p>
                                                </div>
                                                <router-link v-if="!sharedState.isSigned":to="{ name: 'register' }" class="btn btn-lg btn-orange">회원가입</router-link>
                                                <router-link v-if="sharedState.isSigned":to="{ name: 'auction' }" class="btn btn-lg btn-orange">경매 시작하기</router-link>
                                              </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="carousel-item">
                                    <img src="assets/images/12.jpg" class="d-block w-100" alt="..."  style="max-width: 100%; height: 100%;">
                                    <div class="carousel-caption2 d-none d-md-block">
                                    <div class="box2">
                                        <div style="padding:20px;">
                                            <p style="margin-bottom: -0.9rem;">2019 작품소개</p>
                                            <p style="font-size: 30px; margin-bottom: 0rem;">Introducing 2019 art works</p>
                                            <p style="margin-top: 1rem; margin-bottom: -1rem; font-size: 20px; color: #fd7e14;">
                                            2019년 등록된 작품을 구경해보세요.<br>
                                            </p>
                                            <router-link v-if="sharedState.isSigned":to="{ name: 'artworks' }" class="btn btn-lg btn-orange">작품 둘러보기</router-link>
                                        </div>
                                    </div>
                                </div>
                                </div>
                                <div class="carousel-item" v-for="item in auctions" V-if="item.">
                                    <img :src="item.imgData" class="d-block w-100" alt="..."  style="max-width: 100%; height: auto;">
                                    <div class="carousel-caption2 d-none d-md-block">
                                    <div class="box2">
                                        <div style="padding:20px;">
                                            <p style="margin-bottom: -0.9rem;">현재 경매중인 작품</p>
                                            <p style="font-size: 30px; margin-bottom: 0rem;">2019 Action art works</p>
                                            <p style="margin-top: 1rem; margin-bottom: -1rem; font-size: 20px; color: #fd7e14;">
                                            제목 : {{ item.작품정보.이름 }}<br>
                                            남은시간 : {{ item['남은시간'] }}
                                            </p>
                                            <router-link v-if="sharedState.isSigned" :to="{ name: 'auction.detail', params: { id: item.id }}" class="btn btn-lg btn-orange">경매 하러가기</router-link>
                                        </div>
                                    </div>
                                    </div>
                                </div>
                                </div>
                                <a class="carousel-control-prev" href="#carouselExampleCaptions" role="button" data-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                                </a>
                                <a class="carousel-control-next" href="#carouselExampleCaptions" role="button" data-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                                </a>
                            </div>
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
            sharedState: store.state,
            auctions: []
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
        }
    },
    mounted: function () {
        var scope = this;
        let tempArr = [];
        auctionService.findAll(function (data) {
            var result = data;



            if (result == undefined) {
                result = [];
            }
            // 각 경매별 작품 정보를 불러온다.
            function fetchData(start, end) {
                if (start == end) {
                    scope.auctions = tempArr;
                    setInterval(function () {
                        for(let i = 0 ; i < scope.auctions.length ; i++){

                            scope.auctions[i]['남은시간'] = scope.calculateDate( scope.auctions[i]['시작일시'],scope.auctions[i]['종료일시']); 
                            console.log(scope.auctions[i]['남은시간'])
                        }
                    }.bind(scope), 1000);    
                }
                else {
                    var id = result[start]['경매작품id'];
                    workService.findById(id, function (work) {
                        result[start]['작품정보'] = work;
                        result[start]['남은시간'] = scope.calculateDate( result[start]['시작일시'],result[start]['종료일시']); 
                        var now = new Date();
                        var e = new Date(result[start]["종료일시"]);
                        if (now < e)
                            tempArr.push(result[start]);
                        fetchData(start + 1, end);
                    });

                    console.log(result);
                    
                }
                
            }
            fetchData(0, result.length);

        });
    }
})