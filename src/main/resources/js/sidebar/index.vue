<template>
  <el-scrollbar wrap-class="scrollbar-wrapper">
    <el-menu
      :show-timeout="200"
      :default-active="$route.path"
      :collapse="isCollapse"
      mode="vertical"
      background-color="#304156"
      text-color="#bfcbd9"
      active-text-color="#409EFF">
      <sidebar-item
        v-for="route in ROUTER"
        :key="route.path"
        :item="route"
        :base-path="route.path" />
    </el-menu>
  </el-scrollbar>
</template>

<script>
import { mapGetters } from 'vuex'
import store from "../store/store";
import SidebarItem from './SidebarItem.vue'
import router from "../router/router";
export default {
  name:"sideBar",
  store,
  components: { SidebarItem },
  computed: {
    ...mapGetters([
      'ROUTER',
      'SIDEBAR'
    ]),
    isCollapse () {
      return !this.SIDEBAR.opened
    }
  }
}
</script>
