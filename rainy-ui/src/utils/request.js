import axios from 'axios'
import store from '@/store'
import storage from 'store'
import notification from 'ant-design-vue/es/notification'
import { VueAxios } from './axios'
import { ACCESS_TOKEN } from '@/store/mutation-types'
// import { Base64 } from 'js-base64'
export const method = {
  GET: 'get',
  POST: 'post',
  DELETE: 'delete',
  PUT: 'put'
}

// 创建 axios 实例
const request = axios.create({
  // API 请求的默认前缀
  baseURL: process.env.VUE_APP_API_BASE_URL,
  timeout: 6000 // 请求超时时间
})

// 异常拦截处理器
const errorHandler = (error) => {
  return Promise.reject(error)
}

// request interceptor
request.interceptors.request.use(config => {
  const token = storage.get(ACCESS_TOKEN)
  // 如果 token 存在，让每个请求携带自定义 token 请根据实际情况自行修改
  if (token) {
    config.headers['Access-Token'] = token
  }
  return config
}, errorHandler)

// response interceptor
request.interceptors.response.use((response) => {
  if (response.request.responseType === 'blob') {
    return response
  }
  const data = response.data
  if (data.code === 401) {
    notification.error({
      message: '认证失败',
      description: data.message
    })
    store.dispatch('Logout').then(() => {
      setTimeout(() => {
        if (!window.location.pathname.endsWith('/user/login')) {
          window.location.reload()
        }
      }, 500)
    })
  }
  if (data.code === 400) {
    notification.error({
      message: '参数检验失败',
      description: data.message
    })
  }
  if (data.code === 403) {
    notification.error({
      message: '权限不足',
      description: data.message
    })
  }
  if (data.code === 500) {
    notification.error({
      message: '系统错误',
      description: data.message
    })
  }
  return data
}, errorHandler)

const installer = {
  vm: {},
  install (Vue) {
    Vue.use(VueAxios, request)
  }
}

export default request

export {
  installer as VueAxios,
  request as axios
}
