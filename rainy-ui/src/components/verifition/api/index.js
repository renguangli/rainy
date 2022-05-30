/* eslint-disable */
// import request from './../utils/axios' // 组件内部封装的axios
import { axios, method } from '@/utils/request' // 组件内部封装的axios

// 获取验证图片  以及token
export function reqGet (data) {
  return axios({
    url: '/captcha/get',
    method: method.POST,
    data: data,
  })
}

// 滑动或者点选验证
export function reqCheck (data) {
  return axios({
    url: '/captcha/check',
    method: method.POST,
    data: data,
    header
  })
}
