import { axios } from '@/utils/request'

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
    method: 'GET'
  })
}

export function List (parameter) {
  return axios({
    url: api.List,
    method: 'get',
    params: parameter
  })
}

export function ListById (id, parameter) {
  return axios({
    url: `${api.List}/${id}`,
    method: 'get',
    params: parameter
  })
}

export function Add (parameter) {
  return axios({
    url: api.Update,
    method: 'POST',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
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

export function Edit (parameter) {
  return axios({
    url: api.Update,
    method: 'PUT',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
