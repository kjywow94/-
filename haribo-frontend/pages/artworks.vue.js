var artworksView = Vue.component('artworksView', {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="Artworks" description="작품을 둘러볼 수 있습니다.">
                <img src="assets/images/BoraCoin.png" wight=45px height=45px style="margin-top:-5px"/>
            </v-breadcrumb>
            <div id="artwork-list" class="container">
                <div class="row">
                    <div class="col-md-12 text-right">
                        <router-link to="/works/create" class="btn signaure-btn">작품 등록</router-link>
                    </div>
                </div>
                <div class="col-sm-12 col-md-12 mt-3" v-if="artworks.length == 0">
                                <div class="alert alert-warning">등록된 작품이 없습니다. 가장 먼저 작품을 등록해 보세요!</div>
                            </div>
                <div class="row" v-if="artworks.length > 0">
                    <div class="col-sm-12 col-md-4 col-lg-3 artwork" v-for="item in pageArtworks">
                        <div class="card">
                            <div class="card-body">
                                <img :src="item.imgData">
                                <h4 class="text-overflow">{{ item["이름"] }}</h4>
                                <p v-if="item['설명'] != null" class="text-overflow">{{ item["설명"] }}</p>
                                <p v-if="item['설명'] == null">-</p>
                                <router-link :to="{ name: 'work.detail', params: { id: item['id'] } }" class="btn btn-block signaure-btn">이력보기</router-link>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 text-center">
                        <nav class="bottom-pagination">
                            <ul class="pagination" v-if="maxPage > 0">
                                <li class="page-item"v-bind:class="{disabled: page == 1}"><a class="page-link" @click="movePage(1)">맨 앞</a></li>
                                <li class="page-item"v-bind:class="{disabled: page == 1}"><a class="page-link" @click="prevPage()" v-if="false">이전</a></li>
                                
                                    <li v-for = "p in pageArr" class="page-item"v-bind:class="{active: page == p}"><a class="page-link" @click="movePage(p)">{{p}}</a></li>
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
                "설명": "",
                "imgData": "./assets/images/artworks/artwork1.jpg"
            }],
            maxPage: 0,
            page: 1,
            pageArr: [],
            pageArtworks: []
        }
    },
    mounted: function () {
        var scope = this;

        workService.findAll(function (data) {
            scope.artworks = data;
            if(scope.artworks == undefined){
                scope.artworks = [];
            }
            scope.maxPage = parseInt(scope.artworks.length / 8);
            if (scope.artworks.length % 8 > 0)
                scope.maxPage += 1;
            scope.movePage(scope.page);
        }); 
    },
    methods: {
        nextPage() {
            this.page += 1;
            this.movePage(this.page)
        },
        prevPage() {
            this.page -= 1;
            this.movePage(this.page)
        },
        movePage(p) {
            this.page = p;
            let min = this.artworks.length;
            if (min > 8 * this.page)
                min = 8 * this.page;
            this.pageArtworks = [];
            for(var i = (this.page - 1) * 8 ; i < min ; i++){
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


        }
    }
})