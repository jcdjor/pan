<?php
/*
* Created by phpstrom
* USER: 冷魅蘇
* Date: 2019/5/6
* Time: 13:17
* 文件上传数据库写入或更新
*/
@session_start();
header("Content-Type: text/html;charset=utf-8");
//文件信息
$file = $_FILES['myfile'];
//提前文件信息
$path = $_POST['path'];      //文件保存目录
$fileName = $file['name'];      //文件名
$type = explode('/',$file['type'])[0];      //文件类型
$size = $file['size'];      //文件大小
$user = $_SESSION['user'];   //文件所属的用户
$time = date('Y-m-d H:i');      //文件上传的时间

require_once("../conn.php");

$sql = "select * from filelist where fileName='$fileName' and path='$path' and user='$user'";

$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) == 0) {        //上传的文件不是相同的，则插入数据
    //插入文件信息
    $sql = "insert into filelist (time, fileName, size, path, type, user)
values ('$time', '$fileName', '$size', '$path', '$type', '$user')";

    if (mysqli_query($conn, $sql)) {
        echo "新的上传记录插入成功";
    } else {
        mysqli_close($conn);
        echo "服务器异常";
    }
} else {        //更新数据
    $sql = "update filelist set time='$time',size='$size',type='$type' where fileName='$fileName' and path='$path' and user='$user'";
    if (mysqli_query($conn, $sql)) {
        echo "数据更新成功";
    } else {
        mysqli_close($conn);
        echo "服务器异常";
    }
}
mysqli_close($conn);