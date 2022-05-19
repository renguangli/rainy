import { axios } from '@/utils/request'

const api = {
  // 字典
  SsoRedirectUrl: '/sso/redirect'
}

export function getRedirectUrl (parameter) {
  return axios({
    url: api.SsoRedirectUrl,
    method: 'GET',
    params: parameter
  })
}
