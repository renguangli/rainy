(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-29cdfca6","chunk-550aeb33"],{"193a":function(e,t,a){"use strict";a.r(t);var r=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("a-modal",{attrs:{title:(0===e.flag?"新增":"编辑")+"组织",width:500,visible:e.visible,confirmLoading:e.confirmLoading,destroyOnClose:!0},on:{ok:e.handleOk,cancel:e.handleCancel}},[a("a-spin",{attrs:{spinning:e.formLoading}},[a("a-form",{attrs:{form:e.form}},[a("a-form-item",{directives:[{name:"show",rawName:"v-show",value:!1,expression:"false"}]},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["id"],expression:"['id']"}]})],1),a("a-form-item",{attrs:{label:"组织名称",labelCol:e.labelCol,wrapperCol:e.wrapperCol,hasFeedback:""}},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["name",{rules:[{required:!0,min:1,message:"请输入组织名称！"}]}],expression:"['name',{rules: [{required: true, min: 1, message: '请输入组织名称！'}]}]"}],attrs:{placeholder:"请输入组织名称"}})],1),a("a-form-item",{attrs:{label:"父级组织",labelCol:e.labelCol,wrapperCol:e.wrapperCol,hasFeedback:""}},[a("a-tree-select",{directives:[{name:"decorator",rawName:"v-decorator",value:["parentId",{rules:[{required:!0,message:"请选择父级组织！"}]}],expression:"['parentId', {rules: [{ required: true, message: '请选择父级组织！' }]}]"}],staticStyle:{width:"100%"},attrs:{dropdownStyle:{maxHeight:"300px",overflow:"auto"},treeData:e.orgTree,"replace-fields":e.replaceFields,placeholder:"请选择父级组织",treeDefaultExpandAll:""}})],1),a("a-form-item",{attrs:{labelCol:e.labelCol,wrapperCol:e.wrapperCol,label:"排序"}},[a("a-input-number",{directives:[{name:"decorator",rawName:"v-decorator",value:["sort",{rules:[{required:!0,message:"请输入排序！"}]}],expression:"['sort', {rules: [{required: true, message: '请输入排序！'}]}]"}],staticStyle:{width:"100%"},attrs:{min:1,max:1e4}})],1),a("a-form-item",{attrs:{label:"描述",labelCol:e.labelCol,wrapperCol:e.wrapperCol,"has-feedback":""}},[a("a-textarea",{directives:[{name:"decorator",rawName:"v-decorator",value:["description"],expression:"['description']"}],attrs:{rows:4,placeholder:"请输入描述"}})],1)],1)],1)],1)},n=[],o=(a("b0c0"),a("a4d3"),a("e01a"),a("4e82"),a("d3b7"),a("24fd")),i={name:"OrgEdit",data:function(){return{labelCol:{xs:{span:24},sm:{span:"5"}},wrapperCol:{xs:{span:24},sm:{span:16}},visible:!1,formLoading:!1,confirmLoading:!1,replaceFields:{title:"name",key:"id",value:"id"},orgTree:[],form:this.$form.createForm(this),flag:0}},mounted:function(){},methods:{open:function(e,t){this.visible=!0,this.getOrgTree(),this.flag=e,0!==e?(this.form.getFieldDecorator("id",{initialValue:t.id}),this.form.getFieldDecorator("parentId",{initialValue:t.parentId}),this.form.getFieldDecorator("name",{initialValue:t.name}),this.form.getFieldDecorator("description",{initialValue:t.description}),this.form.getFieldDecorator("sort",{initialValue:t.sort})):(this.form.getFieldDecorator("sort",{initialValue:99}),this.form.getFieldDecorator("parentId",{initialValue:0}))},handleOk:function(){var e=this,t=this.form.validateFields;this.confirmLoading=!0,t((function(t,a){t?e.confirmLoading=!1:0===e.flag?e.add(a):e.edit(a)}))},handleCancel:function(){this.form.resetFields(),this.confirmLoading=!1,this.visible=!1},getOrgTree:function(){var e=this;this.formLoading=!0,Object(o["e"])().then((function(t){t.success?e.orgTree=[{id:"0",value:"0",name:"root",children:t.data}]:e.$message.warning(t.message)})).finally((function(){e.formLoading=!1}))},add:function(e){var t=this;Object(o["a"])(e).then((function(a){a.success?(t.$message.success("新增成功"),t.$emit("ok",e),t.handleCancel()):t.$message.error("新增失败："+a.message)})).finally((function(e){t.confirmLoading=!1}))},edit:function(e){var t=this;Object(o["d"])(e).then((function(a){a.success?(t.$message.success("编辑成功"),t.$emit("ok",e),t.handleCancel()):t.$message.error("编辑失败："+a.message)})).finally((function(e){t.confirmLoading=!1}))}}},s=i,l=a("2877"),c=Object(l["a"])(s,r,n,!1,null,null,null);t["default"]=c.exports},"24aa":function(e,t,a){"use strict";a.r(t);var r=function(){var e=this,t=this,a=t.$createElement,r=t._self._c||a;return r("a-row",{attrs:{gutter:24}},[r("a-col",{attrs:{md:5,sm:24}},[r("a-card",{attrs:{bordered:!1,loading:t.treeLoading}},[r("a-tree",{attrs:{treeData:t.orgTree,defaultExpandAll:!0,replaceFields:t.replaceFields},on:{select:t.handleTreeSelect}})],1)],1),r("a-col",{attrs:{md:19,sm:24}},[r("a-card",{attrs:{bordered:!1}},[r("div",{staticClass:"table-page-search-wrapper"},[r("a-form",{attrs:{layout:"inline"}},[r("a-row",{attrs:{gutter:48}},[r("a-col",{attrs:{md:8,sm:24}},[r("a-form-item",{attrs:{label:"组织名称"}},[r("a-input",{attrs:{placeholder:"请输入组织名称"},model:{value:t.queryParam.name,callback:function(e){t.$set(t.queryParam,"name",e)},expression:"queryParam.name"}})],1)],1),r("a-col",{attrs:{md:8,sm:24}},[r("span",{staticClass:"table-page-search-submitButtons"},[r("a-button",{attrs:{type:"primary"},on:{click:function(e){return t.$refs.table.refresh()}}},[t._v("查询")]),r("a-button",{staticStyle:{"margin-left":"8px"},on:{click:function(e){t.queryParam={}}}},[t._v("重置")])],1)])],1)],1)],1),r("s-table",{ref:"table",attrs:{rowKey:function(e){return e.id},alert:{show:!0,clear:function(){e.selectedRowKeys=[]}},columns:t.columns,data:t.loadData,"row-selection":{selectedRowKeys:t.selectedRowKeys,onChange:t.handleChange},showPagination:t.showPagination,expandRowByClick:!0},on:{change:t.handleChange},scopedSlots:t._u([{key:"operation",fn:function(e,a){return r("span",{},[[r("a",{on:{click:function(e){return t.$refs.editor.open(1,a)}}},[t._v("编辑")]),r("a-divider",{attrs:{type:"vertical"}}),r("a-popconfirm",{attrs:{placement:"topRight",title:"确定删除组织["+a.name+"]吗?"},on:{confirm:function(e){return t.del(a.id)}}},[r("a",[t._v("删除")])])]],2)}}])},[r("template",{slot:"operator"},[r("a-button",{attrs:{type:"primary",icon:"plus"},on:{click:function(e){return t.$refs.editor.open(0)}}},[t._v("新增组织")]),r("a-popconfirm",{attrs:{disabled:t.selectedRowKeys.length<1,placement:"topRight",title:"确定批量删除组织吗?"},on:{confirm:t.batchDel}},[r("a-button",{attrs:{type:"danger",disabled:t.selectedRowKeys.length<1}},[r("a-icon",{attrs:{type:"delete"}}),t._v("批量删除")],1)],1)],1)],2),r("editor",{ref:"editor",on:{ok:t.handleOk}})],1)],1)],1)},n=[],o=(a("06f4"),a("fc25")),i=(a("d3b7"),a("25f0"),a("2af9")),s=a("24fd"),l=a("193a"),c={name:"Org",components:{STable:i["m"],editor:l["default"],Empty:o["a"]},data:function(){var e=this;return{treeLoading:!0,simpleImage:o["a"].PRESENTED_IMAGE_SIMPLE,replaceFields:{title:"name",key:"id",value:"id"},showPagination:!0,queryParam:{},columns:[{title:"组织名称",dataIndex:"name"},{title:"描述",dataIndex:"description"},{title:"排序",dataIndex:"sort"},{title:"创建时间",dataIndex:"createTime",checked:!1},{title:"创建者",dataIndex:"createBy",checked:!1},{title:"更新时间",dataIndex:"updateTime",checked:!1},{title:"更新者",dataIndex:"updateBy",checked:!1},{title:"操作",scopedSlots:{customRender:"operation"},width:"150px"}],orgTree:[],loadData:function(t){return Object(s["f"])(Object.assign(t,e.queryParam)).then((function(e){return e}))},selectedRowKeys:[],selectedRows:[]}},mounted:function(){this.getOrgTree()},methods:{handleTreeSelect:function(e){this.queryParam.orgId=e.toString(),this.$refs.table.refresh(),this.queryParam={}},handleOk:function(){this.getOrgTree(),this.$refs.table.refresh(),this.$refs.table.clearSelected()},handleChange:function(e,t){this.selectedRowKeys=e,this.selectedRows=t},getOrgTree:function(){var e=this;this.treeLoading=!0,Object(s["e"])().then((function(t){t.success&&(e.orgTree=t.data)})).finally((function(){e.treeLoading=!1}))},del:function(e){var t=this;Object(s["c"])(e).then((function(e){e.success?(t.$message.success("删除成功"),t.handleOk(),t.$refs.table.clearSelected()):t.$message.error("删除失败："+e.message)}))},batchDel:function(){var e=this;Object(s["b"])(this.selectedRowKeys).then((function(t){t.success?(e.$message.success("删除成功"),e.handleOk()):e.$message.error("删除失败："+t.message)}))}}},d=c,u=(a("978b"),a("2877")),m=Object(u["a"])(d,r,n,!1,null,null,null);t["default"]=m.exports},"24fd":function(e,t,a){"use strict";a.d(t,"e",(function(){return o})),a.d(t,"f",(function(){return i})),a.d(t,"a",(function(){return s})),a.d(t,"c",(function(){return l})),a.d(t,"b",(function(){return c})),a.d(t,"d",(function(){return d}));a("99af");var r=a("b775"),n={OrgTree:"/orgs/tree",List:"/orgs",Update:"/org",BatchDel:"/orgs"};function o(){return Object(r["b"])({url:n.OrgTree,method:"GET"})}function i(e){return Object(r["b"])({url:n.List,method:"get",params:e})}function s(e){return Object(r["b"])({url:n.Update,method:"POST",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function l(e){return Object(r["b"])({url:"".concat(n.Update,"/").concat(e),method:"delete"})}function c(e){return Object(r["b"])({url:n.BatchDel,method:"delete",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function d(e){return Object(r["b"])({url:n.Update,method:"PUT",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}},"978b":function(e,t,a){"use strict";a("e4df")},e4df:function(e,t,a){}}]);