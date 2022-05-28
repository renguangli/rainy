<template>
  <a-modal
    :title="(flag === 0 ? '新增' : '编辑') + '配置'"
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
          label="配置名称"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-input placeholder="请输入配置名称" v-decorator="['name',{rules: [{required: true, min: 1, message: '请输入配置名称！'}]}]" />
        </a-form-item>
        <a-form-item
          label="配置分类"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-select placeholder="请选择配置分类" v-decorator="['categoryCode',{rules: [{required: true, min: 1, message: '请选择配置分类！'}]}]">
            <a-select-option :key="item.value" v-for="item in $options.filters.dictItems('SYS_CONFIG_CATEGORY')" :value="item.value">
              {{ item.name }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          label="唯一编码"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-input :disabled="flag !== 0" placeholder="请输入唯一编码" v-decorator="['code',{rules: [{required: true, min: 1, message: '请输入唯一编码！'}]}]" />
        </a-form-item>
        <a-form-item
          label="数据类型"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-select @change="handleSelect" placeholder="请选择数据类型" v-decorator="['dataType',{rules: [{required: true, min: 1, message: '请选择数据类型！'}]}]">
            <a-select-option :key="item.value" v-for="item in $options.filters.dictItems('SYS_DATA_TYPE')" :value="item.value">
              {{ item.name }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          label="值"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-input-number v-if="dataType === 'int'" style="width: 100%" v-decorator="['value', {rules: [{required: true, message: '请输入值！'}]}]" :min="1" :max="86400" />
          <a-input v-if="dataType === 'string'" placeholder="请输入值" v-decorator="['value',{rules: [{required: true, min: 1, message: '请输入值！'}]}]" />
          <a-radio-group v-if="dataType === 'boolean'" v-decorator="['value', {rules: [{ required: true, message: '请选择！' }]}]">
            <a-radio
              v-for="(item) in $options.filters.dictItems('SYS_YES_OR_NO')"
              :key="item.name"
              :value="item.value"
            >{{ item.name }}&nbsp;&nbsp;&nbsp</a-radio>
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
  import { Add, Edit } from '@/api/config'
  export default {
    name: 'ConfigEdit',
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
        dataType: 'string',
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
          this.form.getFieldDecorator('categoryCode', { initialValue: record.categoryCode })
          this.form.getFieldDecorator('code', { initialValue: record.code })
          this.form.getFieldDecorator('dataType', { initialValue: record.dataType })
          this.dataType = record.dataType
          this.form.getFieldDecorator('value', { initialValue: record.value })
          this.form.getFieldDecorator('description', { initialValue: record.description })
        } else {
          this.form.getFieldDecorator('dataType', { initialValue: this.dataType })
        }
      },
      handleSelect (val) {
        this.dataType = val
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
        this.dataType = 'string'
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
