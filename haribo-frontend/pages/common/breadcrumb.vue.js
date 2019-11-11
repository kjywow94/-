Vue.component('v-breadcrumb', {
    props: ["title", "description", "titleImg"],
    template: `
        <div class="breadcrumb">
            <div class="container">
                <div class="headerTitle" style="width:450px; float:left;">
                    <h4 class="boldTitle">{{ title }}</h4>
                    <p class="boldP">{{ description }}</p>
                </div>
                <div class="effect">
                <img class="titleimgstyle" :src="titleImg">
                </div>
            </div>
        </div>
    `    
})