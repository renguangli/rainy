import { axios, method } from '@/utils/request'

const api = {
  // 定时任务
  List: '/tasks',
  Update: '/task',
  BatchDel: '/tasks',
  Export: '/tasks/export'
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
    method: 'PUT',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function Pause (parameter) {
  return axios({
    url: `${api.Update}/pause`,
    method: method.PUT,
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function Resume (parameter) {
  return axios({
    url: `${api.Update}/resume`,
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
