<template>
  <a-card>
    <v-chart class="chart" :option="option" />
  </a-card>
</template>

<script>
import { use } from 'echarts/core'
import { CanvasRenderer } from 'echarts/renderers'
import { PieChart, LineChart } from 'echarts/charts'
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
  PieChart,
  LineChart,
  TitleComponent,
  TooltipComponent,
  ToolboxComponent,
  LegendComponent,
  GridComponent
])

export default {
  name: 'PowerForecast',
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
          text: '功率预测'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          itemStyle: {
            decal: 'none'
          },
          type: 'scroll',
          align: 'left',
          data: ['Email', 'Union Ads', 'Video Ads', 'Direct', 'Search Engine']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
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
      }
    }
  }
}
</script>

<style scoped>
.chart {
  height: 400px;
}
</style>
