<template>
    <div>
        <el-form-item >
            <input type="radio" :id="nameTable + pole.key" :name="nameTable"/>
<!--            <input v-model="nameTable"  :value="pole.key" type="radio" />-->
<!--            <el-button :id="oneTable.tableModel.tableName + pole.key" @click="primaryChange(oneTable.tableModel.tableName, pole.key)" class="common" type="primary" size="mini" icon="el-icon-key"></el-button>-->
        </el-form-item>
        <el-form-item >
            <el-input v-model="pole.key" placeholder="Approved by"></el-input>
        </el-form-item>
        <el-form-item >
            <el-autocomplete
                    style="float: right"
                    class="inline-input"
                    v-model="pole.type"
                    :fetch-suggestions="querySearch"
                    placeholder="Please Input"
            ></el-autocomplete>
        </el-form-item>
    </div>
</template>

<script>
    export default {
        name: "collapseTableForm",
        props:['nameTable','pole'],
        data(){
            return{

            }
        },

        methods:{
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
                    { "value": "integer" },
                    { "value": "bigint" },
                    { "value": "real" },
                    { "value": "double precision" },
                    { "value": "time" },
                    { "value": "date" },
                    { "value": "timestamp" },
                    { "value": "text" }
                ];
            },
        }
    }
</script>

<style scoped>

</style>