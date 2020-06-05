<template>
    <div style="background-color: #f1f1f1" >
        <section class="el-container" >
            <aside id="sidebar" class="el-aside" style="background-color: #304156; min-height: 100vh; width: 220px;">
                <sidebar-menu/>
            </aside>
            <section  class="el-container is-vertical">
                <div>
                    <el-header style="padding: 0px;">
                        <el-menu style="background-color: #f3f3f4"  mode="horizontal">
                            <el-menu-item class="item">
                                <div class="container" @click="collapse">
                                    <div class="bar1"></div>
                                    <div class="bar2"></div>
                                    <div class="bar3"></div>
                                </div>
                            </el-menu-item>
                            <el-menu-item class="item">
                                АС ГИХД ТОРИС
                            </el-menu-item>
                        </el-menu>
                    </el-header>
                </div>
                <router-view  style="padding:30px; border-radius:5px; "/>
            </section>
        </section>
    </div>
</template>

<script>
    import Header from "../../old-front/components/Header.vue";
    import FileLoader from "../../old-front/components/UploadFile.vue";
    import SidebarMenu from "../sidebar/index.vue";
    import {mapGetters} from "vuex";
    export default {
        name: "App",
        components: {SidebarMenu, FileLoader, Header},
        methods:{
            collapse(){
                this.$store.commit('setCollapse');
                if(this.sidebar.opened ){
                    document.getElementById("sidebar").style.width="220px";
                }else{
                    document.getElementById("sidebar").style.width="50px";
                }
            }
        },
        computed: {
            ...mapGetters([
                'permission_routers',
                'sidebar'
            ]),
            isCollapse () {
                return !this.sidebar.opened
            }
        }
    }
</script>

<style scoped>
    .item:hover{
        background-color: #f3f3f4!important;
    }

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

    div {
        font-family: 'PdfIntextCondPro-Bold';
        font-size: 100% !important;
    }

    body{
        margin: 0 !important;
    }

    #sidebar{
        -webkit-animation: 220ms;
    }

</style>