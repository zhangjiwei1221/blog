import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'

Vue.use(Vuex)

import { default as user } from './modules/user'
import { default as permission } from './modules/permission'

const store = new Vuex.Store({
  modules: {
    user,
    permission
  },
  getters
})

export default store

