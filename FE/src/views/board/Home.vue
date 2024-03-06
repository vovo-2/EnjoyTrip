<template>
  <div class="d-flex flex-column home__container p-container">
    <div class="d-flex align-center justify-space-between mb-5">
      <h2 class="text-h2-medium black--text">게시글</h2>
      <h-btn theme="secondary" @click="goToWrite">작성하기</h-btn>
    </div>
    <section class="content d-flex gap-3">
      <h-input
        filled
        placeholder="찾고싶은 글을 입력하세요."
        class="w-full"
        v-model:value="keyword"
      >
      </h-input>
      <h-btn theme="black" @click="searchArticles"> 검색</h-btn>
    </section>
    <section
      class="d-flex flex-column notice__container mt-5"
      v-if="articles.length > 0"
    >
      <router-link
        v-for="article in articles"
        :to="{
          name: 'article',
          params: {
            articleNo: article.articleNo,
          },
        }"
        :key="article.articleNo"
        class="mt-3"
      >
        <post :post-item="article" />
      </router-link>
    </section>
    <empty-box v-else msg="게시글이 존재하지 않습니다." />
    <v-progress-circular
      v-if="loading"
      indeterminate
      color="gray darken-3"
      class="mx-auto"
    />
    <v-overlay v-if="loading" z-index="999" opacity="0" absolute />
  </div>
</template>
<script setup>
import EmptyBox from "@/components/common/EmptyBox.vue";
import Post from "@/components/Post.vue";
import { useBoardStore } from "@/stores/board";
import { useUserStore } from "@/stores/user";
import { onMounted, ref, computed, defineEmits, onBeforeUnmount } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
const router = useRouter();
const boardStore = useBoardStore();
const userStore = useUserStore();
const { reqGetArticles } = boardStore;
const { reqGetUserInfo } = userStore;
const loading = ref(false);
const keyword = ref("");
const { articles, lastKey } = storeToRefs(boardStore);
const { userInfo } = storeToRefs(userStore);
const goToWrite = () => {
  router.push({
    name: "board-write",
  });
};
const searchArticles = async () => {
  if (loading.value) {
    return;
  }
  loading.value = true;
  const result = await reqGetArticles({
    first: true,
    ...(keyword.value && { word: keyword.value }),
    ...(keyword.value && { key: "subject" }),
  });
  loading.value = false;
};
const loadArticles = async () => {
  if (loading.value || lastKey.value === 0) {
    return;
  }
  loading.value = true;
  const result = await reqGetArticles({
    lastArticleNo: lastKey.value,
    ...(keyword.value && { word: keyword.value }),
    ...(keyword.value && { key: "subject" }),
  });
  loading.value = false;
};
const handleScroll = () => {
  const scrollTop = window.scrollY;
  const clientHeight = document.body.clientHeight;
  const scrollHeight = document.body.scrollHeight;
  const margin = 120;
  if (scrollTop + clientHeight + margin > scrollHeight && !loading.value) {
    if (!lastKey) {
      return;
    }
    loadArticles();
  }
};
onMounted(async () => {
  window.addEventListener("scroll", handleScroll);
  if (loading.value) {
    return;
  }
  loading.value = true;
  const result = await reqGetArticles({
    first: true,
  });
  loading.value = false;
  const accessToken = sessionStorage.getItem("accessToken");
  if (accessToken) {
    await reqGetUserInfo(accessToken);
  }
});
onBeforeUnmount(() => {
  window.removeEventListener("scroll", handleScroll);
});
</script>
<style lang="scss" scoped>
.home__container {
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
</style>
