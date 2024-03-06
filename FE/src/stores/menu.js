import { ref, computed } from "vue";
import { defineStore, storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";
export const useMenuStore = defineStore("menu", () => {
  const router = useRouter();
  const userStore = useUserStore();
  const { userInfo } = storeToRefs(userStore);
  const { changeUserInfo } = userStore;
  const toLogin = () => {
    router.push({
      name: "login",
    });
  };
  const toRegister = () => {
    router.push({
      name: "login",
      state: { isRegister: true },
    });
  };
  const toLogout = () => {
    sessionStorage.removeItem("accessToken");
    sessionStorage.removeItem("refreshToken");
    localStorage.removeItem("userInfo");
    changeUserInfo();
    changeMenuState();
    router.push({
      name: "home",
    });
  };
  const menuList = ref([
    {
      name: "회원가입",
      show: true,
      move: toRegister,
      theme: "primary",
    },
    { name: "로그인", show: true, move: toLogin, theme: "secondary" },
    {
      name: "로그아웃",
      show: false,
      move: toLogout,
      theme: "secondaryLine",
    },
  ]);
  const changeMenuState = () => {
    menuList.value = menuList.value.map((item) => ({
      ...item,
      show: !item.show,
    }));
  };

  return {
    menuList,
    toLogin,
    toRegister,
    changeMenuState,
  };
});
