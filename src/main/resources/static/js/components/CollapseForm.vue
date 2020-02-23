<template>
    <div >
        <el-form :inline="true" class="demo-form-inline">
            <div v-for="key in currencyTable.columnTable">
                <table>
                    <tr>
                        <td>
                            <el-form-item style="padding-top: 10px">
                                <input checked="true" :id="'primary'+key.name+currencyTable.nameTable" type="radio" :name="currencyTable.nameTable" :value="key.name+currencyTable.nameTable" />
                            </el-form-item></td>
                        <td>
                            <el-form-item   style="margin-right: 200px;">
                                <el-input :id="key.name+currencyTable.nameTable" :value="key.name" v-model="key.name" type="text" autocomplete="off"></el-input>
<!--                                <input  :id="key+currencyTable.nameTable" class="inputText" type="text" :value="key">-->
                            </el-form-item></td>
                        <td>
                            <el-form-item>
                                <select :id="'select'+key.name+currencyTable.nameTable" class="inputSelect">
                                    <option value="integer">integer</option>
                                    <option value="text">text</option>
                                    <option value="integer">fghijok3</option>
                                    <option value="integer">test4</option>
                                    <option value="integer">tes5t</option>
                                </select>
                            </el-form-item></td>
                    </tr>
                </table>
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