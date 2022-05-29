const getters = {
  isMobile: state => state.app.isMobile,
  lang: state => state.app.lang,
  theme: state => state.app.theme,
  color: state => state.app.color,
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
  captchaEnable: state => state.app.captchaEnable
}

export default getters
