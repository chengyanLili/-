<template>
  <div class="title">户籍管理</div>
  <div class="operation">
    <div class="search">
      <el-input
        v-model="allData.householdName"
        style="width: 150px; margin-right: 10px"
        clearable
        placeholder="请输入姓名"
      />
      <el-button @click="load" type="primary"
        ><el-icon style="font-size: 18px; margin-right: 6px">
          <Search /> </el-icon
        >搜索</el-button
      >
    </div>
    <div class="add">
      <el-button @click="addFamily" type="primary">添加户籍</el-button>
      <el-button @click="delBatch" type="danger">删除所选户籍</el-button>
    </div>
  </div>
  <el-table
    :data="allData.tableData"
    :height="allData.maxheight"
    :border="true"
    @selection-change="selectionChange"
  >
    <el-table-column type="selection" width="55" />
    <el-table-column prop="accountNo" label="户号" width="120" />
    <el-table-column prop="householdName" label="户主姓名" width="120" />
    <el-table-column prop="householdIdCard" label="户主身份证号"  />
    <el-table-column prop="householdPhone" label="户主电话号码"  />
    <el-table-column prop="inGroup" label="所在小组" />
    <el-table-column fixed="right" label="操作" width="150">
      <template #default="scope">
        <el-button
          type="primary"
          size="small"
          @click="handleDetail(scope.$index, scope.row)"
          >详情</el-button
        >
        <el-button
          size="small"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)"
          >删除</el-button
        >
      </template>
    </el-table-column>
  </el-table>

  <el-dialog
    width="60%"
    v-model="allData.dialogTableVisible"
    title="添加户籍"
    :before-close="beforeClose"
  >
    <el-form :model="allData.addForm" ref="formRef" :rules="allData.formRules">
      <el-form-item label="户号" prop="accountNo" :label-width="formLabelWidth">
        <el-input
          v-model="allData.addForm.accountNo"
          placeholder="请输入户号"
          autocomplete="off"
        />
      </el-form-item>
      <el-form-item label="户主姓名" prop="householdName" :label-width="formLabelWidth">
        <el-input
          v-model="allData.addForm.householdName"
          placeholder="请输入户主姓名"
          autocomplete="off"
        />
      </el-form-item>
      <el-form-item label="出生日期" prop="birthday" :label-width="formLabelWidth">
        <el-date-picker
        v-model="allData.addForm.birthday"
        type="date"
        placeholder="请选择出生日期"
        format="YYYY/MM/DD"
        value-format="YYYY-MM-DD"
      />
      </el-form-item>
      <el-form-item label="户主身份证号" prop="householdIdCard" :label-width="formLabelWidth">
        <el-input
          v-model="allData.addForm.householdIdCard"
          placeholder="户主身份证号"
          autocomplete="off"
        />
      </el-form-item>
      <el-form-item label="户主电话号码" prop="householdPhone" :label-width="formLabelWidth">
        <el-input
          v-model="allData.addForm.householdPhone"
          placeholder="请输入户主电话号码"
          autocomplete="off"
        />
      </el-form-item>
      <el-form-item label="所在小组" prop="inGroup" :label-width="formLabelWidth">
        <el-input
          v-model="allData.addForm.inGroup"
          placeholder="请输入小组"
          autocomplete="off"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="reset">重置</el-button>
        <el-button type="primary" @click="save"> 保存 </el-button>
      </span>
    </template>
  </el-dialog>

  <br />
  <el-pagination
    v-model:current-page="allData.currentPage"
    v-model:page-size="allData.pageSize"
    :page-sizes="[5, 10, 20, 25]"
    layout="total, sizes, prev, pager, next, jumper"
    :total="allData.total"
    @size-change="handleSizeChange"
    @current-change="handleCurrentChange"
  />
</template>

<script setup>
import { onMounted, reactive,ref } from "vue";
import request from "../../../request/request.js";
import router from "../../../router/index";
import { ElMessageBox, ElMessage } from "element-plus";
const formLabelWidth = "140px";
const formRef = ref(null);
const allData = reactive({
  tableData: [],
  maxheight: window.innerHeight - 280,
  currentPage: 1,
  pageSize: 10,
  total: 0,
  dialogTableVisible: false,
  householdName: "",
  addForm: {
    accountNo: "",
    householdName: "",
    householdIdCard: "",
    householdPhone: "",
    birthday:'',
    inGroup:'',
    relation:'户主'
  },
  formRules: {
    accountNo: [{ required: true, message: "请输入户号", trigger: "blur" }],
    householdName: [{ required: true, message: "请输入户主姓名", trigger: "blur" }],
    householdIdCard: [{ required: true, message: "请输入户主身份证号", trigger: "blur" }],
    householdPhone: [{ required: true, message: "请输入户主电话号码", trigger: "blur" }],
    inGroup: [{ required: true, message: "请输入所在小组", trigger: "blur" }],
  }
});

// 获取全部的户籍
const load = () => {
  request
    .get("/api/family/findPage", {
      params: {
        pageNum: allData.currentPage,
        pageSize: allData.pageSize,
        householdName: allData.householdName,
      },
    })
    .then((res) => {
      if ((res.status = "200")) {
        allData.tableData = res.data.data || [];
        allData.total = res.data.total;
      }
    });
};
// 添加
const addFamily = () => {
  allData.dialogTableVisible = true
  
};
// 重置表单
const reset = () => {
  formRef.value.resetFields();
};
// 关闭表单前的回调
const beforeClose = () => {
  formRef.value.resetFields();
  allData.dialogTableVisible = false;
};

// 添加户籍
const save = () => {
  formRef.value.validate((valid)=>{
    allData.addForm.memberName = allData.addForm.householdName
    if(valid) {
      request.post("/api/family/addHousehold",allData.addForm).then(res=>{
        if(res.status == 200){
          ElMessage({
          showClose: true,
          message: "添加成功！",
          type: "success",
        });
        allData.dialogTableVisible = false;
        formRef.value.resetFields();
        load();
        }else{
          ElMessage({
          showClose: true,
          message: "添加失败,请联系管理员！",
          type: "error",
        })
        }
      })
    }
  })
  
};

// 详情的回调
const handleDetail = (index,row) => {
  router.push({
    name: "editFamily",
    query:{
      accountNo: row.accountNo
    }
  });
};
// 删除户籍
const handleDelete = (index, row) => {
  ElMessageBox.confirm("你确定删除所选记录？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  }).then(() => {
    request.delete("/api/family/delete/" + row.familyId).then((res) => {
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
const selectionChange = (selection) => {
  allData.idList = selection;
};
// 批量删除户籍
const delBatch = () => {
  if (allData.idList) {
    let ids = allData.idList.map((v) => v.familyId);
    ElMessageBox.confirm("你确定删除所选记录？", "提示", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    }).then(() => {
      request.post("/api/family/del/batch", ids).then((res) => {
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
            type: "info",
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

onMounted(
  (window.onresize = () => {
    return (() => {
      allData.maxheight = window.innerHeight - 280;
    })();
  })
);
load();
// 每页个数的改变
const handleSizeChange = (val) => {
  allData.pageSize = val;
  load();
};
// 跳转到哪里去
const handleCurrentChange = (val) => {
  allData.currentPage = val;
  load();
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
  .dialog-footer{
    display: flex;
    justify-content: center;
  }
}

</style>
