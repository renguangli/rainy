<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="24">
            <a-form-item label="角色名称">
              <a-input v-model="queryParam.name" placeholder="请输入角色名称"/>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <a-form-item label="角色编码">
              <a-input v-model="queryParam.code" placeholder="请输入角色编码"/>
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
        <a-button type="primary" icon="plus" @click="$refs.editor.open(0)">新增角色</a-button>
        <a-popconfirm :disabled="selectedRowKeys.length < 1" placement="topRight" :title="'确定批量删除角色吗?'" @confirm="batchDel">
          <a-button type="danger" :disabled="selectedRowKeys.length < 1"><a-icon type="delete"/>批量删除</a-button>
        </a-popconfirm>
        <export-excel
          ref="exportExcel"
          @exportExcel="exportExcel"
        />
      </template>
      <span slot="defaultd" slot-scope="text">
        {{ 'SYS_YES_OR_NO' | dictItemValue(text) }}
      </span>
      <span slot="operation" slot-scope="text, record">
        <template>
          <a-button type="link" size="small" :disabled="record.defaultd" @click="$refs.editor.open(1, record)">编辑</a-button>
          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">
              更多 <a-icon type="down" />
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-button :disabled="record.defaultd" type="link" size="small" @click="$refs.menuAssign.open(record)">分配菜单</a-button>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm :disabled="record.defaultd" placement="topRight" :title="'确定删除角色[' + record.name + ']吗?'" @confirm="del(record)">
                  <a-button type="link" size="small" :disabled="record.defaultd">删除</a-button>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </template>
      </span>
    </s-table>
    <editor ref="editor" @ok="handleOk"></editor>
    <menuAssign ref="menuAssign" @ok="handleOk"></menuAssign>
  </a-card>
</template>

<script>
import { STable, ExportExcel } from '@/components'
import { List, Del, BatchDel, Export } from '@/api/role'
import editor from './RoleEdit'
import menuAssign from './MenuAssign'

export default {
  name: 'Role',
  components: {
    STable,
    editor,
    menuAssign,
    ExportExcel
  },
  data () {
    return {
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
          title: '角色名称',
          dataIndex: 'name'
        }, {
          title: '角色编码',
          dataIndex: 'code'
        }, {
          title: '描述',
          dataIndex: 'description',
          ellipsis: true
        }, {
          title: '是否默认',
          dataIndex: 'defaultd',
          scopedSlots: { customRender: 'defaultd' }
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
    batchDel () {
      const param = {
        ids: [],
        names: []
      }
      this.selectedRows.forEach(v => {
        param.ids.push(v.id)
        param.names.push(v.name)
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
.ant-btn-link {
  margin-right: 0;
  padding: 0;
}
.table-operator {
  margin-bottom: 18px;
}
button {
  margin-right: 8px;
}
</style>
