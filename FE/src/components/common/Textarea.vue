<template>
  <div class="poin-input">
    <label
      v-if="label"
      :for="`${_uid}-textarea`"
      class="font-size-14 font-weight-medium gray--text text--darken-3"
      >{{ label }}</label
    >
    <textarea
      class="poin-textarea"
      :id="`${_uid}-textarea`"
      :name="name"
      :placeholder="placeholder"
      :value="value"
      :maxlength="maxlength"
      :minlength="minlength"
      :readonly="readonly"
      :disabled="disabled"
      :required="required"
      @input="updateInput"
      @change="changeInput"
      @blur="blur"
      @focus="focus"
      @keyup.enter="enterAction"
      :style="`height: ${height}px`"
      :class="{
        invalid,
        readonly,
        disabled,
        'success-line': success,
        'mt-2': label,
      }"
    />
    <div class="d-flex" v-if="!hideDetails">
      <span
        class="error--text text-detail-1-regular error-msg"
        v-show="invalid"
        >{{ errorMsg }}</span
      >
      <span
        class="success--text text-detail-1-regular error-msg"
        v-show="!invalid && success"
        >{{ successMsg }}</span
      >
      <span
        v-if="countLength && value"
        class="ml-auto text-end text-detail-1-regular gray--text text--darken-3"
      >
        {{ value.length }}/{{ maxlength || 50 }}
      </span>
    </div>
  </div>
</template>

<script>
export default {
  name: "PoinUiTextarea",
  props: {
    value: {
      type: String,
      default: "",
    },
    placeholder: {
      type: String,
      default: "",
    },
    errorMsg: {
      type: String,
      default: "",
    },
    maxlength: {
      type: [Number, String],
      required: false,
    },
    minlength: {
      type: [Number, String],
      required: false,
    },
    countLength: {
      type: Boolean,
      default: false,
    },
    readonly: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    required: {
      type: Boolean,
      default: false,
    },
    prefix: {
      type: String,
      required: false,
    },
    suffix: {
      type: String,
      required: false,
    },
    label: {
      type: String,
      required: false,
    },
    name: {
      type: String,
      required: false,
    },
    hideDetails: {
      type: Boolean,
      default: false,
    },
    success: {
      type: Boolean,
      default: false,
    },
    successMsg: {
      type: String,
      default: "",
    },
    height: {
      type: Number,
      default: 240,
    },
  },
  computed: {
    invalid() {
      return this.errorMsg;
    },
  },
  methods: {
    updateInput(e) {
      this.$emit("input", e.target.value.trim());
    },
    changeInput(e) {
      this.$emit("change", e.target.value.trim());
    },
    blur(e) {
      this.$emit("blur", e.target.value);
    },
    focus(e) {
      this.$emit("focus", e.target.value);
    },
    enterAction() {
      this.$emit("keyup:enter");
    },
  },
};
</script>

<style scoped lang="scss">
* {
  box-sizing: border-box;
  font-size: 14px;
}
textarea {
  width: 100%;
  padding: 12px;
  border-radius: 4px;
  border: 1px solid $gray3;
  border-radius: 4px;
  outline: none;
  background-color: $white;
  box-sizing: border-box;
  font-size: 14px;
  color: $black;
  letter-spacing: -1px;

  &::placeholder {
    color: $gray6;
    font-size: 14px;
  }
  &.invalid {
    border-color: $error;
  }
  &:focus {
    border-color: $gray6;
    outline: none;
  }
  &:focus-visible {
    outline: none;
  }
  &.success-line {
    border-color: $success;
  }
  &.readonly {
    background-color: $gray2;
  }
  &.disabled {
    background-color: $gray2;
    color: $gray5;
    cursor: not-allowed;
  }
}
</style>
