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
import ShowPattern from "../pages/pattern/ShowPattern.vue";
import ShowPatternArchive from "../pages/pattern/ShowPatternArchive.vue";
import cardView from "../pages/cardView.vue";
import ShowPatternNoArchive from "../pages/pattern/ShowPatternNoArchive.vue";
import PatternTable from "../pages/patternTable/PatternTable.vue";
import ShowPatternTable from "../pages/patternTable/ShowPatternTable.vue";

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
                name: 'showNoArchive',
                meta: {
                    title: 'Не архивные'
                },
            },

        ],
    },
    {
        path:'/pattern',
        name: 'pattern',
        component: Source,
        meta: {
            title: 'Шаблоны',
        },
        children:[
            {
                path:'show',
                component: ShowPattern,
                name: 'showPattern',
                meta: {
                    title: 'Все шаблоны'
                },
            },
            {
                path:'showArchive',
                component: ShowPatternArchive,
                name: 'showArchivePattern',
                meta: {
                    title: 'Архивные'
                },
            },
            {
                path:'showNotArchive',
                component: ShowPatternNoArchive,
                name: 'showNotArchivePattern',
                meta: {
                    title: 'Не архивные'
                },
            },
        ]
    },

    {
        path:'/patternTable',
        name: 'patternTable',
        component: PatternTable,
        meta: {
            title: 'Таблицы',
        },
        children:[
            {
                path:'show',
                component: ShowPatternTable,
                name: 'ShowPatternTable',
                meta: {
                    title: 'Все таблицы'
                },
            },
        ]
    },

    {
        path:'/source/update/:id',
        component: UpdateSource,
        name: 'sourceUpdate',
    },
    {
        path:'/pattern/card/:id',
        component: cardView,
        name: 'card',
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