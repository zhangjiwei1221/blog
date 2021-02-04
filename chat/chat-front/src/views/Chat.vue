<template>
  <div id="app">
    <div class="main">
      <Contact @set-contact="set"/>
      <Dialog :contact="contact" :msgList="msgList"/>
    </div>
  </div>
</template>

<script>
import {request} from '@/network'
import Contact from '@/components/Contact'
import Dialog from '@/components/Dialog'

export default {
  name: "Chat",
  components: {
    Dialog,
    Contact
  },
  data() {
    return {
      contact: null,
      msgList: []
    }
  },
  methods: {
    set(user) {
      this.contact = user
      request({
        method: 'post',
        url: '/pullMsg',
        params: {
          from: JSON.parse(localStorage.getItem('user')).id,
          to: this.contact.id
        }
      }).then(res => {
        this.msgList = res.data.data
      }).catch(err => {
        console.log(err)
      })
    }
  }
}
</script>

<style scoped>
#app {
  width: 100%;
  height: 100%;
  background-size: cover;
  background-image: url("../assets/img/chat-bg.jpg");
}

.main {
  width: 1080px;
  height: 648px;
  margin-top: 72px;
  margin-left: auto;
  margin-right: auto;
  border-radius: 5px;
  background-color: #efeded;
  border: #d0d0d0 1px solid;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)
}
</style>
