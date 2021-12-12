<template>
  <div id="app">
    <SysDept/>
  </div>
</template>

<script>
import SysDept from '@/views/SysDept'

export default {
  name: 'App',
  components: {
    SysDept
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
