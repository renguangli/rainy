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
    <s-table
      class="role-assign"
      :loading="tableLoading"
      :rowKey="(record) => record.id"
      :alert="{ show: true, clear: () => { this.selectedRowKeys = [] }}"
      :columns="columns"
      :data="loadData"
      :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: handleChange }"
      :showPagination="true"
    >
    </s-table>
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
        loadData: parameter => {
          return List(Object.assign(parameter, {})).then((res) => {
            return res
          })
        },
        selectedRowKeys: [],
        record: {}
      }
    },
    methods: {
      // 打开页面初始化
      open (record) {
        this.record = record
        this.visible = true
        this.getRoleIds()
      },
      handleChange (selectedRowKeys) {
        this.selectedRowKeys = selectedRowKeys
      },
      getRoleIds () {
        GetRoleIds(this.record.id).then(res => {
          this.selectedRowKeys = res.data
        })
      },
      handleOk () {
        this.confirmLoading = true
        AssignRoles(this.record.id, this.selectedRowKeys).then(res => {
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
