<template>

  <a-modal
    :title="(flag === 0 ? '新增' : '编辑') + '用户'"
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
          label="用户名"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-input placeholder="请输入用户名" v-decorator="['username',{rules: [{required: true, min: 1, message: '请输入用户名！'}]}]" />
        </a-form-item>
        <a-form-item
          label="密码"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-input type="password" placeholder="请输入密码" v-decorator="['password',{rules: [{required: true, min: 1, message: '请输入密码！'}]}]" />
        </a-form-item>
        <a-form-item
          label="组织机构"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-tree-select
            v-decorator="['orgId', {rules: [{ required: true, message: '请选择组织机构！' }]}]"
            style="width: 100%"
            :dropdownStyle="{ maxHeight: '300px', overflow: 'auto' }"
            :treeData="orgTree"
            :replace-fields="replaceFields"
            placeholder="请选择组织机构"
            treeDefaultExpandAll
          >
          </a-tree-select>
        </a-form-item>
        <a-form-item
          label="职位"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-select
            v-decorator="['positionId', {rules: [{ required: true, message: '请选择职位！' }]}]"
            show-search
            placeholder="请选择职位"
            option-filter-prop="children"
            :filter-option="filterOption"
            @focus="handleFocus"
            @blur="handleBlur"
            @change="handleChange"
          >
            <a-select-option v-for="item in positions" :key="item.name" :value="item.id">
              {{ item.name }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          label="昵称"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-input placeholder="请输入昵称" v-decorator="['name',{rules: [{required: true, min: 1, message: '请输入昵称！'}]}]" />
        </a-form-item>
        <a-form-item
          label="手机号"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-input placeholder="请输入手机号" v-decorator="['telephone',{rules: [{required: true, min: 1, message: '请输入手机号！'}]}]" />
        </a-form-item>
        <a-form-item
          label="邮箱"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-input placeholder="请输入邮箱" v-decorator="['email',{rules: [{required: true, min: 1, message: '请输入邮箱！'},{type: 'email', message: '邮箱格式不正确'}]}]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { GetOrgTree } from '@/api/org'
  import { List as GetPositions } from '@/api/position'
  import { Add, Edit } from '@/api/user'
  export default {
    name: 'UserEdit',
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
        positions: [],
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
        this.getPositions()
        this.flag = flag
        if (flag !== 0) {
          this.form.getFieldDecorator('id', { initialValue: record.id })
          this.form.getFieldDecorator('username', { initialValue: record.username })
          this.form.getFieldDecorator('password', { initialValue: record.password })
          this.form.getFieldDecorator('orgId', { initialValue: record.orgId })
          this.form.getFieldDecorator('positionId', { initialValue: record.positionId })
          this.form.getFieldDecorator('name', { initialValue: record.name })
          this.form.getFieldDecorator('telephone', { initialValue: record.telephone })
          this.form.getFieldDecorator('email', { initialValue: record.email })
        } else {
          this.form.getFieldDecorator('orgId', { initialValue: record })
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
            this.orgTree = res.data
          } else {
            this.$message.warning(res.message)
          }
        }).finally(() => {
          this.formLoading = false
        })
      },
      getPositions () {
        GetPositions({ paged: false }).then(res => {
          if (res.success) {
            this.positions = res.data.records
          } else {
            this.$message.warning(res.message)
          }
        })
      },
      handleChange (value) {
        console.log(`selected ${value}`)
      },
      handleBlur () {
        console.log('blur')
      },
      handleFocus () {
        console.log('focus')
      },
      filterOption (input, option) {
        return (
          option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
        )
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
