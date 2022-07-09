<template>
  <el-container>
    <LeftBar/>
    <HeaderBar/>
    <el-main id="main">
      <router-view v-slot="{ Component }">
        <keep-alive>
          <component :is="Component" v-if="useRoute().meta.keepAlive"/>
        </keep-alive>
        <component :is="Component" v-if="!useRoute().meta.keepAlive"/>
      </router-view>
    </el-main>
    <RightBar/>
    <Backtop/>
  </el-container>
</template>

<script lang="ts" setup>
import HeaderBar from '@/components/layout/HeaderBar.vue'
import LeftBar from '@/components/layout/LeftBar.vue'
import RightBar from '@/components/layout/RightBar.vue'
import Backtop from '@/components/Backtop.vue'
import '@/assets/css/reset.less';
import '@/assets/css/common.less';
import {useRoute} from "vue-router";
</script>

<style lang="less">
html,
body {
  height: 100%;
}

#app {
  min-height: 100%;
  background-color: #0f172a;
  color: #94a3b8;
}

#main {
  margin: 0 calc(400px + 100% - 100vw) 0 350px;
  padding-top: 55px;
  min-width: 786.5px;
  --el-main-padding: 0 7px 0 !important;
}
</style>
