//编辑脚本
function myChart() {
    var title = "柱状图";
    var divChart, myChart_option;
    var chartwidth = 400, chartheight = 300;

    //创建Chart控件
    function createChart() {
        divChart = new ht.Chart(null);
        divChart.setSize(chartwidth, chartheight);
        _gmyChart = divChart.getChart();
        myChart_option = {
            color: ['#3398DB'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '直接访问',
                    type: 'bar',
                    barWidth: '60%',
                    data: [10, 52, 200, 334, 390, 330, 220]
                }
            ]
        };
        _gmyChart.setOption(myChart_option);
    };

    var html = dataModel.getDataByTag("myChart");
    if (html) {
        chartwidth = html.a("w") || chartwidth;
        chartheight = html.a("h") || chartheight;
        createChart();
        html.setHtml(divChart);
    }
}

myChart();