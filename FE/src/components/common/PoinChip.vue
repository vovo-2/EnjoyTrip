<!-- default: grayLine -->
<template>
  <div
    class="p-chip border-box"
    :class="{
      'p-chip--large': large,
      'p-chip--small': small,
      'p-chip--primary': theme === 'primary',
      'p-chip--primary-line': theme === 'primaryLine',
      'p-chip--secondary': theme === 'secondary',
      'p-chip--secondary-line': theme === 'secondaryLine',
      'p-chip--error': theme === 'error',
      'p-chip--error-line': theme === 'errorLine',
      'p-chip--success': theme === 'success',
      'p-chip--success-line': theme === 'successLine',
      'p-chip--disabled': disabled,
    }"
    @click="$emit('click')"
  >
    <span class="p-chip--content">
      <slot></slot>
      <slot name="icon" v-if="iconData">
        <img :src="iconData" class="ml-2" />
      </slot>
      <slot name="close" v-if="onClose">
        <v-icon @click="$emit('close')" class="ml-2" :color="closeIconColor"
          >mdi-close-circle</v-icon
        >
      </slot>
    </span>
  </div>
</template>

<script>
export default {
  name: "PoinUiChip",
  props: {
    theme: {
      type: String,
      default: "grayLine",
    },
    iconData: {
      type: String,
      default: "",
    },
    onClose: {
      type: Boolean,
      default: false,
    },
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
  },
  computed: {
    closeIconColor() {
      switch (this.theme) {
        case "primaryLine":
          return "coral";
        case "secondaryLine":
          return "darkGreen";
        case "successLine":
          return "success";
        case "errorLine":
          return "error";
        default:
          return "white";
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.p-chip {
  border-radius: 100px;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  min-width: fit-content;
  min-height: 32px;
  cursor: default;
  background-color: $white;
  @include hover-before;
  font-weight: 500;
  font-size: 12px;
  background-color: $white;
  border: 1px solid $gray3;
  color: $gray8;
  padding: 8px 16px;
}
.p-chip--disabled {
  pointer-events: none;
  border-radius: 60px;
}
.p-chip--primary {
  background-color: $primary;
  border: 1px solid $primary;
  color: $white;
}
.p-chip--primary-line {
  border: 1px solid $primary;
  background-color: $white;
  color: $primary;
}
.p-chip--secondary {
  background-color: $secondary;
  border: 1px solid $secondary;
  color: $white;
}
.p-chip--secondary-line {
  border: 1px solid $secondary;
  background-color: $white;
  color: $secondary;
}
.p-chip--error {
  background-color: $error;
  border: 1px solid $error;
  color: $white;
}
.p-chip--error-line {
  border: 1px solid $error;
  background-color: $white;
  color: $error;
}
.p-chip--success {
  background-color: $success;
  border: 1px solid $success;
  color: $white;
}
.p-chip--success-line {
  border: 1px solid $success;
  background-color: $white;
  color: $success;
}
.p-chip--content {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.p-chip--large {
  min-height: 40px;
  font-weight: 500;
  font-size: 14px;
  padding: 8px 16px;
}
.p-chip--small {
  min-height: 24px;
  font-weight: 500;
  font-size: 10px;
  padding: 4px 8px;
  line-height: 14px;
}
</style>
