//上下文菜单定义
function initContextMenu() {
    contextMenu = new ht.widget.ContextMenu();
    contextMenu.addTo(g2d.getView());
    contextMenu.afterShow = function () {
    };
    contextMenu.afterHide = function () {
    };
    contextMenu.beforeShow = function () {
        contextMenu.setItems([]);
    };
}
initContextMenu();