<?php
/*
* Created by phpstrom
* USER: 冷魅蘇
* Date: 2019/5/6
* Time: 14:03
*/

$servername = "localhost";
$username = "root";
$password = "123456";
$dbname = "pan";

// 创建连接
$conn = new mysqli($servername, $username, $password, $dbname);
mysqli_query($conn , "set names utf8");

// 检测连接
if ($conn->connect_error) {
    die("服务器异常 " . $conn->connect_error);
}

