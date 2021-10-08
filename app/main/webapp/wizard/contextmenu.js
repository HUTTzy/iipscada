//上下文菜单定义
function runtime_initContextMenu() {
    var _tagnameMenu = {
        label: "tagname",
        tagname: "",
        action: function () {
            showTagInfo(_tagnameMenu.tagname);
        }
    };

    var _tagsMenu = {
        label: "画面数据库",
        tags: "",
        action: function () {
            viewCurrentTags();
        }
    };

    var _ntrendMenu = {
        label: "实时趋势",
        tags: "",
        action: function () {
            openrealtrend();
        }
    };


    var _trendMenu = {
        label: "实时趋势",
        tags: "",
        action: function () {
            openrealtrend("tags=" + _trendMenu.tags);
        }
    };

    var _nhistrendMenu = {
        label: "历史趋势",
        tags: "",
        action: function () {
            openhistrend();
        }
    };

    var _histrendMenu = {
        label: "历史趋势",
        tags: "",
        action: function () {
            openhistrend("tags=" + _histrendMenu.tags);
        }
    };

    var _copytagnameMenu = {
        label: "复制变量",
        tags: "",
        action: function () {
            clipdata = _copytagnameMenu.tags;
            clipboard.onClick(clipboard_button_event);
        }
    };

    var _tagnameMenu1 = {
        label: "tagname",
        tagname: "",
        action: function () {
            showTagInfo(_tagnameMenu1.tagname);
        }
    };

    var _tagnameMenu2 = {
        label: "tagname",
        tagname: "",
        action: function () {
            showTagInfo(_tagnameMenu2.tagname);
        }
    };

    var _tagnameMenu3 = {
        label: "tagname",
        tagname: "",
        action: function () {
            showTagInfo(_tagnameMenu3.tagname);
        }
    };

    var _tagnameMenu4 = {
        label: "tagname",
        tagname: "",
        action: function () {
            showTagInfo(_tagnameMenu4.tagname);
        }
    };

    var _tagnameMenu5 = {
        label: "tagname",
        tagname: "",
        action: function () {
            showTagInfo(_tagnameMenu5.tagname);
        }
    };

    var _tagnameMenu6 = {
        label: "tagname",
        tagname: "",
        action: function () {
            showTagInfo(_tagnameMenu6.tagname);
        }
    };

    var _tagnameMenu7 = {
        label: "tagname",
        tagname: "",
        action: function () {
            showTagInfo(_tagnameMenu7.tagname);
        }
    };

    var _tagnameMenu8 = {
        label: "tagname",
        tagname: "",
        action: function () {
            showTagInfo(_tagnameMenu8.tagname);
        }
    };

    var _tagnameMenu9 = {
        label: "tagname",
        tagname: "",
        action: function () {
            showTagInfo(_tagnameMenu9.tagname);
        }
    };

    var _vistagnameMenu = {
        label: "tagname",
        tagname: "",
        action: function () {
            showTagInfo(_vistagnameMenu.tagname);
        }
    };

    var _enbagnameMenu = {
        label: "tagname",
        tagname: "",
        action: function () {
            showTagInfo(_enbagnameMenu.tagname);
        }
    };

    var _flashtagnameMenu = {
        label: "tagname",
        tagname: "",
        action: function () {
            showTagInfo(_flashtagnameMenu.tagname);
        }
    };


    var _zoominMenu = {
        label: "放大",
        action: function () {
            zoomIn();
        }
    };

    var _zoomoutMenu = {
        label: "缩小",
        action: function () {
            zoomOut();
        }
    };

    var _zoomresetMenu = {
        label: "还原缩放",
        action: function () {
            zoomReset();
        }
    };
    var _autofillMenu = {
        label: "自动适应画面",
        action: function () {
            fitContent();
        }
    };

    var _playbackMenu = {
        label: "历史回放",
        type: "check",
        selected: _playbackmode,
        action: function () {
            if (!_playbackmode)
                startplayback();
            else
                stopplayback();
        }
    };

    var _editor = {
        label: "编辑画面",
        action: function () {
            if (!_playbackmode)
                editor(filename);
        }
    };

    var _moveMenu = {
        label: "平移",
        type: "check",
        selected: isPannable,
        action: function () {
            isPannable = _moveMenu.selected;
        }
    };

    var _zorepostionMenu = {
        label: "还原坐标点",
        action: function () {
            moveToDefault();
        }
    };

    var _updateMenu = {
        label: "刷新",
        action: function () {
            invalidateAll();
        }
    };
    contextMenu = new ht.widget.ContextMenu();
    contextMenu.addTo(g2d.getView());
    contextMenu.afterShow = function () {
    };
    contextMenu.afterHide = function () {
    };
    contextMenu.beforeShow = function () {
        var slist = new ht.List();
        _playbackMenu.selected = _playbackmode;
        var l = dataModel.getSelectionModel().size();
        if (l == 0) {
            slist.add(_tagsMenu);
            slist.add(_ntrendMenu);
            slist.add(_nhistrendMenu);

            slist.add(_zoominMenu);
            slist.add(_zoomoutMenu);
            slist.add(_zoomresetMenu);
            slist.add(_autofillMenu);
            slist.add(_zorepostionMenu);
            if (!iframe) {
                slist.add(_moveMenu);
            }
            if (showPlayback)
               slist.add(_playbackMenu);
            slist.add(_updateMenu);
        }
        else if (l == 1) {
            var seldata = dataModel.getSelectionModel().getFirstData();
            if (seldata) {
                var tags = new Array();
                var histags = new Array();
                var tag;

                if (seldata.a("tagname")) {
                    tag = seldata.a("tagname");
                    if (tag != "") {
                        var tagobj = db[tag];
                        if (tagobj) {
                            _tagnameMenu.label = tag + (tagobj.desc == "" ? "" : "(" + tagobj.desc + ")");
                            _tagnameMenu.tagname = tag;
                            slist.add(_tagnameMenu);
                            tags.push(tag);
                            if (tagobj.hs)
                                histags.push(tag);
                        }
                    }
                }

                if (seldata.a("vistagname")) {
                    tag = seldata.a("vistagname");
                    if (tag != "") {
                        if (!ar_contains(tags, tag)) {
                            var tagobj = db[tag];
                            if (tagobj) {
                                _vistagnameMenu.label = tag + (tagobj.desc == "" ? "" : "(" + tagobj.desc + ")");
                                _vistagnameMenu.tagname = tag;
                                slist.add(_vistagnameMenu);
                                tags.push(tag);
                            }

                            if (tagobj.hs)
                                histags.push(tag);
                        }
                    }
                }

                if (seldata.a("enbtagname")) {
                    tag = seldata.a("enbtagname");
                    if (tag != "") {
                        if (!ar_contains(tags, tag)) {
                            var tagobj = db[tag];
                            if (tagobj) {
                                _enbagnameMenu.label = tag + (tagobj.desc == "" ? "" : "(" + tagobj.desc + ")");
                                _enbagnameMenu.tagname = tag;
                                slist.add(_enbagnameMenu);
                                tags.push(tag);

                                if (tagobj.hs)
                                    histags.push(tag);
                            }
                        }
                    }
                }

                if (seldata.a("flashtagname")) {
                    tag = seldata.a("flashtagname");
                    if (tag != "") {
                        if (!ar_contains(tags, tag)) {
                            var tagobj = db[tag];
                            if (tagobj) {
                                _flashtagnameMenu.label = tag + (tagobj.desc == "" ? "" : "(" + tagobj.desc + ")");
                                _flashtagnameMenu.tagname = tag;
                                slist.add(_flashtagnameMenu);
                                tags.push(tag);

                                if (tagobj.hs)
                                    histags.push(tag);
                            }
                        }
                    }
                }

                for (var i = 1; i < 10; i++) {
                    if (seldata.a("tagname" + i.toString())) {
                        tag = seldata.a("tagname" + i.toString());
                        if (tag != "") {
                            if (!ar_contains(tags, tag)) {
                                var tagobj = db[tag];
                                if (tagobj) {
                                    var tmenu = eval("_tagnameMenu" + i.toString());
                                    if (tmenu) {
                                        tmenu.label = tag + (tagobj.desc == "" ? "" : "(" + tagobj.desc + ")");
                                        tmenu.tagname = tag;
                                        slist.add(tmenu);
                                        tags.push(tag);
                                        if (tagobj.hs)
                                            histags.push(tag);
                                    }
                                }
                            }
                        }
                    }
                }
                if (tags.length > 0) {
                    _trendMenu.tags = tags.join(",");
                    _copytagnameMenu.tags = tags.join("\n");;
                    slist.add(_copytagnameMenu);

                    slist.add(_trendMenu);
                }

                if (histags.length > 0) {
                    _histrendMenu.tags = histags.join(",");;
                    slist.add(_histrendMenu);
                }

            }
        }

        if (slist.size() == 0) {
            slist.add(_tagsMenu);
            slist.add(_zoominMenu);
            slist.add(_zoomoutMenu);
            slist.add(_zoomresetMenu);
            slist.add(_autofillMenu);
            slist.add(_zorepostionMenu);
            if (!iframe) {
                slist.add(_moveMenu);
            }
            if (showPlayback)
               slist.add(_playbackMenu);
            slist.add(_updateMenu);
        }

        if (userlevel >= 100)
            slist.add(_editor);
        contextMenu.setItems(slist.toArray());
    };
}

runtime_initContextMenu();