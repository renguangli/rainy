<template>
  <a-config-provider :locale="locale">
    <div v-if="active" id="app">
      <router-view/>
    </div>
  </a-config-provider>
</template>

<script>
import { domTitle, setDocumentTitle } from '@/utils/domUtil'
import { i18nRender } from '@/locales'

export default {
  provide () {
    return {
      reload: this.reload
    }
  },
  data () {
    return {
      active: true
    }
  },
  computed: {
    locale () {
      // 只是为了切换语言时，更新标题
      const { title } = this.$route.meta
      title && (setDocumentTitle(`${i18nRender(title)} - ${this.$store.getters.title || domTitle}`))

      return this.$i18n.getLocaleMessage(this.$store.getters.lang).antLocale
    }
  },
  methods: {
    reload () {
      this.active = false
      this.$nextTick(function () {
        this.active = true
      })
    }
  }
}
</script>
