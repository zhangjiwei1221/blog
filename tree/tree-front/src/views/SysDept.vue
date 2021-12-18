<template>
  <el-container class="container">
    <el-aside class="aside">
      <el-tree
          default-expand-all
          :data="deptTreeSelect"
          :props="{ label: 'value' }"/>
    </el-aside>
    <el-main>
      <el-table
          :data="deptTree"
          row-key="id">
        <el-table-column
            align="center"
            prop="name"
            label="名称"/>
        <el-table-column
            align="center"
            prop="code"
            label="编码"/>
        <el-table-column
            align="center"
            prop="gmtCreate"
            label="创建时间"/>
        <el-table-column
            align="center"
            label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="small">新增</el-button>
            <el-button type="text" size="small">查看</el-button>
            <el-button type="text" size="small">编辑</el-button>
            <el-button type="text" size="small" @click="remove(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
import {remove, tree, treeSelect} from '@/views/api/dept'

export default {
  name: "SysDept",
  data() {
    return {
      deptTree: [],
      deptTreeSelect: []
    }
  },
  created() {
    this.init()
  },
  methods: {
    /**
     * 初始化下拉列表及表格数据
     */
    init() {
      tree().then(data =>
        this.deptTree = data.data
      )
      treeSelect().then(data => {
        this.deptTreeSelect = data.data
      })
    },
    /**
     * 删除指定行数据
     */
    remove(row) {
      remove(row.id).then(() => {
        this.init()
        this.$message.success('删除成功')
      }).catch(errorMsg => {
        this.$message.error(errorMsg)
      })
    }
  }
}
</script>

<style lang="less" scoped>
.container {
  width: 80%;
  height: 500px;
  margin-left: auto;
  margin-right: auto;

  .aside {
    border-right: solid 1px #E4E7ED;
  }
}
</style>
