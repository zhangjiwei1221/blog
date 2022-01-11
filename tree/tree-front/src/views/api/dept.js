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

// 获取部门懒加载下拉树结构
export function lazyTreeSelect(parentId) {
  return request({
    url: `${prefix}/lazyTree/${parentId}`,
    method: 'get'
  })
}

// 获取部门及以下部门树结构列表
export function childTree(id) {
  return request({
    url: `${prefix}/childTree/${id}`,
    method: 'get'
  })
}

// 获取部门信息
export function info(id) {
  return request({
    url: `${prefix}/info/${id}`,
    method: 'get'
  })
}

// 新增部门
export function add(data) {
  return request({
    url: `${prefix}/add`,
    method: 'post',
    data
  })
}

// 修改部门
export function update(data) {
  return request({
    url: `${prefix}/update`,
    method: 'put',
    data
  })
}

// 删除部门
export function remove(id) {
  return request({
    url: `${prefix}/delete/${id}`,
    method: 'delete'
  })
}
