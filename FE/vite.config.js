// import { fileURLToPath, URL } from 'node:url'
import path from 'path'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'

// https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vite-plugin
import vuetify from 'vite-plugin-vuetify'
const __dirname = path.resolve()
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue(), vueJsx(), vuetify({ autoImport: true })],
  resolve: {
    alias: [{ find: '@', replacement: path.resolve(__dirname, 'src') }]
  },
  runtimeCompiler: true,
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: `
          @import "@/style/config/_variables.scss"; 
          @import "@/style/config/_mixin.scss";
        `
      }
    }
  }
})
