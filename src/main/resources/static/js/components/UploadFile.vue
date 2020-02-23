<template>
    <div>
        Выберите источник <search-source/>
        <br>
        <br>
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
                source:''
            }
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

            getSourceId(){
                let sourceName = document.getElementById('sourceList').value;
                // console.log(sourceName);
                AXIOS.get('/source/'+ sourceName,
                ).then(response=>{
                    this.source = response.data;
                    return this.source.id;
                }).catch(error=>{
                    console.log("ERROR"+error);
                });
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
                let sourceId = this.getSourceId();
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
                    // this.postData(this.controller,formData);
                    JsonStr = '';
                }
            }
        }
    }

</script>

<style scoped>
</style>