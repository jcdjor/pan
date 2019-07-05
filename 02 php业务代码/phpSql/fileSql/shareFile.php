<?php
/*
* Created by phpstrom
* USER: 冷魅蘇
* Date: 2019/5/14
* Time: 20:19
*/
@session_start();
header("Content-Type: text/html;charset=utf-8");
//获取文件目录
$filePath = $_POST['filePath'];
//获取文件名
$fileName = $_POST['fileName'];
//分享文件的用户
$user = $_SESSION['user'];

//导入连接数据库
require_once("../conn.php");

//查询数据库，得到相应的信息
$sql = "select * from filelist where fileName='$fileName' and path='$filePath'";
//开始查询
$result = mysqli_query($conn, $sql);
//如果查询到结构
if (mysqli_num_rows($result) > 0) {
    // 输出数据
    while($row = mysqli_fetch_assoc($result)) {
        $sql = "insert into sharefile (time, fileName, size, path, type, user)
values ('".$row['time']."', '".$row['fileName']."', '".$row['size']."', '".$row['path']."', '".$row['type']."', '".$row['user']."')";
    }
    if (mysqli_query($conn, $sql)) {
        echo "分享成功";
    } else {
        echo "服务器异常，文件不可分享1";
    }
} else {
    echo "服务器异常，文件不可分享2";
}

mysqli_close($conn);

