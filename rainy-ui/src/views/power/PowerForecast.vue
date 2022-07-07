<template>
  <a-row :gutter="48">
    <a-col :md="24" :sm="24">
      <a-card>
        <div>
          <a-form layout="inline">
            <a-row :gutter="24">
              <a-col :md="18" :sm="24">
                <a-form-item
                  label="当前场站">
                  <a-tree-select
                    v-model="queryParam.stationCode"
                    style="min-width: 150px"
                    :dropdownStyle="{ maxHeight: '300px', overflow: 'auto' }"
                    :treeData="treeData"
                    :replace-fields="replaceFields"
                    placeholder="请选择场站"
                    treeDefaultExpandAll
                  >
                  </a-tree-select>
                </a-form-item>
                <a-form-item label="预测时间">
                  <a-date-picker valueFormat="YYYY-MM-DD" v-model="queryParam.date" @change="onChange" />
                </a-form-item>
                <a-form-item>
                  <a-button @click="list" type="primary">查询</a-button>
                  <a-button @click="queryParam = {}" style="margin-left: 8px">重置</a-button>
                  <a-button @click="$refs.editor.open(option.xAxis.data, option.series[0].data, option.series[1].data)" type="dashed" style="margin-left: 8px">手动调整</a-button>
                </a-form-item>
              </a-col>
<!--              <a-col :md="6" :sm="24">-->
<!--                <a-form-item style="float: right" label="更新时间">-->
<!--                  2022-10-01 00:00:00-->
<!--                </a-form-item>-->
<!--              </a-col>-->
            </a-row>
          </a-form>
        </div>
        <a-spin :spinning="spinning">
          <v-chart class="chart" :option="option" :autoresize="true" />
        </a-spin>
      </a-card>
      <editor ref="editor" @ok="handleOk"></editor>
    </a-col>
  </a-row>
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
import editor from './PowerForecastEdit'
import { ListTree } from '@/api/resource'
import { List } from '@/api/powerForecast'
import moment from 'moment'

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
  name: 'PowerForecast',
  components: {
    VChart,
    editor
  },
  provide: {
    [THEME_KEY]: 'ecTheme'
  },
  data () {
    return {
      visible: false,
      treeData: [],
      replaceFields: { title: 'name', key: 'id', value: 'code' },
      queryParam: {
        date: moment().format('YYYY-MM-DD'),
        stationCode: 'dxdd01'
      },
      spinning: false,
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
      }
    }
  },
  mounted () {
    this.listTree()
    this.list()
  },
  methods: {
    handleOk () {

    },
    list () {
      this.spinning = true
      List(this.queryParam).then(res => {
        const data = res.data
        var x = []
        var y1 = []
        var y2 = []
        data.forEach(re => {
          x.push(re.datetime)
          y1.push(re.value)
          y2.push(re.adjustValue)
        })
        this.option.xAxis.data = x
        this.option.series[0].data = y1
        this.option.series[1].data = y2
      }).finally(() => {
        this.spinning = false
      })
    },
    listTree () {
      ListTree().then(res => {
        this.treeData = res.data
      })
    },
    hide () {
      this.visible = false
    },
    onChange () {
      this.list()
    }
  }
}
</script>

<style scoped>
.chart {
  height: 400px;
}
</style>
