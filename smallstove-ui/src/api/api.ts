import requests from '@/api/requests'
import {ElMessage} from "element-plus/es";

/**
 * 响应是否成功
 * @param code 响应码
 */
export function isSuccess(code: number) {
    return String(code).substring(0, 3) === '200';
}

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
                localStorage.clear()
                sessionStorage.clear()
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

/**
 * 分页获取文章列表
 * @param args 分页数据: 当前页, 页尺寸, 类型ID, 用户ID
 */
export async function getArticleList(args: {
    currentPage: number
    pageSize: number
    typeId?: number
    userId?: number
}) {
    return requests.get('/anonymous/article-list', {params: args})
}

/**
 * 获取文章详情   异步方法即使使用了await, 返回值也是promise对象
 * @param articleId 文章ID
 */
export async function getArticleDetails(articleId: unknown) {
    return requests.get(`/anonymous/article-details/${articleId}`)
}

/**
 * 点赞
 * @param articleId
 */
export async function likes(articleId: unknown) {
    requests.put(`/likes/${articleId}`)
        .then((value: any) => {
            if (isSuccess(value.code)) {
                ElMessage.success(value.message)
            }
        })
}

/**
 * 查询文章标签
 */
export async function getTags() {
    return requests.get('/tags')
}

/**
 * 图片上传
 */
export async function upload() {
    return requests.post('/upload-image')
}



