//对话框1
mydialog = new ht.widget.Dialog();
mydialog.setConfig({
    title: "询问",
    closable: false,
    draggable: true,
    width: 200,
    height:100,
    contentPadding: 10,
    content: '<div>确定操作吗?</div>',
    buttons: [
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
    buttonsAlign: "right",
});
//mydialog.show();