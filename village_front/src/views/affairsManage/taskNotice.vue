<template>
    <div class="title">任务列表</div>
    <div class="search">
      <el-input v-model="data.searchName" style="width:150px;margin-right: 10px;" placeholder="请输入标题" />
      <el-button @click="load" type="primary"><el-icon style="font-size: 18px;margin-right: 6px">
          <Search />
        </el-icon>搜索</el-button>
    </div>
    <el-button style="margin-bottom: 10px;" type="primary" @click="handleAdd">发布新任务</el-button>
<el-table
    :data="data.tableData"
    :border="true"
    :height="data.maxheight"
  > 
    <el-table-column type="selection" width="55" />
    <el-table-column prop="taskTitle" label="标题" width="120" show-overflow-tooltip />
    <el-table-column prop="content" label="内容" width="120" show-overflow-tooltip />
    <el-table-column prop="publishTime" sortable label="发布时间" width="120" />
    <el-table-column prop="chargeName" label="负责人姓名" width="120" />
    <el-table-column prop="chargePhone" label="负责人电话" width="120" />
    <el-table-column label="任务状态" width="120" >
      <template #default="scope">
        <el-tag v-if="scope.row.infoState == 1" type="success" size="small">进行中</el-tag>
        <el-tag v-if="scope.row.infoState == 2" type="info" size="small">已完成</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="note" label="备注" width="100" />
    <el-table-column fixed="right" label="操作" width="200">
      <template #default="scope">
        <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
        <el-button type="primary" size="small" @click="detail(scope.row)">详情</el-button>
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
      :current-page="1"
      :page-size="10"
      :page-sizes="[10, 20, 30, 40]"
      layout="total, sizes, prev, pager, next, jumper"
      :total="400"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  <el-dialog v-model="data.dialogTableVisible" title='任务发布'>
    <el-form :model="data.addForm">
      <el-form-item label="标题" :label-width="formLabelWidth">
        <el-input v-model="data.noticeForm.taskTitle" placeholder="请输入标题" autocomplete="off" />
      </el-form-item>
      <el-form-item label="内容" :label-width="formLabelWidth">
        <el-input
          type="textarea"
          placeholder="请输入内容"
          v-model="data.noticeForm.content"
          maxlength="30"
          show-word-limit
        >
      </el-input>
      </el-form-item>
      <el-form-item label="附件" :label-width="formLabelWidth">
        <el-upload
          class="upload-demo"
          ref="upload"
          :auto-upload="false">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
      </el-upload>
      </el-form-item>
      <el-form-item>
        <div class="btn">
          <el-button @click="cancle">取消</el-button>
          <el-button type="primary" @click="onSubmit">立即发布</el-button>
        </div>    
  </el-form-item>
    </el-form>
  </el-dialog>
    
</template>

<script setup>
import { reactive } from 'vue';
const formLabelWidth = '140px'
const data = reactive({
  tableData:[
    {taskTitle:'哈哈哈哈哈哈哈',content:'内容1',publishTime:'2022-01-09',chargePhone:'1765432231',chargeName:'笑笑',infoState:1,note:'备注哈哈哈'},
    {taskTitle:'嘎嘎嘎嘎嘎嘎嘎',content:'内容2',publishTime:'2021-01-09',chargePhone:'1765432231',chargeName:'笑笑',infoState:2,note:'备注哈哈哈'},
  ],
noticeForm: {
    name: '',
    content:''
  },
  searchName:'',
  dialogTableVisible: false,
  maxheight :window.innerHeight - 280
})
const load = () => {

}
const handleAdd = ()=>{
  data.dialogTableVisible = true
}
const handleEdit = (row)=>{
  data.dialogTableVisible = true
  data.noticeForm = row
}
const cancle = () => {
  data.dialogTableVisible = false
}
const onSubmit = () =>{
  data.dialogTableVisible = false
}
const detail = () => {
  
}
const handleSizeChange = () => {

}
const handleCurrentChange = () => {

}
</script>

<style>
  .title{
   font-size: 18px;
   margin-bottom: 10px;
  }
  .search{
    margin: 10px 0;
    display: flex;
  }
  .btn{
    margin-left: 200px;
  }
</style>