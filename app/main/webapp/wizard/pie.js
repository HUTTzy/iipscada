//编辑脚本
function myPie() {
    var title = "饼图";
    var divChart, myPie_option;
    var chartwidth = 400, chartheight = 300;

    //创建Chart控件
    function createChart() {
        divChart = new ht.Chart(null);
        divChart.setSize(chartwidth, chartheight);
        _gmyPie = divChart.getChart();
        myPie_option = {
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            legend: {
                //orient: 'vertical',
                x: 'left',
                data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
            },
            series: [
                {
                    name: '访问来源',
                    type: 'pie',
                    radius: '60%', //['80%','60%']
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: true,
                            textStyle: {
                                fontSize: '30',
                                fontWeight: 'bold'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    data: [
                        { value: 335, name: '直接访问' },
                        { value: 310, name: '邮件营销' },
                        { value: 234, name: '联盟广告' },
                        { value: 135, name: '视频广告' },
                        { value: 1548, name: '搜索引擎' }
                    ]
                }
            ]
        };
        _gmyPie.setOption(myPie_option);
    };

    /*
    createChart();

    var html = dataModel.getDataByTag("myPie");
    if (html)
        html.setHtml(divChart);
    */

    var html = dataModel.getDataByTag("myPie");
    if (html) {
        chartwidth = html.a("w") || chartwidth;
        chartheight = html.a("h") || chartheight;
        createChart();
        html.setHtml(divChart);
    }
}

myPie();