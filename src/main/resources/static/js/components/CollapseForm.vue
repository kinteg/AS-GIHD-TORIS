<template>
    <div >
        <el-form :inline="true" class="demo-form-inline">
            <div v-for="key in currencyTable.columnTable">
                <el-form-item style="padding-top: 10px">
                    <input checked="true" :id="'primary'+key.name+currencyTable.nameTable" type="radio" :name="currencyTable.nameTable" :value="key.name+currencyTable.nameTable" />
                </el-form-item>
                <el-form-item style="margin-right: 200px;">
                    <el-input :id="key.name+currencyTable.nameTable" :value="key.name" v-model="key.name" type="text" autocomplete="off"></el-input>
                    <!--<input :id="key+currencyTable.nameTable" class="inputText" type="text" :value="key">-->
                </el-form-item>
                <el-form-item>
                    <el-autocomplete
                            :id="'select'+key.name+currencyTable.nameTable"
                            :value="key.type"
                            class="inline-input"
                            v-model="key.type"
                            :fetch-suggestions="querySearch"
                            placeholder="Please Input"
                            @select="handleSelect"
                    ></el-autocomplete>
<!--                    <select :id="'select'+key.name+currencyTable.nameTable" class="inputSelect">-->
<!--                        <option value="integer">integer</option>-->
<!--                        <option value="text">text</option>-->
<!--                        <option value="integer">fghijok3</option>-->
<!--                        <option value="integer">test4</option>-->
<!--                        <option value="integer">tes5t</option>-->
<!--                    </select>-->
                </el-form-item>
            </div>
            <table>
                <tr v-for="pole in currencyTable.table">
                    <td  style="border-bottom: 1px solid #dcdfe6; padding: 10px" v-for="value in pole">
                        {{value}}
                    </td>
                </tr>
            </table>
        </el-form>
    </div>
</template>

<script>
    export default {
        name: "CollapseForm",
        props: ['currencyTable'],
        data(){
            return {}
        },
        methods:{

            getKeys(table){
                let keys =[];
                for(let k in table)
                {
                    keys.push(k);
                }

                return keys;
            },
            querySearch(queryString, cb) {
                var links = this.links;
                var results = queryString ? links.filter(this.createFilter(queryString)) : links;
                // call callback function to return suggestions
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