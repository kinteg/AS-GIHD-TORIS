<template>
    <div>
        <el-collapse>
            <div v-for="currency in data" class="currency">
                <el-collapse-item :title=currency.nameTable >
                    Название таблицы<el-input :disabled="disabled" :id="currency.nameTable" :value="currency.nameTable" v-model="currency.nameTable" type="text" autocomplete="off"></el-input>
                    <br><br><span style="padding: 5px; border-right: 1px solid #dcdfe6">Ключ</span> Название поля <span style=" float: right">Тип поля</span>
                    <collapse-form  :disabled="disabled" :currency-table="currency"/>
                </el-collapse-item>
            </div>
        </el-collapse>

    </div>
</template>

<script>
    import CollapseForm from "./CollapseForm.vue";
    export default {
        name: "CollapseShow",
        components: {CollapseForm},
        props: ['data', 'disabled']
    }
</script>

<style scoped>
</style>