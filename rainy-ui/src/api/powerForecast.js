import { axios, method } from '@/utils/request'

const api = {
  List: '/powerForecasts'
}

export function List (parameter) {
  return axios({
    url: api.List,
    method: method.GET,
    params: parameter
  })
}
