//调色板组件  mypalette
function mypalette_init() {
    var mypalette = new ht.widget.Palette();
    var pdataModel = mypalette.getDataModel(),
        view = mypalette.getView(),
        style = view.style,
        mapGroup = new ht.Group(),
        phoneGroup = new ht.Group(),
        routerGroup = new ht.Group();

    mapGroup.setName("地图");
    mapGroup.setExpanded(true);
    for (var i = 1; i < 3; i++) {
        var node = new ht.Node();
        node.setName("map" + i);
        pdataModel.add(node);
        node.setParent(mapGroup);
    }
    pdataModel.add(mapGroup);

    phoneGroup.setName("手机");
    phoneGroup.setExpanded(true);
    for (var i = 1; i < 3; i++) {
        var node = new ht.Node();
        node.setName("phone" + i);
        pdataModel.add(node);
        node.setParent(phoneGroup);
    }
    pdataModel.add(phoneGroup);

    routerGroup.setName("路由器");
    routerGroup.setExpanded(true);
    for (var i = 1; i < 3; i++) {
        var node = new ht.Node();
        node.setName("router" + i);
        pdataModel.add(node);
        node.setParent(routerGroup);
    }
    pdataModel.add(routerGroup);

    style.position = "absolute";
    style.top = "210px";
    style.right = "5px";
    style.background = "#F1F1F1";
    mypalette.setWidth(200);
    mypalette.setHeight(400);

    document.body.appendChild(view);
    /*
    window.addEventListener("resize", function (e) {
        mypalette.iv();
    });
    */
}
mypalette_init();