<template>
  <div class="login__wrapper border-box">
    <div class="img__wrapper"></div>
    <div class="form__wrapper">
      <div class="bg__wrapper px-10 py-15 rounded">
        <h1 class="text-h1-bold mb-10">로그인</h1>
        <h-input v-model:value="userId" label="아이디" class="mb-5" />
        <h-input
          type="password"
          v-model:value="userPassword"
          label="비밀번호"
        />
        <div class="btn__container mt-10">
          <h-btn theme="secondary" @click="openRegister">회원 가입</h-btn>
          <h-btn @click="reqLogin"> 로그인</h-btn>
        </div>
      </div>
    </div>
    <register :dialog="dialog" @close="dialog = false" />
  </div>
</template>

<script setup>
import Register from "@/components/Register.vue";
import { useRoute, useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";
import { useMenuStore } from "@/stores/menu";
import { ref, onMounted, onBeforeMount } from "vue";
import { storeToRefs } from "pinia";

const store = useUserStore();
const menuStore = useMenuStore();
const { userInfo } = storeToRefs(store);
const { reqPostLogin, reqGetUserInfo, changeUserInfo } = store;
const { changeMenuState } = menuStore;
const router = useRouter();
const route = useRoute();
const userId = ref("");
const loading = ref(false);
const userPassword = ref("");
const dialog = ref(false);
const openRegister = () => {
  dialog.value = true;
};

async function reqLogin() {
  if (loading.value) {
    return;
  }
  loading.value = true;
  const result = await reqPostLogin({
    ...(userId.value && { userId: userId.value }),
    ...(userPassword.value && { userPassword: userPassword.value }),
  });
  loading.value = false;
  if (result.status === 201) {
    let token = sessionStorage.getItem("accessToken");
    await reqGetUserInfo(token);
    changeUserInfo();
    changeMenuState();
    router.push("/");
  }
}

onBeforeMount(() => {
  if (history.state.isRegister) {
    dialog.value = true;
  }
});
</script>

<style lang="scss" scoped>
.login__wrapper {
  width: 100%;
  height: 100%;

  display: flex;
  justify-content: center;

  .btn__container {
    gap: 12px;
    display: flex;
    justify-content: flex-end;
  }
  .img__wrapper {
    background-image: url("@/assets/loginBG.jpeg");
    background-size: cover;
    width: 50%;
    height: 100%;
  }
  .form__wrapper {
    width: 50%;
    height: 80%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    padding-right: 240px;
    padding-left: 120px;
    .bg__wrapper {
      display: flex;
      flex-direction: column;
      justify-content: center;
      background-color: $gray1;
    }
  }
}
</style>
