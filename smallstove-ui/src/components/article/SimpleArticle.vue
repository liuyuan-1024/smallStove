<!-- 显示在个人主页中的单个文章组件 -->
<template>
  <div class="article floating-border">
    <div>
      {{ article.createTime }}
      <span v-for="(tag, index) in article.typeVoList" :key="index" class="hover-pointer" @click.stop="toTagDetails">
        {{ tag.typeName }}
      </span>
    </div>

    <md-editor v-model="article.title" preview-only class="markdown" preview-theme="preview"/>
    <md-editor v-model="article.thumbnail" preview-only class="markdown" preview-theme="preview"/>
  </div>

</template>

<script setup lang="ts">
import MdEditor from 'md-editor-v3'
import 'md-editor-v3/lib/style.css'
import {toTagDetails} from '@/assets/ts/common'
import Article from "@/types/interface/Article";

defineProps<{
  article: Article
}>()
</script>

<style lang="less" scoped>
.article {
  margin: 10px 0;
}
</style>