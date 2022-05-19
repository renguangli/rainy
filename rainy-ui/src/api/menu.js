import { axios } from '@/utils/request'

const api = {
  Menus: '/menus/antdv',
  MenuList: '/menus/tree',
  MenuTree: '/menus/tree',
  Update: '/menu'
}

export function GetMenuTree (parameter) {
  return axios({
    url: api.MenuTree,
    method: 'get',
    params: parameter
  })
}

export function List (parameter) {
  return axios({
    url: api.MenuList,
    method: 'get',
    params: parameter
  })
}

export function Add (parameter) {
  return axios({
    url: api.Update,
    method: 'POST',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function Del (id) {
  return axios({
    url: `${api.Update}/${id}`,
    method: 'delete'
  })
}

export function Edit (parameter) {
  return axios({
    url: api.Update,
    method: 'PUT',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
