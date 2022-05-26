import { axios, method } from '@/utils/request'

const api = {
  List: '/roles',
  Update: '/role',
  BatchDel: '/roles'
}

export function GetMenuIds (roleId) {
  return axios({
    url: `${api.Update}/${roleId}/menuIds`,
    method: method.GET
  })
}

export function List (parameter) {
  return axios({
    url: api.List,
    method: method.GET,
    params: parameter
  })
}

export function Add (parameter) {
  return axios({
    url: api.Update,
    method: method.POST,
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function Del (parameter) {
  return axios({
    url: api.Update,
    method: method.DELETE,
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function BatchDel (parameter) {
  return axios({
    url: api.BatchDel,
    method: method.DELETE,
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function Edit (parameter) {
  return axios({
    url: api.Update,
    method: method.PUT,
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function AssignMenu (roleId, menuIds) {
  return axios({
    url: `${api.Update}/${roleId}/menus`,
    method: method.POST,
    data: menuIds,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
