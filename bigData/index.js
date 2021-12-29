(function ($) {
    'use strict';

    $.index = {};
    $.extend($.index, {
        // socket对象  用于定时去获取后台的数据来刷新页面
        socket: null,
        // 接入商接口分布
        pieChart: null,
        // 服务调用分析柱形图
        serviceBar: null,
        // 消息处理折线图
        msgLine: null,
        // 引擎负载折线图
        engineLine: null,
        /**
         * 初始化
         */
        init() {
            // this.rainBg();
            this.initWebSocket();
            this.initPieChart();
            this.initServiceBar();
            this.initMsgLine();
            this.initEngineLine();
            this.initEvent();
        },

        /**
         * 创建雨滴背景
         */
        rainBg() {
            let c = document.querySelector(".rain");
            let ctx = c.getContext("2d");//获取canvas上下文
            let w = c.width = document.querySelector('.main').clientWidth;
            let h = c.height = document.querySelector('.main').clientHeight;
            //设置canvas宽、高

            function random(min, max) {
                return Math.random() * (max - min) + min;
            }

            function RainDrop() { }
            //雨滴对象 这是绘制雨滴动画的关键
            RainDrop.prototype = {
                init: function () {
                    this.x = random(0, w);//雨滴的位置x
                    this.y = h;//雨滴的位置y
                    this.color = 'hsl(180, 100%, 50%)';//雨滴颜色 长方形的填充色
                    this.vy = random(4, 5);//雨滴下落速度
                    this.hit = 0;//下落的最大值
                    this.size = 2;//长方形宽度
                },
                draw: function () {
                    if (this.y > this.hit) {
                        let linearGradient = ctx.createLinearGradient(this.x, this.y, this.x, this.y + this.size * 30)
                        // 设置起始颜色
                        linearGradient.addColorStop(0, '#14789c')
                        // 设置终止颜色
                        linearGradient.addColorStop(1, '#090723')
                        // 设置填充样式
                        ctx.fillStyle = linearGradient
                        ctx.fillRect(this.x, this.y, this.size, this.size * 50);//绘制长方形，通过多次叠加长方形，形成雨滴下落效果
                    }
                    this.update();//更新位置
                },
                update: function () {
                    if (this.y > this.hit) {
                        this.y -= this.vy;//未达到底部，增加雨滴y坐标
                    } else {
                        this.init();
                    }
                }
            };

            function resize() {
                w = c.width = window.innerWidth;
                h = c.height = window.innerHeight;
            }

            //初始化一个雨滴

            let rs = []
            for (let i = 0; i < 10; i++) {
                setTimeout(function () {
                    let r = new RainDrop();
                    r.init();
                    rs.push(r);
                }, i * 300)
            }

            function anim() {
                ctx.clearRect(0, 0, w, h);//填充背景色，注意不要用clearRect，否则会清空前面的雨滴，导致不能产生叠加的效果
                for (let i = 0; i < rs.length; i++) {
                    rs[i].draw();//绘制雨滴
                }
                requestAnimationFrame(anim);//控制动画帧
            }

            window.addEventListener("resize", resize);
            //启动动画
            anim()
        },

        /**
         * 初始化websocket  使用websocket访问后台获取定时更新的数据
         */
        initWebSocket() {

        },

        /**
         * 初始化饼图
         */
        initPieChart() {
            let $this = this;
            let ctx = document.getElementById('pieChart');
            $this.pieChart = echarts.init(ctx);

            let option = {
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    top: '5%',
                    left: 'center'
                },
                series: [
                    {
                        name: '',
                        type: 'pie',
                        radius: ['40%', '70%'],
                        avoidLabelOverlap: false,
                        label: {
                            show: true,
                            position: 'center',
                            formatter: '接口分布',
                            color: '#fff'
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
                            { value: 1048, itemStyle: {color: '#80ebd790'} },
                            { value: 735, itemStyle: {color: '#6198dd'} },
                            { value: 580, itemStyle: {color: '#e295f590'}},
                            { value: 484, itemStyle: {color: '#6ebb6995'} }
                        ]
                    }
                ]
            }
            $this.pieChart.setOption(option);
        },

        /**
         * 初始化服务调用分析柱形图（不采用echarts图）
         */
        initServiceBar() {
            let $this = this;

        },

        /**
         * 初始化消息处理折线图
         */
        initMsgLine() {
            let $this = this;
            let ctx = document.getElementById('msgLine');
            $this.msgLine = echarts.init(ctx);
            function randomData() {
                now = new Date(+now + oneDay);
                value = value + Math.random() * 21 - 10;
                return {
                    name: now.toString(),
                    value: [
                        [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
                        Math.round(value)
                    ]
                };
            }
            let data = [];
            let now = new Date(1997, 9, 3);
            let oneDay = 24 * 3600 * 1000;
            let value = Math.random() * 1000;
            for (let i = 0; i < 1000; i++) {
                data.push(randomData());
            }
            let option = {
                tooltip: {
                    trigger: 'axis',
                    formatter: function (params) {
                        params = params[0];
                        let date = new Date(params.name);
                        return (
                            date.getDate() +
                            '/' +
                            (date.getMonth() + 1) +
                            '/' +
                            date.getFullYear() +
                            ' : ' +
                            params.value[1]
                        );
                    },
                    axisPointer: {
                        animation: false
                    }
                },
                xAxis: {
                    type: 'time',
                    splitLine: {
                        show: false
                    },
                    axisLabel: {
                        color: '#fff'
                    }
                },
                yAxis: {
                    type: 'value',
                    boundaryGap: [0, '100%'],
                    splitLine: {
                        show: false
                    },
                    axisLabel: {
                        color: '#fff'
                    }
                },
                series: [
                    {
                        name: 'Fake Data',
                        type: 'line',
                        showSymbol: false,
                        data: data,
                        itemStyle: {
                            color: '#fff'
                        }
                    }
                ]
            };
            $this.msgLine.setOption(option);
            setInterval(function () {
                for (let i = 0; i < 5; i++) {
                    data.shift();
                    data.push(randomData());
                }
                $this.msgLine.setOption({
                    series: [
                        {
                            data: data
                        }
                    ]
                });
            }, 1000);
        },

        /**
         * 初始化引擎负载折线图
         */
        initEngineLine() {
            let $this = this;
            let ctx = document.getElementById('engineLine');
            $this.engineLine = echarts.init(ctx);
            function randomData() {
                now = new Date(+now + oneDay);
                value = value + Math.random() * 21 - 10;
                return {
                    name: now.toString(),
                    value: [
                        [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
                        Math.round(value)
                    ]
                };
            }
            let data = [];
            let now = new Date(1997, 9, 3);
            let oneDay = 24 * 3600 * 1000;
            let value = Math.random() * 1000;
            for (let i = 0; i < 1000; i++) {
                data.push(randomData());
            }
            let option = {
                tooltip: {
                    trigger: 'axis',
                    formatter: function (params) {
                        params = params[0];
                        let date = new Date(params.name);
                        return (
                            date.getDate() +
                            '/' +
                            (date.getMonth() + 1) +
                            '/' +
                            date.getFullYear() +
                            ' : ' +
                            params.value[1]
                        );
                    },
                    axisPointer: {
                        animation: false
                    }
                },
                xAxis: {
                    type: 'time',
                    splitLine: {
                        show: false
                    },
                    axisLabel: {
                        color: '#fff'
                    }
                },
                yAxis: {
                    type: 'value',
                    boundaryGap: [0, '100%'],
                    splitLine: {
                        show: false
                    },
                    axisLabel: {
                        color: '#fff'
                    }
                },
                series: [
                    {
                        name: 'Fake Data',
                        type: 'line',
                        showSymbol: false,
                        data: data,
                        itemStyle: {
                            color: '#fff'
                        }
                    }
                ]
            };
            $this.engineLine.setOption(option);
            setInterval(function () {
                for (let i = 0; i < 5; i++) {
                    data.shift();
                    data.push(randomData());
                }
                $this.engineLine.setOption({
                    series: [
                        {
                            data: data
                        }
                    ]
                });
            }, 1000);
        },
        /**
         * 初始化时间
         */
        initEvent() {
            let $this = this;
            // 绑定echarts resize事件
            window.onresize = function () {
                $this.pieChart && $this.pieChart.resize();
                $this.msgLine && $this.msgLine.resize();
                $this.engineLine && $this.engineLine.resize();
            }
        }

    });

})(jQuery);