Vue.component('v-admin-nav', {
    template: `
        <div class="row sub-nav">
            <div class="col-md-6">
                <nav class="nav nav-pills nav-fill">
                    <router-link class="nav-item nav-link" v-bind:class="{ active: viewName == 'admin.user_manage' }" :to="{ name: 'admin.user_manage'}">회원 관리</router-link>
                    <router-link class="nav-item nav-link" v-bind:class="{ active: viewName == 'admin.artwork_manage' }" :to="{ name: 'admin.artwork_manage' }">작품 관리</router-link>
                </nav>
            </div>
        </div>
    `,
    data() {
        return {
            sharedState: store.state,
            viewName: ""
        }
    },
    mounted: function(){
        this.viewName = this.$route.name;
    }
});