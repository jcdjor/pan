<?php
/*
* Created by phpstrom
* USER: 冷魅蘇
* Date: 2019/5/7
* Time: 18:00
* 删除数据库里的文件记录
*/
@session_start();
header("Content-Type: text/html;charset=utf-8");
//文件目录
$filePath = $_POST['filePath'];
//文件名
$fileName = $_POST['fileName'];      //文件保存目录
//拼接目录
$path = $filePath.'/'.$fileName;
//连接数据库
require_once("../conn.php");

//查询数据库，查询数据库中是否存在这个文件，如果查询存在，那么这是一个文件
$sql = "select * from filelist where fileName='$fileName' and path='$filePath'";

$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) != 0){     //如果不为0，那说明这是一个文件
    //删除文件列表里的记录
    $sql = "delete from filelist where fileName='$fileName' and path='$filePath';";
    //删除分享列表里的记录
    $sql .= "delete from sharefile where fileName='$fileName' and path='$filePath'";
    //使用 mysqli_multi_query 函数执行多条语句
    if(mysqli_multi_query($conn,$sql)){
        echo "数据删除成功";
    }else{
        echo "服务器异常，数据删除失败";
    }
}else{
    //使用 link 匹配分级目录，删除文件列表里的记录
    $sql = "delete from filelist where path like '$path%'";
    //使用 link 匹配分级目录，删除分享列表里的记录
    $sql = "delete from sharefile where path like '$path%'";
    if(mysqli_multi_query($conn,$sql)){
        echo "数据删除成功";
    }else{
        echo "服务器异常，数据删除失败";
    }
}
mysqli_close($conn);






