<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="8" :sm="24">
            <a-form-item label="应用名称">
              <a-input v-model="queryParam.name" placeholder="请输入应用名称"/>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <a-form-item label="唯一编码">
              <a-input v-model="queryParam.code" placeholder="请输唯一编码"/>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="$refs.table.refresh()">查询</a-button>
              <a-button style="margin-left: 8px" @click="queryParam = {};$refs.table.refresh()">重置</a-button>
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
        <a-button type="primary" icon="plus" @click="$refs.editor.open(0)">新增应用</a-button>
        <a-popconfirm :disabled="selectedRowKeys.length < 1" placement="topRight" :title="'确定批量删除应用吗?'" @confirm="batchDel">
          <a-button type="danger" :disabled="selectedRowKeys.length < 1"><a-icon type="delete"/>批量删除</a-button>
        </a-popconfirm>
        <export-excel
          ref="exportExcel"
          @exportExcel="exportExcel"
        />
      </template>
      <span slot="status" slot-scope="text">
        <a-tag v-if="text == 0" color="#87d068">
          {{ 'SYS_ENABLE_DISABLE' | dictItemValue(text) }}
        </a-tag>
        <a-tag v-if="text == 1" color="#f50">
          {{ 'SYS_ENABLE_DISABLE' | dictItemValue(text) }}
        </a-tag>
      </span>
      <span slot="operation" slot-scope="text, record">
        <template>
          <a-popconfirm v-if="record.status == 0" placement="topRight" :title="'确定禁用应用[' + record.name + ']吗?'" @confirm="disable(record)">
            <a>禁用</a>
          </a-popconfirm>
          <a-popconfirm v-if="record.status == 1" placement="topRight" :title="'确定启用应用[' + record.name + ']吗?'" @confirm="enable(record)">
            <a>启用</a>
          </a-popconfirm>
          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">
              更多 <a-icon type="down" />
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="$refs.editor.open(1, record)">编辑</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm placement="topRight" :title="'确定删除应用[' + record.name + ']吗?'" @confirm="del(record)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </template>
      </span>
    </s-table>
    <editor ref="editor" @ok="handleOk"></editor>
  </a-card>
</template>

<script>

import { STable, ExportExcel } from '@/components'
import editor from './AppEdit'
import { List, Del, Disable, Enable, BatchDel, Export } from '@/api/app'

export default {
  name: 'App',
  components: {
    STable,
    editor,
    ExportExcel
  },
  data () {
    return {
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
          title: '应用名称',
          dataIndex: 'name'
        }, {
          title: '唯一编码',
          dataIndex: 'code'
        }, {
          title: '重定向地址',
          dataIndex: 'redirect',
          ellipsis: true
        }, {
          title: '密钥',
          dataIndex: 'secretKey',
          scopedSlots: { customRender: 'dataType' }
        }, {
          title: '描述',
          dataIndex: 'description',
          ellipsis: true
        }, {
          title: '状态',
          dataIndex: 'status',
          scopedSlots: { customRender: 'status' }
        }, {
          title: '创建时间',
          dataIndex: 'createTime',
          checked: false
        }, {
          title: '创建者',
          dataIndex: 'createBy',
          checked: false
        }, {
          title: '更新时间',
          dataIndex: 'updateTime',
          checked: false
        }, {
          title: '更新者',
          dataIndex: 'updateBy',
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
      this.$refs.table.clearSelected()
    },
    handleChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    del (record) {
      const param = { id: record.id, name: record.name }
      Del(param).then(res => {
        if (res.success) {
          this.$message.success('删除成功')
          this.handleOk()
        } else {
          this.$message.error('删除失败：' + res.message)
        }
      })
    },
    disable (record) {
      const param = {
        id: record.id,
        name: record.name
      }
      Disable(param).then(res => {
        if (res.success) {
          this.$message.success('禁用成功')
          this.handleOk()
        } else {
          this.$message.error('禁用失败：' + res.message)
        }
      })
    },
    enable (record) {
      const param = {
        id: record.id,
        name: record.name
      }
      Enable(param).then(res => {
        if (res.success) {
          this.$message.success('启用成功')
          this.handleOk()
        } else {
          this.$message.error('启用失败：' + res.message)
        }
      })
    },
    batchDel () {
      const param = {
        ids: [],
        names: []
      }
      this.selectedRows.forEach(record => {
        param.ids.push(record.id)
        param.names.push(record.name)
      })
      BatchDel(param).then(res => {
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
