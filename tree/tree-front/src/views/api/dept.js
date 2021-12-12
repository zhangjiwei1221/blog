import request from '@/network'

// 获取部门下拉树结构
export function treeSelect(params) {
    return request({
        url: '/api/system/dept/treeSelect',
        method: 'get',
        params
    })
}
