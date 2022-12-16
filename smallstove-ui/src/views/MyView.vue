<template>
  <div class="floating-border user-info">
    <el-image
        :src="loginUserRef.avatar? avatar:'avatar-404.jpg'"
        fit="fill"
        loading="lazy"
        :preview-src-list="avatar"
    />
    <h1>昵称: {{ nickName }}</h1>
    <div v-if="isLoginUser()">
      账号: {{ loginUserRef.username }}
      <br>
      邮箱: {{ loginUserRef.email }}
      <br>
      手机: {{ loginUserRef.mobile }}
      <br>
      性别: {{ Sex[loginUserRef.sex] }}
    </div>
    <el-button type="danger" @click="logout">退出登录</el-button>
  </div>

  <div class="user-articles">
    <simple-article
        v-for="(article, index) in page.rows"
        :key="index"
        :article="article"
        @click="toArticleDetails(article.articleId)"
    />

    <el-pagination
        v-model:currentPage="currentPage"
        v-model:pageSize="pageSize"
        class="pagination"
        :pager-count="7"
        layout="total,size, prev, pager, next, jumper"
        :total="page.total"
        @current-change="handleCurrentChange"
    />
  </div>
</template>

<script setup lang="ts">
import {reactive, ref} from "vue";
import {useRoute} from "vue-router";
import {useLoginUserStore} from "@/stores";
import {storeToRefs} from "pinia";
import {getUserInfo , logout} from "@/api/user";
import {getArticleList} from "@/api/article";
import {toArticleDetails} from '@/assets/ts/common';
import SimpleArticle from '@/components/article/SimpleArticle.vue';
import Page from "@/types/interface/Page";
import Article from "@/types/interface/Article";
import {Sex} from "@/enums/SystemEnums";

// 当前登录用户信息
let loginUserRef = storeToRefs(useLoginUserStore()).loginUser
const loginUserId = loginUserRef.value.id

// 必然显示的信息
let avatar = ref<string>(loginUserRef.value.avatar)
let nickName = ref<string>(loginUserRef.value.nickName)

// 判断当前登录用户浏览的是否为本人信息
function isLoginUser(): boolean {
  return String(loginUserId) === useRoute().params.userId
}

if (!isLoginUser()) {
  getUserInfo(Number(useRoute().params.userId))
      .then((res) => {
        avatar.value = res.data.avatar
        nickName.value = res.data.nickName
      })
}

// 分页相关
let currentPage = ref<number>(1);
let pageSize = ref<number>(10)
const page = reactive<Page<Article>>({
  rows: [],
  total: 0,
})
getArticleList({
  currentPage: currentPage.value,
  pageSize: pageSize.value,
  userId: Number(useRoute().params.userId)
}).then((res: any) => {
  page.rows = res.data.rows
  page.total = res.data.total
})
const handleCurrentChange = () => {
  getArticleList({
    currentPage: currentPage.value,
    pageSize: pageSize.value,
  }).then((res: any) => {
    page.rows = res.rows
    page.total = res.total
  })
}
</script>

<style lang="less" scoped>
.user-info {
  padding: 10px;

  .el-image {
    width: 100px;
    height: 100px;
    border-radius: 8px;
  }
}
</style>
