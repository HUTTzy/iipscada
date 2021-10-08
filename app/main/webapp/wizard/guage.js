//编辑脚本
function myGuage() {
    var title = "仪表盘";
    var divChart;
    var chartwidth = 400, chartheight = 300;

    //创建Chart控件
    function createChart() {
        divChart = new ht.Chart(null);
        divChart.setSize(chartwidth, chartheight);
        _gmyGuage = divChart.getChart();
        myGuage_option = {
            tooltip: {
                formatter: "{a} <br/>{b} : {c}%"
            },
            series: [
                {
                    name: '效率指标',
                    type: 'gauge',
                    detail: {
                        //backgroundColor: 'rgba(30,144,255,0.8)',
                        // borderWidth: 1,
                        formatter: '{value}%',
                        borderColor: '#fff',
                        shadowColor: '#fff', //默认透明
                        shadowBlur: 5,
                        //width: 80,
                        //height:30,
                        //offsetCenter: [25, '20%'],       // x, y，单位px
                        textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                            fontWeight: 'bolder',
                            color: '#000000',
                            fontSize: '20px'
                        }
                    },
                    data: [{ value: 50, name: '效率' }]
                }
            ]
        };
        _gmyGuage.setOption(myGuage_option);
    };

    var html = dataModel.getDataByTag("myGuage");
    if (html) {
        chartwidth = html.a("w") || chartwidth;
        chartheight = html.a("h") || chartheight;
        createChart();
        html.setHtml(divChart);
    }

}

function myGuage_SetValue(number) {
    if (myGuage_option.series[0] && myGuage_option.series[0].data) {
        myGuage_option.series[0].data[0].value = number;
        _gmyGuage.setOption(myGuage_option, true);
    }
}

myGuage();

setInterval(function () {
    myGuage_SetValue((Math.random() * 100).toFixed(2) - 0);    
}, 5000);