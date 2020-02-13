<template>
    <div>
        <upload-file/>
        <show-collapse/>
    </div>
</template>

<script>
    import UploadFile from "../components/UploadFile.vue";
    import ShowCollapse from "../components/ShowCollapse.vue";
    export default {
        name: "FileLoader",
        components: {ShowCollapse, UploadFile}
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