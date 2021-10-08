//编辑脚本
function fun_myjustgage() {
    var myjustgage_div = document.createElement("div");
    myjustgage_div.id = "myjustgage";
    document.body.appendChild(myjustgage_div);
    myjustgage = new JustGage(
    {
        id: "myjustgage",
        value:0,
        min: 0,
        max: 100,
        title: "Title",
        label: "value",
        donut: true,
        relativeGaugeSize: true
    });
    var html = dataModel.getDataByTag("myjustgage");
    if (html) {
        chartwidth = html.a("w");
        chartheight = html.a("h");
        html.setHtml(myjustgage_div);
    }
}
fun_myjustgage();
//更新数据
//myjustgage.refresh(value);
//myjustgage.refresh(value,max);
