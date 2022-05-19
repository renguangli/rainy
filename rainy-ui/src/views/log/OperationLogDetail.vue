<template>
  <a-modal
    :title="'查看操作日志详情'"
    :width="1000"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
    :destroyOnClose="true"
  >
    <a-spin :spinning="formLoading">
      <a-form :form="form" >
        <a-form-item v-show="false" >
          <a-input v-decorator="['id']" />
        </a-form-item>
        <a-row :gutter="24">
          <a-col :md="12" :sm="24">
            <a-form-item
              label="操作人"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              hasFeedback
            >
              <a-input v-decorator="['username']" />
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item
              label="操作时间"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              hasFeedback
            >
              <a-input v-decorator="['datetime']" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="12" :sm="24">
            <a-form-item
              label="系统模块"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              hasFeedback
            >
              <a-input v-decorator="['module']" />
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item
              style="width: 100%"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="操作类型"
              hasFeedback
            >
              <a-select v-decorator="['operationTypeCode']">
                <a-select-option :key="item.value" v-for="item in $options.filters.dictItems('SYS_OPERATION_LOG_TYPE')" :value="item.value" @click="selectMenuType(item.value)">
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="12" :sm="24">
            <a-form-item
              label="操作详情"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              hasFeedback
            >
              <a-input v-decorator="['detail']" />
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item
              label="执行时长"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              hasFeedback
            >
              <a-input v-decorator="['processTime']" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="12" :sm="24">
            <a-form-item
              label="请求路径"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              hasFeedback
            >
              <a-input v-decorator="['path']" />
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item
              label="请求方法"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              hasFeedback
            >
              <a-input v-decorator="['method']" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="12" :sm="24">
            <a-form-item
              label="ip地址"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              hasFeedback
            >
              <a-input v-decorator="['remoteIp']" />
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item
              label="浏览器"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              hasFeedback
            >
              <a-input v-decorator="['browser']" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="12" :sm="24">
            <a-form-item
              label="操作系统"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              hasFeedback
            >
              <a-input v-decorator="['os']" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="12" :sm="24">
            <a-form-item
              label="class类名"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              hasFeedback
            >
              <a-input v-decorator="['className']" />
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item
              label="方法名"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              hasFeedback
            >
              <a-input v-decorator="['methodName']" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="12" :sm="24">
            <a-form-item
              label="方法参数"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              hasFeedback
            >
              <a-textarea v-decorator="['params']" />
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item
              label="返回结果"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              hasFeedback
            >
              <a-textarea v-decorator="['result']" />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import IconSelector from '@/components/IconSelector'
  export default {
    name: 'OperationLogDetail',
    components: { IconSelector },
    data () {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 }
        },
        visible: false,
        confirmLoading: false,
        formLoading: false,
        form: this.$form.createForm(this)
      }
    },
    methods: {
      // 打开页面初始化
      open (record) {
        this.visible = true
        this.form.getFieldDecorator('id', { initialValue: record.id })
        this.form.getFieldDecorator('username', { initialValue: record.username })
        this.form.getFieldDecorator('datetime', { initialValue: record.datetime })
        this.form.getFieldDecorator('module', { initialValue: record.module })
        this.form.getFieldDecorator('operationTypeCode', { initialValue: record.operationTypeCode })
        this.form.getFieldDecorator('detail', { initialValue: record.detail })
        this.form.getFieldDecorator('processTime', { initialValue: record.processTime })
        this.form.getFieldDecorator('path', { initialValue: record.path })
        this.form.getFieldDecorator('method', { initialValue: record.method })
        this.form.getFieldDecorator('remoteIp', { initialValue: record.remoteIp })
        this.form.getFieldDecorator('browser', { initialValue: record.browser })
        this.form.getFieldDecorator('os', { initialValue: record.os })
        this.form.getFieldDecorator('className', { initialValue: record.className })
        this.form.getFieldDecorator('methodName', { initialValue: record.methodName })
        this.form.getFieldDecorator('params', { initialValue: record.params })
        this.form.getFieldDecorator('result', { initialValue: record.result })
      },
      setFieldValue (field, value) {
        this.form.resetFields(`${field}`, [])
        this.form.getFieldDecorator(field, { initialValue: value })
      },
      handleSubmit () {
        this.handleCancel()
      },
      handleCancel () {
        this.form.resetFields()
        this.confirmLoading = false
        this.visible = false
      }
    }
  }
</script>
