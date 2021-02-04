<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>

export default {
  name: 'App',
  components: {
  }
}
</script>

<style>
body, html, #app {
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
}

#app {
  overflow: auto;
}

#app::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}

#app::-webkit-scrollbar-thumb {
  border-radius: 6px;
  background-color: #afacac;
}

#app::-webkit-scrollbar-track {
  background: #efeded;
  border-radius: 3px;
}
</style>
