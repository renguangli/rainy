<template>
  <a-modal
    :title="(flag === 0 ? '新增' : '编辑') + '菜单'"
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
              label="菜单名称"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              hasFeedback
            >
              <a-input placeholder="请输入菜单名称" v-decorator="['title',{rules: [{required: true, min: 1, message: '请输入菜单名称！'}]}]" />
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item
              style="width: 100%"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="菜单类型"
              hasFeedback
            >
              <a-select placeholder="请选择菜单类型" v-decorator="['typeCode', {rules: [{required: true, min: 1, message: '请选择菜单类型！'}]}]">
                <a-select-option :key="item.value" v-for="item in $options.filters.dictItems('SYS_MENU_TYPE')" :value="item.value" @click="selectMenuType(item.value)">
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="12" :sm="24">
            <div>
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="父级菜单"
                has-feedback
              >
                <a-tree-select
                  v-decorator="['parentId', {rules: [{ required: true, message: '请选择父级菜单！' }]}]"
                  style="width: 100%"
                  :dropdownStyle="{ maxHeight: '300px', overflow: 'auto' }"
                  :treeData="menuTree"
                  :replace-fields="{ value: 'id', key: 'id' }"
                  placeholder="请选择父级菜单"
                  treeDefaultExpandAll
                >
                  <span slot="title" slot-scope="{ id }">{{ id }}
                  </span>
                </a-tree-select>
              </a-form-item>
            </div>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <span slot="label">
                <a-tooltip title="内链、外链、组件">
                  <a-icon type="question-circle-o" />
                </a-tooltip>&nbsp;
                打开方式
              </span>
              <a-radio-group :disabled="openTypeDisabled" v-decorator="['target', {rules: [{ required: true, message: '请选择打开方式！' }]}]">
                <a-radio
                  v-for="(item) in $options.filters.dictItems('SYS_MENU_OPEN_TYPE')"
                  :key="item.name"
                  :value="item.value"
                  :disabled="type === 'menu' && item.value === 'none' ? targetDisable : false"
                  @change="selectTarget(item.value)"
                >{{ item.name }}</a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="12" :sm="24">
            <div>
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="图标"
              >
                <a-input placeholder="请选择图标" disabled="disabled" v-decorator="['icon']" >
                  <a-icon slot="addonAfter" @click="visibleIcon = true" type="setting" />
                </a-input>
              </a-form-item>
            </div>
          </a-col>
          <a-col :md="12" :sm="24">
            <div>
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                hasFeedback
              >
                <span slot="label">
                  <a-tooltip title="内外链接地址，例：https://www.baidu.com">
                    <a-icon type="question-circle-o" />
                  </a-tooltip>&nbsp;
                  链接地址
                </span>
                <a-input placeholder="请输入链接地址" :disabled="urlDisabled" v-decorator="['url', {rules: [{required: urlRequired, message: '请输入链接地址！'}]}]" />
              </a-form-item>
            </div>
          </a-col>
        </a-row>
        <a-row :gutter="24" >
          <a-col :md="12" :sm="24">
            <div>
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                hasFeedback
              >
                <span slot="label">
                  <a-tooltip title="前端views文件夹下路径，例：permission/Menu。注：目录级填写：RouteView(不带面包屑)，PageView(带面包屑)，菜单级内链打开http链接填写：Iframe">
                    <a-icon type="question-circle-o" />
                  </a-tooltip>&nbsp;
                  前端组件
                </span>
                <a-input placeholder="请输入前端组件" :disabled="componentDisabled" prop="component" v-decorator="['component',{rules: [{required: componentRequired, message: '请输入前端组件！'}]}]"/>
              </a-form-item>
            </div>
          </a-col>
          <a-col :md="12" :sm="24">
            <div v-if="pathVisible">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                hasFeedback
              >
                <span slot="label">
                  <a-tooltip title="浏览器显示的URL，例：/menu">
                    <a-icon type="question-circle-o" />
                  </a-tooltip>&nbsp;
                  路由地址
                </span>
                <a-input placeholder="请输入路由" :disabled="pathDisabled" v-decorator="['path', {rules: [{required: pathRequired, message: '请输入路由！'}]}]" />
              </a-form-item>
            </div>
            <div v-if="methodVisible">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                hasFeedback
              >
                <span slot="label">
                  <a-tooltip title="浏览器显示的URL，例：/menu">
                    <a-icon type="question-circle-o" />
                  </a-tooltip>&nbsp;
                  请求方法
                </span>
                <a-select placeholder="请选请求方法" v-decorator="['method', {rules: [{required: true, min: 1, message: '请选请求方法！'}]}]">
                  <a-select-option :key="item.value" v-for="item in $options.filters.dictItems('SYS_HTTP_METHOD')" :value="item.value">
                    {{ item.name }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </div>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="12" :sm="24">
            <a-form-item
              style="width: 100%"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="应用"
              hasFeedback
            >
              <a-select placeholder="请选择应用" v-decorator="['appCode', {rules: [{required: true, min: 1, message: '请选择应用！'}]}]">
                <a-select-option :key="item.code" v-for="item in $store.getters.apps" :value="item.code">
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="排序"
            >
              <a-input-number style="width: 100%" v-decorator="['sort', {rules: [{required: true, message: '请输入排序！'}]}]" :min="1" :max="10000" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="12" :sm="24">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="是否可见"
            >
              <a-switch checkedChildren="是" unCheckedChildren="否" v-decorator="['show', { valuePropName: 'checked' }]"/>
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <div>
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="描述"
              >
                <a-input placeholder="请输入描述" v-decorator="['description']" />
              </a-form-item>
            </div>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
    <a-modal
      :width="850"
      :visible="visibleIcon"
      @cancel="handleCancelIcon"
      footer=""
      :mask="false"
      :closable="false"
      :destroyOnClose="true"
    >
      <icon-selector v-model="defaultIcon" @change="handleIconChange"/>
    </a-modal>
  </a-modal>
</template>

<script>
  import IconSelector from '@/components/IconSelector'
  import { List, Add, Edit } from '@/api/menu'
  export default {
    name: 'MenuEdit',
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
        visibleIcon: false,
        pathVisible: true,
        methodVisible: false,
        confirmLoading: false,
        menuTree: [],
        pathDisabled: false,
        pathRequired: true,
        urlDisabled: false,
        urlRequired: true,
        componentDisabled: false,
        componentRequired: true,
        // 默认图标
        defaultIcon: 'pause-circle',
        formLoading: false,
        openTypeDisabled: false,
        targetDisable: true,
        openTypeDefault: [],
        openType: '',
        linkRequired: true,
        linkDisabled: false,
        type: '',
        form: this.$form.createForm(this),
        flag: 0
      }
    },
    methods: {
      init () {
        this.form.getFieldDecorator('parentId', { initialValue: 0 })
        this.form.getFieldDecorator('typeCode', { initialValue: 'menu' })
        this.form.getFieldDecorator('target', { initialValue: '_self' })
        this.form.getFieldDecorator('icon', { initialValue: this.defaultIcon })
        this.form.getFieldDecorator('component', { initialValue: 'Iframe' })
        this.form.getFieldDecorator('show', { initialValue: true })
        this.form.getFieldDecorator('sort', { initialValue: 99 })
        this.form.getFieldDecorator('appCode', { initialValue: 'sys' })
        this.componentDisabled = true
      },
      // 打开页面初始化
      open (flag, record) {
        // 初始化菜单树
        this.getMenuList()
        this.visible = true
        this.flag = flag
        if (flag !== 0) {
          this.form.getFieldDecorator('id', { initialValue: record.id })
          this.form.getFieldDecorator('parentId', { valuePropName: 'selected', initialValue: record.parentId })
          // 菜单名称
          this.form.getFieldDecorator('title', { initialValue: record.title })
          // 图标
          this.form.getFieldDecorator('icon', { initialValue: record.icon })
          // 是否展示
          this.form.getFieldDecorator('show', { initialValue: record.show })
          this.form.getFieldDecorator('appCode', { initialValue: record.appCode })
          this.selectMenuType(record.typeCode)
          this.selectTarget(record.target, record.typeCode)
          this.setFieldValue('component', record.component)
          this.setFieldValue('path', record.path)
          this.setFieldValue('url', record.url)
          this.setFieldValue('method', record.method)
          this.setFieldValue('sort', record.sort)
          this.setFieldValue('description', record.description)
        } else {
          this.init()
        }
      },
      /**
       * 选择菜单类型,初始化打开方式
       */
      selectMenuType (typeCode) {
        this.type = typeCode
        this.setFieldValue('typeCode', typeCode)
        if (typeCode === 'catalog_breadcrumb') {
          this.selectTarget('_component')
          this.urlDisabled = true
          this.urlRequired = false
          this.componentDisabled = true
          this.componentRequired = true
          this.pathDisabled = true
          this.pathRequired = false
          this.openTypeDisabled = true
          this.methodVisible = false
          this.pathVisible = true
          this.setFieldValue('component', 'PageView')
          this.setFieldValue('url', '')
          this.setFieldValue('path', '')
        } else if (typeCode === 'catalog') {
          this.selectTarget('_component')
          this.urlDisabled = true
          this.urlRequired = false
          this.componentDisabled = true
          this.componentRequired = true
          this.pathDisabled = true
          this.pathRequired = false
          this.openTypeDisabled = true
          this.methodVisible = false
          this.pathVisible = true
          this.setFieldValue('component', 'RouteView')
          this.setFieldValue('url', '')
          this.setFieldValue('path', '')
        } else if (typeCode === 'menu') {
          this.selectTarget('_self')
          this.urlDisabled = false
          this.urlRequired = true
          this.componentRequired = false
          this.openTypeDisabled = false
          this.methodVisible = false
          this.pathVisible = true
          this.targetDisable = true
          // 清空组件
          this.setFieldValue('component', '')
        } else if (typeCode === 'button') {
          this.selectTarget('none')
          this.urlDisabled = false
          this.urlRequired = true
          this.componentRequired = false
          this.openTypeDisabled = false
          this.openTypeDisabled = true
          this.methodVisible = true
          this.pathVisible = false
          // 清空组件
          this.setFieldValue('component', '')
        }
      },
      /**
       * 选择打开方式执行方法
       */
      selectTarget (target, typeCode) {
        this.setFieldValue('target', target)
        if (target === '_self') {
          this.componentDisabled = true
          this.componentRequired = false
          this.pathDisabled = false
          this.pathRequired = true
          this.urlDisabled = false
          this.urlRequired = true
          this.setFieldValue('component', 'Iframe')
        } else if (target === 'none') {
          this.componentDisabled = true
          this.componentRequired = false
          this.pathDisabled = true
          this.pathRequired = false
          this.urlDisabled = false
          this.urlRequired = true
          this.setFieldValue('component', '')
        } else if (target === '_blank') {
          this.pathDisabled = true
          this.pathRequired = false
          this.componentDisabled = true
          this.componentRequired = false
          this.urlDisabled = false
          this.urlRequired = true
          this.setFieldValue('component', '')
          this.setFieldValue('path', '')
        } else if (target === '_component') {
          if (typeCode === 'catalog' || typeCode === 'catalog_breadcrumb') {
            this.componentDisabled = true
            this.componentRequired = false
            this.pathDisabled = true
            this.pathRequired = false
          } else {
            this.componentDisabled = false
            this.componentRequired = true
            this.pathDisabled = false
            this.pathRequired = true
          }
          this.urlDisabled = true
          this.urlRequired = false
          this.setFieldValue('url', '')
          this.setFieldValue('component', '')
        }
      },
      setFieldValue (field, value) {
        this.form.resetFields(`${field}`, [])
        this.form.getFieldDecorator(field, { initialValue: value })
      },
      handleIconChange (icon) {
        // console.log('new icon：'+icon)
        this.form.getFieldDecorator('icon', { initialValue: icon })
        this.visibleIcon = false
      },
      handleCancelIcon () {
        this.visibleIcon = false
      },
      handleSubmit () {
        const { form: { validateFields } } = this
        this.confirmLoading = true
        validateFields((errors, values) => {
          if (!errors) {
            values.name = values.title
            if (this.flag === 0) {
              this.add(values)
            } else {
              this.edit(values)
            }
          } else {
            this.confirmLoading = false
          }
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
      },
      getMenuList () {
        this.formLoading = true
        List().then(res => {
          if (res.success) {
            this.menuTree = [{
              'id': '0',
              'title': 'root',
              'children': res.data
            }]
            this.formLoading = false
          } else {
            this.$message.warning(res.message)
          }
        })
      },
      handleCancel () {
        this.form.resetFields()
        this.confirmLoading = false
        this.visible = false
        this.pathDisabled = false
        this.pathRequired = true
        this.urlDisabled = false
        this.urlRequired = true
        this.componentDisabled = false
        this.componentRequired = true
        this.defaultIcon = 'pause-circle'
        this.openTypeDisabled = false
        this.linkDisabled = false
        this.linkRequired = true
        this.methodVisible = false
        this.pathVisible = true
      }
    }
  }
</script>
