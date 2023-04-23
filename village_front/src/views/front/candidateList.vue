<template>
  <div class="candidateList">
    <div class="back">
      <el-icon style="cursor: pointer" @click="back">
        <ArrowLeftBold />
      </el-icon>
      <span style="cursor: pointer" @click="back">返回</span>
    </div>
    <div
      class="myMessages"
      v-for="(msg, index) in data.messageList"
      :key="index"
    >
      <div class="msgBody">
        <div class="msg-icon">
          <el-image
            style="width: 100px; height: 100px"
            :src="msg.avatarUrl"
            fit="fill"
          />
        </div>
        <div class="msgContent">
          <ul class="info">
            <li>竞选岗位：{{ msg.voteType }}</li>
            <li>姓名：{{ msg.name }}</li>
            <li>性别：{{ msg.gender }}</li>
            <li>电话：{{ msg.phone }}</li>
          </ul>
          <div class="operate">
            <el-button type="primary" @click="handleDetail(msg)"
              >候选人详情</el-button
            >
          </div>
        </div>
      </div>
    </div>
  </div>
  <el-drawer v-model="drawer" :title="data.title">
    <ul class="info" style="color: black; display: grid; margin-top: 50px">
      <li>
        <el-avatar
          shape="square"
          :size="100"
          fit="fill"
          :src="data.infos.avatarUrl"
        />
      </li>
      <li>姓名：{{ data.infos.name }}</li>
      <li>性别：{{ data.infos.gender }}</li>
      <li>出生日期：{{ data.infos.birthday }}</li>
      <li>联系电话：{{ data.infos.phone }}</li>
      <li>经历：{{ data.infos.experience }}</li>
      <li>
        <el-tag class="ml-2" type="success" @click="handleVote">投票</el-tag>
      </li>
    </ul>
  </el-drawer>
</template>

<script setup>
import { reactive, ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import router from "../../router/index";
const route = useRoute();
import request from "../../request/request";
import { ElMessage } from "element-plus";
const drawer = ref(false);
const data = reactive({
  // 投过票的列表
  votedList: [],
  messageList: [],
  total: "",
  title: "",
  infos: {},
  noticeForm: {
    title: "",
    publishName: "",
    content: "",
  },
});
onMounted(() => {
  load();
});
// 获取全部的消息通知
const load = () => {
  request
    .get("/api/candidate/findPage", {
      params: {
        pageNum: 1,
        pageSize: 100,
        name: "",
        phone: "",
        voteType: route.query.voteType,
      },
    })
    .then((res) => {
      if ((res.status = "200")) {
        data.messageList = res.data.data;
        data.total = res.data.total;
      }
    });
};
// 返回
function back() {
  router.back();
}
// 日期格式
const formatDates = function (cellValue) {
  if (cellValue !== null) {
    return cellValue.split("T")[0];
  }
  return;
};
const handleVote = () => {
  // 记录是否投票
  let num = {};
  let repeat = 0;
  num.username = localStorage.getItem("users").username;
  num.name = data.infos.name;
  num.voteType = data.infos.voteType;
  data.infos.votes += 1;
  data.votedList.forEach((v) => {
    if (
      v.username == num.username &&
      v.name == data.infos.name &&
      v.voteType == data.infos.voteType
    ) {
      ElMessage({
        showClose: true,
        message: "每个人只能投一票哦",
        type: "info",
      });
      repeat = 1;
    }
  });
  if (!repeat) {
    request.post("/api/candidate/addOrUpdate", data.infos).then((res) => {
      if (res.status == 200) {
        ElMessage({
          showClose: true,
          message: "投票成功！",
          type: "success",
        });
        data.votedList.push(num);
      } else {
        ElMessage({
          showClose: true,
          message: "投票失败,请联系管理员！",
          type: "error",
        });
      }
    });
  }
};
const handleDetail = (msg) => {
  data.title = msg.name;
  data.infos = Object.assign({}, msg);
  data.infos.birthday = formatDates(msg.birthday);
  drawer.value = true;
};
</script>
<style lang="less" scoped>
body {
  background-color: #ebecef;

  .candidateList {
    background-color: #fff;
    height: calc(100vh - 140px);
    border-radius: 6px;
    overflow: auto;
    .back {
      margin: 10px 10%;
      display: flex;
      align-items: center;
      span {
        display: block;
      }
    }
    .myMessages {
      .msgBody {
        width: 80%;
        overflow: hidden;
        /*溢出的部分隐藏*/
        white-space: nowrap;
        /*文本不换行*/

        height: 150px;
        margin: 10px auto;
        border: 1px solid rgb(190, 187, 187);
        border-radius: 6px;
        display: flex;
        align-items: center;

        .msg-icon {
          margin: 0 10px;
        }
        .msgContent {
          display: flex;
          .operate {
            margin: 50px;
          }
          .info {
            display: block;
            list-style: none;
            font-size: 15px;
            font-weight: bold;
            color: black;
          }
        }
      }
    }
  }
}
</style>
