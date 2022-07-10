<template>
  <div class="article-header clearFloat">
    <el-image
        :src="avatar ? avatar:'avatar-404.jpg'"
        class="avatar"
        @click.stop="toMy(userId)"
    />
    <div class="user">
      <span class="username hover-pointer" @click.stop="toMy(userId)"> {{ nickName }} </span>
      <div>
        {{ createTime }}
        <span v-for="(tag, index) in typeVoList" :key="index" class="hover-pointer" @click.stop="toTagDetails">
          {{ tag.typeName }}
        </span>
      </div>
    </div>
    <el-button :icon="MoreFilled" class="menu-button fold-option"/>
  </div>
</template>

<script setup lang="ts">
import {MoreFilled} from '@element-plus/icons-vue'
import {toTagDetails, toMy} from '@/assets/ts/common'
import Type from "@/types/interface/Type";

defineProps<{
  userId: number,
  nickName: string,
  avatar: string,
  createTime: string,
  typeVoList: Type[]
}>()
</script>

<style lang="less" scoped>
.article-header {
  padding-bottom: 5px;
  .user {
    position: absolute;
    left: 65px;
    height: 50px;

    .username {
      display: block;
      padding-bottom: 3px;
      font-size: 18px;
    }
  }

  .fold-option {
    float: right;
  }
}
</style>
