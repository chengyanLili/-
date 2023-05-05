import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "front",
      component: () => import("../views/home.vue"),
      children: [
        {
          path: "/front/frontHome",
          name: "frontHome",
          component: () => import("../views/front/frontHome.vue"),
        },
        {
          path: "/front/cadre",
          name: "cadre",
          component: () => import("../views/front/cadres.vue"),
        },
        {
          path: "/front/publicInfo",
          name: "publicInfo",
          component: () => import("../views/front/publicInfo.vue"),
        },
        {
          path: "/front/readContent",
          name: "readConten",
          component: () => import("../views/front/readContent.vue"),
        },
        {
          path: "/front/message",
          name: "message",
          component: () => import("../views/front/messageList.vue"),
        },
        {
          path: "/front/myVoting",
          name: "myVoting",
          component: () => import("../views/front/myVoting.vue"),
        },
        {
          path: "/front/candidateList",
          name: "candidateList",
          component: () => import("../views/front/candidateList.vue"),
        },
        {
          path: "/front/myself",
          name: "myself",
          component: () => import("../views/front/myself.vue"),
        },
      ],
    },

    // 村干部界面
    {
      path: "/populationFile",
      name: "container",
      component: () => import("../views/container/container.vue"),
      children: [
        {
          path: "/populationFile",
          name: "populationFile",
          component: () => import("../views/villigerManage/populationFile.vue"),
        },
        {
          path: "/users",
          name: "users",
          component: () => import("../views/users/users.vue"),
        },
        {
          path: "/self",
          name: "self",
          component: () => import("../views/self/self.vue"),
        },
        {
          path: "editFamily",
          name: "editFamily",
          component: () =>
            import("../views/villigerManage/familyManage/editFamily.vue"),
        },
        // 数据统计
        {
          path: "statistics",
          name: "statistics",
          component: () => import("../views/statistics/statistics.vue"),
        },
        {
          path: "familyList",
          name: "familyList",
          component: () =>
            import("../views/villigerManage/familyManage/familyList.vue"),
        },
        {
          path: "/floatPerson",
          name: "floatPerson",
          component: () => import("../views/villigerManage/floatPerson.vue"),
        },
        {
          path: "/voting",
          name: "voting",
          component: () => import("../views/voting/voting.vue"),
        },
        {
          path: "/candidate",
          name: "candidate",
          component: () => import("../views/voting/candidate.vue"),
        },
        {
          path: "/committee",
          name: "committee",
          component: () => import("@/views/committee/committee.vue"),
        },
        // 任务通知
        {
          path: "/taskNotice",
          name: "taskNotice",
          component: () => import("@/views/affairsManage/taskNotice.vue"),
        },
        // 信箱管理
        {
          path: "/messageBox",
          name: "messageBox",
          component: () => import("@/views/messageBox/messageBox.vue"),
        },
        // 困难户救助管理
        {
          path: "/difficultVillager",
          name: "difficultVillager",
          component: () =>
            import("@/views/difficultVillager/difficultVillager.vue"),
        },
        // 土地变更管理
        {
          path: "/landChange",
          name: "landChange",
          component: () => import("@/views/landChange/landChange.vue"),
        },
      ],
    },

    {
      path: "/login",
      name: "login",
      component: () => import("../views/login/login.vue"),
    },
    {
      path: "/register",
      name: "register",
      component: () => import("../views/register/register.vue"),
    },
  ],
});

export default router;
