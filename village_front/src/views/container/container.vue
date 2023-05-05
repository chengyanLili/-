<script setup>
import Header from "../../components/header.vue";
import Aside from "../../components/aside.vue";
</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px">
        <Aside></Aside>
      </el-aside>
      <el-container>
        <el-header style="padding: 0px">
          <Header></Header>
        </el-header>
        <el-main style="background-color: #fff">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style lang="less" scoped>
.el-main {
  overflow: hidden;
}
</style>
