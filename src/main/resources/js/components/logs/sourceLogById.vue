<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
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
                        <el-form-item label="Краткое наименование набора:">
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

        <div class="horizontal-scroll-wrapper  rectangles">
            <table style=" overflow-x: auto; ">
                <tr>
                    <th>Поле</th>
                    <th>Было</th>
                    <th>Стало</th>
                </tr>
                <tr v-for="log in sourceLog">
                    <td></td>
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

        },

        mounted() {
            this.sourceLogId = this.$route.params.id;
            // /getAll/beforeAfter/
            AXIOS.get("sourceLogger/getAll/beforeAfter/" + this.sourceLogId).then(response => {
                console.log(response);
                this.sourceLog = response.data.content;
            });

            AXIOS.get("sourceLogger/" + this.sourceLogId).then(response => {
                console.log(response);
                this.sourceLogInfo = response.data;
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