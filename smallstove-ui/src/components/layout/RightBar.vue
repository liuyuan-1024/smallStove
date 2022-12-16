<template>
  <el-aside v-show="isLogin()" id="right-bar">
    <div class="search-bar">
      <el-input class="search" placeholder="Please Input" clearable :prefix-icon="Search"/>
    </div>

    <div class="floating-border user-info clearFloat">
      <el-image
          :src="loginUserRef.avatar"
          fit="fill"
          class="avatar"
          @click="toMy(loginUserRef.id)"
      />
      <h2>{{ loginUserRef.nickName }}</h2>
    </div>

    <!-- markdown 快捷键 -->
    <HotKey v-if="isPublish()"/>
  </el-aside>
</template>

<script lang="ts" setup>
import HotKey from '@/components/markdown/HotKey.vue';
import {Search} from '@element-plus/icons-vue';
import {isPublish} from "@/assets/ts/common";
import {useLoginUserStore} from "@/stores";
import {storeToRefs} from "pinia";
import {toMy,isLogin} from "@/assets/ts/common";

const loginUserRef = storeToRefs(useLoginUserStore()).loginUser;

let login = isLogin();
console.log("isLogin", login);
</script>

<style lang="less" scoped>
#right-bar {
  position: fixed;
  right: calc(100px + 100% - 100vw);
  overflow: auto;
  width: 300px;

  .search-bar {
    height: 55px;
    line-height: 55px;
  }

  .user-info {
    clear: both;
    padding: 10px;
  }
}
</style>
