<template>
  <a-row :gutter="48">
    <a-col :md="12" :sm="24">
      <a-card
        :title="'系统信息'"
        :loading="loading"
        :bordered="false">
        <p>操作系统： {{ monitor.osName }} </p>
        <p>系统架构：{{ monitor.osArch }}</p>
        <p>cpu核心数：{{ monitor.availableProcessors }}</p>
        <p>平均负载：{{ monitor.systemLoadAverage }}</p>
      </a-card>
      <a-card
        :title="'java 信息'"
        :loading="loading"
        :bordered="false">
        <p>厂商： {{ monitor.javaVendor }} </p>
        <p>版本：{{ monitor.javaVersion }}</p>
      </a-card>
    </a-col>
    <a-col :md="12" :sm="24">
      <a-card
        :title="'jvm信息'"
        :loading="loading"
        :bordered="false">
        <p>名称： {{ monitor.jvmName }} </p>
        <p>版本： {{ monitor.jvmVersion }} </p>
        <p>运行时长： {{ monitor.runtime/1000 }} 秒</p>
        <p>最大内存：{{ monitor.maxMemory }} mb</p>
        <p>总内存：{{ monitor.totalMemory }} mb</p>
        <p>已使用内存：{{ monitor.useMemory }} mb</p>
        <p>剩余内存：{{ monitor.freeMemory }} mb</p>
        <p>内存使用率：{{ monitor.useRate }} %</p>
      </a-card>
    </a-col>
  </a-row>

</template>

<script>

import { SysMonitor } from '@/api/SysMonitor'

export default {
  name: 'Dict',
  components: {},
  data () {
    return {
      loading: true,
      monitor: {}
    }
  },
  mounted () {
    this.sysMonitor()
    setInterval(() => {
      this.sysMonitor()
    }, 5000)
  },
  methods: {
    sysMonitor () {
      this.loading = true
      SysMonitor().then(res => {
        this.monitor = res.data
      }).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>
