<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="24">
            <a-form-item label="菜单名称">
              <a-input v-model="queryParam.name" placeholder="请输入菜单名称"/>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <a-form-item label="菜单类型">
              <a-select v-model="queryParam.typeCode" placeholder="请选择菜单类型">
                <a-select-option :key="item.value" v-for="item in $options.filters.dictItems('SYS_MENU_TYPE')" :value="item.value">
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="$refs.table.refresh()">查询</a-button>
              <a-button style="margin-left: 8px" @click="queryParam = {}">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <s-table
      ref="table"
      size="middle"
      :rowKey="(record) => record.id"
      :alert="false"
      :columns="columns"
      :data="loadData"
      :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onChange }"
      :showPagination="false"
      :expandRowByClick="true"
    >
      <template slot="operator">
        <a-button type="primary" icon="plus" @click="$refs.editor.open(0)">新增菜单</a-button>
        <export-excel
          ref="exportExcel"
          @exportExcel="exportExcel"
        />
      </template>
      <span slot="icon" slot-scope="text">
        <a-icon v-if="text" :type="text"></a-icon>
      </span>
      <span slot="type" slot-scope="text">
        <a-tag v-if="text === 'menu'" color="#2db7f5">
          {{ 'SYS_MENU_TYPE' | dictItemValue(text) }}
        </a-tag>
        <a-tag v-if="text === 'button'" color="#108ee9">
          {{ 'SYS_MENU_TYPE' | dictItemValue(text) }}
        </a-tag>
        <a-tag v-if="text === 'catalog_breadcrumb' || text === 'catalog'" color="#87d068">
          {{ 'SYS_MENU_TYPE' | dictItemValue(text) }}
        </a-tag>
      </span>
      <span slot="target" slot-scope="text">
        {{ 'SYS_MENU_OPEN_TYPE' | dictItemValue(text) }}
      </span>
      <span slot="operation" slot-scope="text, record">
        <template>
          <a @click="$refs.editor.open(1, record)">编辑</a>
          <a-divider type="vertical"/>
          <a-popconfirm placement="topRight" :title="'确定删除菜单[' + record.name +']吗？'" @confirm="del(record)">
            <a>删除</a>
          </a-popconfirm>
        </template>
      </span>
    </s-table>
    <editor ref="editor" @ok="handleOk"></editor>
  </a-card>
</template>

<script>
import { STable, ExportExcel } from '@/components'
import { List, Del, Export } from '@/api/menu'
import editor from './MenuEdit'

export default {
  name: 'Menu',
  components: {
    STable,
    editor,
    ExportExcel
  },
  data () {
    return {
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
          title: '菜单名称',
          dataIndex: 'title',
          width: '200px'
        }, {
          title: '菜单类型',
          dataIndex: 'typeCode',
          scopedSlots: { customRender: 'type' }
        }, {
          title: '打开方式',
          dataIndex: 'target',
          scopedSlots: { customRender: 'target' }
        }, {
          title: '请求地址',
          dataIndex: 'path'
        }, {
          title: 'url地址',
          dataIndex: 'url',
          checked: false
        }, {
          title: '请求方法',
          dataIndex: 'method',
          checked: false
        }, {
          title: '前端组件',
          dataIndex: 'component',
          checked: false
        }, {
          title: '描述',
          dataIndex: 'description',
          ellipsis: true
        }, {
          title: '排序',
          dataIndex: 'sort'
        }, {
          title: '创建时间',
          dataIndex: 'createTime',
          checked: false
        }, {
          title: '创建者',
          dataIndex: 'createBy',
          checked: false
        }, {
          title: '更新时间',
          dataIndex: 'updateTime',
          checked: false
        }, {
          title: '更新者',
          dataIndex: 'updateBy',
          checked: false
        }, {
          title: '操作',
          scopedSlots: { customRender: 'operation' },
          width: '140px'
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return List(Object.assign(parameter, this.queryParam)).then((res) => {
          return res
        })
      },
      selectedRowKeys: [],
      selectedRows: []
    }
  },
  methods: {
    handleOk () {
      // 新增/修改 成功时，重载列表
      this.$refs.table.refresh()
      this.$refs.table.clearSelected()
    },
    onChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    del (record) {
      const param = { id: record.id, name: record.name }
      Del(param).then(res => {
        if (res.success) {
          this.$message.success('删除成功')
          this.handleOk()
        } else {
          this.$message.error('删除失败：' + res.message)
        }
      })
    },
    exportExcel () {
      Export().then(res => {
        this.$refs.exportExcel.download(res)
      })
    }
  }
}
</script>
<style lang="less">
.table-operator {
  margin-bottom: 18px;
}
button {
  margin-right: 8px;
}
</style>
