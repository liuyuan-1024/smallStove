<template>
  <el-aside id="left-bar">
    <div id="favicon">开源小灶..........哈哈哈</div>

    <div id="left-menu-bar" class="floating-border">
      <router-link to="/" class="router-link">
        <el-button :icon="Monitor" class="menu-button left-menu">主页</el-button>
      </router-link>
      <router-link to="/notification">
        <el-button :icon="Bell" class="menu-button left-menu">
          <el-badge :value="12" type="primary">通知</el-badge>
        </el-button>
      </router-link>
      <router-link to="/chat">
        <el-button :icon="ChatDotRound" class="menu-button left-menu">
          <el-badge :value="12" type="primary">私信</el-badge>
        </el-button>
      </router-link>
      <router-link to="/favorites">
        <el-button :icon="Star" class="menu-button left-menu">收藏</el-button>
      </router-link>
      <el-button :icon="User" class="menu-button left-menu" @click="toMy(loginUserRef.id)">我的</el-button>
    </div>
  </el-aside>
</template>

<script lang="ts" setup>
import {Monitor, Bell, ChatDotRound, Star, User} from '@element-plus/icons-vue';
import {toMy} from "@/assets/ts/common";
import {useLoginUserStore} from "@/stores";
import {storeToRefs} from "pinia";

const loginUserRef = storeToRefs(useLoginUserStore()).loginUser
</script>

<style lang="less" scoped>
#left-bar {
  position: fixed;
  left: 100px;
  width: 250px;
  text-align: center;

  #favicon {
    height: 55px;
    line-height: 55px;
  }

  #left-menu-bar {
    height: 340px;
    padding: 20px 0;
  }

  .left-menu {
    width: 200px;
    padding: 27px 30px;
    margin: 3px;
    // 字体图标
    font-size: 24px;
  }
}
</style>
