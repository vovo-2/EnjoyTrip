<template>
  <div
    class="management-description-card pa-5 rounded gray-3-outlined d-flex-column align-center"
  >
    <img class="lazy" :src="attractionImg" />
    <p class="text-h2-bold text-center">{{ attraction.title }}</p>
    <div class="d-flex-column" style="gap: 12px">
      {{ attraction.addr1 }}
      <h-chip class="mr-2">
        {{ attractionContent }}
      </h-chip>
    </div>
  </div>
</template>

<script setup>
import { defineProps, computed } from "vue";
const content = {
  12: "관광지",
  14: "문화시설",
  15: "축제공연행사",
  25: "여행코스",
  28: "레포츠",
  32: "숙박",
  38: "쇼핑",
  39: "음식점",
};
const props = defineProps({
  attraction: {
    type: Object,
    default: () => {},
  },
});

const attractionImg = computed(() => {
  return props.attraction.firstImage
    ? props.attraction.firstImage
    : "/src/assets/img_default.png";
});
const attractionContent = computed(() => {
  return content[props.attraction.contentTypeId];
});
</script>

<style lang="scss" scoped>
.management-description-card {
  background-color: $gray4;
  gap: 20px;
  img {
    width: 100%;
    border-radius: 20px;
    aspect-ratio: 1 / 1;
    object-fit: cover;
  }
  @media (max-width: $smBreakPoint) {
    grid-template-columns: 1fr;
  }
}
</style>
<!-- 

addr1: "대전광역시 동구 대전로 592-20"
addr2: ""
contentId
: 323001
contentTypeId: 12
firstImage: ""
firstImage2: ""
gugunCode: 2
latitude:36.31411524
longitude: 127.4422639
mlevel: "6"
readcount: 26720
sidoCode: 3
tel: ""
title
: "심광사(대전)"
zipcode: "34688"
 -->
