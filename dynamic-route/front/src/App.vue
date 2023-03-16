<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<style lang="scss">
html, body, #app {
  height: 100%;
  margin: 0;
  padding: 0;
}

#app {
  font-family: Consolas;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}

::-webkit-scrollbar-thumb {
  border-radius: 6px;
  background-color: #afacac;
}

::-webkit-scrollbar-track {
  background: #efeded;
  border-radius: 3px;
}
</style>
