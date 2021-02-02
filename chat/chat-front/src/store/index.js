import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    contact: {}
  },
  mutations: {
    setContact(state, contact) {
      Vue.set(state, 'contact', contact)
    }
  }
})

export default store
