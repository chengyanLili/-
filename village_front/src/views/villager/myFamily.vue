<template>
    <div class="main">
        <div class="content">
            <div class="table_title">顾红户口关系表</div>
        <div class="table">
            <el-table :data="data.tableData" border>
                <el-table-column prop="memberName" label="姓名" width="100px"/>
                <el-table-column prop="relation" label="与户主关系" width="100px"/>
                <el-table-column prop="householdIdCard" show-overflow-tooltip label="身份证号" width="162px"/>
                <el-table-column prop="birthday" label="出生日期" width="120px" :formatter="formatDates"/>
            </el-table>
        </div>
        </div>
    </div>
</template>

<script setup>
import { onBeforeMount,reactive,onMounted } from 'vue'
import request from '../../request/request'
const data = reactive({
    tableData : [],
    users:[]
})

const formatDates = function (cellValue) {
    if(cellValue.birthday){
        return cellValue.birthday.split('T')[0]
    }
    return cellValue.birthday
    }

// 获取登录信息
onBeforeMount(() => {
  data.users = JSON.parse(localStorage.getItem('users'))
})
onMounted (
    ()=>{
        request.get("/api/family/familyDetail?accountNo=" + data.users[0].username).then(res => {
            data.tableData = res.data
        })
    }
)
</script>

<style lang="less" scoped>
    .main{
        background-color: #fff;
        border-radius: 6px;
        .content{
           display: grid;
           justify-content: center;
            margin-top: 50px;
        }
       
    }
    .table_title{
        font-size: 18px;
        margin-bottom: 10px;
    }
</style>