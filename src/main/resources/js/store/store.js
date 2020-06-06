import Vue from 'vue';
import Vuex from 'vuex';
import Cookies from 'js-cookie'
import router from "../router/router.js";
import VueRouter from "vue-router";
import routes from "../router/routes";
Vue.use(Vuex);
export default new Vuex.Store({

    state: {
        sidebarMenu: {
            opened: !+Cookies.get('sidebarStatus'),
            withoutAnimation: false
        },
        routes: routes
    },

    getters: {
        sidebar: state => state.sidebarMenu,
        permission_routers: state => state.routes
    },

    mutations: {
        setCollapse: state => {
            if (state.sidebarMenu.opened) {
                Cookies.set('sidebarStatus', 1)
            } else {
                Cookies.set('sidebarStatus', 0)
            }
            state.sidebarMenu.opened = !state.sidebarMenu.opened;
            state.sidebarMenu.withoutAnimation = false
        }
    },

})