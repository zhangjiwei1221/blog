import request from '@/network'

const prefix = '/api/system/dept'

// 获取部门树结构
export function tree(params) {
  return request({
    url: `${prefix}/tree`,
    method: 'get',
    params
  })
}

// 获取部门下拉树结构
export function treeSelect(params) {
  return request({
    url: `${prefix}/treeSelect`,
    method: 'get',
    params
  })
}

// 删除部门
export function remove(id) {
  return request({
    url: `${prefix}/delete/${id}`,
    method: 'delete'
  })
}
