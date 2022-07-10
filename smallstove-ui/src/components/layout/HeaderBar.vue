<template>
  <div id="fixed-header">
    <el-button v-show="!isArticleListView()" :icon="ArrowLeftBold" class="menu-button back-button" @click="back()"/>
    <div class="recommend-focus">
      <router-link to="/">
        <el-button class="menu-button">推荐</el-button>
      </router-link>
      <router-link to="/attention">
        <el-button class="menu-button">关注</el-button>
      </router-link>
    </div>

    <el-button v-show="!isLogin()" class="menu-button login-publish" @click="toLogin">登录</el-button>
    <el-button v-show="isLogin() && !isPublish()" class="menu-button login-publish" @click="toPublishArticle">
      发帖
    </el-button>
  </div>
</template>

<script lang="ts" setup>
import router from '@/router'
import {ArrowLeftBold} from '@element-plus/icons-vue'
import {isLogin, isArticleListView, isPublish} from "@/assets/ts/common";

const toLogin = () => {
  router.push('/login')
}

const toPublishArticle = () => {
  router.push('/publishArticle')
}

const back = () => {
  router.push('/')
}


</script>

<style lang="less" scoped>
#fixed-header {
  position: fixed;
  top: 0;
  left: 350px;
  z-index: 1;
  width: 786.5px;
  height: 55px;
  text-align: center;
  background-color: #0f172a;

  .recommend-focus {
    height: 100%;
    line-height: 55px;
    margin: auto;
  }

  .back-button, .login-publish {
    position: absolute;
    top: 12px;
    margin: 0;
    background-color: #3b82f6;

    :deep(span) {
      color: #cfcbd8;
    }
  }

  .back-button {
    left: 10px;
  }

  .login-publish {
    right: 10px;
  }
}
</style>
