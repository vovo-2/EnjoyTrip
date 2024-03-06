<template>
  <v-dialog
    v-model="props.dialog"
    persistent
    no-click-animation
    max-width="840px"
    content-class="white"
  >
    <v-card>
      <div class="px-10 py-15">
        <div class="d-flex justify-space-between align-center mb-10">
          <h2 class="text-h2-bold text-center">회원 가입</h2>
          <v-icon @click="closeDialog">mdi-close-thick</v-icon>
        </div>
        <div>
          <h-input label="아이디" v-model:value="userId" />
          <h-input
            type="password"
            label="비밀번호"
            v-model:value="userPassword"
          />
          <h-input label="이름" v-model:value="userName" />
          <div class="d-flex align-center w-full" style="gap: 8px">
            <h-input label="이메일" v-model:value="emailId" class="w-full" />
            <p class="gray--text">@</p>
            <h-input
              label="도메인"
              v-model:value="emailDomain"
              class="w-full"
            />
          </div>
        </div>
        <div class="d-flex justify-end mt-5">
          <h-btn theme="secondary" @click="reqRegister">{{
            registerText
          }}</h-btn>
        </div>
      </div>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useUserStore } from "@/stores/user";
const store = useUserStore();
const { reqPostUser } = store;

const props = defineProps({
  dialog: Boolean,
});
const emit = defineEmits(["close"]);
const userId = ref("");
const userPassword = ref("");
const userName = ref("");
const emailId = ref("");
const emailDomain = ref("");
const loading = ref(false);
const registerText = ref("회원가입");
const closeDialog = () => {
  emit("close");
};

async function reqRegister() {
  if (loading.value) {
    return;
  }
  loading.value = true;
  const result = await reqPostUser({
    ...(userId.value && { userId: userId.value }),
    ...(userPassword.value && { userPassword: userPassword.value }),
    ...(userName.value && { userName: userName.value }),
    ...(emailId.value && { emailId: emailId.value }),
    ...(emailDomain.value && { emailDomain: emailDomain.value }),
  });
  loading.value = false;
  if (result.status === 200) {
    emit("close");
  }
}
</script>

<style lang="scss" scoped></style>
