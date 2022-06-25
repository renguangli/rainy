const getters = {
  sideCollapsed: state => state.app.sideCollapsed,
  layout: state => state.app.layout,
  isMobile: state => state.app.isMobile,
  lang: state => state.app.lang,
  theme: state => state.app.theme,
  fixedHeader: state => state.app.fixedHeader,
  fixSiderbar: state => state.app.fixSiderbar,
  colorWeak: state => state.app.weak,
  primaryColor: state => state.app.color,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  nickname: state => state.user.name,
  welcome: state => state.user.welcome,
  roles: state => state.user.roles,
  userInfo: state => state.user.info,
  addRouters: state => state.permission.addRouters,
  multiTab: state => state.app.multiTab,
  dictTree: state => state.app.dictTree,
  getDicts: (state) => () => {
    const dictTree = state.app.dictTree
    const dicts = []
    for (const dictCode in dictTree) {
      const name = dictTree[dictCode].name
      const nameVal = { name: name, dictCode: dictCode }
      dicts.push(nameVal)
    }
    return dicts
  },
  title: state => state.app.title,
  logo: state => state.app.logo,
  sysDescription: state => state.app.sys_desciption,
  captchaEnable: state => state.app.captchaEnable,
  apps: state => state.app.apps
}

export default getters
