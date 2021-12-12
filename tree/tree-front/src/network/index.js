import {HOST, TIME_OUT, TOKEN} from '@/assets/js/const'
import axios from 'axios'

const req = function request(config) {

  const req = axios.create({
    baseURL: HOST,
    timeout: TIME_OUT
  })

  req.interceptors.response.use(response => response.data)

  return req(config)
}

export default req
