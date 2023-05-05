<template>
  <div class="title">选举管理</div>
  <div class="search">
    <el-select
      v-model="data.searchForm.year"
      clearable
      class="m-2"
      style="margin-right: 10px"
      placeholder="请选年份"
    >
      <el-option
        v-for="item in config.YEARS"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      />
    </el-select>
    <el-select
      v-model="data.searchForm.voteType"
      clearable
      class="m-2"
      style="margin-right: 10px"
      placeholder="请选择竞选职位"
    >
      <el-option
        v-for="item in config.VOTETYPE"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      />
    </el-select>
    <el-button @click="load" type="primary"
      ><el-icon style="font-size: 18px; margin-right: 6px"> <Search /> </el-icon
      >搜索</el-button
    >
  </div>
  <div class="add">
    <el-button type="primary" @click="handleAdd">添加选举</el-button>
    <el-button type="primary" @click="handleDetail">候选人管理</el-button>
    <el-button type="danger" @click="delBatch">删除选中行数据</el-button>
  </div>

  <el-table :data="data.tableData" :height="data.maxheight" :border="true">
    <el-table-column type="selection" width="55" />
    <el-table-column prop="voteType" label="竞选职位" />
    <el-table-column prop="duty" show-overflow-tooltip label="主要职责" />
    <el-table-column prop="startTime" label="开始时间" />
    <el-table-column prop="endTime" label="结束时间" />
    <el-table-column fixed="right" label="操作" width="160">
      <template #default="scope">
        <el-button type="primary" size="small" @click="handleEdit(scope.row)"
          >编辑</el-button
        >
        <el-button size="small" type="danger" @click="handleDelete(scope.row)"
          >删除</el-button
        >
      </template>
    </el-table-column>
  </el-table>

  <!-- 添加/修改村干部 -->
  <el-dialog
    width="60%"
    v-model="data.dialogTableVisible"
    :before-close="beforeClose"
    :title="data.isEdit ? '修改选举' : '添加选举'"
  >
    <el-form ref="formRef" :model="data.addForm" :rules="data.formRules">
      <el-form-item
        label="竞选职位"
        prop="voteType"
        :label-width="formLabelWidth"
      >
        <el-select
          v-model="data.addForm.voteType"
          clearable
          class="m-2"
          style="margin-right: 10px"
          placeholder="请选择竞选职位"
        >
          <el-option
            v-for="item in config.VOTETYPE"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="主要职责" prop="duty" :label-width="formLabelWidth">
        <el-input
          type="textarea"
          v-model="data.addForm.duty"
          placeholder="请输入主要职责"
          autocomplete="off"
        />
      </el-form-item>
      <el-form-item
        label="开始时间"
        prop="startTime"
        :label-width="formLabelWidth"
      >
        <el-date-picker
          v-model="data.addForm.startTime"
          type="date"
          placeholder="请选择开始时间"
          format="YYYY/MM/DD"
          value-format="YYYY-MM-DD"
          style="width: 200px"
        />
      </el-form-item>
      <el-form-item
        label="结束时间"
        prop="endTime"
        :label-width="formLabelWidth"
      >
        <el-date-picker
          v-model="data.addForm.endTime"
          type="date"
          placeholder="请选择结束时间"
          format="YYYY/MM/DD"
          value-format="YYYY-MM-DD"
          style="width: 200px"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="reset">重置</el-button>
        <el-button type="primary" @click="submit"> 提交 </el-button>
      </span>
    </template>
  </el-dialog>
  <br />
  <el-pagination
    :current-page="data.currentPage"
    :page-size="data.pageSize"
    :page-sizes="[5, 10, 20, 30]"
    layout="total, sizes, prev, pager, next, jumper"
    :total="data.total"
    @size-change="handleSizeChange"
    @current-change="handleCurrentChange"
  />
