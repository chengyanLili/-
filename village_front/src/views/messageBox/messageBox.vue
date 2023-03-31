<template>
    <div class="title">收件箱</div>
    <div class="search">
      <el-input v-model="data.searchName" style="width:150px;margin-right: 10px;" placeholder="请输入标题" />
      <el-button @click="load" type="primary"><el-icon style="font-size: 18px;margin-right: 6px">
          <Search />
        </el-icon>搜索</el-button>
    </div>
<el-table
    :data="data.tableData"
    :border="true"
    :height="data.maxheight"
  > 
    <el-table-column type="selection" width="55" />
    <el-table-column prop="msgtitle" label="标题" width="120" show-overflow-tooltip />
    <el-table-column prop="sendName" label="发信人姓名" width="120" show-overflow-tooltip />
    <el-table-column prop="sendPhone" sortable label="发布时间" width="120" />
    <el-table-column prop="sendTime" label="负责人姓名" width="120" />
    <el-table-column prop="createTime" label="负责人电话" width="120" />
    <el-table-column prop="handleName" sortable label="处理人姓名" width="120" />
    <el-table-column prop="handlePhone" label="处理人电话号码" width="130" />
    <el-table-column prop="msgStatus" label="信息状态" width="120" >
      <template #default="scope">
        <el-tag v-if="scope.row.msgStatus == 0" type="info" size="small">待处理</el-tag>
        <el-tag v-if="scope.row.msgStatus == 1" type="success" size="small">已处理</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="note" label="备注" width="100" />
    <el-table-column fixed="right" label="操作" width="150">
      <template #default="scope">
        <el-button type="primary" size="small" @click="detail(scope.row)">处理</el-button>
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
    {msgtitle:'哈哈哈哈哈哈哈',sendName:'内容1',sendTime:'2022-01-09',createTime:'1',sendPhone:'1765432231',handleName:'笑笑',handlePhone:1,msgStatus:1},
    {msgtitle:'嘎嘎嘎嘎嘎嘎嘎',sendName:'内容2',sendTime:'2021-01-09',createTime:'6',sendPhone:'1765432231',handleName:'笑笑',handlePhone:2,msgStatus:0},
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