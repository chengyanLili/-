<template>
  <div class="title">用户管理</div>
  <div class="search">
    <el-input
      v-model="data.username"
      clearable
      style="width: 150px; margin-right: 10px"
      placeholder="请输入用户名"
    />
    <el-button @click="load" type="primary"
      ><el-icon style="font-size: 18px; margin-right: 6px"> <Search /> </el-icon
      >搜索</el-button
    >
  </div>
  <div class="add">
    <el-button type="danger" @click="delBatch">批量删除</el-button>
  </div>

  <el-table
    :data="data.tableData"
    :height="data.maxheight"
    :border="true"
    @selection-change="selectionChange"
  >
    <el-table-column type="selection" width="55" />
    <el-table-column prop="username" label="用户名" width="120" />
    <el-table-column prop="avatarUrl" label="头像" width="120">
      <template #default="scope">
        <el-image
          style="width: 50px; height: 50px"
          :src="scope.row.avatarUrl"
          fit="fill"
        />
      </template>
    </el-table-column>
    <el-table-column prop="gender" label="性别" width="120" />
    <el-table-column
      prop="birthday"
      label="年龄"
      width="120"
      :formatter="formatAge"
    />
    <el-table-column prop="phone" label="电话号码" width="120" />
    <el-table-column prop="nickName" label="昵称" width="120" />
    <el-table-column
      prop="identify"
      label="身份"
      width="120"
      :formatter="formatRole"
    />
    <el-table-column prop="nickName" label="昵称" width="168" />
    <el-table-column fixed="right" label="操作" width="150">
      <template #default="scope">
        <el-button size="small" type="danger" @click="handleDelete(scope.row)"
          >删除</el-button
        >
      </template>
    </el-table-column>
  </el-table>
  <br />
  <el-pagination
    :current-page="data.currentPage"
    :page-size="data.pageSize"
    :page-sizes="[5, 10, 20, 25]"
    layout="total, sizes, prev, pager, next, jumper"
    :total="data.total"
    @size-change="handleSizeChange"
    @current-change="handleCurrentChange"
  />
</template>
<script setup>
import { ref, reactive, onMounted } from "vue";
import request from "../../request/request";
import { ElMessageBox, ElMessage } from "element-plus";
const data = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0,
  tableData: [],
  idList: [],
  username: "",
  maxheight: window.innerHeight - 280,
});
onMounted(
  (window.onresize = () => {
    return (() => {
      data.maxheight = window.innerHeight - 280;
    })();
  })
);
//   身份格式
const formatRole = function (cellValue) {
  if (cellValue.identify == 1) {
    return "村干部";
  }
  return "普通村民";
};

// 年龄格式
const formatAge = function (cellValue) {
  if (cellValue.birthday == null) return "暂无数据";
  if (cellValue.birthday !== null) {
    let currentYear = new Date().getFullYear(); //当前的年份
    let calculationYear = new Date(
      cellValue.birthday.split("T")[0]
    ).getFullYear(); //计算的年份
    const wholeTime =
      currentYear + cellValue.birthday.split("T")[0].substring(4); //周岁时间
    const calculationAge = currentYear - calculationYear; //按照年份计算的年龄
    //判断是否过了生日
    if (new Date().getTime() > new Date(wholeTime).getTime()) {
      return calculationAge;
    } else {
      return calculationAge - 1;
    }
  } else {
    return;
  }
};
// 获取全部的用户
const load = () => {
  request
    .get("/api/users/findPage", {
      params: {
        pageNum: data.currentPage,
        pageSize: data.pageSize,
        username: data.username,
      },
    })
    .then((res) => {
      if ((res.status = "200")) {
        data.tableData = res.data.data;
        data.total = res.data.total;
      }
    });
};
// 每页个数的改变
const handleSizeChange = (val) => {
  data.pageSize = val;
  load();
};
// 跳转到哪里去
const handleCurrentChange = (val) => {
  data.currentPage = val;
  load();
};
const selectionChange = (selection) => {
  data.idList = selection;
};
// 批量删除用户
const delBatch = () => {
  if (data.idList.length > 0) {
    let ids = data.idList.map((v) => v.id);
    ElMessageBox.confirm("你确定删除所选记录？", "提示", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    }).then(() => {
      request.post("/api/users/del/batch", ids).then((res) => {
        if (res.status == 200) {
          ElMessage({
            showClose: true,
            message: "删除成功！",
            type: "success",
          });
          load();
        } else {
          ElMessage({
            showClose: true,
            message: "删除失败！",
            type: "error",
          });
        }
      });
    });
  } else {
    ElMessage({
      showClose: true,
      message: "请至少选择一条数据",
      type: "warning",
    });
  }
};
// 删除用户和
const handleDelete = (row) => {
  ElMessageBox.confirm("你确定删除所选记录？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  }).then(() => {
    request.delete("/api/users/delete/" + row.id).then((res) => {
      if (res.status == 200) {
        ElMessage({
          showClose: true,
          message: "删除成功！",
          type: "success",
        });
        load();
      } else {
        ElMessage({
          showClose: true,
          message: "删除失败！",
          type: "error",
        });
      }
    });
  });
};
onMounted(() => {
  load();
});
</script>

<style lang="less" scoped>
.title {
  font-size: 18px;
}
.search {
  // width: 200px;
  margin: 10px 0;
  display: flex;
}
.add {
  display: flex;
  margin-bottom: 10px;
}
.el-dialog {
  .el-form {
    display: grid;
    grid-template-columns: 1fr 1fr;
  }
}
</style>
