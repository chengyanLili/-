<template>
    <div v-if="urlName == 'messageList'" class="grid-item messages">
          <div class="title">
            <el-badge :value="data.total" class="item">
              <span>消息通知</span>
            </el-badge>
          </div>
          <div class="myMessages" v-for="(msg,index) in data.messageList" :key="index">
                        <div class="msgBody">
                                <div class="msg-icon">
                                        <el-icon size="40"><Files /></el-icon>
                                </div> 
                                <div class="msgContent">
                                        <div class="content-title">
                                                <el-badge is-dot class="item">
                                                        <span class="dianzhui">动态</span>
                                                </el-badge>
                                                
                                                <span @click="toMsgDetail(msg)" class="content-title_cont">{{ msg.title }}</span>                               
                                        </div>
                                        <div class="info">
                                                <div class="info-left">
                                                                <div class="info-time">{{ msg.createTime }}-{{ msg.finishTime }}</div>
                                                        <div class="info-views">
                                                                <el-icon><View /></el-icon>
                                                                <span>{{ msg.viewsNum }}</span>
                                                        </div>
                                                        
                                                        <div class="info-right">                                                  
                                                                <el-icon size="20"><ChatLineRound /></el-icon>
                                                                <span>{{ msg.commentNum }}</span>                      
                                                        </div>
                                                        <div class="info-state">
                                                                <el-button v-if="msg.progress=='进行中'" type="success" size="small" >{{ msg.progress }}</el-button>
                                                                <el-button v-else type="info" size="small" >{{ msg.progress }}</el-button>
                                                        </div>
                                                </div>
                                                
                                        </div>
                                </div>
                </div>
                
        </div>
        </div>
</template>

<script setup>
import router from '@/router/index'
import { reactive,onMounted } from 'vue';
import request from '../../request/request'
import bus from '@/utils/mitt.js'

const toMsgDetail = (msg) => {
        router.push('/villager/messageDetail')
        bus.emit('msg',msg)
}
onMounted(
  () => {
    load()
  }
)
// 获取全部的流动人口信息
const load = () => {
  request.get('/api/task/findPage', {
    params: {
      pageNum: 1,
      pageSize: 100,
      title:''
    }
  }).then((res) => {
    if (res.status = '200') {
      data.messageList = res.data.data
      data.total = res.data.total
    }

  })
}
const data = reactive({
messageList: [],
  total:''
})
// 获取当前路由
let urlName = router.currentRoute.value.name
console.log(urlName);
</script>

<style lang="less">
.myMessages{    
        .msgBody{
                width: 80%;
                overflow: hidden; /*溢出的部分隐藏*/
                white-space: nowrap; /*文本不换行*/
                
                height: 70px;
                margin: 20px auto;
                border: 1px solid rgb(190, 187, 187);
                border-radius: 6px;
                display: flex;
                align-items: center;
                .msg-icon{
                margin: 0 10px;
                color: #73a8dd;
                background-color: rgb(233, 234, 233);
                }
                .content-title{
                        
                        .dianzhui{
                                border: 1px solid rgb(190, 187, 187);
                                border-radius: 3px;
                                background-color: #88e875;
                                margin-right: 0px;
                                padding: 0 10px;
                        }
                        .content-title_cont{
                                margin-left: 10px;
                                cursor: pointer;
                                &:hover{
                                        color: #409EFF;
                                }
                        }
                }
                .info{
                        display: flex;
                        .info-left{
                                display: flex;
                                .info-time{
                                color: rgb(122, 124, 125);
                        }
                        .info-views{
                                .el-icon{
                                        margin: 0 4px 0 10px;
                                }
                                span{
                                        margin-right: 10px;
                                }
                                
                        }
                        }
                        
                        .info-right{
                                margin-right: 100px;
                                .el-icon{
                                        color: #409EFF;
                                        margin: 0 4px;
                                }
                        }
                }      
        }
      
}
.grid-item {
    background-color: #fff;
    border-radius: 6px;
    overflow: auto;
    .title {
      margin: 20px 0 0 10%;
    }
  }
</style>