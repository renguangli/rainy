<template>
  <a-row :gutter="24" >
    <a-col :md="5" :sm="24">
      <a-card :bordered="false" :loading="treeLoading">
        <a-tree
          v-model="queryParam.orgId"
          :treeData="orgTree"
          @select="handleTreeSelect"
          :defaultExpandAll="true"
          :replaceFields="replaceFields" />
      </a-card>
    </a-col>
    <a-col :md="19" :sm="24">
      <a-card :bordered="false">
        <div class="table-page-search-wrapper">
          <a-form layout="inline">
            <a-row :gutter="48">
              <a-col :md="8" :sm="24">
                <a-form-item label="组织名称">
                  <a-input v-model="queryParam.name" placeholder="请输入组织名称"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <span class="table-page-search-submitButtons">
                  <a-button type="primary" @click="$refs.table.refresh()">查询</a-button>
                  <a-button style="margin-left: 8px" @click="queryParam = {}">重置</a-button>
                </span>
              </a-col>
            </a-row>
          </a-form>
        </div>
        <s-table
          ref="table"
          :rowKey="(record) => record.id"
          :alert="{ show: true, clear: () => { this.selectedRowKeys = [] }}"
          :columns="columns"
          :data="loadData"
          :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: handleChange }"
          :showPagination="showPagination"
          :expandRowByClick="true"
          @change="handleChange"
        >
          <template slot="operator">
            <a-button type="primary" icon="plus" @click="$refs.editor.open(0, null, orgId)">新增组织</a-button>
            <a-popconfirm :disabled="selectedRowKeys.length < 1" placement="topRight" :title="'确定批量删除组织吗?'" @confirm="batchDel">
              <a-button type="danger" :disabled="selectedRowKeys.length < 1"><a-icon type="delete"/>批量删除</a-button>
            </a-popconfirm>
          </template>
          <span slot="operation" slot-scope="text, record">
            <template>
              <a @click="$refs.editor.open(1, record)">编辑</a>
              <a-divider type="vertical"/>
              <a-popconfirm placement="topRight" :title="'确定删除组织[' + record.name + ']吗?'" @confirm="del(record)">
                <a>删除</a>
              </a-popconfirm>
            </template>
          </span>
        </s-table>
        <editor ref="editor" @ok="handleOk"></editor>
      </a-card>
    </a-col>
  </a-row>
</template>

<script>
import { Empty } from 'ant-design-vue'
import { STable } from '@/components'
import { GetOrgTree, List, Del, BatchDel } from '@/api/org'
import editor from './OrgEdit'

export default {
  name: 'Org',
  components: {
    STable,
    editor,
    Empty
  },
  data () {
    return {
      treeLoading: true,
      simpleImage: Empty.PRESENTED_IMAGE_SIMPLE,
      replaceFields: { title: 'name', key: 'id', value: 'id' },
      // 查询参数
      showPagination: true,
      queryParam: {},
      orgId: 0,
      // 表头
      columns: [
        {
          title: '组织名称',
          dataIndex: 'name'
        }, {
          title: '描述',
          dataIndex: 'description'
        }, {
          title: '排序',
          dataIndex: 'sort'
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
      orgTree: [],
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
  mounted () {
    this.getOrgTree()
  },
  methods: {
    handleTreeSelect (id) {
      this.orgId = id.toString()
      this.queryParam.orgId = id.toString()
      this.$refs.table.refresh()
      this.queryParam = {}
    },
    handleOk () {
      // 新增/修改/删除 成功时，重载列表,组织树
      this.getOrgTree()
      this.$refs.table.refresh()
      this.$refs.table.clearSelected()
    },
    handleChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    getOrgTree () {
      this.treeLoading = true
      GetOrgTree().then(res => {
        if (res.success) {
          this.orgTree = res.data
        }
      }).finally(() => {
        this.treeLoading = false
      })
    },
    del (record) {
      const param = { id: record.id, name: record.name }
      Del(param).then(res => {
        if (res.success) {
          this.$message.success('删除成功')
          this.handleOk()
          this.$refs.table.clearSelected()
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
        param.names.push(record.name)
      })
      BatchDel(param).then(res => {
        if (res.success) {
          this.$message.success('删除成功')
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
