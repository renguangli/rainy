<template>
  <a-row :gutter="24">
    <a-col :md="5" :sm="24">
      <a-card :bordered="false">
        <a-tree
          :treeData="treeData"
          :show-line="true"
          :replaceFields="replaceFields"
          :expandedKeys="selectedKeys"
          :selectedKeys="[queryParam.stationCode]"
          @expand="handleExpand"
          @select="handleTreeSelect"/>
      </a-card>
    </a-col>
    <a-col :md="19" :sm="24">
      <a-card>
        <div>
          <a-form layout="inline">
            <a-form-item label="预测时间">
              <a-date-picker valueFormat="YYYY-MM-DD" v-model="queryParam.date" @change="onChange" />
            </a-form-item>
            <a-form-item>
              <a-button @click="list" type="primary">查询</a-button>
              <!-- <a-button @click="queryParam = {}" style="margin-left: 8px">重置</a-button> -->
              <a-button @click="adjust()" style="margin-left: 8px">手动调整</a-button>
              <a-button type="dashed" icon="download" style="margin-left: 8px">文件下载</a-button>
            </a-form-item>
            <a-form-item style="float: right">
              <a-tag color="blue">更新时间</a-tag>
              2022-10-01 00:00:00
            </a-form-item>
          </a-form>
        </div>
        <a-spin :spinning="spinning">
          <v-chart class="chart" :option="option" :autoresize="true" />
        </a-spin>
        <editor ref="editor" @ok="handleOk"></editor>
      </a-card>
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
      spinning: false,
      replaceFields: { title: 'name', key: 'code', value: 'code' },
      selectedKeys: [],
      treeData: [],
      queryParam: {
        date: moment().format('YYYY-MM-DD'),
          stationCode: 'dxdd01'
      },
      powerForecasts: [],
      option: {
        title: {
          text: ''
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          left: 'right'
        },
        grid: {
          left: '1%',
          right: '1%',
          top: '10%',
          bottom: '5%',
          containLabel: true
        },
        toolbox: {
          feature: {
            // saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: []
        },
        yAxis: {
          type: 'value',
          name: 'MW',
          min: 0,
          max: 100
        },
        series: [
          {
            name: '预测功率(调整前)',
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
      this.list()
    },
    list () {
      this.spinning = true
      List(this.queryParam).then(res => {
        const data = res.data
        this.powerForecasts = res.data
        var x = []
        var y1 = []
        var y2 = []
        data.forEach(re => {
          x.push(moment(re.datetime).format('MM-DD HH:mm'))
          y1.push(re.value)
          y2.push(re.adjustValue)
        })
        if (y1.length !== 0) {
          this.option.yAxis.max = undefined
        } else {
          this.option.yAxis.max = 100
        }
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
        this.treeData.forEach(r => {
          this.selectedKeys.push(r.code)
        })
      })
    },
    handleTreeSelect (val) {
      if (val.length > 0) {
        this.queryParam.stationCode = val[0]
      }
      this.list()
    },
    handleExpand (val) {
      this.selectedKeys = val
      console.log(val)
    },
    hide () {
      this.visible = false
    },
    onChange () {
      this.list()
    },
    adjust () {
      if (this.powerForecasts.length === 0) {
        this.$message.warning('无数据')
      } else {
        this.$refs.editor.open(this.powerForecasts)
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
