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
    <v-chart ref="chart" class="chart" :option="option" />
  </a-modal>
</template>

<script>
import { use } from 'echarts/core'
import { CanvasRenderer } from 'echarts/renderers'
import { LineChart } from 'echarts/charts'
import { GridComponent, LegendComponent, TitleComponent, ToolboxComponent, TooltipComponent } from 'echarts/components'
import VChart, { THEME_KEY } from 'vue-echarts'

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
      data: []
    }
  },
  mounted () {
  },
  methods: {
    // 打开页面初始化
    open (x, y1, y2) {
      this.visible = true
      this.option.xAxis.data = x
      this.option.series[0].data = y1
      this.option.series[1].data = y2
      this.data = this.copyArray(this.option.series[1].data)
    },
    adjust () {
      // 判断是上调还是下调
      let percentage = 1 + this.value / 100
      if (this.operation === 'down') {
        percentage = 1 - this.value / 100
      }
      // 调整
      const data = this.data.slice(0)
      for (const i in data) {
        data[i] = data[i] * percentage
      }
      this.option.series[1].data = data
      this.$refs.chart.setOption(this.option)
    },
    recover () {
      this.option.series[1].data = this.data
      this.$refs.chart.setOption(this.option)
    },
    copyArray (arr) {
      return arr.slice(0)
    },
    handleOk () {
     this.handleCancel()
    },
    handleCancel () {
      this.confirmLoading = false
      this.visible = false
    }
  }
}
</script>
<style>
.chart {
  height: 400px;
}
</style>
