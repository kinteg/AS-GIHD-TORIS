import Vue from 'vue'
import Element from 'element-ui';
import VueResource from 'vue-resource'
import 'element-ui/lib/theme-chalk/index.css'
import App from 'pages/App.vue'
import localeUI from 'element-ui/lib/locale'
import defaultLang from 'element-ui/lib/locale/lang/ru-RU'
import VueRouter from 'vue-router'
import router from "router/router.js";

localeUI.use(defaultLang);

Vue.use(VueRouter);

Vue.use(VueResource);
Vue.use(Element, { size: 'small', zIndex: 3000 });

new Vue({
    el: '#app',
    render: a => a(App),
    router
});