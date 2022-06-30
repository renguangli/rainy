<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="24">
            <a-form-item label="公告标题">
              <a-input v-model="queryParam.title" placeholder="请输入公告标题"/>
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
        <a-button type="primary" icon="plus" @click="$refs.editor.open(0)">新增公告</a-button>
        <a-popconfirm :disabled="selectedRowKeys.length < 1" placement="topRight" :title="'确定批量删除公告吗?'" @confirm="batchDel">
          <a-button type="danger" :disabled="selectedRowKeys.length < 1"><a-icon type="delete"/>批量删除</a-button>
        </a-popconfirm>
      </template>
      <span slot="operation" slot-scope="text, record">
        <template>
          <a @click="$refs.editor.open(1, record)">编辑</a>
          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">
              更多 <a-icon type="down" />
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm placement="topRight" :title="'确定删除公告[' + record.title + ']吗?'" @confirm="del(record)">
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
import { STable } from '@/components'
import { List, Del, BatchDel } from '@/api/notice'
import editor from './NoticeEdit'

export default {
  name: 'Notice',
  components: {
    STable,
    editor
  },
  data () {
    return {
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
          title: '公告标题',
          dataIndex: 'title',
          width: '140px'
        }, {
          title: '公告内容',
          dataIndex: 'content',
          ellipsis: true
        }, {
          title: '发布时间',
          dataIndex: 'datetime',
          width: '180px'
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
      const param = { id: record.id, name: record.title }
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
      this.selectedRows.forEach(record => {
        param.ids.push(record.id)
        param.names.push(record.title)
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
