<template>
  <div class="static">
    <div class="upper">
      <div class="title">
        <div class="line"></div>
        <div>数据统计</div>
      </div>
      <div class="statistics">
        <el-card v-for="item in data.cards" class="box-card">
          <el-image :src="item.url" fit="fill" />
          <div class="num">
            <div class="num1">
              {{ item.num }}
              <span style="font-size: 18px">{{ item.unit }}</span>
            </div>
            <div class="desc">{{ item.desc }}</div>
          </div>
        </el-card>
      </div>
    </div>
    <div class="bottom">
      <div class="title">
        <div class="line"></div>
        <div>数据分布图</div>
      </div>
      <div class="statistics">
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>性别分布图</span>
            </div>
          </template>
          <div id="genderChart"></div>
        </el-card>
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>学历分布图</span>
            </div>
          </template>
          <div id="eduChart"></div>
        </el-card>
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>年龄分布图</span>
            </div>
          </template>
          <div id="ageChart"></div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, reactive } from "vue";
import * as echarts from "echarts";
import request from "../../request/request.js";
import s1 from "../../assets/img/s1.png";
import s2 from "../../assets/img/s2.png";
import s3 from "../../assets/img/s3.png";

const data = reactive({
  total: "",
  familyTotal: "",
  genderData: [],
  eduChart: [],
  ageChart: [],
  edu: {
    xlq: 0,
    wm: 0,
    xiaoxue: 0,
    chuzhong: 0,
    gaozhong: 0,
    jgxx: 0,
    zhongzhuan: 0,
    dazhuan: 0,
    benke: 0,
    yjsh: 0,
    other: 0,
  },
  age: {
    level0: 0,
    level1: 0,
    level2: 0,
    level3: 0,
    level4: 0,
    level5: 0,
    level6: 0,
    level7: 0,
    level8: 0,
    level9: 0,
  },
  cards: [
    {
      name: "familys",
      url: s1,
      num: 0,
      desc: "当前户数",
      unit: "户",
    },
    {
      name: "people",
      url: s2,
      num: 0,
      desc: "人口总数量",
      unit: "人",
    },
    { name: "partyMember", url: s3, num: 0, desc: "党员人数", unit: "人" },
  ],
});

request
  .get("/api/family/findPage", {
    params: {
      pageNum: 1,
      pageSize: 15,
      householdName: "",
      name: "",
    },
  })
  .then((res) => {
    if ((res.status = "200")) {
      data.cards[0].num = res.data.total;
    }
  });

