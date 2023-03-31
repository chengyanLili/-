<template>
  <div class="title">人口档案管理</div>
  <div class="operation">
    <div class="search">
      <el-input v-model="allData.searchForm.name" style="width:150px;margin-right: 10px;" placeholder="请输入姓名" />
      <el-input v-model="allData.searchForm.phone" style="width:150px;margin-right: 10px;" placeholder="请输入电话" />
      <el-input v-model="allData.searchForm.idcard" style="width:180px;margin-right: 10px;" placeholder="请输入身份证号" />
      <el-button @click="load" type="primary"><el-icon style="font-size: 18px;margin-right: 6px">
          <Search />
        </el-icon>搜索</el-button>
      <el-button type="primary"><el-icon style="font-size: 18px;margin-right: 6px">
          <UploadFilled />
        </el-icon>批量添加</el-button>
      <el-button type="primary"><el-icon style="font-size: 18px;margin-right: 6px">
          <Download />
        </el-icon>导出Excel</el-button>
    </div>
    <div class="add">
      <el-button @click="add" type="primary">添加村民</el-button>
      <el-button type="danger">删除选中行数据</el-button>
    </div>
  </div>
  <el-table :data="allData.tableData" :height="allData.maxheight" :border="true">
    <el-table-column type="selection" width="55" />
    <el-table-column prop="name" label="姓名" width="120" />
    <el-table-column prop="gender" label="性别" width="60" />
    <el-table-column prop="phone" label="电话号码" width="120" />
    <el-table-column prop="age" label="年龄" width="60" />
    <el-table-column prop="nation" label="民族" width="60" />
    <el-table-column prop="idCard" label="身份证号码" width="168" />
    <el-table-column prop="householder" label="是否为户主" width="120" />
    <el-table-column prop="post" label="职务" width="120" />
    <el-table-column prop="policy" label="政治面貌" width="120" />
    <el-table-column prop="education" label="学历" width="120" />
    <el-table-column prop="group" label="所在小组" width="100" />
    <el-table-column fixed="right" label="操作" width="150">
      <template #default="scope">
        <el-button type="primary" size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  <el-dialog width="60%" v-model="allData.dialogTableVisible" :title="allData.dialogTitle[allData.n]">
    <el-form :model="allData.addForm">
      <el-form-item label="姓名" :label-width="formLabelWidth">
        <el-input v-model="allData.addForm.name" placeholder="请输入姓名" autocomplete="off" />
      </el-form-item>
      <el-form-item label="性别" :label-width="formLabelWidth">
        <el-radio-group v-model="allData.addForm.gender">
          <el-radio :label="'男'">男</el-radio>
          <el-radio :label="'女'">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="电话号码" :label-width="formLabelWidth">
        <el-input v-model="allData.addForm.phone" placeholder="请输入电话号码" autocomplete="off" />
      </el-form-item>
      <el-form-item label="年龄" :label-width="formLabelWidth">
        <el-input v-model="allData.addForm.age" placeholder="请输入年龄" autocomplete="off" />
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="民族">
        <el-select v-model="allData.addForm.nation" filterable placeholder="直接选择或搜索选择">
          <el-option v-for="item in config.NATIONLIST" :key="item.id" :index="item.index" :label="item.info"
            :value="item.info"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="身份证号码" :label-width="formLabelWidth">
        <el-input v-model="allData.addForm.idCard" placeholder="请输入身份证号" autocomplete="off" />
      </el-form-item>
      <el-form-item label="是否为户主" :label-width="formLabelWidth">
        <el-select v-model="allData.addForm.householder" clearable placeholder="请选择是否为户主">
          <el-option label="是" value="1"></el-option>
          <el-option label="否" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="职务" :label-width="formLabelWidth">
        <el-input v-model="allData.addForm.post" placeholder="请输入职务" autocomplete="off" />
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="政治面貌">
        <el-select v-model="allData.addForm.policy" filterable placeholder="直接选择或搜索选择">
          <el-option v-for="item in config.POLICYLIST" :key="item.id" :index="item.id" :label="item.label"
            :value="item.label"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="学历">
        <el-select v-model="allData.addForm.education" filterable placeholder="直接选择或搜索选择">
          <el-option v-for="item in config.EDULIST" :key="item.id" :index="item.index" :label="item.label"
            :value="item.label"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所在小组" :label-width="formLabelWidth">
        <el-input v-model="allData.addForm.inGroup" placeholder="请输入小组" autocomplete="off" />
      </el-form-item>

    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" @click="submit">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>

  <br>
  <el-pagination v-model:current-page="allData.currentPage" v-model:page-size="allData.pageSize"
    :page-sizes="[5, 10, 20, 25]" layout="total, sizes, prev, pager, next, jumper" :total="allData.total"
    @size-change="handleSizeChange" @current-change="handleCurrentChange" />
