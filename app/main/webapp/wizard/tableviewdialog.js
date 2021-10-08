//表格对话框 mydialog
//编辑脚本
function mytable_init() {
    var mytabledataModel = new ht.DataModel();
    var mytable = new ht.widget.TablePane(mytabledataModel);
    mytabledataModel.getSelectionModel().setSelectionMode("single");
    mytable.addColumns([
        {
            name: 'index',
            width: 200,
            displayName: 'Index',
            accessType: 'attr',
            align: 'left'
        },
        {
            name: 'nation',
            width: 200,
            displayName: 'Nation',
            accessType: 'attr',
            align: 'left'
        },
        {
            name: 'sex',
            width: 100,
            displayName: 'Sex',
            accessType: 'attr',
            align: 'left'
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

    var mytableView = mytable.getTableView();

    mytableView.setSelectBackground('#E1E1E1');
    mytableView.setRowLineColor('#EDEDED');
    mytableView.setColumnLineVisible(false);

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


    /*获取选中的行数据
        var dm = mytableView.getDataModel();
        var kk = mytableView.getSelectionModel().getFirstData();
        if (kk) {
            alert(kk.a("sex"));
        }
    */



    mytable.setWidth(500);
    mytable.setHeight(300);

    mydialog = new ht.widget.Dialog();
    mydialog.setConfig({
        title: "表格对话框",
        closable: true,
        draggable: true,
        contentPadding: 0,
        width: 550,
        height: 350,
        content: mytable, buttons: [
         {
             label: "确定",
             action: function (item, e) {
                 mydialog.hide();
             }
         },
         {
             label: "取消",
             action: function (item, e) {
                 mydialog.hide();
             }
         }
        ],
        buttonsAlign: "right"
    });
}

mytable_init();
