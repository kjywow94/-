var adminUserManageView = Vue.component('AdminUserManageView', {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="관리자페이지" description="회원과 작품을 관리할 수 있습니다." titleImg="assets/images/auction_title.gif"></v-breadcrumb>
            <div class="container">
                <v-admin-nav></v-admin-nav>
                <div id="my-artwork" class="row">
                    <div class="col-md-12 mt-5">
                        <h4>회원정보</h4>
                        <div>
                            <div class="row" v-if="pageUsers.length > 0">
                            <table class="table table-bordered" style="table-layout:fixed;"> 
                                <thead>
                                    <tr>
                                        <th scope="col">이름</th>
                                        <th scope="col" colspan="2">이메일</th>
                                        <th scope="col" colspan="2">가입일시</th>
                                        <th scope="col">권한</th>
                                        <th scope="col" colspan="2">기타</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="user in pageUsers">
                                        
                                        <td>{{user['이름']}}</td>
                                        <td class="text-overflow" colspan="2">{{user['이메일']}}</td>
                                        <td colspan="2">{{user['등록일시']}}</td>
                                        <td v-if="user['authority'] == 0">탈퇴됨</td>
                                        <td v-if="user['authority'] == 1">관리자</td>
                                        <td v-if="user['authority'] == 2">회원</td>
                                        <td colspan="2">
                                        <button v-if="user['authority'] == 2" class="btn btn-sm btn-outline-secondary" v-on:click="expireUser(user)">탈퇴</button>
                                        <button v-if="user['authority'] == 0" class="btn btn-sm btn-outline-secondary" v-on:click="restoreUser(user)">복구</button>
                                        <button v-if="user['authority'] != 0" class="btn btn-sm btn-outline-secondary" v-on:click="changeAuthUser(user)">권한변경</button>
                                         
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                             
                                
                            </div>
                            <div class="col-sm-12 col-md-8 mt-3" v-if="pageUsers.length == 0">
                            <div class="alert alert-warning">보유중인 작품이 없습니다.</div>
                        </div>
                            <div class="row">
                                <div class="col-md-12 text-center">
                                    <nav class="bottom-pagination">
                                        <ul class="pagination" v-if="pageUsers.length > 0">
                                            <li class="page-item" v-bind:class="{disabled: page == 1}"><a class="page-link" @click="movePage(1)">맨 앞</a></li>
                                            <li v-for = "p in pageArr" class="page-item" v-bind:class="{active: page == p}"><a class="page-link" @click="movePage(p)">{{p}}</a></li>
                                            <li class="page-item" v-bind:class="{disabled: page == maxPage}"><a class="page-link" @click="movePage(maxPage)">맨 뒤</a></li>
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
            users: [],
            page: 1,
            maxPage: 0,
            pageArr: [],
            pageUsers: [],
        }
    },
    mounted: function () {
        let scope = this;

        manageService.findAll(function (data) {
            scope.users = data;
            scope.sort();
            scope.maxPage = parseInt(scope.users.length / 10);
            if(scope.users.length % 10 > 0)
                scope.maxPage += 1; 
            scope.movePage(1);
        });

    },
    methods: {
        
        sort(){
            this.users.sort(function(a,b){
                if(a.authority == b.authority) return 0;
                if(a.authority == 0 || b.authority == 0) return 1;
                return a.authority < b.authority ? -1 : a.authority > b.authority ? 1 : 0;  
            })
        },
        changeAuthUser(user){
            if(user['authority'] == 1){
                if(confirm("해당 사용자의 권한을 관리자에서 회원으로 낮추시겠습니까?")){
                    user['authority'] = 2;
                    manageService.modifyAuth({"id" : user['id'], "authority" : 2}, function(data){
                        alert("권한이 변경되었습니다.");
                    });

                }
            }else if(user['authority'] == 2){
                if(confirm("해당 사용자의 권한을 회원에서 관리자로 상승시키겠습니까?")){
                    user['authority'] = 1;
                    manageService.modifyAuth({"id" : user['id'], "authority" : 1}, function(data){
                        alert("권한이 변경되었습니다.");
                    });
                }
            }
        },
        expireUser(user){
            if(confirm("해당 사용자를 탈퇴시키겠습니까?")){
                user['authority'] = 0;
                manageService.modifyAuth({"id" : user['id'], "authority" : 0}, function(data){
                    alert("처리되었습니다.");
                });

            }
        },
        restoreUser(user){
            if(confirm("해당 사용자를 복구시키겠습니까?")){
                user['authority'] = 2;
                manageService.modifyAuth({"id" : user['id'], "authority" : 2}, function(data){
                    alert("처리되었습니다.");
                });

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
            var min = this.users.length;
            if(min > 10 * this.page)
                min = 10 * this.page;
            this.pageUsers = [];
            console.log((this.page - 1) * 10);
            console.log(min)
            for(var i = (this.page - 1) * 10 ; i < min ; i++){
                this.users[i]['등록일시'] = this.users[i]['등록일시'].replace("T", " ");
                this.pageUsers.push(this.users[i]); 
                
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
        
    }}
})
