import { axios, method } from '@/utils/request'

const api = {
  List: '/resources',
  ListTree: '/resources/tree',
  Update: '/resource',
  BatchDel: '/resources',
  Export: '/resources/export'
}

export function List (parameter) {
  return axios({
    url: api.ListTree,
    method: method.GET,
    params: parameter
  })
}

export function ListTree (parameter) {
  return axios({
    url: api.ListTree,
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

export function Export () {
  return axios({
    url: api.Export,
    method: method.GET,
    responseType: 'blob'
  })
}
