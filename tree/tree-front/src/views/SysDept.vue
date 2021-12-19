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
            <el-button type="text" size="small" @click="infoOrEdit = true">新增</el-button>
            <el-button type="text" size="small">查看</el-button>
            <el-button type="text" size="small">编辑</el-button>
            <el-popconfirm
                confirm-button-text='好的'
                cancel-button-text='不用了'
                icon="el-icon-info"
                icon-color="red"
                title="确认删除该条数据吗？"
                style="margin-left: 10px;"
                @confirm="remove(scope.row)">
              <el-button
                  type="text"
                  size="small"
                  slot="reference">
                删除
              </el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
    <el-dialog :visible.sync="infoOrEdit">
      <el-form :model="deptForm">
        <el-form-item label="名称" label-width="120px">
          <el-input
              v-model="deptForm.name"
              autocomplete="off"/>
        </el-form-item>
        <el-form-item label="编码" label-width="120px">
          <el-input
              v-model="deptForm.code"
              autocomplete="off"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="infoOrEdit = false">取 消</el-button>
        <el-button type="primary" @click="infoOrEdit = false">确 定</el-button>
      </div>
    </el-dialog>
  </el-container>
</template>

<script>
import {remove, tree, treeSelect} from '@/views/api/dept'

export default {
  name: "SysDept",
  data() {
    return {
      deptTree: [],
      deptTreeSelect: [],
      infoOrEdit: false,
      deptForm: {
        name: '',
        code: ''
      }
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
