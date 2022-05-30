import { axios, method } from '@/utils/request'

const api = {
  // 字典
  FrontConfig: '/common/config',
  AvatarUpload: '/avatar'
}

export function FrontConfig () {
  return axios({
    url: api.FrontConfig,
    method: method.GET
  })
}

export function AvatarUpload (parameter) {
  return axios({
    url: api.AvatarUpload,
    method: method.POST,
    data: parameter,
    headers: { contentType: false, processData: false, headers: { 'Content-Type': 'application/x-www-form-urlencoded' } }
  })
}
