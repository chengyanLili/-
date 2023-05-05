<template>
  <div class="mySelf">
    <el-card class="box-card1">
      <template #header>
        <div class="card-header">
          <span> 个人信息 </span>
        </div>
      </template>
      <div class="box-content">
        <div>
          <el-avatar
            shape="square"
            :size="100"
            fit="fill"
            :src="data.editForm.avatarUrl"
            @click="upload"
          />
        </div>
        <hr />
        <div>昵称：{{ data.editForm.nickName }}</div>
        <hr />
        <div>性别：{{ data.editForm.gender }}</div>
        <hr />
        <div>年龄：{{ data.editForm.birthday }}</div>
        <hr />
        <div>联系电话：{{ data.editForm.phone }}</div>
        <hr />
        <div>家庭住址：{{ data.editForm.address }}</div>
        <hr />
      </div>
    </el-card>
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>基本资料</span>
        </div>
      </template>
      <div class="text item">
        <el-form ref="formRef" :model="data.editForm" :rules="data.formRules">
          <el-form-item
            label="用户名"
            prop="username"
            :label-width="formLabelWidth"
            style="width: 350px"
          >
            <el-input
              v-model="data.editForm.username"
              placeholder="请输入用户名"
              autocomplete="off"
              clearable
            />
          </el-form-item>
          <el-form-item
            label="昵称"
            prop="nickName"
            :label-width="formLabelWidth"
            style="width: 350px"
          >
            <el-input
              v-model="data.editForm.nickName"
              placeholder="请输入用昵称"
              autocomplete="off"
              clearable
            />
          </el-form-item>
          <el-form-item
            label="性别"
            prop="gender"
            :label-width="formLabelWidth"
            style="width: 350px"
          >
            <el-radio-group v-model="data.editForm.gender">
              <el-radio :label="'男'">男</el-radio>
              <el-radio :label="'女'">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item
            label="电话号码"
            prop="phone"
            :label-width="formLabelWidth"
            style="width: 350px"
          >
            <el-input
              v-model="data.editForm.phone"
              placeholder="请输入电话号码"
              autocomplete="off"
              clearable
            />
          </el-form-item>
          <el-form-item
            label="出生日期"
            prop="birthday"
            :label-width="formLabelWidth"
            style="width: 350px"
          >
            <el-date-picker
              v-model="data.editForm.birthday"
              type="date"
              placeholder="请选择出生日期"
              format="YYYY/MM/DD"
              value-format="YYYY-MM-DD"
              style="width: 200px"
              clearable
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submit" style="margin-left: 40%">
              修改
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <el-dialog width="50%" title="修改头像" v-model="data.uploadVisible">
      <el-upload
        ref="uploadRef"
        action="http://localhost:9090/file/upload"
        list-type="picture-card"
        :on-success="fileUploadSuccess"
      >
        <el-icon><Plus /></el-icon>
        <template #file="{ file }">
          <div>
            <img
              class="el-upload-list__item-thumbnail"
              :src="file.url"
              alt=""
            />
          </div>
        </template>
      </el-upload>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="submit"> 提交 </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive, ref, onBeforeMount, onMounted } from "vue";
import request from "../../request/request";
import { ElMessage } from "element-plus";
import { Plus } from "@element-plus/icons-vue";
const formLabelWidth = "140px";
const data = reactive({
  uploadRef: "",
  currentIndex: 0,
  avatar: "",
  villageInfo: [],
  editForm: {
    nickName: "",
    gender: "",
    birthday: "",
    phone: "",
    address: "",
    avatarUrl: "",
    username: "",
  },
  uploadVisible: false,
  formRules: {
    username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
    nickName: [{ required: true, message: "请输入昵称", trigger: "blur" }],
    gender: [{ required: true, message: "请输入性别", trigger: "blur" }],
    phone: [{ required: true, message: "请输入电话号码", trigger: "blur" }],
    birthday: [{ required: true, message: "请选择出身日期", trigger: "blur" }],
  },
});
const formRef = ref(null);
onBeforeMount(() => {
  data.villageInfo = JSON.parse(localStorage.getItem("users"));
  data.editForm == JSON.parse(localStorage.getItem("users"));
});
onMounted(() => {
  request
    .get("/api/users/findPage", {
      params: {
        pageNum: 1,
        pageSize: 100,
        username: data.villageInfo[0].username,
      },
    })
    .then((res) => {
      if (res.status == 200) {
        data.editForm = res.data.data[0];
      }
    });
});
// 头像上传成功的回调
const fileUploadSuccess = (res) => {
  data.editForm.avatarUrl = res;
};
const upload = () => {
  data.uploadVisible = true;
};

// 提交表单
const submit = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      request.post("/api/users/edit", data.editForm).then((res) => {
        if (res.status == 200) {
          ElMessage({
            showClose: true,
            message: "修改成功！",
            type: "success",
          });
          data.visible = false;
          data.uploadVisible = false;
        } else {
          ElMessage({
            showClose: true,
            message: "修改失败,请联系管理员！",
            type: "error",
          });
        }
      });
    }
  });
};
const handleEdit = () => {
  data.visible = true;
};
</script>

<style lang="less" scoped>
.mySelf {
  display: grid;
  grid-template-columns: 1fr 2fr;
  gap: 20px;
  height: calc(100vh - 140px);
  border-radius: 6px;
  overflow: auto;
  list-style: none;

  .box-content {
    margin: 10px 20%;
    div {
      margin-top: 20px;
    }
    .el-input {
      width: 200px;
    }
  }

  .el-dialog {
    .dialog-footer {
      display: flex;
      justify-content: center;
    }
    .el-form {
      display: grid;
      grid-template-columns: 1fr 1fr;
    }
  }
}
</style>
