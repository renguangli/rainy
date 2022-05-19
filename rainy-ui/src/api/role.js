import { axios } from '@/utils/request'

const api = {
  List: '/roles',
  Update: '/role',
  BatchDel: '/roles'
}

export function GetMenuIds (roleId) {
  return axios({
    url: `${api.Update}/${roleId}/menuIds`,
    method: 'get'
  })
}

export function List (parameter) {
  return axios({
    url: api.List,
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

export function BatchDel (parameter) {
  return axios({
    url: api.BatchDel,
    method: 'delete',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
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

export function AssignMenu (roleId, menuIds) {
  return axios({
    url: `${api.Update}/${roleId}/menus`,
    method: 'POST',
    data: menuIds,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
