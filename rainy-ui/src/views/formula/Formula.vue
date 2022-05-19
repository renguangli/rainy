<template>
  <a-card :bordered="false">
    <WangEditor ref="editor" @change="handleChange"></WangEditor>
  </a-card>
</template>

<script>
import WangEditor from '@/components/Editor/QuillEditor'

export default {
  name: 'Formula',
  components: {
    WangEditor
  },
  data () {
    return {}
  },
  methods: {
    handleChange () {
      // alert()
    }
  }
}
</script>

<style scoped>

</style>
