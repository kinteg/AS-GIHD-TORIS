<template>
    <div>
        <el-collapse-item :title=currency.nameTable >
            Название поля <span style=" float: right">Тип поля</span>
            <el-form :inline="true" :model="formInline" class="demo-form-inline">
                <div v-for="key in getKeys(currency.table[0])">

                    <el-form-item   style="margin-right: 200px;">
                        <input  :id="key" class="inputText" type="text" :value="key">
                    </el-form-item>
                    <el-form-item >
                        <select id="select" class="inputSelect">
                            <option value="key1">test1</option>
                            <option value="key2">test2</option>
                            <option value="key3">fghijok3</option>
                            <option value="key4">test4</option>
                            <option value="key5">tes5t</option>
                        </select>
                    </el-form-item>
                </div>
            </el-form>
            <el-button type="primary" @click="submit(currency.table[0])">Query</el-button>
        </el-collapse-item>
    </div>
</template>

<script>
    export default {
        name: 'ShowCollapse',
        data() {
            return {
                activeNames: ['1']
            };
        },
        methods: {
            handleChange(val) {
                console.log(val);
            }
        }
    }
</script>

