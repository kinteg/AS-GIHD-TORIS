<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="16">
                <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item
                                :to="{ path: '/pattern/show' }">
                            Все шаблоны
                        </el-breadcrumb-item>
                        <el-breadcrumb-item
                                :to="{ path: '/pattern/card/' + this.$route.params.id}">
                            Просмотр
                        </el-breadcrumb-item>
                    </el-breadcrumb>
                    <p style="font-size: 20px">Просмотр</p>
                    <div>
                        <el-tabs v-model="activeName">
                            <el-tab-pane label="Источник" name="sourceInfo">
                                <div>
                                    <el-row :gutter="20">
                                        <el-col :span="12">
                                            <div>
                                                <el-form
                                                        :label-position="labelPosition"
                                                        label-width="100px"
                                                        :model="source">
                                                    <el-form-item class="label" label="Поставщик данных:">
                                                        {{source.name}}
                                                    </el-form-item>
                                                    <el-form-item class="label" label="Полное наименование набора:">
                                                        {{source.longName}}
                                                    </el-form-item>
                                                    <el-form-item class="label" label="Краткое наименование набора:">
                                                        {{source.shortName}}
                                                    </el-form-item>
                                                    <el-form-item class="label" label="Описание:">
                                                        {{source.description}}
                                                    </el-form-item>
                                                    <el-form-item class="label" label="Дополнительное описание:">
                                                        {{source.addDescription}}
                                                    </el-form-item>
                                                    <el-form-item class="label" label="Сфера (направление):">
                                                        {{source.scope}}
                                                    </el-form-item>
                                                </el-form>
                                            </div>
                                        </el-col>
                                        <el-col :span="12">
                                            <div>
                                                <el-form
                                                        :label-position="labelPosition"
                                                        label-width="100px"
                                                        :model="source">
                                                    <el-form-item class="label" label="Периодичность актуализации:">
                                                        {{source.periodicity}}
                                                    </el-form-item>
                                                    <el-form-item class="label" label="Срок обновления набора данных:">
                                                        {{source.renewalPeriod}}
                                                    </el-form-item>
                                                    <el-form-item class="label" label="Вид набора:">
                                                        {{source.type}}
                                                    </el-form-item>
                                                    <el-form-item class="label" label="Ключевые слова (теги):">
                                                        {{source.tags}}
                                                    </el-form-item>
                                                    <el-form-item class="label" label="Источник данных:">
                                                        {{source.providerLink}}
                                                    </el-form-item>
                                                    <el-form-item class="label" label="Ссылка на данные на сайте поставщика:">
                                                        {{source.dataSource}}
                                                    </el-form-item>
                                                </el-form>
                                            </div>
                                        </el-col>
                                    </el-row>
                                </div>
<!--                                <el-button-->
<!--                                        v-if="userAccess"-->
<!--                                        @click="updateSource"-->
<!--                                        style="margin-top: 10px; background-color: #1ab394; border-color: #1ab394; color: white;">-->
<!--                                    Редактировать-->
<!--                                </el-button>-->
                                <el-button
                                        @click="updateSource"
                                        style="margin-top: 10px; background-color: #1ab394; border-color: #1ab394; color: white;">
                                    Редактировать
                                </el-button>
                            </el-tab-pane>
                            <el-tab-pane label="Шаблон" name="patternInfo">
                                <div v-if="viewPattern">
                                    <p style="font-size: 20px">Просмотр шаблона</p>
                                    <el-row :gutter="20">
                                        <el-col :span="12">
                                            <div>
                                                <el-form :label-position="labelPosition" label-width="100px" :model="pattern">
                                                    <el-form-item class="label" label="Название:">
                                                        {{pattern.name}}
                                                    </el-form-item>
                                                    <el-form-item class="label" label="Описание:">
                                                        {{pattern.description}}
                                                    </el-form-item>
                                                    <el-form-item class="label" label="Сфера (направление):">
                                                        {{pattern.direction}}
                                                    </el-form-item>
                                                    <el-form-item class="label" label="Ответственный за ведение:">
                                                        {{pattern.management}}
                                                    </el-form-item>
                                                    <el-form-item class="label" label="Архивность:">
                                                        {{pattern.isArchive ? "Да" : "Нет"}}
                                                    </el-form-item>
                                                    <el-form-item class="label" label="Дата создания:">
                                                        {{pattern.dateCreation}}
                                                    </el-form-item>
                                                </el-form>
                                            </div>
                                        </el-col>
                                        <el-col :span="12">
                                            <div>
                                                <el-form :label-position="labelPosition" label-width="100px" :model="pattern">
                                                    <el-form-item class="label" label="Дата архивации:">
                                                        {{pattern.dateDeactivation}}
                                                    </el-form-item>
                                                    <el-form-item class="label" label="Дата активации:">
                                                        {{pattern.dateActivation}}
                                                    </el-form-item>
                                                    <el-form-item class="label" label="Последнее обновление:">
                                                        {{pattern.lastUpdate}}
                                                    </el-form-item>
                                                    <el-form-item class="label" label="Всего таблиц:">
                                                        {{pattern.fileCount}}
                                                    </el-form-item>
                                                    <el-form-item class="label" label="Архивировано таблиц:">
                                                        {{pattern.archiveFileCount}}
                                                    </el-form-item>
                                                </el-form>
                                            </div>
                                        </el-col>
                                    </el-row>
