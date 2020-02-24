<template>
    <div>
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm"  :label-position="labelPosition" label-width="100px">
                <search-source/>
            <el-form-item prop="description" label="Название">
                <el-input id="name" v-model="ruleForm.name"></el-input>
            </el-form-item>
            <el-form-item prop="description" label="Описание">
                <el-input id="description" v-model="ruleForm.description"></el-input>
            </el-form-item>
            <el-form-item prop="direction" label="Направление">
                <el-input id="direction" v-model="ruleForm.direction"></el-input>
            </el-form-item>
            <el-form-item prop="management" label="Ответственный за ведение">
                <el-input id="management" v-model="ruleForm.management"></el-input>
            </el-form-item>
        </el-form>

        <label>Выберите файл для загрузки
            <input type="file" id="file" ref="file" v-on:change="handleFileUpload()"/>
        </label>
        <button v-on:click="submitFile()">Загрузить файлы</button>
        <br>
        <br>
        <collapse-show :data="data"/>
        <el-button type="primary" @click="submit">Создать шаблон</el-button>
    </div>
</template>

<script>
    import UploadFile from "./UploadFile.vue";
    import CollapseShow from "./CollapseShow.vue";
    import SearchSource from "./SearchSource.vue";
    import {AXIOS} from "../AXIOS/http-common";

    export default {
        name: "CreatePattern",
        components: {SearchSource, CollapseShow, UploadFile},
        data() {
            let symbol = new RegExp( "[~!@#$%^&*()\\-+=|\/';:,.]");
            let validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('Заполните поле'));
                } else {
                    if(symbol.exec(value)!==null){
                        callback(new Error('Недопустимые символы: ~!@#$%^&*()-+=|  / \';:,.'));
                    }else
                        callback();
                }
            };

            return {
                ruleForm: {
                    text: '',
                    name:'',
                    description:'',
                    direction: '',
                    management: ''
                },
                rules: {
                    text: [
                        { validator: validatePass, trigger: 'blur' }
                    ], description: [
                        { validator: validatePass, trigger: 'blur' }
                    ], direction: [
                        { validator: validatePass, trigger: 'blur' }
                    ], management: [
                        { validator: validatePass, trigger: 'blur' }
                    ],
                },
                labelPosition: 'left',
                input: {},
                file: '',
                data: '',
                source:'',
                sourceId:'',
                formLabelAlign: {
                    description: '',
                    direction: '',
                    management: ''
                }
            };
        },
        methods: {

            submitFile(){
                this.getSourceId();
            },

            getSourceId(){
                let sourceName = document.getElementById('sourceList').value;
                if(sourceName === ''){
                    console.log("error");
                }
                AXIOS.get('/source/'+ sourceName,
                ).then(response=>{
                    this.sourceId = response.data.id;
                    let formData = new FormData();
                    formData.append('sourceId', response.data.id);
                    formData.append('file', this.file);
                    this.postData('/single-file',formData);
                }).catch(error=>{
                    console.log("ERROR"+error);
                });
            },
            handleFileUpload(){
                this.file = this.$refs.file.files[0];
            },

            getKeys(table){
                let keys =[];
                for(let k in table)
                {
                    keys.push(k);
                }
                return keys;
            },

            postData(controller, data) {
                AXIOS.post(controller,
                    data,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }
                ).then(response=>{
                    this.data = response.data.content;
                }).catch(error=>{
                    console.log("ERROR"+error);
                })
            },

            getElement(keys,tables,JsonStr){
                let primaryKey = false;
                let inputTextValue = document.getElementById(keys+tables).value;
                let inputTypeValue = document.getElementById("select"+keys+tables).value;
                if(document.getElementById("primary"+keys+tables).checked){
                    primaryKey = true;
                }
                JsonStr = JsonStr.concat('{"name":"' + inputTextValue + '","type":"' + inputTypeValue + '","primary":' + primaryKey +'}');
                return JsonStr;
            },

            getAllPole(){
                this.name = document.getElementById("name").value;
                this.description = document.getElementById("description").value;
                this.direction = document.getElementById("direction").value;
                this.management = document.getElementById("management").value;
                this.source = document.getElementById("sourceList").value;
                this.source = document.getElementById("sourceList").value;
            },

            submit(){
                let tables = [];
                let allKeys = [];
                let JsonStr;
                let arrJson =[];
                let formData = new FormData();
                this.getAllPole();
                console.log(this.sourceId);
                for(let i = 0; i<this.data.length; i++) {

                    tables.push(this.data[i].nameTable);
                    allKeys.push(this.getKeys(this.data[i].table[0]));

                }

                for(let i = 0; i< tables.length; i++) {

                    let keys = allKeys[i];
                    let nameTable = document.getElementById(tables[i]).value;
                    JsonStr = '{"content":{"nameFile":"' + tables[i] + '","nameTable":"' + nameTable + '","columnTable":[';

                    for(let j = 0; j< keys.length; j++) {

                        JsonStr = this.getElement(keys[j],tables[i],JsonStr);
                        if(j !== keys.length - 1) {JsonStr = JsonStr.concat(',');}
                    }

                    JsonStr = JsonStr.concat(']}}');
                    arrJson.push(JsonStr);
                    JsonStr = '';
                }
                    console.log(arrJson);
                    formData.append('file', this.file);
                    formData.append('json', arrJson);
                    formData.append('name', this.name);
                    formData.append('description', this.description);
                    formData.append('direction', this.direction);
                    formData.append('management', this.management);
                    formData.append('sourceId', this.sourceId);
                    this.postData('pattern/create',formData);
            }
        }
    }

</script>

<style scoped>

</style>