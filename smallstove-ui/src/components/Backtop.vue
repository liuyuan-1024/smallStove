<template>
  <el-backtop class="back-top" :bottom="15" :right="15">
    <el-icon>
      <Top/>
    </el-icon>
  </el-backtop>
</template>

<script setup>
import {Top} from '@element-plus/icons-vue'
</script>

<style lang="less" scoped>
.back-top {
  width: 50px;
  height: 50px;
  background-color: #1e293b;
  border-radius: 5px;

  &:hover{
    background-color: #334155;
  }
}
</style>