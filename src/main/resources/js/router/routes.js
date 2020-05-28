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
import SourceLogsById from "../pages/logs/SourceLogsById.vue";
import PatternLogsById from "../pages/logs/PatternTableLogsById.vue";
import OnePatternTableView from "../pages/patternTable/OnePatternTableView.vue";
import TableLogsById from "../pages/logs/TableLogsById.vue";
import ShowPatternTableArchive from "../pages/patternTable/ShowPatternTableArchive.vue";
import ShowPatternTableNotArchive from "../pages/patternTable/ShowPatternTableNotArchive.vue";
import PatternTableUpdate from "../pages/patternTable/PatternTableUpdate.vue";
import UploadDataToAnotherSystem from "../pages/uploadData/UploadDataToAnotherSystem.vue";
import UploadDataForm from "../pages/uploadData/UploadDataForm.vue";
import NotFoundPages from "../pages/NotFoundPages.vue";
import LoginPage from "../pages/LoginPage.vue";
import {AXIOS} from "../AXIOS/http-common";
import {getToken} from "../modules/auth";
import Cookies from 'js-cookie'
let hidden;
hidden = Cookies.get('isAdmin') !== "true";
const routes = [
    {
        path:'/source',
        name: 'source',
        component: Source,
        meta: {
            title: 'Источники',
            icon: 'el-icon-collection'
        },
        children:[
            {
                path:'show',
                component: ShowSource,
                name: 'show',
                meta: {
                    title: 'Все источники',
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
            icon:'el-icon-postcard'
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
            icon:'el-icon-notebook-2'
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

            {
                path:'showArchive',
                component: ShowPatternTableArchive,
                name: 'ShowPatternTableArchive',
                meta: {
                    title: 'Архивные'
                },
            },

            {
                path:'showNotArchive',
                component: ShowPatternTableNotArchive,
                name: 'ShowPatternTableNotArchive',
                meta: {
                    title: 'Не архивные'
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
            icon: 'el-icon-paperclip',
        },
        hidden: hidden,
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
        path:'/uploadData',
        name: 'uploadData',
        component: UploadDataToAnotherSystem,
        meta: {
            title: 'Загрузить в другую систему',
            icon: 'el-icon-download'
        },
        children:[
            {
                path:'send',
                component: UploadDataForm,
                name: 'send',
                meta: {
                    title: 'В другую систему',
                },
            },
        ]
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
    },

    {
        path:'/source/update/:id',
        component: UpdateSource,
        name: 'sourceUpdate',
    },

    {
        path:'/logs/sourceLogs/:id',
        component: SourceLogsById,
        name: 'sourceLogsById',
    },

    {
        path:'/logs/patternLogs/:id',
        component: PatternLogsById,
        name: 'patternLogsById',
    },

    {
        path:'/pattern/card/:id',
        component: cardView,
        name: 'card',
    },

    {
        path:'/patternTable/show/:id',
        component: OnePatternTableView,
        name: 'OnePatternTableView',
    },

    {
        path:'/logs/patternTableLogs/:id',
        component: TableLogsById,
        name: 'patternLogsById',
    },

    {
        path:'/patternTable/update/:id',
        component: PatternTableUpdate,
        name: 'PatternTableUpdate',
    },

    {
        path:'/notFound',
        component: NotFoundPages,
        name: 'NotFoundPages',
    },

    {
        path:'/login',
        component: LoginPage,
        name: 'login',
    },


]

export default routes