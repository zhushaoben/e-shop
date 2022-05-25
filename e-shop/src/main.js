import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import axios from "axios";
import 'element-ui/lib/theme-chalk/index.css';
import './utils/api'

Vue.config.productionTip = false
Vue.use(ElementUI);
Vue.use(axios);
Vue.prototype.$axios = axios;
axios.defaults.withCredentials = true;
//axios.defaults.headers['withCredentials'] = true;
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
