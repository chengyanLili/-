<template>
  <div class="title">候选人管理</div>
  <div class="search">
    <el-input
      v-model="data.searchForm.name"
      clearable
      style="width: 150px; margin-right: 10px"
      placeholder="请输入姓名"
    />
    <el-input
      v-model="data.searchForm.phone"
      clearable
      style="width: 150px; margin-right: 10px"
      placeholder="请输入电话"
    />
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
    <el-button type="primary" @click="handleAdd">添加候选人</el-button>
    <el-button type="danger" @click="delBatch">删除选中行数据</el-button>
  </div>

  <el-table
    :data="data.tableData"
    :height="data.maxheight"
    :border="true"
    @selection-change="selectionChange"
  >
    <el-table-column type="selection" width="55" />
    <el-table-column prop="name" label="姓名" />
    <el-table-column prop="gender" label="性别" />
    <el-table-column prop="birthday" label="年龄" :formatter="formatAge" />
    <el-table-column prop="voteType" label="竞选岗位" width="120" />
    <el-table-column prop="whichGroup" label="所在小组" width="120" />
    <el-table-column prop="phone" label="电话号码" width="120" />
    <el-table-column prop="avatarUrl" label="照片" width="80">
      <template #default="scope">
        <el-image
          style="width: 50px; height: 50px"
          :src="scope.row.avatarUrl"
          fit="fill"
        />
      </template>
    </el-table-column>
    <el-table-column
      prop="votes"
      label="获得票数"
      :formatter="formatterVotes"
    />
    <el-table-column prop="experience" label="经历" />
    <el-table-column fixed="right" label="操作" width="200">
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

  <!-- 添加/修改候选人 -->
  <el-dialog
    width="60%"
    v-model="data.dialogTableVisible"
    :before-close="beforeClose"
    :title="data.isEdit ? '修改候选人' : '添加候选人'"
  >
    <el-form ref="formRef" :model="data.addForm" :rules="data.formRules">
      <el-form-item label="姓名" prop="name" :label-width="formLabelWidth">
        <el-input
          v-model="data.addForm.name"
          placeholder="请输入姓名"
          autocomplete="off"
        />
      </el-form-item>
      <el-form-item label="性别" prop="gender" :label-width="formLabelWidth">
        <el-radio-group v-model="data.addForm.gender">
          <el-radio :label="'男'">男</el-radio>
          <el-radio :label="'女'">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="电话号码" prop="phone" :label-width="formLabelWidth">
        <el-input
          v-model="data.addForm.phone"
          placeholder="请输入电话号码"
          autocomplete="off"
        />
      </el-form-item>
      <el-form-item
        label="出生日期"
        prop="birthday"
        :label-width="formLabelWidth"
      >
        <el-date-picker
          v-model="data.addForm.birthday"
          type="date"
          placeholder="请选择出生日期"
          format="YYYY/MM/DD"
          value-format="YYYY-MM-DD"
          style="width: 200px"
        />
      </el-form-item>
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
      <el-form-item
        label="经历"
        prop="experience"
        :label-width="formLabelWidth"
      >
        <el-input
          type="textarea"
          v-model="data.addForm.experience"
          placeholder="请输入主要职责"
          autocomplete="off"
        />
      </el-form-item>
      <el-form-item
        label="所在小组"
        prop="whichGroup"
        :label-width="formLabelWidth"
      >
        <el-input
          type="textarea"
          v-model="data.addForm.whichGroup"
          placeholder="请输入所在的小组"
          autocomplete="off"
        />
      </el-form-item>
      <el-form-item label="照片" prop="avatarUrl" :label-width="formLabelWidth">
        <el-upload
          ref="uploadRef"
          class="upload-demo"
          action="http://localhost:9090/file/upload"
          :auto-upload="true"
          :on-success="fileUploadSuccess"
        >
          <template #trigger>
            <el-button type="primary">点击上传</el-button>
          </template>
        </el-upload>
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
  searchForm: {
    name: "",
    phone: "",
    voteType: "",
  },
  addForm: {
    name: "",
    phone: "",
    age: "",
    gender: "",
    avatarUrl: "",
    voteType: "",
    experience: "",
  },
  formRules: {
    name: [{ required: true, message: "请输入竞选者姓名", trigger: "blur" }],
    phone: [
      { required: true, message: "请输入竞选者电话号码", trigger: "blur" },
    ],
    birthday: [{ required: true, message: "请选择生产日期", trigger: "blur" }],
    gender: [{ required: true, message: "请选择性别", trigger: "blur" }],
    experience: [{ required: true, message: "请输入经历", trigger: "blur" }],
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
// 年龄格式
const formatAge = function (cellValue) {
  if (cellValue.birthday !== null && cellValue.birthday !== undefined) {
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
    return "暂无数据";
  }
};
const formatterVotes = function (cellValue) {
  return cellValue.votes || 0;
};
const selectionChange = (selection) => {
  data.idList = selection;
};
// 头像上传成功的回调
const fileUploadSuccess = (res) => {
  data.addForm.avatarUrl = res;
};
// 重置表单
function reset() {
  (data.addForm = {
    name: "",
    phone: "",
    age: "",
    gender: "",
    avatarUrl: "",
    voteType: "",
    experience: "",
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
// 获取全部的候选人信息
const load = () => {
  request
    .get("/api/candidate/findPage", {
      params: {
        pageNum: data.currentPage,
        pageSize: data.pageSize,
        name: data.searchForm.name,
        phone: data.searchForm.phone,
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
        request.post("/api/candidate/addOrUpdate", data.addForm).then((res) => {
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
      if (valid) {
        request.post("/api/candidate/addOrUpdate", data.addForm).then((res) => {
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
      request.post("/api/candidate/del/batch", ids).then((res) => {
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
    request.delete("/api/candidate/delete/" + row.id).then((res) => {
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
