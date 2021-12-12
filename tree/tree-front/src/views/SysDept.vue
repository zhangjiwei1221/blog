<template>
  <div>
    <el-tree
        :data="deptTree"
        :props="{ label: 'value' }"/>
  </div>
</template>

<script>
import { treeSelect } from '@/views/api/dept'

export default {
  name: "SysDept",
  data() {
    return {
      deptTree: []
    }
  },
  created() {
    treeSelect().then(data => {
      this.deptTree = data.data
    })
  }
}
</script>

<style scoped>

</style>
