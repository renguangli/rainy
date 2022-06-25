import store from '@/store'
import storage from 'store'
import {
  TITLE,
  ACCESS_TOKEN,
  APP_LANGUAGE,
  TOGGLE_CONTENT_WIDTH,
  TOGGLE_FIXED_HEADER,
  TOGGLE_FIXED_SIDEBAR, TOGGLE_HIDE_HEADER,
  TOGGLE_LAYOUT, TOGGLE_NAV_THEME, TOGGLE_WEAK,
  TOGGLE_COLOR, TOGGLE_MULTI_TAB, SIDEBAR_TYPE, LOGO
} from '@/store/mutation-types'
// import { printANSI } from '@/utils/screenLog'
import defaultSettings from '@/config/defaultSettings'

export default function Initializer () {
  // printANSI() // 请自行移除该行.  please remove this line
  store.dispatch('setLang', storage.get(APP_LANGUAGE, 'zh-CN'))
  store.dispatch('setDictTree')

  store.commit(SIDEBAR_TYPE, storage.get(SIDEBAR_TYPE, defaultSettings.sidebarType))
  store.commit(TITLE, storage.get(TITLE, defaultSettings.title))
  store.commit(LOGO, storage.get(LOGO, defaultSettings.logo))
  store.commit(TOGGLE_LAYOUT, storage.get(TOGGLE_LAYOUT, defaultSettings.layout))
  store.commit(TOGGLE_FIXED_HEADER, storage.get(TOGGLE_FIXED_HEADER, defaultSettings.fixedHeader))
  store.commit(TOGGLE_FIXED_SIDEBAR, storage.get(TOGGLE_FIXED_SIDEBAR, defaultSettings.fixSiderbar))
  store.commit(TOGGLE_CONTENT_WIDTH, storage.get(TOGGLE_CONTENT_WIDTH, defaultSettings.contentWidth))
  store.commit(TOGGLE_HIDE_HEADER, storage.get(TOGGLE_HIDE_HEADER, defaultSettings.autoHideHeader))
  store.commit(TOGGLE_NAV_THEME, storage.get(TOGGLE_NAV_THEME, defaultSettings.navTheme))
  store.commit(TOGGLE_WEAK, storage.get(TOGGLE_WEAK, defaultSettings.colorWeak))
  store.commit(TOGGLE_COLOR, storage.get(TOGGLE_COLOR, defaultSettings.primaryColor))
  store.commit(TOGGLE_MULTI_TAB, storage.get(TOGGLE_MULTI_TAB, defaultSettings.multiTab))
  store.commit('SET_TOKEN', storage.get(ACCESS_TOKEN))

  // last step
}
