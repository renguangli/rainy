import router from './router'
import store from './store'
import storage from 'store'
import NProgress from 'nprogress' // progress bar
import '@/components/NProgress/nprogress.less' // progress bar custom style
// import notification from 'ant-design-vue/es/notification'
import { setDocumentTitle, domTitle } from '@/utils/domUtil'
import { ACCESS_TOKEN, APP_CODE, APP_CODE_DEFAULT } from '@/store/mutation-types'
import { i18nRender } from '@/locales'
import { getRedirectUrl } from '@/api/sso'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const allowList = ['login', 'register', 'activate', 'registerResult'] // no redirect allowList
const loginRoutePath = '/user/login'
const defaultRoutePath = '/'

router.beforeEach((to, from, next) => {
  NProgress.start() // start progress bar
  to.meta && typeof to.meta.title !== 'undefined' && setDocumentTitle(`${i18nRender(to.meta.title)} - ${store.getters.title || domTitle}`)
  /* has token */
  if (storage.get(ACCESS_TOKEN)) {
    if (to.path === loginRoutePath) {
      const redirectUrl = to.query.redirect
      // 如果重定向地址为 http 开头
      if (redirectUrl && redirectUrl.startsWith('http')) {
        getRedirectUrl({ redirect: redirectUrl }).then((res) => {
          if (res.success) {
            location = res.data
          } else {
            // 失败时，调用登出，来清空历史保留信息
            store.dispatch('Logout').then(() => {
              next({ path: loginRoutePath, query: { redirect: redirectUrl } })
            })
          }
        })
      } else {
        next({ path: defaultRoutePath })
        NProgress.done()
      }
    } else {
      // check login user.roles is null
      if (store.getters.addRouters.length === 0) {
        // alert(store.getters.roles.length)
        // request login userInfo
        store
          .dispatch('GetInfo')
          .then(res => {
            // generate dynamic router
            const appCode = storage.get(APP_CODE, APP_CODE_DEFAULT)
            store.dispatch('GenerateRoutes', appCode).then(() => {
              // 根据roles权限生成可访问的路由表
              // 动态添加可访问路由表
              // VueRouter@3.5.0+ New API
              store.getters.addRouters.forEach(r => {
                router.addRoute(r)
              })
              // 请求带有 redirect 重定向时，自动重定向到该地址
              const redirect = decodeURIComponent(from.query.redirect || to.path)
              if (to.path === redirect) {
                const matchRouter = router.match(to.path)
                if (matchRouter.path !== '/404') {
                  next({ ...to, replace: true })
                } else {
                  next({ path: defaultRoutePath })
                }
              } else {
                // 跳转到目的路由
                next({ path: redirect })
              }
            })
          })
          .catch(() => {
            // notification.error({
            //   message: '错误',
            //   description: '请求用户信息失败，请重试'
            // })
            // 失败时，获取用户信息失败时，调用登出，来清空历史保留信息
            store.dispatch('Logout').then(() => {
              next({ path: loginRoutePath, query: { redirect: to.fullPath } })
            })
          })
      } else {
        next()
      }
    }
  } else {
    if (allowList.includes(to.name)) {
      // 在免登录名单，直接进入
      next()
    } else {
      next({ path: loginRoutePath, query: { redirect: to.fullPath } })
      NProgress.done() // if current page is login will not trigger afterEach hook, so manually handle it
    }
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})
