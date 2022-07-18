import { axios, method } from '@/utils/request'

const api = {
  List: '/powerForecasts',
  Update: '/powerForecasts'
}

export function List (parameter) {
  return axios({
    url: api.List,
    method: method.GET,
    params: parameter
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
