import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    user: null,
    contact: null
  },
  mutations: {
    setUser(state, user) {
      Vue.set(state, 'user', user)
    },
    setContact(state, contact) {
      Vue.set(state, 'contact', contact)
    }
  }
})

export default store
