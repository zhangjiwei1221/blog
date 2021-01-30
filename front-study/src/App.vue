<template>
  <div id="app">
    <SlotDemo v-slot="{ user }" :user=" { name: 'aaa', age: 21 }">
    </SlotDemo>
  </div>
</template>

<script>
import Chart from './components/Chart'
import SlotDemo from './components/SlotDemo'

export default {
  name: 'App',
  components: {
    Chart,
    SlotDemo
  }
}
</script>

<style>
#app {
}
</style>
