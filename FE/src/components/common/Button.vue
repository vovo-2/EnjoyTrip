<template>
  <button
    @click="onClick"
    :disabled="disabled"
    class="p-btn border-box"
    :class="{
      'p-size--large': large,
      'p-size--small': small,
      'p-size--medium': !large && !small,
      'p-btn--primary': theme === 'primary',
      'p-btn--primary-line': theme === 'primaryLine',
      'p-btn--gray-line': theme === 'grayLine',
      'p-btn--white-line': theme === 'whiteLine',
      'p-btn--black-line': theme === 'blackLine',
      'p-btn--black': theme === 'black',
      'p-btn--transparent': theme === 'transparent',
      'p-btn--secondary': theme === 'secondary',
      'p-btn--secondary-line': theme === 'secondaryLine',
      'p-btn--excel': theme === 'excel',
      'p-btn--success': theme === 'success',
      'p-btn--disabled': disabled,
    }"
  >
    <span class="p-btn__content">
      <slot v-if="!loading" />
      <v-progress-circular
        v-else
        indeterminate
        :color="spinnerColor"
        size="23"
        width="2"
      />
    </span>
  </button>
</template>

<script setup>
import { defineEmits, defineProps, ref, computed, onMounted } from "vue";

// -- theme --
// 1. primary
// 2. primaryLine
// 3. secondary
// 4. secondaryLine
// 5. grayLine
// 6. excel
// 7. success
const props = defineProps({
  large: {
    type: Boolean,
    default: false,
  },
  small: {
    type: Boolean,
    default: false,
  },
  disabled: {
    type: Boolean,
    default: false,
  },
  loading: {
    type: Boolean,
    default: false,
  },
  theme: {
    type: String,
    default: "primary",
  },
});
const emits = defineEmits(["click"]);
const outlined = computed(() => {
  return props.theme.includes("Line");
});
const spinnerColor = computed(() => {
  if (outlined) {
    return props.theme.replace("Line", "").replace("primary", "coral");
  }
  if (props.theme === "white") {
    return "black";
  }
  return "white";
});
const onClick = () => {
  if (props.disabled || props.loading) {
    return;
  }
  emits("click");
};
</script>

<style lang="scss" scoped>
.p-btn {
  // all: unset;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  vertical-align: middle;
  padding: 0 12px;
  cursor: pointer;
  border-radius: 4px;
  background-color: $white;
  min-width: fit-content;
  @include hover-before;
}

.p-btn--primary {
  background-color: $primary;
  border: 1px solid $primary;
  color: $white;
  &:before {
    background-color: $primary800;
  }
  &:focus::before,
  &:hover::before {
    opacity: 1;
  }
}
.p-btn--primary-line {
  background-color: $white;
  border: 1px solid $primary;
  color: $primary;
}
.p-btn--secondary {
  background-color: $secondary;
  border: 1px solid $secondary;
  color: $white;
  &:before {
    background-color: $secondary800;
  }
  &:focus::before,
  &:hover::before {
    opacity: 1;
  }
}
.p-btn--secondary-line {
  background-color: $white;
  border: 1px solid $secondary;
  color: $secondary;
}
.p-btn--gray-line {
  background-color: $white;
  border: 1px solid $gray3;
  color: $gray8;
}
.p-btn--white-line {
  background-color: transparent;
  border: 1px solid $white;
  color: $white;
}
.p-btn--black-line {
  background-color: $white;
  border: 1px solid $black;
  color: $black;
}
.p-btn--black {
  background-color: $black;
  border: 1px solid $black;
  color: $white;
}
.p-btn--excel {
  background-color: $excelColor;
  border: 1px solid $excelColor;
  color: $white;
}
.p-btn--success {
  background-color: $success;
  border: 1px solid $success;
  color: $white;
}
.p-btn--transparent {
  background-color: transparent;
  border: 1px solid transparent;
  color: $gray8;
}
.p-btn__content {
  display: flex;
  align-items: center;
  justify-content: center;
  flex: 1 0 auto;
  position: relative;
}
.p-size--large {
  min-height: 48px;
  font-size: 16px;
  font-weight: 700;
}
.p-size--small {
  min-height: 32px;
  font-size: 14px;
  font-weight: 400;
}
.p-size--medium {
  min-height: 40px;
  font-size: 14px;
  font-weight: 400;
}
.p-btn--disabled {
  background-color: $gray2 !important;
  border: 1px solid $gray2 !important;
  color: $gray5 !important;
  cursor: not-allowed;
  &:before {
    all: unset;
    box-sizing: border-box;
  }
  &:focus::before,
  &:hover::before {
    opacity: 0;
  }
}
</style>
