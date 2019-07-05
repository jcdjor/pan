/*
* Created by phpstrom
* USER: 冷魅蘇
* Date: 2019/5/3
* Time: 0:56
*/
function getData(userPath){
    $.get(servicePath + "FilesServlet",
        {
            path: userPath,
            time: new Date().getTime() //加随机参数解决缓存问题
        },
        function (res,status) {
            var obj = res.data.list;
            window.console.log(obj);
            //启动表格
            startTable(obj);
        },
        "json"
    );
}
//初始化dataTanles的功能
$.extend($.fn.dataTable.defaults,{
    //中文语言设置
    searching: true,       //搜索
    ordering: true,        //排序
    scrollX: false,         //水平滚动条
    scrollY: false          //垂直滚动条
});
//表格生成函数
function startTable(obj) {
    $('#example').DataTable({
        language: {
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },
        data: obj,
        columns: [
            //文件信息
            {
                title:"文件名",
                data: null,
                render: function (data, type, row, meta) {    //data:当前的数据，tpye：当前数据，row：当前行所有数据
                    var note = '<div class="checkbox pull-left"><label> <input name="file_' + meta.row + '" id="file_' + meta.row + '" type="checkbox" value="' + meta.row + '"></label></div>';

                    if (row.type == "fa-folder-o"){
                        note += '<div class="pull-left" onclick="changeDir(\'' + row.filePath + '/' + row.fileName + '\')"><a href="javascript:void(0)"><i class="fa ' + row.type + ' fa-3x" aria-hidden="true"></i></a></div><div class="pull-left" style="line-height: 35px;margin-left: 15px;" onclick="changeDir(\'' + row.filePath + '/' + row.fileName + '\')"><a href="javascript:void(0)">' + row.fileName + '</a></div>';
                    }else{
                        note += '<div class="pull-left"><a href="javascript:void(0)"><i class="fa ' + row.type + ' fa-3x" aria-hidden="true"></i></a></div><div class="pull-left" style="line-height: 35px;margin-left: 15px;"><a href="javascript:void(0)">' + row.fileName + '</a></div>';
                    }

                    note += '<div class="pull-right" style="line-height: 35px;margin-right: 30px;">';
                    //如果是文件夹，不显示现在下载按钮
                    if (row.type != "fa-folder-o"){
                        note += '<a href="' + servicePath + 'DownloadServlet?fileName=' + row.fileName + '&filePath=' + row.filePath + '"><button class="btn btn-success btn-xs" type="button"><i class="fa fa-arrow-circle-down"></i>下载</button></a><button class="btn btn-info btn-xs" type="button" onclick="share(\'' + row.filePath + '\',\'' + row.fileName + '\')"><i class="fa fa-share-square-o"></i>分享</button>';
                    }
                    //分享和删除按钮
                    note += '<button class="btn btn-danger btn-xs" type="button" onclick="deleteFile(\'' + row.filePath + '\',\'' + row.fileName + '\')"><i class="fa fa fa-times"></i>删除</button></div>';
                    return note;
                }
            },
            //大小
            { title:"大小",data: 'size' },
            //上传时间
            { title:"上传时间",data: 'date'}
        ],
    });
}
//文件类型搜索
function fileTpye(value) {
    $.get("phpSql/fileSql/fileType.php",
        {
            value:value,
            time: new Date().getTime() //加随机参数解决缓存问题
        },
        function (data,stuts) {
            if (stuts == "success"){
                if (data != "没有寻找到相关文件"){
                    $('#example').DataTable().destroy();
                    $('#example').empty();
                    startTable(data);
                }else {
                    alert("没有寻找到相关文件");
                }
                window.console.log(data);
            }else {
                alert("寻找文件失败");
            }
        },
        "json"
    );
}
//返回我的网盘，返回到userPath
function backPath() {
    var userPath = $.cookie('userPath');
    $('#example').DataTable().destroy();
    $('#example').empty();
    getData(userPath);
}
//我的分享
function myShare() {
    $.get("phpSql/fileSql/myShare.php",
        {
            time: new Date().getTime() //加随机参数解决缓存问题
        },
        function (data,stuts) {
            if (stuts == "success"){
                if (data != "没有寻找到相关文件"){
                    $('#example').DataTable().destroy();
                    $('#example').empty();
                    shareTable(data);
                    window.console.log(data);
                }else {
                    alert("你没有分享任何文件，快去分享吧");
                }
            }else {
                alert("服务器异常，寻找文件失败");
            }
        },
        "json"
    );
}
//公共资源
function publicAssets(){
    $.get("phpSql/fileSql/publicAssets.php",
        {
            time: new Date().getTime() //加随机参数解决缓存问题
        },
        function (data,stuts) {
            if (stuts == "success"){
                if (data != "没有寻找到相关文件"){
                    $('#example').DataTable().destroy();
                    $('#example').empty();
                    shareTable(data);
                    window.console.log(data);
                }else {
                    alert("公共资源没有任何文件，快去贡献一些吧");
                }
            }else {
                alert("服务器异常，寻找文件失败");
            }
        },
        "json"
    );
}
//我的分享和公共资源不显示 分享按钮
function shareTable(obj) {
    $('#example').DataTable({
        language: {
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },
        data: obj,
        columns: [
            //文件信息
            {
                data: null,
                render: function (data, type, row, meta) {    //data:当前的数据，tpye：当前数据，row：当前行所有数据
                    var note = '<div class="checkbox pull-left"><label> <input name="file_' + meta.row + '" id="file_' + meta.row + '" type="checkbox" value="' + meta.row + '"></label></div>';


                    note += '<div class="pull-left"><a href="javascript:void(0)"><i class="fa ' + row.type + ' fa-3x" aria-hidden="true"></i></a></div><div class="pull-left" style="line-height: 35px;margin-left: 15px;"><a href="javascript:void(0)">' + row.fileName + '</a></div>';

                    note += '<div class="pull-right" style="line-height: 35px;margin-right: 30px;">';
                    //如果是文件夹，不显示现在下载按钮

                    note += '<a href="' + servicePath + 'DownloadServlet?fileName=' + row.fileName + '&filePath=' + row.filePath + '"><button class="btn btn-success btn-xs" type="button"><i class="fa fa-arrow-circle-down"></i>下载</button></a>';

                    return note;
                }
            },
            //大小
            { data: 'size' },
            //上传时间
            { data: 'date' }
        ],
    });
}