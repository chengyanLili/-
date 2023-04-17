<template>
  <div class="grid-item messages">
    <div class="title" style="margin-bottom: 20px;">
      <el-badge :value="data.noReadTotal" class="item">
        <span>未读信息</span>
      </el-badge>
      <el-button type="primary" style="margin-left: 20px;" @click="sendMsg">发信件</el-button>
      <el-radio-group v-model="data.radio" style="margin: 0 20px;align-items: center;">
        <el-radio :label="1" @click="allMsg">全部</el-radio>
        <el-radio :label="3" @click="replys">已回复</el-radio>
        <el-radio :label="6" @click="noReply">未回复</el-radio>
      </el-radio-group>
    </div>
    <el-empty v-if="data.list.length==0" description="暂无数据" />
    <div class="myMessages" v-for="(msg, index) in data.list" :key="index">
      <div class="msgBody">
        <div class="msgContent">
          <div class="content-title">
            <div v-if="msg.reply && msg.isRead == null" class="dot"></div>
            <el-button v-if="msg.reply" type="success" size="small">已回复</el-button>
            <el-button v-else type="info" size="small">未回复</el-button>
             <span @click="toMsgDetail(msg)" class="content-title_cont">
              查看详情
            </span>
          </div>
          <div class="info">
            主题：{{ msg.theme }}
          </div>

        </div>
        <div class="msg-icon">
          <el-button type="danger" :icon="Delete" circle @click="handleDelete(msg)"/>
        </div>
      </div>
    </div>
  </div>
  <el-dialog v-model="dialogVisible" :before-close="bfClose">
    <template #header>
      <h3>发信件</h3>
    </template>
    <el-form ref="ruleFormRef" :model="data.sendForm" status-icon :rules="rules" label-width="120px">
      <el-form-item label="主题" prop="theme">
        <el-input v-model="data.sendForm.theme" clearable autocomplete="off" placeholder="请输入主题"></el-input>
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <el-input type="textarea" v-model="data.sendForm.content" clearable autocomplete="off"
          placeholder="请输入内容">
        </el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" style="display: flex;justify-content: center;">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="send">确 定</el-button>
    </span>
  </el-dialog>

  <el-dialog v-model="dialogFormVisible" :before-close="handleClose">
    <template #header>
      <h3>消息详情</h3>
    </template>
    <template #default>
      <div>
        <el-form label-width="80px">
          <el-form-item label="主题" prop="theme">
            <el-input disabled v-model="data.noticeForm.theme" clearable autocomplete="off"
              placeholder="请输入主题"></el-input>
          </el-form-item>
          <el-form-item label="信件内容" prop="content">
            <el-input disabled type="textarea" v-model="data.noticeForm.content"></el-input></el-form-item>
          <el-form-item label="回复">
            <el-input type="textarea" v-model="data.noticeForm.reply"></el-input>
          </el-form-item>
        </el-form>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { reactive, onBeforeMount, ref, onMounted } from "vue";
import request from "../../request/request";
import { ElMessage, ElMessageBox } from 'element-plus'
import { Delete } from '@element-plus/icons-vue'
const ruleFormRef = ref(null)
const sendMsg = () => {
  dialogVisible.value = true
}
const data = reactive({
  messageList: [],
  list:[],
  total: "",
  noticeForm: {
    title: '',
    publishName: '',
    content: '',
    isRead:''
  },
  noReadTotal:0,
  radio:1,
  sendForm: {
    theme: '',
    content: ''
  },
  users: [],
  reply:[],
  noReply:[]
});

