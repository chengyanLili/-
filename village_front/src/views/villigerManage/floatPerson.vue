<template>
  <div class="title">流动人口管理</div>
  <div class="search">
    <el-input
      v-model="data.searchForm.name"
      style="width: 150px; margin-right: 10px"
      placeholder="请输入姓名"
    />
    <el-input
      v-model="data.searchForm.phone"
      style="width: 150px; margin-right: 10px"
      placeholder="请输入电话"
    />
    <el-input
      v-model="data.searchForm.idCard"
      style="width: 180px; margin-right: 10px"
      placeholder="请输入身份证号"
    />
    <el-button @click="load" type="primary"
      ><el-icon style="font-size: 18px; margin-right: 6px">
        <Search /> </el-icon
      >搜索</el-button
    >
    <!-- <el-button type="primary"><el-icon style="font-size: 18px;margin-right: 6px">
          <Download />
        </el-icon>导出Excel</el-button> -->
  </div>
  <div class="add">
    <el-button type="primary" @click="handleAdd">添加流动人口</el-button>
    <el-button type="danger" @click="delBatch">删除选中行数据</el-button>
  </div>

  <el-table
    :data="data.tableData"
    :height="data.maxheight"
    :border="true"
    @selection-change="selectionChange"
  >
    <el-table-column type="selection" width="55" />
    <el-table-column prop="name" label="姓名" width="120" />
    <el-table-column prop="gender" label="性别" width="60" />
    <el-table-column prop="phone" label="电话号码" width="120" />
    <el-table-column
      prop="birthday"
      label="年龄"
      width="120"
      :formatter="formatAge"
    />
    <el-table-column prop="idCard" label="身份证号码" width="168" />
    <el-table-column
      prop="whenCome"
      label="何时来此"
      width="120"
      :formatter="formatDates"
    />
    <el-table-column prop="witchFamily" label="暂住何户" width="120" />
    <el-table-column prop="whyCome" label="因何来此" width="120" />
    <el-table-column fixed="right" label="操作" width="150">
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
  <!-- 添加/修改流动人口 -->
  <el-dialog
    width="60%"
    v-model="data.dialogTableVisible"
    :title="data.isEdit ? '修改流动人口' : '添加流动人口'"
    :before-close="beforeClose"
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
        label="身份证号码"
        prop="idCard"
        :label-width="formLabelWidth"
      >
        <el-input
          v-model="data.addForm.idCard"
          placeholder="请输入身份证号"
          autocomplete="off"
        />
      </el-form-item>
      <el-form-item
        label="何时来此"
        prop="whenCome"
        :label-width="formLabelWidth"
      >
        <el-date-picker
          v-model="data.addForm.whenCome"
          type="date"
          placeholder="请选择何时来此"
          format="YYYY/MM/DD"
          value-format="YYYY-MM-DD"
          style="width: 200px"
        />
      </el-form-item>
      <el-form-item
        label="暂住何户"
        prop="witchFamily"
        :label-width="formLabelWidth"
      >
        <el-input
          v-model="data.addForm.witchFamily"
          placeholder="请输入现住何户"
          autocomplete="off"
        />
      </el-form-item>
      <el-form-item
        label="因何来此"
        prop="whyCome"
        :label-width="formLabelWidth"
      >
        <el-input
          v-model="data.addForm.whyCome"
          placeholder="请输入来村的原因"
          autocomplete="off"
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
const formRef = ref(null);
const formLabelWidth = "140px";
const data = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0,
  tableData: [],
  idList: [],
  dialogTableVisible: false,
  isEdit: false,
  searchForm: {
    name: "",
    phone: "",
    idCard: "",
  },
  addForm: {
    name: "",
    phone: "",
    birthday: "",
    gender: "",
    idCard: "",
    whenCome: "",
    witchFamily: "",
    whyCome: "",
  },
  maxheight: window.innerHeight - 280,
  formRules: {
    name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
    gender: [{ required: true, message: "请输入性别", trigger: "blur" }],
    phone: [{ required: true, message: "请输入电话号码", trigger: "blur" }],
    idCard: [{ required: true, message: "请输入身份证号", trigger: "blur" }],
  },
});
onMounted(
  (window.onresize = () => {
    return (() => {
      data.maxheight = window.innerHeight - 280;
    })();
  })
);
// 重置表单
function reset() {
  data.addForm = {
    name: "",
    phone: "",
    birthday: "",
    gender: "",
    idCard: "",
    whenCome: "",
    witchFamily: "",
    whyCome: "",
  };
  formRef.value.resetFields();
}
// 关闭表单前的回调
const beforeClose = () => {
  reset();
  formRef.value.resetFields();
  data.dialogTableVisible = false;
};
// 年龄格式
const formatAge = function (cellValue) {
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
// 日期格式
const formatDates = function (cellValue) {
  if (cellValue.whenCome !== null) {
    return cellValue.birthday.split("T")[0];
  }
  return;
};
// 获取全部的流动人口信息
const load = () => {
  request
    .get("/api/floatPopulation/findPage", {
      params: {
        pageNum: data.currentPage,
        pageSize: data.pageSize,
        name: data.searchForm.name,
        phone: data.searchForm.phone,
        idCard: data.searchForm.idCard,
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
// 添加流动人口
const handleAdd = () => {
  data.isEdit = false;
  data.dialogTableVisible = true;
};

// 编辑流动人口
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
        request
          .post("/api/floatPopulation/addOrUpdate", data.addForm)
          .then((res) => {
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
      console.log(data.addForm);
      if (valid) {
        request
          .post("/api/floatPopulation/addOrUpdate", data.addForm)
          .then((res) => {
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
const selectionChange = (selection) => {
  data.idList = selection;
};
// 批量流动人口
const delBatch = () => {
  if (data.idList.length > 0) {
    let ids = data.idList.map((v) => v.id);
    ElMessageBox.confirm("你确定删除所选记录？", "提示", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    }).then(() => {
      request.post("/api/floatPopulation/del/batch", ids).then((res) => {
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
// 删除流动人口
const handleDelete = (row) => {
  ElMessageBox.confirm("你确定删除所选记录？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  }).then(() => {
    request.delete("/api/floatPopulation/delete/" + row.id).then((res) => {
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

<style lang="less">
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
