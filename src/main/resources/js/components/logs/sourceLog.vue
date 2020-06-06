<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/logs/sourceLogs' }">Логи источников</el-breadcrumb-item>
        </el-breadcrumb>
        <p style="font-size: 20px">Логи источников </p>
        <div v-if="sourceLog.length !== 0" class="horizontal-scroll-wrapper  rectangles">
            <table style=" overflow-x: auto; ">
                <tr>
                    <th></th>
                    <th>Номер</th>
                    <th>Действие</th>
<!--                    <th>Кто изменил</th>-->
                    <th>Статус</th>
                    <th>Ошибка</th>
                    <th>Источник</th>
                    <th>Дата</th>
                </tr>
                <tr v-for="log in sourceLog">
                    <td>
                        <el-button
                                @click="showCard(log.id)"
                                style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "
                                type="primary"
                                size="mini"
                                icon="el-icon-view"/>
                    </td>
                    <td>{{log.id}}</td>
                    <td>{{log.actions.action}}</td>
<!--                    <td>{{log.usrId.fio}}</td>-->
                    <td>{{log.statuses.status}}</td>
                    <td>{{log.errors.error}}</td>
                    <td>
                        <router-link v-if="log.sourceId > 0" :to="'/source/view/' + log.sourceId">{{log.sourceId}}</router-link>
                        <span v-else-if="log.sourceId < 0" >-</span>
                    </td>
                    <td>{{log.dateCreation}}</td>
                </tr>
            </table>
            <my-pagination
                    :page-size="pagination.pageSize"
                    :current-page="pagination.currentPage"
                    :totalPages="pagination.totalPages"
                    :totalElements="pagination.totalElements"
                    @onCurrentChange="onCurrentChange"
                    @onSizeChange="onSizeChange"/>
        </div>
        <div v-else>
            <p style="font-size: 20px"> Данных нет</p>
        </div>
    </div>
</template>

<script>
    import router from "../../router/router";
    import {AXIOS} from "../../AXIOS/http-common";
    import MyPagination from "../general/pagination.vue";
    export default {
        name: "sourceLog",
        components: {MyPagination},
        data() {
            return {
                sourceLog:"",
                pagination:{
                    pageSize: 10,
                    currentPage: 1,
                    totalPages: 0,
                    totalElements: 0,
                },
            }
        },
        methods:{
            showCard(id){
                router.push("sourceLogs/" + id);
            },

            onCurrentChange(value) {
                this.pagination.currentPage = value;
                let currentPage = this.pagination.currentPage - 1;
                AXIOS.get("/sourceLogger/getAll?size=" + this.pagination.pageSize + "&page=" + currentPage).then(response => {
                    this.sourceLog = response.data.content;
                }).catch(error => {
                        console.log('ERROR: ' + error);
                    })
            },
            onSizeChange(value) {
                this.pagination.pageSize = value;
                this.pagination.currentPage = 1;
                let currentPage = this.pagination.currentPage - 1;

                AXIOS.get("/sourceLogger/getAll?size=" + this.pagination.pageSize + "&page=" + currentPage).then(response => {
                    this.sourceLog = response.data.content;
                })
                    .catch(error => {
                        console.log('ERROR: ' + error);
                    })
            }
        },
        mounted() {
            AXIOS.get("sourceLogger/getAll").then(response => {
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
</style>