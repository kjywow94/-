var auctionDetailView = Vue.component('AuctionDetailView', {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="경매 작품 상세 정보" description="선택하신 경매 작품의 상세 정보를 보여줍니다." :titleImg="work.imgData"></v-breadcrumb>
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="card" style="margin-bottom: 15px;">
                            <div class="card-body">
                                <table class="table table-bordered">
                                    <tr>
                                        <th width="20%">생성자</th>
                                        <td><router-link :to="{ name: 'work.by_user', params: { id: creator['id'] } }">{{ creator['이름'] }}({{creator['이메일']}})</router-link></td>
                                    </tr>
                                    <tr>
                                        <th>작품명</th>
                                        <td>{{ work['이름'] }}</td>
                                    </tr>
                                    <tr>
                                        <th>작품 설명</th>
                                        <td>{{ work['설명'] }}</td>
                                    </tr>
                                    <tr>
                                        <th>경매 시작일</th>
                                        <td>{{ auction['경매시작시간'] }}</td>
                                    </tr>
                                    <tr>
                                        <th>경매 종료일</th>
                                        <td>{{ auction['경매종료시간'] }}</td>
                                    </tr>
                                    <tr>
                                        <th>최저가</th>
                                        <td><strong>{{ auction['최소금액'] }} ETH</strong></td>
                                    </tr>
                                    <tr>
                                        <th>컨트랙트 주소</th>
                                        <td><router-link :to="{name: 'explorer.tx.detail.fromto', params: { address:  auction['경매컨트랙트주소']  }}" class="tx-number">{{ auction['경매컨트랙트주소'] }}</router-link></td>
                                    </tr>
                                    <tr>
                                        <th>상태</th>
                                        <td>
                                                <span class="badge badge-success" v-if="auction['종료'] == false && timeLeft != '경매 마감'">경매 진행중</span>
                                                <span class="badge badge-danger" v-if="auction['종료'] == true && timeLeft != '경매 마감'">경매 종료</span>
                                                <span class="badge badge-warning" v-if="timeLeft == '경매 마감'">경매 마감</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>남은시간</th>
                                        <td>
                                        {{ timeLeft }}
                                        </td>
                                    </tr>
                                </table>
                                <table class="table table-bordered mt-3" v-if="bidder.id">
                                    <tr>
                                        <th>입찰 횟수</th>
                                        <td>{{ bidCount }}</td>
                                    </tr>
                                    <tr>
                                        <th width="20%">현재 최고 입찰자</th>
                                        <td>{{ bidder['이름'] }}({{ bidder['이메일'] }})</td>
                                    </tr>
                                    <tr>
                                        <th>현재 최고 입찰액</th>
                                        <td>{{ auction['최고입찰액'] }} ETH</td>
                                    </tr>
                                </table>
                                <div class="alert alert-warning mt-3" role="alert" v-if="!bidder.id">
                                    입찰 내역이 없습니다.
                                </div>
                                <div class="alert alert-danger mt-3" role="alert" v-if="auction['종료'] == true">
                                    경매가 종료되었습니다.
                                </div>
                                <div class="row mt-5">
                                    <div class="col-md-6">
                                        <router-link :to="{ name: 'auction' }" class="btn btn-sm btn-outline-secondary">경매 리스트로 돌아가기</router-link>
                                    </div>
                                    <div class="col-md-6 text-right" v-if="sharedStates.user.id == work['회원id'] && auction['종료'] != true">
                                        <button v-if="bidder.id &&  timeLeft == '경매 마감'" type="button" class="btn btn-sm btn-primary" v-on:click="closeAuction" v-bind:disabled="isCanceling || isClosing">{{ isClosing ? "낙찰중" : "낙찰하기" }}</button>
                                        <button type="button" class="btn btn-sm btn-danger" v-on:click="cancelAuction" v-bind:disabled="isCanceling || isClosing ">{{ isCanceling ? "취소하는 중" : "경매취소하기" }}</button>
                                    </div>
                                    <div class="col-md-6 text-right" v-if="sharedStates.user.id != work['회원id'] && auction['종료'] != true && !isExpired">
                                        <router-link :to="{ name: 'auction.bid', params: { id: this.$route.params.id } }" class="btn btn-sm btn-primary">입찰하기</router-link>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <v-foot-nav></v-foot-nav>
        </div>
    `,
    data() {
        return {
            work: {},
            creator: { id: 0 },
            auction: {},
            sharedStates: store.state,
            bidder: {},
            isCanceling: false,
            isClosing: false,
            isExpired: false,
            bidCount: 0,
            timeLeft: null
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
        closeAuction: function () {
            /**
             * 컨트랙트를 호출하여 경매를 종료하고
             * 경매 상태 업데이트를 위해 API를 호출합니다. 
             */
            var scope = this;
            var privateKey = window.prompt("경매를 종료하시려면 지갑 비밀키를 입력해주세요.", "");
            walletService.isValidPrivateKey(scope.creator.id, privateKey, (isValid, walletAddress) => {
                if (isValid) {
                    scope.isClosing = true;
                    var options = {
                        privateKey: privateKey,
                        contractAddress: scope.auction['경매컨트랙트주소'],
                        walletAddress: walletAddress
                    }

                    auction_close(options, response => {
                        scope.isClosing = false;
                        if (response.gasUsed == 3000000) {
                            alert("경매 종료중 오류가 발생했습니다.");
                        } else {
                            auctionService.close(this.$route.params.id, scope.bidder.id, () => {
                                alert("경매가 종료되었습니다.");
                                scope.$router.go(-1);
                            }, () => {
                                alert("경매 종료후 데이터베이스 갱신에 실패했습니다");
                            });
                        }
                    });
                } else {
                    alert("비밀키를 다시 확인해주세요.");
                }
            });
        },
        cancelAuction: async function () {
            /**
             * 컨트랙트를 호출하여 경매를 취소하고
             * 경매 상태 업데이트를 위해 API를 호출합니다. 
             */
            var scope = this;
            var privateKey = window.prompt("경매를 취소하시려면 지갑 비밀키를 입력해주세요.", "");
            walletService.isValidPrivateKey(scope.creator.id, privateKey, (isValid, walletAddress) => {
                if (isValid) {
                    scope.isCanceling = true;
                    var options = {
                        privateKey: privateKey,
                        contractAddress: scope.auction['경매컨트랙트주소'],
                        walletAddress: walletAddress
                    }

                    auction_cancel(options, response => {
                        scope.isCanceling = false;
                        if (response.gasUsed == 3000000) {
                            alert("경매 취소중 오류가 발생했습니다.");
                        } else {
                            auctionService.cancel(this.$route.params.id, scope.bidder.id, () => {
                                alert("경매가 취소되었습니다.");
                                scope.$router.go(-1);
                            }, errorResponse => {
                                alert("경매 취소후 데이터베이스 갱신에 실패했습니다");
                            });
                        }
                    });
                } else {
                    alert("비밀키를 다시 확인해주세요.");
                }
            });
        }
    },
    mounted: async function () {
        var auctionId = this.$route.params.id;
        var scope = this;
        var web3 = createWeb3();
        auctionService.countBidById(auctionId, function(result){
            scope.bidCount = result;
        });
        // 경매 정보 조회
        auctionService.findById(auctionId, function (auction) {
            var amount = Number(auction['최소금액']).toLocaleString().split(",").join("")
            auction['최소금액'] = web3.utils.fromWei(amount, 'ether');

            var workId = auction['작품id'];
    
            // 작품 정보 조회
            workService.findById(workId, function (work) {
                scope.work = work;
                var creatorId = work['회원id'];

                // 생성자 정보 조회
                userService.findById(creatorId, function (user) {
                    scope.creator = user;
                });
            });

            // 입찰자 조회
            if (auction['최고입찰액'] > 0) {
                var amount = Number(auction['최고입찰액']).toLocaleString().split(",").join("")
                auction['최고입찰액'] = web3.utils.fromWei(amount, 'ether');
                var bidderId = auction['최고입찰자id'];
                userService.findById(bidderId, function (user) {
                    scope.bidder = user;
                });
            }

            scope.auction = auction;
            if(new Date(scope.auction['경매종료시간']).getTime() < new Date().getTime())
                scope.isExpired = true;
            scope.timeLeft = scope.calculateDate(new Date(), scope.auction['경매종료시간']);
            scope.interval = setInterval(function () {
                scope.timeLeft = scope.calculateDate(new Date(), scope.auction['경매종료시간']); 
            }.bind(scope), 1000);      
        });


    }
});