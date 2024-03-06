import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import ApiClient from "@/api";
import { httpStatusCode } from "@/utils/http-status";
const attraction = "attraction";
const user = "user";
const board = "board";
export const useBoardStore = defineStore(board, () => {
  const apiInstance = new ApiClient(
    { baseURL: import.meta.env.VITE_APP_API_BASE_URL },
    [user, attraction, board]
  );
  const articles = ref([]);
  const totalArticleCount = ref("");
  const lastKey = ref("");
  async function reqGetArticles(payload = {}) {
    const { key, word, lastArticleNo, listSize, first } = payload;
    const result = await apiInstance.board.getArticles({
      ...(key && { key }),
      ...(word && { word }),
      ...(lastArticleNo && { lastArticleNo }),
      ...(listSize && { listSize }),
    });
    if (result.status === httpStatusCode.OK) {
      const data = JSON.parse(result.data);
      if (first) {
        articles.value = data.articles;
        totalArticleCount.value = data.totalArticleCount;
        lastKey.value = data?.lastArticleNo;
      } else {
        data.articles.forEach((each) => {
          articles.value.push(each);
        });
        totalArticleCount.value = data.totalArticleCount;
        lastKey.value = data?.lastArticleNo;
      }
      console.log(lastKey.value);
    }
    return result;
  }
  async function reqPutArticle() {
    const result = await apiInstance.board.putArticle();
    if (result.status === httpStatusCode.OK) {
      console.log(result);
    }
    return result;
  }
  async function reqPostArticle(payload = {}) {
    const { subject, content, imageFiles, userId } = payload;
    const result = await apiInstance.board.postArticle({
      ...(subject && { subject }),
      ...(content && { content }),
      ...(imageFiles && { imageFiles }),
      ...(userId && { userId }),
    });
    if (result.status === httpStatusCode.OK) {
      console.log(result);
    }
    return result;
  }
  async function reqGetArticle(payload = {}) {
    const { articleNo } = payload;
    const result = await apiInstance.board.getArticle({
      ...(articleNo && { articleNo }),
    });
    if (result.status === httpStatusCode.OK) {
      return JSON.parse(result.data);
    }
    return result;
  }
  async function reqDeleteArticle() {
    const result = await apiInstance.board.deleteArticle();
    if (result.status === httpStatusCode.OK) {
      console.log(result);
    }
    return result;
  }

  return {
    reqGetArticles,
    reqGetArticle,
    reqPutArticle,
    reqPostArticle,
    reqDeleteArticle,
    articles,
    lastKey,
  };
});
