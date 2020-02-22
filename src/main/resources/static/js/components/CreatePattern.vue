<template>
    <div>
        <el-form :label-position="labelPosition" label-width="100px" :model="formLabelAlign">
            <el-form-item label="Источник">
                <search-source/>
            </el-form-item>
            <el-form-item label="Описание">
                <el-input id="description" v-model="formLabelAlign.description"></el-input>
            </el-form-item>
            <el-form-item label="Направление">
                <el-input id="direction" v-model="formLabelAlign.direction"></el-input>
            </el-form-item>
            <el-form-item label="Ответственный за ведение">
                <el-input id="management" v-model="formLabelAlign.management"></el-input>
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
            return {
                labelPosition: 'left',
                input: {},
                file: '',
                data: '',
                source:'',
                formLabelAlign: {
                    description: '',
                    direction: '',
                    management: ''
                }
            };
        },
        methods: {

            submitFile(){
                let formData = new FormData();

                formData.append('file', this.file);
                this.postData('/single-file',formData);
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
                let e = document.getElementById("select"+keys+tables);
                let inputSelectValue = e.options[e.selectedIndex].value;
                if(document.getElementById("primary"+keys+tables).checked){
                    primaryKey = true;
                }
                JsonStr = JsonStr.concat('{"name":"' + inputTextValue + '","type":"' + inputSelectValue + '","primary":' + primaryKey +'}');
                return JsonStr;
            },

            submit(){
                let tables = [];
                let allKeys = [];
                let JsonStr;
                this.description = document.getElementById("description").value;
                this.direction = document.getElementById("direction").value;
                this.management = document.getElementById("management").value;
                this.source = document.getElementById("sourceList").value;

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

                        if(j !== keys.length - 1)
                            JsonStr = JsonStr.concat(',');
                    }
                    JsonStr = JsonStr.concat(']}}');

                    console.log(JsonStr);
                    let formData = new FormData();

                    formData.append('file', this.file);
                    formData.append('json', JsonStr);
                    formData.append('description', this.description);
                    formData.append('direction', this.direction);
                    formData.append('management', this.management);
                    formData.append('source', this.source);
                    // this.postData('',formData);
                    JsonStr = '';
                }
            }
        }
    }

</script>

<style scoped>

</style>