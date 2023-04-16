<script setup>
import { ref, reactive } from "vue";
import { ElMessage } from 'element-plus'
import request from '../../request/request.js'
import router from '../../router/index.js'

const ruleFormRef = ref(null)
const user = reactive({});

const rules = reactive({
    username: [
        { required: 'true', message: '账户不能为空', trigger: 'blur' }
    ],
    password: [
        { required: 'true', message: '密码不能为空', trigger: 'blur' }
    ],
    identify: [
        { required: 'true', message: '请选择角色', trigger: 'blur' }
    ]

});

// 进行登录验证 
const login = async () => {
    if (!ruleFormRef) return;
    ruleFormRef.value.validate((valid) => {
        if (valid) {
            request.post('/api/users/login',user).then((res) => {
                if (res.data.length !==0 ) {
                    localStorage.setItem('users', JSON.stringify(res.data))
                    ElMessage({
                        showClose: true,
                        message: '登录成功！',
                        type: 'success',
                    })
                    if (user.identify == 1) {
                        router.push('/home')
                        return
                    }
                    router.push('/villager')
                   
                } else {
                    ElMessage({
                        showClose: true,
                        message: '用户名或密码错误！',
                        type: 'error',
                    })
                }
            })

        } else {
            ElMessage({
                showClose: true,
                message: '用户名或密码不能为空！',
                type: 'error',
            })
            return false;
        }
    });
};

</script>

<template>
    <div class="wrap">
        <div class="login">
            <div class="form">
                <el-form ref="ruleFormRef" :model="user" status-icon :rules="rules" label-width="120px"
                    class="user">
                    <el-form-item label="账号：" prop="username">
                        <el-input v-model="user.username" autocomplete="off" placeholder="请输入账号"></el-input>
                    </el-form-item>
                    <el-form-item label="密码：" prop="password">
                        <el-input show-password v-model="user.password" type="password" autocomplete="off"
                            placeholder="请输入密码"></el-input>
                    </el-form-item>
                    <el-form-item label="角色：" prop="identify">
                        <el-select v-model="user.identify" placeholder="请选择角色">
                            <el-option label="村干部" value="1" />
                            <el-option label="普通村民" value="2" />
                        </el-select>
                    </el-form-item>
                    <br>
                    <el-form-item >
                        <el-button type="primary" @click="login(ruleFormRef)">登录</el-button>
                        <el-button type="warning">
                            <router-link to="/register">注册</router-link>
                        </el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<style lang="less" scoped>
.wrap {
    background-image: url(../../assets/img/village.jpeg);
    background-repeat: no-repeat;
    background-size: 100% 100%;
    width: 100%;
    height: 100%;
    position: fixed;
    opacity: 0.8;

    .login {
        width: 30%;
        height: 40%;
        min-width: 300px;
        min-height: 300px;
        background-color: #fff;
        position: absolute;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
        margin: auto;
        opacity: 0.9;
        border-radius: 8px;
        box-shadow: 0 0 10px 5px #b1b3b8;

        .form {
            margin-top: 3rem;
            width: 300px;
        }

    }
}

a {
    //去掉下换线
    text-decoration: none;

    //文字颜色更改
    color: #fff;
}
</style>