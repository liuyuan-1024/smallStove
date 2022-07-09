// 定义Store实例并导出，useStore可以是任何东西，比如useUser, useCart
// 第一个参数，唯一不可重复，字符串类型，作为仓库ID 以区分仓库
// 第二个参数，以对象形式配置仓库的state,getters,actions

import {defineStore} from 'pinia';
import LoginUser from "@/types/interface/LoginUser";

// 'LoginUser' 是storeId，自己随便取，保证唯一
export const useLoginUserStore = defineStore('LoginUser', {
    // 定义state的一种方式
    state: () => {
        return {
            loginUser: {
                id: -1,
                username: '',
                nickName: '',
                email: '',
                mobile: '',
                sex: -1,
                avatar: '',
                token: {
                    access_token: '',
                    refresh_token: ''
                }
            }
        }
    },
    // 支持同步,异步
    actions: {
        // 查询本人信息并肩信息设置到pinia中管理
        async set(loginUser: LoginUser) {
            try {
                this.loginUser = loginUser
                // 将本人信息存储到sessionStorage中, 防止用户刷新页面导致个人信息丢失
                sessionStorage.setItem('loginUser', JSON.stringify(loginUser))
            } catch (error) {
                console.log('useLoginUserStore出错!', error)
            }
        },
        // 当本人信息发生改变时, 重新设置本人信息
        reset() {
            try {
                const item = sessionStorage.getItem('loginUser');
                if (item === null || item === undefined || item === '') {
                    // sessionStorage中个人信息为空, 清空localStorage的令牌, 让用户重新登录
                    localStorage.clear()
                } else {
                    this.loginUser = JSON.parse(item)
                }
            } catch (error) {
                console.log('useLoginUserStore出错!', error)
            }
        }
    }
})