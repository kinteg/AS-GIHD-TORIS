<template>
    <div>
        <el-form :label-position="labelPosition"  label-width="100px">
            <search-source />

            <input class="custom-file-input" type="file" id="file" ref="file" v-on:change="handleFileUpload()"/>
            <el-button type="primary"  @click="submitFile()">Загрузить</el-button><br>
            <br>
            <collapse-show :disabled="true" :data="data" />
            <el-button  type="primary"  @click="submit">Загрузить</el-button>
        </el-form>

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
                text:'',
                input: {},
                file: '',
                data: '',
                source:'',
                sourceId:'',
                labelPosition: 'left',
            }
        },
        updated() {
           this.text = document.getElementById('sourceList').value;
           console.log('ll');
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
                    this.sourceId = response.data.id;
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
                if(document.getElementById('sourceList').value!==''){
                    let tables = [];
                    let allKeys = [];
                    let files = [];
                    let nameFiles = [];
                    let nameTables = [];


                    for(let i = 0; i<this.data.length; i++) {
                        tables.push(this.data[i].nameTable);
                        files.push(this.data[i].nameFile);
                        allKeys.push(this.getKeys(this.data[i].table[0]));

                    }
                    for(let i = 0; i< tables.length; i++) {
                        // let keys = allKeys[i];
                        let nameTable = document.getElementById(tables[i]).value;
                        nameTables.push(nameTable);
                        nameFiles.push(files[i]);
                        // JsonStr = '{"content":{"nameFile":"' + files[i] + '","nameTable":"' + nameTable + '","columnTable":[';
                        // for(let j = 0; j< keys.length; j++) {
                        //     JsonStr = this.getElement(keys[j],tables[i],JsonStr);
                        //
                        //     if(j !== keys.length - 1)
                        //         JsonStr = JsonStr.concat(',');
                        // }
                        // JsonStr = JsonStr.concat(']}}');
                    }
                        let formData = new FormData();
                        formData.append('file', this.file);
                        formData.append('nameTable', nameTables);
                        formData.append('nameFile', nameFiles);
                        formData.append('id', this.sourceId);
                        this.postData(this.controller,formData);
                        // JsonStr = '';
                } else{
                    alert('Выберите источник');
                }
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