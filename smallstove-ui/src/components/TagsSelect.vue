<template>
  <el-select
      v-model="tags"
      multiple
      multiple-limit="10"
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
import {getTags} from "@/api/api";
import Type from "@/types/interface/Type";

const tags = ref<Type[]>([])
const allTags = ref<Type[]>([])

getTags().then((value) => {
  allTags.value = value.data
})
</script>

<style lang="less" scoped>
.el-select {
  width: 100%;
  padding-top: 8px;

  :deep(.el-input) {
    --el-input-bg-color: #1e293b !important;
  }

  :deep(.el-tag--info) {
    background-color: #343F51 !important;
  }
}
</style>