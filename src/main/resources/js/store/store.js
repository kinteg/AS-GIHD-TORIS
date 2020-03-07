import Vue from 'vue';
import Vuex from 'vuex';
import Cookies from 'js-cookie'
import router from "../router/router";
Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        count: 0
    },

    sidebar: {
        opened: !+Cookies.get('sidebarStatus'),
        withoutAnimation: false
    },
    getters: {
        SIDEBAR: (state) => state.sidebar.opened,
        ROUTER: (state)=> router.routes
    },


    mutations: {
        increment (state) {
            state.count++
        }
    }
})