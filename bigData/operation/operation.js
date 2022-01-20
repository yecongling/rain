(function ($) {
    'use strict';
    $.operation = {};

    $.extend($.operation, {

        /**
         * 趋势图
         */
        trendChart: null,

        /**
         * 诊断分析柱形图
         */
        diagnosisChart: null,

        /**
         * 患者来源分析饼图
         */
        patientPie: null,

        /**
         * 科室床位使用率以及平均住院日分析
         */
        bedAndHospitalDayChart: null,

        /**
         * 初始化
         */
        init() {
            this.initTrendChart();
            this.initDiagnosisChart();
            this.initPatientPie();
            this.initBedAndHospitalDayChart();
            this.initEvent();
            this.setIntervalFn();
        },

        /**
         * 初始化趋势图
         */
        initTrendChart() {
            let $this = this;
            let ctx = document.getElementById('trendChart');
            $this.trendChart = echarts.init(ctx);
            let option = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        animation: false
                    }
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: ['0:00', '2:00', '4:00', '6:00', '8:00', '10:00', '12:00', '14:00', '16:00', '18:00', '20:00', '22:00'],
                    axisLine: {
                        lineStyle: {
                            color: '#547082'
                        }
                    },
                    splitLine: {
                        show: true,
                        lineStyle: {
                            color: '#1e3b4e'
                        }
                    },
                    axisLabel: {
                        color: '#dcdcdc',
                        interval: 0
                    }
                },
                yAxis: {
                    type: 'value',
                    axisLine: {
                        lineStyle: {
                            color: '#547082'
                        }
                    },
                    splitLine: {
                        show: true,
                        lineStyle: {
                            color: '#1e3b4e'
                        }
                    },
                    axisLabel: {
                        color: '#dcdcdc'
                    }
                },
                grid: {
                    right: 0,
                    bottom: 20,
                    top: 10,
                    show: false,
                    borderColor: '#315976'
                },
                series: [
                    {
                        data: [820, 932, 901, 934, 1290, 1330, 1320, 1231, 1234, 12, 134, 22],
                        type: 'line',
                        symbol: 'none',
                        smooth: true,
                        itemStyle: {
                            normal: {
                                color: '#1678e3'
                            }
                        }
                    },
                    {
                        data: [23, 932, 23, 934, 32, 55, 34, 22, 45, 12, 134, 22],
                        type: 'line',
                        smooth: true,
                        symbol: 'none',
                        itemStyle: {
                            normal: {
                                color: '#12872b'
                            }
                        }
                    }
                ]
            }
            $this.trendChart.setOption(option);
        },

        /**
         * 初始化疾病诊断分析柱形图
         */
        initDiagnosisChart() {
            let $this = this;
            let ctx = document.getElementById('diagnosisChart');
            $this.diagnosisChart = echarts.init(ctx);
            let option = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                grid: {
                    right: 0,
                    left: 20,
                    bottom: 20,
                    top: 10,
                    show: false,
                    borderColor: '#315976',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        data: ['肺心病', '高血压', '肝炎', '冠心病', '白血病', '糖尿病', '慢性肾炎'],
                        axisTick: {
                            alignWithLabel: true
                        },
                        splitLine: {
                            show: false,
                            lineStyle: {
                                color: '#1e3b4e'
                            }
                        },
                        axisLabel: {
                            color: '#dcdcdc',
                            interval: 0
                        }
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        splitLine: {
                            show: true,
                            lineStyle: {
                                color: '#1e3b4e'
                            }
                        },
                        axisLabel: {
                            color: '#dcdcdc'
                        }
                    }
                ],
                series: [
                    {
                        name: 'Direct',
                        type: 'bar',
                        barWidth: '30%',
                        data: [10, 52, 200, 334, 390, 330, 220],
                        itemStyle: {
                            normal: {
                                barBorderRadius: [7, 7, 0, 0],
                                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                    {offset: 0, color: '#2378f7'},
                                    {offset: 0.7, color: '#2378f7'},
                                    {offset: 1, color: 'rgba(15,79,151,0.65)'}
                                ])
                            }
                        }
                    }
                ]
            }
            $this.diagnosisChart.setOption(option);
        },

        /**
         * 初始化患者来源分析饼图
         */
        initPatientPie() {
            let $this = this;
            let ctx = document.getElementById('patientPie');
            $this.patientPie = echarts.init(ctx);
            let option = {
                tooltip: {
                    trigger: 'item'
                },
                grid: {
                    right: 0,
                    left: 20,
                    bottom: 20,
                    top: 10,
                    show: false
                },
                series: [
                    {
                        name: '患者来源',
                        type: 'pie',
                        radius: '50%',
                        data: [
                            {
                                value: 1048, name: '未填写', itemStyle: {
                                    color: '#e0e545'
                                }
                            },
                            {
                                value: 735, name: '15-24周岁', itemStyle: {
                                    color: '#48b6f5'
                                }
                            },
                            {
                                value: 580, name: '25-44周岁', itemStyle: {
                                    color: '#20bd5e'
                                }
                            },
                            {
                                value: 484, name: '45-64周岁', itemStyle: {
                                    color: '#c78dfa'
                                }
                            },
                            {
                                value: 300, name: '65岁以上', itemStyle: {
                                    color: '#fda769'
                                }
                            }
                        ],
                    }
                ]
            }
            $this.patientPie.setOption(option);
        },

        /**
         * 初始化科室床位使用率和平均住院日分析面积折线图
         */
        initBedAndHospitalDayChart() {
            let $this = this;
            let ctx = document.getElementById('bedAndHospitalDayChart');
            $this.bedAndHospitalDayChart = echarts.init(ctx);
            let option = {
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: [
                        {
                            name: '平均住院日',
                            icon: 'rect',
                            itemStyle: {
                                color: '#31affa'
                            },
                            textStyle: {
                                color: '#fff'
                            }
                        },
                        {
                            name: '床位使用率',
                            icon: 'rect',
                            textStyle: {
                                color: '#fff'
                            }
                        }]
                },
                xAxis: {
                    type: 'category',
                    data: ['外科', '内科', '神经科', '呼吸科', '儿科', '妇科', '妇产科'],
                    axisLabel: {
                        color: '#dcdcdc',
                        interval: 0
                    },
                    splitLine: {
                        show: false,
                        lineStyle: {
                            color: '#1e3b4e'
                        }
                    }
                },
                yAxis: {
                    type: 'value',
                    name: '（元）',
                    nameTextStyle: {
                        color: '#fff'
                    },
                    axisLabel: {
                        color: '#dcdcdc',
                        interval: 0
                    },
                    splitLine: {
                        show: true,
                        lineStyle: {
                            color: '#1e3b4e'
                        }
                    },
                },
                grid: {
                    right: '5%',
                    left: 20,
                    bottom: 20,
                    top: '20%',
                    show: false,
                    borderColor: '#315976',
                    containLabel: true
                },
                series: [
                    {
                        name: '平均住院日',
                        data: [20, 93, 90, 34, 290, 330, 320],
                        type: 'line',
                        itemStyle: {
                            color: '#50b3f3'
                        },
                        lineStyle: {
                            color: '#50b3f3'
                        },
                        smooth: true,
                    },
                    {
                        name: '床位使用率',
                        type: 'bar',
                        barWidth: '50%',
                        data: [10, 52, 200, 334, 390, 330, 220],
                        itemStyle: {
                            normal: {
                                barBorderRadius: [7, 7, 0, 0],
                                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                    {offset: 0, color: '#2378f7'},
                                    {offset: 0.7, color: '#2378f7'},
                                    {offset: 1, color: 'rgba(15,79,151,0.65)'}
                                ])
                            }
                        }
                    }
                ]
            }
            $this.bedAndHospitalDayChart.setOption(option);
        },

        /**
         * 初始化事件
         */
        initEvent() {
            let $this = this;
            /* 患者来源切换 */
            $('input[type="radio"][name="divide"]').off('click').on('click', function () {
                let value = this.value;
                // 性别
                let data = [];
                if (value === '1') {
                    data = [
                        {
                            value: 1048, name: '男', itemStyle: {
                                color: '#e0e545'
                            }
                        },
                        {
                            value: 735, name: '女', itemStyle: {
                                color: '#48b6f5'
                            }
                        }
                    ];
                } else {
                    // 年龄段
                    data = [
                        {
                            value: 1048, name: '未填写', itemStyle: {
                                color: '#e0e545'
                            }
                        },
                        {
                            value: 735, name: '15-24周岁', itemStyle: {
                                color: '#48b6f5'
                            }
                        },
                        {
                            value: 580, name: '25-44周岁', itemStyle: {
                                color: '#20bd5e'
                            }
                        },
                        {
                            value: 484, name: '45-64周岁', itemStyle: {
                                color: '#c78dfa'
                            }
                        },
                        {
                            value: 300, name: '65岁以上', itemStyle: {
                                color: '#fda769'
                            }
                        }
                    ]
                }
                if ($this.patientPie) {
                    let option = $this.patientPie.getOption();
                    option.series[0].data = data;
                    $this.patientPie.setOption(option);
                }

            });
        },

        /**
         * 定时刷新
         */
        setIntervalFn() {
            let click = false;
            /* 患者来源分析定时切换数据划分维度 */
            setInterval(() => {
                if (click) {
                    $('input[type="radio"][name="divide"][value="1"]').click();
                } else {
                    $('input[type="radio"][name="divide"][value="2"]').click();
                }
                click = !click;
            }, 5000);
        }
    });
})(jQuery);