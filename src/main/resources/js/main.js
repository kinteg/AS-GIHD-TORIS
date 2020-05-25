import Vue from 'vue'
import Element from 'element-ui';
import VueResource from 'vue-resource'
import 'element-ui/lib/theme-chalk/index.css'
import localeUI from 'element-ui/lib/locale'
import defaultLang from 'element-ui/lib/locale/lang/ru-RU'
import VueRouter from 'vue-router'
import router from "./router/router.js";
import App from "./pages/App.vue";
import '@babel/polyfill'
import store from './store/store'
import {defaultConfig, setToken, torisInit} from './modules/auth'
localeUI.use(defaultLang);

Vue.use(VueRouter);

Vue.use(VueResource);
Vue.use(Element, { size: 'small', zIndex: 3000 });

torisInit(
    {
        config: {
            sys_id: "urn-eis-toris-gihd-uzd-2020",
            domain: "test.toris.gov.spb.ru",
            domain_proto: "https:"
        },

        error(){

        },

        success(data){
            setToken(data.AISTOKEN);
        }
    }
);

new Vue({
    el: '#app',
    store,
    render: a => a(App),
    router
});