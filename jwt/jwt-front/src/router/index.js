import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component() {
      return import('@/views/Home')
    }
  },
  {
    path: '/home',
    name: 'Home',
    component() {
      return import('@/views/Home')
    }
  },
  {
    path:'/login',
    name:'Login',
    component() {
      return import('@/views/Login')
    }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


// 设置全局的前置导航守卫
router.beforeEach((to, from, next) => {
  // 如果跳转的目的路径是 login 界面, 不做操作
  if (to.path === '/login') {
    next()
  } else {
    /**
     * 如果是其他界面, 判断本地是否存在 Token
     * 如果存在, 则正常跳转
     * 否则重定向到 login 界面
     */
    let token = localStorage.getItem('authorization')
    if (!token) {
      next('/login')
    } else {
      next()
    }
  }
})

export default router
