<template>
  <v-checkbox
    :input-value="value"
    @change="updateInput"
    color="coral"
    :off-icon="
      disabled ? 'mdi-minus-circle' : 'mdi-checkbox-blank-circle-outline'
    "
    on-icon="mdi-check-circle"
    :size="size"
    hide-details
    class="mt-0 pt-0"
    :readonly="readonly"
    :disabled="disabled"
  >
    <template v-slot:label>
      <slot />
    </template>
  </v-checkbox>
</template>

<script>
export default {
  name: "PoinUiCheckbox",
  props: {
    value: {
      type: Boolean,
      default: false,
    },
    size: {
      type: Number,
      default: 20,
    },
    readonly: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    updateInput(val) {
      this.$emit("input", val);
    },
  },
};
</script>

<style lang="scss" scoped>
::v-deep {
  .theme--light.v-icon.mdi-checkbox-blank-circle-outline {
    color: $gray3;
  }
  .theme--light.v-icon.mdi-minus-circle {
    color: $gray3 !important;
    border-radius: 50%;
  }
  .v-input--selection-controls__ripple {
    opacity: 0;
  }
}
</style>
