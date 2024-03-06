<template>
  <div class="poin-input">
    <label
      v-if="props.label"
      :for="_uid"
      class="font-size-14 font-weight-medium gray--text text--darken-3"
      >{{ props.label
      }}<span class="error--text ml-1" v-if="props.required">*</span></label
    >
    <div
      class="poin-input-group"
      :style="`height: ${height}px`"
      :class="{
        filled,
        invalid,
        disabled,
        readonly,
        'success-line': success,
        'mt-2': label,
      }"
    >
      <div class="poin-input-prefix" aria-hidden="true" v-if="props.prefix">
        {{ props.prefix }}
      </div>
      <input
        :type="inputType"
        class="poin-input"
        :id="`${_uid}-input`"
        :name="props.name"
        :placeholder="props.placeholder"
        :value="inputValue"
        :min="props.min"
        :max="props.max"
        :maxlength="props.maxlength"
        :minlength="props.minlength"
        :readonly="props.readonly"
        :disabled="props.disabled"
        :required="props.required"
        @input="updateInput"
        @focus="onFocus"
        @blur="onBlur"
        @keyup.enter="enterAction"
        :class="{ 'pl-1': prefix, 'pr-1': suffix, 'p-input--dense': dense }"
      />
      <div class="poin-input-suffix" aria-hidden="true" v-if="suffix">
        {{ props.suffix }}
      </div>
      <div
        class="poin-input-suffix"
        aria-hidden="true"
        v-if="clearable && value"
      ></div>
      <div
        class="poin-input-suffix"
        aria-hidden="true"
        v-if="props.type === 'password'"
      >
        <v-icon
          color="gray"
          size="20"
          @click.stop="togglePassword"
          class="cursor-pointer"
          >{{
            inputType === "password" ? "mdi-eye-outline" : "mdi-eye-off-outline"
          }}</v-icon
        >
      </div>
      <div
        class="poin-input-suffix"
        aria-hidden="true"
        v-if="$slots.default == 'append'"
      >
        <slot name="append" />
      </div>
    </div>
    <div class="d-flex" v-if="!props.hideDetails">
      <span
        class="error--text text-detail-1-regular error-msg"
        v-show="invalid"
        >{{ props.errorMsg }}</span
      >
      <span
        class="success--text text-detail-1-regular error-msg"
        v-show="!invalid && props.success"
        >{{ props.successMsg }}</span
      >
      <span
        v-if="props.countLength && props.value"
        class="ml-auto text-end text-detail-1-regular gray--text text--darken-3"
      >
        {{ props.value.length }}/{{ props.maxlength || 50 }}
      </span>
    </div>
  </div>
</template>

