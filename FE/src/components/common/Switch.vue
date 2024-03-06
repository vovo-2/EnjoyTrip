<template>
  <label class="switch" :class="{ disabled, readonly }">
    <input
      :id="`${_uid}-switch`"
      type="checkbox"
      :checked="value"
      @change="updateInput"
      :readonly="readonly"
      :disabled="disabled"
      :class="{ checked: value }"
    />
    <span class="slider round" :class="value ? color : bgColor"></span>
  </label>
</template>

<script>
export default {
  name: "PoinUiSwitch",
  props: {
    value: {
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
    color: {
      type: String,
      default: "success",
    },
    bgColor: {
      type: String,
      default: "gray",
    },
  },
  methods: {
    updateInput() {
      if (this.readonly || this.disabled) {
        return;
      }
      this.$emit("input", !this.value);
    },
  },
};
</script>

<style scoped lang="scss">
/* The switch - the box around the slider */
.switch {
  position: relative;
  display: inline-block;
  width: 44px;
  height: 24px;
}

/* Hide default HTML checkbox */
.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

/* The slider */
.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  -webkit-transition: 0.3s;
  transition: 0.3s;
}

.slider:before {
  position: absolute;
  content: "";
  height: 20px;
  width: 20px;
  left: 2px;
  bottom: 2px;
  background-color: $white;
  -webkit-transition: 0.3s;
  transition: 0.3s;
}

input:focus + .slider {
  box-shadow: 0 0 1px $gray5;
}

input.checked + .slider:before {
  -webkit-transform: translateX(20px);
  -ms-transform: translateX(20px);
  transform: translateX(20px);
}

.slider.round {
  border-radius: 24px;
}

.slider.round:before {
  border-radius: 50%;
}
.readonly .slider {
  cursor: default;
}
.disabled .slider {
  cursor: not-allowed;
}
</style>
