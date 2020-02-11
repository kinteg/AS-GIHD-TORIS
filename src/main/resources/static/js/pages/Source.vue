<template>
    <div>
        <search-source/>
        <create-source/>
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