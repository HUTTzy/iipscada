//TableView mytable
//编辑脚本
function mytable_init() {
    var mytabledataModel = new ht.DataModel();
    //mytabledataModel.getSelectionModel().setSelectionMode("single");
    mytable = new ht.widget.TablePane(mytabledataModel);
    mytable.setWidth(g2d.getWidth());
    mytable.setHeight(g2d.getHeight() - 200);
    mytable.getView().style.top = "200px";

    mytable.addColumns([
        {
            name: 'index',
            displayName: 'Index',
            accessType: 'attr',
            align: 'center'
        },
        {
            name: 'nation',
            displayName: 'Nation',
            accessType: 'attr',
            align: 'center'
        },
        {
            name: 'sex',
            displayName: 'Sex',
            accessType: 'attr',
            align: 'center'
        }
    ]);

    for (var i = 0; i < 30; i++) {
        var data = new ht.Data();
        data.a({
            index: i,
            sex: "sex" + i.toString(),
            nation: "nation" + i.toString()
        });
        mytabledataModel.add(data);
    }

    var mytableHeader = mytable.getTableHeader();

    mytableHeader.setColumnLineColor('#C8C8C8');
    mytableHeader.setInsertColor('#6DCDF3');
    mytableHeader.getLabelFont = function (column) { return 'bold 12px Arial' };

    mytableView = mytable.getTableView();

    mytableView.setSelectBackground('#E1E1E1');
    mytableView.setRowLineColor('#EDEDED');
    mytableView.setColumnLineVisible(false);

    //mytableView.setCheckMode(true);
    mytableView.setRowHeight(23);
    mytableView.setAutoHideScrollBar(false);
    mytableView.drawRowBackground = function (g, data, selected, x, y, width, height) {
        if (selected) {
            g.fillStyle = '#87A6CB';
        }
        else if (mytableView.getRowIndex(data) % 2 === 0) {
            g.fillStyle = '#F1F4F7';
        }
        else {
            g.fillStyle = '#FAFAFA';
        }
        g.beginPath();
        g.rect(x, y, width, height);
        g.fill();
    };

    window.addEventListener('resize', function (e) {
        mytable.setWidth(g2d.getWidth());
        mytable.setHeight(g2d.getHeight() - 200);
    }, false);

    document.body.appendChild(mytable.getView());


    /*获取选中的行数据
        //var dm = mytableView.getDataModel();
        var kk = mytableView.getSelectionModel().getFirstData();
        if (kk) {
            alert(kk.a("sex"));
        }
    */
}

mytable_init();

