<!--suppress JSUnresolvedVariable -->
<template>
  <el-container>
    <LeftBar />
    <HeaderBar />
    <el-main id="main">
      <router-view v-slot="{ Component }">
        <keep-alive>
          <component :is="Component" v-if="useRoute().meta.keepAlive" />
        </keep-alive>
        <component :is="Component" v-if="!useRoute().meta.keepAlive" />
      </router-view>
    </el-main>
    <RightBar />
    <Backtop />
  </el-container>
</template>

<script lang="ts" setup>
import HeaderBar from '@/components/layout/HeaderBar.vue'
import LeftBar from '@/components/layout/LeftBar.vue'
import RightBar from '@/components/layout/RightBar.vue'
import Backtop from '@/components/Backtop.vue'
import '@/assets/css/reset.less'
import '@/assets/css/common.less'
import { useRoute } from 'vue-router'
</script>
