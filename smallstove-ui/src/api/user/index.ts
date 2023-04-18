import requests from '@/api/requests'
import { ElMessage } from 'element-plus/es'
import { isSuccess } from '@/utils/common'
import { clearLocal, clearSession } from '@/utils/storage'
import router from '@/router'

/**
 * 检查token是否合法
 */
export async function checkToken() {
  return requests.get('/check-token')
}

/**
 * 刷新token
 */
export async function refreshToken() {
  return requests.get('/refresh-token')
}

/**
 * 登录
 * @param args 账号, 密码
 */
export async function login(args: { username: string; password: string }) {
  return requests.post('/anonymous/login', {
    username: args.username,
    password: args.password
  })
}

/**
 * 注销
 */
export async function logout() {
  requests
    .post('/logout')
    .then((res: any) => {
      clearLocal()
      clearSession()
      ElMessage.success(res.message)
      router.push('/')
    })
    .catch((error) => {
      ElMessage.warning(error.message)
    })
}

/**
 * todo 在assets/ts/commons.ts中已有判断方法
 * 判断用户是否已登录
 * true 已登陆
 * false 未登录
 */
export async function isLogin(userId: number) {
  requests.get(`/isLogin/${userId}`).then((res: any) => {
    if (isSuccess(res.code)) {
      return res.data
    }
  })
}

/**
 * 查看本人的个人信息
 */
export async function getUserInfo(userId: number) {
  return requests.get(`/user-info/${userId}`)
}
