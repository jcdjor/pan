/*
* Created by phpstrom
* USER: 冷魅蘇
* Date: 2019/5/3
* Time: 20:35
*/
//上传文件
function upload() {
    var file = $("#myfile").val();
    if(file == null || file == ""){
        return alert("请选择文件");
    }
    //获取状态显示的div
    var alertDiv = $('#uploadModal div.alert');
    //开始显现上传状态
    alertDiv.text("");
    alertDiv.removeClass("alert-danger");
    alertDiv.addClass("alert-info");
    alertDiv.text("请稍等，正在上传文件...");

    //formData里面已经有myfile了
    var formData = new FormData($('#uploadForm')[0]);
    //将上传的路径希儿formData
    formData.append("path",$.cookie("userPath"));
    //post上传
    $.ajax({
        type: 'post',
        url: servicePath + "UploadServlet",
        data: formData,
        async:false,
        cache: false,
        processData: false,
        contentType: false,
        success: function(data){
            window.console.log(data);
            alertDiv.removeClass("alert-info");
            alertDiv.text("");
            alert(data);
            $('#uploadModal').modal('hide');
            $('#example').DataTable().destroy();
            $('#example').empty();
            getData($.cookie("userPath"));
            uploadSql(formData);
        },
        error:function () {
            alertDiv.removeClass("alert-info");
            alertDiv.addClass("alert-danger");
            alertDiv.text("对不起！文件上传失败。");
        }
    });
}
//删除文件
function deleteFile(filePath,fileName) {
    //提示框确认是否删除
    if (!confirm("确定要删除该文件或文件夹？")){
        return;
    }
    $.ajaxSettings.async = false;
    $.get(servicePath + "DeleteServlet",
        {
            path: filePath +'/'+ fileName,
            time: new Date().getTime() //加随机参数解决缓存问题
        },
        function (res,status) {
            if (res == "success") {
                //销毁表
                $('#example').DataTable().destroy();
                $('#example').empty();
                //重新生成表
                getData($.cookie("userPath"));
                deleteSql(filePath,fileName);
            }else {
                alert("删除失败");
            }
        }
    );
}
//创建文件夹
function mkdirs(){
    //获取新建文件夹的名称
    var dirPath = $('#dirPath').val();
    $.get(servicePath + "MkDirsServlet",
        {
            dirPath: $.cookie("userPath") +'/'+ dirPath,
            time: new Date().getTime() //加随机参数解决缓存问题
        },
        function (res,status) {
            if (res == "success") {
                window.console.log(res);
                alert("新建文件夹成功");
                $('#mkdirsModal').modal('hide');
                $('#example').DataTable().destroy();
                $('#example').empty();
                getData($.cookie("userPath"));
            }else {
                alert("新建失败");
            }
        }
    );
}
//文件分享
function share(filePath,fileName) {
    //提示框确认是否删除
    if (!confirm("确定要分享该文件？")){
        return;
    }
    $.post("phpSql/fileSql/shareFile.php",
        {
            filePath: filePath,
            fileName: fileName
        },
        function (res) {
            if (res == "分享成功") {
                alert("文件已分享到公共资源");
            }else {
                alert(res);
            }
        }
    );
}
//改变用户目录所在情况，并修改面包屑导航
function changeDir(dirPath) {
    //调用函数设置面包屑导航
    breadcrumbCahnge(dirPath);
    //改变用户的目录的 cookie
    $.cookie('userPath', null, { path: '/'});
    $.cookie('userPath', dirPath, { expires: 7,path: '/'});
    $('#example').DataTable().destroy();
    $('#example').empty();
    getData(dirPath);
}
//面包屑导航[id=breadcrumb]生成函数
function breadcrumbCahnge(dirPath) {
    //分解目录，方便为面包屑导航进行修改
    var arr = dirPath.split('/');       //如{"","jcdjor","app"}
    //最后一个目录，即改变后的目录，单独处理，故选择减1，因为最后一个目录不需要加改变目录事件
    var arrLen = arr.length;
    //初始化目录的字符串
    var str = "";
    //获取面包屑导航的ol标签
    var ol = $("#breadcrumb");
    //先把面包屑导航中的内容销毁
    ol.empty();
    if (arrLen == 2){       //arrLen 为 2，代表用户在主目录
        ol.append("<li class=\"active\" onclick='backPath()'><a href=\"javascript:void(0)\">我的网盘</a></li>");
    }else {
        //从 i=1 开始，因为数组第一个上空的
        for (var i=1; i<arrLen; i++ ){
            //如果是第一个目录，是用户主目录，显示的信息改成我的网盘
            if(i==1){
                str += "/" + arr[i];
                ol.append("<li onclick=\"changeDir('"+str+"')\"><a href=\"javascript:void(0)\">我的网盘</a></li>");
                continue;
            }
            //如果是最后目录，是用户所到的目录，不需要
            if(i==(arrLen-1)){
                str += "/" + arr[i];
                ol.append("<li class=\"active\">"+arr[i]+"</li>");
                break;
            }
            //拼接目录
            str += "/" + arr[i];
            ol.append("<li onclick=\"changeDir('"+str+"')\"><a href=\"javascript:void(0)\">"+arr[i]+"</a></li>");
        }
    }
}

    


