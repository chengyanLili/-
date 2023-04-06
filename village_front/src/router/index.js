import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    // {
    //   path: '/home',
    //   name: 'home',
    //   component: () => import('../views/home.vue')
    // },
    // 管理员的界面
    {
      path: '/',
      name: 'container',
      component: () => import('../views/container/container.vue'),
      children:[
        {
          path: '/home',
          name: 'populationFile',
          component: () => import('../views/villigerManage/populationFile.vue')
        },
            {
              path: 'familyTree',
              name: 'familyTree',
              component: () => import('../views/villigerManage/familyManage/familyTree.vue')
            },
            {
              path: 'editFamily',
              name: 'editFamily',
              component: () => import('../views/villigerManage/familyManage/editFamily.vue')
            },
            {
              path: 'familyList',
              name: 'familyList',
              component: () => import('../views/villigerManage/familyManage/familyList.vue')
            },
        {
          path: '/floatPerson',
          name: 'floatPerson',
          component: () => import('../views/villigerManage/floatPerson.vue')
        },
        {
          path: '/produce',
          name: 'produce',
          component: () => import('../views/committee/produce.vue')
        },
        {
          path: '/committee',
          name: 'committee',
          component: () => import('@/views/committee/committee.vue')
        },
        // 任务通知
        {
          path: '/taskNotice',
          name: 'taskNotice',
          component: () => import('@/views/affairsManage/taskNotice.vue')
        },
        // 信箱管理
        {
          path: '/messageBox',
          name: 'messageBox',
          component: () => import('@/views/messageBox/messageBox.vue')
        },
        // 困难户救助管理
        {
          path: '/difficultVillager',
          name: 'difficultVillager',
          component: () => import('@/views/difficultVillager/difficultVillager.vue')
        },
        // 土地变更管理
        {
          path: '/landChange',
          name: 'landChange',
          component: () => import('@/views/landChange/landChange.vue')
        }
      ]
    },

    // 普通村民的界面
    {
      path: '/villager',
      name: 'villager',
      component: () => import('../views/villagerLayout/Layout.vue'),
      children:[
        {
          path:'/villager/messageList',
          name:'messageList',
          component:() => import('../views/villager/messageList.vue')
        },
        {
          path:'/villager/myPolicy',
          name:'myPolicy',
          component:() => import('../views/villager/myPolicy.vue')
        },
        {
          path:'/villager/messageDetail',
          name:'messageDetail',
          component:() => import('../views/villager/messageDetail.vue')
        },
      ]
    },
   
    {
      path:'/login',
      name:'login',
      component:() => import('../views/login/login.vue')
  },
  {
    path:'/register',
    name:'register',
    component:() => import('../views/register/register.vue')
},
  
  ]
})

export default router
