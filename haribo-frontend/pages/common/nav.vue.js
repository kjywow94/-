var navVue = Vue.component("v-nav", {
    props: ["isSigned"],
    template: `
        <nav class="navbar navbar-expand-sm navbar-dark bg-primary">
            <div class="container"> 
                <router-link class="navbar-brand" to="/" style="color:#9430a0">Auction<img src="assets/images/BoraCoin.png" wight=45px height=45px style="margin-top:-5px"/></router-link>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span><img src="assets/images/list-solid.png" wight=20px height=20px style="margin-top:-5px"/></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav ml-auto" dark>
                        <li class="nav-item"s>
                            <router-link class="nav-link" to="/artworks">Artworks</router-link> 
                        </li>
                        <li class="nav-item">
                            <router-link class="nav-link" to="/auction">경매참여</router-link>
                        </li>
                        <li class="nav-item">
                            <router-link class="nav-link" to="/explorer/auctions">익스플로러</router-link>
                        </li>
                        <li class="nav-item" v-if="sharedState.isSigned">
                            <router-link class="nav-link" to="/mypage/wallet_create" v-if="!sharedState.user.hasWallet">마이페이지</router-link>
                            <router-link class="nav-link" to="/mypage/wallet_info" v-if="sharedState.user.hasWallet">마이페이지</router-link>
                        </li>
                        <li class="nav-item" v-if="!sharedState.isSigned">
                            <router-link class="nav-link" to="/login">로그인</router-link>
                        </li>
                        <li class="nav-item" v-if="!sharedState.isSigned">
                            <router-link class="nav-link" to="/register">회원가입</router-link>
                        </li>
                        <li class="nav-item" v-if="sharedState.isSigned">
                            <router-link class="nav-link" to="/logout">로그아웃</router-link>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    `,
    data() {
        return {
            sharedState: store.state
        }
    }
})