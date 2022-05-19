import storage from 'store'
import { login, getInfo, logout } from '@/api/login'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import { welcome } from '@/utils/util'
import notification from 'ant-design-vue/es/notification'

const user = {
  state: {
    token: '',
    name: '',
    welcome: '',
    avatar: '',
    roles: [],
    info: {}
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, { name, welcome }) => {
      state.name = name
      state.welcome = welcome
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_INFO: (state, info) => {
      state.info = info
    }
  },

  actions: {
    // 登录
    Login ({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo).then(res => {
          if (res.success) {
            storage.set(ACCESS_TOKEN, res.data.tokenValue, res.data.tokenTimeout)
            commit('SET_TOKEN', res.data.tokenValue)
            resolve()
          } else {
            reject(res)
          }
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo ({ commit }) {
      return new Promise((resolve, reject) => {
        getInfo().then(res => {
          const result = res.data
          commit('SET_NAME', { name: result.name, welcome: welcome() })
          commit('SET_AVATAR', process.env.VUE_APP_API_BASE_URL + result.avatar)
          commit('SET_ROLES', result.roles)
          commit('SET_INFO', result)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    Logout ({ commit, state }) {
      return new Promise((resolve) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          storage.remove(ACCESS_TOKEN)
          commit('SET_NAME', '')
          commit('SET_AVATAR', '')
          commit('SET_ROLES', [])
          commit('SET_ROUTERS', [])
          resolve()
        }).catch((err) => {
          notification.error({
            message: '错误',
            description: '退出失败:' + err
          })
          // console.log('logout fail:', err)
          // resolve()
        }).finally(() => {
        })
      })
    }

  }
}

export default user
