<template>
  <a-modal
    title="分配菜单"
    :width="350"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
    :destroyOnClose="true"
  >
    <a-spin :spinning="formLoading">
      <a-tree
        v-model="checkedKeys"
        :treeData="menuTree"
        :defaultExpandAll="false"
        :replaceFields="replaceFields"
        :selectedKeys="checkedKeys"
        :checkable="true"
        @check="handleCheck"
      />
    </a-spin>
  </a-modal>
</template>

<script>
  import { GetMenuTree } from '@/api/menu'
  import { GetMenuIds, AssignMenu } from '@/api/role'
  export default {
    name: 'MenuAssign',
    data () {
      return {
        visible: false,
        confirmLoading: false,
        formLoading: false,
        replaceFields: { key: 'id' },
        checkedKeys: [],
        checkedNodes: [],
        halfCheckedKeys: [],
        changed: false,
        menuTree: [],
        record: {}
      }
    },
    methods: {
      // 打开页面初始化
      open (record) {
        this.record = record
        this.visible = true
        // 初始化菜单树
        this.getMenuTree()
      },
      getMenuTree () {
        this.formLoading = true
        GetMenuTree().then(res => {
          if (res.success) {
            this.menuTree = res.data
            this.getMenuIds()
          } else {
            this.$message.warning(res.message)
          }
        }).finally(() => {
          this.formLoading = false
        })
      },
      getMenuIds () {
        const param = { id: this.record.id, name: this.record.name }
        GetMenuIds(param).then(res => {
          if (res.success) {
            this.checkedKeys = res.data
          } else {
            this.$message.warning(res.message)
          }
        })
      },
      handleCheck (checkedKeys, e) {
        this.changed = true
        this.checkedNodes = e.checkedNodes
        this.halfCheckedKeys = e.halfCheckedKeys
      },
      getRoleMenuList () {
        const param = {
          id: this.record.id,
          name: this.record.name,
          ids: [],
          halfIds: [],
          names: []
        }
        this.checkedNodes.forEach(node => {
          param.ids.push(node.data.props.id)
          param.names.push(node.data.props.name)
        })
        for (const index in this.halfCheckedKeys) {
          param.halfIds.push(this.halfCheckedKeys[index])
          // todo 查询名称
          // param.names.push(this.halfCheckedKeys[index])
        }
        return param
        // const roleMenuList = []
        // for (const index in this.checkedKeys) {
        //   const roleMenuRel = {
        //     roleId: this.record.id,
        //     menuId: this.checkedKeys[index],
        //     all: true
        //   }
        //   roleMenuList.push(roleMenuRel)
        // }
        // for (const index in this.halfCheckedKeys) {
        //   const roleMenuRel = {
        //     roleId: this.record.id,
        //     menuId: this.halfCheckedKeys[index],
        //     all: false
        //   }
        //   roleMenuList.push(roleMenuRel)
        // }
        // return roleMenuList
      },
      handleSubmit () {
        if (!this.changed) {
          this.handleCancel()
          return
        }
        this.confirmLoading = true
        AssignMenu(this.getRoleMenuList()).then(res => {
          if (res.success) {
            this.$message.info('分配成功')
            this.handleCancel()
          } else {
            this.$message.error('分配失败:' + res.message)
          }
        }).finally(() => {
          this.confirmLoading = false
        })
      },
      handleCancel () {
        this.visible = false
        this.formLoading = false
        this.confirmLoading = false
        this.checkedKeys = []
        this.checkedNodes = []
        this.changed = false
      }
    }
  }
</script>
