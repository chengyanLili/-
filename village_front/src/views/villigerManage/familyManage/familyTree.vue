<template>
        <div class="container">
            <div class="left-chart" id="left-chart"></div>
            <div class="right-tabel">
                
            </div>
        </div>
    </template>
    
    <script setup>
    import * as echarts from 'echarts';
    import { onMounted } from 'vue-demi';
    function showEchart() {
        var myChart = echarts.init(document.getElementById('left-chart'));
        var option = {
            title: {
                show: false
            },
            tooltip: {
                trigger: 'item',
                formatter: "{b}"
                //formatter: "{b}: {c}"
            },
            //那个右上角的工具栏
            toolbox: {
                show: false,
            },
            calculable: false,
            series: [
                {
                    name: '树图',
                    type: 'tree',
                    //排列方式，横向、纵向
                    orient: 'vertical',
                    //根节点的位置
                    rootLocation: { x: 'center', y: '10%' },
                    //连接线长度
                    layerPadding: 30,
                    //结点间距
                    nodePadding: 20,
                    //图形形状
                    symbol: 'circle',
                    //尺寸大小
                    symbolSize: 40,
                    //图的一些样式设置
                    itemStyle: {
                        //正常情况显示
                        label: {
                            show: true,
                            position: 'inside',
                            textStyle: {
                                //字体颜色、大小、加粗
                                color: '#000',
                                fontSize: 15,
                                fontWeight: 'bolder'
                            },
    
                            color: '#fff',
                            lineStyle: {
                                color: '#000',
                                width: 1,
                                type: 'broken'
                            }
                        },
                        //鼠标移上去样式
                        emphasis: {
                            label: {
                                show: false,
                                textStyle: {
                                    align: 'center',
                                    verticalAlign: 'middle'
                                }
                            },
                            color: '#bfa',
                            borderWidth: 1
                        }
                    },
                    data: [
                        {
                            name: '张三',
                            //图片大小
                            symbolSize: [60, 80],
                            //图片
                            symbol: 'image://https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
                            children: [
                                {
                                    name: '同户',
                                    value: 4,
                                    symbol: 'circle',
                                    color: '#fff',
                                    symbolSize: [60, 60],
                                    children: [
                                        {
                                            name: '小黄',
                                            symbol: 'image://https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
                                            symbolSize: [60, 80],
                                            value: 4,
                                            itemStyle: {
    
                                                label: {
                                                    show: true,
                                                    position: 'bottom'
                                                }
    
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: '同址',
                                    symbol: 'circle',
                                    symbolSize: [60, 60],
                                    children: [
                                        {
                                            name: '小明',
                                            symbol: 'image://https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
                                            symbolSize: [60, 80],
                                            value: 4,
                                            itemStyle: {
    
                                                label: {
                                                    show: true,
                                                    position: 'bottom'
                                                }
    
                                            }
                                        },
                                        {
                                            name: '小黄',
                                            symbol: 'image://https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
                                            symbolSize: [60, 80],
                                            value: 4,
                                            itemStyle: {
    
                                                label: {
                                                    show: true,
                                                    position: 'bottom'
                                                }
    
                                            }
                                        }
                                    ]
                                },
                            ]
                        }
                    ]
                }
            ]
        };
        myChart.setOption(option);
    }
    onMounted(() => {
        showEchart()
    })

    </script>
    
    <style lang="less" scoped>
    .container {
        display: grid;
        grid-template-columns: 1fr 1fr;
        grid-template-rows: 1;
    
        .left-chart {
            border: black 1px solid;
            height: 600px;
        }
    
        .right-tabel {
            border: black 1px solid;
        }
    
    }
    </style>