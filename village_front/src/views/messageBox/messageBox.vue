<template>
    <div class="title">收件箱</div>
    <div class="search">
      <el-input v-model="data.searchTheme" style="width:150px;margin-right: 10px;" placeholder="请输入主题" />
      <el-button @click="load" type="primary"><el-icon style="font-size: 18px;margin-right: 6px">
          <Search />
        </el-icon>搜索</el-button>
    </div>
    <div class="add">
    <el-button type="danger" @click="delBatch">删除选中行数据</el-button>
  </div>
<el-table
    :data="data.tableData"
    :border="true"
    :height="data.maxheight"
    @selection-change="selectionChange"
  > 
    <el-table-column type="selection" width="55" />
    <el-table-column prop="theme" label="主题" width="120" show-overflow-tooltip />
    <el-table-column prop="content" label="内容" width="120" show-overflow-tooltip />
    <el-table-column prop="sendName" label="发件人姓名" width="120" show-overflow-tooltip />
    <el-table-column prop="sendTime" sortable label="发信时间" width="120" :formatter="formatDates"/>
    <el-table-column prop="handleName" label="处理人姓名" width="120" />
    <el-table-column prop="msgStatus" label="信息状态" width="120" >
      <template #default="scope">
        <el-tag v-if="scope.row.msgStatus == '待处理'" type="info" size="small">待处理</el-tag>
        <el-tag v-if="scope.row.msgStatus == '已处理'" type="success" size="small">已处理</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="replyTime" sortable label="处理时间" width="120" :formatter="formatDates"/>
    <el-table-column fixed="right" label="操作" width="150">
      <template #default="scope">
        <el-button type="primary" size="small" @click="handleEdit(scope.row)">处理</el-button>
        <el-button
          size="small"
          type="danger"
          @click="handleDelete(scope.row)"
          >删除</el-button
        >
      </template>
    </el-table-column>
  </el-table>
  <br>
  <el-pagination
      :current-page="data.currentPage"
      :page-size="data.pageSize"
      :page-sizes="[5, 10, 20, 25]"
      layout="total, sizes, prev, pager, next, jumper"
      :total="data.total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    <el-dialog 
    width="50%" 
    v-model="data.dialogTableVisible" 
    :before-close="beforeClose"
    >
    <template #header>
      <div class="my-header">
        <h4> <el-icon><InfoFilled /></el-icon>回复信件</h4>
      </div>
    </template>
    <el-form ref="formRef" :model="data.addForm">
      <el-form-item label="信息主题" prop="theme" :label-width="formLabelWidth">
        <el-input disabled v-model="data.addForm.theme" autocomplete="off" />
      </el-form-item>
      <el-form-item label="收信日期" prop="sendTime" :label-width="formLabelWidth">
        <el-input disabled v-model="data.addForm.sendTime" autocomplete="off" />
      </el-form-item>
      <el-form-item label="发信人姓名" prop="sendName" :label-width="formLabelWidth">
        <el-input disabled v-model="data.addForm.sendName" autocomplete="off" />
      </el-form-item>
      <el-form-item label="信件内容"  prop="content" :label-width="formLabelWidth">
        <el-input disabled :autosize="{ minRows: 2, maxRows: 4 }" type="textarea"  v-model="data.addForm.content"  autocomplete="off" />
      </el-form-item>
    </el-form>
      <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>回复信件</span>
          <el-button type="primary" @click="submit">回复</el-button>
        </div>
      </template>
      <div>
        <el-input :autosize="{ minRows: 2, maxRows: 4 }" type="textarea"  v-model="data.addForm.reply"  autocomplete="off" />
      </div>
    </el-card>
  </el-dialog>
</template>
<script setup>
import { ref,reactive,onMounted } from 'vue'
import request from '../../request/request'
import { ElMessageBox, ElMessage } from "element-plus"
const formRef = ref(null);
const formLabelWidth = '100px'
const data = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0,
  tableData : [],
  idList: [],
dialogTableVisible: false,
isEdit: false,
searchTheme:'',
addForm: {
    theme: '',
    content: '',
    sendName: '',
    reply:''
  },
  maxheight :window.innerHeight - 280
})
  onMounted(
    window.onresize = () => {
      return (() => {
          data.maxheight = window.innerHeight - 280
      })()
    }
  )
        // 重置表单
function reset() {
  data.addForm = {
    name: '',
    phone: '',
    birthday: '',
    gender: '',
    idCard: '',
    whenCome: '',
    witchFamily: '',
    whyCome: '',
  }
    formRef.value.resetFields()
}
    // 关闭表单前的回调
const beforeClose = () => {
  reset()
  formRef.value.resetFields();
  data.dialogTableVisible = false;
}
  // 日期格式
const formatDates = function (cellValue) {
   if(cellValue.replyTime !== null) {
   return cellValue.replyTime.split('T')[0]
  }else if( cellValue.sendTime !== null){
    return cellValue.sendTime.split('T')[0]
  }
   return
  }
// 获取全部的流动人口信息
const load = () => {
  request.get('/api/message/findPage', {
    params: {
      pageNum: data.currentPage,
      pageSize: data.pageSize,
      theme: data.searchTheme
    }
  }).then((res) => {
    if (res.status = '200') {
      data.tableData = res.data.data
      data.total = res.data.total
    }

  })
}
// 每页个数的改变
const handleSizeChange = (val) => {
  data.pageSize = val
  load()
}
// 跳转到哪里去
const handleCurrentChange = (val) => {
  data.currentPage = val
  load()
}
// 编辑流动人口
const handleEdit = (row) => {
  data.dialogTableVisible = true
  data.addForm = row
}
// 提交表单
const submit = () => {
  data.addForm.msgStatus = '已处理'
      request.post("/api/message/addOrUpdate",data.addForm).then((res)=>{
        if(res.status == 200){
          ElMessage({
            showClose: true,
            message: "回复成功！",
            type: "success",
            })
            load()
            beforeClose()
          }else{
              ElMessage({
              showClose: true,
              message: "回复失败,请联系管理员！",
              type: "error",
          })
        }
      })
  }
const selectionChange = (selection) => {
  data.idList = selection;
};
// 批量删除信息
const delBatch = () => {
  if(data.idList.length>0) {
    let ids = data.idList.map((v) => v.id);
  ElMessageBox.confirm("你确定删除所选记录？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  }).then(() => {
    request.post("/api/message/del/batch", ids).then((res) => {
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
// 删除信息
const handleDelete = ( row ) => {
  ElMessageBox.confirm("你确定删除所选记录？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  }).then(() => {
    request.delete("/api/message/delete/" + row.id).then((res) => {
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
onMounted(
  () => {
    load()
  }
)
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
  // .el-form {
  //   display: grid;
  //   grid-template-columns: 1fr 1fr;
  // }
  .el-dialog__header{
    background-color: #409EFF;
    margin: 0;
    color: #fff;
    font-size: 17px;
  }
  .card-header{
    display: flex;
    justify-content: space-between;
  }
}
</style>