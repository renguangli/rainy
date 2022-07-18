import { axios, method } from '@/utils/request'

const api = {
  ListDatabases: '/db/databases',
  ListTables: '/db/tables',
  ListColumns: '/db/columns'
}

export function ListDatabases () {
  return axios({
    url: api.ListDatabases,
    method: method.GET
  })
}

export function ListTables (parameter) {
  return axios({
    url: api.ListTables,
    method: method.GET,
    params: parameter
  })
}

export function ListColumns (parameter) {
  return axios({
    url: api.ListColumns,
    method: method.GET,
    params: parameter
  })
}
