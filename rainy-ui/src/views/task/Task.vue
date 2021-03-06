<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="32">
          <a-col :md="8" :sm="24">
            <a-form-item label="任务名称">
              <a-input v-model="queryParam.name" placeholder="请输入任务名称"/>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <a-form-item label="任务分组">
              <a-select v-model="queryParam.group" placeholder="请选择任务分组" @select="$refs.table.refresh()">
                <a-select-option :key="item.value" v-for="item in $options.filters.dictItems('SYS_TASK_GROUP')" :value="item.value">
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="$refs.table.refresh()">查询</a-button>
              <a-button style="margin-left: 8px" @click="queryParam = {};$refs.table.refresh()">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <s-table
      size="middle"
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
        <a-button type="primary" icon="plus" @click="$refs.editor.open(0)">新增任务</a-button>
        <a-popconfirm :disabled="selectedRowKeys.length < 1" placement="topRight" :title="'确定批量删除任务吗？'" @confirm="batchDel">
          <a-button type="danger" :disabled="selectedRowKeys.length < 1"><a-icon type="delete"/>批量删除</a-button>
        </a-popconfirm>
        <export-excel
          ref="exportExcel"
          @exportExcel="exportExcel"
        />
      </template>
      <span slot="group" slot-scope="text">
        {{ 'SYS_TASK_GROUP' | dictItemValue(text) }}
      </span>
      <span slot="status" slot-scope="text">
        <a-tag v-if="text === 0" color="#2db7f5">
          {{ 'SYS_TASK_STATUS' | dictItemValue(text) }}
        </a-tag>
        <a-tag v-if="text === 1" color="#f50">
          {{ 'SYS_TASK_STATUS' | dictItemValue(text) }}
        </a-tag>
      </span>
      <span slot="operation" slot-scope="text, record">
        <router-link :to="{path:'/task/log',query:{name:record.name,group:record.group}}">
          <a>执行日志</a>
        </router-link>
        <a-divider type="vertical"/>
        <a-dropdown>
          <a class="ant-dropdown-link">
            更多 <a-icon type="down" />
          </a>
          <a-menu slot="overlay">
            <a-menu-item>
              <a @click="resume(record)">启动</a>
            </a-menu-item>
            <a-menu-item>
              <a @click="pause(record)">暂停</a>
            </a-menu-item>
            <a-menu-item>
              <a @click="$refs.editor.open(1, record)">编辑</a>
            </a-menu-item>
            <a-menu-item>
              <a-popconfirm placement="topRight" :title="'确定删除任务[' + record.name + ']吗？'" @confirm="del(record)">
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
import { STable, ExportExcel } from '@/components'
import { List, Del, BatchDel, Pause, Resume, Export } from '@/api/task'
import editor from './TaskEdit'

export default {
  name: 'Task',
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
          title: '任务名称',
          dataIndex: 'name'
        }, {
          title: '任务分组',
          dataIndex: 'group',
          scopedSlots: { customRender: 'group' }
        }, {
          title: 'cron表达式',
          dataIndex: 'cron'
        }, {
          title: 'class类名',
          dataIndex: 'className',
          ellipsis: true
        }, {
          title: '任务状态',
          dataIndex: 'status',
          align: 'center',
          scopedSlots: { customRender: 'status' }
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
          width: '160px'
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
    },
    pause (record) {
      if (record.status === 1) {
        this.$message.warning('任务暂停中')
        return
      }
      const param = { id: record.id, name: record.name }
      Pause(param).then(res => {
        if (res.success) {
          this.$message.success('暂停成功')
          this.$refs.table.clearSelected()
          this.handleOk()
        } else {
          this.$message.error('暂停失败：' + res.message)
        }
      })
    },
    resume (record) {
      if (record.status === 0) {
        this.$message.warning('任务启动中')
        return
      }
      const param = { id: record.id, name: record.name }
      Resume(param).then(res => {
        if (res.success) {
          this.$message.success('启动成功')
          this.$refs.table.clearSelected()
          this.handleOk()
        } else {
          this.$message.error('启动失败：' + res.message)
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
<style>
.table-operator {
  margin-bottom: 18px;
}
button {
  margin-right: 8px;
}
.ant-badge-status-dot {
    width: 10px;
    height: 10px;
}
</style>
