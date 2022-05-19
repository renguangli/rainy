<template>
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
      :showPagination="true"
      @change="handleChange"
    >
      <template slot="operator">
        <a-popconfirm :disabled="selectedRowKeys.length < 1" placement="topRight" :title="'确定批量下线吗！'" @confirm="batchKickOut">
          <a-button type="danger" icon="delete" :disabled="selectedRowKeys.length < 1">批量下线</a-button>
        </a-popconfirm>
      </template>
      <span slot="operation" slot-scope="text, record">
        <a-popconfirm placement="topRight" :title="'确定下线用户[' + record.name + ']吗?'" @confirm="kickOut(record.id)">
          <a>下线</a>
        </a-popconfirm>
      </span>
    </s-table>
  </a-card>
</template>

<script>
import { Empty } from 'ant-design-vue'
import { STable } from '@/components'
import { OnlineUser, KickOut, BatchKickOut } from '@/api/SysMonitor'

export default {
  name: 'UserOnline',
  components: {
    STable,
    Empty
  },
  data () {
    return {
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
          title: '用户名',
          dataIndex: 'username'
        }, {
          title: '昵称',
          dataIndex: 'name'
        }, {
          title: '登录次数',
          dataIndex: 'loginCount'
        }, {
          title: '登录时间',
          dataIndex: 'lastLoginTime'
        }, {
          title: '登录ip',
          dataIndex: 'lastLoginIp'
        }, {
          title: '浏览器',
          dataIndex: 'browser'
        }, {
          title: '操作系统',
          dataIndex: 'os'
        }, {
          title: '操作',
          scopedSlots: { customRender: 'operation' },
          width: '150px'
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return OnlineUser(this.queryParam).then((res) => {
          return res
        })
      },
      selectedRowKeys: [],
      selectedRows: []
    }
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
    kickOut (id) {
      KickOut(id).then(res => {
        if (res.success) {
          this.$message.success('下线成功')
          this.handleOk()
        } else {
          this.$message.error('下线失败：' + res.message)
        }
      })
    },
    batchKickOut () {
      BatchKickOut(this.selectedRowKeys).then(res => {
        if (res.success) {
          this.$message.success('下线成功')
          this.handleOk()
        } else {
          this.$message.error('下线失败：' + res.message)
        }
      })
    }
  }
}
</script>
