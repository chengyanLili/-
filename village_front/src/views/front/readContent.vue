<template>
  <div class="readContent">
    <div class="back">
      <el-icon style="cursor: pointer" @click="back">
        <ArrowLeftBold />
      </el-icon>
      <span style="cursor: pointer" @click="back">返回</span>
    </div>
    <h2>{{ content.title }}</h2>
    <hr />
    <div class="read-info">
      <div class="author">作者：{{ content.publishName }}</div>
      <div class="time">时间：{{ content.createTime }}</div>
    </div>
    <div class="content">
      <div>
        <v-md-editor
          class="md"
          v-model="content.content"
          left-toolbar=""
          right-toolbar=""
          mode="preview"
        />
      </div>
    </div>
    <div v-show="content.fileUrl" class="fujian">
      附件：<el-tag @click="download(content.fileUrl)" style="cursor: pointer"
        >点击下载</el-tag
      >
    </div>
    <div>
      <el-button type="primary" @click="back" style="margin: 10px 40%"
        >关闭</el-button
      >
    </div>
  </div>
</template>

<script setup>
import { useRoute } from "vue-router";
const route = useRoute();
import router from "../../router/index";
const content = JSON.parse(route.query.content);
const download = (url) => {
  window.open(url, "_self");
};
// 富文本编辑器配置

function back() {
  router.go(-1);
}
</script>

<style lang="less">
.readContent {
  background-color: #fff;
  padding: 0px;
  height: calc(100vh - 140px);
  overflow: auto;
  .back {
    margin: 10px 0 0 20px;
    display: flex;
    align-items: center;
    span {
      display: block;
    }
  }
  h2 {
    text-align: center;
  }
  .read-info {
    display: flex;
    align-items: center;
    justify-content: center;
    .author {
      padding: 0 80px;
    }
  }
}
.fujian {
  margin: 20px;
}
</style>
