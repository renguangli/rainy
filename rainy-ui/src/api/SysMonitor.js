import { axios } from '@/utils/request'

const api = {
  SysMonitor: '/sys/monitor',
  UserOnline: '/users/online',
  KickOut: '/user',
  BatchKickOut: '/users/kickOut'
}

export function SysMonitor () {
  return axios({
    url: api.SysMonitor,
    method: 'get'
  })
}

export function OnlineUser (parameter) {
  return axios({
    url: api.UserOnline,
    method: 'get',
    params: parameter
  })
}

export function KickOut (userId) {
  return axios({
    url: `${api.KickOut}/${userId}/kickOut`,
    method: 'put'
  })
}

export function BatchKickOut (parameter) {
  return axios({
    url: api.BatchKickOut,
    method: 'put',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
