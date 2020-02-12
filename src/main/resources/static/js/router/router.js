import Vue from 'vue'
import VueRouter from 'vue-router'
import Source from "pages/Source.vue";
import App from "../pages/App.vue";
import FileLoader from "../pages/FileLoader.vue";

Vue.use(VueRouter)

const routes = [
    { path: '/source', component: Source },
    { path: '/fileLoader', component: FileLoader },
    { path: '*', component: FileLoader }
]

export default new VueRouter({
    mode: 'history',
    routes
})