<template>
    <div>
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" :label-position="labelPosition"
                 label-width="100px">
            <search-source/>
            <el-form-item prop="name" label="Название">
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

        <input class="custom-file-input" type="file" id="file" ref="file" v-on:change="handleFileUpload()"/>
        <el-button type="primary"  @click="submitFile()">Загрузить</el-button><br>
        <br>
        <br>
        <collapse-show :data="data"/>
        <div v-if="ruleForm.name !=='' && ruleForm.description !=='' && ruleForm.direction !=='' && ruleForm.management !==''" >
        <el-button type="primary" @click="submit">Создать шаблон</el-button></div>
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
            let symbol = new RegExp("[~!@#$%^&*()\\-+=|\/';:,.]");
            let validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('Заполните поле'));
                } else {
                    if (symbol.exec(value) !== null) {
                        callback(new Error('Недопустимые символы: ~!@#$%^&*()-+=|  / \';:,.'));
                    } else
                        callback();
                }
            };

            return {
                ruleForm: {
                    text: '',
                    name: '',
                    description: '',
                    direction: '',
                    management: ''
                },
                rules: {
                    name: [
                        {validator: validatePass, trigger: 'blur'}
                    ], description: [
                        {validator: validatePass, trigger: 'blur'}
                    ], direction: [
                        {validator: validatePass, trigger: 'blur'}
                    ], management: [
                        {validator: validatePass, trigger: 'blur'}
                    ],
                },
                labelPosition: 'left',
                input: {},
                file: '',
                data: '',
                source: '',
                sourceId: '',
                arrJson: [],
                formLabelAlign: {
                    description: '',
                    direction: '',
                    management: ''
                }
            };
        },
        methods: {

            submitFile() {
                this.getSourceId();
            },

            getSourceId() {
                let sourceName = document.getElementById('sourceList').value;
                if (sourceName === '') {
                    console.log("error");
                }
                AXIOS.get('/source/' + sourceName,
                ).then(response => {
                    this.sourceId = response.data.id;
                    let formData = new FormData();
                    formData.append('sourceId', response.data.id);
                    formData.append('file', this.file);
                    this.postData('/single-file', formData);
                }).catch(error => {
                    console.log("ERROR" + error);
                });
            },
            handleFileUpload() {
                this.file = this.$refs.file.files[0];
            },

            postData(controller, data) {
                AXIOS.post(controller,
                    data,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }
                ).then(response => {
                    this.data = response.data.content;
                }).catch(error => {
                    console.log("ERROR" + error);
                })
            },

            getElement(keys, tables, JsonStr) {
                let primaryKey = false;
                let inputTextValue = document.getElementById(keys + tables).value;
                let inputTypeValue = document.getElementById("select" + keys + tables).value;
                if (document.getElementById("primary" + keys + tables).checked) {
                    primaryKey = true;
                }
                JsonStr = JsonStr.concat('{"name":"' + inputTextValue + '","type":"' + inputTypeValue + '","primary":' + primaryKey + '}');
                return JsonStr;
            },

            getKeys(table) {
                let keys = [];
                for (let i = 0; i<table.length; i++) {
                    // console.log(table[i].name);
                    keys.push(table[i].name);
                }
                return keys;
            },
            submit() {
                let tables = [];
                let allKeys = [];
                let JsonStr;
                let files = [];
                for (let i = 0; i < this.data.length; i++) {

                    tables.push(this.data[i].nameTable);
                    // console.log(this.data[i].columnTable);
                    // allKeys.push(this.getKeys(this.data[i].table[0]));
                    allKeys.push(this.getKeys(this.data[i].columnTable));
                    files.push(this.data[i].nameFile);
                }


                for (let i = 0; i < tables.length; i++) {
                    let keys = allKeys[i];
                    let nameTable = document.getElementById(tables[i]).value;
                    JsonStr = '{"content":{"nameFile":"' + files[i] + '","nameTable":"' + nameTable + '","columnTable":[';
                    for (let j = 0; j < keys.length; j++) {
                        JsonStr = this.getElement(keys[j], tables[i], JsonStr);

                        if (j !== keys.length - 1)
                            JsonStr = JsonStr.concat(',');
                    }
                    JsonStr = JsonStr.concat(']}}');
                    console.log(JsonStr);
                    this.arrJson.push(JsonStr);
                    JsonStr = '';
                }

                let formData = new FormData();
                formData.append('file', this.file);
                formData.append('json', this.arrJson);
                formData.append('name', this.ruleForm.name);
                formData.append('description', this.ruleForm.description);
                formData.append('direction', this.ruleForm.direction);
                formData.append('management', this.ruleForm.management);
                formData.append('sourceId', this.sourceId);
                this.postData('pattern/create', formData);
            }
        }
    }

</script>

<style scoped>
    .custom-file-input::-webkit-file-upload-button {
        visibility: hidden;
    }
    .custom-file-input{
        text-align: right;
    }

    .custom-file-input::before {
        content: 'Выберите Файл';
        background:#409EFF;
        border: 1px solid #409EFF;
        border-radius: 3px;
        padding: 9px ;
        cursor: pointer;
        color: white;
        -webkit-appearance: button;
    }
    .custom-file-input:hover::before {
        background-color: #66b1ff;
    }
    .custom-file-input:active::before {
        background: -webkit-linear-gradient(top, #66b1ff, #66b1ff);
    }
</style>