<!--                                    <el-button-->
<!--                                               v-if="userAccess"-->
<!--                                               @click="updatePattern"-->
<!--                                               style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "-->
<!--                                               type="primary">-->
<!--                                        Редактировать-->
<!--                                    </el-button>-->
                                    <el-button
                                            @click="updatePattern"
                                            style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "
                                            type="primary">
                                        Редактировать
                                    </el-button>
                                </div>
                                <div v-else >
                                    <el-row :gutter="20">
                                        <el-col :span="12">
                                            <div>
                                                <el-form :model="pattern" :rules="rules" ref="pattern"
                                                         :label-position="labelPosition" label-width="100px">
                                                    <el-form-item prop="name" label="Название">
                                                        <el-input v-model="pattern.name"></el-input>
                                                    </el-form-item>
                                                    <el-form-item prop="description" label="Описание">
                                                        <el-input v-model="pattern.description"></el-input>
                                                    </el-form-item>
                                                </el-form>
                                            </div>
                                        </el-col>
                                        <el-col :span="12">
                                            <div>
                                                <el-form :model="pattern" :rules="rules" ref="pattern"
                                                         :label-position="labelPosition" label-width="100px">
                                                    <el-form-item prop="direction" label="Направление:">
                                                        <el-input v-model="pattern.direction"></el-input>
                                                    </el-form-item>
                                                    <el-form-item prop="management" label="Отвтественный за ведение:">
                                                        <el-input v-model="pattern.management"></el-input>
                                                    </el-form-item>
                                                </el-form>
                                            </div>
                                        </el-col>
                                    </el-row>
                                    <el-button
                                            @click="backUpdate"
                                            style="background-color: #1ab394; border-color: #1ab394; color: white;">
                                        Назад
                                    </el-button>
                                    <el-button
                                            @click="updatePatternAccept"
                                            style="background-color: #1ab394; border-color: #1ab394; color: white;">
                                        Сохранить
                                    </el-button>
                                </div>
                            </el-tab-pane>
                            <el-tab-pane label="Таблицы" name="tableInfo">
                                <p style="font-size: 20px">Таблицы
                                    <span v-if="isMainPage">
<!--                                        <el-button-->
<!--                                                v-if="userAccess"-->
<!--                                                @click="addTableTab"-->
<!--                                                style="float: right; margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "-->
<!--                                                type="primary"-->
<!--                                                icon="el-icon-plus"/>-->
<!--                                        <el-upload-->
<!--                                                v-if="userAccess"-->
<!--                                                style="float: right; margin-right: 10px;"-->
<!--                                                class="upload-demo"-->
<!--                                                ref="upload"-->
<!--                                                action=""-->
<!--                                                :limit="1"-->
<!--                                                :on-change="sendFiles"-->
<!--                                                :auto-upload="false">-->
<!--                                            <el-button-->
<!--                                                    v-if="userAccess"-->
<!--                                                    slot="trigger"-->
<!--                                                    style="background-color: #1ab394; border-color: #1ab394"-->
<!--                                                    size="small"-->
<!--                                                    type="primary">-->
<!--                                                Загрузить данные в таблицы-->
<!--                                            </el-button>-->
                                          <el-button
                                                  @click="addTableTab"
                                                  style="float: right; margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "
                                                  type="primary"
                                                  icon="el-icon-plus"/>
                                        <el-upload
                                                style="float: right; margin-right: 10px;"
                                                class="upload-demo"
                                                ref="upload"
                                                action=""
                                                :limit="1"
                                                :on-change="sendFiles"
                                                :auto-upload="false">
                                            <el-button
                                                    slot="trigger"
                                                    style="background-color: #1ab394; border-color: #1ab394"
                                                    size="small"
                                                    type="primary">
                                                Загрузить данные в таблицы
                                            </el-button>
                                        </el-upload>
                                    </span>
                                </p>
                                <div v-if="viewTable">
                                    <div>
                                        <div style="padding-right: 2px;" class="horizontal-scroll-wrapper  rectangles">
                                            <table style="display: block; overflow-x: auto; ">
                                                <tr>
                                                    <th></th>
                                                    <th><el-checkbox ></el-checkbox></th>
                                                    <th @click="sort('id')">Номер</th>
                                                    <th @click="sort('name_table')">Навание таблицы</th>
                                                    <th @click="sort('name_file')">Навание файла</th>
                                                    <th @click="sort('archive')">Архивность</th>
                                                    <th @click="sort('date_creation')">Дата создания</th>
                                                    <th @click="sort('date_deactivation')">Дата деактивации</th>
                                                    <th @click="sort('date_activation')">Дата активации</th>
                                                    <th @click="sort('last_update')">Последнее обновление</th>
                                                </tr>
                                                <tr>
                                                    <td><el-button
                                                            @click="sort('')"
                                                            style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "
                                                            type="primary"
                                                            size="mini"
                                                            icon="el-icon-search">
                                                    </el-button>
                                                    </td>
                                                    <td></td>
                                                    <td><el-input placeholder="Please input" v-model="patternTable.id"></el-input></td>
                                                    <td><el-input placeholder="Please input" v-model="patternTable.nameTable"></el-input></td>
                                                    <td><el-input placeholder="Please input" v-model="patternTable.nameFile"></el-input></td>
                                                    <td>
                                                        <el-select v-model="value" placeholder="Select">
                                                            <el-option
                                                                    v-for="item in options"
                                                                    :key="item.value"
                                                                    :label="item.label"
                                                                    :value="item.value">
                                                            </el-option>
                                                        </el-select>
                                                    </td>
                                                    <td> <div class="block">
                                                        <el-date-picker
                                                                value-format="yyyy-MM-dd"
                                                                v-model="patternTable.dateCreation"
                                                                type="daterange"
                                                                range-separator="To"
                                                                start-placeholder="Start date"
                                                                end-placeholder="End date">
                                                        </el-date-picker>
                                                    </div></td>
                                                    <td> <div class="block">
                                                        <el-date-picker
                                                                value-format="yyyy-MM-dd"
                                                                v-model="patternTable.dateDeactivation"
                                                                type="daterange"
                                                                range-separator="To"
                                                                start-placeholder="Start date"
                                                                end-placeholder="End date">
                                                        </el-date-picker>
                                                    </div></td>
                                                    <td> <div class="block">
                                                        <el-date-picker
                                                                value-format="yyyy-MM-dd"
                                                                v-model="patternTable.dateActivation"
                                                                type="daterange"
                                                                range-separator="To"
                                                                start-placeholder="Start date"
                                                                end-placeholder="End date">
                                                        </el-date-picker>
                                                    </div></td>
                                                    <td> <div class="block">
                                                        <el-date-picker
                                                                value-format="yyyy-MM-dd"
                                                                v-model="patternTable.lastUpdate"
                                                                type="daterange"
                                                                range-separator="To"
                                                                start-placeholder="Start date"
                                                                end-placeholder="End date">
                                                        </el-date-picker>
                                                    </div></td>
                                                </tr>
                                                <tbody v-for="table in patternTableData">
                                                <tr>
                                                    <td>
                                                        <router-link :to="'/patternTable/show/' + table.id">
                                                            <el-button style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary" size="mini" icon="el-icon-view"></el-button>
                                                        </router-link>
                                                        <span v-if="table.isArchive">
