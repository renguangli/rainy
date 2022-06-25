<template>
  <pro-layout
    :menus="menus"
    :collapsed="collapsed"
    :collapseWidth="32"
    :mediaQuery="query"
    :isMobile="isMobile"
    :handleMediaQuery="handleMediaQuery"
    :handleCollapse="handleCollapse"
    :i18nRender="i18nRender"
    v-bind="settings"
  >
    <!-- Ads begin
      广告代码 真实项目中请移除
      production remove this Ads
    -->
    <!--    <ads v-if="isProPreviewSite && !collapsed"/>-->
    <!-- Ads end -->

    <!-- 1.0.0+ 版本 pro-layout 提供 API，
          我们推荐使用这种方式进行 LOGO 和 title 自定义
    -->
    <template v-slot:menuHeaderRender>
      <div>
        <img class="logo" :src="logo">
        <h1>{{ title }}</h1>
      </div>
    </template>
    <!-- 1.0.0+ 版本 pro-layout 提供 API,
          增加 Header 左侧内容区自定义
    -->
    <template v-slot:headerContentRender>
      <div>
        <a-menu
          style="border-bottom: 0;"
          mode="horizontal"
          :default-selected-keys="selectedApp"
        >
          <a-tooltip title="刷新页面" >
            <a-icon type="reload" style="font-size: 18px;cursor: pointer;vertical-align: -13px" @click="() => { refresh() }" />
          </a-tooltip>
          <a-menu-item v-for="item in apps" :key="item.code" style="top:0;margin-bottom: -10px; padding-left: 10px; padding-right: 10px" @click="switchApp(item.code)">
            {{ item.name }}
          </a-menu-item>
        </a-menu>
      </div>
    </template>

    <setting-drawer v-if="isDev" :settings="settings" @change="handleSettingChange">
      <a-list-item>
        <a-switch slot="actions" size="small" :defaultChecked="settings.multiTab" @change="handleMultiTabChange" />
        <a-list-item-meta>
          <div slot="title">{{ i18nRender('app.setting.multiTab') }}</div>
        </a-list-item-meta>
      </a-list-item>
    </setting-drawer>
    <template v-slot:rightContentRender>
      <right-content :top-menu="settings.layout === 'topmenu'" :is-mobile="isMobile" :theme="settings.theme" />
    </template>
    <!-- layout content -->
    <template>
      <multi-tab v-if="settings.multiTab"></multi-tab>
    </template>
    <!-- custom footer / 自定义Footer -->
    <template v-slot:footerRender>
      <global-footer />
    </template>
    <router-view />
  </pro-layout>
</template>
<script>
import { SettingDrawer, updateTheme } from '@ant-design-vue/pro-layout'
import { i18nRender } from '@/locales'
import { mapState } from 'vuex'
import { CONTENT_WIDTH_TYPE, SIDEBAR_TYPE, TOGGLE_MOBILE_TYPE, APP_CODE, APP_CODE_DEFAULT } from '@/store/mutation-types'

// import defaultSettings from '@/config/defaultSettings'
import RightContent from '@/components/GlobalHeader/RightContent'
import GlobalFooter from '@/components/GlobalFooter'
import LogoSvg from '../assets/logo.svg?inline'
import message from 'ant-design-vue/lib/message'
import storage from 'store'

export default {
  name: 'BasicLayout',
  inject: ['reload'],
  components: {
    SettingDrawer,
    RightContent,
    GlobalFooter,
    LogoSvg
  },
  data () {
    return {
      // preview.pro.antdv.com only use.
      isProPreviewSite: process.env.VUE_APP_PREVIEW === 'true' && process.env.NODE_ENV !== 'development',
      // end
      isDev: process.env.NODE_ENV === 'development' || process.env.VUE_APP_PREVIEW === 'true',

      // base
      menus: [],
      // 侧栏收起状态
      collapsed: this.$store.getters.sideCollapsed,
      title: this.$store.getters.title,
      logo: this.$store.getters.logo,
      settings: {
        // 布局类型
        layout: this.$store.getters.layout, // 'sidemenu', 'topmenu'
        // CONTENT_WIDTH_TYPE
        contentWidth: this.$store.getters.layout === 'sidemenu' ? CONTENT_WIDTH_TYPE.Fluid : this.$store.getters.contentWidth,
        // 主题 'dark' | 'light'
        theme: this.$store.getters.theme,
        // 主色调
        primaryColor: this.$store.getters.primaryColor,
        fixedHeader: this.$store.getters.fixedHeader,
        fixSiderbar: this.$store.getters.fixSiderbar,
        colorWeak: this.$store.getters.colorWeak,
        multiTab: this.$store.getters.multiTab,
        hideHintAlert: false,
        hideCopyButton: false
      },
      // 媒体查询
      query: {},

      // 是否手机模式
      isMobile: false,
      apps: this.$store.getters.apps,
      selectedApp: []
    }
  },
  computed: {
    ...mapState({
      // 动态主路由
      mainMenu: state => state.permission.addRouters
    })
  },
  created () {
    const routes = this.mainMenu.find(item => item.path === '/')
    this.menus = (routes && routes.children) || []
    // 处理侧栏收起状态
    this.$watch('collapsed', () => {
      this.$store.commit(SIDEBAR_TYPE, this.collapsed)
    })
    this.$watch('isMobile', () => {
      this.$store.commit(TOGGLE_MOBILE_TYPE, this.isMobile)
    })
  },
  mounted () {
    this.selectedApp.push(storage.get(APP_CODE, APP_CODE_DEFAULT))
    const userAgent = navigator.userAgent
    if (userAgent.indexOf('Edge') > -1) {
      this.$nextTick(() => {
        this.collapsed = !this.collapsed
        setTimeout(() => {
          this.collapsed = !this.collapsed
        }, 16)
      })
    }

    // first update color
    // TIPS: THEME COLOR HANDLER!! PLEASE CHECK THAT!!
    if (process.env.NODE_ENV !== 'production' || process.env.VUE_APP_PREVIEW === 'true') {
      updateTheme(this.settings.primaryColor)
    }
  },
  methods: {
    i18nRender,
    handleMediaQuery (val) {
      this.query = val
      if (this.isMobile && !val['screen-xs']) {
        this.isMobile = false
        return
      }
      if (!this.isMobile && val['screen-xs']) {
        this.isMobile = true
        this.collapsed = false
        this.settings.contentWidth = CONTENT_WIDTH_TYPE.Fluid
        // this.settings.fixSiderbar = false
      }
    },
    handleCollapse (val) {
      this.collapsed = val
    },
    handleMultiTabChange (checked) {
      this.$store.commit('multi_tab', checked)
      this.handleSettingChange({ 'type': 'multiTab', 'value': checked })
    },
    handleSettingChange ({ type, value }) {
      console.log('type', type, value)
      this.$store.commit(type, value)
      type && (this.settings[type] = value)
      switch (type) {
        case 'contentWidth':
          this.settings[type] = value
          break
        case 'layout':
          if (value === 'sidemenu') {
            this.settings.contentWidth = CONTENT_WIDTH_TYPE.Fluid
          } else {
            this.settings.fixSiderbar = false
            this.settings.contentWidth = CONTENT_WIDTH_TYPE.Fixed
          }
          break
      }
    },
    refresh () {
      this.reload()
    },
    switchApp (appCode) {
      message.loading('切换应用中...', 0.1)
      storage.set(APP_CODE, appCode)
      // this.refresh()
      // this.$router.push(this.$route.path, { replace: true })
      window.location.reload()
    }
  }
}
</script>

<style lang="less">
@import "./BasicLayout.less";
</style>
