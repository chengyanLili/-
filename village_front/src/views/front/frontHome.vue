<template>
    <div class="wrapper">
      <div class="upper">
        <div class="upper-title">走进荒田</div>
        <div class="upper-content" v-for="item in data.introduceList">
          <div class="upper-content_text">
            <span>{{ item.content }}</span>
            <div class="upper-btns">
              <div class="upper-btn1">
                <el-button style=" margin-left: 10px;" type="primary" icon="el-icon-edit" circle></el-button>
                <div>荒田概况</div>
              </div>
              <div class="upper-btn1">
                <el-button style=" margin-left: 10px;" type="success" icon="el-icon-edit" circle></el-button>
                <div>特色产业</div>
              </div>
              <div class="upper-btn1">
                <el-button style=" margin-left: 10px;" type="warning" icon="el-icon-edit" circle></el-button>
                <div>基层组织</div>
              </div>
              <div class="upper-btn1">
                <el-button style=" margin-left: 10px;" type="danger" icon="el-icon-edit" circle></el-button>
                <div>文化教育</div>
              </div>
            </div>
          </div>
          <div class="upper-content_img">
            <el-image
              :src="item.url"
              fit="fill">
            </el-image>
          </div>
        </div>
      </div>
      <hr>
      <div class="bottom">
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
    lawList:[],
    introduceList: [
            { url:'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',label : '荒田概况' , content : '荒田村隶属于云南省临沧市凤庆县雪山镇，地处雪山镇东北边，东邻中山村，南邻兴街村，西邻三岔河镇光华村，北邻三岔河镇涌金村，到镇道路为土路，交通方便。该村距雪山镇政府所在地23公里，距凤庆县县城108公里。该村辖渔塘、田坝等15个村民小组，从事第一产业人数943人。全村国土面积16.3平方公里，海拔1850米，年平均气温23℃，年降水量1300毫米，适合种植水稻、玉米等农作物。农民收入以种植业为主。'}
        ]
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
.wrapper{
    
    background-color: #ffffffeb;
    height: calc(100vh - 140px);
    overflow: auto;
    .upper{
      .upper-title{
        background-color: #92C4F8;
        height: 40px;
        line-height: 40px;
      }
      .upper-content{
        margin: 10px;
        display: grid;
        grid-template-columns: 2fr 1fr;
        gap: 20px;
        .upper-content_text{
          position:relative;
          .upper-btns{
            display: flex;
            position: absolute;
	          bottom: 10px;
            left: 10px;
            .upper-btn1{
              margin-left: 20px;
            }
          }
        }
      }
    }
    .bottom{
      display: flex;
      justify-content: space-around;
      margin-top: 10px;
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

}
  

</style>