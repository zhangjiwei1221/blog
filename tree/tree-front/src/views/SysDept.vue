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
            <el-button type="text" size="small" @click="addOrEdit = isAdd = true, reset()">新增</el-button>
            <el-button type="text" size="small" @click="info(scope.row)">查看</el-button>
            <el-button type="text" size="small" @click="initDialog(scope.row)">编辑</el-button>
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
    <el-dialog :visible.sync="addOrEdit" width="30%">
      <el-form :model="deptForm">
        <el-form-item v-if="deptForm.parentId !== '0'" label="上级部门" label-width="120px">
          <el-cascader
              style="width: 100%;"
              ref="cascader"
              :show-all-levels="false"
              v-model="deptForm.parentId"
              :options="deptTreeSelect"
              :props="{ checkStrictly: true, label: 'value', value: 'id' }"
              @change="deptOption"
              clearable/>
        </el-form-item>
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
        <el-button @click="addOrEdit = false">取 消</el-button>
        <el-button type="primary" @click="addOrEdit = false, submit()">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="infoDialog" width="25%">
      <el-descriptions title="部门信息" :column="1">
        <el-descriptions-item
            v-if="deptInfoForm.parentDeptName"
            label="上级部门">
          {{deptInfoForm.parentDeptName}}
        </el-descriptions-item>
        <el-descriptions-item label="编码">{{deptInfoForm.code}}</el-descriptions-item>
        <el-descriptions-item label="名称">{{deptInfoForm.name}}</el-descriptions-item>
        <el-descriptions-item label="部门及以下部门">
          <el-tree
              default-expand-all
              :data="childTreeSelect"
              :props="{ label: 'value' }"/>
        </el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="infoDialog = false">确 定</el-button>
      </div>
    </el-dialog>
  </el-container>
</template>

<script>
import {add, childTree, info, remove, tree, treeSelect, update} from '@/views/api/dept'

export default {
  name: "SysDept",
  data() {
    return {
      deptTree: [],
      deptTreeSelect: [],
      isAdd: false,
      addOrEdit: false,
      infoDialog: false,
      deptForm: {
        name: '',
        code: '',
        parentId: ''
      },
      deptInfoForm: {
        name: '',
        code: '',
        parentDeptName: ''
      },
      childTreeSelect: []
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
      tree().then(res =>
        this.deptTree = res.data
      )
      treeSelect().then(res => {
        this.deptTreeSelect = res.data
      })
    },
    /**
     * 清空表单缓存
     */
    reset() {
      this.deptForm = {
        name: '',
        code: '',
        parentId: ''
      }
    },
    /**
     * 编辑时初始化对话框信息
     */
    initDialog(row) {
      this.deptForm = { ...row }
      this.addOrEdit = true
      this.isAdd = false
    },
    deptOption() {
      this.$refs.cascader.dropDownVisible = false
    },
    /**
     * 新增 / 修改时提交表单
     */
    submit() {
      let _ = this.deptForm.parentId
      this.deptForm.parentId = _[_.length - 1]
      this.isAdd ? this.add(this.deptForm) : this.update(this.deptForm)
    },
    /**
     * 获取部门信息
     */
    info(row) {
      this.infoDialog = true
      info(row.id).then(res => {
        this.deptInfoForm = { ...res.data }
      })
      this.childTree(row)
    },
    /**
     * 获取指定部门的子树结构
     */
    childTree(row) {
      childTree(row.id).then(res => {
        this.childTreeSelect = res.data
      })
    },
    /**
     * 新增部门
     */
    add(data) {
      add(data).then(() => {
        this.init()
        this.$message.success('添加成功')
      })
    },
    /**
     * 修改部门信息
     */
    update(data) {
      update(data).then(() => {
        this.init()
        this.$message.success('修改成功')
      }).catch(errorMsg => {
        this.$message.error(errorMsg)
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
