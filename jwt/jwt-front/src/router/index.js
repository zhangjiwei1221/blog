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

router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    next()
  } else {
    let token = localStorage.getItem('authorization')
    if (!token) {
      next('/login')
    } else {
      next()
    }
  }
})

export default router
