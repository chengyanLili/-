<template>
    <div class="wrapper">
        <div class="left">
        <div class="swiper">
          <el-carousel :interval="5000" arrow="always" height="200px">
            <el-carousel-item v-for="item in 3" :key="item">
              <h3 text="2xl" justify="center">{{ item }}</h3>
            </el-carousel-item>
          </el-carousel>
        </div>
      </div>
      <div class="right">
        <el-tabs
          v-model="activeName"
          type="card"
          class="demo-tabs"
          @tab-click="handleClick"
          style="height: 250px;"
        >
          <el-tab-pane label="乡村要闻" name="first">
            <div class="title">乡村要闻</div>
            <hr>
              <li v-for="item in data.villageNews" :index="item.id" :key="item.id">
                <span>{{ item.title }}</span>
              </li>
          </el-tab-pane>
          <el-tab-pane label="凤庆县要闻" name="second">
            <div class="title">凤庆县要闻</div>
            <hr>
              <li v-for="item in data.countyNews" :index="item.id" :key="item.id">
                <span>{{ item.title }}</span>
              </li>
          </el-tab-pane>
          <el-tab-pane label="森林防火" name="third">
            <div class="title">森林防火</div>
            <hr>
              <li v-for="item in data.fileList" :index="item.id" :key="item.id">
                <span>{{ item.title }}</span>
              </li>
          </el-tab-pane>
          <el-tab-pane label="法律法规" name="four">
            <div class="title">法律法规</div>
            <hr>
              <li v-for="item in data.lawList" :index="item.id" :key="item.id">
                <span>{{ item.title }}</span>
              </li>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>

</template>

<script setup>
import { reactive,ref ,onMounted } from 'vue';
import request from '../../request/request'
const activeName = ref('first')
const data = reactive({
    villageNews:[],
    countyNews:[],
    fileList:[],
    lawList:[]
  })
 const handleClick = (val)=>{
  // console.log(val);
 }
 onMounted (()=>{
    load()
 }) 
 const load = () => {
  request.get("/api/task/findPage",{
    params: {
      pageNum: 1,
      pageSize: 100,
      title: '',
      noticeType: ''
    }
  }).then(res=>{
    const lists = res.data.data
    lists.map(v=>{
      switch (v.noticeType){
        case '乡村要闻':
          data.villageNews.push(v)
          break
        case '凤庆县要闻':
          data.countyNews.push(v)
          break
        case '森林防火':
          data.fileList.push(v)
          break
        case '法律法规':
          data.lawList.push(v)
          break
      }
    })
  })
}
</script>

<style lang="less" scoped>
body{
  background-color: #ebecef;
}
.wrapper{
    display: flex;
    justify-content: space-around;
    background-color: #ffffffeb;
    height: calc(100vh - 130px);
    overflow: auto;
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
    
  }
  .right{
    width: 48%;
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