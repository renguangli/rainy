import { axios, method } from '@/utils/request'

const api = {
  List: '/apps',
  Update: '/app',
  BatchDel: '/apps',
  Export: '/apps/export',
  Disable: '/app/disable',
  Enable: '/app/enable'
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

export function Disable (parameter) {
  return axios({
    url: api.Disable,
    method: method.PUT,
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function Enable (parameter) {
  return axios({
    url: api.Enable,
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
