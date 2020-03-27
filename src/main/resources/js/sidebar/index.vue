<template>
    <div>
<!--        <button @click="collapse"></button>-->
    <el-menu
            :show-timeout="200"
            :default-active="$route.path"
            :collapse="isCollapse"
            mode="vertical"
            background-color="#304156"
            text-color="#bfcbd9"
            active-text-color="#409EFF">
        <sidebar-item
                v-for="route in permission_routers"
                :key="route.path"
                :item="route"
                :base-path="route.path" />
    </el-menu>
    </div>
</template>

<script>
    import { mapGetters } from 'vuex'
    import SidebarItem from './SidebarItem.vue'

    export default {
        name: "sidebarMenu",
        components: { SidebarItem },
        methods:{
            collapse(){
                this.$store.commit('setCollapse');
            }
        },
        computed: {
            ...mapGetters([
                'permission_routers',
                'sidebar'
            ]),
            isCollapse () {
                console.log(this.sidebar.opened );
                return !this.sidebar.opened
            }
        }
    }
</script>
