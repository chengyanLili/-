<template>
<div class="grid-item messages">
    <div class="myMessages" v-for="(msg, index) in data.messageList" :key="index">
      <div class="msgBody">
        <div class="msg-icon">
            <el-image style="width: 100px; height: 100px" :src="url" fit="fill" />
        </div>
        <div class="msgContent" style="display: block;">
            <ul class="info">
                <li>姓名：{{ msg.name }}</li>
                <li>职务：{{ msg.post }}</li>
                <li>主要职责：{{ msg.duty }}</li>
                <li>电话：{{ msg.phone }}</li>
            </ul>
        </div>
      </div>
    </div>
  </div>

</template>

<script setup>
import { reactive, ref, onMounted } from "vue";
import request from "../../request/request";
const url =
  'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg'
const data = reactive({
  messageList: [],
  total: "",
  noticeForm: {
    title: '',
    publishName:'',
    content:''
  },
});
onMounted(() => {
  load();
});
// 获取全部的消息通知
const load = () => {
  request
    .get("/api/cadre/findPage", {
      params: {
        pageNum: 1,
        pageSize: 100,
        name:'',
        phone:'',
        idCard:''
      },
    })
    .then((res) => {
      if ((res.status = "200")) {
        data.messageList = res.data.data;
        data.total = res.data.total;
      }
    })
}
</script>
<style lang="less" scoped>
body{
  background-color: #ebecef;
  
.grid-item {
  background-color: #fff;
  height: calc(100vh - 140px);
  border-radius: 6px;
  overflow: auto;
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
      margin: 0 10px
    }
    .msgContent{
        .info{
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