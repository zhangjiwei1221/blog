<template>
  <el-row type="flex" justify="center" align="middle" class="main">
    <el-col :span="12">
      <el-button @click="exit">注销</el-button>
      <el-button @click="test">测试</el-button>
    </el-col>
  </el-row>
</template>

<script>

import {
  Row,
  Col,
  Button,
  Message
} from 'element-ui'
import {request} from '../network/request'

export default {
  name: 'Home',
  components: {
    'el-row': Row,
    'el-col': Col,
    'el-button': Button
  },
  methods: {
    exit() {
      request({
        method: 'get',
        url: '/exit',
        params: {
          uid: localStorage.getItem('uid')
        }
      }).then(() => {
        // 用户注销后清除本地 Token
        localStorage.removeItem('authorization')
        // 并跳转到登录界面
        this.$router.push('/login')
        Message('注销成功')
      }).catch(err => {
        console.log(err)
      })
    },
    test() {
      request({
        method: 'get',
        url: '/hello',
      }).then(res => {
        if (res.data) {
          // Token 未过期则会正常返回 'Hello, world!' 信息
          Message(res.data)
        } else {
          // 否则提示用户登录
          Message('请重新登录')
        }
      }).catch(err => {
        console.log(err)
      })
    }
  }
}
</script>

<style scoped>
.main {
  height: 100%;
  text-align: center;
}
</style>
