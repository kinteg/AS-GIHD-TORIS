<template>
    <div>
        <label>File
            <input type="file" id="file" ref="file" v-on:change="handleFileUpload()"/>
        </label>
        <button v-on:click="submitFile()">Submit</button>
        <br>
        <br>
        <el-collapse v-model="activeNames" @change="handleChange">
            <div v-for="currency in data" class="currency">
                <el-collapse-item :title=currency.nameTable >
                    Название поля <span style=" float: right">Тип поля</span>
                    <el-form :inline="true" :model="formInline" class="demo-form-inline">
                        <div v-for="key in getKeys(currency.table[0])">

                            <el-form-item   style="margin-right: 200px;">
                                <input  :id="key" style="
                            color: #606266;
                            height: 32px;
                            border-radius: 4px;
                            border: 1px solid #dcdfe6;
                            width: 100%;
                            padding: 0 15px;
                            margin: 8px 0;
                            box-sizing: border-box;" type="text" :value="key">
                            </el-form-item>
                            <el-form-item >
                                <select style="
                                background-color: white;
                                border: 1px solid #dcdfe6;
                                border-radius: 4px;
                                height: 32px;
                                color: #606266;
                                width: 100%;
                                padding: 0 15px;
                                margin: 8px 0;
                                                ">
                                    <option class="select" :value="key">test</option>
                                    <option :value="key">test</option>
                                    <option :value="key">fghijok</option>
                                    <option :value="key">test</option>
                                    <option :value="key">test</option>
                                </select>
                            </el-form-item>
                        </div>
                    </el-form>
                    <el-button type="primary" @click="submit(currency.table[0])">Query</el-button>
                </el-collapse-item>
            </div>
        </el-collapse>
        <div
                v-for="currency in data"
                class="currency"
        >

        </div>
    </div>
</template>

<script>
    import {AXIOS} from "../AXIOS/http-common";
    export default {
        name: "UploadFile",
        /*
          Defines the data used by the component
        */
        data(){
            return {
                input: {},
                file: '',
                data: '',
            }
        },
        methods: {
            handleChange(val) {
                console.log(val);
            },
            /*
              Submits the file to the server
            */
            submitFile(){
                /*
                        Initialize the form data
                    */
                let formData = new FormData();
                /*
                    Add the form data we need to submit
                */
                formData.append('file', this.file);
                /*
                  Make the request to the POST /single-file URL
                */
                AXIOS.post( '/single-file',
                    formData,
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
            /*
              Handles a change on the file upload
            */
            handleFileUpload(){
                this.file = this.$refs.file.files[0];
            },

            getKeys(table){
                let keys =[];
                for(let k in table)
                {
                    keys.push(k);
                    //console.log(k);
                }

                return keys;
            },
            submit(keys){
                for(let key in keys)
                {
                    let val = document.getElementById(key).value;
                    console.log(val);
                }

            }
        }
    }
</script>

<style scoped>
    .el-header {
        margin: auto;
        display: block;
        padding: 10px;
    }
    .el-menu-item {
        text-decoration: none;
    }
</style>