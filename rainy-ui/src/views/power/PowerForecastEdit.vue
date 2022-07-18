<template>
  <a-modal
    title="功率预测调整"
    :width="1200"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    :destroyOnClose="true"
  >
    <div>
      <a-form layout="inline">
        <a-form-item>
          <a-input-group compact>
            <a-select v-model="operation" :default-value="operation">
              <a-select-option value="up">
                上调
              </a-select-option>
              <a-select-option value="down">
                下调
              </a-select-option>
            </a-select>
            <a-input-number
              v-model="value"
              @change="adjust"
              :min="0"
              :max="100"
              :formatter="value => `${value}%`"
              :parser="value => value.replace('%', '')"
            />
          </a-input-group>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="recover">复原</a-button>
        </a-form-item>
      </a-form>
    </div>
    <v-chart ref="chart" class="chart" :option="option" @click="singleAdjust" />
  </a-modal>
</template>

<script>
import { use } from 'echarts/core'
import { CanvasRenderer } from 'echarts/renderers'
import { LineChart } from 'echarts/charts'
import { GridComponent, LegendComponent, TitleComponent, ToolboxComponent, TooltipComponent } from 'echarts/components'
import VChart, { THEME_KEY } from 'vue-echarts'
import { Edit } from '@/api/powerForecast'

use([
  CanvasRenderer,
  LineChart,
  TitleComponent,
  TooltipComponent,
  ToolboxComponent,
  LegendComponent,
  GridComponent
])

export default {
  name: 'PowerForecastEdit',
  components: {
    VChart
  },
  provide: {
    [THEME_KEY]: 'ecTheme'
  },
  data () {
    return {
      value: 0,
      operation: 'up',
      option: {
        title: {
          text: ''
        },
        tooltip: {
          trigger: 'axis'
        },
        grid: {
          left: '0',
          right: '1%',
          top: '10%',
          bottom: '0',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: []
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '预测功率',
            type: 'line',
            // symbol: 'none',
            smooth: true,
            data: []
          },
          {
            name: '预测功率(调整后)',
            type: 'line',
            // symbol: 'none',
            smooth: true,
            data: []
          }
        ]
      },
      visible: false,
      formLoading: false,
      confirmLoading: false,
      powerForecasts: []
    }
  },
  mounted () {
  },
  methods: {
    // 打开页面初始化
    open (data) {
      this.visible = true
      this.powerForecasts = data
      const x = []
      const y1 = []
      const y2 = []
      data.forEach(re => {
        x.push(re.datetime)
        y1.push(re.value)
        y2.push(re.value)
        // y2.push(re.adjustValue)
      })
      this.option.xAxis.data = x
      this.option.series[0].data = y1
      this.option.series[1].data = y2
    },
    singleAdjust (param) {
      console.log(param.value)
      console.log(param.dataIndex)
      console.log(param)
    },
    adjust () {
      // 判断是上调还是下调
      let percentage = 1 + this.value / 100
      if (this.operation === 'down') {
        percentage = 1 - this.value / 100
      }
      // 调整
      const data = this.getValues()
      for (const i in data) {
        data[i] = data[i] * percentage
      }
      this.option.series[1].data = data
      this.$refs.chart.setOption(this.option)
    },
    recover () {
      this.option.series[1].data = this.getValues()
      this.value = 0
      this.$refs.chart.setOption(this.option)
    },
    getValues () {
      const data = []
      this.powerForecasts.forEach(re => {
        data.push(re.value)
        // y2.push(re.adjustValue)
      })
      return data
    },
    handleOk () {
      const data = this.option.series[1].data
      for (let i = 0; i < data.length; i++) {
        this.powerForecasts[i].adjustValue = data[i].toFixed(4)
      }
      Edit(this.powerForecasts).then(res => {
        if (res.success) {
          this.$message.success('调整成功')
          this.$emit('ok')
          this.handleCancel()
        } else {
          this.$message.error('调整失败：' + res.message)
        }
      })
    },
    handleCancel () {
      this.confirmLoading = false
      this.visible = false
      this.data = []
      this.powerForecasts = []
      this.value = 0
    }
  }
}
</script>
<style>
.chart {
  height: 400px;
}
</style>
