<template>
  <div>
    {{ count }}
    <button @click="increment">+</button>
    <slot :user="user">
      {{ user.name }}
    </slot>
  </div>
</template>

<script>
export default {
  name: "SlotDemo",
  data() {
    return {
      count: 0
    }
  },
  props: {
    user: {
      type: Object,
      default: {}
    }
  },
  methods: {
    increment() {
      this.count++;
    }
  }
}
</script>

<style scoped>

</style>
