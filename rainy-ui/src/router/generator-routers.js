// eslint-disable-next-line
import * as loginService from '@/api/login'
// eslint-disable-next-line
import {BasicLayout, BlankLayout, Iframe, PageView, RouteView} from '@/layouts'

// 前端路由表
const constantRouterComponents = {
  // 基础页面 layout 必须引入
  BasicLayout: BasicLayout,
  BlankLayout: BlankLayout,
  RouteView: RouteView,
  PageView: PageView,
  Iframe: Iframe,
  '403': () => import(/* webpackChunkName: "error" */ '@/views/exception/403'),
  '404': () => import(/* webpackChunkName: "error" */ '@/views/exception/404'),
  '500': () => import(/* webpackChunkName: "error" */ '@/views/exception/500'),

  // 你需要动态引入的页面组件
  Workplace: () => import('@/views/dashboard/Workplace'),
  Analysis: () => import('@/views/dashboard/Analysis'),

  // form
  BasicForm: () => import('@/views/form/basicForm'),
  StepForm: () => import('@/views/form/stepForm/StepForm'),
  AdvanceForm: () => import('@/views/form/advancedForm/AdvancedForm'),

  // list
  TableList: () => import('@/views/list/TableList'),
  StandardList: () => import('@/views/list/BasicList'),
  CardList: () => import('@/views/list/CardList'),
  SearchLayout: () => import('@/views/list/search/SearchLayout'),
  SearchArticles: () => import('@/views/list/search/Article'),
  SearchProjects: () => import('@/views/list/search/Projects'),
  SearchApplications: () => import('@/views/list/search/Applications'),
  ProfileBasic: () => import('@/views/profile/basic'),
  ProfileAdvanced: () => import('@/views/profile/advanced/Advanced'),

  // result
  ResultSuccess: () => import(/* webpackChunkName: "result" */ '@/views/result/Success'),
  ResultFail: () => import(/* webpackChunkName: "result" */ '@/views/result/Error'),

  // exception
  Exception403: () => import(/* webpackChunkName: "fail" */ '@/views/exception/403'),
  Exception404: () => import(/* webpackChunkName: "fail" */ '@/views/exception/404'),
  Exception500: () => import(/* webpackChunkName: "fail" */ '@/views/exception/500'),

  // account
  AccountCenter: () => import('@/views/account/center'),
  AccountSettings: () => import('@/views/account/settings/Index'),
  BasicSettings: () => import('@/views/account/settings/Basic'),
  SecuritySettings: () => import('@/views/account/settings/Security'),
  CustomSettings: () => import('@/views/account/settings/Custom'),
  BindingSettings: () => import('@/views/account/settings/Binding'),
  NotificationSettings: () => import('@/views/account/settings/Notification')

  // 'TestWork': () => import(/* webpackChunkName: "TestWork" */ '@/views/dashboard/TestWork')
}

// 前端未找到页面路由（固定不用改）
const notFoundRouter = {
  path: '*',
  redirect: '/404',
  hidden: true
}

// account
const accountNav = [
    {
      name: 'account',
      parentId: 0,
      id: 10028,
      meta: {
        title: '用户设置',
        icon: 'user',
        show: false
      },
      redirect: '/account/center',
      component: 'RouteView'
    },
  {
    name: 'center',
    parentId: 10028,
    id: 10029,
    meta: {
      title: '个人中心',
      show: true
    },
    component: 'AccountCenter'
  },
  // 特殊三级菜单
  {
    name: 'settings',
    parentId: 10028,
    id: 10030,
    meta: {
      title: '个人设置',
      hideHeader: true,
      hideChildren: true,
      show: true
    },
    redirect: '/account/settings/basic',
    component: 'AccountSettings'
  },
  {
    name: 'BasicSettings',
    path: '/account/settings/basic',
    parentId: 10030,
    id: 10031,
    meta: {
      title: '基本设置',
      show: false
    },
    component: 'BasicSettings'
  },
  {
    name: 'SecuritySettings',
    path: '/account/settings/security',
    parentId: 10030,
    id: 10032,
    meta: {
      title: '安全设置',
      show: false
    },
    component: 'SecuritySettings'
  },
  {
    name: 'CustomSettings',
    path: '/account/settings/custom',
    parentId: 10030,
    id: 10033,
    meta: {
      title: '个性化设置',
      show: false
    },
    component: 'CustomSettings'
  },
  {
    name: 'BindingSettings',
    path: '/account/settings/binding',
    parentId: 10030,
    id: 10034,
    meta: {
      title: '账户绑定',
      show: false
    },
    component: 'BindingSettings'
  },
  {
    name: 'NotificationSettings',
    path: '/account/settings/notification',
    parentId: 10030,
    id: 10034,
    meta: {
      title: '新消息通知',
      show: false
    },
    component: 'NotificationSettings'
  }
]

