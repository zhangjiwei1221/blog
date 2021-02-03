import Vue from 'vue'
import store from '@/store'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const Login = () => import('@/views/Login')
const Chat = () => import('@/views/Chat')

const routes = [
  {
    path: '/',
    redirect: '/chat'
  },
  {
    path:'/login',
    name:'Login',
    component: Login
  },
  {
    path:'/chat',
    name:'Chat',
    component: Chat
  }
]

const router = new VueRouter({
  mode: 'history',
  routes
})

router.beforeEach(((to, from, next) => {
  const user = store.state.user
  if (to.path !== '/login' && !user) {
    next('/login')
  }
  next()
}))

export default router
