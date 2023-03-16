import request from '@/util/request'

export function getMenuRouteList() {
  return request({
    url: '/youlai-auth/oauth/token',
    method: 'post',
    params: data,
    headers: {
      'Authorization': 'Basic bWFsbC1hZG1pbi13ZWI6MTIzNDU2'
    }
  })
}
