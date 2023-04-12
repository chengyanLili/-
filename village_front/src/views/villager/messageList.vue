<template>
  <div class="grid-item messages">
    <div class="title">
      <el-badge :value="data.total" class="item">
        <span>消息通知</span>
      </el-badge>
    </div>
    <div class="myMessages" v-for="(msg, index) in data.messageList" :key="index">
      <div class="msgBody">
        <div class="msg-icon">
          <el-icon size="40">
            <Files />
          </el-icon>
        </div>
        <div class="msgContent">
          <div class="content-title">
            <el-badge is-dot class="item">
              <span class="dianzhui">动态</span>
            </el-badge>

            <span @click="toMsgDetail(msg)" class="content-title_cont">{{
              msg.title
            }}</span>
          </div>
          <div class="info">
            <div class="info-left">
              <div class="info-time">
                {{ msg.createTime }}-{{ msg.finishTime }}
              </div>
              <div class="info-views">
                <el-icon>
                  <View />
                </el-icon>
                <span>{{ msg.viewsNum }}</span>
              </div>

              <div class="info-right">
                <el-icon size="20">
                  <ChatLineRound />
                </el-icon>
                <span>{{ msg.commentNum }}</span>
              </div>
              <div class="info-state">
                <el-button v-if="msg.progress == '进行中'" type="success" size="small">{{ msg.progress }}</el-button>
                <el-button v-else type="info" size="small">{{
                  msg.progress
                }}</el-button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <el-drawer v-model="drawer" direction="rtl" :before-close="handleClose" size="65%">
    <template #header>
      <h4>消息详情</h4>
    </template>
    <template #default>
      <div>
        <el-form label-width="80px">
          <div class="input">
            <el-form-item label="主题">
              <el-input disabled v-model="data.noticeForm.title" clearable autocomplete="off" placeholder="请输入主题"></el-input>
            </el-form-item>
            <el-form-item label="姓名">
              <el-input disabled v-model="data.noticeForm.publishName" clearable autocomplete="off"
                placeholder="请输入发布者姓名"></el-input>
            </el-form-item>
          </div>
          <el-form-item label="内容">
            <v-md-editor 
              ref="md" 
              v-model="data.noticeForm.content"
              left-toolbar=""
              right-toolbar=""
              mode="preview">
            </v-md-editor>
          </el-form-item>
        </el-form>

          <div v-for="i in 18" class="comments">
            hhh
          </div>
        
      </div>
    </template>
    <template #footer>
      <div style="flex: auto">
        回到顶部
        <el-backtop :right="100" :bottom="100" :visibility-height="10"/>
       </div>
    </template>
  </el-drawer>
</template>

<script setup>
import router from "@/router/index";
import { reactive, ref, onMounted } from "vue";
import request from "../../request/request";

const data = reactive({
  messageList: [],
  total: "",
  noticeForm: {
    title: '',
    publishName:'',
    content:''
  },
});

const drawer = ref(false);

const toMsgDetail = (msg) => {
  data.noticeForm = msg
  console.log('msg',msg)
  drawer.value = true;
};
onMounted(() => {
  load();
});
// 获取全部的消息通知
const load = () => {
  request
    .get("/api/task/findPage", {
      params: {
        pageNum: 1,
        pageSize: 100,
        title: "",
      },
    })
    .then((res) => {
      if ((res.status = "200")) {
        data.messageList = res.data.data;
        data.total = res.data.total;
      }
    });
};
const handleClose = ()=>{
  drawer.value = false
}

// 获取当前路由
// let urlName = router.currentRoute.value.name
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
    margin: 20px auto;
    border: 1px solid rgb(190, 187, 187);
    border-radius: 6px;
    display: flex;
    align-items: center;

    .msg-icon {
      margin: 0 10px;
      color: #73a8dd;
      background-color: rgb(233, 234, 233);
    }

    .content-title {
      .dianzhui {
        border: 1px solid rgb(190, 187, 187);
        border-radius: 3px;
        background-color: #88e875;
        margin-right: 0px;
        padding: 0 10px;
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
  border-radius: 6px;
  overflow: auto;

  .title {
    margin: 20px 0 0 10%;
  }
}
.el-drawer__header{
  margin-bottom: 0;
}
.el-input{
    width: 200px;
    font-size: 14px;
  }
  .input{
    display: flex;
  }
</style>
