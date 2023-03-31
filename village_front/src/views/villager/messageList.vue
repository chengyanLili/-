<template>
    <div v-if="urlName == 'messageList'" class="grid-item messages">
          <div class="title">
            <el-badge :value="12" class="item">
              <span>消息通知</span>
            </el-badge>
          </div>
          <Messages></Messages>
        </div>
</template>

<script setup>
import Messages from "../../components/myMessage/message.vue";
import router from "../../router/index.js";

// 获取当前路由
let urlName = router.currentRoute.value.name
console.log(urlName);
</script>

<style lang="less">
.grid-item {
    background-color: #fff;
    border-radius: 6px;
    overflow: auto;
    .title {
      margin: 20px 0 0 10%;
    }
  }
</style>