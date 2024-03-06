import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: () => import("@/views/Feature.vue"),
    },
    {
      path: "/login",
      name: "login",
      component: () => import("@/views/Login.vue"),
    },
    {
      path: "/attraction",
      name: "attraction",
      component: () => import("@/views/Attraction.vue"),
    },
    {
      path: "/board",
      component: () => import("@/views/board"),
      children: [
        {
          path: "",
          name: "board",
          component: () => import("@/views/board/Home.vue"),
        },
        {
          path: "write",
          name: "board-write",
          component: () => import("@/views/board/Write.vue"),
        },
        {
          path: "article/:articleNo",
          name: "article",
          component: () => import("@/views/board/Detail.vue"),
        },
      ],
    },
  ],
});
export default router;
