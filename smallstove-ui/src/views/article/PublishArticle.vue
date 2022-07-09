<template>
  <!--  :toolbars="toolbars"-->
  <md-editor v-model="text"
             :toolbars="toolbars"
             :placeholder="placeholder"
             :show-code-row-number="true"
             class="markdown markdown-editor"
             @on-upload-img="onUploadImg"

  />
  <TagsSelect/>
</template>

<script setup lang="ts">
import TagsSelect from '@/components/TagsSelect.vue'
import {onMounted, ref} from "vue"
import {getTags} from "@/api/api"
import MdEditor from 'md-editor-v3'
import 'md-editor-v3/lib/style.css'
import Type from "@/types/interface/Type";

let text = ref(``)
const tags = ref<Type[]>([])

onMounted(() => {
  getTags().then((value) => {
    tags.value = value.data
  })
})

const placeholder = "首个'一级标题'将作为帖子标题,\n暂不支持上传图片, 十分抱歉!!"
const toolbars = [
  'bold', 'underline', 'italic', 'strikeThrough', 'sub', 'sup', 'quote', 'unorderedList', 'orderedList', 'codeRow',
  'code', 'link', 'image', 'table', 'mermaid', 'katex', 'revoke', 'next', 'save',
  '=',
  'pageFullscreen', 'preview', 'htmlPreview', 'catalog'
];

/**
 * 上传图片的预览是直接加载远程地址的图片, 所以在回调函数中需要urls作为参数
 * @param files
 * @param callback
 */
const onUploadImg = async (files: Array<File>, callback: (urls: Array<string>) => void) => {
  const URLs: string[] = []
  files.map((file) => {
    URLs.push(URL.createObjectURL(file))
  })
  callback(URLs.map((url) => url));
};

</script>