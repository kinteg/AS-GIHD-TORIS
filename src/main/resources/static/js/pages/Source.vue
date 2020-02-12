<template>
    <div>
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="120px" class="demo-ruleForm">
            <el-form-item>
                <h2>Поиск источника</h2>
                <search-source/>
            </el-form-item>
            <el-form-item >
                <h2>Создание источника</h2>
                <create-source/>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import SearchSource from "../components/SearchSource.vue";
    import CreateSource from "../components/CreateSource.vue";
    export default {
        name: "Source",
        components: {CreateSource, SearchSource}
    }
</script>

<style scoped>
    .el-container {
        padding: 20px;
        margin: auto;
        max-width: 950px;
        position: relative;
        display: block;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
    }
    div {
        font-family: 'PdfIntextCondPro-Bold';
        font-size: 100% !important;
    }
</style>