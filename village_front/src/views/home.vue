<script setup>
import { reactive,ref } from 'vue';
import router from '../router';
  const data = reactive({
    navMenu : [
      { id:1,
        label:'首页',
        url:'/front/frontHome'
      },
      { id:2,
        label:'乡村干部',
        url:'/front/cadre'
      },
      { id:3,
        label:'信息公开',
        url:'/front/publicInfo'
      },
      { id:4,
        label:'走进荒田',
        url:''
      },
      { id:5,
        label:'登录',
        url:''
      }
    ],
    policyList:[
    {
        id:1,
        time:'2022-1-12',
        theme:'财政部农业农村部发布2022年重点强农惠农政策',
        content:'年中央财政信开展粮食耕种原则上县域内补贴标准应统一。'
      },
      {
        id:2,
        time:'2022-1-12',
        theme:'财政部 农业农村部发布2022年重点强农惠农政策',
        content:'年中央财政信开展粮食耕种原则上县域内补贴标准应统一。'
      },
      {
        id:3,
        time:'2022-1-12',
        theme:'财政部 农业农村部发布2022年重点强农惠农政策',
        content:'年中央财政信开展粮食耕种原则上县域内补贴标准应统一。'
      }
    ],
    currentIndex:0
  })

 const clickMenu = function(menu){
  data.currentIndex = menu.id
  router.push(menu.url)
 }

</script>

<template>
  <div class="head"></div>
  <div class="nav">
    <ul>
       <li v-for="menu in data.navMenu" :key="menu.url" :class="{active:data.currentIndex === menu.id}" @click="clickMenu(menu)">{{menu.label}}</li>
    </ul>
    <div class="main">
      <router-view></router-view>
    </div>
    
  </div>
  
</template>
<style lang="less" scoped>
body{
  background-color: #ebecef;
}

  .head{
    background-image: url('../assets/img/village.jpeg');
    background-repeat: no-repeat;
    // background-size: 100% 100%;
    width: 100%;
    height: 80px;
  }
  .nav{
    background-color: #79bbff;
    width: 100%;
    height: 40px;
    ul{
      display: flex;
      justify-content: center;
      align-items: center;
      list-style: none;
      color: #fff;
      li{
        margin: 8px 20px;
        cursor:pointer;
      }
    }
  }
.active{
  color: black;
}
.main{
  background-color: #ffffffeb;
  margin: 10px auto 0 auto;
  width: 80%;
  height: 100%;
}

</style>
