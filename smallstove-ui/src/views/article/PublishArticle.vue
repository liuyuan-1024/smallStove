<template>
  <el-input v-model="title"
            maxlength="64"
            show-word-limit
            placeholder="标题, 不超过64个字"
            class="title-input"
  />
  <md-editor v-model="content"
             :toolbars="toolbars"
             :placeholder="placeholder"
             :show-code-row-number="true"
             class="markdown markdown-editor"
             @on-upload-img="onUploadImg"
  />
  <TagsSelect/>
  <el-button id="submit" class="menu-button submit-button" @click="publish">提交</el-button>
</template>

<script setup lang="ts">
import TagsSelect from '@/components/TagsSelect.vue'
import {ref} from "vue"
import MdEditor from 'md-editor-v3'
import 'md-editor-v3/lib/style.css'
import {ElMessage} from "element-plus/es";

const placeholder = "首个'一级标题'将作为帖子标题,\n暂不支持上传图片, 十分抱歉!!"
const toolbars = [
  'bold', 'underline', 'italic', 'strikeThrough', 'sub', 'sup', 'quote', 'unorderedList', 'orderedList', 'codeRow',
  'code', 'link', 'image', 'table', 'mermaid', 'katex', 'revoke', 'next', 'save',
  '=',
  'pageFullscreen', 'preview', 'htmlPreview', 'catalog'
];

let title = ref<string>('')
let content = ref<string>(``)

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

/**
 * 提交帖子
 */
function publish(): void {
  if (title.value === '' || content.value === '') {
    ElMessage.warning('标题或内容不能为空!!!')
    return
  }

  alert('提交帖子');
}
</script>

<style lang="less" scoped>
.title-input {
  margin: 0 0 5px 0;

  :deep(.el-input__count-inner) {
    background: #1e293b !important;
  }
}

.submit-button {
  background-color: #1e293b;
  margin-left: 6px;
}
</style>