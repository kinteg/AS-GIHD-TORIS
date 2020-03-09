<template>
    <div >
        <el-form :inline="true" class="demo-form-inline">
            <div v-for="key in currencyTable.columnTable">
                <el-form-item style="padding-top: 10px">
                    <label>
                        <input :disabled="disabled" :checked="key.primary" :id="'primary'+key.name+currencyTable.nameTable" type="radio" :name="currencyTable.nameTable" :value="key.name+currencyTable.nameTable" />
                    </label>
                </el-form-item>
                <el-form-item style="margin-right: 200px;">
                    <el-input :disabled="disabled" :id="key.name+currencyTable.nameTable" v-model="key.name" type="text"
                              autocomplete="off"/>
<!--                    <input type="text"  class="inputText" :id="key.name+currencyTable.nameTable" :value="key.name"/>-->
                </el-form-item>
                <el-form-item>
                    <el-autocomplete :disabled="disabled"
                                     :id="'select'+key.name+currencyTable.nameTable"
                                     class="inline-input"
                                     v-model="key.type"
                                     :fetch-suggestions="querySearch"
                                     placeholder="Please Input"
                                     @select="handleSelect"
                    />
                </el-form-item>
            </div>
            <el-collapse>
                <el-collapse-item title="Данные">
                    <table>
                        <tr v-for="pole in currencyTable.table">
                            <td  style="border-bottom: 1px solid #dcdfe6; padding: 10px" v-for="value in pole">
                                {{value}}
                            </td>
                        </tr>
                    </table>
                </el-collapse-item>
            </el-collapse>
        </el-form>
    </div>
</template>

<script>
    export default {
        name: "CollapseForm",
        props: ['currencyTable','disabled'],
        data(){
            return {
                num:0
            }
        },
        methods:{
            // getKeys(table){
            //     let keys =[];
            //     for(let key in table)
            //     {
            //         keys.push(key);
            //     }
            //`
            //     return keys;
            // },`
            querySearch(queryString, cb) {
                let links = this.links;
                let results = queryString ? links.filter(this.createFilter(queryString)) : links;
                cb(results);
            },
            createFilter(queryString) {
                return (link) => {
                    return (link.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
                };
            },
            loadAll() {
                return [
                    { "value": "bigint"},
                    { "value": "boolean"},
                    { "value": "date"},
                    { "value": "integer"},
                    { "value": "text"},
                    { "value": "timestamp"},
                ];
            },
            handleSelect(item) {
                console.log(item);
            }
        },
        mounted() {
            this.links = this.loadAll();
        }
    }
</script>

<style scoped>
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

</style>