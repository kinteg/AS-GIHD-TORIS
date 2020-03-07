import Vue from 'vue'
import VueRouter from 'vue-router'
import Source from "pages/Source.vue";
import FileLoader from "../pages/FileLoader.vue";
import Pattern from "../pages/CreatePatternPage.vue";

Vue.use(VueRouter)

const routes = [
    {
        path:'/source',
        component: Source,
        name: 'source',
        meta: {
            title: 'Источник'
        }
    },
    // { path: '/source', component: Source },
    { path: '/fileLoader', component: FileLoader },
    { path: '/createPattern', component: Pattern },
    { path: '*', component: FileLoader }
]

export default new VueRouter({
    mode: 'history',
    routes
})