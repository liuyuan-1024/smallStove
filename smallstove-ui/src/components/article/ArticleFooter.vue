<template>
  <div class="bottom-bar">
    <el-button :icon="ForkSpoon" disabled class="bottom-button disabled-button">围观 {{ viewsNumber }}</el-button>
    <el-button :icon="MagicStick" class="small-menu-button bottom-button" @click.stop="clickLikes()">
      点赞 {{ likesNumberRef }}
    </el-button>
    <el-button :icon="Share" class="small-menu-button bottom-button">分享</el-button>
  </div>
</template>

<script setup lang="ts">
import {MagicStick, ForkSpoon, Share} from '@element-plus/icons-vue'
import {likes} from '@/api/article'
import {toRef} from "vue";

const props = defineProps<{
  articleId: number,
  viewsNumber: number,
  likesNumber: number,
}>()

let likesNumberRef = toRef(props, 'likesNumber');

const clickLikes = () => {
  likes(props.articleId)
}
</script>

<style lang="less" scoped>
.bottom-bar {
  padding-top: 5px;
  border-top: 1px solid #94a3b8;

  .bottom-button {
    width: 225px;
  }

  .el-button.is-disabled {
    border-color: transparent;
    background-color: transparent;

    :deep(span) {
      font-weight: bold;
      color: #94a3b8;
    }
  }
}
</style>
