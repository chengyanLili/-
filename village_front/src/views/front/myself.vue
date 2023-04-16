<template>
    <div class="content">
      <ul class="info" style="color:black;display: grid; margin-top: 50px;">
        <li>
          <el-avatar
            shape="square"
            :size="100"
            fit="fill"
            :src="data.villageInfo[0].avatarUrl"
            @click="upload"
          />
        </li>
        <li>昵称：{{ data.villageInfo[0].nickName }}</li>
        <li>性别：{{ data.villageInfo[0].gender }}</li>
        <li>年龄：{{ data.villageInfo[0].birthday }}</li>
        <li>联系电话：{{ data.villageInfo[0].phone }}</li>
        <li>家庭住址：{{ data.villageInfo[0].address }}</li>
        <li><el-tag class="ml-2" type="success" @click="handleEdit">修改</el-tag></li>
      </ul>
    <!-- 上传头像 -->
    <el-dialog
    width="50%" 
    title="修改头像"
    v-model="data.uploadVisible" >
    <el-upload action="#" list-type="picture-card" :auto-upload="false">
      <el-icon><Plus /></el-icon>
      <template #file="{ file }">
      <div>
        <img class="el-upload-list__item-thumbnail" :src="file.url" alt="" />
      </div>
    </template>
  </el-upload>
  <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="editAvatar">
          提交
        </el-button>
      </span>
    </template>
    </el-dialog>
    <el-dialog 
    width="50%" 
    v-model="data.visible" 
    :before-close="beforeClose"
    >
    <template #header>
      <div class="my-header">
        <h4>修改个人信息</h4>
      </div>
    </template>
    <el-form :model="data.editForm" ref="formRef" :rules="data.formRules">
      <el-form-item label="昵称" prop="nickName" :label-width="formLabelWidth">
        <el-input
          v-model="data.editForm.nickName"
          placeholder="请输入姓名"
          autocomplete="off"
        />
      </el-form-item>
      <el-form-item label="性别" prop="gender" :label-width="formLabelWidth">
        <el-radio-group v-model="data.editForm.gender">
          <el-radio :label="'男'">男</el-radio>
          <el-radio :label="'女'">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="电话号码" prop="phone" :label-width="formLabelWidth">
        <el-input
          v-model="data.editForm.phone"
          placeholder="请输入电话号码"
          autocomplete="off"
        />
      </el-form-item>
      <el-form-item label="出生日期" prop="birthday" :label-width="formLabelWidth">
        <el-date-picker
        v-model="data.editForm.birthday"
        type="date"
        placeholder="请选择出生日期"
        format="YYYY/MM/DD"
        value-format="YYYY-MM-DD"
        style="width:200px"
      />
      </el-form-item>
      <el-form-item label="家庭住址" prop="address" :label-width="formLabelWidth">
        <el-input
          v-model="data.editForm.address"
          placeholder="请输入家庭住址"
          autocomplete="off"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="reset">重置</el-button>
        <el-button type="primary" @click="submit">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
</div>
</template>

<script setup>
import { reactive,ref,onBeforeMount } from "vue"
import request from '../../request/request'
import {  ElMessage } from "element-plus"
import { Plus } from '@element-plus/icons-vue'
const formLabelWidth = '100px'
const data = reactive({
  currentIndex: 0,
  villageInfo: [],
  editForm:{
    nickName:'',
    gender:'',
    birthday:'',
    phone:'',
    address:''
  },
  visible: false,
  uploadVisible: false,
  formRules: {
    nickName: [{ required: true, message: "请输入姓名", trigger: "blur" }],
    gender: [{ required: true, message: "请输入性别", trigger: "blur" }],
    phone: [{ required: true, message: "请输入电话号码", trigger: "blur" }]
  },
})
const formRef = ref(null);
onBeforeMount(() => {
  data.villageInfo = JSON.parse(localStorage.getItem('users'))
  console.log(data.villageInfo[0]);
})
    // 关闭表单前的回调
    const beforeClose = () => {
  reset()
  data.visible = false;
}
const reset = ()=>{
  data.editForm = {
    nickName:'',
    gender:'',
    birthday:'',
    phone:'',
    address:''
  }
}
const upload = () => {
  data.uploadVisible = true
}
// 提交表单
const editAvatar = () => {
  console.log(file.url);
      request.post("/api/users/edit",data.editForm).then((res)=>{
        if(res.status == 200){
          ElMessage({
            showClose: true,
            message: "修改成功！",
            type: "success",
            })
            data.editForm.username = data.villageInfo[0].username
            data.editForm.password = data.villageInfo[0].password
            data.editForm.id = data.villageInfo[0].id
            localStorage.setItem('users', data.editForm)
            beforeClose()
          }else{
              ElMessage({
              showClose: true,
              message: "修改失败,请联系管理员！",
              type: "error",
          })
        }
      })
  }
  
// 提交表单
const submit = () => {
    formRef.value.validate((valid)=>{
    if(valid){
      request.post("/api/users/edit",data.editForm).then((res)=>{
        if(res.status == 200){
          ElMessage({
            showClose: true,
            message: "修改成功！",
            type: "success",
            })
            beforeClose()
          }else{
              ElMessage({
              showClose: true,
              message: "修改失败,请联系管理员！",
              type: "error",
          })
        }
      })
    }
  })
  }
const handleEdit = ()=>{
  console.log(data.villageInfo[0]);
  data.editForm = data.villageInfo[0]
  data.visible = true
}
</script>

<style lang="less" scoped>
  .content{
    background-color: #fff;
    height: calc(100vh - 140px);
    border-radius: 6px;
    overflow: auto;
  .info {
    list-style: none;
    grid-template-rows: 8;
    gap: 20px;
    justify-content: center;
  }
}
.el-dialog {
  .dialog-footer{
    display: flex;
    justify-content: center;
  }
}
</style>