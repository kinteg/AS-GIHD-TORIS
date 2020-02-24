<template>
    <div>
        <search-source/>
        <label>Выберите файл для загрузки
            <input type="file" id="file" ref="file" v-on:change="handleFileUpload()"/>
        </label>
        <button v-on:click="submitFile()">Загрузить файлы</button>
        <br>
        <br>
        <collapse-show :data="data" />
        <el-button type="primary" @click="submit">Загрузить</el-button>
    </div>
</template>

<script>
    import {AXIOS} from "../AXIOS/http-common";
    import CollapseShow from "./CollapseShow.vue";
    import SearchSource from "./SearchSource.vue";

    export default {
        name: "UploadFile",
        components: {SearchSource, CollapseShow},
        props:['controller'],
        data(){
            return {
                input: {},
                file: '',
                data: '',
                source:'',
                sourceId:'',
            }
        },
        methods: {
            submitFile(){
                this.getSourceId();
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

            getSourceId(){
                let sourceName = document.getElementById('sourceList').value;
                AXIOS.get('/source/'+ sourceName,
                ).then(response=>{
                    let formData = new FormData();
                    formData.append('sourceId', response.data.id);
                    formData.append('file', this.file);
                    this.postData('/single-file',formData);
                }).catch(error=>{
                    console.log("ERROR"+error);
                });
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

            submit(){
                // let symbol = new RegExp( "[~!@#$%^&*()\\-+=|\/';:,.]");
                // let str = symbol.exec("asdasdlkas-fjdasdasdasd");
                let tables = [];
                let allKeys = [];
                let files = [];
                let JsonStr;

                for(let i = 0; i<this.data.length; i++) {
                    // console.log(this.data[i].nameFile);
                    tables.push(this.data[i].nameTable);
                    files.push(this.data[i].nameFile);
                    allKeys.push(this.getKeys(this.data[i].table[0]));

                }
                for(let i = 0; i< tables.length; i++) {
                    let keys = allKeys[i];
                    let nameTable = document.getElementById(tables[i]).value;
                    JsonStr = '{"content":{"nameFile":"' + files[i] + '","nameTable":"' + nameTable + '","columnTable":[';
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
                    this.postData(this.controller,formData);
                    JsonStr = '';
                }
            }
        }
    }

</script>

<style scoped>
</style>