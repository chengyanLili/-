<template>
    <div class="title">土地变更管理</div>
    <div class="search">
      <el-input v-model="data.searchForm.transferName" clearable style="width:150px;margin-right: 10px;" placeholder="请输入转让人姓名" />
      <el-input v-model="data.searchForm.transferPhone" clearable style="width:150px;margin-right: 10px;" placeholder="请输入转让人电话" />
      <el-button @click="load" type="primary"><el-icon style="font-size: 18px;margin-right: 6px">
          <Search />
        </el-icon>搜索</el-button>
      <!-- <el-button type="primary"><el-icon style="font-size: 18px;margin-right: 6px">
          <Download />
        </el-icon>导出Excel</el-button> -->
    </div>
    <div class="add">
    <el-button type="primary" @click="handleAdd">添加变更</el-button>
    <el-button type="danger" @click="delBatch">删除选中行数据</el-button>
  </div>

      <el-table
    :data="data.tableData"
    :height="data.maxheight"
    :border="true"
    @selection-change="selectionChange"
  > 
    <el-table-column type="selection" width="55" />
    <el-table-column prop="transferName" label="转让人姓名" width="120" />
    <el-table-column prop="transferPhone" label="转让人电话" width="120" />
    <el-table-column prop="transferIntoName" label="转入人姓名" width="120" />
    <el-table-column prop="transferIntoPhone" label="转入人电话" width="120" />
    <el-table-column prop="notaryName" label="公证人姓名" width="120" />
    <el-table-column prop="notaryPhone" label="公证人电话" width="120" />
    <el-table-column prop="changePlace" label="变更的土地" width="120" />
    <el-table-column prop="changeDay" label="变更日期" width="120" :formatter="formatDates" />
    <el-table-column prop="changeNum" label="转让面积" width="120" />
    <el-table-column fixed="right" label="操作" width="150">
      <template #default="scope">
        <el-button type="primary" size="small" @click="handleEdit(scope.row)"
          >编辑</el-button
        >
        <el-button
          size="small"
          type="danger"
          @click="handleDelete(scope.row)"
          >删除</el-button
        >
      </template>
    </el-table-column>
  </el-table>
  <!-- 添加/修改变更 -->
  <el-dialog width="60%" v-model="data.dialogTableVisible" :title="data.isEdit ? '修改变更' : '添加变更'">
    <el-form ref="formRef" :model="data.addForm" :rules="data.formRules">
      <el-form-item label="公证人姓名" prop="notaryName" :label-width="formLabelWidth">
        <el-input v-model="data.addForm.notaryName" placeholder="请输入公证人姓名" autocomplete="off" />
      </el-form-item>
      <el-form-item label="公证人电话" prop="notaryPhone" :label-width="formLabelWidth">
        <el-input v-model="data.addForm.notaryPhone" placeholder="请输入公证人电话" autocomplete="off" />
      </el-form-item>
      <el-form-item label="变更的土地" prop="changePlace" :label-width="formLabelWidth">
        <el-input v-model="data.addForm.changePlace" placeholder="请输入变更的土地" autocomplete="off" />
      </el-form-item>
      <el-form-item label="变更日期" prop="changeDay" :label-width="formLabelWidth">
        <el-date-picker
        v-model="data.addForm.changeDay"
        type="date"
        placeholder="请选择变更日期"
        format="YYYY/MM/DD"
        value-format="YYYY-MM-DD"
        style="width:200px"
      /></el-form-item>
      <el-form-item label="转让人姓名" prop="transferName" :label-width="formLabelWidth">
        <el-input v-model="data.addForm.transferName" placeholder="请输入转让人姓名" autocomplete="off" />
      </el-form-item>
      <el-form-item label="转让人电话" prop="transferPhone" :label-width="formLabelWidth">
        <el-input v-model="data.addForm.transferPhone" placeholder="请输入转让人电话" autocomplete="off" />
      </el-form-item>
      <el-form-item label="转入人姓名" prop="transferIntoName" :label-width="formLabelWidth">
        <el-input v-model="data.addForm.transferIntoName" placeholder="请输入转入人姓名" autocomplete="off" />
      </el-form-item>
      <el-form-item label="转让面积" prop="changeNum" :label-width="formLabelWidth">
        <el-input v-model="data.addForm.changeNum" placeholder="请输入转让面积" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="reset">重置</el-button>
        <el-button type="primary" @click="submit">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
    <br>
    <el-pagination
      :current-page="data.currentPage"
      :page-size="data.pageSize"
      :page-sizes="[5,10, 20, 30]"
      layout="total, sizes, prev, pager, next, jumper"
      :total="data.total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
