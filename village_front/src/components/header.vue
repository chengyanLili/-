<script setup>
import { onBeforeMount, reactive, watch } from "vue";
import router from "../router/index";
let data = reactive({
  isShow: false,
  activeTollId: "",
  infoItem: ["个人中心", "退出登录"],
  users: [],
});
onBeforeMount(() => {
  data.users = JSON.parse(localStorage.getItem("users"));
});
function exit() {
  router.push({
    name: "login",
  });
  localStorage.removeItem("users");
}
watch(
  //监听控制div显示隐藏的变量activeTollId
  () => data.activeTollId,
  //如果得到的新数值不为空,说明div元素显示了,给整个页面上添加mousedown鼠标点击事件调用onbourfun
  //否则得到的新数值为空说明div元素隐藏了,那就移除调mousedown点击事件
  (val) =>
    val
      ? document.addEventListener("mousedown", onbourfun)
      : document.removeEventListener("mousedown", onbourfun)
);

//定义隐藏方法,用来区别点击的是否为 要隐藏的 div元素,如果点击的不是带有needHide-Container类名的元素则惦记的为div外的元素 这时我们要隐藏div所以给activeTollId.value赋值为''
const onbourfun = (event) => {
  //   const ev = window.event || event;
  const path = event.path || (event.composedPath && event.composedPath());
  //   console.log(val);

  if (!path.includes(document.querySelector(".needHide")))
    data.activeTollId = "";
};
const myself = () => {
  router.push("/self");
};
</script>
<template>
  <div class="myheader">
    <div class="avatar">
      <el-avatar :src="data.users[0].avatarUrl" />
      <el-dropdown>
        <span>
          <el-icon style="margin-top: 10px; color: #fff">
            <Operation />
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="myself">个人中心</el-dropdown-item>
            <el-dropdown-item @click="exit">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<style lang="less">
.myheader {
  background-color: #6cb5ff;
  color: #fff;
  height: 3.75rem;
  display: flex;
  align-items: center;
  padding-left: 80%;
  justify-content: space-around;
  position: relative;

  .avatar {
    span {
      font-size: 22px;
      vertical-align: middle;
      padding-left: 0.625rem;
    }
  }

  .box-card {
    position: absolute;
    top: 3.75rem;
    min-width: 150px;
  }
}
</style>
