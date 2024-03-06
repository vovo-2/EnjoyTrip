<template>
  <header class="header__container border-box">
    <div class="header__wrap">
      <div class="header__wrapper">
        <img
          class="logo cursor-pointer"
          src="@/assets/logo.png"
          alt=""
          @click="toHome"
        />
        <nav class="local-nav d-flex justify-center">
          <ul class="d-flex align-center">
            <li
              v-for="(menu, index) in localMenu"
              :key="index"
              @click="menu.move"
              class="cursor-pointer px-4 py-2 gray-3-border-bottom text-center text-h5-bold"
            >
              {{ menu.name }}
            </li>
          </ul>
        </nav>
      </div>
      <div class="header__wrapper">
        <template v-for="menu in menuList" :key="menu.routeName">
          <template v-if="menu.show">
            <h-btn @click="menu.move" :theme="menu.theme">{{
              menu.name
            }}</h-btn>
          </template>
        </template>
      </div>
    </div>
  </header>
</template>

<script setup>
import { useUserStore } from "@/stores/user";
import { storeToRefs } from "pinia";
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import { useMenuStore } from "@/stores/menu";

const menuStore = useMenuStore();
const userStore = useUserStore();
const { menuList } = storeToRefs(menuStore);
const router = useRouter();
const { changeMenuState } = menuStore;
const { userInfo } = storeToRefs(useUserStore);
const toAttraction = () => {
  router.push({
    name: "attraction",
  });
};
const toHome = () => {
  router.push({
    name: "home",
  });
};
const toBoard = () => {
  router.push({
    name: "board",
  });
};
const localMenu = [
  {
    name: "관광지 정보",
    move: toAttraction,
  },
  {
    name: "게시판",
    move: toBoard,
  },
];
</script>

<style scoped lang="scss">
.local-nav {
  ul {
    gap: 12px;
    li {
      &:hover {
        color: $grey7;
      }
    }
  }
}
.logo {
  max-width: 50%;
  height: auto;
  display: block;
}
.header__wrapper {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  gap: 20px;
}
.header__container {
  width: 100%;
  height: var(--header-height);
  z-index: var(--header-zIndex);

  box-sizing: border-box;
  position: relative;
  background-color: $white;
  box-shadow: 0 1px 0 0 $gray3;

  display: flex;
  flex-direction: row;
  align-items: center;
}
.header__container .header__wrap {
  max-width: 1240px;
  width: 100%;
  padding: 0 20px;
  display: flex;
  flex-direction: row;
  align-items: center;
  margin: 0 auto;
  justify-content: space-between;
}
.header__container .login__wrap {
  font-size: 12px;
  margin-left: 13px;
  color: #1c1c1c;
  font-weight: 00;
  display: flex;
  align-items: center;
}
.header__container .login__wrap span {
  cursor: pointer;
  padding: 0 8px;
}
.login-btn {
  background-color: $white;
  border: 1.5px solid $gray3;
  border-radius: 8px;
  color: $black;
  padding: 8px 20px;
  font-size: 14px;
  font-weight: 400;
  letter-spacing: -0.5px;
  margin-left: 8px;
  transition: all 0.3s ease-in-out;
  cursor: pointer;
  height: 40px;
  &:hover {
    background-color: $primary100;
    border-color: $primary500;
    color: $primary800;
  }
}
.campus-register-btn {
  background-color: $primary;
  border: 1.5px solid $primary;
  border-radius: 8px;
  color: $white;
  padding: 8px 20px;
  margin-left: 16px;
  transition: all 0.3s ease-in-out;
  height: 40px;
  font-size: 14px;
  font-weight: 500;
  letter-spacing: -0.5px;
  &:hover {
    background-color: $primary800;
    border-color: $primary800;
  }
}
</style>
