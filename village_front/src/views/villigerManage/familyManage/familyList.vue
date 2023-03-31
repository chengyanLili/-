<template>
        <div class="title">户籍管理</div>
        <div class="operation">
          <div class="search">
            <el-input v-model="allData.searchForm.name" style="width:150px;margin-right: 10px;" placeholder="请输入姓名" />
            <el-input v-model="allData.searchForm.phone" style="width:150px;margin-right: 10px;" placeholder="请输入电话" />
            <el-button @click="load" type="primary"><el-icon style="font-size: 18px;margin-right: 6px">
                <Search />
              </el-icon>搜索</el-button>
          </div>
          <div class="add">
            <el-button @click="addFamily" type="primary">添加户籍</el-button>
            <el-button type="danger">删除所选户籍</el-button>
          </div>
        </div>
        <el-table :data="allData.tableData" :height="allData.maxheight" :border="true">
          <el-table-column type="selection" width="55" />
          <el-table-column prop="householdNum" label="户号" width="120" />
          <el-table-column prop="name" label="姓名" width="120" />
          <el-table-column prop="gender" label="性别" width="60" />
          <el-table-column prop="phone" label="电话号码" width="120" />
          <el-table-column prop="age" label="年龄" width="60" />
          <el-table-column prop="group" label="所在小组" width="100" />
          <el-table-column fixed="right" label="操作" width="150">
            <template #default="scope">
              <el-button type="primary" size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
              <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      
        <el-dialog width="60%" v-model="allData.dialogTableVisible" :title="allData.dialogTitle[allData.n]">
          <el-form :model="allData.addForm">
            <el-form-item label="姓名" :label-width="formLabelWidth">
              <el-input v-model="allData.addForm.name"  placeholder="请输入姓名" autocomplete="off" />
            </el-form-item>
            <el-form-item label="性别" :label-width="formLabelWidth">
              <el-radio-group v-model="allData.addForm.gender">
                <el-radio :label="3">男</el-radio>
                <el-radio :label="6">女</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="电话号码"  :label-width="formLabelWidth">
              <el-input v-model="allData.addForm.phone" placeholder="请输入电话号码" autocomplete="off" />
            </el-form-item>
            <el-form-item label="年龄" :label-width="formLabelWidth">
              <el-input v-model="allData.addForm.age"  placeholder="请输入年龄" autocomplete="off" />
            </el-form-item>
            <el-form-item :label-width="formLabelWidth" label="民族">
              <el-select v-model="allData.addForm.nation" filterable placeholder="直接选择或搜索选择">
                <el-option v-for="item in allData.addForm.nationList" :key="item.id" :index="item.index" :label="item.info"
                  :value="item.value"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="身份证号码"  :label-width="formLabelWidth">
              <el-input v-model="allData.addForm.idCard" placeholder="请输入身份证号" autocomplete="off" />
            </el-form-item>
            <el-form-item label="是否为户主" :label-width="formLabelWidth">
              <el-select v-model="allData.addForm.householder" clearable placeholder="请选择是否为户主">
                <el-option label="是" value="是"></el-option>
                <el-option label="否" value="否"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="职务" :label-width="formLabelWidth">
              <el-input v-model="allData.addForm.post" placeholder="请输入职务" autocomplete="off" />
            </el-form-item>
            <el-form-item :label-width="formLabelWidth" label="政治面貌">
              <el-select v-model="allData.addForm.policy" filterable placeholder="直接选择或搜索选择" >
                <el-option v-for="item in allData.addForm.policyList" :key="item.id" :index="item.id" :label="item.label"
                  :value="item.label"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item :label-width="formLabelWidth" label="学历">
              <el-select v-model="allData.addForm.education" filterable placeholder="直接选择或搜索选择">
                <el-option v-for="item in allData.addForm.eduList" :key="item.id" :index="item.index" :label="item.label"
                  :value="item.label"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="所在小组" :label-width="formLabelWidth">
              <el-input v-model="allData.addForm.group"  placeholder="请输入小组" autocomplete="off" />
            </el-form-item>
      
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="cancel">取消</el-button>
              <el-button type="primary" @click="save">
                保存
              </el-button>
            </span>
          </template>
        </el-dialog>
      
        <br>
        <el-pagination v-model:current-page="allData.currentPage" v-model:page-size="allData.pageSize"
          :page-sizes="[5, 10, 20, 25]" layout="total, sizes, prev, pager, next, jumper" :total="allData.total"
          @size-change="handleSizeChange" @current-change="handleCurrentChange" />
      </template>
      
      <script setup>
      import { onMounted, reactive } from 'vue';
      import request from '../../../request/request.js'
      import router from '../../../router/index'
      const formLabelWidth = '140px'
      const allData = reactive({
        tableData: [
                {
                householdNum:176478,
                name:'小米',
                gender:'女',
                phone:17869350021,
                age:23,
                group:'大'
                }
        ],
        maxheight: window.innerHeight - 280,
        currentPage: 1,
        pageSize: 10,
        total: 0,
        dialogTableVisible: false,
        dialogTitle: ['添加户籍', '编辑'],
        n: '',
        searchForm: {
          name: '',
          phone: '',
          idcard: ''
        },
        addForm: {
          name: '',
          phone: '',
          age: '',
          gender: '',
          idCard: '',
          post: '',
          householder: '',
          name: '',
          policy: '',
          education: '',
          group: '',
          policyList: [
            {
              id: 1,
              label: '中国共产党党员'
            },
            {
              id: 2,
              label: '九三学学社社员'
            },
            {
              id: 3,
              label: '台湾民主自治同盟盟员'
            },
            {
              id: 4,
              label: '无党派民主人士'
            },
            {
              id: 5,
              label: '群众'
            },
            {
              id: 6,
              label: '中国共产党预备党员'
            },
            {
              id: 7,
              label: '中国共产主义青年团团员'
            },
            {
              id: 8,
              label: '中国国民党革命委员会会员'
            },
            {
              id: 9,
              label: '中国民主同盟'
            },
            {
              id: 10,
              label: '中国民主建国会会员'
            },
            {
              id: 11,
              label: '中国民主促进会会员'
            },
            {
              id: 12,
              label: '中国农工民主党党员'
            },
            {
              id: 13,
              label: '中国致公党党员'
            },
            {
              id: 14,
              label: '其他'
            }
          ],
          eduList: [
            {
              id: 1,
              label: '请选择'
            },
            {
              id: 2,
              label: '学龄前'
            },
            {
              id: 3,
              label: '文盲或半文盲'
            },
            {
              id: 4,
              label: '小学'
            },
            {
              id: 5,
              label: '初中'
            },
            {
              id: 6,
              label: '高中'
            },
            {
              id: 7,
              label: '技工学校'
            },
            {
              id: 8,
              label: '中等专业学校'
            },
            {
              id: 9,
              label: '大专'
            },
            {
              id: 10,
              label: '大学专科和专科学校'
            },
            {
              id: 11,
              label: '大学本科'
            },
            {
              id: 12,
              label: '研究生'
            },
            {
              id: 13,
              label: '其他'
            }
          ],
          nationList: [
            {
              id: 1,
              info: '汉族',
              value: 1
            },
            {
              id: 2,
              info: '壮族',
              value: 2
            },
            {
              id: 3,
              info: '满族',
              value: 3
            },
            {
              id: 4,
              info: '回族',
              value: 4
            },
            {
              id: 5,
              info: '苗族',
              value: 5
            },
            {
              id: 6,
              info: '维吾尔族',
              value: 6
            },
            {
              id: 7,
              info: '土家族',
              value: 7
            },
            {
              id: 8,
              info: '彝族',
              value: 8
            },
            {
              id: 9,
              info: '蒙古族',
              value: 9
            },
            {
              id: 10,
              info: '藏族',
              value: 10
            },
            {
              id: 11,
              info: '布依族',
              value: 11
            },
            {
              id: 12,
              info: '侗族',
              value: 12
            },
            {
              id: 13,
              info: '瑶族',
              value: 13
            },
            {
              id: 14,
              info: '朝鲜族',
              value: 14
            },
            {
              id: 15,
              info: '白族',
              value: 15
            },
            {
              id: 16,
              info: '哈尼族',
              value: 16
            },
            {
              id: 17,
              info: '哈萨克族',
              value: 17
            },
            {
              id: 18,
              info: '黎族',
              value: 18
            },
            {
              id: 19,
              info: '傣族',
              value: 19
            },
            {
              id: 20,
              info: '畲族',
              value: 20
            },
            {
              id: 21,
              info: '傈僳族',
              value: 21
            },
            {
              id: 22,
              info: '仡佬族',
              value: 22
            },
            {
              id: 23,
              info: '东乡族',
              value: 23
            },
            {
              id: 24,
              info: '高山族',
              value: 24
            },
            {
              id: 25,
              info: '拉祜族',
              value: 25
            },
            {
              id: 26,
              info: '水族',
              value: 26
            },
            {
              id: 27,
              info: '佤族',
              value: 27
            },
            {
              id: 28,
              info: '纳西族',
              value: 28
            },
            {
              id: 29,
              info: '羌族',
              value: 29
            },
            {
              id: 30,
              info: '土族',
              value: 30
            },
            {
              id: 31,
              info: '仫佬族',
              value: 31
            },
            {
              id: 32,
              info: '锡伯族',
              value: 32
            },
            {
              id: 33,
              info: '柯尔克孜族',
              value: 33
            },
            {
              id: 34,
              info: '达斡尔族',
              value: 34
            },
            {
              id: 35,
              info: '景颇族',
              value: 35
            },
            {
              id: 36,
              info: '毛南族',
              value: 36
            },
            {
              id: 37,
              info: '撒拉族',
              value: 37
            },
            {
              id: 38,
              info: '布朗族',
              value: 38
            },
            {
              id: 39,
              info: '塔吉克族',
              value: 39
            },
            {
              id: 40,
              info: '阿昌族',
              value: 40
            },
            {
              id: 41,
              info: '普米族',
              value: 41
            },
            {
              id: 42,
              info: '鄂温克族',
              value: 42
            },
            {
              id: 43,
              info: '怒族',
              value: 43
            },
            {
              id: 44,
              info: '京族',
              value: 44
            },
            {
              id: 45,
              info: '基诺族',
              value: 45
            },
            {
              id: 46,
              info: '德昂族',
              value: 46
            },
            {
              id: 47,
              info: '保安族',
              value: 47
            },
            {
              id: 48,
              info: '俄罗斯族',
              value: 48
            },
            {
              id: 49,
              info: '裕固族',
              value: 49
            },
            {
              id: 50,
              info: '乌孜别克族',
              value: 50
            },
            {
              id: 51,
              info: '门巴族',
              value: 51
            },
            {
              id: 52,
              info: '鄂伦春族',
              value: 52
            },
            {
              id: 53,
              info: '独龙族',
              value: 53
            },
            {
              id: 54,
              info: '塔塔尔族',
              value: 54
            },
            {
              id: 55,
              info: '赫哲族',
              value: 55
            },
            {
              id: 56,
              info: '珞巴族',
              value: 56
            }
          ],
        },
        rules:{
          name:[{required:true,message:'请输入姓名',trigger:'blur'}]
        }

      
      })
      
      // 获取全部的人口信息
      const load = () => {
        request.get('http://127.0.0.1:9090/population/findPage', {
          params: {
            currentPage: allData.currentPage,
            pageSize: allData.pageSize,
            name: allData.searchForm.name,
            phone: allData.searchForm.phone,
            idcard: allData.searchForm.idcard
          }
        }).then((res) => {
          if (res.status = '200') {
            allData.tableData = res.data.data.villagers
            allData.total = res.data.data.total
          }
      
        })
      }
      // 添加
      const addFamily = () => {
        router.push({
                name:'addFamily'
        })
      }
      const cancel = () => {
        allData.dialogTableVisible = false
      }
      const save = () => {
        allData.dialogTableVisible = false
        console.log('addForm', allData.addForm);
      }
      
      onMounted(
        window.onresize = () => {
          return (() => {
            allData.maxheight = window.innerHeight - 280
          })()
        }
      )
      load();
      // 每页个数的改变
      const handleSizeChange = (val) => {
        allData.pageSize = val
        load()
      }
      // 跳转到哪里去
      const handleCurrentChange = (val) => {
        allData.currentPage = val
        load()
      }
      </script>
      
      <style lang="less" scoped>
      .title {
        font-size: 18px;
      }
      
      .search {
        // width: 200px;
        margin: 10px 0;
        display: flex;
      }
      
      .add {
        display: flex;
        margin-bottom: 10px;
      }
      
      .el-dialog {
        .el-form {
          display: grid;
          grid-template-columns: 1fr 1fr;
        }
      
      }
      </style>