<!-- markdown 快捷键 -->
<template>
  <el-scrollbar id="markdown" class="floating-border">
    <md-editor v-model="hotKey" preview-only class="markdown hot-key" preview-theme="preview"/>
  </el-scrollbar>
</template>

<script lang="ts" setup>
import MdEditor from 'md-editor-v3'
import 'md-editor-v3/lib/style.css'

const hotKey = `
| 键位 | 功能 |
| - | - |
| CTRL + 1-6 | 1-6级标题 |
| CTRL + B| 加粗|
| CTRL + U | 下划线 |
| CTRL + I | 斜体 |
| CTRL + SHIFT + S | 删除线 |
| CTRL + ↑ | 上角标 |
| CTRL + ↓ | 下角标 |
| CTRL + Q| 引用 |
| CTRL + SHIFT + U | 无序列表 |
| CTRL + O | 有序列表 |
| CTRL + ALT + C | 行内代码 |
| CTRL + SHIFT + C | 块级代码 |
| CTRL + L | 链接 |
| CTRL + SHIFT + I | 图片链接 |
| CTRL + SHIFT + ALT + T | 表格 |
`
</script>

<style lang="less" scoped>
#markdown {
  margin: 10px 0;
}

.hot-key {
  :deep(table) {
    margin: 0 auto !important;
  }
}
</style>
