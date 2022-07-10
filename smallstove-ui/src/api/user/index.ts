import requests from "@/api/requests";
import {ElMessage} from "element-plus/es";
import {isSuccess} from "@/utils/common";
import {clearLocal, clearSession} from "@/utils/storage";

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
        password: args.password,
    })
}

/**
 * 注销
 */
export async function logout() {
    requests.post('/logout')
        .then((res: any) => {
            if (isSuccess(res.code)) {
                clearLocal()
                clearSession()
                ElMessage.success(res.message)
            }
        })
}

/**
 * 查看本人的个人信息
 */
export async function getUserInfo(userId: number) {
    return requests.get(`/user-info/${userId}`)
}
