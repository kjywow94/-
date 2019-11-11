var navFoot = Vue.component("v-foot-nav", {
    props: ["isSigned"],
    template: `
        <footer class="page-footer font-small blue" style="margin-top: 50px;">

        <!-- Copyright -->
        <div class="footer-copyright text-center py-3">© 2019 Copyright : 
        <p style="font-color=#6f42c1; display: inline-block; margin-top: 7.5px;">BORACOIN</p>
        </div>
        <!-- Copyright -->
    
        </footer>
    `,
    data() {
        return {
            sharedState: store.state
        }
    }
})
