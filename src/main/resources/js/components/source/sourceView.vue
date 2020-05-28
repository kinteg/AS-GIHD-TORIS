<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="16">
                <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item
                                :to="{ path: '/source/show' }">
                            Все источники
                        </el-breadcrumb-item>
                        <el-breadcrumb-item
                                :to="{ path: '/source/view/' + this.$route.params.id}">
                            Просмотр
                        </el-breadcrumb-item>
                    </el-breadcrumb>
                    <p style="font-size: 20px">Просмотр источника</p>
                    <div>
                        <el-tabs v-model="activeName">
                            <el-tab-pane label="Источник" name="sourceInfo">
                                <el-row :gutter="20">
                                    <el-col :span="12">
                                        <div>
                                            <el-form :label-position="labelPosition" label-width="100px" :model="source">
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
                                            <el-form :label-position="labelPosition" label-width="100px" :model="source">
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
                                <el-button
                                        @click="back"
                                        style="margin-top: 10px; background-color: #1ab394; border-color: #1ab394; color: white;">
                                    Назад
                                </el-button>
                                <el-button v-if="userAccess"
                                           @click="update"
                                           style="margin-top: 10px; background-color: #1ab394; border-color: #1ab394; color: white;">
                                    Редактировать
                                </el-button>
                            </el-tab-pane>
                            <el-tab-pane label="Шаблоны" name="patternInfo">
                                <span v-if="hiddenTable"  style="background-color: white;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
                                    <pattern-by-source
                                            style="padding: 0 5px 0 0; box-shadow: none "
                                            :access="userAccess"
                                            :source-id="this.$route.params.id"/>
                                </span>
                                <div v-else>
                                    <pattern-view :pattern-id="this.patternId" />
                                    <el-button
                                            @click="backView"
                                            style="background-color: #1ab394; border-color: #1ab394; color: white;">
                                        Назад
                                    </el-button>
                                </div>
                            </el-tab-pane>
                            <el-tab-pane label="Таблицы" name="tableInfo">
                                <pattern-table-all-view-one :access="userAccess" :source-id="this.$route.params.id"/>
                            </el-tab-pane>
                        </el-tabs>
                    </div>
                </div>
            </el-col>
            <el-col v-if="userAccess" :span="8">
                <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
                    <p style="font-size: 20px">История изменений источника</p>
                    <table style="overflow-x: auto; ">
                        <tr>
                            <th>Дата изменения</th>
                            <th>Ссылка</th>
                        </tr>
                        <tr v-for="log in sourceLog">
                            <td>{{log.dateCreation}}</td>
                            <td><router-link :to="'/logs/sourceLogs/' + log.id">Просмотр</router-link></td>
                        </tr>
                    </table>
                    <el-pagination
                            style="margin: 10px auto; text-align: center "
                            class="pager"
                            background
                            layout="prev, pager, next"
                            :page-size="pagination.pageSize"
                            :page-count="pagination.totalPages"
                            :current-page="pagination.currentPage"
                            :pager-count="2"
                            @current-change="onCurrentChange"
                            :total="pagination.totalElements">
                    </el-pagination>
                </div>
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="16">
                <div v-if="isAdmin" style="background-color: white; padding: 30px; margin-top: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
                    <p style="font-size: 20px">Права доступа</p>
                    <el-form :inline="true" :model="operators" class="demo-form-inline">
                        <el-form-item label="ID оператора в ЕСОВ">
                            <el-input v-model="operators.USER_ESOV_UID" placeholder="USER_ESOV_UID"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button v-if="operators.USER_ESOV_UID !== ''"
                                       @click="addUser"
                                       style="background-color: #1ab394; border-color: #1ab394; color: white;">
                                Добавить оператора
                            </el-button>
                        </el-form-item>
                    </el-form>
                    <div v-if="user === null">
                        <p>Для этого источника никто не назначен</p>
                    </div>
                    <div v-else>
                        <table>
                            <tr>
                                <th></th>
                                <th>ФИО</th>
                                <th>ЕСОВ id</th>
                            </tr>
                            <tr v-for="oneUser in user">
                                <td>
                                    <el-button
                                            @click="deleteUser(oneUser.secretKey)"
                                            style="float: right; margin-left: 10px; background-color: #1ab394; border-color: #1ab394 "
                                            type="primary"  icon="el-icon-delete">
                                    </el-button>
                                </td>
                                <td>{{oneUser.fio}}</td>
                                <td>{{oneUser.secretKey}}</td>
                            </tr>
                        </table>
                    </div>
                </div>
            </el-col>
            <el-col :span="8">
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import router from "../../router/router";
    import {AXIOS} from "../../AXIOS/http-common";
    import PatternView from "../pattern/patternView.vue";
    import PatternTable from "../pattern/patternTable.vue";
    import MyPagination from "../general/pagination.vue";
    import PatternTableAllViewOne from "../patternTable/patternTableViewOne.vue";
    import PatternBySource from "../pattern/patternBySource.vue";
    import {getToken} from "../../modules/auth";

    export default {
        name: "sourceView",
        components: {PatternBySource, PatternTableAllViewOne, MyPagination, PatternTable, PatternView},
        data() {
            return {
                userAccess: false,
                isAdmin: false,
                operators:{
                    USER_ESOV_UID:""
                },
                user:"",
                sourceLog:"",
                table:[],
                patternData:[],
                patternId: "",
                hiddenTable: true,
                hiddenView: false,
                hiddenUpdate: false,
                activeName: "sourceInfo",
                labelPosition: "top",

                pagination:{
                    pageSize: 5,
                    currentPage: 1,
                    totalPages: 0,
                    totalElements: 0,
                    pagerCount: 2,
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
                },
                pattern: {
                    check: [],
                    key: "id",
                    sort: "",
                    id: "",
                    name: "",
                    fileCount: "",
                    archiveFileCount: "",
                    description: "",
                    direction: "",
                    management: "",
                    isArchive: "",
                    dateCreation: "",
                    dateDeactivation: "",
                    dateActivation: "",
                    lastUpdate: "",
                }
            }
        },

        methods:{
            update(){
                router.push({name:'sourceUpdate'});
            },

            updateUserTable(){
                AXIOS.get("/user/getAllUserWithSource/" + this.$route.params.id).then(response=>{
                    this.user = response.data;
                });
            },

            notify(title,message,type) {
                this.$notify({
                    title: title,
                    message: message,
                    type: type
                });
            },

            deleteUser(secretKey){
                AXIOS.get("/user/deleteUserInSource/" + getToken() + "/" + this.$route.params.id + "/" + secretKey).then(response=>{
                    console.log(response);
                    this.notify("Успешно", "Оператор был удален", "success");
                    this.updateUserTable();
                })
            },

            addUser(){
                AXIOS.get("/user/setUserInSource/" + getToken() + "/" + this.$route.params.id + "/" + this.operators.USER_ESOV_UID).then(response=>{
                    if(response.data){
                        this.notify("Успешно", "Оператор был добавлен", "success");
                        this.updateUserTable();
                    } else {
                        this.notify("Ошибка", "Ошибка при добавлении оператора. Проверьте введенные данные. Возможно он уже добавлен", "error");
                    }
                })
            },

            back(){
                router.push({name:'show'});
            },

            onCurrentChange(value){
                this.pagination.currentPage = value;
                let currentPage = this.pagination.currentPage - 1;
                AXIOS.get("sourceLogger/getAll/" + this.$route.params.id + "?size=" + this.pagination.pageSize + "&page=" + currentPage).then(response => {
                    this.sourceLog = response.data.content;
                })
            },

            patternView(id) {
                this.hiddenTable = false;
                this.patternId = id;
            },

            backView(){
                this.hiddenTable = true;
                this.patternId = "";
            }
        },

        mounted() {
            AXIOS.get("/user/isChangeSource/" + getToken() + "/" + this.$route.params.id).then(response=>{
                this.userAccess = response.data;
                console.log(this.userAccess);
            });

            AXIOS.get("/user/isAdmin/" + getToken()).then(response=>{
                this.isAdmin = response.data;
            });

            AXIOS.get("/user/getAllUserWithSource/" + this.$route.params.id).then(response=>{
                this.user = response.data;
            });

            AXIOS.get("source/" + this.$route.params.id).then(response => {
                if(response.data === ""){
                    router.push({name:'NotFoundPages'})
                } else {
                    this.source = response.data;
                }
            });

            AXIOS.get("pattern/getAll/" + this.$route.params.id).then(response => {
                this.patternData = response.data.content;
            });

            AXIOS.get("tableCreator/getAllBySource/" + this.$route.params.id).then(response => {
                this.table = response.data;
            });

            AXIOS.get("sourceLogger/getAll/"+this.$route.params.id +"?size=" + this.pagination.pageSize).then(response => {
                this.sourceLog = response.data.content;
                this.pagination.totalPages = response.data.totalPages;
                this.pagination.totalElements = response.data.totalElements;
            });
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

    .label{
        word-break: break-all;
    }
</style>