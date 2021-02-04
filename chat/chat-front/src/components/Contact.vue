<template>
  <div class="contact">
    <div class="top">
      <div class="left">
        <img class="avatar" :src="`${api}/static/img/${user.id}.jpg`" alt=""/>
      </div>
      <div class="right">
        {{ user.username }}
      </div>
    </div>
    <div v-if="friendList.length" class="bottom">
      <div v-for="(friend, i) in friendList" class="friend" :class="{activeColor: isActive(i)}" @click="setContact(i)">
        <div class="left">
          <img class="avatar" :src="`${api}/static/img/${friend.id}.jpg`" alt=""/>
        </div>
        <div class="right">
          {{ friend.username }}
        </div>
      </div>
    </div>
    <div v-else class="info">
      <div class="msg">
        还没有好友~~~
      </div>
    </div>
  </div>
</template>

<script>
import api from '@/network/const'
import {request} from '@/network'

export default {
  name: 'Contact',
  data() {
    return {
      api: api,
      active: -1,
      friendList: []
    }
  },
  mounted() {
    request({
      method: 'post',
      url: '/getFriends',
      params: {
        id: this.user.id
      }
    }).then(res => {
      this.friendList = res.data.data
    }).catch(err => {
      console.log(err)
    })
  },
  computed: {
    user() {
      return JSON.parse(localStorage.getItem('user'))
    }
  },
  methods: {
    setContact(index) {
      this.active = index
      this.$emit('set-contact', this.friendList[index])
    },
    isActive(index) {
      return this.active === index
    }
  }
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

.activeColor {
  background-color: #c9cbcb;
}

.top .left {
  flex: 1;
  text-align: center;
}

.avatar {
  width: 48px;
  height: 48px;
  border-radius: 4px;
}

.top .right {
  flex: 3;
}

.friend {
  width: 360px;
  height: 60px;
  line-height: 60px;
  display: flex;
  align-items: center;
  border-bottom: #faf7f7 1px solid;
}

.friend .left {
  flex: 1;
  margin-top: 24px;
  text-align: center;
}

.friend .right {
  flex: 3;
  color: #575454;
  font-size: 14px;
}

.friend .avatar {
  width: 36px;
  height: 36px;
}

.info {
  margin-top: 230px;
}

.info .msg {
  text-align: center;
}
</style>
