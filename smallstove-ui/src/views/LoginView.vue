<!--suppress JSCheckFunctionSignatures -->
<template>
  <el-form ref="loginFormRef" :model="loginForm" :rules="rules" label-width="100px" class="loginForm">
    <el-form-item label="账号" prop="username">
      <el-input v-model="loginForm.username" type="text" autocomplete="off" />
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="loginForm.password" type="password" autocomplete="off" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm(loginFormRef)">登录</el-button>
      <el-button @click="resetForm(loginFormRef)">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import router from '@/router'
import type { FormInstance } from 'element-plus'
import { ElMessage } from 'element-plus'
import { useLoginUserStore } from '@/stores'
import { login } from '@/api/user'
import LoginUser from '@/types/interface/LoginUser'
import { setAccessTokenToLocal, setRefreshTokenToLocal } from '@/utils/storage'

const loginFormRef = ref<FormInstance>()
const loginForm = reactive({
  username: '',
  password: ''
})

const rules = reactive({
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
    { min: 5, max: 12, message: '账号长度应该在5-12位之间', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 5, max: 16, message: '密码长度应该在5-16位之间', trigger: 'blur' }
  ]
})

const submitForm = (form: FormInstance | undefined) => {
  if (form) {
    form.validate(async (valid) => {
      if (!valid) {
        ElMessage.warning('表单信息填写错误!')
        return false
      }

      // 登录并保存令牌
      const res: any = await login(loginForm)
      const loginUser: LoginUser = res.data
      setAccessTokenToLocal(loginUser.token.access_token)
      setRefreshTokenToLocal(loginUser.token.refresh_token)
      useLoginUserStore()
        .set(loginUser)
        .then(() => {
          router.push('/')
          ElMessage.success(res.message)
        })
    })
  }
}

const resetForm = (form: FormInstance | undefined) => {
  if (form) {
    form.resetFields()
  }
}
</script>
