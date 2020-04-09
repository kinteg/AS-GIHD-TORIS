<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Логи источника </p>
        <el-row :gutter="20">
            <el-col :span="12">
                <div>
                    <el-form :label-position="labelPosition" label-width="100px" :model="patternTableLogInfo">
                        <el-form-item label="Номер:">
                            {{patternTableLogInfo.id}}
                        </el-form-item>
                        <el-form-item label="Действие:">
                            {{patternTableLogInfo.actions.action}}
                        </el-form-item>
                        <el-form-item label="Краткое наименование набора:">
                            {{patternTableLogInfo.statuses.status}}
                        </el-form-item>
                    </el-form>
                </div>
            </el-col>
            <el-col :span="12">
                <div>
                    <el-form :label-position="labelPosition" label-width="100px" :model="patternTableLogInfo">
                        <el-form-item label="Ошибка:">
                            {{patternTableLogInfo.errors.error}}
                        </el-form-item>
                        <el-form-item label="Источник:">
                            {{patternTableLogInfo.sourceId}}
                        </el-form-item>
                        <el-form-item label="Дата изменения:">
                            {{patternTableLogInfo.dateCreation}}
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
                <tr v-for="log in patternTableLog">
                    <td>{{log.columnName}}</td>
                    <td>{{log.before}}</td>
                    <td>{{log.after}}</td>
                </tr>
            </table>
        </div>
    </div>
</template>

<script>
    import router from "../../router/router";
    import {AXIOS} from "../../AXIOS/http-common";
    export default {
        name: "tableLogById",
        data() {
            return{
                labelPosition: "top",
                patternTableLogId:"",
                patternTableLogInfo: "",
                patternTableLog:"",
            }
        },

        methods: {

        },

        mounted() {
            this.patternTableLogId = this.$route.params.id;
            console.log(this.patternTableLogId);
            AXIOS.get("patternTableLogger/getAll/beforeAfter/" + this.patternTableLogId).then(response => {
                console.log(response);
                this.patternTableLog = response.data.content;
            });

            AXIOS.get("patternTableLogger/" + this.patternTableLogId).then(response => {
                console.log(response);
                this.patternTableLogInfo = response.data;
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