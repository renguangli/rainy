import { axios, method } from '@/utils/request'

const userApi = {
  Login: '/auth/login',
  Logout: '/auth/logout',
  ForgePassword: '/auth/forge-password',
  Register: '/auth/register',
  twoStepCode: '/auth/2step-code',
  SendSms: '/account/sms',
  SendSmsErr: '/account/sms_err',
  // get my info
  UserInfo: '/user/info',
  UserMenu: '/menus/antdv'
}

const api = {
  Login: '/sso/login',
  Logout: '/sso/logout',
  Register: '/register',
  Activate: '/activate',
  ForgePassword: '/password/forget',
  // get my info
  UserInfo: '/userinfo',
  UserMenu: '/menus/antdv'
}

/**
 * login func
 * parameter: {
 *     username: '',
 *     password: '',
 *     remember_me: true,
 *     captcha: '12345'
 * }
 * @param parameter
 * @returns {*}
 */
export function login (parameter) {
  return axios({
    url: api.Login,
    method: method.POST,
    data: parameter
  })
}

export function getSmsCaptcha (parameter) {
  return axios({
    url: userApi.SendSms,
    method: method.POST,
    data: parameter
  })
}

export function getInfo () {
  return axios({
    url: api.UserInfo,
    method: method.GET
  })
}

export function getCurrentUserNav (parameter) {
  return axios({
    url: userApi.UserMenu,
    method: method.GET,
    params: parameter
  })
}

export function logout () {
  return axios({
    url: api.Logout,
    method: method.POST,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function Register (parameter) {
  return axios({
    url: api.Register,
    method: method.POST,
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function Activate (token) {
  return axios({
    url: `${api.Activate}/${token}`,
    method: method.POST,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * get user 2step code open?
 * @param parameter {*}
 */
export function get2step (parameter) {
  return axios({
    url: userApi.twoStepCode,
    method: method.POST,
    data: parameter
  })
}
