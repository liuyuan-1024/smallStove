<!-- 显示在文章列表中的单个文章组件 -->
<template>
  <div class="article floating-border" @click="toArticleDetails(article.articleId)">
    <ArticleHeader
        :user-id="article.userId"
        :nick-name="article.nickName"
        :avatar="article.avatar"
        :create-time="article.createTime"
        :type-vo-list="article.typeVoList"
    />

    <md-editor v-model="article.title" preview-only class="markdown" preview-theme="preview"/>
    <md-editor v-model="article.thumbnail" preview-only class="markdown" preview-theme="preview"/>

    <ArticleFooter
        :article-id="article.articleId"
        :views-number="article.viewsNumber"
        :likes-number="article.likesNumber"
    />
  </div>
</template>

<script setup lang="ts">
import MdEditor from 'md-editor-v3'
import 'md-editor-v3/lib/style.css'
import ArticleHeader from './ArticleHeader.vue'
import ArticleFooter from './ArticleFooter.vue'
import {toArticleDetails} from '@/assets/ts/common'
import Article from "@/types/interface/Article";

defineProps<{
  article: Article
}>()
</script>

<style lang="less" scoped>
.article {
  position: relative;
  margin: 7px 0;
  padding: 10px;
}
</style>
