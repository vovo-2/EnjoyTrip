<template>
  <div class="p-container">
    <p class="text-h2-bold text-center mt-5">관광지 정보</p>
    <div class="d-flex-column">
      <div class="select_container mb-5 pa-3">
        <h-select
          :options="sido"
          placeholder="시도"
          v-model:value="selectSido"
          @update:modelValue="sidoChange"
        />
        <h-select
          :options="gugun"
          placeholder="구군"
          v-model:value="selectGuGun"
          @update:modelValue="gugunChange"
        />
        <h-select
          :options="contentType"
          placeholder="관광지 유형"
          v-model:value="selectContent"
          @update:modelValue="contentChange"
        />
        <h-btn theme="black" @click="getSearchAttraction"> 검색</h-btn>
        <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
      </div>
      <div class="d-flex mb-5" style="gap: 12px">
        <h-input
          placeholder="검색하고 싶은 관광지명을 입력하세요"
          class="w-full"
          v-model:value="keyword"
        ></h-input>
        <h-btn theme="primaryLine" @click="clearOptions">초기화</h-btn>
      </div>
    </div>
    <KakaoMap :position="selectedAttraction" />
    <v-navigation-drawer
      v-model="drawer"
      absolute
      right
      width="300"
      class="drawer_container"
    >
      <attraction-card
        class="ma-3 cursor-pointer v-table--hover"
        v-if="attractions?.length > 0"
        v-for="attraction in attractions"
        :attraction="attraction"
        @click="setAttraction(attraction)"
      />

      <p v-else class="ma-3 text-h4-medium">검색결과가 존재하지 않습니다.</p>
    </v-navigation-drawer>
  </div>
</template>

<script setup>
import Header from "@/components/layouts/Header.vue";
import KakaoMap from "@/components/common/KakaoMap.vue";
import AttractionCard from "@/components/AttractionCard.vue";
import { useAttractionStore } from "@/stores/attraction";
import { onMounted, ref, computed, defineEmits } from "vue";
import { storeToRefs } from "pinia";

const emits = defineEmits(["update:modelValue"]);
const attractionStore = useAttractionStore();
const { reqGetSido, reqGetContentType, reqGetGuGun, reqGetSearch } =
  attractionStore;
const { sido, contentType, gugun, attractions } = storeToRefs(attractionStore);
const selectSido = ref("");
const selectContent = ref("");
const selectGuGun = ref("");
const selectedLat = ref("");
const selectedLon = ref("");
const searchOptions = [
  {
    title: "관광지명",
  },
  {
    title: "주소",
  },
];
const keyword = ref("");
const drawer = ref(false);
const loading = ref(false);
const gugunChange = (gugun) => {
  selectGuGun.value = gugun;
};
const contentChange = (content) => {
  selectContent.value = content;
};
const sidoChange = async (sido) => {
  selectSido.value = sido;
  await reqGetGuGun(selectSido.value);
};
const selectedAttraction = computed(() => {
  return {
    ...(selectedLat.value && { lat: selectedLat.value }),
    ...(selectedLon.value && { lon: selectedLon.value }),
  };
});
const setAttraction = (current) => {
  selectedLat.value = current.latitude;
  selectedLon.value = current.longitude;
};
const clearOptions = () => {
  selectSido.value = "";
  emits("update:modelValue", selectSido.value);
  gugunChange("");
};
const getSearchAttraction = async () => {
  if (loading.value) {
    return;
  }
  if (
    !selectSido.value &&
    !selectGuGun.value &&
    !selectContent.value &&
    !keyword.value
  ) {
    alert("하나의 옵션을 선택해주세요");
    return;
  }
  loading.value = true;
  const result = await reqGetSearch({
    ...(selectSido.value && { sidoCode: selectSido.value }),
    ...(selectGuGun.value && { gugunCode: selectGuGun.value }),
    ...(selectContent.value && { contentTypeId: selectContent.value }),
    ...(keyword.value && { word: keyword.value }),
    ...(keyword.value && { key: "title" }),
  });
  loading.value = false;
  drawer.value = true;
};

onMounted(async () => {
  if (navigator.geolocation) {
    // GeoLocation을 이용해서 접속 위치를 얻어옵니다
    navigator.geolocation.getCurrentPosition(function (position) {
      setAttraction({
        latitude: position.coords.latitude,
        longitude: position.coords.longitude,
      });
    });
  }
  loading.value = true;
  await reqGetSido();
  await reqGetContentType();
  loading.value = false;
});
</script>

<style scoped>
.select_container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 10px;
}
.drawer_container {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
