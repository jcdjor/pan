<?php
/*
* Created by phpstrom
* USER: 冷魅蘇
* Date: 2019/5/6
* Time: 20:03
*/
@session_start();
header("Content-Type: text/html;charset=utf-8");
$user = isset($_POST['user']) ? htmlspecialchars($_POST['user']) : '';
$pwd = isset($_POST['pwd']) ? htmlspecialchars($_POST['pwd']) : '';
$userPath = "/".$user;

//连接数据库
require_once("../conn.php");

$sql = "select * from user where user_name='$user'";


$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
    while($row = mysqli_fetch_array($result)){
        if($pwd != $row['pwd']){
            echo "用户密码错误";
        }else{
            $_SESSION['user'] = $user;
            echo "密码正确，正在登录...";
        }
    }
} else {
    echo "用户不存在";
}
mysqli_close($conn);
