<template>
  <div class="app">
    <button @click="download">下载</button>
  </div>
</template>

<script>

export default {
  name: 'Notify',
  methods: {
    download() {
      let str1 = new Blob(['Hello, world1!'], {type: 'text/plain;charset=utf-8'})
      let str2 = new Blob(['Hello, world2!'], {type: 'text/plain;charset=utf-8'})
      saveAs(str1, `license.txt`)
      saveAs(str2, `key.txt`)
    }
  }
}
</script>

<style scoped>

</style>
