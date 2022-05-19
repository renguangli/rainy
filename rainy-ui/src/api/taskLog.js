import { axios } from '@/utils/request'

const api = {
  // 定时任务
  List: '/taskLogs',
  Update: '/taskLog',
  BatchDel: '/taskLogs',
  Clear: '/taskLogs/clear'
}

export function List (parameter) {
  return axios({
    url: api.List,
    method: 'get',
    params: parameter
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

export function Clear () {
  return axios({
    url: api.Clear,
    method: 'delete',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