<!--                                                <el-button-->
<!--                                                        v-if="userAccess"-->
<!--                                                        @click="deArchiveOneTable(table.id)"-->
<!--                                                        style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "-->
<!--                                                        type="primary" size="mini"-->
<!--                                                        icon="el-icon-upload2"/>-->
                                                    <el-button
                                                        @click="deArchiveOneTable(table.id)"
                                                        style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "
                                                        type="primary" size="mini"
                                                        icon="el-icon-upload2"/>
                                            </span>
                                                        <span v-else>
<!--                                                <el-button-->
<!--                                                        v-if="userAccess"-->
<!--                                                        @click="deleteOneTable(table.id)"-->
<!--                                                        style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "-->
<!--                                                        type="primary"-->
<!--                                                        size="mini"-->
<!--                                                        icon="el-icon-delete"/>-->
                                            <el-button
                                                    @click="deleteOneTable(table.id)"
                                                    style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "
                                                    type="primary"
                                                    size="mini"
                                                    icon="el-icon-delete"/>
                                            </span>
                                                    </td>
                                                    <td> <el-checkbox @change="check(table.id)"></el-checkbox></td>
                                                    <td>{{table.id}}</td>
                                                    <td>{{table.nameTable}}</td>
                                                    <td>{{table.nameFile}}</td>
                                                    <td>{{table.isArchive ? "Да" : "Нет"}}</td>
                                                    <td>{{table.dateCreation}}</td>
                                                    <td>{{table.dateDeactivation}}</td>
                                                    <td>{{table.dateActivation}}</td>
                                                    <td>{{table.lastUpdate}}</td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <div v-else-if="updateTable">
                                    update
                                </div>
                                <div v-else-if="createTable">
                                    <el-upload
                                            class="upload-demo"
                                            ref="upload"
                                            action=""
                                            :limit="1"
                                            :on-remove="clearForm"
                                            :on-change="onChange"
                                            :auto-upload="false">
                                        <el-button
                                                slot="trigger"
                                                style="background-color: #1ab394; border-color: #1ab394"
                                                size="small"
                                                type="primary">
                                            Выбрать файл
                                        </el-button>
                                        <div class="el-upload__tip" slot="tip">Выберите файл из которого будут созданы таблицы</div>
                                    </el-upload>
                                    <el-collapse v-for="oneTable in table">
                                        <el-collapse-item :title="oneTable.tableModel.tableName" >
                                            <el-input
                                                    style="padding-bottom: 10px;"
                                                    v-model="oneTable.tableModel.tableName"
                                                    placeholder="Название таблицы"/>
                                            <el-form  v-for="pole in oneTable.tableModel.models" :inline="true"  class="demo-form-inline">
                                                <el-form-item>
                                                    <el-radio-group v-model="oneTable.tableModel.primaryKey">
                                                        <el-radio :label="pole.key">-</el-radio>
                                                    </el-radio-group>
                                                </el-form-item>
                                                <el-form-item>
                                                    <el-input v-model="pole.key" placeholder="Название поля"></el-input>
                                                </el-form-item>
                                                <el-form-item>
                                                    <el-autocomplete
                                                            style="float: right"
                                                            class="inline-input"
                                                            v-model="pole.type"
                                                            :fetch-suggestions="querySearch"
                                                            placeholder="Введите тип"
                                                    ></el-autocomplete>
                                                </el-form-item>
                                            </el-form>
                                            <h2>Предпросмотр</h2>
                                            <table style=" padding: 0 5px 0 0;">
                                                <tr>
                                                    <th v-for="pole in oneTable.tableModel.models">{{pole.key}}</th>
                                                </tr>
                                                <tr v-for="value in oneTable.values">
                                                    <td v-for="oneValue in value">{{oneValue}}</td>
                                                </tr>
                                            </table>
                                        </el-collapse-item>
                                    </el-collapse>
                                    <el-button
                                            @click="showTableTab('yes')"
                                            style="background-color: #1ab394; border-color: #1ab394; color: white;">
                                        Назад
                                    </el-button>
                                    <el-button
                                            @click="addTable"
                                            style="background-color: #1ab394; border-color: #1ab394; color: white;">
                                        Сохранить
                                    </el-button>
                                </div>
                                <div v-else-if="showTable">
                                    <pattern-table-view-by-pattern-id :pattern-id="patternId"/>
                                </div>
                            </el-tab-pane>
                        </el-tabs>
                    </div>
                </div>
            </el-col>
