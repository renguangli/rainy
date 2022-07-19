<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="24">
            <a-form-item label="操作人">
              <a-input v-model="queryParam.username" placeholder="请输入操作人"/>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="系统模块">
              <a-select v-model="queryParam.module" placeholder="请选择系统模块" @select="$refs.table.refresh()">
                <a-select-option :key="item.value" v-for="item in $options.filters.dictItems('SYS_MODULE')" :value="item.value">
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="操作类型">
              <a-select v-model="queryParam.operationTypeCode" placeholder="请选择操作类型" @select="$refs.table.refresh()">
                <a-select-option :key="item.value" v-for="item in $options.filters.dictItems('SYS_OPERATION_LOG_TYPE')" :value="item.value">
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col v-if="!expand" :md="6" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="$refs.table.refresh()">查询</a-button>
              <a-button style="margin-left: 8px" @click="queryParam = {};$refs.table.refresh()">重置</a-button>
              <a :style="{ marginLeft: '8px', fontSize: '12px' }" @click="expand = !expand">
                展开 <a-icon :type="expand ? 'up' : 'down'" />
              </a>
            </span>
          </a-col>
        </a-row>
        <a-row v-if="expand" :gutter="24">
          <a-col :md="10" :sm="24">
            <a-form-item label="时间">
              <a-range-picker
                :show-time="{ format: 'HH:mm' }"
                format="YYYY-MM-DD HH:mm"
                :placeholder="['开始时间', '结束时间']"
                @change="handleDateOk"
              />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="$refs.table.refresh()">查询</a-button>
              <a-button style="margin-left: 8px" @click="queryParam = {};$refs.table.refresh()">重置</a-button>
              <a :style="{ marginLeft: '8px', fontSize: '12px' }" @click="expand = !expand">
                收缩 <a-icon :type="expand ? 'up' : 'down'" />
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <s-table
      size="middle"
      ref="table"
      :rowKey="(record) => record.id"
      :alert="{ show: true, clear: () => { this.selectedRowKeys = [] }}"
      :columns="columns"
      :data="loadData"
      :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: handleChange }"
      :showPagination="true"
      :expandRowByClick="true"
      @change="handleChange"
    >
      <template slot="operator">
        <a-popconfirm :disabled="selectedRowKeys.length < 1" placement="topRight" :title="'确定批量删除操作日志吗?'" @confirm="batchDel">
          <a-button type="danger" :disabled="selectedRowKeys.length < 1"><a-icon type="delete"/>批量删除</a-button>
        </a-popconfirm>
        <a-popconfirm placement="topRight" :title="'确定清空日志吗?'" @confirm="clear">
          <a-button type="danger"><a-icon type="delete"/>清空日志</a-button>
        </a-popconfirm>
        <export-excel
          ref="exportExcel"
          @exportExcel="exportExcel"
        />
      </template>
      <span slot="operationTypeCode" slot-scope="text">
        {{ 'SYS_OPERATION_LOG_TYPE' | dictItemValue(text) }}
      </span>
      <span slot="success" slot-scope="text">
        <a-tag v-if="text" color="#87d068">
          {{ 'SYS_SUCCESS_FAIL' | dictItemValue(text) }}
        </a-tag>
        <a-tag v-if="!text" color="#f50">
          {{ 'SYS_SUCCESS_FAIL' | dictItemValue(text) }}
        </a-tag>
      </span>
      <span slot="operation" slot-scope="text, record">
        <template>
          <a @click="$refs.detail.open(record)">查看详情</a>
          <a-divider type="vertical"/>
          <a-popconfirm placement="topRight" :title="'确定删除这条操作日志吗?'" @confirm="del(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </template>
      </span>
    </s-table>
    <detail ref="detail" @ok="handleOk"></detail>
  </a-card>
</template>

<script>
import { ExportExcel, STable } from '@/components'
import detail from './OperationLogDetail'
import { BatchDel, Clear, Del, Export, List } from '@/api/operationLog'

export default {
  name: 'Position',
  components: {
    STable,
    detail,
    ExportExcel
  },
  data () {
    return {
      // 查询参数
      queryParam: {},
      expand: false,
      // 表头
      columns: [
        {
          title: '系统模块',
          dataIndex: 'module'
        }, {
          title: '操作类型',
          dataIndex: 'operationTypeCode',
          scopedSlots: { customRender: 'operationTypeCode' }
        }, {
          title: '操作人',
          dataIndex: 'username'
        }, {
          title: '操作时间',
          dataIndex: 'datetime'
        }, {
          title: '详情',
          dataIndex: 'detail'
        }, {
          title: '是否成功',
          dataIndex: 'success',
          scopedSlots: { customRender: 'success' }
        }, {
          title: '操作',
          scopedSlots: { customRender: 'operation' },
          width: '150px'
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return List(Object.assign(parameter, this.queryParam)).then((res) => {
          return res
        })
      },
      selectedRowKeys: [],
      selectedRows: []
    }
  },
  methods: {
    handleOk () {
      // 新增/修改/删除 成功时，重载列表
      this.$refs.table.refresh()
      this.$refs.table.clearSelected()
    },
    handleChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    handleDateOk (value, dateString) {
      this.queryParam.startTime = dateString[0]
      this.queryParam.endTime = dateString[1]
      this.handleOk()
    },
    del (id) {
      Del(id).then(res => {
        if (res.success) {
          this.$message.success('删除成功')
          this.handleOk()
        } else {
          this.$message.error('删除失败：' + res.message)
        }
      })
    },
    batchDel () {
      BatchDel(this.selectedRowKeys).then(res => {
        if (res.success) {
          this.$message.success('删除成功')
          this.$refs.table.clearSelected()
          this.handleOk()
        } else {
          this.$message.error('删除失败：' + res.message)
        }
      })
    },
    clear () {
      Clear().then(res => {
        if (res.success) {
          this.$message.success('删除成功')
          this.$refs.table.clearSelected()
          this.handleOk()
        } else {
          this.$message.error('删除失败：' + res.message)
        }
      })
    },
    exportExcel () {
      Export().then(res => {
        this.$refs.exportExcel.download(res)
      })
    }
  }
}
</script>
<style>
.table-operator {
  margin-bottom: 18px;
}
button {
  margin-right: 8px;
}
</style>
