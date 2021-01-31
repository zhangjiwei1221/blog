<template>
  <el-row type="flex" class="login">
    <el-col :span="6">
      <h1 class="title">JWT 测试</h1>
      <el-form :model="loginForm" :rules="rules" status-icon ref="ruleForm" class="demo-ruleForm">
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            autocomplete="off"
            placeholder="用户名"
            prefix-icon="el-icon-user-solid"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            type="password"
            v-model="loginForm.password"
            autocomplete="off"
            placeholder="请输入密码"
            prefix-icon="el-icon-lock"
          ></el-input>
        </el-form-item>
        <el-form-item prop="isRemember">
          <el-checkbox v-model="loginForm.isRemember" class="remember">记住密码</el-checkbox>
          <el-button type="primary" @click="submitForm" class="login-btn">登录</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>
<script>

import {
  Row,
  Col,
  Form,
  Input,
  Button,
  Message,
  Checkbox,
  FormItem,
  Loading
} from 'element-ui'
import {request} from '../network/request'

export default {
  name: 'Login',
  components: {
    'el-row': Row,
    'el-col': Col,
    'el-form': Form,
    'el-checkbox': Checkbox,
    'el-input': Input,
    'el-button': Button,
    'el-form-item': FormItem
  },
  data() {
    return {
      loginForm: {
        username: '',
        password: '',
        isRemember: false
      },
      rules: {
        username: [{
          required: true,
          message: '请输入用户名',
          trigger: 'blur'
        }],
        password: [{
          required: true,
          message: '请输入密码',
          trigger: 'blur'
        }]
      }
    }
  },
  methods: {
    submitForm() {
      const loading = Loading.service({ fullscreen: true })
      request({
        method: 'post',
        url: '/login',
        data: {
          'username': this.loginForm.username,
          'password': this.loginForm.password,
          'isRemember': this.loginForm.isRemember
        }
      }).then(res => {
        loading.close()
        localStorage.setItem('uid', res.data)
        this.$router.push('/home')
        Message('登录成功')
      }).catch(err => {
        console.log(err)
      })
    }
  }
}
</script>

<style scoped>
.login {
  height: 100%;
  justify-content: center;
  background-size: cover;
  background-image: url("../assets/img/login-bg.jpg");
}

.login, el-col {
  align-items: center;
}

.remember {
  width: 37%;
  margin-left: 3%;
}

.title {
  text-align: center;
}

.login-btn {
  width: 60%;
  height: 40px;
  border-radius: 20px;
}
</style>