<!--            <el-col v-if="isAdmin" :span="8">-->
            <el-col :span="8">
                <div v-if="activeName === 'patternInfo'">
                    <pattern-log-card :pattern-id="patternId"/>
                </div>
                <div v-else-if="activeName === 'sourceInfo'">
                    <source-log-card :source-id="sourceId"/>
                </div>
                <div v-else-if="patternTableId !== '' && activeName === 'tableInfo' ">
                    <pattern-table-log-card :pattern-table-id="patternTableId"/>
                </div>

            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="16">
                <div style="background-color: white; padding: 30px; margin-top: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
                    <p style="font-size: 20px">Файлы</p>
                    <table style="overflow-x: auto; ">
                        <tr>
                            <th>Номер</th>
                            <th>Шаблон</th>
                            <th>Файл</th>
                            <th>Дата создания</th>
                        </tr>
                        <tr v-for="file in patternFile">
                            <td>{{file.id}}</td>
                            <td>{{file.patternId}}</td>
                            <td>
                                <el-button @click="downloadFile(file.file)"
                                           style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "
                                           type="primary"  icon="el-icon-download">

                                </el-button>
                            </td>
                            <td>{{file.dateCreation}}</td>
                        </tr>
                    </table>
                </div>
            </el-col>
            <el-col :span="8">
                <div v-if="showTable" style="margin-top: 20px; background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
                    <p style="font-size: 20px">Версии</p>
                    <table style="overflow-x: auto; ">
                        <tr>
                            <th>

                            </th>
                            <th>Версия</th>
                        </tr>
                        <tr v-for="tableVersion in patternTableVersion.content">
                            <td>
                                <router-link :to="'/patternTable/show/' + tableVersion.id">
                                    <el-button style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394" type="primary" size="mini" icon="el-icon-view"></el-button>
                                </router-link>
                            </td>
                            <td>{{tableVersion.version}}</td>
                        </tr>
                    </table>
                    <el-pagination
                            style="margin: 10px auto; text-align: center "
                            class="pager"
                            background
                            layout="prev, pager, next"
                            :page-size="paginationVersion.pageSize"
                            :page-count="paginationVersion.totalPages"
                            :current-page="paginationVersion.currentPage"
                            @current-change="onCurrentChangeVersion"
                            :total="paginationVersion.totalElements">
                    </el-pagination>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import {AXIOS} from "../../AXIOS/http-common";
    import MyPagination from "./pagination.vue";
    import router from "../../router/router";
    import PatternLogCard from "../logs/patternLogCard.vue";
    import SourceLogCard from "../logs/sourceLogCard.vue";
    import PatternTableLogCard from "../logs/patternTableLogCard.vue";
    import PatternTableViewByPatternId from "../patternTable/patternTableViewByPatternId.vue";
    import {getToken} from "../../modules/auth";

    export default {
        name: "card",
        components: {PatternTableViewByPatternId, PatternTableLogCard, SourceLogCard, PatternLogCard, MyPagination},
        data(){
            return{
                isAdmin:"",
                userAccess:"",
                sourceArchive:"",
                primaryKey:"",
                tableName:"",
                patternTableVersion:"",
                paginationVersion: {
                    pageSize: 5,
                    currentPage: 1,
                    totalPages: 0,
                    totalElements: 0,
                    pagerCount: 2,
                },

                radio:"",
                patternFile:"",
                options: [{
                    value: '',
                    label: ''
                }, {
                    value: 'true',
                    label: 'Да'
                }, {
                    value: false,
                    label: 'Нет'
                }],
                value: '',
                patternLog:"",
                isMainPage: true,
                pickerOptions: {
                    shortcuts: [{
                        text: 'Last week',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: 'Last month',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                            picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: 'Last 3 months',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                            picker.$emit('pick', [start, end]);
                        }
                    }]
                },

                hidden:{
                    id: true,
                    nameFile: true,
                    nameTable: true,
                    isArchive: true,
                    dateCreation: true,
                    dateDeactivation: true,
                    dateActivation: true,
                    lastUpdate: true,
                },

                patternTableId:"",
                table:[],
                fileList:[],
                labelPosition: "top",
                activeName: "patternInfo",
                patternData:"",
                showOnlyOneTable:"",
                patternTableData:"",
                viewPattern: true,
                viewTable: true,
                updateTable: false,
                sendDataTable: false,
                createTable: false,
                showTable: false,
                patternId:"",
                sourceId:"",
                file:'',

                pagination:{
                    pageSize: 10,
                    currentPage: 1,
                    totalPages: 0,
                    totalElements: 0,
                },

                pattern: {
                    check: [],
                    key: "id",
                    sort: "",
                    id: "",
                    nameTable: "",
                    fileCount: "",
                    sourceId: "",
                    archiveFileCount: "",
                    description: "",
                    direction: "",
                    management: "",
                    isArchive: "",
                    dateCreation: "",
                    dateCreation1: "",
                    dateCreation2: "",
                    dateDeactivation: "",
                    dateDeactivation1: "",
                    dateDeactivation2: "",
                    dateActivation: "",
                    dateActivation1: "",
                    dateActivation2: "",
                    lastUpdate: "",
                    lastUpdate1: "",
                    lastUpdate2: "",
                },

                patternTable:{
                    check: [],
                    key: "id",
                    sort: "",
                    id:"",
                    nameTable:"",
                    nameFile:"",
                    isArchive: "",
                    dateCreation: "",
                    dateCreation1: "",
                    dateCreation2: "",
                    dateDeactivation: "",
                    dateDeactivation1: "",
                    dateDeactivation2: "",
                    dateActivation: "",
                    dateActivation1: "",
                    dateActivation2: "",
                    lastUpdate: "",
                    lastUpdate1: "",
                    lastUpdate2: "",
                },

                source:{
                    name:"",
                    longName:"",
                    shortName:"",
                    description:"",
                    addDescription:"",
                    scope:"",
                    periodicity:"",
                    renewalPeriod:"",
                    type:"",
                    tags:"",
                    providerLink:"",
                    dataSource:"",
                    isArchive: "",
                    dateCreation: "",
                    dateDeactivation: "",
                    dateActivation: "",
                    lastUpdate: "",
                },

                rules: {
                    name: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    description: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    direction: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                    management: [
                        { required: true, message: 'Заполните поле', trigger: 'blur' }
                    ],
                }
            }
        },
        methods:{
            updateSource(){
                router.push("/source/update/" + this.sourceId)
            },

            onCurrentChangeVersion(value){
                this.paginationVersion.currentPage = value;
                let currentPage = this.paginationVersion.currentPage - 1;
                AXIOS.get("tableCreator/getAllOldVersions?oldName=" + this.tableName +"&size="
                    + this.paginationVersion.pageSize + "&page=" + currentPage).then(response => {
                    this.patternTableVersion = response.data;
                })
            },

            primaryChange(tableName, pole){
                if(tableName + pole !== this.primaryKey){
                    document.getElementById(tableName + pole).classList.remove("common");
                    document.getElementById(tableName + pole).classList.add("primary");
                    if(this.primaryKey !== "") {
                        document.getElementById(this.primaryKey).classList.remove("primary");
                        document.getElementById(this.primaryKey).classList.add("common");
                    }
                    this.primaryKey = tableName + pole;
                }
            },

            downloadFile(fileName){
                AXIOS({
                    url: 'fileUnLoader/getPatternFile/'+fileName,
                    method: 'GET',
                    responseType: 'blob', // important
                }).then((response) => {
                    const url = window.URL.createObjectURL(new Blob([response.data]));
                    const link = document.createElement('a');
                    link.href = url;
                    link.setAttribute('download', fileName); //or any other extension
                    document.body.appendChild(link);
                    link.click();
                });
            },

            onCurrentChange(){
                this.pagination.currentPage = value;
                let currentPage = this.pagination.currentPage - 1;
                AXIOS.get("sourceLogger/getAll/"+this.$route.params.id +"?size=" + this.pagination.pageSize + "&page=" + currentPage).then(response => {
                    this.patternLog = response.data.content;
                })
            },

            backUpdate(){
                this.$confirm('Уверены что хотите вернуться?', 'Назад', {
                    confirmButtonText: 'Да',
                    cancelButtonText: 'Нет',
                    type: 'warning'
                }).then(() => {
                    this.pattern = "";
                    this.viewPattern = true;
                    this.updatePage();
                }).catch(() => {

                });
            },

            sendData(file, fileList){
                let formData = new FormData();
                formData.append("file",file.raw);
                formData.append("patternTableId",this.patternTableId);
                AXIOS.post("fileLoader/sendData/",
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }).then(response => {
                    this.notify('Успешно','Данные были загружены','success');
                    let tableData = new FormData();
                    tableData.append("id",this.patternTableId);
                    AXIOS.post("tableCreator/getTable/",tableData).then(response => {
                        this.showOnlyOneTable = response.data;
                    });
                });

            },

            sendFiles(file, fileList){
                let formData = new FormData();
                formData.append("file",file.raw);
                formData.append("patternId",this.patternId);
                AXIOS.post("fileLoader/checkDates/",
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }).then(response => {
                    let error = false;
                    for(let i = 0; i<response.data.length; i++) {
                        if(response.data[i].status === "ERROR"){
                            error = true;
                            this.$alert('В табличке меньше полей, чем в файле. Возможна утечка данных. Обновите поля таблички или измените файл (' + response.data[i].filename + ')', 'Ошибка', {
                                confirmButtonText: 'OK',
                            });
                        } else if (response.data[i].status === "WARN") {
                            this.$confirm('В табличке больше полей, чем в файле. Хотите продолжить загрузку? Либо вы можете обновить поля таблички или измените файл (' + response.data[i].filename + ')', 'Ошибка', {
                                confirmButtonText: 'Отменить загрузку',
                                cancelButtonText: 'Продолжить загрузку',
                                type: 'warning'
                            }).then(() => {
                                error = true;
                            });
                        }
                    }
                    if(error === false){
                        AXIOS.post("fileLoader/sendDates/",
                            formData,
                            {
                                headers: {
                                    'Content-Type': 'multipart/form-data'
                                }
                            }).then(response => {
                            this.notify('Успешно','Данные были загружены','success');
                            this.updatePage();
                        });
                    }
                });
            },

            deleteTable(id) {
                AXIOS.get("tableCreator/archive/" + id).then(response => {
                    if(response.data.name !== ""){
                        this.notify('Успешно','Таблица была активирована','success');
                        this.updatePage();
                    } else {
                        this.notify('Ошибка','Таблица не была активирована','error');
                    }
                });
            },

            deleteOneTable(id) {
                this.deleteTable(id);
            },

            deArchiveTable(id){
                AXIOS.get("tableCreator/deArchive/" + id ).then(response => {
                    if(response.data.name !== ""){
                        this.notify('Успешно','Таблица была активирована','success');
                        this.updatePage();
                    } else {
                        this.notify('Ошибка','Таблица не была активирована','error');
                    }
                });
            },

            deArchiveOneTable(id){
                this.deArchiveTable(id);
            },

            showOneTable(id){
                this.isMainPage = false;
                this.patternTableId = id;
                this.viewTable = false;
                this.updateTable = false;
                this.createTable = false;
                this.showTable = true;
                this.sendDataTable = false;
                let formData = new FormData();
                formData.append("id",id);
                AXIOS.post("tableCreator/getTable/",formData).then(response => {
                    this.showOnlyOneTable = response.data;
                    AXIOS.get("tableCreator/" + this.patternTableId).then(response => {
                        this.patternTableData = response.data;
                        this.tableName = response.data.nameTable;
                        AXIOS.get("tableCreator/getAllOldVersions?oldName=" + this.tableName + "&size=" + this.paginationVersion.pageSize).then(response => {
                            this.patternTableVersion = response.data;
                            this.paginationVersion.totalPages = response.data.totalPages;
                            this.paginationVersion.totalElements = response.data.totalElements;
                        });
                    });
                });
            },

            showTableTab(change){
                if(change === "yes"){
                    this.$confirm('Вы уверены что хотите вернуться назад?', 'Назад', {
                        confirmButtonText: 'Да',
                        cancelButtonText: 'Нет',
                        type: 'warning'
                    }).then(() => {
                        this.isMainPage = true;
                        this.viewTable = true;
                        this.updateTable = false;
                        this.createTable = false;
                        this.showTable = false;
                        this.sendDataTable = false;
                        this.updatePage();
                    }).catch(() => {
                    });
                } else {
                    this.patternTableId = "";
                    this.isMainPage = true;
                    this.viewTable = true;
                    this.updateTable = false;
                    this.createTable = false;
                    this.showTable = false;
                    this.sendDataTable = false;
                    this.updatePage();
                }
            },

            addTableTab(){
                this.isMainPage = false;
                this.viewTable = false;
                this.updateTable = false;
                this.createTable = true;
                this.showTable = false;
                this.sendDataTable = false;
            },


            addTable(){
                let existingTable = false;
                for(let i = 0; i<this.table.length; i++){
                    let oneTable = this.table[i];
                    let key = [];
                    let type = [];
                    let primary = [];
                    let model = oneTable.tableModel.models;
                    let tableName = oneTable.tableModel.tableName;
                    let fileName = oneTable.tableModel.filename;
                    let primaryKey = oneTable.tableModel.primaryKey;
                    for(let j = 0; j<model.length; j++){
                        primary.push(primaryKey === model[j].key);
                        key.push(model[j].key);
                        type.push(model[j].type);
                    }
                    let formData = new FormData();

                    formData.append("filename", fileName );
                    formData.append("tableName", tableName );
                    formData.append("names", key );
                    formData.append("types", type );
                    formData.append("primaries", primary );
                    formData.append("patternId", this.patternId);
                    // formData.append("token", getToken());

                    AXIOS.get("/tableCreator/exist/"+tableName).then(response => {
                        existingTable = response.data;

                        if(existingTable === true) {
                            this.notify("Ошибка","Таблица (" + tableName + ") c таким именем уже существует", "error");
                        } else {
                            AXIOS.post("/tableCreator/create",
                                formData,
                                {
                                    headers: {
                                        'Content-Type': 'multipart/form-data'
                                    }
                                }
                            ).then(response => {
                                if(response === false) {
                                    this.notify("Ошибка","Таблица " + tableName + "не была создана", "error");
                                } else {
                                    this.notify("Успешно","Таблица " + tableName + " была создана", "success");
                                }
                            });
                        }
                    });

                }
            },

            querySearch(queryString, cb) {
                let links = this.links;
                let results = queryString ? links.filter(this.createFilter(queryString)) : links;
                cb(results);
            },

            createFilter(queryString) {
                return (link) => {
                    return (link.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
                };
            },

            loadAll() {
                return [
                    { "value": "integer" },
                    { "value": "bigint" },
                    { "value": "real" },
                    { "value": "double precision" },
                    { "value": "time" },
                    { "value": "date" },
                    { "value": "timestamp" },
                    { "value": "text" }
                ];
            },

            clearForm(){
                this.table = "";
            },

            onChange(file, fileList) {
                let formData = new FormData();
                formData.append("file",file.raw);
                AXIOS.post("fileLoader/firstUpload/",
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }).then(response => {
                    this.table = response.data;
                    for(let i = 0; i < this.table.length; i++) {
                        this.table[i].tableModel.primaryKey = this.table[0].tableModel.models[0].key;
                    }
                });
            },

            notify(title,message,type) {
                this.$notify({
                    title: title,
                    message: message,
                    type: type
                });
            },

            updatePattern(){
                this.viewPattern = false;
            },

            updatePatternAccept(){
                let formData = new FormData();
                formData.append("id",this.pattern.id);
                formData.append("name",this.pattern.name);
                formData.append("fileCount",this.pattern.fileCount);
                formData.append("archiveFileCount",this.pattern.archiveFileCount);
                formData.append("description",this.pattern.description);
                formData.append("direction",this.pattern.direction);
                formData.append("management",this.pattern.management);
                formData.append("sourceId",this.pattern.sourceId);
                formData.append("isArchive",this.pattern.isArchive);
                // formData.append("token", getToken());
                AXIOS.post("/pattern/update",
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }
                ).then(response => {
                    if(response.data.name == null){
                        this.notify("Ошибка","Ошибка при изменении шаблона.","error");
                    } else {
                        this.notify("Успешно",'Шаблон "' + response.data.name + '" успешно изменен.',"success");
                    }
                });
            },

            updatePage() {
                AXIOS.get("pattern/" + this.patternId).then(response => {
                    this.pattern = response.data;
                });

                if (this.patternTable.dateCreation !== null && this.patternTable.dateCreation !== "") {
                    this.patternTable.dateCreation1 = this.patternTable.dateCreation[0];
                    this.patternTable.dateCreation2 = this.patternTable.dateCreation[1];
                } else {
                    this.patternTable.dateCreation1 = "";
                    this.patternTable.dateCreation2 = "";
                }

                if (this.patternTable.dateDeactivation !== null && this.patternTable.dateDeactivation !== "") {
                    this.patternTable.dateDeactivation1 = this.patternTable.dateDeactivation[0];
                    this.patternTable.dateDeactivation2 = this.patternTable.dateDeactivation[1];
                } else {
                    this.patternTable.dateDeactivation1 = "";
                    this.patternTable.dateDeactivation2 = "";
                }

                if (this.patternTable.dateActivation !== null && this.patternTable.dateActivation !== "") {
                    this.patternTable.dateActivation1 = this.patternTable.dateActivation[0];
                    this.patternTable.dateActivation2 = this.patternTable.dateActivation[1];
                } else {
                    this.patternTable.dateActivation1 = "";
                    this.patternTable.dateActivation2 = "";
                }

                if (this.patternTable.lastUpdate !== null && this.patternTable.lastUpdate !== "") {
                    this.patternTable.lastUpdate1 = this.patternTable.lastUpdate[0];
                    this.patternTable.lastUpdate2 = this.patternTable.lastUpdate[1];
                } else {
                    this.patternTable.lastUpdate1 = "";
                    this.patternTable.lastUpdate2 = "";
                }

                let formData = new FormData();
                formData.append("isArchive", this.value);
                formData.append("sort", this.patternTable.sort);
                formData.append("key", this.patternTable.key);
                formData.append("id", this.patternTable.id);
                formData.append("name", this.patternTable.nameTable);
                formData.append("direction", this.patternTable.nameFile);
                formData.append("dateCreation1", this.patternTable.dateCreation1);
                formData.append("dateCreation2", this.patternTable.dateCreation2);
                formData.append("dateDeactivation1", this.patternTable.dateDeactivation1);
                formData.append("dateDeactivation2", this.patternTable.dateDeactivation2);
                formData.append("dateActivation1", this.patternTable.dateActivation1);
                formData.append("dateActivation2", this.patternTable.dateActivation2);
                formData.append("lastUpdate1", this.patternTable.lastUpdate1);
                formData.append("lastUpdate2", this.patternTable.lastUpdate2);
                formData.append("size", this.pagination.pageSize);
                formData.append("page", this.pagination.currentPage - 1);
                AXIOS.post("/tableCreator/getAllSort/" + this.patternId,
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }).then(response => {
                    this.pagination.totalPages = response.data.totalPages;
                    this.pagination.totalElements = response.data.totalElements;
                    this.patternTableData = response.data.content;
                })
            },

            sort(key){
                if(this.patternTable.dateCreation !== null && this.patternTable.dateCreation !== "") {
                    this.patternTable.dateCreation1 = this.patternTable.dateCreation[0];
                    this.patternTable.dateCreation2 = this.patternTable.dateCreation[1];
                } else {
                    this.patternTable.dateCreation1 = "";
                    this.patternTable.dateCreation2 = "";
                }

                if(this.patternTable.dateDeactivation !== null && this.patternTable.dateDeactivation !== "") {
                    this.patternTable.dateDeactivation1 = this.patternTable.dateDeactivation[0];
                    this.patternTable.dateDeactivation2 = this.patternTable.dateDeactivation[1];
                } else {
                    this.patternTable.dateDeactivation1 = "";
                    this.patternTable.dateDeactivation2 = "";
                }

                if(this.patternTable.dateActivation !== null && this.patternTable.dateActivation !== "") {
                    this.patternTable.dateActivation1 = this.patternTable.dateActivation[0];
                    this.patternTable.dateActivation2 = this.patternTable.dateActivation[1];
                } else {
                    this.patternTable.dateActivation1 = "";
                    this.patternTable.dateActivation2 = "";
                }

                if(this.patternTable.lastUpdate !== null && this.patternTable.lastUpdate !== "") {
                    this.patternTable.lastUpdate1 = this.patternTable.lastUpdate[0];
                    this.patternTable.lastUpdate2 = this.patternTable.lastUpdate[1];
                } else {
                    this.patternTable.lastUpdate1 = "";
                    this.patternTable.lastUpdate2 = "";
                }

                if(this.patternTable.key === key ) {
                    switch(this.patternTable.sort) {
                        case "":
                            this.patternTable.sort = "asc";
                            break;
                        case "asc":
                            this.patternTable.sort = "desc";
                            break;
                        case "desc":
                            this.patternTable.sort = "";
                            break;
                    }
                }
                else {
                    this.patternTable.key = key;
                    this.patternTable.sort = "asc";
                }
                let formData = new FormData();
                formData.append("isArchive",this.value);
                formData.append("sort",this.patternTable.sort);
                formData.append("key",this.patternTable.key);
                formData.append("id",this.patternTable.id);
                formData.append("name",this.patternTable.nameTable);
                formData.append("direction",this.patternTable.nameFile);
                formData.append("dateCreation1",this.patternTable.dateCreation1);
                formData.append("dateCreation2",this.patternTable.dateCreation2);
                formData.append("dateDeactivation1",this.patternTable.dateDeactivation1);
                formData.append("dateDeactivation2",this.patternTable.dateDeactivation2);
                formData.append("dateActivation1",this.patternTable.dateActivation1);
                formData.append("dateActivation2",this.patternTable.dateActivation2);
                formData.append("lastUpdate1",this.patternTable.lastUpdate1);
                formData.append("lastUpdate2",this.patternTable.lastUpdate2);
                formData.append("size",this.pagination.pageSize);
                formData.append("page",this.pagination.currentPage - 1);
                AXIOS.post("/tableCreator/getAllSort/" + this.patternId,
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }).then(response => {
                    this.pagination.totalPages = response.data.totalPages;
                    this.pagination.totalElements = response.data.totalElements;
                    this.patternTableData = response.data.content;
                })
            },

        },

        mounted() {
            // AXIOS.get("/user/isAdmin/" + getToken()).then(response=>{
            //     this.isAdmin = response.data;
            // });

            this.patternId = this.$route.params.id;
            AXIOS.get("pattern/" + this.patternId).then(response => {
                if(response.data === ""){
                    router.push({name:'NotFoundPages'})
                } else {
                    this.pattern = response.data;
                    this.sourceId = response.data.sourceId;

                    // AXIOS.get("user/isChangeSource/" + getToken() + "/" + this.sourceId).then(response=>{
                    //     this.userAccess = response.data;
                    // });

                    AXIOS.get("source/" + this.sourceId).then(response => {
                        this.source = response.data;
                    });
                }

            });

            AXIOS.get("tableCreator/getAll/" + this.patternId).then(response => {
                this.pagination.totalPages = response.data.totalPages;
                this.pagination.totalElements = response.data.totalElements;
                this.patternTableData = response.data.content;

            });

            AXIOS.get("patternLogger/getAll/"+this.$route.params.id +"?size=" + this.pagination.pageSize).then(response => {
                this.patternLog = response.data.content;
                this.pagination.totalPages = response.data.totalPages;
                this.pagination.totalElements = response.data.totalElements;
            });

            AXIOS.get("fileUnLoader/getAllPatternFileByPatternId/"+this.patternId).then(response => {
                this.patternFile = response.data;
            });
            this.links = this.loadAll();
        }
    }
</script>

<style scoped>
    table, td, th {
        border: 1px solid #d7d7d7;
        text-align: center;
    }

    td{
        padding: 10px;
    }

    table {
        border-collapse: collapse;
        width: 100%;
    }

    th {
        padding: 10px;
        height: 50px;
    }

    .common {
        margin-bottom: 10px;
        background-color: #1ab394;
        border-color: #1ab394
    }

    .primary {
        margin-bottom: 10px;
        background-color: #ffcf06;
        border-color: #ffcf06;
    }

    .label{
        word-break: break-all;
    }
</style>