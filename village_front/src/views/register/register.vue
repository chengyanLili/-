<script setup>
import { ref, reactive } from "vue";
import { ElMessage } from "element-plus";
import request from "../../request/request.js";
import router from "../../router/index.js";

const ruleFormRef = ref(null);
const addForm = reactive({
  username: "",
  password: "",
  avatarUrl:
    "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
});

const rules = reactive({
  username: [{ required: "true", message: "账户不能为空", trigger: "blur" }],
  password: [{ required: "true", message: "密码不能为空", trigger: "blur" }],
});
// 进行登录验证
const register = async () => {
  if (!ruleFormRef) return;
  ruleFormRef.value.validate((valid) => {
    if (valid) {
      request.post("/api/users/add", addForm).then((res) => {
        if (res.status == 200) {
          ElMessage({
            showClose: true,
            message: "注册成功",
            type: "success",
          });
          router.push("/login");
        } else {
          ElMessage({
            showClose: true,
            message: "注册失败",
            type: "error",
          });
        }
      });
    } else {
      ElMessage({
        showClose: true,
        message: "用户名或密码不能为空！",
        type: "error",
      });
      return false;
    }
  });
};
</script>

<template>
  <div class="wrap">
    <div class="login">
      <div class="form">
        <el-form
          ref="ruleFormRef"
          :model="addForm"
          status-icon
          :rules="rules"
          label-width="120px"
          class="addForm"
        >
          <el-form-item label="用户名：" prop="username">
            <el-input
              v-model="addForm.username"
              autocomplete="off"
              placeholder="请输入用户名"
            ></el-input>
          </el-form-item>
          <el-form-item label="密码：" prop="password">
            <el-input
              v-model="addForm.password"
              type="password"
              autocomplete="off"
              placeholder="请输入密码"
            ></el-input>
          </el-form-item>
          <br />
          <el-form-item>
            <el-button type="primary" @click="register">立即注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<style lang="less" scoped>
.wrap {
  background-image: url(../../assets/img/village.jpeg);
  background-repeat: no-repeat;
  background-size: 100% 100%;
  width: 100%;
  height: 100%;
  position: fixed;
  opacity: 0.8;

  .login {
    width: 30%;
    height: 35%;
    min-width: 300px;
    background-color: #fff;
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    margin: auto;
    opacity: 0.9;
    border-radius: 8px;
    box-shadow: 0 0 10px 5px #b1b3b8;

    .form {
      margin-top: 3rem;
      width: 300px;
    }
  }
}
a {
  //去掉下换线
  text-decoration: none;

  //文字颜色更改
  color: #fff;
}
</style>
