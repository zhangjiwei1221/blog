import axios from 'axios'
import store from '@/store'
import { getToken } from '@/util/auth'

const req = function request(config) {

  const req = axios.create({
    baseURL: 'http://localhost',
    timeout: 60 * 1000
  })

  req.interceptors.request.use(
    config => {
      if (store.getters.token) {
        config.headers['Authorization'] = getToken() || ''
      }
      return config
    })

  req.interceptors.response.use(response => {
    const res = response.data;
    if (res?.code === 500) {
      return Promise.reject(res.message)
    }
    return res
  })

  return req(config)
}

export default req
