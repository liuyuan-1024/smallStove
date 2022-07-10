<template>
  <div class="floating-border">
    <div id="article-option-bar" class="clearFloat">
      <el-button class="menu-button list-option">热门</el-button>
      <el-button class="menu-button list-option">最新</el-button>
      <el-button class="menu-button list-option">话题</el-button>
    </div>
    <div id="topics" class="clearFloat">
      <el-button class="small-menu-button topic" @click="toTagDetails">Java核心</el-button>
      <el-button class="small-menu-button topic" @click="toTagDetails">Web开发</el-button>
      <el-button class="small-menu-button topic" @click="toTagDetails">Vue探讨</el-button>
    </div>
  </div>

  <SingleArticle
      v-for="(article, index) in page.rows"
      :key="index"
      :article="article"
  />

  <el-pagination
      v-model:currentPage="currentPage"
      v-model:pageSize="pageSize"
      :pager-count="7"
      layout="total,size, prev, pager, next, jumper"
      :total="page.total"
      class="pagination"
      @current-change="handleCurrentChange"
  />
</template>

<script setup lang="ts">
import {ref, reactive} from 'vue'
import SingleArticle from '@/components/article/SingleArticle.vue'
import {getArticleList} from '@/api/article'
import {toTagDetails} from '@/assets/ts/common'
import Page from '@/types/interface/Page'
import Article from "@/types/interface/Article";

let currentPage = ref(1)
let pageSize = ref(10)
const page: Page<Article> = reactive({
  rows: [],
  total: 0,
})

getArticleList({
  currentPage: currentPage.value,
  pageSize: pageSize.value,
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
#article-option-bar {
  padding: 8px 18px 0;

  .list-option {
    float: left;
    padding: 5px 10px;
  }
}

#topics {
  padding: 5px 18px;

  .topic {
    float: left;
  }
}
</style>
