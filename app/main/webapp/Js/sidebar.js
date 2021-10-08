;(function () {
    /****
     * 切换菜单
     * 2018/10/18
     */
    let contentDom = $("#mycontent");

    window.changePage = (url) => {
        $.post(url, {}, function (result) {
            contentDom.html(result);
        });
    }
    changePage('/user')
})();