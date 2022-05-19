<template>

  <a-modal
    :title="(flag === 0 ? '新增' : '编辑') + '任务'"
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
          label="任务名称"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-input placeholder="请输入任务名称" v-decorator="['name',{rules: [{required: true, min: 1, message: '请输入任务名称！'}]}]" />
        </a-form-item>
        <a-form-item
          label="任务分组"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback>
          <a-select placeholder="请选择任务分组" v-decorator="['group',{rules: [{required: true, min: 1, message: '请选择任务分组！'}]}]">
            <a-select-option :key="item.value" v-for="item in $options.filters.dictItems('SYS_TASK_GROUP')" :value="item.value">
              {{ item.name }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          label="cron表达式"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-input placeholder="请输入cron表达式" v-decorator="['cron',{rules: [{required: true, min: 1, message: '请输入cron表达式！'}]}]" />
        </a-form-item>
        <a-form-item
          label="class类名"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-input placeholder="请输入class类名" v-decorator="['className',{rules: [{required: true, min: 1, message: '请输入class类名！'}]}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="任务状态"
        >
          <a-radio-group v-decorator="['status', {rules: [{ required: true, message: '请选择任务状态！' }]}]">
            <a-radio
              v-for="(item) in $options.filters.dictItems('SYS_TASK_STATUS')"
              :key="item.name"
              :value="item.value"
            >{{ item.name }}</a-radio>
          </a-radio-group>
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
  import { Add, Edit } from '@/api/task'
  export default {
    name: 'TaskEdit',
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
          this.form.getFieldDecorator('group', { initialValue: record.group })
          this.form.getFieldDecorator('cron', { initialValue: record.cron })
          this.form.getFieldDecorator('className', { initialValue: record.className })
          this.form.getFieldDecorator('status', { initialValue: record.status })
          this.form.getFieldDecorator('description', { initialValue: record.description })
        } else {
          // 任务默认启动状态
          this.form.getFieldDecorator('status', { initialValue: 0 })
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
