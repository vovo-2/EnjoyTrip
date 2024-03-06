<template>
  <div
    class="empty-box"
    :style="`height: ${height}px; min-height: ${height}px`"
  >
    <v-progress-circular
      v-if="loading"
      class="mr-2"
      indeterminate
      color="black"
      :size="16"
      width="2"
    />{{ loading ? "데이터를 불러오는 중입니다." : msg }}
    <slot />
  </div>
</template>

<script>
export default {
  name: "EmptyBox",
  props: {
    msg: {
      type: String,
      default: "등록된 데이터가 없습니다.",
    },
    loading: {
      type: Boolean,
      default: false,
    },
    height: {
      type: [Number, String],
      default: 360,
    },
  },
};
</script>

<style lang="scss" scoped>
.empty-box {
  display: flex;
  flex: 1;
  justify-content: center;
  align-items: center;
  text-align: center;
  color: $gray8;
  border: 1px dashed $gray3;
  border-radius: 4px;
  font-size: 14px;
}
</style>
