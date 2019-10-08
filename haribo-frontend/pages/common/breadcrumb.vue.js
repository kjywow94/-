Vue.component('v-breadcrumb', {
    props: ["title", "description", "titleImg"],
    template: `
        <div class="breadcrumb">
            <div class="container">
                <div style="width:400px; float:left;">
                    <h4>{{ title }}</h4>
                    <p>{{ description }}</p>
                </div>
                <img class="titleimgstyle" :src="titleImg">
            </div>
        </div>
    `    
})