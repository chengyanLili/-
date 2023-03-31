import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  // 反向代理
  server: {
    open: true,  //启动项目自动打开浏览器
    port:8080,
    host:"127.0.0.1",
    proxy: {
         //    配置跨域
         '/api': {
             target: 'http://127.0.0.1:9090',//这里是后端接口地址
             changeOrigin: true,
             rewrite: (path) => path.replace(/^\/api/,'')
        }
    },
  
}
})



