<template>
    <div class="title">流动人口管理</div>
    <div class="search">
      <el-input v-model="data.searchForm.name" style="width:150px;margin-right: 10px;" placeholder="请输入姓名" />
      <el-input v-model="data.searchForm.phone" style="width:150px;margin-right: 10px;" placeholder="请输入电话" />
      <el-input v-model="data.searchForm.idcard" style="width:180px;margin-right: 10px;" placeholder="请输入身份证号" />
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
    <el-button type="primary" @click="handleAdd">添加流动人口</el-button>
    <el-button type="danger">删除选中行数据</el-button>
  </div>

      <el-table
    :data="data.tableData"
    :height="data.maxheight"
    :border="true"
  > 
    <el-table-column type="selection" width="55" />
    <el-table-column prop="name" label="姓名" width="120" />
    <el-table-column prop="gender" label="性别" width="60" />
    <el-table-column prop="phone" label="电话号码" width="120" />
    <el-table-column prop="age" label="年龄" width="60" />
    <el-table-column prop="idCard" label="身份证号码" width="168" />
    <el-table-column prop="whenCome" label="何时来此" width="120" />
    <el-table-column prop="whereCome" label="暂住何户" width="120" />
    <el-table-column prop="whyCome" label="因何来此" width="120" />
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
  <!-- 添加/修改流动人口 -->
  <el-dialog width="60%" v-model="data.dialogTableVisible" :title="data.isEdit ? '修改流动人口' : '添加流动人口'">
    <el-form :model="data.addForm">
      <el-form-item label="姓名" :label-width="formLabelWidth">
        <el-input v-model="data.addForm.name" placeholder="请输入姓名" autocomplete="off" />
      </el-form-item>
      <el-form-item label="性别" :label-width="formLabelWidth">
        <el-radio-group v-model="data.addForm.gender">
          <el-radio :label="'男'">男</el-radio>
          <el-radio :label="'女'">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="电话号码" :label-width="formLabelWidth">
        <el-input v-model="data.addForm.phone" placeholder="请输入电话号码" autocomplete="off" />
      </el-form-item>
      <el-form-item label="年龄" :label-width="formLabelWidth">
        <el-input v-model="data.addForm.age" placeholder="请输入年龄" autocomplete="off" />
      </el-form-item>
      <el-form-item label="身份证号码" :label-width="formLabelWidth">
        <el-input v-model="data.addForm.idCard" placeholder="请输入身份证号" autocomplete="off" />
      </el-form-item>
      <el-form-item label="何时来此" :label-width="formLabelWidth">
        <el-input v-model="data.addForm.whenCome" placeholder="请输入来村的时间" autocomplete="off" />
      </el-form-item>
      <el-form-item label="暂住何户" :label-width="formLabelWidth">
        <el-input v-model="data.addForm.whereCome" placeholder="请输入现住何户" autocomplete="off" />
      </el-form-item>
      <el-form-item label="因何来此" :label-width="formLabelWidth">
        <el-input v-model="data.addForm.whyCome" placeholder="请输入来村的原因" autocomplete="off" />
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
    <el-pagination
      :current-page="1"
      :page-size="10"
      :page-sizes="[10, 20, 30, 40]"
      layout="total, sizes, prev, pager, next, jumper"
      :total="400"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
</template>
<script setup>
import { reactive,onMounted } from 'vue'
import request from '../../request/request';
const formLabelWidth = '140px'
const data = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0,
   tableData : [],
dialogTableVisible: false,
isEdit: false,
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
    whenCome: '',
    whereCome: '',
    whyCome: '',
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
  const handleSizeChange = (val) => {
  console.log(`${val} items per page`)
}
// 获取全部的流动人口信息
const load = () => {
  request.get('/api/floatPopulation/findPage', {
    params: {
      pageNum: data.currentPage,
      pageSize: data.pageSize,
      name: data.searchForm.name,
      phone: data.searchForm.phone,
      idCard: data.searchForm.idCard
    }
  }).then((res) => {
    if (res.status = '200') {
      data.tableData = res.data.data
      data.total = res.data.total
    }

  })
}
const handleCurrentChange = (val) => {
  console.log(`current page: ${val}`)
}
const handleAdd = () => {
  data.dialogTableVisible = true
}
const handleEdit = (row) => {
  data.isEdit = true
  data.dialogTableVisible = true
  data.addForm = row
  console.log('row',row);
}
const cancel = () => {
  data.dialogTableVisible = false
}
const submit = () => {
  data.dialogTableVisible = false
}
const handleDelete = (row) => {
  console.log(row.id);
}
onMounted(
  () => {
    load()
  }
)
</script>


<style lang="less">
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