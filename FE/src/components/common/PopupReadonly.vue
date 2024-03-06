<template>
  <v-dialog
    :max-width="isMobile ? 960 : 360"
    :fullscreen="isMobile"
    v-model="dialog"
    persistent
    no-click-animation
  >
    <div class="white w-full fill-height px-6 py-10 border-box">
      <div class="w-full d-flex align-center justify-space-between mb-5">
        <h2 class="popup-title single-line">
          {{ title }}
        </h2>
        <p-icon-btn icon="Close" @click="onClose" />
      </div>
      <div class="popup__wrap">
        <slot />
      </div>
    </div>
  </v-dialog>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  name: "PopupReadonly",
  props: {
    dialog: {
      type: Boolean,
      default: false,
    },
    title: {
      type: String,
      default: "",
    },
  },
  computed: {
    ...mapGetters({
      isMobile: "common/isMobile",
    }),
  },
  methods: {
    onClose() {
      this.$emit("close");
    },
  },
};
</script>

<style lang="scss" scoped>
::v-deep {
  .popup-title {
    font-size: 28px;
    height: 40px;
    font-weight: bold;
  }
  .popup__wrap {
    height: 440px;
    overflow-y: auto;
    @media (max-width: $mdBreakPoint) {
      height: calc(100vh - 160px);
    }
  }
}
</style>
