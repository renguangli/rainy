(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-6d0074d9"],{cc5e:function(e,t,a){"use strict";a.d(t,"f",(function(){return o})),a.d(t,"g",(function(){return i})),a.d(t,"a",(function(){return s})),a.d(t,"d",(function(){return c})),a.d(t,"c",(function(){return l})),a.d(t,"e",(function(){return d})),a.d(t,"b",(function(){return u}));a("99af");var r=a("b775"),n={List:"/roles",Update:"/role",BatchDel:"/roles"};function o(e){return Object(r["b"])({url:"".concat(n.Update,"/").concat(e,"/menuIds"),method:"get"})}function i(e){return Object(r["b"])({url:n.List,method:"get",params:e})}function s(e){return Object(r["b"])({url:n.Update,method:"POST",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function c(e){return Object(r["b"])({url:"".concat(n.Update,"/").concat(e),method:"delete"})}function l(e){return Object(r["b"])({url:n.BatchDel,method:"delete",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function d(e){return Object(r["b"])({url:n.Update,method:"PUT",data:e,headers:{"Content-Type":"application/json;charset=UTF-8"}})}function u(e,t){return Object(r["b"])({url:"".concat(n.Update,"/").concat(e,"/menus"),method:"POST",data:t,headers:{"Content-Type":"application/json;charset=UTF-8"}})}},d5a0:function(e,t,a){"use strict";a.r(t);var r=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("a-modal",{attrs:{title:(0===e.flag?"新增":"编辑")+"角色",width:500,visible:e.visible,confirmLoading:e.confirmLoading,destroyOnClose:!0},on:{ok:e.handleOk,cancel:e.handleCancel}},[a("a-spin",{attrs:{spinning:e.formLoading}},[a("a-form",{attrs:{form:e.form}},[a("a-form-item",{directives:[{name:"show",rawName:"v-show",value:!1,expression:"false"}]},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["id"],expression:"['id']"}]})],1),a("a-form-item",{attrs:{label:"角色名称",labelCol:e.labelCol,wrapperCol:e.wrapperCol,hasFeedback:""}},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["name",{rules:[{required:!0,min:1,message:"请输入角色名称！"}]}],expression:"['name',{rules: [{required: true, min: 1, message: '请输入角色名称！'}]}]"}],attrs:{placeholder:"请输入角色名称"}})],1),a("a-form-item",{attrs:{labelCol:e.labelCol,wrapperCol:e.wrapperCol,label:"角色编码"}},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["code",{rules:[{required:!0,message:"请输入角色编码！"}]}],expression:"['code', {rules: [{required: true, message: '请输入角色编码！'}]}]"}],staticStyle:{width:"100%"},attrs:{placeholder:"请输入角色编码",min:1,max:1e4}})],1),a("a-form-item",{attrs:{label:"描述",labelCol:e.labelCol,wrapperCol:e.wrapperCol,"has-feedback":""}},[a("a-textarea",{directives:[{name:"decorator",rawName:"v-decorator",value:["description"],expression:"['description']"}],attrs:{rows:4,placeholder:"请输入描述"}})],1)],1)],1)],1)},n=[],o=(a("b0c0"),a("a4d3"),a("e01a"),a("d3b7"),a("cc5e")),i={name:"RoleEdit",data:function(){return{labelCol:{xs:{span:24},sm:{span:"5"}},wrapperCol:{xs:{span:24},sm:{span:16}},visible:!1,formLoading:!1,confirmLoading:!1,form:this.$form.createForm(this),flag:0}},mounted:function(){},methods:{open:function(e,t){this.visible=!0,this.flag=e,0!==e&&(this.form.getFieldDecorator("id",{initialValue:t.id}),this.form.getFieldDecorator("name",{initialValue:t.name}),this.form.getFieldDecorator("code",{initialValue:t.code}),this.form.getFieldDecorator("description",{initialValue:t.description}))},handleOk:function(){var e=this,t=this.form.validateFields;this.confirmLoading=!0,t((function(t,a){t?e.confirmLoading=!1:0===e.flag?e.add(a):e.edit(a)}))},handleCancel:function(){this.form.resetFields(),this.confirmLoading=!1,this.visible=!1},add:function(e){var t=this;Object(o["a"])(e).then((function(a){a.success?(t.$message.success("新增成功"),t.$emit("ok",e),t.handleCancel()):t.$message.error("新增失败："+a.message)})).finally((function(e){t.confirmLoading=!1}))},edit:function(e){var t=this;Object(o["e"])(e).then((function(a){a.success?(t.$message.success("编辑成功"),t.$emit("ok",e),t.handleCancel()):t.$message.error("编辑失败："+a.message)})).finally((function(e){t.confirmLoading=!1}))}}},s=i,c=a("2877"),l=Object(c["a"])(s,r,n,!1,null,null,null);t["default"]=l.exports}}]);