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
                                <li data-target="#carouselExampleCaptions" data-slide-to="2"></li>
                                <li data-target="#carouselExampleCaptions" data-slide-to="3"></li>
                                <li data-target="#carouselExampleCaptions" data-slide-to="4"></li>
                                </ol>
                                <div class="carousel-inner">
                                <div class="carousel-item active">
                                    <img src="assets/images/11.jpg" class="d-block w-100" alt="..."  style="max-width: 100%; height: auto;">
                                    <div class="carousel-caption1 d-none d-md-block">
                                    <div style="background=#000000">
                                    <h5>작품 경매하기</h5>
                                    <hr style="width=500px; border-bottom=0px; text-align=left; margin-left=0px;">
                                    <p style="text-decoration: underline;">       rrr              </p>
                                    <router-link v-if="!sharedState.isSigned":to="{ name: 'register' }" class="btn btn-lg btn-primary">회원가입</router-link>
                                    <router-link v-if="sharedState.isSigned":to="{ name: 'auction' }" class="btn btn-lg btn-primary">경매 시작하기</router-link>
                                    <p>Nulla vitae elit libero, a pharetra augue mollis interdum.</p>
                                    </div>
                                    </div>
                                </div>
                                <div class="carousel-item">
                                    <img src="assets/images/12.jpg" class="d-block w-100" alt="..."  style="max-width: 100%; height: auto;">
                                    <div class="carousel-caption d-none d-md-block">
                                    <h5>Second slide label</h5>
                                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                                    </div>
                                </div>
                                <div class="carousel-item">
                                    <img src="assets/images/13.jpg" class="d-block w-100" alt="..."  style="max-width: 100%; height: auto;">
                                    <div class="carousel-caption d-none d-md-block">
                                    <h5>Third slide label</h5>
                                    <p>Praesent commodo cursus magna, vel scelerisque nisl consectetur.</p>
                                    </div>
                                </div>
                                <div class="carousel-item">
                                    <img src="assets/images/14.jpg" class="d-block w-100" alt="..."  style="max-width: 100%; height: auto;">
                                    <div class="carousel-caption d-none d-md-block">
                                    <h5>Third slide label</h5>
                                    <p>Praesent commodo cursus magna, vel scelerisque nisl consectetur.</p>
                                    </div>
                                </div>
                                <div class="carousel-item">
                                    <img src="assets/images/15.jpg" class="d-block w-100" alt="..."  style="max-width: 100%; height: auto;">
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