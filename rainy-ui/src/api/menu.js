import { axios, method } from '@/utils/request'

const api = {
  Menus: '/menus/antdv',
  MenuList: '/menus/tree',
  MenuTree: '/menus/tree',
  Update: '/menu',
  Export: '/menus/export'
}

export function GetMenuTree (parameter) {
  return axios({
    url: api.MenuTree,
    method: method.GET,
    params: parameter
  })
}

export function List (parameter) {
  return axios({
    url: api.MenuList,
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

export function Export () {
  return axios({
    url: api.Export,
    method: method.GET,
    responseType: 'blob'
  })
}
