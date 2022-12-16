import LoginUser from "@/types/interface/LoginUser";

const ACCESS = 'access_token'
const REFRESH = 'refresh_token'

/**
 * localStorage存储access_token
 * @param access_token 权限令牌
 */
export function setAccessTokenToLocal(access_token: string) {
    localStorage.setItem(ACCESS, access_token)
}

/**
 * localStorage存储refresh_token
 * @param refresh_token 刷新令牌
 */
export function setRefreshTokenToLocal(refresh_token: string) {
    localStorage.setItem(REFRESH, refresh_token)
}

/**
 * 移除localStorage中存储的access_token
 */
export function delAccessTokenFromLocal() {
    localStorage.removeItem(ACCESS)
}

/**
 * 移除localStorage中存储的refresh_token
 */
export function delRefreshTokenFromLocal() {
    localStorage.removeItem(REFRESH)
}

/**
 * 获取localStorage中存储的access_token
 */
export function getAccessTokenFromLocal(): string {
    return localStorage.getItem(ACCESS) || ''
}

/**
 * 获取localStorage中存储的refresh_token
 */
export function getRefreshTokenFromLocal(): string {
    return localStorage.getItem(REFRESH) || ''
}

/**
 * 清空localStorage
 */
export function clearLocal() {
    localStorage.clear()
}


const LOGIN_USER = 'loginUser'

/**
 * sessionStorage存储登录用户信息
 * @param loginUser 登录用户信息
 */
export function setLoginUserToSession(loginUser: LoginUser) {
    sessionStorage.setItem(LOGIN_USER, JSON.stringify(loginUser))
}

/**
 * 移除sessionStorage中存储的用户信息
 */
export function delLoginUserFromSession() {
    sessionStorage.removeItem(LOGIN_USER)
}

/**
 * sessionStorage存储用户信息
 */
export function getLoginUserFromSession(): LoginUser | '' {
    const item = sessionStorage.getItem(LOGIN_USER);
    if (item === null || item === '') {
        return ''
    } else {
        return JSON.parse(item);
    }
}

/**
 * 清空localStorage
 */
export function clearSession() {
    sessionStorage.clear()
}
