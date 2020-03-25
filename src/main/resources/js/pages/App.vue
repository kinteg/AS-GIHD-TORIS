<template>
    <div>
        <sidebar-menu/>
        <Header/>
        <el-container>
            <router-view/>
        </el-container>
    </div>
</template>

<script>
    import Header from "../components/Header.vue";
    import FileLoader from "../components/UploadFile.vue";
    import SidebarMenu from "../sidebar/index.vue";
    export default {
        name: "App",
        components: {SidebarMenu, FileLoader, Header},
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