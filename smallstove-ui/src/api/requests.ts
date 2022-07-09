import axios from 'axios'
// 引入el 提示框
import {ElMessage} from 'element-plus'

const requests = axios.create({
    // 请求地址
    baseURL: 'http://localhost:8080',
    // 设置接口超时时间, 1.5s
    timeout: 1500,
})

// 请求拦截器
requests.interceptors.request.use(
    (config) => {
        // 配置请求头
        const access_token: string = localStorage.getItem('access_token') || ''
        const refresh_token: string = localStorage.getItem('refresh_token') || ''
        config.headers = {
            //'Content-Type':'application/x-www-form-urlencoded',   // 传参方式表单
            'Content-Type': 'application/json;charset=UTF-8', // 传参方式json
            access_token: access_token,
            refresh_token: refresh_token,
        }
        return config
    },
    (error) => {
        ElMessage.warning('请求拦截异常,请稍后再试!')
        return Promise.reject(error)
    }
)

//http response 拦截器, 会处理所有的错误响应(code是400开头)
requests.interceptors.response.use(
    (res) => {
        // 响应结果的状态(code的前三位), 200正常 400异常
        let status = String(res.data.code).substring(0, 3);
        if (status !== "200") {
            localStorage.removeItem('access_token')
            localStorage.removeItem('refresh_token')
            ElMessage.warning(res.data.message)
        }
        // 返回的就是自定义的ResponseResult
        return res.data
    },
    (error) => {
        ElMessage.warning('请求响应异常,请稍后再试!')
        return Promise.reject(error)
    }
)

export default requests
