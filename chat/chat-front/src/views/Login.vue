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
        <el-form-item>
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
  Loading,
  Message,
  FormItem
} from 'element-ui'
import {request} from '@/network'

export default {
  name: 'Login',
  components: {
    'el-row': Row,
    'el-col': Col,
    'el-form': Form,
    'el-input': Input,
    'el-button': Button,
    'el-form-item': FormItem
  },
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
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
          'password': this.loginForm.password
        }
      }).then(res => {
        loading.close()
        let user = res.data.data
        delete user.password
        if (!user) {
          Message('用户名或密码错误')
          return
        }
        localStorage.setItem('user', JSON.stringify(user))
        this.$router.push('/chat')
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

.title {
  text-align: center;
}

.login-btn {
  width: 100%;
  height: 40px;
  border-radius: 20px;
}
</style>
