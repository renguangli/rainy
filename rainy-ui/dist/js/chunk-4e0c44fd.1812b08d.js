(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-4e0c44fd","chunk-39f87b57","chunk-5bc52fdb"],{"0dbe":function(e,t,a){},"24fd":function(e,t,a){"use strict";a.d(t,"e",(function(){return o})),a.d(t,"f",(function(){return s})),a.d(t,"a",(function(){return i})),a.d(t,"c",(function(){return c})),a.d(t,"b",(function(){return l})),a.d(t,"d",(function(){return d}));a("99af");var n=a("b775"),r={OrgTree:"/orgs/tree",List:"/orgs",Update:"/org",BatchDel:"/orgs"};function o(){return Object(n["b"])({url:r.OrgTree,method:"GET"})}function s(e){return Object(n["b"])({url:r.List,method:"get",params:e})}function i(e){return Object(n["b"])({url:r.Update,method:"POST",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function c(e){return Object(n["b"])({url:"".concat(r.Update,"/").concat(e),method:"delete"})}function l(e){return Object(n["b"])({url:r.BatchDel,method:"delete",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function d(e){return Object(n["b"])({url:r.Update,method:"PUT",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}},"4daa":function(e,t,a){"use strict";a.r(t);var n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("a-modal",{attrs:{title:(0===e.flag?"新增":"编辑")+"用户",width:500,visible:e.visible,confirmLoading:e.confirmLoading,destroyOnClose:!0},on:{ok:e.handleOk,cancel:e.handleCancel}},[a("a-spin",{attrs:{spinning:e.formLoading}},[a("a-form",{attrs:{form:e.form}},[a("a-form-item",{directives:[{name:"show",rawName:"v-show",value:!1,expression:"false"}]},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["id"],expression:"['id']"}]})],1),a("a-form-item",{attrs:{label:"用户名",labelCol:e.labelCol,wrapperCol:e.wrapperCol,hasFeedback:""}},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["username",{rules:[{required:!0,min:1,message:"请输入用户名！"}]}],expression:"['username',{rules: [{required: true, min: 1, message: '请输入用户名！'}]}]"}],attrs:{placeholder:"请输入用户名"}})],1),a("a-form-item",{attrs:{label:"密码",labelCol:e.labelCol,wrapperCol:e.wrapperCol,hasFeedback:""}},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["password",{rules:[{required:!0,min:1,message:"请输入密码！"}]}],expression:"['password',{rules: [{required: true, min: 1, message: '请输入密码！'}]}]"}],attrs:{type:"password",placeholder:"请输入密码"}})],1),a("a-form-item",{attrs:{label:"组织机构",labelCol:e.labelCol,wrapperCol:e.wrapperCol,hasFeedback:""}},[a("a-tree-select",{directives:[{name:"decorator",rawName:"v-decorator",value:["orgId",{rules:[{required:!0,message:"请选择组织机构！"}]}],expression:"['orgId', {rules: [{ required: true, message: '请选择组织机构！' }]}]"}],staticStyle:{width:"100%"},attrs:{dropdownStyle:{maxHeight:"300px",overflow:"auto"},treeData:e.orgTree,"replace-fields":e.replaceFields,placeholder:"请选择组织机构",treeDefaultExpandAll:""}})],1),a("a-form-item",{attrs:{label:"职位",labelCol:e.labelCol,wrapperCol:e.wrapperCol,hasFeedback:""}},[a("a-select",{directives:[{name:"decorator",rawName:"v-decorator",value:["positionId",{rules:[{required:!0,message:"请选择职位！"}]}],expression:"['positionId', {rules: [{ required: true, message: '请选择职位！' }]}]"}],attrs:{"show-search":"",placeholder:"请选择职位","option-filter-prop":"children","filter-option":e.filterOption},on:{focus:e.handleFocus,blur:e.handleBlur,change:e.handleChange}},e._l(e.positions,(function(t){return a("a-select-option",{key:t.name,attrs:{value:t.id}},[e._v(" "+e._s(t.name)+" ")])})),1)],1),a("a-form-item",{attrs:{label:"昵称",labelCol:e.labelCol,wrapperCol:e.wrapperCol,hasFeedback:""}},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["name",{rules:[{required:!0,min:1,message:"请输入昵称！"}]}],expression:"['name',{rules: [{required: true, min: 1, message: '请输入昵称！'}]}]"}],attrs:{placeholder:"请输入昵称"}})],1),a("a-form-item",{attrs:{label:"手机号",labelCol:e.labelCol,wrapperCol:e.wrapperCol,hasFeedback:""}},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["telephone",{rules:[{required:!0,min:1,message:"请输入手机号！"}]}],expression:"['telephone',{rules: [{required: true, min: 1, message: '请输入手机号！'}]}]"}],attrs:{placeholder:"请输入手机号"}})],1),a("a-form-item",{attrs:{label:"邮箱",labelCol:e.labelCol,wrapperCol:e.wrapperCol,hasFeedback:""}},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["email",{rules:[{required:!0,min:1,message:"请输入邮箱！"},{type:"email",message:"邮箱格式不正确"}]}],expression:"['email',{rules: [{required: true, min: 1, message: '请输入邮箱！'},{type: 'email', message: '邮箱格式不正确'}]}]"}],attrs:{placeholder:"请输入邮箱"}})],1)],1)],1)],1)},r=[],o=(a("b0c0"),a("d3b7"),a("24fd")),s=a("6e6f"),i=a("c24f"),c={name:"UserEdit",data:function(){return{labelCol:{xs:{span:24},sm:{span:"5"}},wrapperCol:{xs:{span:24},sm:{span:16}},visible:!1,formLoading:!1,confirmLoading:!1,replaceFields:{title:"name",key:"id",value:"id"},orgTree:[],positions:[],form:this.$form.createForm(this),flag:0}},mounted:function(){},methods:{open:function(e,t){this.visible=!0,this.getOrgTree(),this.getPositions(),this.flag=e,0!==e&&(this.form.getFieldDecorator("id",{initialValue:t.id}),this.form.getFieldDecorator("username",{initialValue:t.username}),this.form.getFieldDecorator("password",{initialValue:t.password}),this.form.getFieldDecorator("orgId",{initialValue:t.orgId}),this.form.getFieldDecorator("positionId",{initialValue:t.positionId}),this.form.getFieldDecorator("name",{initialValue:t.name}),this.form.getFieldDecorator("telephone",{initialValue:t.telephone}),this.form.getFieldDecorator("email",{initialValue:t.email}))},handleOk:function(){var e=this,t=this.form.validateFields;this.confirmLoading=!0,t((function(t,a){t?e.confirmLoading=!1:0===e.flag?e.add(a):e.edit(a)}))},handleCancel:function(){this.form.resetFields(),this.confirmLoading=!1,this.visible=!1},getOrgTree:function(){var e=this;this.formLoading=!0,Object(o["e"])().then((function(t){t.success?e.orgTree=t.data:e.$message.warning(t.message)})).finally((function(){e.formLoading=!1}))},getPositions:function(){var e=this;Object(s["e"])({paged:!1}).then((function(t){t.success?e.positions=t.data.records:e.$message.warning(t.message)}))},handleChange:function(e){console.log("selected ".concat(e))},handleBlur:function(){console.log("blur")},handleFocus:function(){console.log("focus")},filterOption:function(e,t){return t.componentOptions.children[0].text.toLowerCase().indexOf(e.toLowerCase())>=0},add:function(e){var t=this;e.avatar="/avatar.jpg",Object(i["a"])(e).then((function(a){a.success?(t.$message.success("新增成功"),t.$emit("ok",e),t.handleCancel()):t.$message.error("新增失败："+a.message)})).finally((function(e){t.confirmLoading=!1}))},edit:function(e){var t=this;Object(i["e"])(e).then((function(a){a.success?(t.$message.success("编辑成功"),t.$emit("ok",e),t.handleCancel()):t.$message.error("编辑失败："+a.message)})).finally((function(e){t.confirmLoading=!1}))}}},l=c,d=a("2877"),u=Object(d["a"])(l,n,r,!1,null,null,null);t["default"]=u.exports},"5a69":function(e,t,a){"use strict";a("0dbe")},"5c9a":function(e,t,a){"use strict";a.r(t);var n=function(){var e=this,t=this,a=t.$createElement,n=t._self._c||a;return n("a-modal",{attrs:{title:"分配角色",width:800,visible:t.visible,confirmLoading:t.confirmLoading,destroyOnClose:!0},on:{ok:t.handleOk,cancel:t.handleCancel}},[n("s-table",{staticClass:"role-assign",attrs:{loading:t.tableLoading,rowKey:function(e){return e.id},alert:{show:!0,clear:function(){e.selectedRowKeys=[]}},columns:t.columns,data:t.loadData,"row-selection":{selectedRowKeys:t.selectedRowKeys,onChange:t.handleChange},showPagination:!0}})],1)},r=[],o=(a("d3b7"),a("2af9")),s=a("cc5e"),i=a("c24f"),c={name:"RoleAssign",components:{STable:o["m"]},data:function(){return{visible:!1,confirmLoading:!1,tableLoading:!1,columns:[{title:"角色名称",dataIndex:"name"},{title:"角色编码",dataIndex:"code"},{title:"描述",dataIndex:"description"}],loadData:function(e){return Object(s["g"])(Object.assign(e,{})).then((function(e){return e}))},selectedRowKeys:[],record:{}}},methods:{open:function(e){this.record=e,this.visible=!0,this.getRoleIds()},handleChange:function(e){this.selectedRowKeys=e},getRoleIds:function(){var e=this;Object(i["f"])(this.record.id).then((function(t){e.selectedRowKeys=t.data}))},handleOk:function(){var e=this;this.confirmLoading=!0,Object(i["b"])(this.record.id,this.selectedRowKeys).then((function(t){t.success?(e.$message.success("分配成功"),e.handleCancel()):e.$message.error("分配失败："+t.message)})).finally((function(){e.confirmLoading=!1}))},handleCancel:function(){this.visible=!1,this.confirmLoading=!1,this.formLoading=!1,this.selectedRowKeys=[]}}},l=c,d=(a("df9d"),a("2877")),u=Object(d["a"])(l,n,r,!1,null,null,null);t["default"]=u.exports},"6e6f":function(e,t,a){"use strict";a.d(t,"e",(function(){return o})),a.d(t,"a",(function(){return s})),a.d(t,"c",(function(){return i})),a.d(t,"b",(function(){return c})),a.d(t,"d",(function(){return l}));a("99af");var n=a("b775"),r={List:"/positions",Update:"/position",BatchDel:"/positions"};function o(e){return Object(n["b"])({url:r.List,method:"get",params:e})}function s(e){return Object(n["b"])({url:r.Update,method:"POST",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function i(e){return Object(n["b"])({url:"".concat(r.Update,"/").concat(e),method:"delete"})}function c(e){return Object(n["b"])({url:r.BatchDel,method:"delete",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function l(e){return Object(n["b"])({url:r.Update,method:"PUT",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}},"74dc":function(e,t,a){"use strict";a.r(t);var n=function(){var e=this,t=this,a=t.$createElement,n=t._self._c||a;return n("a-row",{attrs:{gutter:24}},[n("a-col",{attrs:{md:5,sm:24}},[n("a-card",{attrs:{bordered:!1,loading:t.treeLoading}},[n("a-tree",{attrs:{treeData:t.orgTree,defaultExpandAll:!0,replaceFields:t.replaceFields},on:{select:t.handleTreeSelect}})],1)],1),n("a-col",{attrs:{md:19,sm:24}},[n("a-card",{attrs:{bordered:!1}},[n("div",{staticClass:"table-page-search-wrapper"},[n("a-form",{attrs:{layout:"inline"}},[n("a-row",{attrs:{gutter:48}},[n("a-form-item",{directives:[{name:"show",rawName:"v-show",value:!1,expression:"false"}]},[n("a-input",{model:{value:t.queryParam.orgId,callback:function(e){t.$set(t.queryParam,"orgId",e)},expression:"queryParam.orgId"}})],1),n("a-col",{attrs:{md:8,sm:24}},[n("a-form-item",{attrs:{label:"用户名"}},[n("a-input",{attrs:{placeholder:"请输入用户名"},model:{value:t.queryParam.username,callback:function(e){t.$set(t.queryParam,"username",e)},expression:"queryParam.username"}})],1)],1),n("a-col",{attrs:{md:8,sm:24}},[n("a-form-item",{attrs:{label:"昵称"}},[n("a-input",{attrs:{placeholder:"请输入昵称"},model:{value:t.queryParam.name,callback:function(e){t.$set(t.queryParam,"name",e)},expression:"queryParam.name"}})],1)],1),n("a-col",{attrs:{md:8,sm:24}},[n("span",{staticClass:"table-page-search-submitButtons"},[n("a-button",{attrs:{type:"primary"},on:{click:function(e){return t.$refs.table.refresh()}}},[t._v("查询")]),n("a-button",{staticStyle:{"margin-left":"8px"},on:{click:function(e){t.queryParam={}}}},[t._v("重置")])],1)])],1)],1)],1),n("s-table",{ref:"table",attrs:{rowKey:function(e){return e.id},alert:{show:!0,clear:function(){e.selectedRowKeys=[]}},columns:t.columns,data:t.loadData,"row-selection":{selectedRowKeys:t.selectedRowKeys,onChange:t.handleChange},showPagination:t.showPagination,expandRowByClick:!0},on:{change:t.handleChange},scopedSlots:t._u([{key:"operation",fn:function(e,a){return n("span",{},[[n("a",{on:{click:function(e){return t.$refs.editor.open(1,a)}}},[t._v("编辑")]),n("a-divider",{attrs:{type:"vertical"}}),n("a-dropdown",[n("a",{staticClass:"ant-dropdown-link"},[t._v(" 更多 "),n("a-icon",{attrs:{type:"down"}})],1),n("a-menu",{attrs:{slot:"overlay"},slot:"overlay"},[n("a-menu-item",[n("a",{on:{click:function(e){return t.$refs.roleAssign.open(a)}}},[t._v("分配角色")])]),n("a-menu-item",[n("a-popconfirm",{attrs:{placement:"topRight",title:"确定重置用户["+a.name+"]的密码吗?"},on:{confirm:function(e){return t.resetPassword(a.id)}}},[n("a",[t._v("重置密码")])])],1),n("a-menu-item",[n("a-popconfirm",{attrs:{placement:"topRight",title:"确定删除用户["+a.name+"]吗?"},on:{confirm:function(e){return t.del(a.id)}}},[n("a",[t._v("删除")])])],1)],1)],1)]],2)}}])},[n("template",{slot:"operator"},[n("a-button",{attrs:{type:"primary",icon:"plus"},on:{click:function(e){return t.$refs.editor.open(0)}}},[t._v("新增用户")]),n("a-popconfirm",{attrs:{disabled:t.selectedRowKeys.length<1,placement:"topRight",title:"确定批量删除用户吗?"},on:{confirm:t.batchDel}},[n("a-button",{attrs:{type:"danger",disabled:t.selectedRowKeys.length<1}},[n("a-icon",{attrs:{type:"delete"}}),t._v("批量删除")],1)],1)],1)],2),n("editor",{ref:"editor",on:{ok:t.handleOk}}),n("roleAssign",{ref:"roleAssign"})],1)],1)],1)},r=[],o=(a("06f4"),a("fc25")),s=(a("d3b7"),a("25f0"),a("2af9")),i=a("24fd"),c=a("c24f"),l=a("4daa"),d=a("5c9a"),u={name:"User",components:{STable:s["m"],editor:l["default"],roleAssign:d["default"],Empty:o["a"]},data:function(){var e=this;return{treeLoading:!0,simpleImage:o["a"].PRESENTED_IMAGE_SIMPLE,replaceFields:{title:"name",key:"id"},showPagination:!0,queryParam:{orgId:null},columns:[{title:"用户名",dataIndex:"username"},{title:"昵称",dataIndex:"name"},{title:"头像",dataIndex:"avatar",checked:!1},{title:"邮箱",dataIndex:"email"},{title:"手机号",dataIndex:"telephone"},{title:"最后登录时间",dataIndex:"lastLoginTime",checked:!1},{title:"最后登录ip",dataIndex:"lastLoginIp",checked:!1},{title:"最后更新时间",dataIndex:"updateTime",checked:!1},{title:"操作",scopedSlots:{customRender:"operation"},width:"150px"}],orgTree:[],loadData:function(t){return Object(c["g"])(e.queryParam).then((function(e){return e}))},form:this.$form.createForm(this),selectedRowKeys:[],selectedRows:[]}},mounted:function(){this.getOrgTree()},methods:{handleOk:function(){this.$refs.table.refresh(),this.$refs.table.clearSelected()},handleChange:function(e,t){this.selectedRowKeys=e,this.selectedRows=t},handleTreeSelect:function(e,t,a){console.log("orgId",e),console.log("node",t.node.$children),console.log("extra",a),this.queryParam.orgId=e.toString(),this.$refs.table.refresh()},setFieldValue:function(e,t){this.form.getFieldDecorator(e,{initialValue:t}),this.form.resetFields("".concat(e),[]),this.form.getFieldDecorator(e,{initialValue:t})},getOrgTree:function(){var e=this;this.treeLoading=!0,Object(i["e"])().then((function(t){t.success&&(e.orgTree=t.data)})).finally((function(){e.treeLoading=!1}))},del:function(e){var t=this;Object(c["d"])(e).then((function(e){e.success?(t.$message.success("删除成功"),t.handleOk(),t.$refs.table.clearSelected()):t.$message.error("删除失败："+e.message)}))},batchDel:function(){var e=this;Object(c["c"])(this.selectedRowKeys).then((function(t){t.success?(e.$message.success("删除成功"),e.handleOk()):e.$message.error("删除失败："+t.message)}))},resetPassword:function(e){var t=this;Object(c["h"])(e).then((function(e){e.success?(t.$message.success("重置成功"),t.handleOk()):t.$message.error("重置失败："+e.message)}))}}},m=u,f=(a("5a69"),a("2877")),h=Object(f["a"])(m,n,r,!1,null,null,null);t["default"]=h.exports},"7ea5":function(e,t,a){},c24f:function(e,t,a){"use strict";a.d(t,"g",(function(){return o})),a.d(t,"a",(function(){return s})),a.d(t,"d",(function(){return i})),a.d(t,"c",(function(){return c})),a.d(t,"e",(function(){return l})),a.d(t,"f",(function(){return d})),a.d(t,"b",(function(){return u})),a.d(t,"h",(function(){return m}));a("99af");var n=a("b775"),r={List:"/users",Update:"/user",BatchDel:"/users"};function o(e){return Object(n["b"])({url:r.List,method:"get",params:e})}function s(e){return Object(n["b"])({url:r.Update,method:"POST",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function i(e){return Object(n["b"])({url:"".concat(r.Update,"/").concat(e),method:"delete"})}function c(e){return Object(n["b"])({url:r.BatchDel,method:"delete",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function l(e){return Object(n["b"])({url:r.Update,method:"PUT",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function d(e){return Object(n["b"])({url:"".concat(r.Update,"/").concat(e,"/roleIds"),method:"GET"})}function u(e,t){return Object(n["b"])({url:"".concat(r.Update,"/").concat(e,"/roles"),method:"POST",data:t,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function m(e){return Object(n["b"])({url:"".concat(r.Update,"/").concat(e,"/password/reset"),method:"put"})}},cc5e:function(e,t,a){"use strict";a.d(t,"f",(function(){return o})),a.d(t,"g",(function(){return s})),a.d(t,"a",(function(){return i})),a.d(t,"d",(function(){return c})),a.d(t,"c",(function(){return l})),a.d(t,"e",(function(){return d})),a.d(t,"b",(function(){return u}));a("99af");var n=a("b775"),r={List:"/roles",Update:"/role",BatchDel:"/roles"};function o(e){return Object(n["b"])({url:"".concat(r.Update,"/").concat(e,"/menuIds"),method:"get"})}function s(e){return Object(n["b"])({url:r.List,method:"get",params:e})}function i(e){return Object(n["b"])({url:r.Update,method:"POST",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function c(e){return Object(n["b"])({url:"".concat(r.Update,"/").concat(e),method:"delete"})}function l(e){return Object(n["b"])({url:r.BatchDel,method:"delete",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function d(e){return Object(n["b"])({url:r.Update,method:"PUT",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function u(e,t){return Object(n["b"])({url:"".concat(r.Update,"/").concat(e,"/menus"),method:"POST",data:t,headers:{"Content-Type":"application/json;charset=UTF-8"}})}},df9d:function(e,t,a){"use strict";a("7ea5")}}]);