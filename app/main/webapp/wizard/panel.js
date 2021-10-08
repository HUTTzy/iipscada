//面板 myPanel
function myPanel_init() {
    var formPane = new ht.widget.FormPane();
    formPane.getLabelFont = function (item) { return "bold 12px arial, sans-serif"; };
    formPane.getLabelVAlign = function (item) { return 'top'; };
    formPane.addRow([
                        '用户名称:',
                        {
                            id: 'firstName',
                            textField: {
                                text: 'Eric'
                            }
                        }
    ], [80, 0.1]);
    formPane.addRow([
                        '密码:',
                        {
                            id: 'password',
                            textField: {
                                text: 'ht for web',
                                type: 'password'
                            }
                        }
    ], [80, 0.1]);

    formPane.addRow([
                        '描述:',
                        {
                            id: 'description',
                            textArea: {
                                text: 'www.wtsoftware.cn'
                            }
                        }
    ], [80, 0.1], 0.1);

    formPane.addRow([
                        null,
                        {
                            button: {
                                label: '提交',
                                onClicked: function () {
                                    alert(
                                        '用户名:' + formPane.v('firstName') + '\n' +
                                        '密码:' + formPane.v('password') + '\n' +
                                        '描述:' + formPane.v('description')
                                    );
                                }
                            }
                        },
                        {
                            button: {
                                label: '清除',
                                onClicked: function () {
                                    formPane.v({
                                        firstName: '',
                                        password: '',
                                        description: ''
                                    });
                                }
                            }
                        }
    ], [0.1, 100, 100]);

    formPane.setWidth(350);
    formPane.setHeight(200);

    var myPanel = new ht.widget.Panel({
        id: "myPanel",
        title: "面板",
        restoreToolTip: "面板",
        titleIcon: "",
        width: 350,
        contentHeight: 200,
        narrowWhenCollapse: true,
        content: formPane,
        expanded: true
    });
    myPanel.setPositionRelativeTo("rightBottom");//leftTop  leftBottom rightTop
    myPanel.setPosition(0, 0);
    document.body.appendChild(myPanel.getView());
}

myPanel_init();
