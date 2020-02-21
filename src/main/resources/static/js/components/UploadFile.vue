<template>
    <div>
        <label>Выберите файл для загрузки
            <input type="file" id="file" ref="file" v-on:change="handleFileUpload()"/>
        </label>
        <button v-on:click="submitFile()">Submit</button>
        <br>
        <br>
        <el-collapse v-model="activeNames" @change="handleChange">
            <div v-for="currency in data" class="currency">
                <el-collapse-item :title=currency.nameTable >
                    Название таблицы<input  :id="currency.nameTable" class="inputText" type="text" :value="currency.nameTable">
                    <br><br>Название поля <span style=" float: right">Тип поля</span>
                    <el-form :inline="true" :model="formInline" class="demo-form-inline">
                        <div v-for="key in getKeys(currency.table[0])">
                            <el-form-item   style="margin-right: 200px;">
                                <input  :id="key+currency.nameTable" class="inputText" type="text" :value="key">
                            </el-form-item>
                            <el-form-item>
                                <select :id="'select'+key+currency.nameTable" class="inputSelect">
                                    <option value="integer">integer</option>
                                    <option value="text">text</option>
                                    <option value="integer">fghijok3</option>
                                    <option value="integer">test4</option>
                                    <option value="integer">tes5t</option>
                                </select>
                            </el-form-item>
                        </div>
                    </el-form>
                    <table >
                        <tr v-for="pole in currency.table">
                            <td  style="border-bottom: 1px solid #dcdfe6; padding: 10px" v-for="value in pole">
                                {{value}}
                            </td>
                        </tr>
                    </table>
                </el-collapse-item>

            </div>
        </el-collapse>
        <el-button type="primary" @click="submit">Загрузить</el-button>

    </div>
</template>

<script>
    import {AXIOS} from "../AXIOS/http-common";

    export default {
        name: "UploadFile",

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

            submitFile(){
                let formData = new FormData();

                formData.append('file', this.file);
                this.postData('/single-file',formData);
                // AXIOS.post( '/single-file',
                //     formData,
                //     {
                //         headers: {
                //             'Content-Type': 'multipart/form-data'
                //         }
                //     }
                // ).then(response=>{
                //     this.data = response.data.content;
                // }).catch(error=>{
                //     console.log("ERROR"+error);
                // })

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
                let inputTextValue = document.getElementById(keys+tables).value;
                let e = document.getElementById("select"+keys+tables);
                let inputSelectValue = e.options[e.selectedIndex].value;
                JsonStr = JsonStr.concat('{"name":"' + inputTextValue + '","type":"' + inputSelectValue + '"}');
                return JsonStr;
            },

            submit(){
                let tables = [];
                let allKeys = [];
                let JsonStr;
                for(let i = 0; i<this.data.length; i++) {

                    tables.push(this.data[i].nameTable);
                    allKeys.push(this.getKeys(this.data[i].table[0]));

                }

                for(let i = 0; i< tables.length; i++) {
                    let keys = allKeys[i];
                    let nameTable = document.getElementById(tables[i]).value;
                    JsonStr = '{"content":{"nameFile":"' + tables[i] + '","nameTable":"' + nameTable + '","columnTable":[';
                    for(let j = 0; j< keys.length; j++) {
                        // let inputTextValue = document.getElementById(keys[j]+tables[i]).value;
                        //
                        // let e = document.getElementById("select"+keys[j]+tables[i]);
                        // let inputSelectValue = e.options[e.selectedIndex].value;
                        // JsonStr = JsonStr.concat('{"name":"' + inputTextValue + '","type":"' + inputSelectValue + '"}');
                        JsonStr = this.getElement(keys[j],tables[i],JsonStr);

                        if(j !== keys.length - 1)
                            JsonStr = JsonStr.concat(',');
                    }
                    JsonStr = JsonStr.concat(']}}');

                    console.log(JsonStr);
                    let formData = new FormData();

                    formData.append('file', this.file);
                    formData.append('json',JsonStr);
                    this.postData('/sendData',formData);
                    // AXIOS.post( '/sendData',
                    //     formData,
                    //     {
                    //         headers: {
                    //             'Content-Type': 'multipart/form-data'
                    //         }
                    //     }
                    // ).then(response=>{
                    //     this.data = response.data.content;
                    // }).catch(error=>{
                    //     console.log("ERROR"+error);
                    // });
                    JsonStr = '';
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
    .inputText{
        color: #606266;
        height: 32px;
        border-radius: 4px;
        border: 1px solid #dcdfe6;
        width: 100%;
        padding: 0 15px;
        margin: 8px 0;
        box-sizing: border-box;
    }

    .inputSelect{
        background-color: white;
        border: 1px solid #dcdfe6;
        border-radius: 4px;
        height: 32px;
        color: #606266;
        width: 100%;
        padding: 0 15px;
        margin: 8px 0;
    }
</style>