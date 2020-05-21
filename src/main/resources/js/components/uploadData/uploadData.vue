<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Загрузка данных в другую систему</p>
        <el-form
                style="width: 50%"
                :label-position="labelPosition"
                label-width="100px" :model="dataBase"
                :rules="rules"
                ref="ruleForm">
            <el-form-item label="Ссылка на базу данных">
                <el-input @input="validate" v-model="dataBase.url"></el-input>
            </el-form-item>
            <el-form-item  label="Имя пользователя">
                <el-input @input="validate" v-model="dataBase.userName"></el-input>
            </el-form-item>
            <el-form-item label="Пароль">
                <el-input @input="validate" v-model="dataBase.password"></el-input>
            </el-form-item>
            <el-button v-if="hiddenPatternCheck" @click="connectToDataBase" style="background-color: #1ab394; border-color: #1ab394; color: white;">Подключиться к базе данных</el-button>
            <el-button v-if="hiddenPatternCheck" @click="testConnection" style="background-color: #1ab394; border-color: #1ab394; color: white;">Проверить подключение</el-button>
        </el-form>

        <div v-if="isConnect">
            <p>Вы успешно подключились к базе данных:</p>
            <el-upload
                    class="upload-demo"
                    ref="upload"
                    action=""
                    :limit="1"
                    :on-change="sendData"
                    :auto-upload="false">
                <el-button slot="trigger" style="background-color: #1ab394; border-color: #1ab394" size="small" type="primary">Загрузить данные в базу данных</el-button>
            </el-upload>
            <el-row :gutter="20">
                <el-col :span="12">
                    <div class="tableBlock">
                        <p style="font-size: 16px">Таблицы:</p>
                        <el-form  :inline="true" class="demo-form-inline">
                            <el-form-item v-for="oneTable in tables.table">
                                {{oneTable}}
                            </el-form-item>
                            <el-form-item  v-for="oneTable in select.selectFile" style="float: right">
                                <el-select  v-model="file" placeholder="Select" value="">
                                    <el-option
                                            v-for="file in allFiles"
                                            :key="file"
                                            :label="file"
                                            :value="file">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-form>
                    </div>
                </el-col>
                <el-col :span="12">
                    <div v-if="allFiles!==''" class="tableBlock">
                        <p style="font-size: 16px">Файлы:</p>
                        <p v-for="file in allFiles">{{file}}</p>
                    </div>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
    import {AXIOS} from "../../AXIOS/http-common";

    export default {
        name: "uploadData",
        data(){
            return{
                isConnect:false,
                hiddenPatternCheck: false,
                labelPosition:'top',
                allFiles: "",
                dataBase:{
                    url:'jdbc:postgresql://localhost:5432/foodloft',
                    userName:'postgres',
                    password:'123',
                },
                file:"",
                select:{
                    selectFile:[],
                },
                tables:{
                    table: [],
                },
                rules: {
                    url: [
                        { required: true, message: 'Введите ссылку на базу данных', trigger: 'blur' },
                    ],
                    userName: [
                        { required: true, message: 'Введите имя пользователя', trigger: 'blur' },
                    ],
                    password: [
                        { required: true, message: 'Введите пароль', trigger: 'blur' },
                    ],
                }

            }
        },
        methods: {
            validate(){
                this.hiddenPatternCheck = this.dataBase.url !== "" &&
                    this.dataBase.userName !== "" &&
                    this.dataBase.password !== "" ;
            },

            crutch(pole){

            },

            testConnection(){
                let formData = new FormData();
                formData.append("URL", this.dataBase.url);
                formData.append("username", this.dataBase.userName);
                formData.append("password", this.dataBase.password);
                AXIOS.post("/fileLoaderAnotherDb/isConnection",formData).then(response=>{
                    console.log(response);
                    if(response.data === true){
                        this.noticeSuccess("Успешно");
                    }else {
                        this.noticeError("Ошибка подключения")
                    }
                });
            },

            connectToDataBase(){
                let formData = new FormData();
                formData.append("URL", this.dataBase.url);
                formData.append("username", this.dataBase.userName);
                formData.append("password", this.dataBase.password);
                AXIOS.post("/fileLoaderAnotherDb/isConnection",formData).then(response=>{
                    if(response.data === true){
                        AXIOS.post("/fileLoaderAnotherDb/getAllTableNames",formData).then(response=>{
                            console.log(response);
                            for(let i = 0; i < response.data.length; i++){
                                this.tables.table.push(response.data[i]);
                                this.select.selectFile.push(i);
                            }

                            console.log(this.tables);
                            console.log(this.select);
                            this.isConnect = true;
                        });
                        this.noticeSuccess("Успешно");
                    }else {
                        this.noticeError("Ошибка подключения")
                    }
                });
            },

            sendData(file, fileList){
                let formData = new FormData();
                formData.append("file",file.raw);
                AXIOS.post("/fileLoaderAnotherDb/getAllFileNames", formData).then(response=>{
                    this.allFiles = response.data;
                    console.log(this.allFiles);
                })
            },

            noticeError(message) {
                this.$notify({
                    title: 'Ошибка',
                    message:message ,
                    type: 'error',
                });
            },

            noticeSuccess(message) {
                this.$notify({
                    title: 'Успешно',
                    dangerouslyUseHTMLString: true,
                    message: message,
                    type: 'success',
                });
            },
        }
    }
</script>

<style scoped>
    .tableBlock{
        padding: 15px;
        margin-top: 15px;
        border-radius: 4px;
        border: 1px solid #DCDFE6;
    }
</style>