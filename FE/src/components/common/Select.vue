<template>
  <v-select
    class="my-select"
    :items="options"
    :value="value"
    style="height: 48px"
    :placeholder="placeholder"
    :menu-props="{
      bottom: true,
      offsetY: true,
      contentClass: 'p-select-options',
    }"
    @change="updateInput"
    :clearable="clearable"
    hide-details
    single-line
    outlined
    color="gray darken-1"
    :background-color="readonly ? 'gray lighten-3' : backgroundColor"
    :readonly="readonly"
    :disabled="disabled"
    item-color="black"
    ref="select"
    :no-data-text="noDataText"
  >
    <template v-slot:selection="data">
      <div class="text-body-2-regular black--text single-line">
        {{ data.item.title }}
      </div>
    </template>
  </v-select>
</template>
<script setup>
import { defineEmits, defineProps, ref, computed, onMounted } from "vue";
const props = defineProps({
  value: {
    type: [String, Number, Boolean],
    default: "",
  },
  options: {
    type: Array,
    default: () => [],
  },
  noDataText: {
    type: String,
    default: "데이터가 없습니다.",
  },
  placeholder: {
    type: String,
    default: "카테고리를 선택해주세요.",
  },
  dense: {
    type: Boolean,
    default: true,
  },
  backgroundColor: {
    type: String,
    default: "white",
  },
  readonly: {
    type: Boolean,
    default: false,
  },
  disabled: {
    type: Boolean,
    default: false,
  },
  clearable: {
    type: Boolean,
    default: false,
  },
  rules: {
    type: Array,
    default: [],
  },
  width: {
    type: [Number, String],
    default: "170px",
  },
});
const textOption = (data) => {
  const canvas = document.createElement("canvas");
  const context = canvas.getContext("2d");
  const textWidth = context.measureText(data.item.title).width + 14; //+14 -> padding-x

  let result = {
    text: data.item.title,
    width: "100%",
    hoverOn: false,
  };

  if (textWidth > props.width) {
    return {
      ...result,
      width: `${props.width - 28}px `,
      class: "single-line",
      hoverOn: true,
    };
  }
  return result;
};
const emits = defineEmits([
  "input",
  "blur",
  "focus",
  "keyup:enter",
  "click:claer",
  "update:selected",
]);
const inputValue = ref(props.value);
const updateInput = (val) => {
  inputValue.value = val;
  emits("update:selected", inputValue.value);
};
const onClear = () => {
  inputValue.value = "";
  emits("update:selected", inputValue.value);
  emits("click:clear");
};
</script>

<style lang="scss" scoped>
:deep(.v-input) {
  * {
    &:active,
    &:focus,
    &:focus-visible {
      outline: unset !important;
    }
    font-size: 14px;
    color: $black;
  }
}

:deep(.v-input__append-inner) {
  margin-top: 0 !important;
  align-self: center;
}

:deep(.v-select__slot) {
  display: flex;
  align-items: center;
  min-height: 40px;
}

:deep(.v-select__selections input) {
  &::placeholder {
    color: $gray6;
    font-size: 14px;
  }
}

:deep(fieldset) {
  border-color: $gray3;
  outline: none;
}

:deep(.v-select__selection--comma) {
  all: unset;
  box-sizing: border-box;
}

:deep(.v-input__icon--clear) {
  width: 20px;
  min-width: 20px !important;
  height: 20px;
}

:deep(.v-icon__component) {
  width: 20px;
  height: 20px;
}
:deep(.v-select--is-menu-active .v-input__append-inner i) {
  transform: rotate(180deg);
}
</style>

<style lang="scss">
.p-select-options {
  * {
    box-sizing: border-box;
  }
  box-sizing: border-box;
  border: 1px solid $gray3;
  box-shadow: unset;
  .v-select-list {
    padding: 12px 0;
  }
  .v-list-item {
    min-height: 40px;
    transition: 0.3s cubic-bezier(0.25, 0.8, 0.5, 1);
    padding: 0 12px;
    &:hover {
      background-color: $gray1;
    }
    &::before {
      all: unset;
      box-sizing: border-box;
    }
    .v-list-item__title {
      color: $black;
      font-size: 14px;
    }
  }
}
</style>
