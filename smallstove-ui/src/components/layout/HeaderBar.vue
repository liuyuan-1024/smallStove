<template>
  <div id="fixed-header">
    <el-button v-show="!isArticleListView()" :icon="ArrowLeftBold" class="menu-button back" @click="back()"/>

    <div class="header-menu-box">
      <el-menu :default-active="route.path" mode="horizontal" :ellipsis="false" class="menu header-menu">
        <el-menu-item class="menu-item header-menu-item recommend" index="/" @click="toMain()">
          <span>推荐</span>
        </el-menu-item>
        <el-menu-item class="menu-item header-menu-item attention" index="/attention" @click="toAttention()">
          <span>关注</span>
        </el-menu-item>
      </el-menu>
    </div>

    <el-button v-show="!isLogin()" class="menu-button login-publish" @click="toLogin">登录</el-button>
    <el-button v-show="isLogin() && !isPublish()" class="menu-button login-publish" @click="toPublishArticle">
      发帖
    </el-button>
  </div>
</template>

<script lang="ts" setup>
import {ArrowLeftBold} from '@element-plus/icons-vue'
import {
  back,
  toMain,
  toAttention,
  toLogin,
  toPublishArticle,
  isLogin,
  isArticleListView,
  isPublish
} from "@/assets/ts/common";
import {useRoute} from "vue-router";

const route = useRoute()
</script>

<style lang="less" scoped>
#fixed-header {
  position: fixed;
  top: 0;
  left: 350px;
  z-index: 1;
  width: 772.5px;
  height: 55px;
  margin: 0 7px;

  .header-menu-box {
    width: 160px;
    margin: 0 auto;

    .header-menu {
      justify-content: center;
      background-color: transparent !important;
      text-align: center;

      .header-menu-item {
        border: none;
        width: 80px;
        height: 55px;
        font-size: 20px;
      }
    }
  }


  .back, .login-publish {
    position: absolute;
    top: 12px;
    margin: 0;
    background-color: #3b82f6;

    :deep(span) {
      color: #cfcbd8;
    }
  }

  .back {
    left: 10px;
  }

  .login-publish {
    right: 10px;
  }
}
</style>
