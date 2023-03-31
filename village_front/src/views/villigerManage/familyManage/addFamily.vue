<template>
        <div class="back" >
                <el-icon style="cursor: pointer" @click="back">
                        <ArrowLeftBold />
                </el-icon>
                <span  style="cursor: pointer" @click="back">返回</span>
        </div>
        <div class="grid">
                <div class="grid_left">
                        <div class="addFamily">
                                <div class="title" style="margin-top: 10px;">
                                <span>添加户主</span><el-icon>
                                        <Plus />
                                </el-icon>
                        </div>
                        <div class="household">
                                <el-form
                                label-width="100px"
                                :model="data.householdForm"
                                style="max-width: 294px"
                                >
                                <el-form-item label="户主">
                                        <el-input v-model="data.houseName" placeholder="请输入户主姓名" />
                                </el-form-item>
                                <el-form-item label="户号">
                                        <el-input v-model="data.houseId" placeholder="请输入户号" />
                                </el-form-item>
                        </el-form>
                        
                        </div>
                        <div class="title">
                                <span>添加成员</span><el-icon>
                                        <Plus />
                                </el-icon>
                        </div>
                </div>
                <div class="member">
                          <el-form
                                label-width="100px"
                                :model="data.memberForm"
                                style="max-width: 460px"
                                >
                                <el-form-item label="成员">
                                        <el-select style="width: 194px;" v-model="data.memberForm.relation" placeholder="与户主关系">
                                                <el-option v-for="item in data.options" :key="item.value" :label="item.label" :value="item.value" />
                                        </el-select>
                                </el-form-item>
                                <el-form-item label="姓名">
                                        <el-input v-model="data.memberForm.name" placeholder="请输入姓名" />
                                </el-form-item>
                                <el-form-item label="身份证号">
                                        <el-input v-model="data.memberForm.idCard" placeholder="请输入身份证号" />
                                </el-form-item>
                                <el-form-item label="年龄">
                                        <el-input v-model="data.memberForm.age" placeholder="请输入年龄" />
                                </el-form-item>
                        </el-form>
                </div>
                <div class="btn">
                        <el-button type="primary">添加</el-button>
                </div>
                </div>
                <div class="grid_right">
                <div class="table_title">顾红户口关系表</div>
                <el-table :data="data.tableData" border >
                    <el-table-column prop="name" label="姓名" />
                    <el-table-column prop="relation" label="与户主关系" width="100px"/>
                    <el-table-column prop="idCard" show-overflow-tooltip label="身份证号" width="100px"/>
                    <el-table-column prop="age" label="年龄" />
                </el-table>
                </div>
        </div>
</template>

<script setup>
import { ref,reactive } from 'vue'
import router from '../../../router/index'
const data = reactive({
        options : [
        {
                value: 'Option1',
                label: '妻子',
        },
        {
                value: 'Option2',
                label: '儿子',
        },
        {
                value: 'Option3',
                label: '女儿',
        },
        {
                value: 'Option4',
                label: '父亲',
        },
        {
                value: 'Option5',
                label: '母亲',
        },
        {
                value: 'Option6',
                label: '爷爷',
        },
        {
                value: 'Option7',
                label: '奶奶',
        },
],
        tableData : [
        {
        name: '顾红',
        relation: '户主',
        idCard: '533522222226612122',
        age:12
        },
        ],
        memberForm: {
                name:'',
                houseName:'',
                relation:'',
                houseId:'',
                age:'',
                idCard:'',
        },
        householdForm:{
                houseName:'',
                household:''
        }
        
        

})
function back() {
        router.back()
}

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