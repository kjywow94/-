/**
 * 화면: 경매 등록하기
 */

var auctionRegisterView = Vue.component('AuctionRegisterView', {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="경매 등록하기" description="새로운 경매를 등록합니다." titleImg="assets/images/register_title.jpg"></v-breadcrumb>
            <div class="row">
                <div class="col-md-6 mx-auto">
                    <div class="card" style="margin-bottom: 15px;">
                        <div class="card-header">신규 경매 등록하기</div>
                        <div class="card-body">
                            <div v-if="!registered">
                                <div class="form-group">
                                    <label id="privateKey">지갑 개인키</label>
                                    <input id="privateKey" v-model="before.input.privateKey" type="text" class="form-control" placeholder="지갑 개인키를 입력해주세요.">
                                </div>
                                <div class="form-group">
                                    <label id="work">작품 선택</label>
                                    <select v-model="before.selectedWork" class="form-control">
                                        <option v-for="work in before.works" :value="work.id">{{ work['이름'] }}</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label id="minPrice">최저가</label>
                                    <div class="input-group">
                                        <input id="minPrice" v-model="before.input.minPrice" type="text" class="form-control" placeholder="최저가를 입력해주세요.">
                                        <div class="input-group-append">
                                            <div class="input-group-text">ETH</div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label id="startDate">경매 시작일시</label>
                                    <div class="input-group">
                                    <input id="startDate" v-model="before.input.startDate" type="date" class="form-control" placeholder="yyyy-MM-dd HH:mm:ss, 예: 2019-04-21 21:00:00">
                                    <input id="startDate" v-model="before.input.startTime" type="time" class="form-control" placeholder="yyyy-MM-dd HH:mm:ss, 예: 2019-04-21 21:00:00">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label id="untilDate">경매 종료일시</label>
                                    <div class="input-group">
                                    <input id="startDate" v-model="before.input.untilDate" type="date" class="form-control" placeholder="yyyy-MM-dd HH:mm:ss, 예: 2019-04-21 21:00:00">
                                    <input id="startDate" v-model="before.input.untilTime" type="time" class="form-control" placeholder="yyyy-MM-dd HH:mm:ss, 예: 2019-04-21 21:00:00">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <button class="btn btn-sm btn-primary" v-on:click="register" v-bind:disabled="isCreatingContract">{{ isCreatingContract ? "계약을 생성하는 중입니다." : "경매 등록하기" }}</button>
                                    </div>
                                    <div class="col-md-6 text-right">
                                        <button class="btn btn-sm btn-outline-secondary" v-on:click="goBack">이전으로 돌아가기</button>
                                    </div>
                                </div>
                            </div>
                            <div v-if="registered">
                                <div class="alert alert-success" role="alert">
                                    경매가 생성되었습니다.
                                </div>
                                <table class="table table-bordered mt-5">
                                    <tr>
                                        <th>경매작품</th>
                                        <td>{{ after.work['이름'] }}</td>
                                    </tr>
                                    <tr>
                                        <th>최저가</th>
                                        <td>{{ after.result['최저가'] }} ETH</td>
                                    </tr>
                                    <tr>
                                        <th>시작일시</th>
                                        <td>{{ before.input.startDate +" "+before.input.startTime}}</td>
                                    </tr>
                                    <tr>
                                        <th>종료일시</th>
                                        <td>{{ before.input.untilDate +" "+before.input.untilTime}}</td>
                                    </tr>
                                    <tr>
                                        <th>컨트랙트 주소</th>
                                        <td>{{ after.result['컨트랙트주소'] }}</td>
                                    </tr>
                                </table>
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
            isCreatingContract: false,
            registered: false,
            sharedStates: store.state,
            // 경매 등록전 입력값
            before: {
                works: [],
                selectedWork: null,
                input: {

                }
            },

            // 경매 등록 후 등록 결과 완료 표시 용도
            after: {
                result: {},
                work: {}
            }
        }
    },
    methods: {
        getWorks: async function (){
            // 내 작품 목록 가져오기
            var scope = this;
            var tmp = [];
            await workService.findWorksByOwner(this.sharedStates.user.id, async function (result) {
                tmp = result;
                for(let i = 0 ; i < tmp.length ; i++){
                    await auctionService.findStatus(tmp[i], "V", function (isNotUsing){
                        if(isNotUsing){
                            scope.before.works.push(tmp[i]);
                        }
                        
                    });
    
                }
            });
        },
        goBack: function () {
            this.$router.go(-1);
        },
        register: function () {
            /**
              * 컨트랙트를 호출하여 경매를 생성하고
              * 경매 정보 등록 API를 호출합니다. 
              */

            var scope = this;
            if(scope.before.input.privateKey == null){
                alert("비밀키를 확인해 주세요.");
                return;
            }if(scope.before.input.privateKey.length < 10  || !scope.before.input.privateKey.startsWith("0x")){
                alert("비밀키를 확인해 주세요.");
                return;
            }
            if(scope.before.selectedWork == null){
                alert("작품을 선택해 주세요.");
                return;
            }
            if(scope.before.input.minPrice == null){
                alert("경매 최저가를 입력해 주세요.");
                return;
            }if(new Date(scope.before.input.startDate+"T"+scope.before.input.startTime) >= new Date(scope.before.input.untilDate+"T"+scope.before.input.untilTime)){
                alert("경매 시간이 올바르지 않습니다.");
                return;
            }
            this.isCreatingContract = true;

            // 비밀키 확인
            walletService.isValidPrivateKey(this.sharedStates.user.id, scope.before.input.privateKey, (isValid, walletAddress) => {
                if (isValid) {
                    var sDate = new Date(scope.before.input.startDate+" "+scope.before.input.startTime);
                    var eDate = new Date(scope.before.input.untilDate+" "+scope.before.input.untilTime);
                    createAuction({
                        workId: scope.before.selectedWork,
                        minValue: scope.before.input.minPrice,
                        startTime: sDate.getTime() / 1000,
                        endTime: eDate.getTime() / 1000
                    }, walletAddress, scope.before.input.privateKey, function (responseAddress) {
                        var contractAddress = responseAddress;
                        var data = {
                            "경매생성자id": scope.sharedStates.user.id,
                            "경매작품id": scope.before.selectedWork,
                            "시작일시": sDate,
                            "종료일시": eDate,
                            "최저가": Number(scope.before.input.minPrice),
                            "컨트랙트주소": contractAddress,
                        }
                        data.시작일시.setHours(data.시작일시.getHours() + 9);
                        data.종료일시.setHours(data.종료일시.getHours() + 9);
                        // 3. 선택한 작업 정보를 가져옵니다.
                        workService.findById(scope.before.selectedWork, function (result) {
                            scope.after.work = result;
                        });

                        // 4. 생성한 경매를 등록 요청 합니다.
                        auctionService.register(data, function (result) {
                            alert("경매가 등록되었습니다.");
                            scope.registered = true;
                            scope.after.result = data;
                            self.isCreatingContract = false;

                        });
                    });

                } else {
                    alert("비밀키를 다시 확인해주세요.");
                    this.isCreatingContract = false;

                }
            });
        }
    },
    mounted: function () {
        var scope = this;
        
        var format = new Date();
        var year = format.getFullYear();
        var month = format.getMonth() + 1;
        if(month<10) month = '0' + month;
        var date = format.getDate();

        if(date<10) date = '0' + date;
        var hour = format.getHours();

        if(hour<10) hour = '0' + hour;
        var min = format.getMinutes();

        if(min<10) min = '0' + min;
        var sec = format.getSeconds();

        if(sec<10) sec = '0' + sec;
        
        scope.before.input.startDate =  year + "-" + month + "-" + date;
        scope.before.input.startTime =  hour + ":" + min + ":" + sec;

        scope.before.input.untilDate =  year + "-" + month + "-" + date;
        scope.before.input.untilTime =  hour + ":" + min + ":" + sec;
        this.getWorks();

        
    }
})