<template>
  <div class="d-flex align-center my-8 text-body-2-regular justify-center">
    <button
      class="d-flex align-center"
      :disabled="!prevEnable"
      @click="prevPage"
    >
      <p-icon :icon="setLeftIcon" class="icon-outlined" />
      <span
        class="mx-3 text-body-2-regular"
        :class="prevEnable ? 'black--text' : 'gray--text'"
        >{{ prevBtnText }}</span
      >
    </button>
    <span class="mx-3 paging-hr"></span>
    <button
      class="d-flex align-center"
      :disabled="!nextEnable"
      @click="nextPage"
    >
      <span
        class="mx-3 text-body-2-regular"
        :class="nextEnable ? 'black--text' : 'gray--text'"
        >{{ nextBtnText }}</span
      >
      <p-icon :icon="setRightIcon" class="icon-outlined" />
    </button>
  </div>
</template>

<script>
export default {
  name: "PageMove",
  props: {
    lastKey: {
      type: String,
      default: "",
    },
    limit: {
      type: Number,
      default: 10,
    },
    itemLength: {
      type: Number,
      default: 5,
    },
    loadNext: {
      type: Function,
      default: () => {},
    },
    pageNum: {
      type: Number,
      default: 1,
    },
  },
  data() {
    return {
      nextBtnText: "다음",
      prevBtnText: "이전",
      page: 1,
    };
  },
  watch: {
    pageNum: {
      immediate: true,
      handler(pageNum) {
        this.page = pageNum;
      },
    },
  },
  computed: {
    prevEnable() {
      return this.page !== 1;
    },
    nextEnable() {
      if (this.itemLength <= this.page * this.limit) {
        if (this.lastKey) {
          return true;
        }
        return false;
      }
      return true;
    },
    setRightIcon() {
      return this.nextEnable ? "RightSm/Black" : "RightSm/Gray5";
    },
    setLeftIcon() {
      return this.prevEnable ? "LeftSm/Black" : "LeftSm/Gray5";
    },
  },
  methods: {
    nextPage() {
      if (this.lastKey) {
        if (this.itemLength === this.page * this.limit) {
          this.loadNext(false);
          this.$emit("next");
        }
      }
      this.page += 1;
      this.$emit("page", this.page);
    },
    prevPage() {
      this.page -= 1;
      this.$emit("page", this.page);
    },
  },
};
</script>

<style scoped lang="scss">
.icon-outlined {
  border: 1px solid $gray3;
  border-radius: 4px;
}

.paging-hr {
  width: 2px;
  display: inline-block;
  height: 20px;
  content: "";
  background-color: $gray3;
}
</style>
