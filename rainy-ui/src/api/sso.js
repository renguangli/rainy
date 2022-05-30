import { axios, method } from '@/utils/request'

const api = {
  // 获取重定向地址
  SsoRedirectUrl: '/sso/redirect'
}

export function getRedirectUrl (parameter) {
  return axios({
    url: api.SsoRedirectUrl,
    method: method.GET,
    params: parameter
  })
}
