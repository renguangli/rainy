import { axios } from '@/utils/request'

const api = {
  // 定时任务
  List: '/tasks',
  Update: '/task',
  BatchDel: '/tasks'
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
    url: api.Update,
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

export function Pause (id) {
  return axios({
    url: `${api.Update}/${id}/pause`,
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function Resume (id) {
  return axios({
    url: `${api.Update}/${id}/resume`,
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
