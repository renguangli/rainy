<template>

  <a-modal
    :title="(flag === 0 ? '新增' : '编辑') + '组织'"
    :width="500"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    :destroyOnClose="true"
  >
    <a-spin :spinning="formLoading">
      <a-form :form="form" >
        <a-form-item v-show="false" >
          <a-input v-decorator="['id']" />
        </a-form-item>
        <a-form-item
          label="组织名称"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-input placeholder="请输入组织名称" v-decorator="['name',{rules: [{required: true, min: 1, message: '请输入组织名称！'}]}]" />
        </a-form-item>
        <a-form-item
          label="父级组织"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-tree-select
            v-decorator="['parentId', {rules: [{ required: true, message: '请选择父级组织！' }]}]"
            style="width: 100%"
            :dropdownStyle="{ maxHeight: '300px', overflow: 'auto' }"
            :treeData="orgTree"
            :replace-fields="replaceFields"
            placeholder="请选择父级组织"
            treeDefaultExpandAll
          >
          </a-tree-select>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="排序"
        >
          <a-input-number style="width: 100%" v-decorator="['sort', {rules: [{required: true, message: '请输入排序！'}]}]" :min="1" :max="10000" />
        </a-form-item>
        <a-form-item
          label="描述"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-textarea :rows="4" placeholder="请输入描述" v-decorator="['description']"></a-textarea>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { GetOrgTree, Add, Edit } from '@/api/org'
  export default {
    name: 'OrgEdit',
    data () {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: `5` }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 }
        },
        visible: false,
        formLoading: false,
        confirmLoading: false,
        replaceFields: { title: 'name', key: 'id', value: 'id' },
        orgTree: [],
        form: this.$form.createForm(this),
        flag: 0
      }
    },
    mounted () {
    },
    methods: {
      // 打开页面初始化
      open (flag, record) {
        this.visible = true
        this.getOrgTree()
        this.flag = flag
        if (flag !== 0) {
          this.form.getFieldDecorator('id', { initialValue: record.id })
          this.form.getFieldDecorator('parentId', { initialValue: record.parentId })
          this.form.getFieldDecorator('name', { initialValue: record.name })
          this.form.getFieldDecorator('description', { initialValue: record.description })
          this.form.getFieldDecorator('sort', { initialValue: record.sort })
        } else {
          this.form.getFieldDecorator('sort', { initialValue: 99 })
          this.form.getFieldDecorator('parentId', { initialValue: 0 })
        }
      },
      handleOk () {
        const { form: { validateFields } } = this
        this.confirmLoading = true
        validateFields((errors, values) => {
          if (errors) {
            this.confirmLoading = false
          } else {
            if (this.flag === 0) {
              this.add(values)
            } else {
              this.edit(values)
            }
          }
        })
      },
      handleCancel () {
        this.form.resetFields()
        this.confirmLoading = false
        this.visible = false
      },
      getOrgTree () {
        this.formLoading = true
        GetOrgTree().then(res => {
          if (res.success) {
            this.orgTree = [{
              'id': '0',
              'value': '0',
              'name': 'root',
              'children': res.data
            }]
          } else {
            this.$message.warning(res.message)
          }
        }).finally(() => {
          this.formLoading = false
        })
      },
      add (values) {
        Add(values).then(res => {
          if (res.success) {
            this.$message.success('新增成功')
            this.$emit('ok', values)
            this.handleCancel()
          } else {
            this.$message.error('新增失败：' + res.message)
          }
        }).finally((res) => {
          this.confirmLoading = false
        })
      },
      edit (values) {
        Edit(values).then(res => {
          if (res.success) {
            this.$message.success('编辑成功')
            this.$emit('ok', values)
            this.handleCancel()
          } else {
            this.$message.error('编辑失败：' + res.message)
          }
        }).finally((res) => {
          this.confirmLoading = false
        })
      }
    }
  }
</script>
