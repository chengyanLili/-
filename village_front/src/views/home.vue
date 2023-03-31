<script setup>
import { reactive } from 'vue';
  const data = reactive({
    navMenu : [
      { id:1,
        label:'首页',
        url:'22'
      },
      { id:2,
        label:'新闻动态',
        url:''
      },
      { id:3,
        label:'乡村风貌',
        url:''
      },
      { id:4,
        label:'留言问答',
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
      },
      {
        id:4,
        time:'2022-1-12',
        theme:'财政部 农业农村部发布2022年重点强农惠农政策',
        content:'年中央财政信开展粮食耕种原则上县域内补贴标准应统一。'
      },
      {
        id:5,
        time:'2022-1-12',
        theme:'财政部 农业农村部发布2022年重点强农惠农政策',
        content:'年中央财政信开展粮食耕种原则上县域内补贴标准应统一。'
      },
      {
        id:6,
        time:'2022-1-12',
        theme:'财政部 农业农村部发布2022年重点强农惠农政策',
        content:'年中央财政信开展粮食耕种原则上县域内补贴标准应统一。'
      }
    ],
    currentIndex:0
  })

 const clickMenu = function(menu){
  data.currentIndex = menu.id
  console.log(menu);
 }

</script>

<template>
  <div class="head"></div>
  <div class="nav">
    <ul>
       <li v-for="menu in data.navMenu" :key="menu.url" :class="{active:data.currentIndex === menu.id}" @click="clickMenu(menu)">{{menu.label}}</li>
    </ul>
    <div class="main">
      <div class="left">
        <div class="swiper">
          <el-carousel :interval="5000" arrow="always" height="200px">
            <el-carousel-item v-for="item in 3" :key="item">
              <h3 text="2xl" justify="center">{{ item }}</h3>
            </el-carousel-item>
          </el-carousel>
        </div>
        <div class="policyList">
            <div class="title">惠农政策：</div>
            <hr>
              <li v-for="item in data.policyList" :index="item.id" :key="item.id">
                <a href="">{{'['+item.time+']' }} &nbsp&nbsp&nbsp {{ item.theme }}</a>
              </li>
            </div>
      </div>
      <div class="right">
          <div class="video"></div>
          <div class="hot">
            <div class="title">热点关注：</div>
            <hr>
              <li v-for="item in data.policyList" :index="item.id" :key="item.id">
                <a href="">{{'['+item.time+']' }} &nbsp&nbsp&nbsp {{ item.theme }}</a>
              </li>
          </div>
      </div>
    </div>
  </div>

</template>
<style lang="less" scoped>
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
  display: flex;
 justify-content: space-around;
  margin: 10px auto 0 auto;
  width: 80%;
  height: 80%;
  .left{
    width: 50%;
    .swiper{
      width: 100%;
      height: 200px;
      background-color: rgb(102, 205, 86);
        .el-carousel__item h3 {
          text-align: center;
        }
    }
    .policyList{
      margin-top: 10px;
      padding: 0;
      .title{
        font-size: large;
        width: 100px;
        text-align: center;
        background-color: #79bbff;
      }
      li{
        height: 24px;
        list-style: none;
        text-overflow:ellipsis; 
        overflow: hidden; 
        a {
                //去掉下换线
                text-decoration: none;
                color:rgb(94, 92, 92);
          }
      }
       
    }
  }
  .right{
    width: 48%;
    .video{
      width: 100%;
      height: 250px;
      background-color: #bfa;
    }
    .hot{
      margin-top: 10px;
      .title{
        font-size: large;
        width: 100px;
        text-align: center;
        background-color: #f05431;
      }
      li{
        height: 24px;
        list-style: none;
        text-overflow:ellipsis; 
        overflow: hidden; 
        a {
                //去掉下换线
                text-decoration: none;
                color:rgb(94, 92, 92);
          }
      }
    }
  }
}

</style>