</template>

<script setup>
import { onMounted, reactive } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus'
import request from '../../request/request.js'
import { config } from '../../constants/index.js'
const formLabelWidth = '140px'
const allData = reactive({
  tableData: [{
    date: '2016-05-02',
    name: '王小虎',
    address: '上海市普陀区金沙江路 1518 弄'
  }, {
    date: '2016-05-04',
    name: '王小虎',
    address: '上海市普陀区金沙江路 1517 弄'
  }, {
    date: '2016-05-01',
    name: '王小虎',
    address: '上海市普陀区金沙江路 1519 弄'
  }, {
    date: '2016-05-03',
    name: '王小虎',
    address: '上海市普陀区金沙江路 1516 弄'
  }],
  maxheight: window.innerHeight - 280,
  currentPage: 1,
  pageSize: 10,
  total: 0,
  dialogTableVisible: false,
  dialogTitle: ['添加村民', '编辑'],
  n: 0,
  searchForm: {
    name: '',
    phone: '',
    idCard: ''
  },
  addForm: {
    name: '',
    phone: '',
    age: '',
    gender: '',
    idCard: '',
    post: '',
    householder: '',
    policy: '',
    education: '',
    inGroup: '',
    nation: ''
  },

})

// 获取全部的人口信息
const load = () => {
  request.get('http://127.0.0.1:9090/population/findPage', {
    params: {
      pageNum: allData.currentPage,
      pageSize: allData.pageSize,
      name: allData.searchForm.name,
      phone: allData.searchForm.phone,
      idCard: allData.searchForm.idCard
    }
  }).then((res) => {
    if (res.status = '200') {
      allData.tableData = res.data.data
      allData.total = res.data.total
    }

  })
}
// 添加
const add = () => {
  allData.n = 0
  allData.dialogTableVisible = true
}
const cancel = () => {
  allData.dialogTableVisible = false
}

// 弹窗提交的回调
const submit = () => {
  if (allData.n == 0) {
    // 确认添加
    allData.dialogTableVisible = false
    request.post('api/population/add', allData.addForm).then(res => {
      if (res.status == 200) {
        ElMessage({
          showClose: true,
          message: '添加成功！',
          type: 'success',
        })
        load()
      } else {
        ElMessage({
          showClose: true,
          message: '添加失败！',
          type: 'error',
        })
      }
    })
  } else {
    // 确认编辑
    allData.dialogTableVisible = false
    request.post('api/population/add', allData.addForm).then(res => {
      if (res.status == 200) {
        ElMessage({
          showClose: true,
          message: '修改成功！',
          type: 'success',
        })
        load()
      } else {
        ElMessage({
          showClose: true,
          message: '修改失败！',
          type: 'error',
        })
      }
    })
  }


}

// 编辑村民信息
const handleEdit = ((index, row) => {
  allData.n = 1
  allData.dialogTableVisible = true
  console.log(index, row)
  allData.addForm = row
})

// 删除村民
const handleDelete = ((id) => {
  console.log(id);
  ElMessageBox.confirm(
    '你确定删除所选记录？',
    'Warning',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(() => {
      request.post('http://127.0.0.1:9090/population/delete?id='+id).then(res => {
          if (res.status == 200) {
            ElMessage({
              showClose: true,
              message: '删除成功！',
              type: 'success',
            })
            load()
          } else {
            ElMessage({
              showClose: true,
              message: '删除失败！',
              type: 'error',
            })
          }
        })
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '删除取消',
      })
    })


})

onMounted(
  window.onresize = () => {
    return (() => {
      allData.maxheight = window.innerHeight - 280
    })()
  }
)
load();
// 每页个数的改变
const handleSizeChange = (val) => {
  allData.pageSize = val
  load()
}
// 跳转到哪里去
const handleCurrentChange = (val) => {
  allData.currentPage = val
  load()
}
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

}
</style>