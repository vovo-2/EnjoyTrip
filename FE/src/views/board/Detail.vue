<template>
  <section
    class="post-detail__content d-flex flex-column w-full mw-800 mx-auto pa-10 rounded"
  >
    <!-- profile nickname menu -->
    <!-- post title -->
    <h4 class="text-h4-medium">{{ article.subject }}</h4>
    <div class="d-flex align-center justify-end">
      <p class="ml-2 text-body-2-regular">작성자 : {{ article.userName }}</p>
      <!-- slot pubtype status chip -->
    </div>
    <!-- post content -->
    <div style="min-height: 360px" class="post-detail__conts">
      {{ article.content }}
    </div>
    <!-- register time -->
    <div
      class="d-flex align-center text-detail-1-regular gray--text text--darken-2"
    >
      {{ getDateFormat(article.registerTime) }} &nbsp;&middot;&nbsp; 조회수:
      {{ article.hit }}
    </div>
  </section>
</template>

<script setup>
// articleNo
// :
// 251
// content
// :
// "테스트 마지막 글1133333"
// hit
// :
// 19
// imageInfos
// :
// []
// registerTime
// :
// "2023-11-04 23:50:12"
// subject
// :
// "999테스트 마지막 글12233"
// userId
// :
// "ssafy"
// userName
// :
// "ssafyName"
import { useRoute } from "vue-router";
import { ref, onMounted } from "vue";
import { getDateFormat } from "@/utils/date-format";
import { useBoardStore } from "@/stores/board";
const boardStore = useBoardStore();
const { reqGetArticle } = boardStore;
const route = useRoute();
const loading = ref(false);
const article = ref({});
onMounted(async () => {
  if (loading.value) {
    return;
  }
  loading.value = true;
  const result = await reqGetArticle({
    ...(route.params.articleNo && { articleNo: route.params.articleNo }),
  });
  loading.value = false;
  article.value = result;
});
</script>

<style lang="scss" scoped>
.post-detail__content {
  gap: 20px;
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
  margin-bottom: 40px;
}
.post-detail__conts {
  width: 100%;
  word-break: break-all;
}
.attach__content {
  cursor: pointer;
  display: grid;
  grid-template-columns: 40px 1fr;
  border-radius: 4px;
  align-items: center;
  justify-content: center;
  gap: 8px;
  padding: 8px;
  background-color: $gray1;
  margin-top: 4px;
  margin-bottom: 4px;
}
</style>
