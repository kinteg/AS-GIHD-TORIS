<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Логи шаблонов </p>
        <div class="horizontal-scroll-wrapper  rectangles">
            <table style="overflow-x: auto; ">
                <tr>
                    <th>Номер</th>
                    <th>Действие</th>
                    <th>Статус</th>
                    <th>Ошибка</th>
                    <th>Шаблон</th>
                    <th>Дата</th>
                </tr>
                <tr v-for="log in patternLog">
                    <td>{{log.id}}</td>
                    <td>{{log.actions.action}}</td>
                    <td>{{log.statuses.status}}</td>
                    <td>{{log.errors.error}}</td>
                    <td>{{log.patternId}}</td>
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
    </div>
</template>

<script>
    import router from "../../router/router";
    import {AXIOS} from "../../AXIOS/http-common";
    import MyPagination from "../general/pagination.vue";
    export default {
        name: "patternLog",
        components: {MyPagination},
        data() {
            return {
                patternLog:"",
                pagination:{
                    pageSize: 10,
                    currentPage: 1,
                    totalPages: 0,
                    totalElements: 0,
                },
            }
        },
        methods:{
            onCurrentChange(value) {
                this.pagination.currentPage = value;
                let currentPage = this.pagination.currentPage - 1;
                AXIOS.get("/patternLogger/getAll?size=" + this.pagination.pageSize + "&page=" + currentPage).then(response => {
                    this.patternLog = response.data.content;
                })
                    .catch(error => {
                        console.log('ERROR: ' + error);
                    })
            },
            onSizeChange(value) {
                this.pagination.pageSize = value;
                this.pagination.currentPage = 1;
                let currentPage = this.pagination.currentPage - 1;

                AXIOS.get("/patternLogger/getAll?size=" + this.pagination.pageSize + "&page=" + currentPage).then(response => {
                    this.patternLog = response.data.content;
                })
                    .catch(error => {
                        console.log('ERROR: ' + error);
                    })
            }
        },
        mounted() {
            AXIOS.get("patternLogger/getAll").then(response => {
                this.patternLog = response.data.content;
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