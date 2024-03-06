<template>
  <v-app id="app">
    <Header />
    <router-view />
  </v-app>
</template>

<script setup>
import Header from "@/components/layouts/Header.vue";
</script>

<style>
/* @import "@/style/vuetify.min.css"; */
@import "@/style/import-font.css";
@import "@/style/reset.css";
@import "@/style/global.css";
</style>
<style lang="scss">
@import "@/style/styles.scss";
.v-snack__content {
  width: 100%;
  display: flex;
  justify-content: center;
  font-size: 14px;
}
</style>
<style scoped>
#enjoy {
  position: relative;
}

#app > .web {
  height: 100%;
  width: 100%;
}
</style>
