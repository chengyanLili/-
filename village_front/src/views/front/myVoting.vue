<template>
  <div class="vote">
    <div class="myVoting">
      <div class="voteTitle">
        <el-icon><CameraFilled /></el-icon>
        <div>选举动态</div>
      </div>
      <div
        class="myMessages"
        v-for="(msg, index) in data.messageList"
        :key="index"
      >
        <div class="msgBody">
          <div class="msg-icon">
            <el-icon size="35"><Message /></el-icon>
          </div>
          <div class="msgContent" style="display: block">
            <ul class="info">
              <li style="width: 420px">{{ msg.voteType }} 选举</li>
              <el-tag style="cursor: pointer" @click="handleDetail"
                >点我报名</el-tag
              >
              <el-tag
                style="margin-left: 20px; cursor: pointer"
                type="success"
                @click="candidate(msg.voteType)"
                >查看候选人</el-tag
              >
            </ul>
          </div>
        </div>
      </div>
      <el-dialog width="60%" v-model="data.dialogTableVisible" title="报名选举">
        <el-form ref="formRef" :model="data.addForm" :rules="data.formRules">
          <el-form-item label="姓名" prop="name" :label-width="formLabelWidth">
            <el-input
              v-model="data.addForm.name"
              placeholder="请输入姓名"
              autocomplete="off"
            />
          </el-form-item>
          <el-form-item
            label="性别"
            prop="gender"
            :label-width="formLabelWidth"
          >
            <el-radio-group v-model="data.addForm.gender">
              <el-radio :label="'男'">男</el-radio>
              <el-radio :label="'女'">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item
            label="电话号码"
            prop="phone"
            :label-width="formLabelWidth"
          >
            <el-input
              v-model="data.addForm.phone"
              placeholder="请输入电话号码"
              autocomplete="off"
            />
          </el-form-item>
          <el-form-item
            label="出生日期"
            prop="birthday"
            :label-width="formLabelWidth"
          >
            <el-date-picker
              v-model="data.addForm.birthday"
              type="date"
              placeholder="请选择出生日期"
              format="YYYY/MM/DD"
              value-format="YYYY-MM-DD"
              style="width: 200px"
            />
          </el-form-item>
          <el-form-item
            label="竞选职位"
            prop="voteType"
            :label-width="formLabelWidth"
          >
            <el-select
              v-model="data.addForm.voteType"
              clearable
              class="m-2"
              style="margin-right: 10px"
              placeholder="请选择竞选职位"
            >
              <el-option
                v-for="item in config.VOTETYPE"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item
            label="经历"
            prop="experience"
            :label-width="formLabelWidth"
          >
            <el-input
              type="textarea"
              v-model="data.addForm.experience"
              placeholder="请输入主要职责"
              autocomplete="off"
            />
          </el-form-item>
          <el-form-item
            label="小组长"
            prop="whichGroup"
            :label-width="formLabelWidth"
          >
            <el-input
              type="textarea"
              v-model="data.addForm.whichGroup"
              placeholder="如果竞选的是小组长，请输入具体的小组"
              autocomplete="off"
            />
          </el-form-item>
          <el-form-item
            label="照片"
            prop="avatarUrl"
            :label-width="formLabelWidth"
          >
            <el-upload
              ref="uploadRef"
              class="upload-demo"
              action="http://localhost:9090/file/upload"
              :auto-upload="true"
              :on-success="fileUploadSuccess"
            >
              <template #trigger>
                <el-button type="primary">点击上传</el-button>
              </template>
            </el-upload>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button type="primary" @click="submit"> 立即报名 </el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from "vue";
import router from "../../router/index";
import request from "../../request/request";
import { ElMessage } from "element-plus";
import { config } from "../../constants/index.js";
const formRef = ref(null);
const formLabelWidth = "100px";
const data = reactive({
  messageList: [],
  candidateList: [],
  total: "",
  dialogTableVisible: false,
  noticeForm: {
    title: "",
    publishName: "",
    content: "",
  },
  addForm: {
    name: "",
    phone: "",
    age: "",
    gender: "",
    avatarUrl: "",
    voteType: "",
    experience: "",
  },
  formRules: {
    name: [{ required: true, message: "请输入竞选者姓名", trigger: "blur" }],
    phone: [
      { required: true, message: "请输入竞选者电话号码", trigger: "blur" },
    ],
    birthday: [{ required: true, message: "请选择生产日期", trigger: "blur" }],
    gender: [{ required: true, message: "请选择性别", trigger: "blur" }],
    experience: [{ required: true, message: "请输入经历", trigger: "blur" }],
  },
});
onMounted(() => {
  load();
});
// 获取全部的消息通知
const load = () => {
  request
    .get("/api/voting/findPage", {
      params: {
        pageNum: 1,
        pageSize: 100,
        year: "",
        voteType: "",
      },
    })
    .then((res) => {
      if ((res.status = "200")) {
        data.messageList = res.data.data;
        data.total = res.data.total;
      }
    });
  // 获取候选人
  request
    .get("/api/candidate/findPage", {
      params: {
        pageNum: 1,
        pageSize: 100,
        name: "",
        phone: "",
        voteType: "",
      },
    })
    .then((res) => {
      if ((res.status = "200")) {
        data.candidateList = res.data.data;
        data.total = res.data.total;
      }
    });
};
const handleDetail = () => {
  data.dialogTableVisible = true;
};
// 头像上传成功的回调
const fileUploadSuccess = (res) => {
  data.addForm.avatarUrl = res;
};

// 查看候选人
const candidate = (voteType) => {
  console.log("voteType", voteType);
  router.push({
    path: "/front/candidateList",
    query: {
      voteType: voteType,
    },
  });
};

// 提交表单
const submit = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      data.addForm.voteType = data.addForm.whichGroup;
      request.post("/api/candidate/addOrUpdate", data.addForm).then((res) => {
        if (res.status == 200) {
          ElMessage({
            showClose: true,
            message: "添加成功！",
            type: "success",
          });
          data.dialogTableVisible = false;
        } else {
          ElMessage({
            showClose: true,
            message: "添加失败,请联系管理员！",
            type: "error",
          });
        }
      });
    }
  });
};
</script>
<style lang="less" scoped>
.voteTitle {
  margin: 10px 10%;
  display: flex;
  align-items: center;
  .el-icon {
    font-size: 20px;
    margin-right: 6px;
    color: #409eff;
  }
}
body {
  background-color: #ebecef;
  .vote {
    background-color: #fff;
    height: calc(100vh - 140px);
    border-radius: 6px;
    overflow: auto;
    .myVoting {
      .el-dialog {
        .el-form {
          display: grid;
          grid-template-columns: 1fr 1fr;
        }
        .dialog-footer {
          display: flex;
          justify-content: center;
        }
      }
      .myMessages {
        .msgBody {
          width: 80%;
          overflow: hidden;
          /*溢出的部分隐藏*/
          white-space: nowrap;
          /*文本不换行*/

          height: 50px;
          margin: 10px auto;
          border: 1px solid rgb(190, 187, 187);
          border-radius: 6px;
          display: flex;
          align-items: center;

          .msg-icon {
            margin: 0 10px;
            color: #e6a23c;
          }
          .msgContent {
            .info {
              display: flex;
              list-style: none;
              font-size: 15px;
              font-weight: bold;
              color: black;
            }
          }
        }
      }
    }
  }
}
</style>