<script setup>
import { defineEmits, defineProps, ref, computed, onMounted } from "vue";
const props = defineProps({
  type: {
    type: String,
    default: "text",
  },
  value: {
    type: [String, Number],
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
  dense: {
    type: Boolean,
    default: false,
  },
  clearable: {
    type: Boolean,
    default: false,
  },
  filled: {
    type: Boolean,
    default: false,
  },
  min: {
    type: String,
  },
  max: {
    type: String,
  },
});

const emits = defineEmits([
  "input",
  "blur",
  "focus",
  "keyup:enter",
  "click:claer",
  "update:value",
]);
const inputType = ref("");
const inputValue = ref(props.value);

const updateInput = (e) => {
  inputValue.value = e.target.value;
  emits("update:value", inputValue.value);
};

function onBlur() {
  emits("blur");
}
function onFocus() {
  emits("focus");
}
function enterAction() {
  emits("keyup:enter");
}
function togglePassword() {
  if (inputType.value === "password") {
    inputType.value = "text";
  } else if (inputType.value === "text") {
    inputType.value = "password";
  }
}
function onClear() {
  emits("input", "");
  emits("click:clear");
}
const invalid = computed(() => {
  return props.errorMsg;
});
const height = computed(() => {
  return props.dense ? 40 : 48;
});
onMounted(() => {
  inputType.value = props.type;
});
</script>

<style scoped lang="scss">
* {
  box-sizing: border-box;
  font-size: 14px;
}
.poin-input--error {
  border-color: $error;
}
/* .poin-input:focus-within {
  .error-msg {
    display: none;
  }
} */
.poin-input-group {
  align-items: center;
  background-color: $white;
  display: flex;
  padding: 0;
  border: 1px solid $gray3;
  border-radius: 4px;
  outline: none;
  &.filled {
    background-color: $gray1;
    border: 1px solid $gray1;
    input {
      &:-webkit-autofill,
      &:-webkit-autofill:hover,
      &:-webkit-autofill:focus {
        border: none;
        outline: none;
        background-color: $gray1;
        box-shadow: 0 0 0px 1000px $gray1 inset;
        -webkit-box-shadow: 0 0 0px 1000px $gray1 inset;
        transition: background-color 5000s ease-in-out 0s;
      }
    }
  }
  &.invalid {
    border-color: $error;
  }
  &:focus-within {
    border-color: $gray6;
  }
  &.success-line {
    border-color: $success;
  }
  &.readonly {
    background-color: $gray2;
    border: 1px solid $gray3;
    input {
      &:-webkit-autofill,
      &:-webkit-autofill:hover,
      &:-webkit-autofill:focus {
        border: none;
        outline: none;
        background-color: $gray2;
        box-shadow: 0 0 0px 1000px $gray2 inset;
        -webkit-box-shadow: 0 0 0px 1000px $gray2 inset;
        transition: background-color 5000s ease-in-out 0s;
      }
    }
  }
  &.disabled {
    background-color: $gray2;
    border: 1px solid $gray3;
    color: $gray5;
    input {
      &:-webkit-autofill,
      &:-webkit-autofill:hover,
      &:-webkit-autofill:focus {
        border: none;
        outline: none;
        background-color: $gray2;
        box-shadow: 0 0 0px 1000px $gray2 inset;
        -webkit-box-shadow: 0 0 0px 1000px $gray2 inset;
        transition: background-color 5000s ease-in-out 0s;
      }
    }
  }
  input {
    border: 0;
    height: 100%;
    margin-top: 0;
    min-width: 0;
    width: 100%;
    letter-spacing: -1px;
    color: $black;
    padding: 0 8px;
    font-size: 14px;
    height: 100%;
    border-radius: 4px;
    &::placeholder {
      color: $gray6;
      font-size: 14px;
    }
    &:focus {
      outline: none;
    }
    &:focus-visible {
      outline: none;
    }
    &:disabled {
      cursor: not-allowed;
    }
    &:-webkit-autofill,
    &:-webkit-autofill:hover,
    &:-webkit-autofill:focus {
      border: none;
      outline: none;
      background-color: $white;
      box-shadow: 0 0 0px 1000px $white inset;
      -webkit-box-shadow: 0 0 0px 1000px $white inset;
      transition: background-color 5000s ease-in-out 0s;
    }
    &.p-input--dense {
      font-size: 14px;
      &::placeholder {
        font-size: 14px;
      }
    }
  }
}
.poin-input-group--error {
  border-color: $error;
  border-style: solid;
}

.poin-input-prefix,
.poin-input-suffix {
  color: $gray6;
  line-height: 0;
  height: 100%;
  display: flex;
  align-items: center;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  white-space: nowrap;
  &.poin-input-prefix {
    padding-left: 8px;
  }
  &.poin-input-suffix {
    padding-right: 8px;
  }
}
input[type="datetime-local"] {
  background: transparent
    url("https://file.poincampus.com/assets/icons/Calendar/Black.svg")
    calc(100% - 8px) 50% no-repeat;
}
input[type="datetime-local"]::-webkit-inner-spin-button {
  display: none;
}
input[type="datetime-local"]::-webkit-calendar-picker-indicator {
  opacity: 0;
}
input[type="date"] {
  background: transparent
    url("https://file.poincampus.com/assets/icons/Calendar/Black.svg")
    calc(100% - 8px) 50% no-repeat;
}
input[type="date"]::-webkit-inner-spin-button {
  display: none;
}
input[type="date"]::-webkit-calendar-picker-indicator {
  opacity: 0;
}
input[type="month"] {
  background: transparent
    url("https://file.poincampus.com/assets/icons/Calendar/Black.svg")
    calc(100% - 8px) 50% no-repeat;
}
input[type="month"]::-webkit-inner-spin-button {
  display: none;
}
input[type="month"]::-webkit-calendar-picker-indicator {
  opacity: 0;
}
input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
}

/* 파이어폭스에서의 초기화 방법 */
input[type="number"] {
  -moz-appearance: textfield;
}
</style>
