<template>
  <div id="app">
    <Chat/>
  </div>
</template>

<script>
import Chat from '@/views/Chat'

export default {
  name: 'App',
  components: {
    Chat
  }
}
</script>

<style>
#app {
  overflow: hidden;
}
</style>
