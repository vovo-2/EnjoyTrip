<template>
  <v-dialog
    v-model="confirm"
    :max-width="maxWidth"
    persistent
    no-click-animation
    content-class="white"
  >
    <slot v-if="$scopedSlots['custom']" name="custom" />
    <div class="border-box text-center" v-else>
      <div
        class="w-full d-flex flex-column justify-center px-4 py-10"
        style="min-height: 120px"
      >
        <h5 v-if="title" class="text-h5-medium mb-3">{{ title }}</h5>
        <div
          class="text-body-2-regular keep-all"
          v-html="msg"
          :class="{ 'gray--text text--darken-3': Boolean(title) }"
        ></div>
      </div>
    </div>
    <div class="confirm-btn__wrap border-box">
      <v-btn
        color="gray darken-3"
        class="text-body-2-regular flex-fill"
        text
        @click.stop="onCancel"
        :disabled="loading"
        large
      >
        {{ btnTxtCancel }}
      </v-btn>
      <div class="btn-divider"></div>
      <v-btn
        color="primary"
        class="text-body-2-regular flex-fill"
        text
        @click.stop="onSubmit"
        :loading="loading"
        large
      >
        {{ btnTxtConfirm }}
      </v-btn>
    </div>
  </v-dialog>
</template>

<script>
export default {
  name: "PoinUiConfirm",
  props: {
    confirm: {
      type: Boolean,
      default: false,
    },
    title: {
      type: String,
    },
    msg: {
      type: String,
      default: "",
    },
    loading: {
      type: Boolean,
      default: false,
    },
    btnTxtConfirm: {
      type: String,
      default: "확인",
    },
    btnTxtCancel: {
      type: String,
      default: "취소",
    },
    maxWidth: {
      type: [Number, String],
      default: "360",
    },
  },
  methods: {
    onCancel() {
      this.$emit("cancel");
    },
    onSubmit() {
      if (this.loading) {
        return;
      }
      this.$emit("submit");
    },
  },
};
</script>

<style lang="scss" scoped>
.confirm-btn__wrap {
  width: 100%;
  border-top: 1px solid $gray3;
  display: grid;
  grid-template-columns: 1fr 1px 1fr;
  .btn-divider {
    width: 1px;
    height: 100%;
    background-color: $gray3;
  }
}
</style>
