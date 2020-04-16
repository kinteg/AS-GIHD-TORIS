<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="16">
                <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
                    <p style="font-size: 20px">Просмотр таблицы
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
                    </p>
                    <div class="horizontal-scroll-wrapper  rectangles">
                        <table style="display: block; overflow-x: auto;">
                            <tr>
                                <th v-for="pole in showOnlyOneTable.tableModel.models">{{pole.key}}</th>
                            </tr>
                            <tr v-for="value in showOnlyOneTable.values.content">
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
                        <!--            <el-button @click="showTableTab" style="margin-top: 10px; background-color: #1ab394; border-color: #1ab394; color: white;">Назад</el-button>-->
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
                <div style="margin-top: 20px; background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
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
            viewVersion(id){
                router.push({name: "patternTable/show/" + id});
            },

            updatePage(){
                let formData = new FormData();
                formData.append("id", this.patternTableId);
                AXIOS.post("tableCreator/getTable/",formData).then(response => {
                    this.paginationOneTable.totalPages = response.data.values.totalPages;
                    this.paginationOneTable.totalElements = response.data.values.totalElements;
                    this.showOnlyOneTable = response.data;
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
                    } else if (response.data.status === "WARNING"){

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
                    this.patternTableVersion = response.data.content;
                })
            },

            onSizeChangeOneTable(value){
                this.paginationOneTable.pageSize = value;
                this.paginationOneTable.currentPage = 1;
                let formData = new FormData();
                formData.append("id",this.patternTableId);
                formData.append("size",this.paginationOneTable.pageSize);
                formData.append("page",this.paginationOneTable.currentPage - 1);
                AXIOS.post("tableCreator/getTable/",formData).then(response => {
                    this.showOnlyOneTable = response.data;
                });
            },

            onCurrentChangeOneTable(value){
                this.paginationOneTable.currentPage = value;
                let formData = new FormData();
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
            let formData = new FormData();
            formData.append("id", this.patternTableId);
            AXIOS.post("tableCreator/getTable/",formData).then(response => {
                this.paginationOneTable.totalPages = response.data.values.totalPages;
                this.paginationOneTable.totalElements = response.data.values.totalElements;
                this.showOnlyOneTable = response.data;
                this.tableName = response.data.tableModel.tableName;
                AXIOS.get("tableCreator/getAllOldVersions?oldName=" + this.tableName + "&size=" + this.paginationVersion.pageSize).then(response => {
                    this.patternTableVersion = response.data;
                    console.log(response);
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

</style>