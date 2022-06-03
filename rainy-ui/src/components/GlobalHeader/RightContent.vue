<template>
  <div :class="wrpCls">
    <button style="padding: 0;border: 0;background: #ffffff;cursor: pointer;" data-feedbackfin-button>反馈</button>
    <notice-icon style="vertical-align: -0.25em;cursor: pointer"></notice-icon>
    <avatar-dropdown :menu="showMenu" :current-user="currentUser" :class="prefixCls" />
    <select-lang :class="prefixCls" />
  </div>
</template>
<script>
import NoticeIcon from '@/components/NoticeIcon'
import AvatarDropdown from './AvatarDropdown'
import SelectLang from '@/components/SelectLang'

window.feedbackfin = { config: {}, ...window.feedbackfin }
window.feedbackfin.config.url = process.env.VUE_APP_API_BASE_URL + '/feedback'
window.feedbackfin.config.user = { name: '123', email: '123' }
export default {
  name: 'RightContent',
  components: {
    NoticeIcon,
    AvatarDropdown,
    SelectLang
  },
  props: {
    prefixCls: {
      type: String,
      default: 'ant-pro-global-header-index-action'
    },
    isMobile: {
      type: Boolean,
      default: () => false
    },
    topMenu: {
      type: Boolean,
      required: true
    },
    theme: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      showMenu: true,
      currentUser: {}
    }
  },
  computed: {
    wrpCls () {
      return {
        'ant-pro-global-header-index-right': true,
        [`ant-pro-global-header-index-${(this.isMobile || !this.topMenu) ? 'light' : this.theme}`]: true
      }
    }
  },
  mounted () {
  }
}
</script>
