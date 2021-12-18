import axios from 'axios'
import {HOST, TIME_OUT} from '@/assets/js/const'

const req = function request(config) {

  const req = axios.create({
    baseURL: HOST,
    timeout: TIME_OUT
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
