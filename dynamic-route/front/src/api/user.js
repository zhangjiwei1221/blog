import request from '@/util/request'

export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

export function getUserInfo() {
  return request({
    url: '/youlai-admin/api/v1/users/me',
    method: 'get'
  })
}
