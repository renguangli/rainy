<template>
  <a-modal
    :title="(flag === 0 ? '发布' : '编辑') + '公告'"
    :width="1000"
    :visible="visible"
    :confirmLoading="confirmLoading"
    ok-text="发布"
    cancel-text="取消"
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
          label="公告标题"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          hasFeedback
        >
          <a-input placeholder="请输入公告标题" v-decorator="['title',{rules: [{required: true, min: 1, message: '请输入公告标题！'}]}]" />
        </a-form-item>
        <a-form-item
          label="公告内容"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <WangEditor ref="editor" :value="content"></WangEditor>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="通知人"
        >
          <a-transfer
            :data-source="mockData"
            show-search
            :filter-option="filterOption"
            :target-keys="targetKeys"
            :render="item => item.title"
            @change="handleChange"
            @search="handleSearch"
          />
<!--          <a-input placeholder="请输入通知人" style="width: 100%" v-decorator="['userId', {rules: [{required: true, message: '请输入通知人！'}]}]" :min="1" :max="10000" />-->
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { Add, Edit } from '@/api/notice'
  import WangEditor from '@/components/Editor/WangEditor'

  export default {
    name: 'NoticeEdit',
    components: {
      WangEditor
    },
    data () {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: `3` }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 20 }
        },
        visible: false,
        formLoading: false,
        confirmLoading: false,
        content: '',
        mockData: [],
        targetKeys: [],
        form: this.$form.createForm(this),
        flag: 0
      }
    },
    mounted () {
      this.getMock()
    },
    methods: {
      // 打开页面初始化
      open (flag, record) {
        this.visible = true
        this.flag = flag
        if (flag !== 0) {
          this.$nextTick(() => {
            this.form.setFieldsValue(
              {
                id: record.id,
                title: record.title,
                userId: record.userId
              }
            )
            this.content = record.content
          })
        }
      },
      handleOk () {
        const { form: { validateFields } } = this
        this.confirmLoading = true
        validateFields((errors, values) => {
          values.content = this.content
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
        this.content = ''
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
      },
      getMock () {
        const targetKeys = []
        const mockData = []
        for (let i = 0; i < 20; i++) {
          const data = {
            key: i.toString(),
            title: `content${i + 1}`,
            description: `description of content${i + 1}`,
            chosen: Math.random() * 2 > 1
          }
          if (data.chosen) {
            targetKeys.push(data.key)
          }
          mockData.push(data)
        }
        this.mockData = mockData
        this.targetKeys = targetKeys
      },
      filterOption (inputValue, option) {
        return option.description.indexOf(inputValue) > -1
      },
      handleChange (targetKeys, direction, moveKeys) {
        console.log(targetKeys, direction, moveKeys)
        this.targetKeys = targetKeys
      },
      handleSearch (dir, value) {
        console.log('search:', dir, value)
      }
    }
  }
</script>
