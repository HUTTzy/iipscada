//编辑脚本
function myEchart() {
    var title = "实时趋势";
    var querytags = "", divChart, myEchart_option;
    var chartwidth = 600, chartheight = 400;
    var timer = 3000;//定时器
    var countdata = 600; //最大多少个数据
    var lengthdefine = new Array(),lengthname = new Array(),seriesdefine = new Array(),seriesdata = new Array(),units = new Array();

    //创建标签数据
    function createseries(tagname, desc, unit) {
        if (typeof tagname == "string")
            tagname = tagname.toUpperCase();

        seriesdata[tagname] = new Array();
        lengthdefine.push(tagname);
        lengthname[tagname] = desc;
        units[tagname] = unit;
        seriesdefine.push({
            name: tagname,
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: seriesdata[tagname]
        });
    };

    createseries("system.second", "秒", "S");
    createseries("system.minute", "当前分钟值", "Min");
    createseries("system.day", "当前日值", "day");

    querytags = lengthdefine.join(',');

    //创建Chart控件
    function createChart() {
        divChart = new ht.Chart(null);
        divChart.setSize(chartwidth, chartheight);
        myEchart_chart = divChart.getChart();
        myEchart_option = {
            //animation:false,
            title: {
                text: title
            },
            color: ['#ff0000', '#0000ff', '#000000', '#d48265', '#91c7ae', '#749f83', '#ca8622', '#bda29a', '#6e7074', '#546570', '#c4ccd3'],
            toolbox: {
                show: true,
                feature: {
                    saveAsImage: {}
                }
            },
            grid: {
                show: true,
                left: 40,
                top: 60,
                right: 40,
                bottom: 40
            },
            tooltip: {
                trigger: 'axis',
                formatter: function (ps) {
                    var str = "";
                    if (ps.length > 0 && ps[0].value) {
                        if (ie)
                            str = gtm2Local(ps[0].value[0]).toLocaleString();
                        else
                            str = ps[0].value[0];

                        for (var i in ps) {
                            var params = ps[i];
                            if (params.value != null) {
                                str += "<br/>";
                                str += lengthname[params.seriesName] + " " + ':' + params.name + units[params.seriesName];
                            }
                        }
                    }
                    return str;
                },
                axisPointer: {
                    animation: false
                }
            },
            xAxis: {
                type: 'time',
                splitLine: {
                    show: false
                }
            },
            yAxis: [
                {
                    type: 'value',
                    name: 'L数值%',
                    min: '0',
                    max: '100',
                    boundaryGap: [0, '100%'],
                    splitLine: {
                        show: true
                    }
                },
                {
                    type: 'value',
                    name: 'R数值%',
                    min: '0',
                    max: '100',
                    boundaryGap: [0, '100%'],
                    splitLine: {
                        show: true
                    }
                }
            ],
            legend: {
                show: true,
                orient: 'horizontal',      // 布局方式，默认为水平布局，可选为：
                // 'horizontal' ¦ 'vertical'
                x: 'center',               // 水平安放位置，默认为全图居中，可选为：
                // 'center' ¦ 'left' ¦ 'right'
                // ¦ {number}（x坐标，单位px）
                y: 'top',                  // 垂直安放位置，默认为全图顶端，可选为：
                data: lengthdefine,
                formatter: function (name) {
                    return lengthname[name];
                }
            },
            series: seriesdefine
        };
        myEchart_chart.setOption(myEchart_option);
        
        if (querytags != "")
            myEchart_query();
    };

    window["myEchart_query"] = function myEchart_query() {
        if (querytags != "") {
            $.post("server/scada/getvalues.ashx", { tags: querytags, nocache: 'true', type: "double" },function (data) {
                var d = 0;
                if (data.login > 0) {
                    var rows = data.rows;
                    for (var i in rows) {
                        var tag = rows[i];
                        if (tag.id != "") {
                            tagname = tag.id.toUpperCase();
                            if (seriesdata[tagname].length > countdata)
                                seriesdata[tagname].shift();
                            d = tag.value;
                            if (tag.max > tag.min) {
                                d = (d - tag.min) / (tag.max - tag.min) * 100.0;
                                }

                            if (ie) {
                                seriesdata[tagname].push({ name: tag.value, value: [local2GTM(tag.time), d] });
                                }
                            else {
                                seriesdata[tagname].push({ name: tag.value, value: [tag.time, d] });
                                }                           
                        }
                    }
                    myEchart_chart.setOption({ series: seriesdefine });
                }
            }
           , "json");
            setTimeout("myEchart_query()", timer);
        }
    };

    

    var html = dataModel.getDataByTag("myEchart");
    if (html) {
        chartwidth = html.a("w") || chartwidth;
        chartheight = html.a("h") || chartheight;
        createChart();
        html.setHtml(divChart);
    }
}

myEchart();