// 根级菜单
const rootRouter = {
  key: '',
  name: 'index',
  path: '',
  component: 'BasicLayout',
  redirect: '/account/center',
  meta: {
    title: '首页'
  },
  children: []
}

/**
 * 动态生成菜单
 * @param token
 * @returns {Promise<Router>}
 */
export const generatorDynamicRouter = () => {
  return new Promise((resolve, reject) => {
    loginService
      .getCurrentUserNav()
      .then(res => {
        // console.log('generatorDynamicRouter response:', res)
        const { data } = res
        const menuNav = []
        const childrenNav = []
        // 后端数据, 根级树数组,  根级 PID
        listToTree(data, childrenNav, 0)
        // account, 根级树数组,  根级 PID
        listToTree(accountNav, childrenNav, 0)
        rootRouter.children = childrenNav
        menuNav.push(rootRouter)
        // console.log('menuNav', menuNav)
        const routers = generator(menuNav)
        routers.push(notFoundRouter)
        // console.log('routers', routers)
        resolve(routers)
      })
      .catch(err => {
        reject(err)
      })
  })
}

/**
 * 格式化树形结构数据 生成 vue-router 层级路由表
 *
 * @param routerMap
 * @param parent
 * @returns {*}
 */
export const generator = (routerMap, parent) => {
  return routerMap.map(item => {
    const { title, show, hideChildren, hiddenHeaderContent, target, icon, url } = item.meta || {}
    let _target = null
      if (target === '_blank') {
        _target = '_blank'
      }
    const currentRouter = {
      // 如果路由设置了 path，则作为默认 path，否则 路由地址 动态拼接生成如 /dashboard/workplace
      path: encodeURI(item.url || item.path || `${(parent && parent.path) || ''}/${item.key}`),
      // 路由名称，建议唯一
      name: item.name || item.key || '',
      // 该路由对应页面的 组件 :方案1
      // component: constantRouterComponents[item.component || item.key],
      // 该路由对应页面的 组件 :方案2 (动态加载)
      component: constantRouterComponents[item.component || item.key] || (() => import(`@/views/${item.component}`)),

      // meta: 页面标题, 菜单图标, 页面权限(供指令权限用，可去掉)
      meta: {
        title: title,
        icon: icon || undefined,
        hiddenHeaderContent: hiddenHeaderContent,
        target: _target,
        permission: item.name,
        keepAlive: true,
        url: url
      }
    }
    // 是否设置了隐藏菜单
    if (show === false) {
      currentRouter.hidden = true
    }
    // 是否设置了隐藏子菜单
    if (hideChildren) {
      currentRouter.hideChildrenInMenu = true
    }
    // 为了防止出现后端返回结果不规范，处理有可能出现拼接出两个 反斜杠
    if (!currentRouter.path.startsWith('http')) {
      currentRouter.path = currentRouter.path.replace('//', '/')
    }
    // 重定向
    item.redirect && (currentRouter.redirect = item.redirect)
    // 是否有子菜单，并递归处理
    if (item.children && item.children.length > 0) {
      // Recursion
      currentRouter.children = generator(item.children, currentRouter)
    }
    return currentRouter
  })
}

/**
 * 数组转树形结构
 * @param list 源数组
 * @param tree 树
 * @param parentId 父ID
 */
const listToTree = (list, tree, parentId) => {
  list.forEach(item => {
    // 判断是否为父级菜单
    if (item.parentId === parentId) {
      const child = {
        ...item,
        key: item.key || item.name,
        children: []
      }
      // 迭代 list， 找到当前菜单相符合的所有子菜单
      listToTree(list, child.children, item.id)
      // 删掉不存在 children 值的属性
      if (child.children.length <= 0) {
        delete child.children
      }
      // 加入到树中
      tree.push(child)
    }
  })
}
