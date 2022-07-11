<template>
  <div class="article floating-border">
    <ArticleHeader
        :user-id="article.userId"
        :nick-name="article.nickName"
        :avatar="article.avatar"
        :create-time="article.createTime"
        :type-vo-list="article.typeVoList"
    />

    <md-editor v-model="article.title" preview-only class="markdown"/>
    <md-editor v-model="article.thumbnail" preview-only class="markdown"/>
    <md-editor v-model="article.content" preview-only class="markdown"/>

    <ArticleFooter
        :article-id="article.articleId"
        :views-number="article.viewsNumber"
        :likes-number="article.likesNumber"
    />
  </div>
</template>

<script setup lang="ts">
import {ref} from "vue";
import {useRoute} from "vue-router";
import MdEditor from 'md-editor-v3'
import 'md-editor-v3/lib/style.css'
import ArticleHeader from '@/components/article/ArticleHeader.vue'
import ArticleFooter from '@/components/article/ArticleFooter.vue'
import {getArticleDetails} from "@/api/article";

const article = ref({})
getArticleDetails(useRoute().params.articleId)
    .then((value: any) => {
      article.value = value.data
    })
</script>

<style lang="less" scoped>
.article {
  position: relative;
  margin: 7px 0;
  padding: 10px;
}
</style>
