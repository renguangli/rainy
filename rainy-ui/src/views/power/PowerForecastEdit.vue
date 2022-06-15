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
              :min="1"
              :max="100"
              :formatter="value => `${value}%`"
              :parser="value => value.replace('%', '')"
            />
          </a-input-group>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="adjust">确定</a-button>
          <a-button style="margin-left: 8px" @click="recover">复原</a-button>
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
import {
  TitleComponent,
  TooltipComponent,
  ToolboxComponent,
  LegendComponent,
  GridComponent
} from 'echarts/components'
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
      value: 1,
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
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: 'Email',
            type: 'line',
            symbol: 'none',
            smooth: true,
            data: [120, 132, 101, 134, 90, 230, 210]
          },
          {
            name: 'Union Ads',
            type: 'line',
            symbol: 'none',
            smooth: true,
            data: [220, 182, 191, 234, 290, 330, 310]
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
    open () {
      this.visible = true
      this.data = this.copyArray(this.option.series[1].data);
    },
    adjust () {
      // 判断是上调还是下调
      let percentage = 1 + this.value/100
      if (this.operation === 'down') {
        percentage = 1 - this.value/100
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
      return  arr.slice(0);
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
