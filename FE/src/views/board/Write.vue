<template>
  <div class="w-full mw-800 mx-auto pa-10 write__container rounded">
    <section class="w-full mb-10">
      <p class="text-h1-bold mb-6">게시글 작성</p>
    </section>
    <div class="outline-box mb-5">
      <section class="w-full mb-7">
        <p class="text-body-2-regular mb-2 text-label">
          제목 <span class="coral--text">*</span>
        </p>
        <h-input
          placeholder="문의 제목을 입력해주세요."
          v-model:value="subject"
          :maxlength="50"
          countLength
          :readonly="loading"
        />
      </section>
      <section class="w-full mb-7">
        <p class="text-body-2-regular mb-2 text-label">
          내용 <span class="coral--text">*</span>
        </p>
        <textarea style="height: 300px" v-model="content" />
      </section>
      <section class="w-full">
        <p class="text-body-2-regular mb-2 text-label">
          파일 업로드 <span class="coral--text">*</span>
        </p>
        <file-upload />
      </section>
    </div>
    <div class="w-full d-flex mt-10">
      <h-btn theme="grayLine" class="w-full mr-1" large @click="onCancel">
        <span class="d-block">취소</span>
      </h-btn>
      <h-btn theme="primary" class="w-full ml-1" large @click="onSubmit">
        <span class="d-block">등록</span>
      </h-btn>
    </div>
  </div>
</template>

<script setup>
import FileUpload from "@/components/common/FileUpload.vue";
import { useRouter } from "vue-router";
import { ref } from "vue";
import { useBoardStore } from "@/stores/board";
import { useUserStore } from "@/stores/user";
import { storeToRefs } from "pinia";
const boardStore = useBoardStore();
const userStore = useUserStore();
const { reqPostArticle } = boardStore;
const { userInfo } = storeToRefs(userStore);
const subject = ref("");
const content = ref("");
const router = useRouter();
const imageFiles = ref([]);
const loading = ref(false);
const onCancel = () => {
  router.push({
    name: "board",
  });
};
const onSubmit = async () => {
  if (loading.value) {
    return;
  }
  if (!subject.value || !content.value) {
    alert("내용을 입력해주세요");
    return;
  }
  if (!userInfo.value) {
    alert("로그인이 필요한 서비스입니다.");
    return;
  }
  const tmpInfo = userInfo.value.userInfo;
  loading.value = true;
  const result = await reqPostArticle({
    ...(subject.value && { subject: subject.value }),
    ...(content.value && { content: content.value }),
    ...(imageFiles.value && { imageFiles: imageFiles.value }),
    ...(tmpInfo.userId && { userId: tmpInfo.userId }),
  });
  loading.value = true;
  // if (result.status === 200) {
  //   router.push({
  //     name: "board",
  //   });
  // } else {
  //   alert("알 수 없는 오류입니다.");
  //   router.push({
  //     name: "board",
  //   });
  // }
};
</script>
<style lang="scss" scoped>
.write__container {
  border: 1px solid $gray3;
  margin-top: 12vh;
  margin-bottom: 12vh;
  @media (max-width: $lgBreakPoint) {
    margin-top: 120px;
    margin-bottom: 120px;
  }
  @media (max-width: $mdBreakPoint) {
    margin-top: 80px;
    margin-bottom: 80px;
  }
}
* {
  box-sizing: border-box;
  font-size: 14px;
}
textarea {
  width: 100%;
  padding: 12px;
  border-radius: 4px;
  border: 1px solid $gray3;
  border-radius: 4px;
  outline: none;
  background-color: $white;
  box-sizing: border-box;
  font-size: 14px;
  color: $black;
  letter-spacing: -1px;

  &::placeholder {
    color: $gray6;
    font-size: 14px;
  }
  &.invalid {
    border-color: $error;
  }
  &:focus {
    border-color: $gray6;
    outline: none;
  }
  &:focus-visible {
    outline: none;
  }
  &.success-line {
    border-color: $success;
  }
  &.readonly {
    background-color: $gray2;
  }
  &.disabled {
    background-color: $gray2;
    color: $gray5;
    cursor: not-allowed;
  }
}
</style>
