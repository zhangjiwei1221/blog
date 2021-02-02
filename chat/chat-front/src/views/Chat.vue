<template>
  <div class="main">
    <Contact/>
    <Dialog/>
  </div>
</template>

<script>
import {mapState} from 'vuex'
import {mapMutations} from 'vuex'
import Contact from '@/components/Contact'
import Dialog from "@/components/Dialog";

export default {
  name: "Chat",
  components: {
    Dialog,
    Contact
  },
  computed: mapState({
    contact: 'contact'
  }),
  methods: {
    ...mapMutations(['setContact'])
  }
}
</script>

<style scoped>
.main {
  width: 1080px;
  height: 648px;
  margin-top: 72px;
  margin-left: auto;
  margin-right: auto;
  border-radius: 5px;
  border: #d0d0d0 1px solid;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)
}
</style>
