<template>
  <v-dialog
    v-model="alert"
    max-width="374"
    persistent
    style="z-index: 10001"
    no-click-animation
  >
    <div class="border-box white" :class="textAlign">
      <div
        class="w-full d-flex flex-column justify-center px-4 py-6"
        style="min-height: 120px"
      >
        <b v-if="title" class="mb-2 text-body-1-bold">{{ title }}</b>
        <div
          class="text-body-2-regular"
          v-html="msg"
          :class="{ 'grey--text text--darken-2': Boolean(title) }"
          style="word-break: keep-all"
        ></div>
        <router-link
          v-if="csLink"
          target="_blank"
          :to="{
            name: 'auth-bridge',
            query: {
              next: csLinkRoute,
            },
          }"
          class="mt-5"
        >
          <v-btn color="blue" text>고객센터 바로가기</v-btn></router-link
        >
        <slot v-if="custom" />
      </div>

      <div style="border-top: 1px solid #e4e6ea">
        <v-btn
          color="coral"
          text
          class="w-full text-body-2-regular"
          @click="onSubmit"
          large
        >
          {{ btnText }}
        </v-btn>
      </div>
    </div>
  </v-dialog>
</template>

<script>
import { mapGetters } from "vuex";
import { webBaseUrl, isLocal } from "@/config";
export default {
  name: "PoinUiAlert",
  props: {
    alert: {
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
    csLink: {
      type: Boolean,
      default: false,
    },
    btnText: {
      type: String,
      default: "확인",
    },
    custom: {
      type: Boolean,
      default: false,
    },
    textAlign: {
      type: String,
      default: "text-center",
    },
  },
  computed: {
    ...mapGetters({
      domain: "campuses/getCampusDomain",
      roleCd: "members/getMemberRoleCd",
    }),
    csLinkRoute() {
      if (!this.domain || this.roleCd === "A") {
        return `${webBaseUrl}/help/ask`;
      }
      if (this.isLocal) {
        return this.$router.resolve({
          name: "campus-help-faq",
          params: this.$route.params,
        }).href;
      }
      const baseUrl = window.location.origin;
      return `${baseUrl}/help/faq`;
    },
  },
  methods: {
    onSubmit() {
      if (this.loading) {
        return;
      }
      this.$emit("submit");
    },
  },
};
</script>

<style></style>
