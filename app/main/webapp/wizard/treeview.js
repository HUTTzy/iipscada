//TreeView控件 mytreeView
function mytreeView_init() {
    var mytreeView = new ht.widget.TreeView();
    mytreeView.setSelectBackground('#34495E');
    mytreeView.setRowHeight(20);
    mytreeView.setExpandIcon('images/expand1.gif');
    mytreeView.setCollapseIcon('images/collapse1.gif');
    mytreeView.setIndent(16);
    var mytreeView_view = mytreeView.getView();
    mytreeView_view.style.left = "0px";
    mytreeView_view.style.top = "0px";
    mytreeView_view.style.width = "150px";
    mytreeView_view.style.height = g2d.getHeight().toString() + "px";//"480px";   
    mytreeView_view.style.background = "#F1F1F1";//背景颜色     
    //mytreeView.setCheckMode('all'); mytreeView.enableToolTip();
    mytreeView.getIcon = function (data) {
        if (data.a('class')) { return 'images/class.png'; }
        else {
            if (this.isExpanded(data))
            { return 'images/folder-open.gif'; }
            else { return 'images/folder.gif'; }
        }
    };
    var mytreeView_dm = mytreeView.dm();
    var mytreeView_root = new ht.Data();
    mytreeView_root.setName("mytreeView_root");
    mytreeView_root.setTag("mytreeView_root");
    for (var i = 0; i < 10; i++) {
        var h = new ht.Data();
        h.setName("c" + i.toString());
        //h.setTag("c" + i.toString());
        h.setParent(mytreeView_root);
        mytreeView_dm.add(h);
    }
    mytreeView_dm.add(mytreeView_root);
    mytreeView.onDataClicked = function (data) {
        //alert(data.getName());
    };
    mytreeView.onDataDoubleClicked = function (data) {
        //alert(data.getName());
    };
    mytreeView.expandAll();
    document.body.appendChild(mytreeView.getView());//添加控件到画面
    //修改Html控件的名称，把控件加入入图形中
    //var html = dataModel.getDataByTag("Html2394");
    //html.setHtml(mytreeView.getView());
    window.addEventListener('resize', function (e) {
        mytreeView_view.style.height = g2d.getHeight().toString() + "px";
    }, false);
}

mytreeView_init();