<script setup>
import { reactive,computed } from 'vue';
import router from '../router/index.js'
    const data = reactive({
  menu: [
    {
      id:1,
      lable: '村民信息管理',
      icon: 'User',
      children:[
        {
          path: '/populationFile',
          name: 'populationFile',
          lable: '人口档案管理',
          url: 'populationFile/populationFile',
        },
        {
          path: '/familyList',
          name: 'familyList',
          lable: '户籍管理',
          url: 'familyList/familyList',
        },
        {
          path: '/floatPerson',
          name: 'floatPerson',
          lable: '流动人口管理',
          url: 'floatPerson/floatPerson',
        }
      ]
    },
    {
         path: '/committee',
          name: 'committee',
          lable: '村委会人员管理',
          icon: 'Menu',
          url: 'committee/committee',
    },

    {  path: '/difficultVillager',
      name: 'difficultVillager',
      lable: '困难户救助管理',
      icon: 'Stamp',
      url: 'difficultVillager/difficultVillager',
    },
    {
      id:3,
      lable: '村务管理',
      icon: 'Suitcase',
      children:[
        {
          path: '/taskNotice',
          name: 'taskNotice',
          lable: '任务通知',
          url: 'taskNotice/taskNotice',
        },
        {
          path: '/landChange',
          name: 'landChange',
          lable: '土地变更管理',
          url: 'landChange/landChange',
        }
      ]
    },
    {  path: '/messageBox',
      name: 'messageBox',
      lable: '信箱管理',
      icon: 'PhoneFilled',
      url: 'messageBox/messageBox',
    },
    {  path: '/statistic',
      name: 'statistic',
      lable: '数据统计',
      icon: 'Histogram',
      url: 'statistic/statistic',
    }
  ]
})

        let noChildren = computed(() => {
            return data.menu.filter(item => !item.children)
        })
       let hasChildren =  computed(() => {
            let hasChildren = data.menu.filter(item => item.children)
          return hasChildren
        })

       function clickMenu(item) {
            router.push({
                name:item.name
            })
        }
       function handleOpen(key, keyPath) {
}
       function handleClose(key, keyPath) {

}
      function toChildren(item){
        router.push({
          name:item.name
        })
      }
   
</script>

<template>
  <div>
    <el-menu class="el-menu-vertical-demo" default-active="2" style="height:100vh"
      @open="handleOpen" 
        @close="handleClose" >
      <h2>村务管理系统</h2>
      <div class="info">
        <div class="avatar">
          <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
        </div>
        <ul class="name">
          <li>张翠花</li>
          <li><el-icon>
              <CaretRight />
            </el-icon>系统管理员</li>
        </ul>
      </div>

      <el-sub-menu v-for="item in hasChildren" :index="item.id+''" :key="item.path">
        <template #title>
          <el-icon>
            <component :is="item.icon"/>
          </el-icon>
          <span>{{ item.lable }}</span>
        </template>
        <el-menu-item-group v-for="(item) in item.children" :key="item.path">
                <el-menu-item :index="item.path"  @click="toChildren(item)">{{ item.lable }}</el-menu-item>
            </el-menu-item-group>
      </el-sub-menu>

      <el-menu-item  @click="clickMenu(item)" 
        v-for="item in noChildren" :index="item.path + ''" :key="item.path">
        <el-icon>
          <component :is="item.icon"/>
        </el-icon>
        <span>{{ item.lable }}</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<style lang="less">
h2 {
  padding: 1.25rem 0 0 1.25rem;
  color: #73767a;
}

.info {
  margin-top: 20px;
  width: 100%;
  height: 80px;
  background-color: #ecf5ff;
  display: flex;
  align-items: center;

  .avatar {
    margin-left: 20px;
  }

  .name {
    li {
      list-style: none;
    }

    display: flex;
    flex-direction: column;
    padding: 8px;

    li:first-child {
      font-size: 13px;
      color: #73767a;
    }

    li:nth-child(2) {
      font-size: 14px;
      color: #409EFF
    }
  }
}
</style>