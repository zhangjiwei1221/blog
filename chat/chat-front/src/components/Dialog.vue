<template>
  <div class="dialog">
    <div class="top">

    </div>
    <div class="middle" @mouseover="over" @mouseout="out">
      <div v-for="i in 20" style="height: 50px;border: #d0d0d0 1px solid;border-radius: 3px;margin-bottom: 10px;">
        {{ i }}
      </div>
    </div>
    <div class="line"></div>
    <div class="bottom">
    </div>
  </div>
</template>

<script>
export default {
  name: "Dialog",
  mounted() {
    const mid = document.querySelector('.middle')
    mid.scrollTop = mid.scrollHeight
  },
  methods: {
    over() {
      this.setColor('#cecdcd')
    },
    out() {
      this.setColor('#0000')
    },
    setColor(color) {
      document.documentElement.style.setProperty('--scroll-color', `${color}`)
    }
  }
}
</script>

<style scoped>
:root {
  --scroll-color: #0000;
}

.dialog {
  width: 719px;
  height: 100%;
  float: right;
}

.top {
  width: 100%;
  height: 60px;
  border-bottom: #d0d0d0 1px solid;
}

.top::after {
  content: " ";
  float: right;
  position: relative;
  top: 61px;
  border: 4px solid #0000;
  border-top-color: #8e9292;
}

.middle {
  height: 432px;
  overflow: auto;
  padding: 10px;
  margin: 6px 0 11px 0;
}

.middle::-webkit-scrollbar {
  width: 8px;
  height: 1px;
}

.middle::-webkit-scrollbar-thumb {
  border-radius: 8px;
  background-color: var(--scroll-color);
}

.middle::-webkit-scrollbar-track {
  background: #fff;
  border-radius: 4px;
}

.line {
  width: 100%;
  height: 0;
  position: relative;
  top: -6px;
  border-top: #d0d0d0 1px solid;
}

.bottom {

}

.bottom::after {
  content: " ";
  float: right;
  position: relative;
  top: -15px;
  border: 4px solid #0000;
  border-bottom-color: #8e9292;
}
</style>