</template>
<script setup>
import { onMounted, ref, reactive } from "vue";
import { ElMessageBox, ElMessage } from "element-plus";
import request from "../../request/request";
import router from "../../router/index.js";
import { config } from "../../constants/index.js";
const formLabelWidth = "140px";
const formRef = ref(null);
const data = reactive({
  tableData: [{ duty: "哈哈哈" }],
  currentPage: 1,
  pageSize: 10,
  total: 0,
  isEdit: false,
  idList: [],
  dialogTableVisible: false,
  isEdit: false,
  searchForm: {
    year: "",
    voteType: "",
  },
  addForm: {
    voteType: "",
    duty: "",
    startTime: "",
    endTime: "",
  },
  formRules: {
    voteType: [{ required: true, message: "请选择竞选岗位", trigger: "blur" }],
    duty: [{ required: true, message: "请输入主要职责", trigger: "blur" }],
    startTime: [{ required: true, message: "请选择开始时间", trigger: "blur" }],
    endTime: [{ required: true, message: "请选择结束时间", trigger: "blur" }],
  },
  maxheight: window.innerHeight - 280,
});
onMounted(() => {
  load();
});
onMounted(
  (window.onresize = () => {
    return (() => {
      data.maxheight = window.innerHeight - 280;
    })();
  })
);

// 详情
const handleDetail = () => {
  router.push("/candidate");
};
// 重置表单
function reset() {
  (data.addForm = {
    voteType: "",
    duty: "",
    startTime: "",
    endTime: "",
  }),
    formRef.value.resetFields();
}
// 关闭表单前的回调
const beforeClose = () => {
  reset();
  formRef.value.resetFields();
  data.dialogTableVisible = false;
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
// 获取全部的流动人口信息
const load = () => {
  request
    .get("/api/voting/findPage", {
      params: {
        pageNum: data.currentPage,
        pageSize: data.pageSize,
        year: data.searchForm.year,
        voteType: data.searchForm.voteType,
      },
    })
    .then((res) => {
      if ((res.status = "200")) {
        data.tableData = res.data.data;
        data.total = res.data.total;
      }
    });
};
// 添加村委会成员
const handleAdd = () => {
  data.isEdit = false;
  data.dialogTableVisible = true;
};

// 编辑村委会成员
const handleEdit = (row) => {
  data.isEdit = true;
  data.dialogTableVisible = true;
  data.addForm = row;
};
// 提交表单
const submit = () => {
  if (data.isEdit) {
    formRef.value.validate((valid) => {
      if (valid) {
        request.post("/api/voting/addOrUpdate", data.addForm).then((res) => {
          if (res.status == 200) {
            ElMessage({
              showClose: true,
              message: "修改成功！",
              type: "success",
            });
            load();
            beforeClose();
          } else {
            ElMessage({
              showClose: true,
              message: "修改失败,请联系管理员！",
              type: "error",
            });
          }
        });
      }
    });
  } else {
    formRef.value.validate((valid) => {
      data.addForm.year = data.addForm.startTime.substring(0, 4);
      if (valid) {
        request.post("/api/voting/addOrUpdate", data.addForm).then((res) => {
          if (res.status == 200) {
            ElMessage({
              showClose: true,
              message: "添加成功！",
              type: "success",
            });
            load();
            beforeClose();
          } else {
            ElMessage({
              showClose: true,
              message: "添加失败,请联系管理员！",
              type: "error",
            });
          }
        });
      }
    });
  }
};
// 批量删除
const delBatch = () => {
  if (data.idList.length > 0) {
    let ids = data.idList.map((v) => v.id);
    ElMessageBox.confirm("你确定删除所选记录？", "提示", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    }).then(() => {
      request.post("/api/voting/del/batch", ids).then((res) => {
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
// 删除
const handleDelete = (row) => {
  ElMessageBox.confirm("你确定删除所选记录？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  }).then(() => {
    request.delete("/api/voting/delete/" + row.id).then((res) => {
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
  .dialog-footer {
    display: flex;
    justify-content: center;
  }
}
</style>
