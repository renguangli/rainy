<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="24">
            <a-form-item label="字典名称">
              <a-input v-model="queryParam.name" placeholder="请输入字典名称"/>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="唯一编码">
              <a-input v-model="queryParam.code" placeholder="请输入唯一编码"/>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="字典分类">
              <a-select v-model="queryParam.categoryCode" placeholder="请选择字典分类">
                <a-select-option :key="item.value" v-for="item in $options.filters.dictItems('SYS_DICT_CATEGORY')" :value="item.value">
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
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
      :rowKey="(record) => record.id"
      :alert="{ show: true, clear: () => { this.selectedRowKeys = [] }}"
      :columns="columns"
      :data="loadData"
      :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: handleChange }"
      :showPagination="true"
      :expandRowByClick="true"
      @change="handleChange"
    >
      <template slot="operator">
        <a-button type="primary" icon="plus" @click="$refs.editor.open(0)">新增字典</a-button>
        <a-popconfirm :disabled="selectedRowKeys.length < 1" placement="topRight" :title="'确定批量删除本字典吗?删除后可能导致系统出现故障！'" @confirm="batchDel">
          <a-button type="danger" icon="delete" :disabled="selectedRowKeys.length < 1">批量删除</a-button>
        </a-popconfirm>
      </template>
      <span slot="category" slot-scope="text">
        {{ 'SYS_DICT_CATEGORY' | dictItemValue(text) }}
      </span>
      <span slot="type" slot-scope="text">
        {{ 'SYS_DATA_TYPE' | dictItemValue(text) }}
      </span>
      <span slot="operation" slot-scope="text, record">
        <router-link :to="{path:'/dict/items',query:{dictCode:record.code}}">
          <a>字典项</a>
        </router-link>
        <a-divider type="vertical"/>
        <a-dropdown>
          <a class="ant-dropdown-link">
            更多 <a-icon type="down" />
          </a>
          <a-menu slot="overlay">
            <a-menu-item>
              <a @click="$refs.editor.open(1, record)">编辑</a>
            </a-menu-item>
            <a-menu-item>
              <a-popconfirm placement="topRight" :title="'确定删除本字典[' + [record.name] + ']以及字典项吗?'" @confirm="del(record)">
                <a>删除</a>
              </a-popconfirm>
            </a-menu-item>
          </a-menu>
        </a-dropdown>
      </span>
    </s-table>
    <editor ref="editor" @ok="handleOk"></editor>
  </a-card>
</template>

<script>

import { STable } from '@/components'
import editor from './DictEdit'
import { List, Del, BatchDel } from '@/api/dict'

export default {
  name: 'Dict',
  components: {
    STable,
    editor
  },
  data () {
    return {
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
          title: '字典名称',
          dataIndex: 'name',
          width: '160px'
        }, {
          title: '字典分类',
          dataIndex: 'categoryCode',
          scopedSlots: { customRender: 'category' }
        }, {
          title: '唯一编码',
          dataIndex: 'code'
        }, {
          title: '数据类型',
          dataIndex: 'type',
          scopedSlots: { customRender: 'type' }
        }, {
          title: '描述',
          dataIndex: 'description',
          ellipsis: true
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
          width: '150px'
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
      // 新增/修改/删除 成功时，重载列表
      this.$refs.table.refresh()
      this.$refs.table.clearSelected()
      // 更新字典
      this.$store.dispatch('setDictTree')
    },
    handleChange (selectedRowKeys, selectedRows) {
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
    batchDel () {
      const param = {
        ids: [],
        names: []
      }
      this.selectedRows.forEach(record => {
        param.ids.push(record.id)
        param.names.push(record.name)
      })
      BatchDel(param).then(res => {
        if (res.success) {
          this.$message.success('删除成功')
          this.$refs.table.clearSelected()
          this.handleOk()
        } else {
          this.$message.error('删除失败：' + res.message)
        }
      })
    }
  }
}
</script>
<style>
.table-operator {
  margin-bottom: 18px;
}
button {
  margin-right: 8px;
}
</style>
