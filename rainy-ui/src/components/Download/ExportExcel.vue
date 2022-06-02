<template>
  <a-tooltip placement="top">
    <template slot="title">
      <span>导出所有数据</span>
    </template>
    <a-button type="dashed" @click="exportExcel" :loading="loading"><a-icon type="export"/>导出</a-button>
  </a-tooltip>
</template>

<script>
export default {
  name: 'ExportExcel',
  data () {
    return {
      loading: false
    }
  },
  methods: {
    exportExcel () {
      this.loading = true
      this.$emit('exportExcel', '')
    },
    download (res) {
      console.log('res', res)

      this.loading = false
      const blob = new Blob([res.data], { type: 'application/octet-stream;charset=UTF-8' })
      // 需要后台设置 header  Access-Control-Expose-Headers: Content-Disposition
      const contentDisposition = res.headers['content-disposition']
      const pattern = new RegExp('filename=([^;]+\\.[^.;]+);*')
      const result = pattern.exec(contentDisposition)
      const filename = result[1]
      const downloadElement = document.createElement('a')
      const href = window.URL.createObjectURL(blob) // 创建下载的链接
      const reg = /^["](.*)["]$/g
      downloadElement.style.display = 'none'
      downloadElement.href = href
      downloadElement.download = decodeURI(filename.replace(reg, '$1')) // 下载后文件名
      document.body.appendChild(downloadElement)
      downloadElement.click() // 点击下载
      document.body.removeChild(downloadElement) // 下载完成移除元素
      window.URL.revokeObjectURL(href)
    }
  }
}
</script>
