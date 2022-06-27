<template>
  <a-row :gutter="48">
    <a-col :md="24" :sm="24">
      <a-card>
        <div>
          <a-form layout="inline">
            <a-form-item label="当前场站">
              <a-tree-select
                v-decorator="['parentId', {rules: [{ required: true, message: '请选择父级组织！' }]}]"
                style="width: 100%"
                :dropdownStyle="{ maxHeight: '300px', overflow: 'auto' }"
                :treeData="orgTree"
                :replace-fields="replaceFields"
                placeholder="请选择场站"
                treeDefaultExpandAll
              >
              </a-tree-select>
            </a-form-item>
            <a-form-item label="预测时间">
              <a-date-picker v-model="queryParam.forecastDate" @change="onChange" />
            </a-form-item>
<!--            <a-form-item label="预测时间">-->
<!--              <a-range-picker @change="onChange" />-->
<!--            </a-form-item>-->
            <a-form-item>
              <a-button type="primary">查询</a-button>
              <a-button style="margin-left: 8px">重置</a-button>
              <a-button @click="$refs.editor.open()" type="dashed" style="margin-left: 8px">手动调整</a-button>
            </a-form-item>
            <a-form-item style="float: right" label="更新时间">
              2022-10-01 00:00:00
            </a-form-item>
          </a-form>
        </div>
        <v-chart class="chart" :option="option" :autoresize="true" />
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
      orgTree: [],
      replaceFields: { title: 'name', key: 'id', value: 'id' },
      queryParam: {
        forecastDate: '2022-02-01'
      },
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
  },
  mounted () {
    this.listTree()
  },
  methods: {
    handleOk () {

    },
    listTree () {
      ListTree().then(res => {
        this.orgTree = res.data
      })
    },
    hide () {
      console.log(111)
      this.visible = false
    },
    onChange (p) {
      console.log(p)
    }
  }
}
</script>

<style scoped>
.chart {
  height: 400px;
}
</style>
