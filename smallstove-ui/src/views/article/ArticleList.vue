<template>
  <div class="list-header floating-border">
    <div>
      <el-menu :default-active="route.path" mode="horizontal" router :ellipsis="false" class="menu list-header-menu">
        <el-menu-item class="menu-item list-header-menu-item" index="/">
          <span>热门</span>
        </el-menu-item>
        <el-menu-item class="menu-item list-header-menu-item" index="/new">
          <span>最新</span>
        </el-menu-item>
        <el-menu-item :class="showStyle" index="" @click="isShowTopics()">
          <span>话题</span>
        </el-menu-item>
      </el-menu>
    </div>
    <div v-show="showTopics" id="topics" class="clearFloat">
      <el-button class="small-button" @click="toTagDetails">Java核心</el-button>
      <el-button class="small-button" @click="toTagDetails">Web开发</el-button>
      <el-button class="small-button" @click="toTagDetails">Vue探讨</el-button>
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
      layout="total,size,prev,pager,next,jumper"
      :total="page.total"
      class="pagination"
      @current-change="handleCurrentChange"
  />
</template>

<script setup lang="ts">
import {ref, reactive} from 'vue'
import {useRoute} from "vue-router";
import SingleArticle from '@/components/article/SingleArticle.vue'
import {getArticleList} from '@/api/article'
import {toTagDetails} from '@/assets/ts/common'
import Page from '@/types/interface/Page'
import Article from "@/types/interface/Article";

const route = useRoute()

// 是否显示话题专栏
const showTopics = ref<boolean>(false)
// "话题"按钮的样式, 默认字体颜色是#94a3b8
const showStyle = ref<string>('menu-item list-header-menu-item notActive')

function isShowTopics() {
  showTopics.value = !showTopics.value
  // 改变"话题"按钮的样式
  if (showTopics.value) {
    showStyle.value = 'menu-item list-header-menu-item'
  } else {
    showStyle.value = 'menu-item list-header-menu-item notActive'
  }
}

let currentPage = ref<number>(1)
let pageSize = ref<number>(10)
const page = reactive<Page<Article>>({
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
    page.rows = res.data.rows
    page.total = res.data.total
  })
}
</script>

<style lang="less" scoped>
.list-header {
  padding: 0 15px 5px;

  .list-header-menu {
    display: inline-block;
    background-color: transparent !important;
    text-align: center;

    .list-header-menu-item {
      width: 60px;
      height: 55px;
      font-size: 20px;
    }
  }

  .notActive {
    --el-menu-active-color: #94a3b8 !important;
  }
}
</style>
