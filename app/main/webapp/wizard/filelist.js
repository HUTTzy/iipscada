//图形文件列表控件
function fileList_init() {
    var mylistView1 = new ht.widget.ListView();
    mylistView1.setSelectBackground('#FF0000');
    mylistView1.setRowHeight(30);
    mylistView1.setIndent(25);
    var mylistView1_view = mylistView1.getView();
    mylistView1_view.style.left = "0px";
    mylistView1_view.style.top = "0px";
    mylistView1_view.style.width = "150px";
    mylistView1_view.style.height = g2d.getHeight().toString() + "px";
    mylistView1_view.style.background = "#F1F1F1";//背景颜色     

    mylistView1.getIcon = function (data) {
        return 'node_image';
    };

    var mylistView1_dm = mylistView1.dm();

    $.post("/server/getFileList", {"type": "json"}, function (fileList) {
        fileList.forEach(function (name) {
            if (filename != name) {
                var h = new ht.Node();
                h.setName(name);
                mylistView1_dm.add(h);
            }
        });
    });

    mylistView1.onDataClicked = function (data) {
        location = "?filename=" + data.getName();
    };

    document.body.appendChild(mylistView1.getView());//添加控件到画面

    window.addEventListener('resize', function (e) {
        mylistView1_view.style.height = g2d.getHeight().toString() + "px";//调整高度
    }, false);
}

fileList_init();
