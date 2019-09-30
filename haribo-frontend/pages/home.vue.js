var homeView = Vue.component("Home", {
    template: `
        <div>
            <v-nav></v-nav>
            <div id="main-overview" class="container">
                <div class="row">
                    <div class="col-md-12">
                        <img src="assets/images/clotherroom.jpg"/>
                        <h1>Luxury clothes<br>AUCTION</h1>
                        <h4>블록체인 기반 명품옷 경매를 시작해보세요.</h4>
                        <router-link v-if="!sharedState.isSigned":to="{ name: 'register' }" class="btn btn-lg btn-primary">회원가입</router-link>
                        <router-link v-if="sharedState.isSigned":to="{ name: 'auction' }" class="btn btn-lg btn-primary">경매 시작하기</router-link>
                    </div>
                </div>
            </div>
            <div style="height=300px weight=300px background=black">
            <div class="bd-example">
                <div id="carouselExampleCaptions" class="carousel slide" data-ride="carousel">
                    <ol class="carousel-indicators">
                    <li data-target="#carouselExampleCaptions" data-slide-to="0" class="active"></li>
                    <li data-target="#carouselExampleCaptions" data-slide-to="1"></li>
                    <li data-target="#carouselExampleCaptions" data-slide-to="2"></li>
                    </ol>
                    <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img src="..." class="d-block w-100" alt="...">
                        <div class="carousel-caption d-none d-md-block">
                        <h5>First slide label</h5>
                        <p>Nulla vitae elit libero, a pharetra augue mollis interdum.</p>
                        </div>
                    </div>
                    <div class="carousel-item">
                        <img src="..." class="d-block w-100" alt="...">
                        <div class="carousel-caption d-none d-md-block">
                        <h5>Second slide label</h5>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                        </div>
                    </div>
                    <div class="carousel-item">
                        <img src="..." class="d-block w-100" alt="...">
                        <div class="carousel-caption d-none d-md-block">
                        <h5>Third slide label</h5>
                        <p>Praesent commodo cursus magna, vel scelerisque nisl consectetur.</p>
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
    `,
    data() {
        return {
            sharedState: store.state
        }
    }
})