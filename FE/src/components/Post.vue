<template>
  <div class="post-item" @click.stop="$emit('click')">
    <div class="post__wrapper d-flex justify-center flex-column align-start">
      <!-- if private and user own this post-->

      <!-- post title -->
      <div class="text-h5-medium">
        <div class="d-flex align-center">
          <p class="post-title black--text">{{ postItem.subject }}</p>
        </div>
      </div>
      <!-- time reply view-count -->
      <div
        class="d-flex text-detail-1-regular gray--text text--darken-2 flex-column gap-4"
      >
        <p>
          {{ getDateFormat(postItem.registerTime) }}
          &nbsp;&middot;&nbsp; 조회수: {{ postItem.hit }}&nbsp;&middot;&nbsp;
          작성자: {{ postItem.userName }}
        </p>
      </div>
    </div>
  </div>
</template>
<script setup>
import { getDateFormat } from "@/utils/date-format";
const props = defineProps({
  loading: {
    type: Boolean,
    default: false,
  },
  postItem: {
    type: Object,
    default: () => {},
  },
});
</script>

<style lang="scss" scoped>
.post-item {
  display: flex;
  flex-direction: column;
  border-radius: 4px;
  overflow: hidden;
  width: 100%;
  &.private {
    .post__wrapper {
      border-top-left-radius: 0;
      border-top-right-radius: 0;
    }
  }
  position: relative;
  color: $gray8;
  @include hover-before;
}
.post__wrapper {
  box-sizing: border-box;
  border: 1px solid $gray3;
  background-color: $white;
  padding: 20px;
  border-radius: 4px;
  gap: 12px;
}
.status-bar {
  background-color: $gray2;
  border-color: $gray3;
  border-radius: 4px 4px 0px 0px;
  border-width: 1px 1px 0px 1px;
  border-style: solid;
  gap: 8px;
  padding: 12px 20px;
}
.post-title {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: break-word;
}
.post--content__wrapper {
  max-height: 80px;
  @media (max-width: $mdBreakPoint) {
    max-height: 60px;
  }
  .post--content {
    width: 100%;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 4;
    overflow: hidden;
    text-overflow: ellipsis;
    word-break: break-all;
    @media (max-width: $mdBreakPoint) {
      -webkit-line-clamp: 2;
    }
  }
  .thumbnail {
    object-fit: cover;
    width: 80px;
    height: 80px;
    @media (max-width: $mdBreakPoint) {
      width: 60px;
      height: 60px;
    }
  }
}
.attach-count {
  background: rgba(0, 0, 0, 0.7);
  border-radius: 4px;
  width: 22px;
  height: 20px;
  color: $white;
  position: relative;
  bottom: 25px;
  left: 55px;
  @media (max-width: $mdBreakPoint) {
    bottom: 25px;
    left: 35px;
  }
}
</style>
