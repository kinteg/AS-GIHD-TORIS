<template>
    <div>
        <el-header>
            <el-menu class="el-menu-demo" mode="horizontal">
                <router-link class="el-menu-item" to="/source">Источники</router-link>
                <router-link class="el-menu-item" to="/createPattern">Создать шаблон</router-link>
                <router-link class="el-menu-item" to="/">Загрузить данные</router-link>
            </el-menu>
        </el-header>
    </div>
</template>

<script>
    export default {
        name: "Header"
    }
</script>

<style scoped>
    .el-header {
        margin: auto;
        display: block;
        padding: 10px;
    }
    .el-menu-item {
        text-decoration: none;
    }
</style>