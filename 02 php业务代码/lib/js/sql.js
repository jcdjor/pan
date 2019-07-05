/*
* Created by phpstrom
* USER: 冷魅蘇
* Date: 2019/5/6
* Time: 12:46
*/
//上传文件的信息写入数据库
function uploadSql(formData) {
    $.ajax({
        type: 'post',
        url: "phpSql/fileSql/uploadSql.php",
        data: formData,
        async:false,
        cache: false,
        processData: false,
        contentType: false,
        success: function(data){
            window.console.log(data);
        },
        error:function () {
            alert("服务器异常，数据写入失败");
        }
    });
}
//删除文件，数据库删除数据
function deleteSql(filePath,fileName) {
    $.post("phpSql/fileSql/deleteSql.php",
        {
            filePath: filePath,
            fileName: fileName
        },
        function (res,status) {
            if (status == "success") {
                window.console.log(res);
            }else {
                alert("服务器异常，数据删除失败");
            }
        }
    );
    $.ajaxSettings.async = true;
}
