<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/logs/sourceLogs' }">Логи источников</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/logs/sourceLogs/' + this.sourceLogId }">Просмотр</el-breadcrumb-item>
        </el-breadcrumb>
        <p style="font-size: 20px">Логи источника </p>
        <el-row :gutter="20">
            <el-col :span="12">
                <div>
                    <el-form :label-position="labelPosition" label-width="100px" :model="sourceLogInfo">
                        <el-form-item label="Номер:">
                            {{sourceLogInfo.id}}
                        </el-form-item>
                        <el-form-item label="Действие:">
                            {{sourceLogInfo.actions.action}}
                        </el-form-item>
                        <el-form-item label="Статус:">
                            {{sourceLogInfo.statuses.status}}
                        </el-form-item>
                    </el-form>
                </div>
            </el-col>
            <el-col :span="12">
                <div>
                    <el-form :label-position="labelPosition" label-width="100px" :model="sourceLogInfo">
                        <el-form-item label="Ошибка:">
                            {{sourceLogInfo.errors.error}}
                        </el-form-item>
                        <el-form-item label="Источник:">
                            {{sourceLogInfo.sourceId}}
                        </el-form-item>
                        <el-form-item label="Дата изменения:">
                            {{sourceLogInfo.dateCreation}}
                        </el-form-item>
                    </el-form>
                </div>
            </el-col>
        </el-row>

        <div>
            <table>
                <tr>
                    <th>Поле</th>
                    <th>Было</th>
                    <th>Стало</th>
                </tr>
                <tr v-for="log in sourceLog">
                    <td>{{log.columnName}}</td>
                    <td>{{log.before}}</td>
                    <td>{{log.after}}</td>
                </tr>
            </table>
            <el-button @click="backToLogsTable"
                       style="margin-top: 10px; background-color: #1ab394; border-color: #1ab394; color: white;">
                Назад
            </el-button>

        </div>
    </div>
</template>

<script>
    import router from "../../router/router";
    import {AXIOS} from "../../AXIOS/http-common";
    export default {
        name: "sourceLogById",
        data() {
            return{
                labelPosition: "top",
                sourceLogId:"",
                sourceLogInfo: "",
                sourceLog:"",
            }
        },

        methods: {
            backToLogsTable(){
                router.push({name:'SourceLogs'})
            }
        },

        mounted() {
            this.sourceLogId = this.$route.params.id;
            AXIOS.get("sourceLogger/getAll/beforeAfter/" + this.sourceLogId).then(response => {
                this.sourceLog = response.data.content;
            });

            AXIOS.get("sourceLogger/" + this.sourceLogId).then(response => {
                if(response.data === ""){
                    router.push({name:'NotFoundPages'})
                } else {
                    console.log(response);
                    this.sourceLogInfo = response.data;
                }
            })
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