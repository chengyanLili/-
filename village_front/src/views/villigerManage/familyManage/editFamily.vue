<template>
    <div class="back" >
            <el-icon style="cursor: pointer" @click="back">
                    <ArrowLeftBold />
            </el-icon>
            <span  style="cursor: pointer" @click="back">返回</span>
    </div>
    <div class="grid">
            <div class="grid_left">
                <div class="title">
                                <span>添加成员</span><el-icon>
                                        <Plus />
                                </el-icon>
                        </div>
            <div class="member">
                      <el-form
                            label-width="100px"
                            :model="data.memberForm"
                            style="max-width: 460px"
                            ref="formRef"
                            :rules="data.formRules"
                            >
                            <el-form-item label="成员" prop="relation">
                                    <el-select style="width: 194px;" v-model="data.memberForm.relation" placeholder="与户主关系">
                                            <el-option v-for="item in config.RELATION" :key="item.value" :label="item.label" :value="item.value" />
                                    </el-select>
                            </el-form-item>
                            <el-form-item label="姓名" prop="memberName">
                                    <el-input v-model="data.memberForm.memberName" placeholder="请输入姓名" />
                            </el-form-item>
                            <el-form-item label="身份证号" prop="memberIdCard">
                                    <el-input v-model="data.memberForm.memberIdCard" placeholder="请输入身份证号" />
                            </el-form-item>
                            <el-form-item label="出生日期" prop="birthday">
                                <el-date-picker
                                v-model="data.memberForm.birthday"
                                type="date"
                                placeholder="请选择出生日期"
                                format="YYYY-MM-DD"
                                value-format="YYYY-MM-DD"
                            />
                            </el-form-item>
                    </el-form>
            </div>
            <div class="btn">
                <el-button type="primary" @click="reset">重置</el-button>
                <el-button type="primary" @click="addMenmber">添加</el-button>
            </div>
            </div>
            <div class="grid_right">
            <div class="table_title">顾红户口关系表</div>
            <el-table :data="data.tableData" border >
                <el-table-column prop="memberName" label="姓名" width="100px"/>
                <el-table-column prop="relation" label="与户主关系" width="100px"/>
                <el-table-column prop="householdIdCard" show-overflow-tooltip label="身份证号" width="162px"/>
                <el-table-column prop="birthday" label="出生日期" width="80px" :formatter="formatDates"/>
                <el-table-column fixed="right" label="操作" width="150">
                    <template #default="scope">
                            <el-button
                            type="primary"
                            size="small"
                            @click="handleEdit(scope.$index, scope.row)"
                            >编辑</el-button
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
            </div>
    </div>
</template>

<script setup>
import { ref,reactive,onMounted } from 'vue'
import router from '../../../router/index'
import { config } from '@/constants/index'
import request from '../../../request/request';
import { ElMessageBox, ElMessage } from "element-plus";
const formRef = ref(null);
const data = reactive({
    tableData : [],
    memberForm: {
        memberName:'',
        relation:'',
        birthday:'',
        memberIdCard:''
    }, 
    accountNo:'',
    formRules: {
        relation: [{ required: true, message: "请选择与户主关系", trigger: "blur" }],
        memberName: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        memberIdCard: [{ required: true, message: "请输入身份证号", trigger: "blur" }],
        birthday: [{ required: true, message: "请输入出生日期", trigger: "blur" }]
  }
})
function back() {
    router.back()
}
function reset() {
    formRef.value.resetFields()
}
const formatDates = function () {
        const array = Array.from(arguments).map(item => {
            const string = new Date(item).toLocaleString()
            console.log('#',string);
            return string.slice(0,9)
        })
        return array
    }

function addMenmber() {
    formRef.value.validate(valid => {
        if(valid){
            data.memberForm.accountNo = data.accountNo
            request.post("/api/family/addOrUpdate",data.memberForm).then(res => {
                if(res){
                        ElMessage({
                        showClose: true,
                        message: "添加成功！",
                        type: "success",
                        });
                    request.get("/api/family/familyDetail?accountNo="+router.currentRoute.value.query.accountNo).then(res => {
                    data.tableData = res.data
                })
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
onMounted (
    ()=>{
        data.accountNo = router.currentRoute.value.query.accountNo
        request.get("/api/family/familyDetail?accountNo="+router.currentRoute.value.query.accountNo).then(res => {
            data.tableData = res.data
        })
    }
)

</script>

<style lang="less" scoped>
.back {
    display: flex;
    align-items: center;
    span {
            display: block;
    }    
}
.grid{
    width:100%;
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 20px;
    .grid_left{
.title {
    display: flex;
    align-items: center;
    color: #409EFF;
    margin: 10px 0;
    .el-icon {
            margin-left: 2px;
    }
}

.member {
    display: flex;
    align-items: center;
    // margin: 10px 40px;

    .el-select {
            width: 150px;
    }

    .right {
            display: flex;
            margin: 0 30px;

            .el-input {
                    width: 150px;
                    margin: 0 10px;
            }
    }
}

.btn {
   float: right;
   margin-right: 80px;
}
    }
    .grid_right{
            .table_title{
                    font-size: 18px;
                    margin-bottom: 10px;
            }
    }

}


</style>