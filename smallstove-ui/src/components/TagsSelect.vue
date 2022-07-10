<template>
  <el-select
      v-model="tags"
      multiple
      :multiple-limit="limit"
      filterable
      default-first-option
      :reserve-keyword="false"
      placeholder="选择标签"
  >
    <el-option
        v-for="tag in allTags"
        :key="tag.typeId"
        :label="tag.typeName"
        :value="tag.typeId"
        class="all-tags"
    />
  </el-select>
</template>

<script lang="ts" setup>
import {ref} from 'vue'
import {getTags} from "@/api/article";
import Type from "@/types/interface/Type";

const limit: number = 10
const tags = ref<Type[]>([])
const allTags = ref<Type[]>([])

getTags().then((value) => {
  allTags.value = value.data
})
</script>

<style lang="less" scoped>
.el-select {
  display: inline-block;
  width: 90%;
  padding-top: 5px;

  :deep(.el-select__tags) {
    height: 24px !important;

    .el-tag--info {
      background-color: #343F51 !important;
    }
  }
}
</style>