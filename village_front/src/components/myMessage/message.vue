<template>
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
                                                                <div class="info-time">{{ msg.time }}</div>
                                                        <div class="info-views">
                                                                <el-icon><View /></el-icon>
                                                                <span>{{ msg.viewsNum }}</span>
                                                        </div>
                                                        
                                                        <div class="info-right">                                                  
                                                                <el-icon size="20"><ChatLineRound /></el-icon>
                                                                <span>{{ msg.commentNum }}</span>                      
                                                        </div>
                                                        <div class="info-state">
                                                                <el-button type="success" size="small" >{{ msg.msgState }}</el-button>
                                                        </div>
                                                </div>
                                                
                                        </div>
                                </div>
                </div>
                
        </div>
</template>

<script setup>
import router from '@/router/index'
import { reactive } from 'vue';
import bus from '@/utils/mitt.js'

const toMsgDetail = (msg) => {
        router.push('/villager/messageDetail')
        bus.emit('msg',msg)
}
const data = reactive({
messageList: [
    { title: '2023年村长、副村长、村支书选举报名长、村支书选举报名',time: '[2023-01-12——2023-03-10]',viewsNum: 13, commentNum: 69, msgState:'进行中'},
    { title: '2023年村长、副村长、村支书选举报名长、村支书选举报名',time: '[2023-01-12——2023-03-10]',viewsNum: 66, commentNum: 99, msgState:'已完成'},
    { title: '2023年村长、副村长、村支书选举报名长、村支书选举报名',time: '[2023-01-12——2023-03-10]',viewsNum: 68, commentNum: 72, msgState:'进行中'},
    { title: '2023年村长、副村长、村支书选举报名长、村支书选举报名',time: '[2023-01-12——2023-03-10]',viewsNum: 11, commentNum: 14, msgState:'已完成'},
    { title: '2023年村长、副村长、村支书选举报名长、村支书选举报名',time: '[2023-01-12——2023-03-10]',viewsNum: 32, commentNum: 45, msgState:'进行中'}
  ]
})
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

        
</style>