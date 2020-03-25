import FileLoader from "../pages/FileLoader.vue";
import Pattern from "../pages/CreatePatternPage.vue";
import Source from "../pages/Source.vue";

const routes = [
    {
        path:'/source',
        component: Source,
        name: 'source',
        meta: {
            title: 'Источник',
            icon: 'form'
        },
        children:[
            {
                path:'source',
                component: Source,
                name: 'source',
                meta: {
                    title: 'Источник'
                },
            }
        ]
    },
    {
        path:'/fileLoader',
        component: FileLoader,
        name: 'fileLoader',
        meta: {
            title: 'fileLoader'
        }
    },
    {
        path:'/createPattern',
        component: Pattern,
        name: 'createPattern',
        meta: {
            title: 'createPattern'
        }
    },
    {
        path:'*',
        component: FileLoader,
        name: 'FileLoader',
        meta: {
            title: 'FileLoader'
        }
    },
]

export default routes