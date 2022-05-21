<template>
  <a-modal
    :title="(flag === 0 ? '新增' : '编辑') + '字典项'"
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
          label="所属字典"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-select
            placeholder="请选择字典"
            show-search
            option-filter-prop="children"
            :filter-option="filterOptions"
            v-decorator="['dictCode',{rules: [{required: true, min: 1, message: '请选择字典！'}]}]">
            <a-select-option :key="item.dictCode" v-for="item in $store.getters.getDicts()" :value="item.dictCode">
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
          <a-input disabled="true" placeholder="请输入唯一编码" v-decorator="['code',{rules: [{required: true, min: 1, message: '请输入唯一编码！'}]}]" />
        </a-form-item>
        <a-form-item
          label="字典项值"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-input placeholder="请输入字典项值" v-decorator="['value',{rules: [{required: true, min: 1, message: '请输入字典项值！'}]}]" />
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
  import { Add, Edit } from '@/api/dictItem'
  export default {
    name: 'DictItemEdit',
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
      open (flag, record, dictCode) {
        this.visible = true
        this.flag = flag
        if (flag !== 0) {
          this.form.getFieldDecorator('id', { initialValue: record.id })
          this.form.getFieldDecorator('dictCode', { initialValue: record.dictCode })
          this.form.getFieldDecorator('code', { initialValue: record.code })
          this.form.getFieldDecorator('value', { initialValue: record.value })
          this.form.getFieldDecorator('description', { initialValue: record.description })
          this.form.getFieldDecorator('sort', { initialValue: record.sort })
        } else {
          this.form.getFieldDecorator('dictCode', { initialValue: dictCode })
          this.form.getFieldDecorator('sort', { initialValue: 99 })
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
      filterOptions (input, option) {
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
