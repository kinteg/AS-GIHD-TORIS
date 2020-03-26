import FileLoader from "../../old-front/components/pages/FileLoader.vue";
import Pattern from "../../old-front/components/pages/CreatePatternPage.vue";
import Source from "../../old-front/components/pages/Source.vue";
import ShowSource from "../pages/source/ShowSource.vue";
import UpdateSource from "../pages/source/UpdateSource.vue";
import VueRouter from "vue-router";
import CreateSource from "../pages/source/CreateSource.vue";

const routes = [
    {
        path:'/source',
        component: ShowSource,
        name: 'source',
        meta: {
            title: 'Источники',
        },
        children:[
            {
                path:'show',
                component: ShowSource,
                name: 'show',
                meta: {
                    title: 'Все записи'
                },
            },

        ],
    },
    {
        path:'/source/update',
        component: UpdateSource,
        name: 'sourceUpdate',
    },
    {
        path:'/source/create',
        component: CreateSource,
        name: 'CreateSource',
    }
]

export default routes