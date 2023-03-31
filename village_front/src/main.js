import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import { ID_INJECTION_KEY } from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import zhCn from "element-plus/lib/locale/lang/zh-cn"
import './assets/main.css'

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }
app.provide(ID_INJECTION_KEY, {
    prefix: Math.floor(Math.random() * 10000),
    current: 0
  })

app.use(ElementPlus,{
  locale: zhCn,
})
// 把Element-plus的分页改为中文
zhCn.el.pagination = {
  goto: '跳至',
  pagesize: '条/页',
  total: `共计 {total} 条`,
  pageClassifier: '页',
};

app.use(createPinia())
app.use(router)


app.mount('#app')
