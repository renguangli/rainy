import { axios, method } from '@/utils/request'

const api = {
  List: '/configs',
  Update: '/config',
  BatchDel: '/configs'
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
    url: `${api.Update}`,
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
