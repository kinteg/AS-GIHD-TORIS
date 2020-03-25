import FileLoader from "../../old-front/components/pages/FileLoader.vue";
import Pattern from "../../old-front/components/pages/CreatePatternPage.vue";
import Source from "../../old-front/components/pages/Source.vue";
import ShowSource from "../pages/source/ShowSource.vue";
import UpdateSource from "../pages/source/UpdateSource.vue";

const routes = [
    {
        path:'/source',
        component: ShowSource,
        name: 'source',
        meta: {
            title: 'Источники',
            icon:'file',
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
        path:'/source/sourceUpdate',
        component: UpdateSource,
        name: 'sourceUpdate',
    }
]

export default routes