</template>
<script setup>
import { onMounted, reactive,ref } from 'vue';
import request from '../../request/request'
import { ElMessageBox, ElMessage } from "element-plus"
const formLabelWidth = '140px'
const formRef = ref(null);
const data = reactive({
   tableData : [],
   currentPage: 1,
  pageSize: 10,
  total: 0,
  isEdit:false,
  idList: [],
dialogTableVisible: false,
isEdit: false,
searchForm: {
  transferPhone: '',
  transferName: ''
  },
addForm: {
    notaryName: '',
    notaryPhone: '',
    changeDay: '',
    changePlace: '',
    transferName: '',
    transferIntoName: '',
    changeNum: '',
    transferPhone: '',
    transferIntoPhone:''
  },
  maxheight :window.innerHeight - 280,
  formRules: {
    notaryName: [{ required: true, message: "请输入公证人姓名", trigger: "blur" }],
    transferName: [{ required: true, message: "请输入转让人性别", trigger: "blur" }],
    transferIntoName: [{ required: true, message: "请输入转入人姓名", trigger: "blur" }],
    changePlace: [{ required: true, message: "请输入变更的土地", trigger: "blur" }],
    changeDay:[{ required: true, message: "请选择变更日期", trigger: "blur" }]
  },
})
onMounted(
  () => {
    load()
  }
)
  onMounted(
    window.onresize = () => {
      return (() => {
          data.maxheight = window.innerHeight - 280
      })()
    }
  )
// 获取全部土地变更信息
const load = () => {
  request.get('/api/land/findPage', {
    params: {
      pageNum: data.currentPage,
      pageSize: data.pageSize,
      transferName: data.searchForm.transferName,
      transferPhone: data.searchForm.transferPhone,
    }
  }).then((res) => {
    if (res.status = '200') {
      data.tableData = res.data.data
      data.total = res.data.total
    }
  })
}
const selectionChange = (selection) => {
  data.idList = selection;
};
    // 关闭表单前的回调
const beforeClose = () => {
  reset()
  formRef.value.resetFields()
  data.dialogTableVisible = false
}
  // 日期格式
  const formatDates = function (cellValue) {
   if(cellValue.changeDay !== null) {
   return cellValue.changeDay.split('T')[0]
  }
   return
  }
// 添加变更土地
const handleAdd = () => {
  data.isEdit = false
  data.dialogTableVisible = true
}
// 每页个数的改变
const handleSizeChange = (val) => {
  data.pageSize = val;
  load();
}
// 跳转到哪里去
const handleCurrentChange = (val) => {
  data.currentPage = val;
  load();
}
const handleEdit = (row) => {
  data.isEdit = true
  data.dialogTableVisible = true
  data.addForm = row
  console.log('row',row);
}
// 批量流动人口
const delBatch = () => {
  if(data.idList.length>0) {
    let ids = data.idList.map((v) => v.id);
  ElMessageBox.confirm("你确定删除所选记录？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  }).then(() => {
    request.post("/api/land/del/batch", ids).then((res) => {
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
  }else{
    ElMessage({
          showClose: true,
          message: "请至少选择一条数据",
          type: "warning",
        });
  }
  
};
// 删除流动人口
const handleDelete = ( row ) => {
  ElMessageBox.confirm("你确定删除所选记录？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  }).then(() => {
    request.delete("/api/land/delete/" + row.id).then((res) => {
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
const reset = () => {
  data.addForm ={
    notaryName: '',
    notaryPhone: '',
    changeDay: '',
    changePlace: '',
    transferName: '',
    transferIntoName: '',
    changeNum: '',
    transferPhone: '',
    transferIntoPhone:''
  }
  formRef.value.resetFields()
}
// 提交表单
const submit = () => {
  if(data.isEdit){
    formRef.value.validate((valid)=>{
    if(valid){
      request.post("/api/land/addOrUpdate",data.addForm).then((res)=>{
        if(res.status == 200){
          ElMessage({
            showClose: true,
            message: "修改成功！",
            type: "success",
            })
            load()
            beforeClose()
          }else{
              ElMessage({
              showClose: true,
              message: "修改失败,请联系管理员！",
              type: "error",
          })
        }
      })
    }
  })
  }else{
    formRef.value.validate((valid)=>{
    if(valid){
      request.post("/api/land/addOrUpdate",data.addForm).then((res)=>{
        if(res.status == 200){
          ElMessage({
            showClose: true,
            message: "添加成功！",
            type: "success",
            })
            load()
            beforeClose()
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
  }
}
</script>


<style lang="less" scoped>
  .title{
   font-size: 18px;
  }
  .search{
    // width: 200px;
    margin: 10px 0;
    display: flex;
  }
  .add{
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