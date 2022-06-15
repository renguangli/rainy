<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="24">
            <a-form-item label="任务名称">
              <a-input v-model="queryParam.name" placeholder="请输入任务名称"/>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="任务分组">
              <a-select v-model="queryParam.group" placeholder="请选择任务分组" @select="$refs.table.refresh()">
                <a-select-option :key="item.value" v-for="item in $options.filters.dictItems('SYS_TASK_GROUP')" :value="item.value">
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="执行结果">
              <a-select v-model="queryParam.success" placeholder="请选择执行结果" @select="$refs.table.refresh()">
                <a-select-option :key="item.value" v-for="item in $options.filters.dictItems('SYS_SUCCESS_FAIL')" :value="item.value">
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="$refs.table.refresh()">查询</a-button>
              <a-button style="margin-left: 8px" @click="queryParam = {}">重置</a-button>
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
    >
      <template slot="operator">
        <a-popconfirm placement="topRight" :title="'确定批量删除执行日志吗?'" @confirm="batchDel">
          <a-button type="danger" :disabled="selectedRowKeys.length < 1"><a-icon type="delete"/>批量删除</a-button>
        </a-popconfirm>
        <a-popconfirm placement="topLeft" ok-text="确定" cancel-text="取消" @confirm="clear">
          <template slot="title">
            <p>确定清空执行日志吗？</p>
          </template>
          <a-button type="danger" icon="delete">清空日志</a-button>
        </a-popconfirm>
        <export-excel
          ref="exportExcel"
          @exportExcel="exportExcel"
        />
      </template>
      <span slot="group" slot-scope="text">
        {{ 'SYS_TASK_GROUP' | dictItemValue(text) }}
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
          <a-popconfirm placement="topRight" :title="'确定删除这条执行日志吗?'" @confirm="del(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </template>
      </span>
    </s-table>
  </a-card>
</template>

<script>
import { STable, ExportExcel } from '@/components'
import { List, Del, BatchDel, Clear, Export } from '@/api/taskLog'

export default {
  name: 'TaskLog',
  components: {
    STable,
    ExportExcel
  },
  data () {
    return {
      // 查询参数
      queryParam: {
        name: this.$route.query.name,
        group: this.$route.query.group
      },
      // 表头
      columns: [
        {
          title: '名称',
          dataIndex: 'name'
        }, {
          title: '分组',
          dataIndex: 'group',
          scopedSlots: { customRender: 'group' }
        }, {
          title: 'class类名',
          dataIndex: 'className',
          checked: false
        }, {
          title: '方法',
          dataIndex: 'method',
          checked: false
        }, {
          title: '执行时间',
          dataIndex: 'datetime'
        }, {
          title: '下一次执行时间',
          dataIndex: 'nextDatetime',
          checked: false
        }, {
          title: '执行时长(毫秒)',
          dataIndex: 'processTime'
        }, {
          title: '是否成功',
          dataIndex: 'success',
          scopedSlots: { customRender: 'success' }
        }, {
          title: '错误信息',
          dataIndex: 'errorMessage',
          checked: false
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
    },
    handleChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
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
          this.$message.success('清空成功')
          this.handleOk()
        } else {
          this.$message.error('清空失败：' + res.message)
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
.ant-badge-status-dot {
    width: 10px;
    height: 10px;
}
</style>
