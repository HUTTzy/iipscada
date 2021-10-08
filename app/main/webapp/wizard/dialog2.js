//对话框表单
function formPane_mydialog_init() {
    var formPane_mydialog = new ht.widget.FormPane();
    formPane_mydialog.getLabelFont = function (item) { return "bold 12px arial, sans-serif"; };
    formPane_mydialog.getLabelVAlign = function (item) { return 'top'; };
    formPane_mydialog.addRow([
                        '用户名称:',
                        {
                            id: 'firstName',
                            textField: {
                                text: 'Eric'
                            }
                        }
    ], [80, 0.1]);
    formPane_mydialog.addRow([
                        '密码:',
                        {
                            id: 'password',
                            textField: {
                                text: 'ht for web',
                                type: 'password'
                            }
                        }
    ], [80, 0.1]);

    formPane_mydialog.addRow([
                        '描述:',
                        {
                            id: 'description',
                            textArea: {
                                text: 'www.wtsoftware.cn'
                            }
                        }
    ], [80, 0.1], 0.1);

    formPane_mydialog.addRow([
                        null,
                        {
                            button: {
                                label: '提交',
                                onClicked: function () {
                                    alert(
                                        '用户名:' + formPane_mydialog.v('firstName') + '\n' +
                                        '密码:' + formPane_mydialog.v('password') + '\n' +
                                        '描述:' + formPane_mydialog.v('description')
                                    );
                                }
                            }
                        },
                        {
                            button: {
                                label: '清除',
                                onClicked: function () {
                                    formPane_mydialog.v({
                                        firstName: '',
                                        password: '',
                                        description: ''
                                    });
                                }
                            }
                        }
    ], [0.1, 100, 100]);

    formPane_mydialog.setWidth(350);
    formPane_mydialog.setHeight(200);

    mydialog = new ht.widget.Dialog();
    mydialog.setConfig({
        title: "对话框表单",
        closable: true,
        draggable: true,
        contentPadding: 0,
        width: 450,
        height: 300,
        content: formPane_mydialog
    });
    //mydialog.show();

}

formPane_mydialog_init();