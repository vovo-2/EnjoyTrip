import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import ApiClient from "@/api";
import { httpStatusCode } from "@/utils/http-status";
import { jwtDecode } from "jwt-decode";
const user = "user";
export const useUserStore = defineStore(user, () => {
  const apiInstance = new ApiClient(
    { baseURL: import.meta.env.VITE_APP_API_BASE_URL },
    [user]
  );
  const router = useRouter();

  const userInfo = ref(null);
  function changeUserInfo() {
    const user = localStorage.getItem("userInfo");
    if (user) {
      userInfo.value = JSON.parse(localStorage.getItem("userInfo"));
    } else {
      userInfo.value = null;
    }
  }
  async function reqPostLogin(payload = {}) {
    const { userId, userPassword } = payload;
    const result = await apiInstance.user.postLogin({
      ...(userId && { userId }),
      ...(userPassword && { userPassword }),
    });
    if (result.status === httpStatusCode.CREATE) {
      const data = JSON.parse(result.data);
      let accessToken = data["access-token"];
      let refreshToken = data["refresh-token"];
      sessionStorage.setItem("accessToken", accessToken);
      sessionStorage.setItem("refreshToken", refreshToken);
      await reqGetUserInfo(accessToken);
    } else {
      console.log("로그인 실패했다");
    }
    return result;
  }

  async function reqGetUserInfo(token) {
    let decodeToken = jwtDecode(token);
    console.log("2. decodeToken", decodeToken.userId);
    try {
      const result = await apiInstance.user.findById({
        ...(decodeToken.userId && { userId: decodeToken.userId }),
      });
      if (result.status === httpStatusCode.OK) {
        localStorage.setItem("userInfo", result.data);
      } else {
        console.log("유저 정보 없음!!!!");
      }
    } catch (error) {
      (async () => {
        await apiInstance.user.tokenRegenerate();
      })();
    }
  }

  async function reqTokenRegenerate() {
    console.log(
      "토큰 재발급 >> 기존 토큰 정보 : {}",
      sessionStorage.getItem("accessToken")
    );
    try {
      const result = await apiInstance.user.tokenRegeneration({
        ...(userInfo.value && { user: JSON.stringify(userInfo.value) }),
      });
      if (result.status === httpStatusCode.CREATE) {
        let accessToken = result.data["access-token"];
        console.log("재발급 완료 >> 새로운 토큰 : {}", accessToken);
        sessionStorage.setItem("accessToken", accessToken);
        isValidToken.value = true;
      }
    } catch (error) {
      if (error.response.status === httpStatusCode.UNAUTHORIZED) {
        console.log("갱신 실패");
        // 다시 로그인 전 DB에 저장된 RefreshToken 제거
        try {
          const result = await apiInstance.user.logout(userInfo.value.userid);
          if (result.status === httpStatusCode.OK) {
            console.log("리프레시 토큰 제거 성공");
          } else {
            console.log("리프레시 토큰 제거 실패");
          }
          alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
          userInfo.value = null;
          router.push({ name: "home" });
        } catch (e) {
          console.error(error);
          userInfo.value = null;
        }
      }
    }
  }

  async function reqUserLogout(userId) {
    try {
      const result = await apiInstance.user.logout(userId);
      if (result.status === httpStatusCode.OK) {
        userInfo.value = null;
      } else {
        console.error("유저 정보 없음!!!!");
      }
    } catch (error) {
      console.log(error);
    }
  }

  async function reqPostUser(payload = {}) {
    const { userName, userPassword, userId, emailId, emailDomain } = payload;
    const result = await apiInstance.user.postUser({
      ...(userName && { userName }),
      ...(userPassword && { userPassword }),
      ...(userId && { userId }),
      ...(emailId && { emailId }),
      ...(emailDomain && { emailDomain }),
    });
    return result;
  }

  return {
    userInfo,
    reqPostLogin,
    reqGetUserInfo,
    reqTokenRegenerate,
    reqUserLogout,
    changeUserInfo,
    reqPostUser,
  };
});
