import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router'
import permissionDirective from './directives/permission'

const app = createApp(App)
app.use(ElementPlus)
app.use(router)
app.directive('permission', permissionDirective)
app.mount('#app')

console.log('App mounted successfully')