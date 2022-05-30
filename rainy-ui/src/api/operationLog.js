import { axios, method } from '@/utils/request'

const api = {
  List: '/operationLogs',
  Update: '/operationLog',
  BatchDel: '/operationLogs',
  Clear: '/operationLogs/clear'
}

export function List (parameter) {
  return axios({
    url: api.List,
    method: method.GET,
    params: parameter
  })
}

export function Del (id) {
  return axios({
    url: `${api.Update}/${id}`,
    method: method.DELETE
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

export function Clear () {
  return axios({
    url: api.Clear,
    method: method.DELETE,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
