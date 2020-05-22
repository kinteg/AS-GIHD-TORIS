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

                        <div v-for="oneTable in tables.table">
                            <p class="nameTable" @click="viewTable(oneTable)">{{oneTable}}</p>
                            <select @change="changeSelect(oneTable)" v-if="allFiles !== ['-']" class="select" :id="oneTable">
                                <option class="option" v-for="pole in allFiles" :value="pole">{{pole}}</option>
                            </select>
                            <hr style="color: #DCDFE6">
                        </div>
                        <el-button @click="upload" style="background-color: #1ab394; border-color: #1ab394" size="small" type="primary">Загрузить</el-button>
                    </div>
                </el-col>
                <el-col :span="12">
                    <div v-if="allFiles!==''" class="tableBlock">
                        <p style="font-size: 16px">Файлы:</p>
                        <p class="nameTable" v-for="file in allFiles" @click="viewFile(file)">{{file}}</p>
                    </div>
                </el-col>
            </el-row>
        </div>
        <el-dialog width="95%" v-if="tableInfo!== ''" :title="tableInfo.tableModel.tableName" :visible.sync="dialogTableVisible">
            <div class="horizontal-scroll-wrapper  rectangles">
                <table style="overflow-x: auto;">
                    <tr>
                        <th v-for="pole in tableInfo.tableModel.models">{{pole.key}}</th>
                    </tr>
                    <tr v-for="value in tableInfo.values">
                        <td v-for="oneValue in tableInfo.tableModel.models">{{value[oneValue.key]}}</td>
                    </tr>
                </table>
            </div>
        </el-dialog>

        <el-dialog width="95%" v-if="fileInfo!== ''" title="Файл" :visible.sync="dialogFileVisible">
            <div class="horizontal-scroll-wrapper  rectangles">
                <table style="overflow-x: auto;">
                    <tr>
                        <th v-for="pole in fileInfo.tableModel.models">{{pole.key}}</th>
                    </tr>
                    <tr v-for="value in fileInfo.values">
                        <td v-for="oneValue in fileInfo.tableModel.models">{{value[oneValue.key]}}</td>
                    </tr>
                </table>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {AXIOS} from "../../AXIOS/http-common";

    export default {
        name: "uploadData",
        data(){
            return{
                selectedFile:[],
                tableInfo:"",
                fileInfo:"",
                dialogTableVisible: false,
                dialogFileVisible: false,
                isConnect:false,
                hiddenPatternCheck: false,
                labelPosition:'top',
                allFiles: ["-"],
                file:"",
                dataBase:{
                    url:'jdbc:postgresql://localhost:5432/foodloft',
                    userName:'postgres',
                    password:'123',
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
            changeSelect(id){
                // let selInd = document.getElementById(id).options.selectedIndex;
                // console.log(id);
                // console.log(this.allFiles);
                // document.getElementById(id).options.disabled = false;
                // document.getElementById(id).options[selInd].disabled = true;


                // let selInd = document.getElementById(id).options.selectedIndex;
                // let file = document.getElementById(id).options[selInd].value;
                // this.selectedFile.push(file);
                // this.select = file;
                // let indSelect = this.allFiles.indexOf(file);
                // this.allFiles.splice(indSelect, 1);
            },

            viewFile(name){
                this.dialogFileVisible = true;
                let formData = new FormData();
                formData.append("file", this.file);
                formData.append("filename", name);
                AXIOS.post("/fileLoaderAnotherDb/getFileInfo",formData).then(response=>{
                    this.fileInfo = response.data;
                });
            },

            viewTable(name){
                this.dialogTableVisible = true;
                let formData = new FormData();
                formData.append("tableName", name);
                formData.append("URL", this.dataBase.url);
                formData.append("username", this.dataBase.userName);
                formData.append("password", this.dataBase.password);
                AXIOS.post("/fileLoaderAnotherDb/getTableInfo",formData).then(response=>{
                    this.tableInfo = response.data;
                });
            },

            upload(){
                let oneTable = [];
                let files = [];
                let check = 0;
                let validate = true;
                for(let i = 0; i<this.tables.table.length; i++){
                    let selInd = document.getElementById(this.tables.table[i]).options.selectedIndex;
                    let file= document.getElementById(this.tables.table[i]).options[selInd].value;
                    if(file !== "-"){
                        oneTable.push(this.tables.table[i]);
                        files.push(file);
                    }
                }
                for(let i = 0; i< files.length;i++){
                    for(let j = 0; j<files.length; j++){
                        if(files[i] === files[j]){
                            check++;
                        }
                    }
                    if (check>1){
                        validate = false;
                    }
                    check = 0;
                }
                if(validate){
                    console.log(files.indexOf("q.csv"));
                    let formData = new FormData();
                    formData.append("file",this.file);
                    formData.append("URL", this.dataBase.url);
                    formData.append("username", this.dataBase.userName);
                    formData.append("password", this.dataBase.password);
                    formData.append("filenames",files);
                    formData.append("tableNames",oneTable);
                    AXIOS.post("/fileLoaderAnotherDb/executeFile",formData).then(response=>{
                        this.noticeSuccess("Данные успешно загружены");
                    });
                } else {
                    this.noticeError("Один файл не может быть выбран для нескольких таблиц")
                }

            },

            validate(){
                this.hiddenPatternCheck = this.dataBase.url !== "" &&
                    this.dataBase.userName !== "" &&
                    this.dataBase.password !== "" ;
            },

            testConnection(){
                let formData = new FormData();
                formData.append("URL", this.dataBase.url);
                formData.append("username", this.dataBase.userName);
                formData.append("password", this.dataBase.password);
                AXIOS.post("/fileLoaderAnotherDb/isConnection",formData).then(response=>{
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
                            for(let i = 0; i < response.data.length; i++){
                                this.tables.table.push(response.data[i]);
                            }
                            this.isConnect = true;
                        });
                        this.noticeSuccess("Успешно");
                    }else {
                        this.noticeError("Ошибка подключения")
                    }
                });
            },

            sendData(file, fileList){
                this.file = file.raw;
                let formData = new FormData();
                formData.append("file",file.raw);
                AXIOS.post("/fileLoaderAnotherDb/getAllFileNames", formData).then(response=>{
                    this.allFiles = response.data;
                    this.allFiles.push("-");
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

    .nameTable:hover{
        cursor: pointer;
        color: #304156;
    }

    .select{
        margin: 5px 0 10px 0;
        background-color: white;
        border: 1px solid #DCDFE6;
        border-radius: 4px;
        padding: 5px 15px;
    }

    .option{
        padding: 5px 15px;
    }

    .tableBlock{
        padding: 15px;
        margin-top: 15px;
        border-radius: 4px;
        border: 1px solid #DCDFE6;
    }
</style>