<script setup>
import { reactive, onMounted } from "vue";
import router from "../router";
const data = reactive({
  navMenu1: [
    { id: 1, label: "首页", icon: "HomeFilled", url: "/front/frontHome" },
    { id: 2, label: "乡村干部", icon: "Avatar", url: "/front/cadre" },
    { id: 3, label: "信息公开", icon: "Management", url: "/front/publicInfo" },
    { id: 5, label: "我的信箱", icon: "Box", url: "/front/message" },
    { id: 6, label: "我的选举", icon: "WindPower", url: "/front/myVoting" },
    { id: 7, label: "个人中心", icon: "Flag", url: "/front/myself" },
    { id: 8, label: "退出登录", icon: "SwitchButton", url: "/login" },
  ],
  navMenu: [
    { id: 1, label: "首页", url: "/front/frontHome" },
    { id: 2, label: "乡村干部", url: "/front/cadre" },
    { id: 3, label: "信息公开", url: "/front/publicInfo" },
    { id: 4, label: "登录", url: "/login" },
  ],
  policyList: [],
  currentIndex: 0,
  isLogin: [],
});
onMounted(() => {
  data.currentIndex = 1;
  router.push("/front/frontHome");
  data.isLogin = JSON.parse(localStorage.getItem("users"));
  if (data.isLogin !== null) {
    data.navMenu = data.navMenu1;
  }
});

const clickMenu = function (menu) {
  if (menu.id == 8) {
    localStorage.removeItem("users");
    data.currentIndex = menu.id;
    router.push(menu.url);
  } else {
    data.currentIndex = menu.id;
    router.push(menu.url);
  }
};
</script>

<template>
  <div class="home-nav">
    <div class="home-nav_head"></div>
    <div class="home-nav">
      <div class="home-title">
        <div
          class="homeList"
          v-for="menu in data.navMenu"
          :key="menu.url"
          :class="{ active: data.currentIndex === menu.id }"
          @click="clickMenu(menu)"
        >
          <!-- <div class="home-icon">
            <el-icon size="18"><component :is="menu.icon" /></el-icon>
          </div> -->
          <div class="home-label">{{ menu.label }}</div>
        </div>
      </div>
      <div class="main">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>
<style lang="less">
body {
  background-color: #eaebf0;
}
.home-nav {
  .home-nav_head {
    background-image: url("../assets/img/village.jpeg");
    background-repeat: no-repeat;
    // background-size: 100% 100%;
    width: 100%;
    height: 80px;
  }
  .home-nav {
    background-color: #79bbff;
    width: 100%;
    height: 40px;
    .home-title {
      display: flex;
      justify-content: center;
      align-items: center;
      list-style: none;
      color: #fff;
      .homeList {
        display: flex;
        align-items: center;
        // border: 0.5px solid #fff;
        margin: 0 2px;
        .home-label {
          margin: 8px 10px;
          cursor: pointer;
        }
      }
    }
  }
  .active {
    color: black;
  }
  .main {
    margin: 10px auto 0 auto;
    width: 80%;
    height: 100%;
  }
}
</style>
