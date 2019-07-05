<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>退出登录</title>
</head>
<body>

<?php
    @session_start();
    unset($_SESSION['user']);
?>

<script src="lib/view/js/jquery.js"  type="text/javascript"></script>
<script src="lib/view/js/jquery.cookie.js"  type="text/javascript"></script>
<script>
    $.cookie('userName',null, { path: '/'});
    $.cookie('userPath', null, { path: '/'});
    window.location.href = "login.html";
</script>
</body>
</html>