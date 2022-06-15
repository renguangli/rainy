<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="24">
            <a-form-item label="用户名">
              <a-input v-model="queryParam.username" placeholder="请输入用户名"/>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="登录类型">
              <a-select v-model="queryParam.loginType" placeholder="请选择登录类型" @select="$refs.table.refresh()">
                <a-select-option :key="item.value" v-for="item in $options.filters.dictItems('SYS_LOGIN_TYPE')" :value="item.value">
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-range-picker
              :show-time="{ format: 'HH:mm' }"
              format="YYYY-MM-DD HH:mm"
              :placeholder="['开始时间', '结束时间']"
              @change="handleDateOk"
            />
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
      @change="handleChange"
    >
      <template slot="operator">
        <a-popconfirm :disabled="selectedRowKeys.length < 1" placement="topRight" :title="'确定批量删除登录日志吗?'" @confirm="batchDel">
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
      <span slot="loginType" slot-scope="text">
        {{ 'SYS_LOGIN_TYPE' | dictItemValue(text) }}
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
          <a-popconfirm placement="topRight" :title="'确定删除这条登录日志吗?'" @confirm="del(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </template>
      </span>
    </s-table>
  </a-card>
</template>

<script>
import { STable, ExportExcel } from '@/components'
import { List, Del, BatchDel, Clear, Export } from '@/api/LoginLog'

export default {
  name: 'Position',
  components: {
    STable,
    ExportExcel
  },
  data () {
    return {
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
          title: '用户名',
          dataIndex: 'username',
          width: '80px'
        }, {
          title: '类型',
          dataIndex: 'loginType',
          scopedSlots: { customRender: 'loginType' }
        }, {
          title: '时间',
          dataIndex: 'datetime'
        }, {
          title: 'ip地址',
          dataIndex: 'ip'
        }, {
          title: '浏览器',
          dataIndex: 'browser'
        }, {
          title: '操作系统',
          dataIndex: 'os'
        }, {
          title: '结果',
          dataIndex: 'success',
          scopedSlots: { customRender: 'success' }
        }, {
          title: '错误详情',
          dataIndex: 'errorMessage',
          checked: false,
          ellipsis: true
        }, {
          title: '操作',
          scopedSlots: { customRender: 'operation' },
          width: '40'
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
          this.$message.success('清除成功')
          this.$refs.table.clearSelected()
          this.handleOk()
        } else {
          this.$message.error('清除失败：' + res.message)
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
