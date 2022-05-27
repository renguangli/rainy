import { axios, method } from '@/utils/request'

const api = {
  // 组织
  OrgTree: '/orgs/tree',
  List: '/orgs',
  Update: '/org',
  BatchDel: '/orgs'
}

export function GetOrgTree () {
  return axios({
    url: api.OrgTree,
    method: method.GET
  })
}

export function List (parameter) {
  return axios({
    url: api.List,
    method: method.GET,
    params: parameter
  })
}

export function ListById (id, parameter) {
  return axios({
    url: `${api.List}/${id}`,
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
