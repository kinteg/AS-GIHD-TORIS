<template>
    <div>
        <el-collapse>
            <div v-for="currency in data" class="currency">
                <el-collapse-item :title=currency.nameTable >
<!--                   <input  :id="currency.nameTable" class="inputText" type="text" :value="currency.nameTable">-->
                    Название таблицы<el-input :id="currency.nameTable" :value="currency.nameTable" v-model="currency.nameTable" type="text" autocomplete="off"></el-input>
                    <br><br><span style="padding: 5px; border-right: 1px solid #dcdfe6">Ключ</span> Название поля <span style=" float: right">Тип поля</span>
                    <collapse-form :currency-table="currency"/>
                </el-collapse-item>
            </div>
        </el-collapse>

    </div>
</template>

<script>
    import CollapseForm from "./CollapseForm.vue";
    import validation from  "../methods/validation";
    export default {
        name: "CollapseShow",
        components: {CollapseForm},
        props: ['data']
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