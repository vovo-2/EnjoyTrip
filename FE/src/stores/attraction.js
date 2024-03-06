import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import ApiClient from "@/api";
import { httpStatusCode } from "@/utils/http-status";
const attraction = "attraction";
const user = "user";
export const useAttractionStore = defineStore(attraction, () => {
  const apiInstance = new ApiClient(
    { baseURL: import.meta.env.VITE_APP_API_BASE_URL },
    [user, attraction]
  );
  const attractions = ref([]);
  const content = {
    12: "관광지",
    14: "문화시설",
    15: " 축제공연행사",
    25: "여행코스",
    28: "레포츠",
    32: "숙박",
    38: "쇼핑",
    39: "음식점",
  };
  const sido = ref([]);
  const contentType = ref([]);
  const gugun = ref([]);
  async function reqGetSido() {
    const result = await apiInstance.attraction.getSido();
    if (result.status === httpStatusCode.OK) {
      sido.value = JSON.parse(result.data);
      sido.value = sido.value.map((data) => {
        return {
          title: data.sidoName,
          value: data.sidoCode,
        };
      });
    }
    return result;
  }

  async function reqGetContentType() {
    const result = await apiInstance.attraction.getContentType();
    if (result.status === httpStatusCode.OK) {
      contentType.value = JSON.parse(result.data);

      contentType.value = contentType.value.map((data) => {
        return {
          title: content[data],
          value: data,
        };
      });
    }
    return result;
  }

  async function reqGetGuGun(sido) {
    const result = await apiInstance.attraction.getGuGun({
      ...(sido && { sido }),
    });
    if (result.status === httpStatusCode.OK) {
      gugun.value = JSON.parse(result.data);
      gugun.value = gugun.value.map((data) => {
        return {
          title: data.gugunName,
          value: data.gugunCode,
        };
      });
    }
  }

  async function reqGetSearch(payload = {}) {
    const { sidoCode, gugunCode, contentTypeId, key, word } = payload;
    const result = await apiInstance.attraction.getSearch({
      sidoCode,
      gugunCode,
      contentTypeId,
      ...(key && { key }),
      ...(word && { word }),
    });
    if (result.status === httpStatusCode.OK) {
      attractions.value = JSON.parse(result.data);
    }
  }
  return {
    reqGetSido,
    reqGetContentType,
    sido,
    contentType,
    reqGetGuGun,
    gugun,
    reqGetSearch,
    attractions,
  };
});
