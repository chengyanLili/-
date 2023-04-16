<template>
    <div class="title">任务列表</div>
    <div class="search">
      <el-input v-model="data.searchForm.searchName" clearable style="width:150px;margin-right: 10px;" placeholder="请输入标题" />
      <el-select v-model="data.searchForm.noticeType"  clearable class="m-2" style="margin-right: 10px;"  placeholder="请选择通知类型">
    <el-option
      v-for="item in data.types"
      :key="item.value"
      :label="item.label"
      :value="item.value"
    />
  </el-select>
      <el-button @click="load" type="primary"><el-icon style="font-size: 18px;margin-right: 6px">
          <Search />
        </el-icon>搜索</el-button>
    </div>
    <el-button style="margin-bottom: 10px;" type="primary" @click="handleAdd">发布</el-button>
<el-table
    :data="data.tableData"
    :border="true"
    :height="data.maxheight"
  > 
    <el-table-column type="selection" width="55" />
    <el-table-column prop="title" label="标题" width="120" show-overflow-tooltip />
    <el-table-column prop="noticeType" label="通知类型" width="120" show-overflow-tooltip />
    <el-table-column prop="createTime" sortable label="发布时间" width="120" />
    <el-table-column prop="content" label="文章内容" width="120">
        <template #default="scope">
          <el-button @click="view(scope.row.content)" type="primary">查看内容</el-button>
        </template>
    </el-table-column>
    <el-table-column prop="publishName" label="发布人" width="120" />
    <el-table-column label="任务状态" width="120" >
      <template #default="scope">
        <el-tag v-if="scope.row.progress == '进行中'" type="success">进行中</el-tag>
        <el-tag v-if="scope.row.progress == '已结束'" type="info">已完成</el-tag>
      </template>
    </el-table-column>
    <el-table-column fixed="right" label="操作" width="250">
      <template #default="scope">
        <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
        <el-button
          size="small"
          type="danger"
          @click="handleDelete(scope.row)"
          >删除</el-button>
          <el-button type="info" size="small" @click="handleFinish(scope.row)">结束任务</el-button>
        
      </template>
    </el-table-column>
  </el-table>
  <br>
    <el-pagination
      :current-page="data.currentPage"
      :page-size="data.pageSize"
      :page-sizes="[5,10,20, 30]"
      layout="total, sizes, prev, pager, next, jumper"
      :total="data.total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    <el-dialog title="发布" v-model="data.dialogFormVisible" width="80%" >
      <el-form label-width="80px" >
        <div class="input">
          <el-form-item label="主题">
            <el-input v-model="data.noticeForm.title" clearable autocomplete="off" placeholder="请输入主题"></el-input>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="data.noticeForm.publishName" clearable autocomplete="off" placeholder="请输入发布者姓名"></el-input>
          </el-form-item>
          <el-form-item label="姓名">
            <el-select v-model="data.noticeForm.noticeType" clearable class="m-2" style="margin-right: 10px;"  placeholder="请选择通知类型">
            <el-option
              v-for="item in data.types"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
        </el-select></el-form-item>
        </div>
        <el-form-item label="内容">
          <v-md-editor 
          ref="md" 
          v-model="data.noticeForm.content" 
          :disabled-menus="[]"
          @upload-image="imgAdd"
          height="280px"
          ></v-md-editor>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="详情" v-model="data.viewDialogVis" :before-close="beforeClose" width="70%" >
      <el-card style="overflow: auto;">
        <div>
          <v-md-editor
              class="md"
              v-model="data.content" 
              left-toolbar=""
              right-toolbar=""
              mode="preview"
          />
        </div>
      </el-card>
    </el-dialog>
    
</template>

<script setup>
import axios from 'axios';
import { ElMessageBox,ElMessage } from 'element-plus';
import request from '../../request/request'
import { reactive,onMounted } from 'vue';
const data = reactive({
  tableData:[],
  currentPage: 1,
  pageSize: 10,
  total: 0,
  content:'',
  noticeForm: {
    title: '',
    publishName:'',
    content:'',
    noticeType:''
  },
  types: [
    {label: '乡村要闻',value:'乡村要闻'},
    {label: '凤庆县要闻',value:'凤庆县要闻'},
    {label: '森林防火',value:'森林防火'},
    {label: '法律法规',value:'法律法规'},
    {label: '上级政策',value:'上级政策'},
    {label: '落实完成',value:'落实完成'},
    {label: '村务通知',value:'村务通知'}
  ],
  searchForm: {
    searchName: '',
    noticeType:''
  },

  dialogFormVisible: false,
  viewDialogVis:false,
  maxheight :window.innerHeight - 280
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
const load = () => {
  request.get("/api/task/findPage",{
    params: {
      pageNum: data.currentPage,
      pageSize: data.pageSize,
      title: data.searchForm.searchName,
      noticeType: data.searchForm.noticeType
    }
  }).then(res=>{
    console.log('$',res.data);
   data.tableData = res.data.data
   data.total = res.data.total
  })
}

const beforeClose = () => {
  data.content = ''
  data.noticeForm= {
    title: '',
    publishName:'',
    content:'',
    noticeType:''
  },
  data.viewDialogVis = false
  data.dialogFormVisible = false
}
const cancel = ()=>{
  data.dialogFormVisible = false
}
// 绑定@imgAdd event
function imgAdd(event,insertImage,files) {
      // 第一步.将图片上传到服务器.
      const formData = new FormData();
      formData.append('file', files[0]);
      axios({
        url: 'http://localhost:9090/file/upload',
        method: 'post',
        data: formData,
        headers: {'Content-Type': 'multipart/form-data'},
      }).then((res) => {
        console.log('res',res);
        // 第二步.将返回的url替换到文本原位置![...](./0) -> ![...](url)
        insertImage({
                    url:res.data
               })
      })
    }

  function view(content) {
    console.log(content)
      data.content = content
      data.viewDialogVis = true
    }

const handleAdd = ()=>{
  data.dialogFormVisible = true
}
const handleEdit = (row)=>{
  data.noticeForm = row
  data.dialogFormVisible = true
  
}

const save = () =>{
  data.noticeForm.progress = '进行中'
      axios({
        url: '/api/task/addOrUpdate',
        method: 'post',
        data: data.noticeForm
      }).then((res) => {
        if (res) {
          ElMessage.success("发布成功")
          beforeClose()
          load()
        } else {
          ElMessage.error("发布失败，请联系管理员！")
        }
      })
}
const handleFinish = (row)=>{
  data.noticeForm = row
  data.noticeForm.progress = "已结束"
  request.post('/api/task/addOrUpdate',data.noticeForm).then(res=>{
    if (res) {
          ElMessage.success("已结束任务")
          load()
        } else {
          ElMessage.error("操作失败，请联系管理员！")
        }
  })
}
// 删除任务
const handleDelete = ( row ) => {
  ElMessageBox.confirm("你确定删除所选记录？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  }).then(() => {
    request.delete("/api/task/delete/" + row.id).then((res) => {
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
};
</script>

<style scoped>
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
  .el-input{
    width: 200px;
    font-size: 14px;
  }
  .input{
    display: flex;
  }
  .dialog-footer{
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>