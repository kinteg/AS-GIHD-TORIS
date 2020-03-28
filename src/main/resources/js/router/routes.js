import FileLoader from "../../old-front/components/pages/FileLoader.vue";
import Pattern from "../../old-front/components/pages/CreatePatternPage.vue";
import ShowSource from "../pages/source/ShowSource.vue";
import Source from "../pages/source/Source.vue";
import UpdateSource from "../pages/source/UpdateSource.vue";
import VueRouter from "vue-router";
import CreateSource from "../pages/source/CreateSource.vue";
import ViewSource from "../pages/source/ViewSource.vue";
import ShowSourceArchive from "../pages/source/ShowSourceArchive.vue";
import ShowSourceNoArchive from "../pages/source/ShowSourceNoArchive.vue";

const routes = [
    {
        path:'/source',
        name: 'source',
        component: Source,
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
            {
                path:'showArchive',
                component: ShowSourceArchive,
                name: 'showArchive',
                meta: {
                    title: 'Архивные'
                },
            },
            {
                path:'showNoArchive',
                component: ShowSourceNoArchive,
                name: 'show',
                meta: {
                    title: 'Не архивные'
                },
            },

        ],
    },
    {
        path:'/source/update/:id',
        component: UpdateSource,
        name: 'sourceUpdate',
    },
    {
        path:'/source/view/:id',
        component: ViewSource,
        name: 'sourceUpdate',
    },
    {
        path:'/source/create',
        component: CreateSource,
        name: 'CreateSource',
    }

]

export default routes