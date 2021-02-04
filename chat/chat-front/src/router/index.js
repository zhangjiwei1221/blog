import Vue from 'vue'
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
  let tmp = localStorage.getItem('user')
  const user = tmp && JSON.parse(tmp)
  if (to.path !== '/login' && !user) {
    next('/login')
  }
  next()
}))

export default router
