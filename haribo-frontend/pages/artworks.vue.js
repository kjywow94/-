var artworksView = Vue.component('artworksView', {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="Artworks" description="작품을 둘러볼 수 있습니다."></v-breadcrumb>
            <div id="artwork-list" class="container">
                <div class="row">
                    <div class="col-md-12 text-right">
                        <router-link to="/works/create" class="btn btn-outline-secondary">내 작품 등록하기</router-link>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3 artwork" v-for="item in pageArtworks">
                        <div class="card">
                            <div class="card-body">
                                <img src="./assets/images/artworks/artwork1.jpg">
                                <h4>{{ item["이름"] }}</h4>
                                <p v-if="item['설명'] != null">{{ item["설명"] }}</p>
                                <p v-if="item['설명'] == null">-</p>
                                <router-link :to="{ name: 'work.detail', params: { id: item['id'] } }" class="btn btn-block btn-secondary">이력보기</router-link>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 text-center">
                        <nav class="bottom-pagination">
                            <ul class="pagination">
                                <li class="page-item"v-bind:class="{disabled: page == 1}"><a class="page-link" @click="movePage(1)">맨 앞</a></li>
                                <li class="page-item"v-bind:class="{disabled: page == 1}"><a class="page-link" @click="prevPage()" v-if="false">이전</a></li>
                                
                                    <li v-for = "p in pageArr" class="page-item"v-bind:class="{active: page == p}"><a class="page-link" @click="movePage(p)">{{p}}</a></li>
                                </v-for>
                                <li class="page-item"v-bind:class="{disabled: page == maxPage}"><a class="page-link" @click="nextPage()" v-if="false">다음</a></li>
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
            artworks: [{
                "이름": "",
                "설명": ""
            }],
            maxPage: 0,
            page: 1,
            pageArr: [],
            pageArtworks: [{
                "이름": "",
                "설명": ""
            }]
        }
    },
    mounted: function(){
        var scope = this;

        workService.findAll(function(data){
            scope.artworks = data;
            scope.maxPage = parseInt(scope.artworks.length / 8);
            if(scope.artworks.length % 8 > 0)
                scope.maxPage += 1; 
            scope.movePage(scope.page);
            console.log(scope.maxPage);
        }); 
    },
    methods:{
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
            var min = this.artworks.length;
            if(min > 8 * this.page)
                min = 8 * this.page;
            this.pageArtworks = [];
            for(var i = (this.page - 1) * 8 ; i < min ; i++){
                this.pageArtworks.push({
                    "이름": this.artworks[i]["이름"],
                    "설명": this.artworks[i]["설명"]
                }); 
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
    }
})