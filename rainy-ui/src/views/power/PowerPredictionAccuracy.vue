<template>
  <a-card>
    <a-row :gutter="24">
      <a-col :md="12" :sm="24">
        <v-chart class="chart" :option="option" :autoresize="true" />
      </a-col>
      <a-col :md="12" :sm="24">
        <v-chart class="chart" :option="option2" :autoresize="true" />
      </a-col>
    </a-row>
    <a-row :gutter="24">
      <a-col :md="12" :sm="24">
        <v-chart class="chart" :option="option3" :autoresize="true" />
      </a-col>
      <a-col :md="12" :sm="24">
        <v-chart class="chart" :option="option4" :autoresize="true" />
      </a-col>
    </a-row>
  </a-card>
</template>

<script>
import { use } from 'echarts/core'
import { CanvasRenderer } from 'echarts/renderers'
import { BarChart, LineChart } from 'echarts/charts'
import { GridComponent, LegendComponent, TitleComponent, ToolboxComponent, TooltipComponent } from 'echarts/components'
import VChart, { THEME_KEY } from 'vue-echarts'

use([
  CanvasRenderer,
  BarChart,
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
      option: {
        title: {
          text: '功率预测准确率趋势分析'
        },
        tooltip: {
          trigger: 'axis'
        },
        grid: {
          left: '0',
          right: '1%',
          top: '20%',
          bottom: '5%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: []
        },
        yAxis: {
          name: '%',
          type: 'value',
          min: 0,
          max: 100
        },
        series: [
          {
            name: '准确率',
            type: 'bar',
            data: []
          }
        ]
      },
      option2: {
        title: {
          text: '功率预测与实发功率对比分析'
        },
        tooltip: {
          trigger: 'axis'
        },
        grid: {
          left: '0',
          right: '1%',
          top: '20%',
          bottom: '5%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: ['222']
        },
        yAxis: {
          type: 'value',
          min: 0,
          max: 100
        },
        series: [
          {
            name: '预测功率',
            type: 'line',
            data: [1],
            color: 'green'
          }
        ]
      },
      option3: {
        title: {
          text: '功率预测准确率评价'
        },
        tooltip: {
          trigger: 'axis'
        },
        grid: {
          left: '0',
          right: '1%',
          top: '20%',
          bottom: '5%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: []
        },
        yAxis: {
          name: '天',
          type: 'value',
          min: 0,
          max: 30
        },
        series: [
          {
            name: '准确率大于80%的天数',
            type: 'bar',
            data: [1],
            color: 'green'
          }
        ]
      },
      option4: {
        title: {
          text: '考核电量分析'
        },
        tooltip: {
          trigger: 'axis'
        },
        grid: {
          left: '0',
          right: '1%',
          top: '20%',
          bottom: '5%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: []
        },
        yAxis: {
          type: 'value',
          min: 0
        },
        series: [
          {
            name: '预测功率',
            type: 'bar',
            data: [],
            color: 'green'
          }
        ]
      }
    }
  },
  mounted () {
    this.chart1()
    this.chart2()
    this.chart3()
    this.chart4()
  },
  methods: {
    // 打开页面初始化
    chart1 () {
      const x = ['07-01', '07-02', '07-03', '07-04', '07-05', '07-06', '07-07', '07-08', '07-09', '07-10']
      const y1 = [78, 32, 22, 32, 45, 34, 54, 34, 56, 54]
      this.option.xAxis.data = x
      this.option.series[0].data = y1
    },
    chart2 () {
      const x = ['07-01', '07-02', '07-03']
      const y1 = [78, 32, 12]
      this.option2.xAxis.data = x
      this.option2.series[0].data = y1
    },
    chart3 () {
      const x = ['2022-01', '2022-02', '2022-03', '2022-04', '2022-05', '2022-05', '2022-07']
      const y1 = [12, 23, 23, 28, 23, 16, 21]
      this.option3.xAxis.data = x
      this.option3.series[0].data = y1
    },
    chart4 () {
      const x = ['2022-01', '2022-02', '2022-03', '2022-04', '2022-05', '2022-05', '2022-07']
      const y1 = [78, 32, 23, 45, 23, 43, 30]
      this.option4.xAxis.data = x
      this.option4.series[0].data = y1
    }
  }
}
</script>
<style>
.chart {
  height: 250px;
}
</style>
