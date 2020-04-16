<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Обновление полей</p>

        <div>
            <el-upload
                    class="upload-demo"
                    ref="upload"
                    action=""
                    :limit="1"
                    :on-remove="clearForm"
                    :on-change="onChange"
                    :auto-upload="false">
                <el-button slot="trigger" style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394" size="small" type="primary">Выберите файл для обновления полей</el-button>
            </el-upload>
            <el-collapse v-for="oneTable in table">
                <el-collapse-item :title="patternTableName" >
                    <el-input :disabled="true" style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394" v-model="patternTableName" placeholder="Название таблицы"></el-input>
                    <el-form v-for="pole in oneTable.tableModel.models" :inline="true"  class="demo-form-inline">
                        <el-form-item >
                            <el-button :id="pole.key" @click="primaryChange(pole.key)" class="common" type="primary" size="mini" icon="el-icon-key"></el-button>
                            <!--                                                    <input :checked="pole.primary" type="radio" :name="oneTable.tableModel.tableName"/>-->
                        </el-form-item>
                        <el-form-item >
                            <el-input v-model="pole.key" placeholder="Approved by"></el-input>
                        </el-form-item>
                        <el-form-item >
                            <el-autocomplete
                                    style="float: right"
                                    class="inline-input"
                                    v-model="pole.type"
                                    :fetch-suggestions="querySearch"
                                    placeholder="Please Input"
                            ></el-autocomplete>
                        </el-form-item>
                    </el-form>
                    <h2>Предпросмотр</h2>
                    <table style=" padding: 0 5px 0 0;">
                        <tr>
                            <th v-for="pole in oneTable.tableModel.models">{{pole.key}}</th>
                        </tr>
                        <tr v-for="value in oneTable.values">
                            <td v-for="oneValue in value">{{oneValue}}</td>
                        </tr>
                    </table>
                </el-collapse-item>
            </el-collapse>
            <!--            <el-button @click="showTableTab('yes')" style="background-color: #1ab394; border-color: #1ab394; color: white;">Назад</el-button>-->
            <!--            <el-button @click="addTable" style="background-color: #1ab394; border-color: #1ab394; color: white;">Сохранить</el-button>-->
        </div>

        <router-link :to="'/patternTable/show'">
            <el-button style=" margin-right: 10px;  margin-top: 10px; background-color: #1ab394; border-color: #1ab394; color: white;">
                Назад
            </el-button>
        </router-link>
        <router-link :to="'/patternTable/show'">
        <el-button @click="updateTable" style="background-color: #1ab394; border-color: #1ab394; color: white;">Сохранить</el-button>
        </router-link>
    </div>

</template>

<script>
    import router from "../../router/router";
    import {AXIOS} from "../../AXIOS/http-common";
    import MyPagination from "../general/pagination.vue";

    export default {
        name: "patternTableUpdatePole",
        data(){
            return{
                primaryKey:"",
                table:[],
                patternTable:"",
                patternTableName:"",
                patternNameFile:"",
                patternTableId:"",
            }
        },
        methods:{
            primaryChange(pole){
                if(pole !== this.primaryKey){
                    document.getElementById(pole).classList.remove("common");
                    document.getElementById(pole).classList.add("primary");
                    if(this.primaryKey !== "") {
                        document.getElementById(this.primaryKey).classList.remove("primary");
                        document.getElementById(this.primaryKey).classList.add("common");
                    }
                    this.primaryKey = pole;
                }
            },

            updateTable(){
                let existingTable = false;
                for(let i = 0; i<this.table.length; i++){
                    let oneTable = this.table[i];
                    let key = [];
                    let type = [];
                    let primary = [];
                    let model = oneTable.tableModel.models;
                    let tableName = oneTable.tableModel.tableName;
                    let fileName = oneTable.tableModel.filename;

                    for(let j = 0; j<model.length; j++){
                        key.push(model[j].key);
                        type.push(model[j].type);
                        if(model[j].key === this.primaryKey){
                            primary.push(true);
                        } else {
                            primary.push(false);
                        }
                        // primary.push(model[j].primary);
                    }

                    let formData = new FormData();

                    formData.append("filename", fileName );
                    formData.append("tableName", tableName );
                    formData.append("names", key );
                    formData.append("types", type );
                    formData.append("primaries", primary );
                    formData.append("patternTableId", this.patternTableId);

                    AXIOS.get("/tableCreator/exist/"+tableName).then(response => {
                        existingTable = response.data;

                        if(existingTable === true) {
                            this.notify("Ошибка","Таблица (" + tableName + ") c таким именем уже существует", "error");
                        } else {
                            AXIOS.post("/tableCreator/update",
                                formData,
                                {
                                    headers: {
                                        'Content-Type': 'multipart/form-data'
                                    }
                                }
                            ).then(response => {
                                console.log(response);
                                // if(response === false) {
                                //     this.notify("Ошибка","Таблица " + tableName + "не была обновлена", "error");
                                // } else {
                                //     this.notify("Успешно","Таблица " + tableName + " была обновлена", "success");
                                // }
                            });
                        }
                    });

                }
            },

            clearForm(){
                this.table = "";
            },

            querySearch(queryString, cb) {
                let links = this.links;
                let results = queryString ? links.filter(this.createFilter(queryString)) : links;
                cb(results);
            },

            createFilter(queryString) {
                return (link) => {
                    return (link.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
                };
            },

            loadAll() {
                return [
                    { "value": "integer" },
                    { "value": "bigint" },
                    { "value": "real" },
                    { "value": "double precision" },
                    { "value": "time" },
                    { "value": "date" },
                    { "value": "timestamp" },
                    { "value": "text" }
                ];
            },

            onChange(file, fileList) {
                let formData = new FormData();
                formData.append("file",file.raw);
                formData.append("patternTableName",this.patternTableName);
                formData.append("patternNameFile",this.patternNameFile);
                AXIOS.post("fileLoader/firstUpload/",
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }).then(response => {
                        console.log(response);
                    this.table = response.data;
                });
            },
        },

        mounted() {
            this.patternTableId = this.$route.params.id;
            let formData = new FormData();
            formData.append("id", this.patternTableId);
            AXIOS.post("tableCreator/getTable/",formData).then(response => {
                this.patternTable = response.data.tableModel;
                this.patternTableName = this.patternTable.tableName;
                this.patternNameFile = this.patternTable.filename;
            });
        }
    }
</script>

<style scoped>
    .common {
        margin-bottom: 10px;
        background-color: #1ab394;
        border-color: #1ab394
    }

    .primary {
        margin-bottom: 10px;
        background-color: #ffcf06;
        border-color: #ffcf06;
    }
</style>