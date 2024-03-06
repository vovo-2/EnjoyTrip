<template lang="html">
  <div class="border-box">
    <section class="p-container feature__container my-10">
      <div class="d-flex flex-column align-center">
        <p class="top-title-h1">
          당신의 여행을<br />
          즐겁게
        </p>
        <h-btn @click="goToRegister" theme="secondary" class="mt-5 px-5">
          시작하기
        </h-btn>
      </div>
      <img src="@/assets/main.jpg" class="main-top-image rounded-lg lazy" />
    </section>

    <section
      class="local-padding-y"
      v-for="(feature, index) in features"
      :key="index"
      :style="{ background: feature.bgColor }"
    >
      <div class="p-container d-flex flex-column align-center">
        <div class="d-flex my-10 flex-column align-center">
          <p class="text-main-title" v-html="feature.title"></p>
        </div>
        <div
          class="d-flex flex-column align-lg-center my-10"
          v-for="(item, index) in feature.items"
          :key="index"
          :class="item.reverse ? 'flex-lg-row-reverse' : 'flex-lg-row'"
        >
          <img
            class="image__wrapper"
            src="@/assets/default1.jpeg"
            v-if="item.image"
          />
          <img class="image__wrapper" src="@/assets/default2.jpeg" v-else />
          <div
            class="text__wrapper d-flex flex-column align-center align-lg-start justify-lg-center px-lg-15 px-0"
          >
            <p
              class="sub--title text-center text-lg-left mb-5 mb-lg-8"
              v-html="item.subtitle"
            ></p>
            <div v-if="item.isList" class="pr-0 pr-lg-10" style="width: 100%">
              <ul>
                <li
                  v-for="(list, index) in item.lists"
                  :key="index"
                  @click="toggle(list)"
                  class="py-4 w-full cursor-pointer"
                >
                  <p
                    class="d-flex justify-space-between list-title expand-title"
                    :class="{ highlight: list.active }"
                  >
                    {{ list.listTitle
                    }}<v-icon :color="iconColor(list.active)">{{
                      list.active ? "mdi-minus" : "mdi-plus"
                    }}</v-icon>
                  </p>
                  <div style="height: 16px" v-show="list.active">&nbsp;</div>
                  <v-expand-transition>
                    <div
                      v-show="list.active"
                      v-html="list.listContent"
                      class="list-description"
                    ></div>
                  </v-expand-transition>
                </li>
              </ul>
            </div>
            <div v-else class="list-title">
              <p
                class="text-center text-lg-start text-h5-regular"
                v-html="item.description"
              ></p>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import Header from "@/components/layouts/Header.vue";
import { useRouter } from "vue-router";
import { ref } from "vue";
const router = useRouter();
const features = ref([
  {
    title: `당신만을 위한 관광지를 검색하고 <br /> 정보를 공유하세요!`,
    bgColor: "#FFFAF2",
    items: [
      {
        subtitle: `당신이 가고 싶은 관광지 정보를 확인해보세요`,
        image: true,
        reverse: true,
        isList: true,
        lists: [
          {
            listTitle: "관광지 정보",
            listContent: "관광지 정보를 지도에서 표시해줍니다.",
            active: false,
          },
          {
            listTitle: "가고싶은 관광지 검색",
            listContent:
              "원하는 지역, 조건에 맞는 관광지를 검색할 수 있습니다.",
            active: false,
          },
        ],
      },
      {
        subtitle: `게시판을 통해 다른 사람들과 교류하세요`,
        image: false,
        isList: true,
        reverse: false,
        lists: [
          {
            listTitle: "게시판",
            listContent: "자유로운 글쓰기와 조회가능",
            active: false,
          },
          {
            listTitle: "글쓰기",
            listContent: "이미지 첨부를 통해 다른 사람들과 의견을 공유하세요",
            active: false,
          },
        ],
      },
    ],
  },
]);
const iconColor = (active) => {
  if (active) {
    return "#f55e42";
  }
  return "black";
};
const toggle = (toggleList) => {
  features.value.map((feature) =>
    feature.items.map((item) =>
      item.lists?.map((list) => {
        if (toggleList.listTitle === list.listTitle) {
          list.active = !list.active;
        } else {
          list.active = false;
        }
      })
    )
  );
};

const goToRegister = () => {
  router.push({
    name: "login",
  });
};
</script>

<style scoped lang="scss">
@import url("https://fonts.googleapis.com/css2?family=Song+Myung&display=swap");

.feature__container {
  display: flex;
  align-items: center;
  gap: 40px;
  justify-content: center;
}
.top-title {
  font-size: 52px;
  font-weight: bold;
  font-stretch: normal;
  line-height: 1.3;
  letter-spacing: 0;
  @media (max-width: $mdBreakPoint) {
    font-size: 48px;
  }
  @media (max-width: $smBreakPoint) {
    font-size: 32px;
  }
}
.image__wrapper {
  width: 50%;
  @media (max-width: $lgBreakPoint) {
    width: 100%;
    max-width: 480px;
    margin-bottom: 20px;
  }
}
.add-img {
  width: 100%;
  @media (max-width: $lgBreakPoint) {
    max-width: 480px;
  }
}
.text__wrapper {
  width: 50%;
  text-align: start;
  .sub--title {
    font-size: 24px;
    font-weight: bold;
  }
  .list-title {
    font-size: 18px;
    font-weight: 400;
    &.expand-title:hover {
      color: $primary !important;
      ::deep {
        .v-icon {
          color: $primary !important;
        }
      }
    }
  }
  @media (max-width: $lgBreakPoint) {
    width: 100%;
    max-width: 480px;
  }
}
.main-top-image {
  width: 52.5%;
  @media (max-width: $lgBreakPoint) {
    width: 100%;
    max-width: 600px;
  }
}
.top-tag {
  font-family: "Song Myung", serif;
  font-size: 52px;
  font-weight: 400;
  font-stretch: normal;
  line-height: 1.3;
  letter-spacing: 0;
  @media (max-width: $lgBreakPoint) {
    font-size: 48px;
  }
  @media (max-width: $lgBreakPoint) {
    font-size: 40px;
  }
}
.text-main-title {
  font-size: 32px;
  font-weight: bold;
  font-stretch: normal;
  font-style: normal;
  line-height: 1.5;
  letter-spacing: normal;
  text-align: center;
  color: $black;
  @media (max-width: $lgBreakPoint) {
    max-width: 480px;
  }
}
.list-description {
  font-weight: 400;
  font-size: 16px;
  color: $gray7;
  overflow-wrap: normal;
}
.top-title-h1 {
  font-size: 52px;
  font-weight: bold;
  font-stretch: normal;
  line-height: 1.3;
  letter-spacing: 0;
  @media (max-width: $mdBreakPoint) {
    font-size: 48px;
  }
  @media (max-width: $smBreakPoint) {
    font-size: 40px;
  }
}
</style>
