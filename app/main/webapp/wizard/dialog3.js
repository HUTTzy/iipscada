//HTML对话框表单
mydialog = new ht.widget.Dialog();
mydialog.setConfig({
    title: "HTML对话框表单",
    closable: false,
    draggable: true,
    contentPadding: 10,
    width: 300,
    height: 150,
    content:'<div>文件名称:<input id="filename" style="font-size:14px;"></div>',
    buttons:[
     {
         label: "确定",
         action: function (item, e) {
             mydialog.hide();
             var filename = mydialog.getView().querySelector("#filename").value;
             alert(filename);            
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
//mydialog.show();