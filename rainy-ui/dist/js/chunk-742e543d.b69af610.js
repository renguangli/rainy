(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-742e543d","chunk-2d2101ff"],{"00e7":function(e,t,n){"use strict";n.r(t);var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("a-card",{attrs:{bordered:!1}},[n("a-row",{attrs:{gutter:8}},[n("a-col",{attrs:{span:5}},[n("s-tree",{attrs:{dataSource:e.orgTree,openKeys:e.openKeys,search:!0},on:{"update:openKeys":function(t){e.openKeys=t},"update:open-keys":function(t){e.openKeys=t},click:e.handleClick,add:e.handleAdd,titleClick:e.handleTitleClick}})],1),n("a-col",{attrs:{span:19}},[n("s-table",{ref:"table",attrs:{size:"default",columns:e.columns,data:e.loadData,alert:!1,rowSelection:{selectedRowKeys:e.selectedRowKeys,onChange:e.onSelectChange}},scopedSlots:e._u([{key:"action",fn:function(t,a){return n("span",{},[e.$auth("table.update")?[n("a",{on:{click:function(t){return e.handleEdit(a)}}},[e._v("编辑")]),n("a-divider",{attrs:{type:"vertical"}})]:e._e(),n("a-dropdown",[n("a",{staticClass:"ant-dropdown-link"},[e._v(" 更多 "),n("a-icon",{attrs:{type:"down"}})],1),n("a-menu",{attrs:{slot:"overlay"},slot:"overlay"},[n("a-menu-item",[n("a",{attrs:{href:"javascript:;"}},[e._v("详情")])]),e.$auth("table.disable")?n("a-menu-item",[n("a",{attrs:{href:"javascript:;"}},[e._v("禁用")])]):e._e(),e.$auth("table.delete")?n("a-menu-item",[n("a",{attrs:{href:"javascript:;"}},[e._v("删除")])]):e._e()],1)],1)],2)}}])})],1)],1),n("org-modal",{ref:"modal",on:{ok:e.handleSaveOk,close:e.handleSaveClose}})],1)},o=[],r=(n("99af"),n("1bff")),i=n("2af9"),s=n("b70d"),c=n("0fea"),l={name:"TreeList",components:{STable:i["m"],STree:r["a"],OrgModal:s["default"]},data:function(){var e=this;return{openKeys:["key-01"],queryParam:{},columns:[{title:"#",dataIndex:"no"},{title:"成员名称",dataIndex:"description"},{title:"登录次数",dataIndex:"callNo",sorter:!0,needTotal:!0,customRender:function(e){return e+" 次"}},{title:"状态",dataIndex:"status",needTotal:!0},{title:"更新时间",dataIndex:"updatedAt",sorter:!0},{title:"操作",dataIndex:"action",width:"150px",scopedSlots:{customRender:"action"}}],loadData:function(t){return Object(c["d"])(Object.assign(t,e.queryParam)).then((function(e){return e.result}))},orgTree:[],selectedRowKeys:[],selectedRows:[]}},created:function(){var e=this;Object(c["a"])().then((function(t){e.orgTree=t.result}))},methods:{handleClick:function(e){console.log("handleClick",e),this.queryParam={key:e.key},this.$refs.table.refresh(!0)},handleAdd:function(e){console.log("add button, item",e),this.$message.info("提示：你点了 ".concat(e.key," - ").concat(e.title," ")),this.$refs.modal.add(e.key)},handleTitleClick:function(e){console.log("handleTitleClick",e)},titleClick:function(e){console.log("titleClick",e)},handleSaveOk:function(){},handleSaveClose:function(){},onSelectChange:function(e,t){this.selectedRowKeys=e,this.selectedRows=t}}},d=l,u=(n("3d5f"),n("2877")),f=Object(u["a"])(d,a,o,!1,null,null,null);t["default"]=f.exports},"0fea":function(e,t,n){"use strict";n.d(t,"c",(function(){return r})),n.d(t,"d",(function(){return i})),n.d(t,"b",(function(){return s})),n.d(t,"a",(function(){return c}));var a=n("b775"),o={user:"/user",role:"/role",service:"/service",permission:"/permission",permissionNoPager:"/permission/no-pager",orgTree:"/org/tree"};function r(e){return Object(a["c"])({url:o.role,method:"get",params:e})}function i(e){return Object(a["c"])({url:o.service,method:"get",params:e})}function s(e){return Object(a["c"])({url:o.permissionNoPager,method:"get",params:e})}function c(e){return Object(a["c"])({url:o.orgTree,method:"get",params:e})}},"3d5f":function(e,t,n){"use strict";n("3f31")},"3f31":function(e,t,n){},b70d:function(e,t,n){"use strict";n.r(t);var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("a-modal",{attrs:{title:"操作",width:600,visible:e.visible,confirmLoading:e.confirmLoading},on:{ok:e.handleOk,cancel:e.handleCancel}},[n("a-spin",{attrs:{spinning:e.confirmLoading}},[n("a-form",{attrs:{form:e.form}},[n("a-form-item",{attrs:{label:"父级ID"}},[n("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["parentId",{}],expression:"['parentId', {}]"}],attrs:{disabled:""}})],1),n("a-form-item",{attrs:{label:"机构名称"}},[n("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["orgName",{}],expression:"['orgName', {}]"}]})],1)],1)],1)],1)},o=[],r=n("5530"),i=(n("d3b7"),{name:"OrgModal",data:function(){return{labelCol:{xs:{span:24},sm:{span:5}},wrapperCol:{xs:{span:24},sm:{span:16}},visible:!1,confirmLoading:!1,mdl:{}}},beforeCreate:function(){this.form=this.$form.createForm(this),console.log("form::",this.form)},created:function(){},methods:{add:function(e){this.edit({parentId:e})},edit:function(e){var t=this;this.mdl=Object.assign({},e),this.visible=!0,this.$nextTick((function(){t.form.setFieldsValue(Object(r["a"])({},e))}))},close:function(){this.$emit("close"),this.visible=!1},handleOk:function(){var e=this;this.form.validateFields((function(t,n){t||(console.log("form values",n),e.confirmLoading=!0,new Promise((function(e){setTimeout((function(){return e()}),2e3)})).then((function(){e.$message.success("保存成功"),e.$emit("ok")})).catch((function(){})).finally((function(){e.confirmLoading=!1,e.close()})))}))},handleCancel:function(){this.close()}}}),s=i,c=n("2877"),l=Object(c["a"])(s,a,o,!1,null,null,null);t["default"]=l.exports}}]);