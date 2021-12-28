(function ($) {
    'use strict';

    $.index = {};
    $.extend($.index, {
        // socket对象  用于定时去获取后台的数据来刷新页面
        socket: null,
        // 接入商接口分布
        pieChart: null,
        /**
         * 初始化
         */
        init() {
            this.rainBg();
            this.initWebSocket();
            this.initPieChart();
        },

        /**
         * 创建雨滴背景
         */
        rainBg() {
            var c = document.querySelector(".rain");
            var ctx = c.getContext("2d");//获取canvas上下文
            var w = c.width = document.querySelector('.main').clientWidth;
            var h = c.height = document.querySelector('.main').clientHeight;
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
                        var linearGradient = ctx.createLinearGradient(this.x, this.y, this.x, this.y + this.size * 30)
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

            var rs = []
            for (var i = 0; i < 10; i++) {
                setTimeout(function () {
                    var r = new RainDrop();
                    r.init();
                    rs.push(r);
                }, i * 300)
            }

            function anim() {
                ctx.clearRect(0, 0, w, h);//填充背景色，注意不要用clearRect，否则会清空前面的雨滴，导致不能产生叠加的效果
                for (var i = 0; i < rs.length; i++) {
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
            var $this = this;
            var ctx = document.getElementById('pieChart');
            $this.pieChart = echarts.init(ctx);

            var option = {
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

    });

})(jQuery);