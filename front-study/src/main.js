import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'normalize.css/normalize.css'
import { saveAs } from 'file-saver'

Vue.use(ElementUI)
Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')
