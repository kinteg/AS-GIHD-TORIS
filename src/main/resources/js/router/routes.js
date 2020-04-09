import ShowSource from "../pages/source/ShowSource.vue";
import Source from "../pages/source/Source.vue";
import UpdateSource from "../pages/source/UpdateSource.vue";
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
import SourceLogs from "../pages/logs/SourceLogs.vue";
import Logs from "../pages/logs/Logs.vue";
import PatternLogs from "../pages/logs/PatternLogs.vue";
import TableLogs from "../pages/logs/TableLogs.vue";

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
        path:'/logs',
        name: 'logs',
        component: Logs,
        meta: {
            title: 'Логи',
        },
        children:[
            {
                path:'sourceLogs',
                component: SourceLogs,
                name: 'SourceLogs',
                meta: {
                    title: 'По источникам'
                },
            },
            {
                path:'patternLogs',
                component: PatternLogs,
                name: 'patternLogs',
                meta: {
                    title: 'По шаблонам'
                },
            },
            {
                path:'patternTableLogs',
                component: TableLogs,
                name: 'patternTableLogs',
                meta: {
                    title: 'По таблицам'
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