onMounted(() => {
  // 年龄格式
  const formatAge = function (cellValue) {
    if (cellValue.birthday !== null) {
      let currentYear = new Date().getFullYear(); //当前的年份
      let calculationYear = new Date(
        cellValue.birthday.split("T")[0]
      ).getFullYear(); //计算的年份
      const wholeTime =
        currentYear + cellValue.birthday.split("T")[0].substring(4); //周岁时间
      const calculationAge = currentYear - calculationYear; //按照年份计算的年龄
      //判断是否过了生日
      if (new Date().getTime() > new Date(wholeTime).getTime()) {
        return calculationAge;
      } else {
        return calculationAge - 1;
      }
    } else {
      return;
    }
  };
  (async () => {
    let nan = 0;
    let nv = 0;
    await request
      .get("/api/population/findPage", {
        params: {
          pageNum: 1,
          pageSize: 10000,
          name: "",
          phone: "",
          idCard: "",
        },
      })
      .then((res) => {
        if ((res.status = "200")) {
          data.cards[1].num = res.data.total;
          res.data.data.forEach((item) => {
            if (item.policy == "中国共产党党员") {
              data.cards[2].num++;
            }
            item.gender == "男" ? nan++ : nv++;
            switch (item.education) {
              case "学龄前":
                data.edu.xlq++;
                break;
              case "文盲或半文盲":
                data.edu.wm++;
                break;
              case "小学":
                data.edu.xiaoxue++;
                break;
              case "初中":
                data.edu.chuzhong++;
                break;
              case "高中":
                data.edu.gaozhong++;
                break;
              case "技工学校":
                data.edu.jgxx++;
                break;
              case "中等专业学校":
                data.edu.zhongzhuan++;
                break;
              case "大专":
                data.edu.dazhuan++;
                break;
              case "大学本科":
                data.edu.benke++;
                break;
              case "研究生":
                data.edu.yjsh++;
                break;
              case "其他":
                data.edu.other++;
                break;
            }
            let itemAge = formatAge(item);
            if (itemAge <= 6) {
              data.age.level0++;
              return;
            }
            if (7 <= itemAge && itemAge <= 12) {
              data.age.level1++;
              return;
            }
            if (13 <= itemAge && itemAge <= 17) {
              data.age.level2++;
              return;
            }
            if (18 <= itemAge && itemAge <= 45) {
              data.age.level3++;
              return;
            }
            if (46 <= itemAge && itemAge <= 69) {
              data.age.level4++;
              return;
            }
            if (69 < itemAge && itemAge) {
              data.age.level5++;
              return;
            }
            if (66 <= itemAge && itemAge < 76) {
              data.age.level6++;
              return;
            }
          });
          data.genderData = [
            { value: nan, name: "男" },
            { value: nv, name: "女" },
          ];
          data.eduChart = [
            { name: "学龄前", value: data.edu.xlq },
            { name: "文盲或半文盲", value: data.edu.wm },
            { name: "小学", value: data.edu.xiaoxue },
            { name: "初中", value: data.edu.chuzhong },
            { name: "高中", value: data.edu.gaozhong },
            { name: "技工学校", value: data.edu.jgxx },
            { name: "中等专业学校", value: data.edu.zhongzhuan },
            { name: "大专", value: data.edu.dazhuan },
            { name: "大学本科", value: data.edu.benke },
            { name: "研究生", value: data.edu.yjsh },
            { name: "其他", value: data.edu.other },
          ];
          data.ageChart = [
            { name: "婴幼儿", value: data.age.level0 },
            { name: "少儿", value: data.age.level1 },
            { name: "青少年", value: data.age.level2 },
            { name: "青年", value: data.age.level3 },
            { name: "中年", value: data.age.level4 },
            { name: "老年", value: data.age.level5 },
          ];
        }
      });
    chart(data.genderData, "genderChart", "按性别统计人数");
    chart(data.eduChart, "eduChart", "按学历统计人数");
    chart(data.ageChart, "ageChart", "按年龄统计人数");
  })();
});
const chart = (param, chartName, chartTitle) => {
  var chartName = echarts.init(document.getElementById(chartName));
  chartName.setOption({
    title: {
      text: chartTitle,
      left: "center",
    },
    tooltip: {
      trigger: "item",
    },
    // legend: {
    //   orient: "vertical",
    //   left: "left",
    // },
    series: [
      {
        type: "pie",
        radius: "50%",
        data: param,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: "rgba(0, 0, 0, 0.5)",
          },
        },
      },
    ],
  });

  window.onresize = function () {
    //自适应大小
    chartName.resize();
  };
};
</script>

<style lang="less">
.static {
  .upper {
    height: 200px;
    .title {
      display: flex;
      align-items: center;
      font-size: 18px;
      .line {
        background-color: #409eff;
        width: 4px;
        height: 20px;
        margin-right: 8px;
      }
    }
    .statistics {
      display: grid;
      grid-template-columns: 1fr 1fr 1fr;
      margin-top: 15px;
      gap: 20px;

      .el-card {
        height: 120px;
        background: url("../../assets/img/static.jpeg");
        background-size: cover;
        .el-card__body {
          display: flex;
          justify-content: space-around;
          align-items: center;
        }

        .el-image {
          width: 65px;
          height: 65px;
        }
        .num {
          .num1 {
            font-size: 40px;
          }
          .desc {
            font-size: 16px;
            color: rgb(99, 97, 97);
          }
        }
      }
    }
  }
  .bottom {
    .title {
      display: flex;
      align-items: center;
      font-size: 18px;
      .line {
        background-color: #409eff;
        width: 4px;
        height: 20px;
        margin-right: 8px;
      }
    }
    .statistics {
      width: 100%;
      height: 100%;
      display: grid;
      grid-template-columns: 1fr 1fr 1fr;
      margin-top: 12px;
      gap: 10px;
      .box-card {
        height: 350px;
        .el-card__header {
          padding: 8px;
        }
      }
      #genderChart {
        width: 300px;
        height: 300px;
        background-color: #fff;
      }
      #eduChart {
        width: 300px;
        height: 300px;
        background-color: #fff;
      }
      #ageChart {
        width: 300px;
        height: 300px;
        background-color: #fff;
      }
    }
  }
}
</style>
