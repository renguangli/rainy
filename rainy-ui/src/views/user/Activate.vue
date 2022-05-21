<template>
  <div></div>
</template>
import { Activate } from '@/api/login'
<script>
import { Activate } from '@/api/login'
export default {
  name: 'Activate',
  data () {
    return {}
  },
  created () {
    this.activeAccount()
  },
  methods: {
    activeAccount () {
      const token = this.$route.query.token
      Activate(token).then(res => {
        if (res.success) {
          this.$message.info('注册成功：')
        } else {
          this.$message.error('激活失败：' + res.message)
        }
      })
    }
  }
}
</script>
