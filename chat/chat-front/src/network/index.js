import axios from 'axios'

export function request(config) {

  const req = axios.create({
    baseURL: 'https://localhost',
    timeout: 5000
  })

  return req(config)
}
