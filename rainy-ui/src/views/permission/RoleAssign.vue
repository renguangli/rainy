<template>

  <a-modal
    :title="'分配角色'"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    :destroyOnClose="true"
  >
    <a-table
      class="role-assign"
      size="middle"
      :loading="tableLoading"
      :rowKey="(record) => record.id"
      :alert="{ show: true, clear: () => { this.selectedRowKeys = [] }}"
      :columns="columns"
      :data-source="data"
      :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: handleChange }"
      :pagination="page"
    >
    </a-table>
  </a-modal>
</template>

<script>
  import { STable } from '@/components'
  import { List } from '@/api/role'
  import { AssignRoles, GetRoleIds } from '@/api/user'
  export default {
    name: 'RoleAssign',
    components: {
      STable
    },
    data () {
      return {
        visible: false,
        confirmLoading: false,
        tableLoading: false,
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
            dataIndex: 'description'
          }
        ],
        data: [],
        page: {
          defaultCurrent: 1,
          current: 1,
          pageSize: 6,
          total: 10,
          showTotal: (total, range) => `${range[0]}-${range[1]} of ${total} items`,
          onChange: (current, pageSize) => {
            this.page.current = current
            this.page.pageSize = pageSize
            this.loadData()
          }
        },
        selectedRowKeys: [],
        selectedRows: [],
        record: {}
      }
    },
    methods: {
      // 打开页面初始化
      open (record) {
        this.record = record
        this.visible = true
        this.loadData()
      },
      handleChange (selectedRowKeys, selectedRows) {
        this.selectedRowKeys = selectedRowKeys
        this.selectedRows = selectedRows
      },
      loadData () {
        const param = { current: this.page.current, size: this.page.pageSize }
        List(param).then((res) => {
          this.data = res.data.records
          this.page.current = res.data.current
          this.page.pageSize = res.data.size
          this.page.total = res.data.total
          this.getRoleIds()
        })
      },
      getRoleIds () {
        GetRoleIds(this.record.id).then(res => {
          this.selectedRowKeys = res.data
        })
      },
      handleOk () {
        this.confirmLoading = true
        const param = {
          id: this.record.id,
          name: this.record.username,
          ids: [],
          names: []
        }
        this.selectedRows.forEach(v => {
          param.ids.push(v.id)
          param.names.push(v.name)
        })
        AssignRoles(param).then(res => {
          if (res.success) {
            this.$message.success('分配成功')
            this.handleCancel()
          } else {
            this.$message.error('分配失败：' + res.message)
          }
        }).finally(() => {
          this.confirmLoading = false
        })
      },
      handleCancel () {
        this.visible = false
        this.confirmLoading = false
        this.formLoading = false
        this.selectedRowKeys = []
      }
    }
  }
</script>
<style>
.role-assign > .s-table-tool {
  display: none;
}
</style>
