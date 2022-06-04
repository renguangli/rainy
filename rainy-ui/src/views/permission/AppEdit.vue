<template>
  <a-modal
    :title="(flag === 0 ? '新增' : '编辑') + '应用'"
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
          label="应用名称"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-input placeholder="请输入应用名称" v-decorator="['name',{rules: [{required: true, min: 1, message: '请输入应用名称！'}]}]" />
        </a-form-item>
        <a-form-item
          label="唯一编码"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-input placeholder="请输入唯一编码" v-decorator="['code',{rules: [{required: true, min: 1, message: '请输入唯一编码！'}]}]" />
        </a-form-item>
        <a-form-item
          label="重定向地址"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-input placeholder="请输入重定向地址" v-decorator="['redirect',{rules: [{required: true, min: 1, message: '请输入重定向地址！'}]}]" />
        </a-form-item>
        <a-form-item
          label="密钥"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-input placeholder="请输入密钥" v-decorator="['secretKey',{rules: [{required: true, min: 1, message: '请输入密钥！'}]}]" />
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
  import { Add, Edit } from '@/api/app'
  export default {
    name: 'AppEdit',
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
        this.flag = flag
        if (flag !== 0) {
          this.form.getFieldDecorator('id', { initialValue: record.id })
          this.form.getFieldDecorator('name', { initialValue: record.name })
          this.form.getFieldDecorator('code', { initialValue: record.code })
          this.form.getFieldDecorator('redirect', { initialValue: record.redirect })
          this.form.getFieldDecorator('secretKey', { initialValue: record.secretKey })
          this.form.getFieldDecorator('description', { initialValue: record.description })
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
