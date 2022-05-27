import { axios, method } from '@/utils/request'

const api = {
  SysMonitor: '/sys/monitor',
  UserOnline: '/users/online',
  KickOut: '/user',
  BatchKickOut: '/users/kickOut'
}

export function SysMonitor () {
  return axios({
    url: api.SysMonitor,
    method: method.GET
  })
}

export function OnlineUser (parameter) {
  return axios({
    url: api.UserOnline,
    method: method.GET,
    params: parameter
  })
}

export function KickOut (parameter) {
  return axios({
    url: `${api.KickOut}/kickOut`,
    method: method.PUT,
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
export function BatchKickOut (parameter) {
  return axios({
    url: api.BatchKickOut,
    method: method.PUT,
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
