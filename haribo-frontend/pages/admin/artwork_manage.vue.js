var adminArtworkManageView = Vue.component('AdminArtWorkManageView', {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="관리자페이지" description="회원과 작품을 관리할 수 있습니다."></v-breadcrumb>
            <div class="container">
                <v-admin-nav></v-admin-nav>
                <div id="my-artwork" class="row">
                    <div class="col-md-12 mt-5">
                        <h4>작품정보</h4>
                        <div>
                            <div class="row" v-if="pageArtworks.length > 0">
                                <table class="table table-bordered" style="word-break:break-all;table-layout:fixed;">
                                    <thead>
                                        <tr class="text-center">
                                            <th scope="col">작품명</th>
                                            <th scope="col">작품설명</th>
                                            <th scope="col">작품주인ID</th>
                                            <th scope="col">작품공개</th>
                                            <th scope="col">작품상태</th>
                                            <th scope="col">기타</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr v-for="item in pageArtworks">
                                            <td class="text-overflow"><router-link :to="{ name: 'work.detail', params: { id: item['id'] } }">{{item['이름']}}</router-link></td>
                                            <td class="text-overflow">{{item['설명']}}</td>
                                            <td class="text-center">{{item['회원id']}}</td>
                                            <td class="text-center">{{item['공개여부']}}</td>
                                            <td class="text-center">{{item['상태']}}</td>
                                            <td class="text-center">
                                                <button v-if="(item.공개여부=='Y' && item.상태=='Y')" class="btn btn-sm btn-outline-secondary" v-on:click="deleteArtWork(item.id)">삭제</button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="col-sm-12 col-md-8 mt-3" v-if="pageArtworks.length == 0">
                            <div class="alert alert-warning">작품이 없습니다.</div>
                        </div>
                            <div class="row">
                                <div class="col-md-12 text-center">
                                    <nav class="bottom-pagination">
                                        <ul class="pagination" v-if="artworks.length > 0">
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
            sharedStates: store.state,
            artworks: [],
            page: 1,
            maxPage: 0,
            pageArr: [],
            pageArtworks: []
        }
    },
    methods: {
        movePage(p) {
            this.page = p;
            let min = this.artworks.length;
            if (min > 10 * this.page)
                min = 10 * this.page;
            this.pageArtworks = [];
            for(var i = (this.page - 1) * 10 ; i < min ; i++){
                this.pageArtworks.push(this.artworks[i]); 
            }
            this.pageArr = [];
            for (var i = -5; i < 0; i++) {
                if (this.page + i > 0)
                    this.pageArr.push(this.page + i);
            }
            for (var i = 0; i < 5; i++) {
                if (this.page + i > this.maxPage)
                    break;
                this.pageArr.push(this.page + i);
            }
        },
        findAll(){
            let scope = this;
            //전체 작품 정보를 불러온다.
            manageService.findAllArtwork(function(data){
                scope.artworks = data;  
                if(scope.artworks == undefined){
                    scope.artworks = [];
                }
                scope.maxPage = parseInt(scope.artworks.length / 10);
                if (scope.artworks.length % 10 > 0)
                    scope.maxPage += 1;
                scope.movePage(scope.page);
            });
        },
        deleteArtWork: function(id){
            var scope = this;
            manageService.deleteArtwork(
                id,
                function(response){
                    alert("작품이 삭제되었습니다.");
                    scope.findAll();
                },
                function(error) {
                    alert("작품을 삭제할 수 없습니다.");
                }
            );
        },
        restore: function (id, name, desc){
            var scope = this;
            manageService.restoreArtwork({
                "id": id,
                "이름": name,
                "설명": desc,
                "공개여부": "Y" ,
                "상태": "Y"
                },
                function(){
                    alert('작품이 복원되었습니다.');
                    scope.findAll();
                }, 
                function(error){
                    alert("작품 복원이 불가능합니다.");
                });     
        }
    },
    mounted: function () {
        this.findAll();
    }
})
