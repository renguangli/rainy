(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-8021daea","chunk-f1b4dc16"],{"3b0d":function(e,t,a){"use strict";a.r(t);var r=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("a-modal",{attrs:{title:(0===e.flag?"新增":"编辑")+"任务",width:500,visible:e.visible,confirmLoading:e.confirmLoading,destroyOnClose:!0},on:{ok:e.handleOk,cancel:e.handleCancel}},[a("a-spin",{attrs:{spinning:e.formLoading}},[a("a-form",{attrs:{form:e.form}},[a("a-form-item",{directives:[{name:"show",rawName:"v-show",value:!1,expression:"false"}]},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["id"],expression:"['id']"}]})],1),a("a-form-item",{attrs:{label:"任务名称",labelCol:e.labelCol,wrapperCol:e.wrapperCol,hasFeedback:""}},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["name",{rules:[{required:!0,min:1,message:"请输入任务名称！"}]}],expression:"['name',{rules: [{required: true, min: 1, message: '请输入任务名称！'}]}]"}],attrs:{placeholder:"请输入任务名称"}})],1),a("a-form-item",{attrs:{label:"任务分组",labelCol:e.labelCol,wrapperCol:e.wrapperCol,hasFeedback:""}},[a("a-select",{directives:[{name:"decorator",rawName:"v-decorator",value:["group",{rules:[{required:!0,min:1,message:"请选择任务分组！"}]}],expression:"['group',{rules: [{required: true, min: 1, message: '请选择任务分组！'}]}]"}],attrs:{placeholder:"请选择任务分组"}},e._l(e.$options.filters.dictItems("SYS_TASK_GROUP"),(function(t){return a("a-select-option",{key:t.value,attrs:{value:t.value}},[e._v(" "+e._s(t.name)+" ")])})),1)],1),a("a-form-item",{attrs:{label:"cron表达式",labelCol:e.labelCol,wrapperCol:e.wrapperCol,hasFeedback:""}},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["cron",{rules:[{required:!0,min:1,message:"请输入cron表达式！"}]}],expression:"['cron',{rules: [{required: true, min: 1, message: '请输入cron表达式！'}]}]"}],attrs:{placeholder:"请输入cron表达式"}})],1),a("a-form-item",{attrs:{label:"class类名",labelCol:e.labelCol,wrapperCol:e.wrapperCol,hasFeedback:""}},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["className",{rules:[{required:!0,min:1,message:"请输入class类名！"}]}],expression:"['className',{rules: [{required: true, min: 1, message: '请输入class类名！'}]}]"}],attrs:{placeholder:"请输入class类名"}})],1),a("a-form-item",{attrs:{labelCol:e.labelCol,wrapperCol:e.wrapperCol,label:"任务状态"}},[a("a-radio-group",{directives:[{name:"decorator",rawName:"v-decorator",value:["status",{rules:[{required:!0,message:"请选择任务状态！"}]}],expression:"['status', {rules: [{ required: true, message: '请选择任务状态！' }]}]"}]},e._l(e.$options.filters.dictItems("SYS_TASK_STATUS"),(function(t){return a("a-radio",{key:t.name,attrs:{value:t.value}},[e._v(e._s(t.name))])})),1)],1),a("a-form-item",{attrs:{label:"描述",labelCol:e.labelCol,wrapperCol:e.wrapperCol,"has-feedback":""}},[a("a-textarea",{directives:[{name:"decorator",rawName:"v-decorator",value:["description"],expression:"['description']"}],attrs:{rows:4,placeholder:"请输入描述"}})],1)],1)],1)],1)},n=[],s=(a("b0c0"),a("a4d3"),a("e01a"),a("d3b7"),a("b199")),o={name:"TaskEdit",data:function(){return{labelCol:{xs:{span:24},sm:{span:"5"}},wrapperCol:{xs:{span:24},sm:{span:16}},visible:!1,formLoading:!1,confirmLoading:!1,form:this.$form.createForm(this),flag:0}},mounted:function(){},methods:{open:function(e,t){this.visible=!0,this.flag=e,0!==e?(this.form.getFieldDecorator("id",{initialValue:t.id}),this.form.getFieldDecorator("name",{initialValue:t.name}),this.form.getFieldDecorator("group",{initialValue:t.group}),this.form.getFieldDecorator("cron",{initialValue:t.cron}),this.form.getFieldDecorator("className",{initialValue:t.className}),this.form.getFieldDecorator("status",{initialValue:t.status}),this.form.getFieldDecorator("description",{initialValue:t.description})):this.form.getFieldDecorator("status",{initialValue:0})},handleOk:function(){var e=this,t=this.form.validateFields;this.confirmLoading=!0,t((function(t,a){t?e.confirmLoading=!1:0===e.flag?e.add(a):e.edit(a)}))},handleCancel:function(){this.form.resetFields(),this.confirmLoading=!1,this.visible=!1},add:function(e){var t=this;Object(s["a"])(e).then((function(a){a.success?(t.$message.success("新增成功"),t.$emit("ok",e),t.handleCancel()):t.$message.error("新增失败："+a.message)})).finally((function(e){t.confirmLoading=!1}))},edit:function(e){var t=this;Object(s["d"])(e).then((function(a){a.success?(t.$message.success("编辑成功"),t.$emit("ok",e),t.handleCancel()):t.$message.error("编辑失败："+a.message)})).finally((function(e){t.confirmLoading=!1}))}}},i=o,l=a("2877"),c=Object(l["a"])(i,r,n,!1,null,null,null);t["default"]=c.exports},7187:function(e,t,a){"use strict";a("c866")},"7e88":function(e,t,a){"use strict";a.r(t);var r=function(){var e=this,t=this,a=t.$createElement,r=t._self._c||a;return r("a-card",{attrs:{bordered:!1}},[r("div",{staticClass:"table-page-search-wrapper"},[r("a-form",{attrs:{layout:"inline"}},[r("a-row",{attrs:{gutter:32}},[r("a-col",{attrs:{md:8,sm:24}},[r("a-form-item",{attrs:{label:"任务名称"}},[r("a-input",{attrs:{placeholder:"请输入任务名称"},model:{value:t.queryParam.name,callback:function(e){t.$set(t.queryParam,"name",e)},expression:"queryParam.name"}})],1)],1),r("a-col",{attrs:{md:8,sm:24}},[r("a-form-item",{attrs:{label:"任务分组"}},[r("a-select",{attrs:{placeholder:"请选择任务分组"},model:{value:t.queryParam.group,callback:function(e){t.$set(t.queryParam,"group",e)},expression:"queryParam.group"}},t._l(t.$options.filters.dictItems("SYS_TASK_GROUP"),(function(e){return r("a-select-option",{key:e.value,attrs:{value:e.value}},[t._v(" "+t._s(e.name)+" ")])})),1)],1)],1),r("a-col",{attrs:{md:8,sm:24}},[r("span",{staticClass:"table-page-search-submitButtons"},[r("a-button",{attrs:{type:"primary"},on:{click:function(e){return t.$refs.table.refresh()}}},[t._v("查询")]),r("a-button",{staticStyle:{"margin-left":"8px"},on:{click:function(e){t.queryParam={}}}},[t._v("重置")])],1)])],1)],1)],1),r("s-table",{ref:"table",attrs:{rowKey:function(e){return e.id},alert:{show:!0,clear:function(){e.selectedRowKeys=[]}},columns:t.columns,data:t.loadData,"row-selection":{selectedRowKeys:t.selectedRowKeys,onChange:t.handleChange},showPagination:!0,expandRowByClick:!0},on:{change:t.handleChange},scopedSlots:t._u([{key:"group",fn:function(e){return r("span",{},[t._v(" "+t._s(t._f("dictItemValue")("SYS_TASK_GROUP",e))+" ")])}},{key:"status",fn:function(e){return r("span",{},[r("a-badge",{attrs:{color:0===e?"green":"red"}})],1)}},{key:"operation",fn:function(e,a){return r("span",{},[r("router-link",{attrs:{to:{path:"/task/log",query:{name:a.name,group:a.group}}}},[r("a",[t._v("执行日志")])]),r("a-divider",{attrs:{type:"vertical"}}),r("a-dropdown",[r("a",{staticClass:"ant-dropdown-link"},[t._v(" 更多 "),r("a-icon",{attrs:{type:"down"}})],1),r("a-menu",{attrs:{slot:"overlay"},slot:"overlay"},[r("a-menu-item",[r("a",{on:{click:function(e){return t.resume(a.id)}}},[t._v("启动")])]),r("a-menu-item",[r("a",{on:{click:function(e){return t.pause(a.id)}}},[t._v("暂停")])]),r("a-menu-item",[r("a",{on:{click:function(e){return t.$refs.editor.open(1,a)}}},[t._v("编辑")])]),r("a-menu-item",[r("a-popconfirm",{attrs:{placement:"topRight",title:"确定删除任务["+a.name+"]吗？"},on:{confirm:function(e){return t.del(a.id)}}},[r("a",[t._v("删除")])])],1)],1)],1)],1)}}])},[r("template",{slot:"operator"},[r("a-button",{attrs:{type:"primary",icon:"plus"},on:{click:function(e){return t.$refs.editor.open(0)}}},[t._v("新增任务")]),r("a-popconfirm",{attrs:{disabled:t.selectedRowKeys.length<1,placement:"topRight",title:"确定批量删除任务吗？"},on:{confirm:t.batchDel}},[r("a-button",{attrs:{type:"danger",disabled:t.selectedRowKeys.length<1}},[r("a-icon",{attrs:{type:"delete"}}),t._v("批量删除")],1)],1)],1)],2),r("editor",{ref:"editor",on:{ok:t.handleOk}})],1)},n=[],s=a("2af9"),o=a("b199"),i=a("3b0d"),l={name:"Task",components:{STable:s["m"],editor:i["default"]},data:function(){var e=this;return{queryParam:{},columns:[{title:"任务名称",dataIndex:"name"},{title:"任务分组",dataIndex:"group",scopedSlots:{customRender:"group"}},{title:"cron表达式",dataIndex:"cron"},{title:"class类名",dataIndex:"className"},{title:"任务状态",dataIndex:"status",align:"center",scopedSlots:{customRender:"status"}},{title:"描述",dataIndex:"description",checked:!1},{title:"创建时间",dataIndex:"createTime",checked:!1},{title:"创建者",dataIndex:"createBy",checked:!1},{title:"更新时间",dataIndex:"updateTime",checked:!1},{title:"更新者",dataIndex:"updateBy",checked:!1},{title:"操作",scopedSlots:{customRender:"operation"},width:"160px"}],loadData:function(t){return Object(o["e"])(Object.assign(t,e.queryParam)).then((function(e){return e}))},selectedRowKeys:[],selectedRows:[]}},methods:{handleOk:function(){this.$refs.table.refresh(),this.$refs.table.clearSelected()},handleChange:function(e,t){this.selectedRowKeys=e,this.selectedRows=t},del:function(e){var t=this;Object(o["c"])(e).then((function(e){e.success?(t.$message.success("删除成功"),t.handleOk()):t.$message.error("删除失败："+e.message)}))},batchDel:function(){var e=this;Object(o["b"])(this.selectedRowKeys).then((function(t){t.success?(e.$message.success("删除成功"),e.$refs.table.clearSelected(),e.handleOk()):e.$message.error("删除失败："+t.message)}))},pause:function(e){var t=this;Object(o["f"])(e).then((function(e){e.success?(t.$message.success("暂停成功"),t.$refs.table.clearSelected(),t.handleOk()):t.$message.error("暂停失败："+e.message)}))},resume:function(e){var t=this;Object(o["g"])(e).then((function(e){e.success?(t.$message.success("启动成功"),t.$refs.table.clearSelected(),t.handleOk()):t.$message.error("启动失败："+e.message)}))}}},c=l,u=(a("7187"),a("2877")),d=Object(u["a"])(c,r,n,!1,null,null,null);t["default"]=d.exports},b199:function(e,t,a){"use strict";a.d(t,"e",(function(){return s})),a.d(t,"a",(function(){return o})),a.d(t,"c",(function(){return i})),a.d(t,"b",(function(){return l})),a.d(t,"d",(function(){return c})),a.d(t,"f",(function(){return u})),a.d(t,"g",(function(){return d}));a("99af");var r=a("b775"),n={List:"/tasks",Update:"/task",BatchDel:"/tasks"};function s(e){return Object(r["b"])({url:n.List,method:"get",params:e})}function o(e){return Object(r["b"])({url:n.Update,method:"POST",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function i(e){return Object(r["b"])({url:"".concat(n.Update,"/").concat(e),method:"delete"})}function l(e){return Object(r["b"])({url:n.Update,method:"delete",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function c(e){return Object(r["b"])({url:n.Update,method:"PUT",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function u(e){return Object(r["b"])({url:"".concat(n.Update,"/").concat(e,"/pause"),method:"PUT",headers:{"Content-Type":"application/json;charset=UTF-8"}})}function d(e){return Object(r["b"])({url:"".concat(n.Update,"/").concat(e,"/resume"),method:"PUT",headers:{"Content-Type":"application/json;charset=UTF-8"}})}},c866:function(e,t,a){}}]);