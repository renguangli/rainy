import storage from 'store'
import { FrontConfig } from '@/api/common'
import {
  SIDEBAR_TYPE,
  TOGGLE_MOBILE_TYPE,
  TOGGLE_NAV_THEME,
  TOGGLE_LAYOUT,
  TOGGLE_FIXED_HEADER,
  TOGGLE_FIXED_SIDEBAR,
  TOGGLE_CONTENT_WIDTH,
  TOGGLE_HIDE_HEADER,
  TOGGLE_COLOR,
  TOGGLE_WEAK,
  TOGGLE_MULTI_TAB,
  // i18n
  APP_LANGUAGE,
  DICT_TREE,
  TITLE,
  LOGO,
  SYS_DESCRIPTION,
  CAPTCHA_ENABLE, APPS
} from '@/store/mutation-types'
import { loadLanguageAsync } from '@/locales'

const app = {
  state: {
    sideCollapsed: false,
    isMobile: false,
    theme: 'dark',
    layout: '',
    contentWidth: '',
    fixedHeader: false,
    fixedSidebar: false,
    autoHideHeader: false,
    color: '',
    weak: false,
    multiTab: true,
    lang: 'zh-CN',
    _antLocale: {},
    dictTree: {},
    title: null,
    logo: null,
    captchaEnable: false,
    apps: []
  },
  mutations: {
    [SIDEBAR_TYPE]: (state, type) => {
      state.sideCollapsed = type
      storage.set(SIDEBAR_TYPE, type)
    },
    [TOGGLE_MOBILE_TYPE]: (state, isMobile) => {
      state.isMobile = isMobile
    },
    [TOGGLE_NAV_THEME]: (state, theme) => {
      state.theme = theme
      storage.set(TOGGLE_NAV_THEME, theme)
    },
    [TOGGLE_LAYOUT]: (state, mode) => {
      state.layout = mode
      storage.set(TOGGLE_LAYOUT, mode)
    },
    [TOGGLE_FIXED_HEADER]: (state, mode) => {
      state.fixedHeader = mode
      storage.set(TOGGLE_FIXED_HEADER, mode)
    },
    [TOGGLE_FIXED_SIDEBAR]: (state, mode) => {
      state.fixedSidebar = mode
      storage.set(TOGGLE_FIXED_SIDEBAR, mode)
    },
    [TOGGLE_CONTENT_WIDTH]: (state, type) => {
      state.contentWidth = type
      storage.set(TOGGLE_CONTENT_WIDTH, type)
    },
    [TOGGLE_HIDE_HEADER]: (state, type) => {
      state.autoHideHeader = type
      storage.set(TOGGLE_HIDE_HEADER, type)
    },
    [TOGGLE_COLOR]: (state, color) => {
      state.color = color
      storage.set(TOGGLE_COLOR, color)
    },
    [TOGGLE_WEAK]: (state, mode) => {
      state.weak = mode
      storage.set(TOGGLE_WEAK, mode)
    },
    [APP_LANGUAGE]: (state, lang, antd = {}) => {
      state.lang = lang
      state._antLocale = antd
      storage.set(APP_LANGUAGE, lang)
    },
    [TOGGLE_MULTI_TAB]: (state, bool) => {
      storage.set(TOGGLE_MULTI_TAB, bool)
      state.multiTab = bool
    },
    [DICT_TREE]: (state, dictTree) => {
      state.dictTree = dictTree
      // ?????????????????????
    },
    [TITLE]: (state, title) => {
      state.title = title
    },
    [LOGO]: (state, logo) => {
      state.logo = logo
    },
    [SYS_DESCRIPTION]: (state, sysDesciption) => {
      state.sys_desciption = sysDesciption
    },
    [CAPTCHA_ENABLE]: (state, captchaEnable) => {
      state.captchaEnable = captchaEnable
    },
    [APPS]: (state, apps) => {
      state.apps = apps
    }
  },
  actions: {
    setLang ({ commit }, lang) {
      return new Promise((resolve, reject) => {
        commit(APP_LANGUAGE, lang)
        loadLanguageAsync(lang).then(() => {
          resolve()
        }).catch((e) => {
          reject(e)
        })
      })
    },
    setDictTree ({ commit }) {
      return new Promise((resolve, reject) => {
        FrontConfig().then(response => {
          const data = response.data
          commit(DICT_TREE, data.dictTree)
          commit(TITLE, data.config.SYS_TITLE)
          commit(LOGO, data.config.SYS_LOGO)
          commit(SYS_DESCRIPTION, data.config.SYS_DESCRIPTION)
          commit(CAPTCHA_ENABLE, data.config.CAPTCHA_ENABLE)
          commit(APPS, data.apps)
          resolve(data)
        }).catch(error => {
          reject(error)
        })
      })
    }
  }
}

export default app
