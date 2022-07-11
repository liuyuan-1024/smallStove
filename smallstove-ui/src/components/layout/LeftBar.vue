<template>
  <el-aside id="left-bar">
    <div id="favicon">开源小灶..........哈哈哈</div>

    <el-menu :default-active="route.path" :router="true" class="menu floating-border">
      <el-menu-item class="floating-border menu-item left-menu-item" index="/">
        <el-icon>
          <Monitor/>
        </el-icon>
        <span>主页</span>
      </el-menu-item>
      <el-menu-item class="floating-border menu-item left-menu-item" index="/notification">
        <el-icon>
          <Bell/>
        </el-icon>
        <span>通知</span>
      </el-menu-item>
      <el-menu-item class="floating-border menu-item left-menu-item" index="/chat">
        <el-icon>
          <ChatDotRound/>
        </el-icon>
        <span>私信</span>
      </el-menu-item>
      <el-menu-item class="floating-border menu-item left-menu-item" index="/favorites">
        <el-icon>
          <Star/>
        </el-icon>
        <span>收藏</span>
      </el-menu-item>
      <el-menu-item class="floating-border menu-item left-menu-item" index="/my" @click="toMy(loginUserRef.id)">
        <el-icon>
          <User/>
        </el-icon>
        <span>我的</span>
      </el-menu-item>
    </el-menu>
  </el-aside>
</template>

<script lang="ts" setup>
import {useLoginUserStore} from "@/stores";
import {storeToRefs} from "pinia";
import {Monitor, Bell, ChatDotRound, Star, User} from '@element-plus/icons-vue';
import {toMy} from "@/assets/ts/common";
import {useRoute} from "vue-router";

const loginUserRef = storeToRefs(useLoginUserStore()).loginUser
const route = useRoute()
</script>

<style lang="less" scoped>
#left-bar {
  position: fixed;
  left: 100px;
  width: 250px;

  #favicon {
    height: 55px;
    line-height: 55px;
  }

  .left-menu-item {
    padding: 0 90px;
    font-size: 20px;
  }
}
</style>
