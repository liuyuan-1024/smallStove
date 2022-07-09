<template>
  <div class="user-info">
    <el-image
        :src="avatar? avatar:'avatar-404.jpg'"
        fit="fill"
        loading="lazy"
        :preview-src-list="avatar"
    />
    <h1>nickName: {{ nickName }}</h1>
    <div v-if="isLoginUser()">
      username: {{ loginUserRef.username }}
      <br/>
      email: {{ loginUserRef.email }}
      <br/>
      mobile: {{ loginUserRef.mobile }}
      <br/>
      sex: {{ loginUserRef.sex }}
    </div>
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
import {getArticleList, getUserInfo} from "@/api/api";
import SimpleArticle from '@/components/article/SimpleArticle.vue';
import {toArticleDetails} from '@/assets/ts/common';
import Page from "@/types/interface/Page";
import {useLoginUserStore} from "@/stores";
import {storeToRefs} from "pinia";
import Article from "@/types/interface/Article";

// 当前登录用户信息
let loginUserRef = storeToRefs(useLoginUserStore()).loginUser
const loginUserId = loginUserRef.value.id

// 必然显示的信息
let avatar = ref(loginUserRef.value.avatar)
let nickName = ref(loginUserRef.value.nickName)

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
let currentPage = ref(1);
let pageSize = ref(10)
const page: Page<Article> = reactive({
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
  .el-image {
    width: 100px;
    height: 100px;
    border-radius: 8px;
  }
}
</style>
