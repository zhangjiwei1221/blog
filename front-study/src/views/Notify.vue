<template>
  <button @click="notifyMe">notify</button>
</template>

<script>
export default {
  name: "Notify",
  methods: {
    notifyMe() {
      new Notification('状态更新提醒',{
        body: '你的朋友圈有3条新状态，快去查看吧',
        tag: 'linxin',
        icon: 'http://blog.gdfengshuo.com/images/avatar.jpg',
        requireInteraction: true
      })
    }
  }
}
</script>

<style scoped>

</style>
