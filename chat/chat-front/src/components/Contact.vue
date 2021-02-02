<template>
  <div class="contact">
    <div class="top">
      <div class="left">
        <img class="avatar" src="http://localhost:8888/static/img/1.jpg" alt=""/>
      </div>
      <div class="right">

      </div>
    </div>
    <div class="bottom">

    </div>
  </div>
</template>

<script>
export default {
  name: "Contact"
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
