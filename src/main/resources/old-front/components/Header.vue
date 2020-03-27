<template>
    <div>
        <el-header style="padding: 0px;">
            <el-menu style="background-color: #f3f3f4" class="el-menu-demo" mode="horizontal">
                <div class="container" @click="collapse">
                    <div class="bar1"></div>
                    <div class="bar2"></div>
                    <div class="bar3"></div>
                </div>
<!--                <router-link class="el-menu-item" to="/source">Источникии</router-link>-->
<!--                <router-link class="el-menu-item" to="/createPattern">Создать шаблон</router-link>-->
<!--                <router-link class="el-menu-item" to="/">Загрузить данные</router-link>-->
            </el-menu>
        </el-header>
    </div>
</template>

<script>
    export default {
        name: "Header",
        methods:{
            collapse(){
                this.$store.commit('setCollapse');
            }
        },
    }
</script>

<style scoped>
    .el-menu-item {
        text-decoration: none;
    }
    .container {
        display: inline-block;
        cursor: pointer;
        padding: 10px 13px;
        margin: 5px;
        border-radius: 5px;
        background-color: #1ab394;
    }

    .bar1, .bar2, .bar3 {
        width: 15px;
        height: 3px;
        background-color: white;
        margin: 3px 0;
        transition: 0.4s;
    }

    /* Rotate first bar */

</style>