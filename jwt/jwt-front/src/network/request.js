import axios from 'axios'
import router from '../router'

export function request(config) {

  const req = axios.create({
    baseURL: 'http://localhost:8888',
    timeout: 5000
  })

  req.interceptors.request.use(config => {
    const token = localStorage.getItem('authorization')
    token && (config.headers.authorization = token)
    return config
  })

  req.interceptors.response.use(response => {
    const token = response.headers.authorization
    if (token) {
      localStorage.setItem('authorization', token)
    } else {
      localStorage.removeItem('authorization')
      router.push('/login')
    }
    return response.data
  })

  return req(config)
}
