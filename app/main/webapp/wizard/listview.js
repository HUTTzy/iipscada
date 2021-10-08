//ListView控件 mylistView1
function mylistView1_init() {
    var mylistView1 = new ht.widget.ListView();
    mylistView1.setSelectBackground('#FF0000');
    mylistView1.setRowHeight(20);
    mylistView1.setIndent(20);
    var mylistView1_view = mylistView1.getView();
    mylistView1_view.style.left = "155px";
    mylistView1_view.style.top = "0px";
    mylistView1_view.style.width = "150px";
    mylistView1_view.style.height = g2d.getHeight().toString() + "px";//"480px";   
    mylistView1_view.style.background = "#FFFFFF";//背景颜色     
    //mylistView1.setCheckMode('all'); mylistView1.enableToolTip();

    mylistView1.getIcon = function (data) {
        return 'images/folder.gif';
    };

    var mylistView1_dm = mylistView1.dm();
    for (var i = 0; i < 10; i++) {
        var h = new ht.Data();
        h.setName("c" + i.toString());
        //h.setTag("c" + i.toString());
        mylistView1_dm.add(h);
    }

    mylistView1.onDataClicked = function (data) {
        //alert(data.getName());
    };
    mylistView1.onDataDoubleClicked = function (data) {
        //alert(data.getName());
    };

    document.body.appendChild(mylistView1.getView());//添加控件到画面
    //修改Html控件的名称，把控件加入入图形中
    //var html = dataModel.getDataByTag("Html2394");
    //html.setHtml(mylistView1.getView());

    window.addEventListener('resize', function (e) {
        mylistView1_view.style.height = g2d.getHeight().toString() + "px";   //调整高度
    }, false);
}

mylistView1_init();
