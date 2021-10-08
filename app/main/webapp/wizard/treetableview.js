//TreeTableView  mytreeTable
function mytreeTable_init() {
    var mytreeTabledataModel = new ht.DataModel();
    mytreeTabledataModel.getSelectionModel().setSelectionMode("single");
    var mytreeTablePane = new ht.widget.TreeTablePane(mytreeTabledataModel);
    var mytreeTableView = mytreeTablePane.getTableView();
    var treeColumn = mytreeTableView.getTreeColumn();
    treeColumn.setDisplayName('目录结构');
    treeColumn.setAlign('center');
    treeColumn.setWidth(260);

    mytreeTableView.addColumns([
        {
            name: 'description',
            displayName: '说明',
            accessType: 'attr',
            width: 500,
            sortable: false
        }
    ]);

    mytreeTableView.drawRowBackground = function (g, data, selected, x, y, width, height) {
        if ((!this.getCheckMode() && selected) ||
            (this.getCheckMode() && data === this.getFocusData())) {
            g.fillStyle = '#87A6CB';
        }
        else if (this.getRowIndex(data) % 2 === 0) {
            g.fillStyle = '#F1F4F7';
        }
        else {
            g.fillStyle = '#FAFAFA';
        }
        g.beginPath();
        g.rect(x, y, width, height);
        g.fill();
    };

    mytreeTableView.isChildrenSortable = function (data) {
        if (data) {
            var name = data.getName();
            return false;
        }
        return false;
    };

    mytreeTableView.setVisibleFunc(function (data) {
        if (data.isEmpty()) {

        }
        return true;
    });

    initModel();
    

    function initModel() {
        root = create('根');
        lib = create('库', root, '库目录');
        core = create('核心', lib, '核心类库目录');
        create('javascript', core, '核心产品包');
        plugin = create('插件', lib, '扩展类库目录');
        create('对话框', plugin, '对话框扩展包');
    }

    function create(name, parent, description) {
        var data = new ht.Data();
        data.setName(name);
        data.a('description', description);
        data.setParent(parent);
        mytreeTabledataModel.add(data);
        return data;
    }

    mytreeTableView.expandAll();
    mytreeTablePane.setWidth(g2d.getWidth());
    mytreeTablePane.setHeight(g2d.getHeight() - 200);
    mytreeTablePane.getView().style.top = "200px";
    
    window.addEventListener('resize', function (e) {
        mytreeTablePane.setWidth(g2d.getWidth());
        mytreeTablePane.setHeight(g2d.getHeight() - 200);
    }, false);

    document.body.appendChild(mytreeTablePane.getView());
}

/*获取选中的行数据
    //var dm = mytreeTableView.getDataModel();
    var kk = mytreeTableView.getSelectionModel().getFirstData();
    if (kk) {
        alert(kk.a("description"));
    }
*/

mytreeTable_init();