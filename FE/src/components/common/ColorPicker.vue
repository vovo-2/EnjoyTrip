<template>
  <div>
    <span class="text-body-2-regular gray--text text--darken-3 mb-2">
      색상 선택
    </span>
    <div class="pt-2 d-flex align-center justify-start">
      <span
        class="d-block color"
        v-for="color in colors"
        :key="color"
        :style="`background-color: ${color}; border-color: ${color}`"
        @click="$emit('click', color)"
      >
        <p-icon
          v-if="selectedColor === color"
          icon="Check/White"
          :size="12"
          style="position: absolute; top: 6px; left: 6px"
        />
      </span>
    </div>
  </div>
</template>

<script>
export default {
  name: "ColorPicker",
  props: {
    selectedColor: {
      type: String,
    },
  },
  data() {
    return {
      colors: [
        "#C60000",
        "#F55E42",
        "#FFC400",
        "#0AAC03",
        "#295BE3",
        "#181486",
        "#6C00FF",
        "#222222",
      ],
    };
  },
};
</script>

<style lang="scss" scoped>
.color {
  width: 24px;
  height: 24px;
  border-radius: 50%;
  position: relative;
  cursor: pointer;
  &:not(:last-child) {
    margin-right: 16px;
  }
  @include hover-before;
}
</style>
