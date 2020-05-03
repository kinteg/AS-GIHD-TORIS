<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="16">
                <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item :to="{ path: '/patternTable/show' }">Все таблицы</el-breadcrumb-item>
                        <el-breadcrumb-item :to="{ path: '/patternTable/show/' + this.$route.params.id}">Просмотр</el-breadcrumb-item>
                    </el-breadcrumb>
                    <p style="font-size: 20px">Просмотр таблицы</p>
                    <p style="font-size: 20px">{{showOnlyOneTable.tableModel.tableName}}
                        <span v-if="patternTable.isActive === true && patternTable.isArchive === false">
                        <el-upload
                                style="float: right;"
                                class="upload-demo"
                                ref="upload"
                                action=""
                                :limit="1"
                                :on-change="sendFiles"
                                :auto-upload="false">
                            <el-button slot="trigger" style="background-color: #1ab394; border-color: #1ab394" size="small" type="primary">Загрузить данные в таблицу</el-button>
                        </el-upload>
                        <router-link :to="'/patternTable/update/' + this.patternTableId">
                            <el-button style="float: right; margin-right: 10px; margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394; color: white;">
                                Обновить поля
                            </el-button>
                        </router-link>
                            </span>
                    </p>
                    <div v-if="showOnlyOneTable.values.content.length !== 0" class="horizontal-scroll-wrapper  rectangles">
                        <table style="display: block; overflow-x: auto;">
                            <tr>
                                <th></th>
                                <th @click="sort(pole.key)" v-for="pole in showOnlyOneTable.tableModel.models">{{pole.key}}</th>
                            </tr>
                            <tr>
                                <td><el-button @click="sort('')"  style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary" size="mini" icon="el-icon-search"></el-button></td>
                                <td v-for="serchPole in oneTable">
                                    <input type="text" class="input" :id="serchPole.key"/>
                                </td>
                            </tr>
                            <tr v-for="value in showOnlyOneTable.values.content">
                                <td></td>
                                <td v-for="oneValue in value">{{oneValue}}</td>
                            </tr>
                        </table>
                        <my-pagination
                                :page-size="paginationOneTable.pageSize"
                                :current-page="paginationOneTable.currentPage"
                                :totalPages="paginationOneTable.totalPages"
                                :totalElements="paginationOneTable.totalElements"
                                @onCurrentChange="onCurrentChangeOneTable"
                                @onSizeChange="onSizeChangeOneTable"/>
                        <el-button @click="backToPatternTableTable" style="background-color: #1ab394; border-color: #1ab394; color: white;">Назад</el-button>

                    </div>
                    <div v-else>
                        <p style="font-size: 20px">Данных нет</p>
                    </div>
                </div>
            </el-col>
            <el-col :span="8">
                <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
                    <p style="font-size: 20px">История изменений</p>
                    <table style="overflow-x: auto; ">
                        <tr>
                            <th>Дата изменения</th>
                            <th>Ссылка</th>
                        </tr>
                        <tr v-for="log in patternTableLog">
                            <td>{{log.dateCreation}}</td>
                            <td><router-link :to="'/logs/patternTableLogs/' + log.id">Просмотр</router-link></td>
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
                <div style="background-color: white; padding: 30px; margin-top: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
                    <p style="font-size: 20px">Файлы</p>
                    <table style="overflow-x: auto; ">
                        <tr>
                            <th>Номер</th>
                            <th>Шаблон</th>
                            <th>Файл</th>
                            <th>Дата создания</th>
                        </tr>
                        <tr v-for="file in patternTableFile">
                            <td>{{file.id}}</td>
                            <td>{{file.patternTableId}}</td>
                            <td>
                                <el-button @click="downloadFile(file.file)"  style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary"  icon="el-icon-download"></el-button>
                            </td>
                            <td>{{file.dateCreation}}</td>
                        </tr>
                    </table>
                </div>
            </el-col>
            <el-col :span="8">
                <div v-if="patternTable.isActive" style="margin-top: 20px; background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
                    <p style="font-size: 20px">Версии</p>
                    <table style="overflow-x: auto; ">
                        <tr>
                            <th></th>
                            <th>Версия</th>
                        </tr>
                        <tr v-for="tableVersion in patternTableVersion.content">
                            <td>
                                <router-link :to="'/patternTable/show/' + tableVersion.id">
                                    <el-button @click="viewVersion(tableVersion.id)" style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394" type="primary" size="mini" icon="el-icon-view"></el-button>
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
    import router from "../../router/router";
    import MyPagination from "../general/pagination.vue";
    import {AXIOS} from "../../AXIOS/http-common";
    export default {
        name: "patternTableView",
        components: {MyPagination},
        props:['tableId'],
        data() {
            return {
                patternTable:"",
                sorted:"",
                key:"",
                oneTable:"",
                tableName:"",
                patternTableVersion:"",
                patternTableFile:"",
                patternTableLog:"",
                patternTableId:"",
                showOnlyOneTable: "",
                paginationOneTable: {
                    pageSize: 10,
                    currentPage: 1,
                    totalPages: 0,
                    totalElements: 0,
                },

                paginationVersion: {
                    pageSize: 5,
                    currentPage: 1,
                    totalPages: 0,
                    totalElements: 0,
                    pagerCount: 2,
                },

                pagination:{
                    pageSize: 5,
                    currentPage: 1,
                    totalPages: 0,
                    totalElements: 0,
                    pagerCount: 2,
                },
            }
        },

        methods:{
            backToPatternTableTable(){
                router.push({name: "ShowPatternTable"});
            },

            sort(key){
                let formData = new FormData();
                let keys = [];
                let values = [];
                formData.append("id", this.patternTableId);

                for(let i = 0; i< this.oneTable.length; i++){
                    if(document.getElementById(this.oneTable[i].key).value !== ""){
                        keys.push(this.oneTable[i].key);
                        values.push(document.getElementById(this.oneTable[i].key).value)
                    }
                }
                if(this.key === key ) {
                    switch(this.sorted) {
                        case "":
                            this.sorted = "asc";
                            break;
                        case "asc":
                            this.sorted = "desc";
                            break;
                        case "desc":
                            this.sorted = "";
                            break;
                    }
                }
                else {
                    this.key = key;
                    this.sorted = "asc";
                }
                formData.append("keys", keys);
                formData.append("values", values);
                formData.append("sort",this.sorted);
                formData.append("nameColumn",this.key);
                formData.append("size",this.paginationOneTable.pageSize);
                formData.append("page",this.paginationOneTable.currentPage - 1);
                AXIOS.post("tableCreator/getTable/",formData).then(response => {
                    this.paginationOneTable.totalPages = response.data.values.totalPages;
                    this.paginationOneTable.totalElements = response.data.values.totalElements;
                    this.showOnlyOneTable = response.data;
                    this.tableName = response.data.tableModel.tableName;
                    this.oneTable = response.data.tableModel.models;
                });
            },

            viewVersion(id){
                router.push({name: "show/" + id});
            },

            updatePage(){
                AXIOS.get("tableCreator/" + this.patternTableId).then(response => {
                    this.patternTable = response.data;
                    console.log(response);
                });

                let formData = new FormData();
                formData.append("id", this.patternTableId);
                AXIOS.post("tableCreator/getTable/",formData).then(response => {
                    this.paginationOneTable.totalPages = response.data.values.totalPages;
                    this.paginationOneTable.totalElements = response.data.values.totalElements;
                    this.showOnlyOneTable = response.data;
                    this.tableName = response.data.tableModel.tableName;
                    this.oneTable = response.data.tableModel.models;
                    console.log(response);
                    AXIOS.get("tableCreator/getAllOldVersions?oldName=" + this.tableName + "&size=" + this.paginationVersion.pageSize).then(response => {
                        this.patternTableVersion = response.data;
                        this.paginationVersion.totalPages = response.data.totalPages;
                        this.paginationVersion.totalElements = response.data.totalElements;
                    });
                });

                AXIOS.get("patternTableLogger/getAll/"+this.$route.params.id +"?size=" + this.pagination.pageSize).then(response => {
                    this.patternTableLog = response.data.content;
                    this.pagination.totalPages = response.data.totalPages;
                    this.pagination.totalElements = response.data.totalElements;
                });

                AXIOS.get("fileUnLoader/getAllPatternTableFileByPatternId/"+this.patternTableId).then(response => {
                    this.patternTableFile = response.data;
                });
            },

            notify(title,message,type) {
                this.$notify({
                    title: title,
                    message: message,
                    type: type
                });
            },

            sendFiles(file, fileList){
                let formData = new FormData();

                formData.append("file",file.raw);
                formData.append("patternTableId", this.patternTableId);
                AXIOS.post("fileLoader/checkData/",
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }).then(response => {
                    if(response.data.status === "OK"){
                        AXIOS.post("fileLoader/sendData/",
                            formData,
                            {
                                headers: {
                                    'Content-Type': 'multipart/form-data'
                                }
                            }).then(response => {
                            this.notify('Успешно','Данные были загружены','success');
                            this.updatePage();
                        });
                    } else if (response.data.status === "WARN"){
                        this.$confirm('В табличке больше полей, чем в файле. Хотите ли вы загрузить данные?', 'Предупреждение', {
                            confirmButtonText: 'Обновить поля таблицы',
                            cancelButtonText: 'Загрузить',
                            type: 'warning'
                        }).then(() => {
                            router.push({name: "PatternTableUpdate"})
                        }).catch(() => {
                            AXIOS.post("fileLoader/sendData/",
                                formData,
                                {
                                    headers: {
                                        'Content-Type': 'multipart/form-data'
                                    }
                                }).then(response => {
                                this.notify('Успешно','Данные были загружены','success');
                                this.updatePage();
                            });
                        });
                    } else if (response.data.status === "ERROR") {
                        this.$confirm('В табличке меньше полей, чем в файле. Возможна утечка данных. Хотите ли вы обновить табличку?', 'Предупреждение', {
                            confirmButtonText: 'Обновить поля таблицы',
                            cancelButtonText: 'Отмена',
                            type: 'error'
                        }).then(() => {
                            router.push({name: "PatternTableUpdate"})
                        });
                    }
                });
            },

            downloadFile(fileName){
                AXIOS({
                    url: 'fileUnLoader/getPatternTableFile/'+fileName,
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

            onCurrentChange(value){
                this.pagination.currentPage = value;
                let currentPage = this.pagination.currentPage - 1;
                AXIOS.get("patternTableLogger/getAll/"+this.$route.params.id +"?size=" + this.pagination.pageSize + "&page=" + currentPage).then(response => {
                    this.patternTableLog = response.data.content;
                })
            },

            onCurrentChangeVersion(value){
                this.paginationVersion.currentPage = value;
                let currentPage = this.paginationVersion.currentPage - 1;
                AXIOS.get("tableCreator/getAllOldVersions?oldName=" + this.tableName +"&size=" + this.paginationVersion.pageSize + "&page=" + currentPage).then(response => {
                    console.log(response);
                    this.patternTableVersion = response.data;
                })
            },

            onSizeChangeOneTable(value){
                let formData = new FormData();
                let keys = [];
                let values = [];
                formData.append("id", this.patternTableId);

                for(let i = 0; i< this.oneTable.length; i++){
                    if(document.getElementById(this.oneTable[i].key).value !== ""){
                        keys.push(this.oneTable[i].key);
                        values.push(document.getElementById(this.oneTable[i].key).value)
                    }
                }

                formData.append("keys", keys);
                formData.append("values", values);
                formData.append("sort",this.sorted);
                formData.append("nameColumn",this.key);
                this.paginationOneTable.pageSize = value;
                this.paginationOneTable.currentPage = 1;
                formData.append("id",this.patternTableId);
                formData.append("size",this.paginationOneTable.pageSize);
                formData.append("page",this.paginationOneTable.currentPage - 1);
                AXIOS.post("tableCreator/getTable/",formData).then(response => {
                    this.showOnlyOneTable = response.data;
                });
            },

            onCurrentChangeOneTable(value){
                let formData = new FormData();
                let keys = [];
                let values = [];
                formData.append("id", this.patternTableId);

                for(let i = 0; i< this.oneTable.length; i++){
                    if(document.getElementById(this.oneTable[i].key).value !== ""){
                        keys.push(this.oneTable[i].key);
                        values.push(document.getElementById(this.oneTable[i].key).value)
                    }
                }

                formData.append("keys", keys);
                formData.append("values", values);
                formData.append("sort",this.sorted);
                formData.append("nameColumn",this.key);
                this.paginationOneTable.currentPage = value;
                formData.append("id",this.patternTableId);
                formData.append("size",this.paginationOneTable.pageSize);
                formData.append("page",this.paginationOneTable.currentPage - 1);
                AXIOS.post("tableCreator/getTable/",formData).then(response => {
                    this.showOnlyOneTable = response.data;
                });
            },
        },

        mounted() {
            this.patternTableId = this.$route.params.id;
            AXIOS.get("tableCreator/" + this.patternTableId).then(response => {
                if(response.data === ""){
                    router.push({name:'NotFoundPages'})
                } else {
                    this.patternTable = response.data;
                }
            });

            let formData = new FormData();
            formData.append("id", this.patternTableId);
            AXIOS.post("tableCreator/getTable/",formData).then(response => {
                this.paginationOneTable.totalPages = response.data.values.totalPages;
                this.paginationOneTable.totalElements = response.data.values.totalElements;
                this.showOnlyOneTable = response.data;

                this.tableName = response.data.tableModel.tableName;
                this.oneTable = response.data.tableModel.models;
                AXIOS.get("tableCreator/getAllOldVersions?oldName=" + this.tableName + "&size=" + this.paginationVersion.pageSize).then(response => {
                    this.patternTableVersion = response.data;
                    this.paginationVersion.totalPages = response.data.totalPages;
                    this.paginationVersion.totalElements = response.data.totalElements;
                });
            });

            AXIOS.get("patternTableLogger/getAll/"+this.$route.params.id +"?size=" + this.pagination.pageSize).then(response => {
                this.patternTableLog = response.data.content;
                this.pagination.totalPages = response.data.totalPages;
                this.pagination.totalElements = response.data.totalElements;
            });

            AXIOS.get("fileUnLoader/getAllPatternTableFileByPatternId/"+this.patternTableId).then(response => {
                this.patternTableFile = response.data;
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

    .input{
        background-color: #FFF;
        background-image: none;
        border-radius: 4px;
        border: 1px solid #DCDFE6;
        color: #606266;
        display: inline-block;
        width: auto;
        height: 32px;
        line-height: 32px;
        outline: 0;
        padding: 0 15px;
        letter-spacing: normal;
        word-spacing: normal;
        text-transform: none;
        text-shadow: none;
        text-align: start;
        -webkit-appearance: textfield;
        -webkit-rtl-ordering: logical;
        cursor: text;
    }
</style>