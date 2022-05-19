<template>
  <a-row :gutter="24" >
    <a-col :md="5" :sm="24">
      <a-card :bordered="false" :loading="treeLoading">
        <a-tree
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
              <a-form-item v-show="false" >
                <a-input v-model="queryParam.orgId" />
              </a-form-item>
              <a-col :md="8" :sm="24">
                <a-form-item label="用户名">
                  <a-input v-model="queryParam.username" placeholder="请输入用户名"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="昵称">
                  <a-input v-model="queryParam.name" placeholder="请输入昵称"/>
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
            <a-button type="primary" icon="plus" @click="$refs.editor.open(0)">新增用户</a-button>
            <a-popconfirm :disabled="selectedRowKeys.length < 1" placement="topRight" :title="'确定批量删除用户吗?'" @confirm="batchDel">
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
                    <a @click="$refs.roleAssign.open(record)">分配角色</a>
                  </a-menu-item>
                  <a-menu-item>
                    <a-popconfirm placement="topRight" :title="'确定重置用户[' + record.name + ']的密码吗?'" @confirm="resetPassword(record.id)">
                      <a>重置密码</a>
                    </a-popconfirm>
                  </a-menu-item>
                  <a-menu-item>
                    <a-popconfirm placement="topRight" :title="'确定删除用户[' + record.name + ']吗?'" @confirm="del(record.id)">
                      <a>删除</a>
                    </a-popconfirm>
                  </a-menu-item>
                </a-menu>
              </a-dropdown>
            </template>
          </span>
        </s-table>
        <editor ref="editor" @ok="handleOk"></editor>
        <roleAssign ref="roleAssign"></roleAssign>
      </a-card>
    </a-col>
  </a-row>
</template>

<script>
import { Empty } from 'ant-design-vue'
import { STable } from '@/components'
import { GetOrgTree } from '@/api/org'
import { List, Del, BatchDel, ResetPassword } from '@/api/user'
import editor from './UserEdit'
import roleAssign from './RoleAssign'

export default {
  name: 'User',
  components: {
    STable,
    editor,
    roleAssign,
    Empty
  },
  data () {
    return {
      treeLoading: true,
      simpleImage: Empty.PRESENTED_IMAGE_SIMPLE,
      replaceFields: { title: 'name', key: 'id' },
      // 查询参数
      showPagination: true,
      queryParam: {
        orgId: null
      },
      // 表头
      columns: [
        {
          title: '用户名',
          dataIndex: 'username'
        }, {
          title: '昵称',
          dataIndex: 'name'
        }, {
          title: '头像',
          dataIndex: 'avatar',
          checked: false
        }, {
          title: '邮箱',
          dataIndex: 'email'
        }, {
          title: '手机号',
          dataIndex: 'telephone'
        }, {
          title: '最后登录时间',
          dataIndex: 'lastLoginTime',
          checked: false
        }, {
          title: '最后登录ip',
          dataIndex: 'lastLoginIp',
          checked: false
        }, {
          title: '最后更新时间',
          dataIndex: 'updateTime',
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
        return List(this.queryParam).then((res) => {
          return res
        })
      },
      form: this.$form.createForm(this),
      selectedRowKeys: [],
      selectedRows: []
    }
  },
  mounted () {
    this.getOrgTree()
  },
  methods: {
    handleOk () {
      // 新增/修改/删除 成功时，重载列表,用户树
      this.$refs.table.refresh()
      this.$refs.table.clearSelected()
    },
    handleChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    handleTreeSelect (selectedKeys, node, extra) {
      console.log('orgId', selectedKeys)
      console.log('node', node.node.$children)
      console.log('extra', extra)
      // 值必须是 string
      this.queryParam.orgId = selectedKeys.toString()
      this.$refs.table.refresh()
    },
    setFieldValue (field, value) {
      this.form.getFieldDecorator(field, { initialValue: value })
      this.form.resetFields(`${field}`, [])
      this.form.getFieldDecorator(field, { initialValue: value })
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
    del (id) {
      Del(id).then(res => {
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
      BatchDel(this.selectedRowKeys).then(res => {
        if (res.success) {
          this.$message.success('删除成功')
          this.handleOk()
        } else {
          this.$message.error('删除失败：' + res.message)
        }
      })
    },
    resetPassword (id) {
      ResetPassword(id).then(res => {
        if (res.success) {
          this.$message.success('重置成功')
          this.handleOk()
        } else {
          this.$message.error('重置失败：' + res.message)
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
