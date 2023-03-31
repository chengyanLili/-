<template>
  <div class="content">
    <div class="nav">
      <div class="home">
        <el-icon size="28">
          <HomeFilled />
        </el-icon>
      </div>
      <ul>
        <li
          v-for="menu in data.navMenu"
          :key="menu.url"
          :class="{ active: data.currentIndex === menu.id }"
          @click="clickMenu(menu)"
        >
          {{ menu.label }}
        </li>
      </ul>
      <div class="right">
        <el-icon>
          <SwitchButton />
        </el-icon>
        <span>退出登录</span>
      </div>
    </div>
    <div class="containe">
      <div class="myMessage">
        <div class="grid-item">
          <ul>
            <li>
              <el-avatar
                shape="square"
                :size="100"
                fit="fill"
                :src="data.villageInfo.url"
              />
            </li>
            <li>姓名：{{ data.villageInfo.info.name }}</li>
            <li>性别：{{ data.villageInfo.info.gender }}</li>
            <li>年龄：{{ data.villageInfo.info.age }}</li>
            <li>政治面貌：{{ data.villageInfo.info.policy }}</li>
            <li>学历: {{ data.villageInfo.info.education }}</li>
            <li>联系电话：{{ data.villageInfo.info.phone }}</li>
            <li>家庭住址：{{ data.villageInfo.info.group }}</li>
          </ul>
        </div>
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>
<script setup>
import { reactive } from "vue";
import router from "../../router/index.js";

const props = defineProps({
  column: Number,
});
const data = reactive({
  navMenu: [
    {
      id: 2,
      label: "我的通知",
      url: "/villager/messageList",
    },
    {
      id: 3,
      label: "我的决策",
      url: "/villager/myPolicy",
    },
    {
      id: 4,
      label: "参与监督",
      url: "",
    },
    {
      id: 5,
      label: "我的户籍",
      url: "",
    },
    {
      id: 6,
      label: "个人中心",
      url: "",
    },
  ],
  currentIndex: 0,
  villageInfo: {
    url: "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
    info: {
      name: "盛明兰",
      age: 18,
      gender: "女",
      policy: "团员",
      education: "本科",
      phone: 17867463352,
      group: "大寨",
    },
  },
});

const clickMenu = function (menu) {
  data.currentIndex = menu.id;
  router.push(menu.url);
};

</script>

<style lang="less">

body {
  background-color: #e3e9ee;
}

.nav {
  background-color: #79bbff;
  width: 100%;
  height: 60px;
  display: flex;
  justify-content: space-around;
  align-items: center;

  .home {
    color: aliceblue;

    &:hover {
      color: #337ecc;
    }
  }

  ul {
    display: flex;
    justify-content: center;
    align-items: center;
    list-style: none;
    color: #fff;

    li {
      margin: 8px 20px;
      cursor: pointer;
      &:hover {
        color: #337ecc;
      }
    }
  }

  .right {
    display: flex;
    align-items: center;
    cursor: pointer;

    .el-icon {
      margin: 2px 4px 0 0;
    }

    &:hover {
      color: #fff;
    }
  }
}

.active {
  color: #337ecc;
}

.myMessage {
  height: calc(100vh - 100px);
  margin: 16px;
  display: grid;
  grid-template-columns: minmax(180px, 1fr) 2fr;
  grid-template-rows: 1;
  grid-gap: 16px;

  .grid-item {
    background-color: #fff;
    border-radius: 6px;
    overflow: auto;
    .title {
      margin: 20px 0 0 10%;
    }
  }

  ul {
    list-style: none;
    display: grid;
    grid-template-rows: 8;
    gap: 20px;
    justify-content: center;
    margin-top: 25%;
  }
}
</style>