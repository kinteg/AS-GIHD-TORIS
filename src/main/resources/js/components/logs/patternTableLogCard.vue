<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">История изменений таблицы</p>
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
                :pager-count="pagination.pagerCount"
                @current-change="onCurrentChange"
                :total="pagination.totalElements">
        </el-pagination>
    </div>
</template>

<script>
    import {AXIOS} from "../../AXIOS/http-common";

    export default {
        name: "patternTableLogCard",
        props:['patternTableId'],
        data(){
            return{
                patternTableLog:"",
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
            onCurrentChange(value){
                this.pagination.currentPage = value;
                let currentPage = this.pagination.currentPage - 1;
                AXIOS.get("patternTableLogger/getAll/"+this.patternTableId +"?size=" + this.pagination.pageSize + "&page=" + currentPage).then(response => {
                    this.patternTableLog = response.data.content;
                })
            },
        },

        mounted() {
            AXIOS.get("patternTableLogger/getAll/"+this.patternTableId +"?size=" + this.pagination.pageSize).then(response => {
                this.patternTableLog = response.data.content;
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