import { axios, method } from '@/utils/request'

const api = {
  user: '/user',
  role: '/role',
  service: '/service',
  permission: '/permission',
  permissionNoPager: '/permission/no-pager',
  orgTree: '/org/tree'
}

export default api

export function getUserList (parameter) {
  return axios({
    url: api.user,
    method: method.GET,
    params: parameter
  })
}

export function getRoleList (parameter) {
  return axios({
    url: api.role,
    method: method.GET,
    params: parameter
  })
}

export function getServiceList (parameter) {
  return axios({
    url: api.service,
    method: method.GET,
    params: parameter
  })
}

export function getPermissions (parameter) {
  return axios({
    url: api.permissionNoPager,
    method: method.GET,
    params: parameter
  })
}

export function getOrgTree (parameter) {
  return axios({
    url: api.orgTree,
    method: method.GET,
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveService (parameter) {
  return axios({
    url: api.service,
    method: parameter.id === 0 ? method.GET : method.PUT,
    data: parameter
  })
}

export function saveSub (sub) {
  return axios({
    url: '/sub',
    method: sub.id === 0 ? method.POST : method.PUT,
    data: sub
  })
}
