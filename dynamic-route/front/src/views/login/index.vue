<template>
  <div class="main">
    <el-card class="login-form-layout">
      <el-form :model="loginForm" :rules="loginRules" ref="loginForm">
        <h2 class="login-title color-main">Vue 动态路由</h2>
        <el-form-item prop="username">
          <el-input
            type="text"
            placeholder="请输入用户名"
            prefix-icon="el-icon-user"
            v-model="loginForm.username"/>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            type="password"
            placeholder="请输入密码"
            show-password
            prefix-icon="el-icon-lock"
            v-model="loginForm.password"
            @keyup.enter.native="handleLogin"/>
        </el-form-item>
        <el-form-item style="margin-bottom: 60px;text-align: center">
          <el-button
            type="primary"
            style="width: 100%"
            :loading="loading"
            @click.native.prevent="handleLogin"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'login',
  data() {
    return {
      loginForm: {
        username: 'admin',
        password: '123456',
      },
      loginRules: {
        username: [{required: true, trigger: 'blur'}],
        password: [{required: true, trigger: 'blur'}]
      },
      loading: false
    }
  },
  methods: {
    ...mapActions({
      login: 'user/login'
    }),
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.login(this.loginForm).then(() => {
            this.loading = false
            this.$router.push({path: '/'})
          }).catch(err => {
            this.loading = false
            this.$message.error(err)
          })
          return true
        }
        return false
      })
    }
  }
}
</script>

<style scoped>
.main {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  background-image: linear-gradient(120deg, #89f7fe 0%, #66a6ff 100%);
}

.login-form-layout {
  min-width: 360px;
  min-height: 320px;
  margin-left: auto;
  margin-right: auto;
}

.login-title {
  text-align: center;
}
</style>
