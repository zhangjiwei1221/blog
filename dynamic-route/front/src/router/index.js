import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const Login = () => import('@/views/login/index')
const Home = () => import('@/views/Home')

export const constantRoutes = [
  {
    path: '/login',
    name: 'login',
    meta: {
      title: '登录'
    },
    component: Login
  },
  {
    path: '/',
    name: 'home',
    meta: {
      title: '主页'
    },
    component: Home
  }
]

const createRouter = () => new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL || '',
  routes: constantRoutes
})

const router = createRouter()

export function resetRouter() {
  router.matcher = createRouter().matcher
}

export default router
