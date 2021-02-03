<template>
  <div class="contact">
    <div class="top">
      <div class="left">
        <img class="avatar" :src="`https://localhost/static/img/${user.id}.jpg`" alt=""/>
      </div>
      <div class="right">
        {{ user.username }}
      </div>
    </div>
    <div class="bottom">

    </div>
  </div>
</template>

<script>
import {mapState} from 'vuex'

export default {
  name: "Contact",
  computed: mapState({
    user: 'user'
  })
}
</script>

<style scoped>
.contact {
  width: 360px;
  height: 100%;
  float: left;
  border-right: #d0d0d0 1px solid;
}

.top {
  width: 100%;
  height: 80px;
  display: flex;
  align-items: center;
  border-bottom: #e0dfdf 1px solid;
}

.top .left {
  flex: 1;
  text-align: center;
}

.avatar {
  width: 48px;
  height: 48px;
  border-radius: 24px;
}

.top .right {
  flex: 3;
}
</style>
