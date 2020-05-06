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
            <el-button v-if="hiddenPatternCheck" @click="checkData" style="background-color: #1ab394; border-color: #1ab394; color: white;">Подключиться к базе данных</el-button>
        </el-form>

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

    </div>
</template>

<script>
    import {AXIOS} from "../../AXIOS/http-common";

    export default {
        name: "uploadData",
        data(){
            return{
                hiddenPatternCheck: false,
                labelPosition:'top',
                dataBase:{
                    url:'',
                    userName:'',
                    password:'',
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

            checkData(){
                AXIOS.get("/connectToDataBase").then(response=>{
                    if(response.data.status === "OK"){

                    }
                });
            },

            sendData(file, fileList){
                let formData = new FormData();
                formData.append("file",file.raw);
                AXIOS.get("/uploadFiles").then(response=>{

                })
            }
        }
    }
</script>

<style scoped>

</style>