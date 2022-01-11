(function ($) {
    'use strict';
    $.index2 = {};
    $.extend($.index2, {

        /**
         * 数据中心概况的图表
         */
        circleChart: {
            // CPU
            CPUChart: null,
            // 内存
            memoryChart: null,
            // 磁盘
            diskChart: null,
            // 数据库
            databaseChart: null,
        },
        /**
         * 数据量占比分析饼图
         */
        dataPie: null,
        /**
         * 共享交换文档折线图
         */
        docChart: null,
        /**
         * EMPI地图
         */
        EMPIMap: null,
        /**
         * 初始化
         */
        init() {
            // 定时刷新时间
            // setInterval(() => {
            //
            // }, 1000);
            this.initCircleChart();
            this.initDataPie();
            this.initEMPIMap();
            this.initDocChart();
            this.initEvent();
            this.initWordCloud();
            this.initInterval();
        },

        /**
         * 加载数据中心概况的图表
         */
        initCircleChart() {
            let $this = this;
            let option = $this.getCircleChartOption();
            // 初始化CPUChart
            let ctx1 = document.getElementById("CPUChart");
            $this.circleChart.CPUChart = echarts.init(ctx1);
            $this.circleChart.CPUChart.setOption(option);
            // 初始化memoryChart
            let ctx2 = document.getElementById("memoryChart");
            $this.circleChart.memoryChart = echarts.init(ctx2);
            $this.circleChart.memoryChart.setOption(option);
            // 初始化diskMemory
            let ctx3 = document.getElementById("diskChart");
            $this.circleChart.diskChart = echarts.init(ctx3);
            $this.circleChart.diskChart.setOption(option);
            // 初始化databaseChart
            let ctx4 = document.getElementById("databaseChart");
            $this.circleChart.databaseChart = echarts.init(ctx4);
            $this.circleChart.databaseChart.setOption(option);
        },

        /**
         * 初始化数据量占比分析图表
         */
        initDataPie() {
            let $this = this;
            let ctx = document.getElementById("dataChart");
            $this.dataPie = echarts.init(ctx);
            let option = {
                tooltip: {
                    trigger: 'item'
                },
                series: [
                    {
                        name: 'Access From',
                        type: 'pie',
                        radius: ['40%', '70%'],
                        label: {
                            show: true
                        },
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        },
                        labelLine: {
                            show: true
                        },
                        data: [
                            { value: 1048, name: 'HIS',itemStyle: {color: '#3fc8ce'} },
                            { value: 735, name: 'LIS' ,itemStyle: {color: '#a72f84'}},
                            { value: 580, name: 'PACS',itemStyle: {color: '#45a83b'} },
                            { value: 484, name: 'CIS' ,itemStyle: {color: '#aa8136'}},
                            { value: 300, name: 'NDC' ,itemStyle: {color: '#3861aa'}}
                        ]
                    }
                ]
            }

            $this.dataPie.setOption(option);
        },

        /**
         * 初始化EMPI地图
         */
        initEMPIMap() {
            let $this = this;
            $this.EMPIMap = echarts.init(document.getElementById('EMPIMap'));
            $.get('sichuan.json', result => {
                // 注册 echarts 地图
                echarts.registerMap('sichuan', result);
                let option = {
                    tooltip: {
                        trigger: 'item',
                    },
                    geo: {
                        map: 'sichuan',
                        label: {
                            show: true
                        },
                        roam: false,
                        itemStyle: {
                            areaColor: '#29c7ea'
                        }
                    },
                    visualMap: {
                        min: 0,
                        max: 5000,
                        text: ['High', 'Low'],
                        itemHeight: 100,
                        right: -10,
                        bottom: 0,
                        realtime: false,
                        calculable: true,
                        inRange: {
                            color: ['lightskyblue', 'yellow', 'orangered']
                        },
                        textStyle: {
                            color: '#bab8b8',
                            fontSize: 10
                        }
                    },
                    series: [{
                        type: 'map',
                        map: 'sichuan',
                        roam: false,
                        data: [
                            {name: '成都市', value: 7424},
                            {name: '德阳市', value: 3345},
                            {name: '绵阳市', value: 4334},
                            {name: '广元市', value: 234},
                            {name: '巴中市', value: 234},
                            {name: '达州市', value: 234},
                            {name: '南充市', value: 4533},
                            {name: '广安市', value: 2312},
                            {name: '遂宁市', value: 213},
                            {name: '资阳市', value: 3314},
                            {name: '内江市', value: 2214},
                            {name: '雅安市', value: 1424},
                            {name: '眉山市', value: 324},
                            {name: '乐山市', value: 477},
                            {name: '攀枝花市',value: 234},
                            {name: '凉山彝族自治州', value: 12},
                            {name: '宜宾市', value: 23},
                            {name: '自贡市', value: 455},
                            {name: '泸州市', value: 455},
                            {name: '甘孜藏族自治州', value: 0},
                            {name: '阿坝藏族羌族自治州',value: 334}
                        ],
                    }]
                };
                $this.EMPIMap.setOption(option);
            })
        },

        /**
         * 设置EMPI的数据
         * @param url 请求数据的URL
         * @param name 地图注册区
         * @param data 标注的数据
         */
        setEMPIData(url, name, data) {
            let $this = this;
            if ($this.EMPIMap) {
                let option = $this.EMPIMap.getOption();

            }
        },

        /**
         * 初始化共享交换文档图表
         */
        initDocChart() {
            let $this = this;
            let ctx = document.getElementById('docChart');
            $this.docChart = echarts.init(ctx);
            let option = {
                title: {
                    text: '一周注册时段分析',
                    textStyle: {
                        color: '#fff'
                    },
                    left: 'center',
                    top: 0
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: ['2021-12-09', '2021-12-10', '2021-12-11', '2021-12-12', '2021-12-13', '2021-12-14', '2021-12-15'],
                    splitLine: false,
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: '#ccc'
                        }
                    }
                },
                yAxis: {
                    type: 'value',
                    splitLine: false,
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: '#ccc'
                        }
                    }
                },
                grid: {
                    left: '7%',
                    bottom: '10%',
                    right: '5%',
                    top: '15%'
                },
                series: [
                    {
                        data: [12, 45, 22, 87, 34, 23, 23],
                        type: 'line',
                        smooth: true,
                        lineStyle: {
                            color: {
                                type: 'linear',
                                x: 0,
                                y: 0,
                                x2: 0,
                                y2: 1,
                                colorStops: [{
                                    offset: 0, color: 'red' // 0% 处的颜色
                                }, {
                                    offset: 1, color: 'purple' // 100% 处的颜色
                                }],
                                global: false // 缺省为 false
                            }
                        }
                    }
                ]
            }
            $this.docChart.setOption(option);
        },

        /**
         * 获取数据中心概况图表的配置（因为都是一样的配置）
         */
        getCircleChartOption() {
            return {
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    top: '5%',
                    left: 'center'
                },
                graphic:{
                    type:"text",
                    left:"center",
                    top:"42%",
                    style:{
                        text:"89%",
                        textAlign:"center",
                        fill:"rgb(6,201,219)",
                        fontSize:16
                    }
                },
                series: [
                    {
                        name: 'Access From',
                        type: 'pie',
                        radius: ['60%', '70%'],
                        avoidLabelOverlap: false,
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: false,
                                fontSize: '40',
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: [
                            {value: 89, itemStyle: {color: 'rgb(6,201,219)'}},
                            {value: 11, itemStyle: {color: '#868686'}}
                        ]
                    }
                ]
            };
        },

        /**
         * 初始化词云
         */
        initWordCloud() {
            let arr = [
                {text: '高血压', weight: 34, html: {title: '检索次数：34次'}},
                {text: '脑死亡', weight: 24, html: {title: '检索次数：24次'}},
                {text: '股骨头', weight: 12, html: {title: '检索次数：12次'}},
                {text: '血透', weight: 22, html: {title: '检索次数：22次'}},
                {text: '脱发', weight: 17, html: {title: '检索次数：17次'}},
                {text: '颈动脉', weight: 17, html: {title: '检索次数：17次'}},
                {text: '新冠病毒', weight: 43, html: {title: '检索次数：43次'}},
                {text: '血液', weight: 14, html: {title: '检索次数：14次'}},
            ];
            $('#wordCloud').jQCloud(arr, {
                removeOverflowing: true
            });
            // 用于更新数据
            // $('#keywords').jQCloud('update', arr);
        },

        /**
         * 初始化事件
         */
        initEvent() {
            let $this = this;
            // 绑定echarts resize事件
            window.onresize = function () {
                $this.circleChart.CPUChart && $this.circleChart.CPUChart.resize();
                $this.circleChart.diskChart && $this.circleChart.diskChart.resize();
                $this.circleChart.memoryChart && $this.circleChart.memoryChart.resize();
                $this.circleChart.databaseChart && $this.circleChart.databaseChart.resize();
                $this.dataPie && $this.dataPie.resize();
                $this.EMPIMap && $this.EMPIMap.resize();
                $this.docChart && $this.docChart.resize();
            }
        },

        /**
         * 初始化定时器（用于界面上的元素动效）
         */
        initInterval() {
            let $this = this;
            // 动态更新一下指标
            // setInterval(() => {
                if ($this.docChart) {
                    $this.docChart.dispatchAction({
                        type: 'showTip', // 默认显示提示框
                        seriesIndex: 3, // 这行不能省
                    });
                }
            // }, 2000)
        }
    });
})(jQuery);

