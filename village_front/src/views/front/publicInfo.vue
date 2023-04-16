<template>
            <div class="content">
                <div class="icons">
                    <el-row>
            <el-col :sm="6" :lg="6" @click="policy">
            <el-result
                icon="info"
                title="上级政策"
            >
            </el-result>
            </el-col>
            
            <el-col :sm="6" :lg="6" @click="carrayOut">
            <el-result
                icon="warning"
                title="决策执行"
            >
            </el-result>
            </el-col>
            <el-col :sm="6" :lg="6" @click="finished">
            <el-result
                icon="success"
                title="落实完成"
            >
            </el-result>
            </el-col>
        </el-row>
        </div>
        <div class="lists">
            <div v-for="item in data.items" class="items">
                <el-icon size="35"><HelpFilled /></el-icon>
                <div @click="details(item)">{{ item.title }}</div>
            </div>
        </div>

    <el-dialog title="详情" v-model="data.viewDialogVis" width="80%" >
      <el-card style="overflow: auto;">
        <div>
          <v-md-editor
              class="md"
              v-model="data.content" 
              mode="preview"
          />
        </div>
      </el-card>
    </el-dialog>
        
    </div>

</template>

<script setup>
import { reactive,ref ,onMounted } from 'vue';
import request from '../../request/request'
const data = reactive({
    policyList:[],
    carrayOut:[],
    finished:[],
    items:[],
    content:'',
    viewDialogVis:false
  })
 onMounted (()=>{
    load()
    data.items = data.policyList
 }) 
 const load = () => {
  request.get("/api/task/findPage",{
    params: {
      pageNum: 1,
      pageSize: 100,
      title: '',
      noticeType: ''
    }
  }).then(res=>{
    const lists = res.data.data
    lists.map(v=>{
      switch (v.noticeType){
        case '上级政策':
          data.policyList.push(v)
          break
        case '决策执行':
          data.carrayOut.push(v)
          break
        case '落实完成':
          data.finished.push(v)
          break
      }
    })
  })
}
const policy = ()=>{
    console.log(1, data.policyList);
    data.items = data.policyList
}
const carrayOut = ()=>{
    console.log(2);
    data.items = data.carrayOut
}
const finished = ()=>{
    console.log(3);
    data.items = data.finished
}
const details = (item)=>{
    data.content = item.content
    data.viewDialogVis = true
}

</script>

<style lang="less" scoped>
.content{
    background-color: #fff !important;
    height: calc(100vh - 140px);
    overflow: auto;
    .el-row{
        justify-content: center;
        align-items: center;
    }
    .items{
        display: flex;
        align-items: center;
        margin: 4px 40px;
        height: 60px;
        border: solid 1px rgb(225, 231, 233);
        border-radius: 4px;
        .el-icon{
            color: #b88230;
            margin-right: 10px;
        }
    }
    
}

</style>