    <template>
        <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
            <p style="font-size: 20px">Логи шаблона </p>
            <el-row :gutter="20">
                <el-col :span="12">
                    <div>
                        <el-form :label-position="labelPosition" label-width="100px" :model="patternLogInfo">
                            <el-form-item label="Номер:">
                                {{patternLogInfo.id}}
                            </el-form-item>
                            <el-form-item label="Действие:">
                                {{patternLogInfo.actions.action}}
                            </el-form-item>
                            <el-form-item label="Краткое наименование набора:">
                                {{patternLogInfo.statuses.status}}
                            </el-form-item>
                        </el-form>
                    </div>
                </el-col>
                <el-col :span="12">
                    <div>
                        <el-form :label-position="labelPosition" label-width="100px" :model="patternLogInfo">
                            <el-form-item label="Ошибка:">
                                {{patternLogInfo.errors.error}}
                            </el-form-item>
                            <el-form-item label="Источник:">
                                {{patternLogInfo.patternId}}
                            </el-form-item>
                            <el-form-item label="Дата изменения:">
                                {{patternLogInfo.dateCreation}}
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
                    <tr v-for="log in patternLog">
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
            name: "patternLogById",
            data() {
                return{
                    labelPosition: "top",
                    patternLogId:"",
                    patternLogInfo: "",
                    patternLog:"",
                }
            },

            methods: {

            },

            mounted() {
                this.patternLogId = this.$route.params.id;
                AXIOS.get("patternLogger/getAll/beforeAfter/" + this.patternLogId).then(response => {
                    console.log(response);
                    this.patternLog = response.data.content;
                });

                AXIOS.get("patternLogger/" + this.patternLogId).then(response => {
                    console.log(response);
                    this.patternLogInfo = response.data;
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