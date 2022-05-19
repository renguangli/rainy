<script>
export default {
  name: 'Iframe',
  data () {
    return {}
  },
  render () {
    const url = this.$route.meta.url
    if (!url) {
      return '404'
    }
    const height = (Number(document.documentElement.clientHeight) - 140) + 'px'
    return <iframe id="iframe" height={height} src={url} style="width:100%;overflow:hidden;box-shadow:2px 0 6px rgb(0 21 41 / 35%);" frameBorder="0"></iframe>
  }
}
</script>
