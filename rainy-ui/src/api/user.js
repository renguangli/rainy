import { axios } from '@/utils/request'

const api = {
  List: '/users',
  Update: '/user',
  BatchDel: '/users'
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

export function GetRoleIds (userId) {
  return axios({
    url: `${api.Update}/${userId}/roleIds`,
    method: 'GET'
  })
}

export function AssignRoles (userId, roleIds) {
  return axios({
    url: `${api.Update}/${userId}/roles`,
    method: 'POST',
    data: roleIds,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function ResetPassword (id) {
  return axios({
    url: `${api.Update}/${id}/password/reset`,
    method: 'put'
  })
}
