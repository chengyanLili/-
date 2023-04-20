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
    <el-table-column prop="sendTime" sortable label="发信时间" width="120" :formatter="formatDates2"/>
    <el-table-column prop="handleName" label="处理人姓名" width="120" />
    <el-table-column prop="msgStatus" sortable label="信息状态" width="120" >
      <template #default="scope">
        <el-tag v-if="scope.row.msgStatus == '待处理'" type="info" size="small">待处理</el-tag>
        <el-tag v-if="scope.row.msgStatus == '已处理'" type="success" size="small">已处理</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="replyTime" sortable label="处理时间" width="120" :formatter="formatDates1"/>
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
import { ref,reactive,onBeforeMount,onMounted } from 'vue'
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
    reply:'',
  },
  maxheight :window.innerHeight - 280
})
onBeforeMount(() => {
  const res = JSON.parse(localStorage.getItem('users'))
  data.addForm.handleName = res[0].handleName
})
  onMounted(
    window.onresize = () => {
      return (() => {
          data.maxheight = window.innerHeight - 280
      })()
    }
  )

    // 关闭表单前的回调
const beforeClose = () => {
  formRef.value.resetFields();
  data.dialogTableVisible = false;
}
  // 日期格式
const formatDates1 = function (cellValue) {
   if(cellValue.replyTime !== null) {
   return cellValue.replyTime.split('T')[0]
  }
   return
  }
  const formatDates2 = function (cellValue) {
   if(cellValue.sendTime !== null) {
   return cellValue.sendTime.split('T')[0]
  }
   return
  }
// 获取全部信件
const load = () => {
  request.get('/api/message/findPage', {
    params: {
      pageNum: data.currentPage,
      pageSize: data.pageSize,
      theme: data.searchTheme,
      sendName: ''
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
// 编辑
const handleEdit = (row) => {
  data.dialogTableVisible = true
  data.addForm = row
}
 /**
     * 查询当天日期
     */
    function getNowDate() {
      const timeOne = new Date()
      const year = timeOne.getFullYear()
      let month = timeOne.getMonth() + 1
      let day = timeOne.getDate()
      month = month < 10 ? '0' + month : month
      day = day < 10 ? '0' + day : day
      const NOW_MONTHS_AGO = `${year}-${month}-${day}`
      return NOW_MONTHS_AGO
    }
// 提交表单
const submit = () => {
  data.addForm.msgStatus = '已处理'
  data.addForm.replyTime = getNowDate()
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