const rules = reactive({
  theme: [
    { required: 'true', message: '主题不能为空', trigger: 'blur' }
  ],
  content: [
    { required: 'true', message: '信件内容不能为空', trigger: 'blur' }
  ]
});
const dialogFormVisible = ref(false);
const dialogVisible = ref(false)
// 单选框的回调
const allMsg = () => {
  data.list = data.messageList
}
const replys = () => {
  data.list = data.reply
}
const noReply = () => {
  data.list = data.noReply
}
const bfClose =()=>{
  dialogVisible.value = false
  load()
}
const toMsgDetail = (msg) => {
  data.noticeForm = msg
  data.noticeForm.isRead = 1
  if(msg.reply){
    request.post("/api/message/addOrUpdate", data.noticeForm)
    data.noReadTotal > 0 ? data.noReadTotal-- : 0
    
  }
  dialogFormVisible.value = true
}
// 删除信件
const handleDelete = ( row ) => {
  ElMessageBox.confirm("你确定删除所选记录？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  }).then(() => {
    request.delete("/api/message/delete/" + row.id).then((res) => {
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
onBeforeMount(() => {
  const res = JSON.parse(localStorage.getItem('users'))
  data.sendForm.sendName = res[0].username
})
onMounted(() => {
  load();
});
 /**
     * 查询当天日期
     */
     function getNowDate() {
      const timeOne = new Date()
      const year = timeOne.getFullYear()
      let month = timeOne.getMonth() + 1
      let day = timeOne.getDate()
      month = month < 10 ? '0' + month : month
      day = day < 10 ? '0' + day : day
      const NOW_MONTHS_AGO = `${year}-${month}-${day}`
      return NOW_MONTHS_AGO
    }

// 获取全部的消息通知
const load = () => {
  request
    .get("/api/message/findPage", {
      params: {
        pageNum: 1,
        pageSize: 100,
        theme: '',
        sendName: data.sendForm.sendName
      },
    })
    .then((res) => {
      if ((res.status = "200")) {
        let num = 0
        data.messageList = res.data.data
        data.list =  res.data.data
        res.data.data.map(item=>{
          if(item.reply){
            data.reply.push(item)
          }else{
            data.noReply.push(item)
          }
          if(item.reply && item.isRead == null){
            num = num+1
          }
        })
        data.noReadTotal = num
        data.total = res.data.total
      }
    })
}
const handleClose = () => {
  dialogFormVisible.value = false
}
// 发信件
const send = () => {
  if (!ruleFormRef) return;
    ruleFormRef.value.validate((valid) => {
      if (valid) {
        data.sendForm.sendTime = getNowDate()
  request.post("/api/message/addOrUpdate", data.sendForm).then((res) => {
        if (res) {
          ElMessage({
            showClose: true,
            message: '发送成功',
            type: 'success',
          })
          load()
          dialogVisible.value = false
        } else {
          ElMessage({
            showClose: true,
            message: '发送失败',
            type: 'error',
          })
        }
      })
    }
  })
}
</script>

<style lang="less" scoped>
.myMessages {
  .msgBody {
    width: 80%;
    overflow: hidden;
    /*溢出的部分隐藏*/
    white-space: nowrap;
    /*文本不换行*/
    height: 70px;
    margin: 4px auto;
    border: 1px solid rgb(190, 187, 187);
    border-radius: 6px;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .msgContent{
      margin-left: 20px;
    }
    .msg-icon {
      margin-right: 20px;
      color: #73a8dd;
      background-color: rgb(233, 234, 233);
    }

    .content-title {
      display: flex;
      align-items: center;
      .dot{
        width: 10px;
        height: 10px;
        background-color: red;
        border-radius: 50%;
        margin-right: 10px;
      }

      .content-title_cont {
        margin-left: 10px;
        cursor: pointer;

        &:hover {
          color: #409eff;
        }
      }
    }

    .info {
      display: flex;

      .info-left {
        display: flex;

        .info-time {
          color: rgb(122, 124, 125);
        }

        .info-views {
          .el-icon {
            margin: 0 4px 0 10px;
          }

          span {
            margin-right: 10px;
          }
        }
      }

      .info-right {
        margin-right: 100px;

        .el-icon {
          color: #409eff;
          margin: 0 4px;
        }
      }
    }
  }
}

.grid-item {
  background-color: #fff;
  height: calc(100vh - 140px);
  border-radius: 6px;
  overflow: auto;
  .title {
    margin: 20px 0 0 10%;
  }
}
</style>
