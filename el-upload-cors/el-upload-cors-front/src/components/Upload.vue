<template>
  <div class="app">
    <el-upload
      class="upload-demo"
      action="http://localhost:8888/springboot/uploadByAction">
      <el-button size="small" type="primary">点击上传</el-button>
    </el-upload>
  </div>
</template>

<script>

import axios from 'axios';

export default {
  name: 'Upload',
  methods: {
    upload(param) {
      const formData = new FormData()
      formData.append('file', param.file)
      const url = 'http://localhost:8888/uploadWithoutAction'
      axios.post(url, formData).then(data => {
        console.log('上传图片成功')
      }).catch(response => {
        console.log('图片上传失败')
      })
    }
  }
}
</script>

<style scoped>

</style>
