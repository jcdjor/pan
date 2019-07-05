<?php
/*
* Created by phpstrom
* USER: 冷魅蘇
* Date: 2019/5/6
* Time: 16:24
*/
/**
 * 用户注册时，SQL操作
 */
@session_start();
header("Content-Type: text/html;charset=utf-8");
$userName = isset($_POST['user']) ? htmlspecialchars($_POST['user']) : '';
$password = isset($_POST['password']) ? htmlspecialchars($_POST['password']) : '';
$userPath = "/".$userName;

//连接数据库
require_once("../conn.php");

$sql = "select * from user where user_name='$userName'";

$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) == 0) {
    //用户注册插入数据
    $sql = "insert into user (user_name, pwd, user_path)
values ('$userName', '$password', '$userPath')";

    if (mysqli_query($conn, $sql)) {
        $_SESSION['user'] = $userName;
        echo "注册成功，正在登录...";
    } else {
        mysqli_close($conn);
        echo "服务器异常";
    }
} else {
    echo "用户名已被注册";
}
mysqli_close($conn);
?>
