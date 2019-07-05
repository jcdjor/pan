<?php
/*
* Created by phpstrom
* USER: 冷魅蘇
* Date: 2019/5/7
* Time: 21:16
* 我的文档、视频、图片、收藏等查询
*/
@session_start();
header("Content-Type: text/html;charset=utf-8");
$value= $_GET['value'];      //文件类型
$userName = $_SESSION['user'];       //所属用户

require_once("../conn.php");
$sql = "select * from filelist";
switch ($value){
    case "text":
        $sql = "select * from filelist where type in ('application','message','text','x-world') and user='$userName'";     break;
    case "image":
        $sql = "select * from filelist where type ='image' and user='$userName'";     break;
    case "audio":
        $sql = "select * from filelist where type ='audio' and user='$userName'";     break;
    case "video":
        $sql = "select * from filelist where type ='video' and user='$userName'";     break;
    default:
        $sql = "select * from filelist where user='$userName'";     break;
}
$arr = array(array());      //用来存放数据的二维数组
$num = 0;    //方便对数组赋值

$result = mysqli_query($conn,$sql);


while($row = mysqli_fetch_array($result)) {
    $arr[$num]['date'] = $row['time'];
    $arr[$num]['fileName'] = $row['fileName'];
    $arr[$num]['filePath'] = $row['path'];
    $arr[$num]['size'] = formatSizeUnits($row['size']);
    $arr[$num]['type'] = formatType($row['fileName']);
    $num++;
}
if (isset($arr[0]['date'])){
    echo json_encode($arr);
}else{
    echo json_encode("没有寻找到相关文件");
}
mysqli_close($conn);


function formatSizeUnits($bytes){
    if ($bytes >= 1073741824) {
        $bytes = number_format($bytes / 1073741824, 2) . 'GB';
    } else if ($bytes >= 1048576) {
        $bytes = number_format($bytes / 1048576, 2) . 'MB';
    } else if ($bytes >= 1024) {
        $bytes = number_format($bytes / 1024, 2) . 'kB';
    } else if ($bytes > 1) {
        $bytes = $bytes . 'B';
    } else if ($bytes == 1) {
        $bytes = $bytes . 'B';
    } else {
        $bytes = '0B';
    }
    return $bytes;
}

function formatType($file){
    $type = "fa-file-image-o";
    if(strpos($file,".txt")) {
        $type = "fa-file-text-o";
    }else if(strpos($file,".doc")!==false || strpos($file,".docx")!==false) {
        $type = "fa-file-word-o";
    }else if(strpos($file,".xls")!==false || strpos($file,".xlsx")!==false) {
        $type = "fa-file-excel-o";
    }else if(strpos($file,".ppt")!==false || strpos($file,".pptx")!==false) {
        $type = "fa-file-powerpoint-o";
    }else if(strpos($file,".png")!==false || strpos($file,".jpg")!==false || strpos($file,".jpeg")!==false) {
        $type = "fa-file-image-o";
    }else if(strpos($file,".mp3")!==false || strpos($file,".amr")!==false) {
        $type = "fa-file-audio-o";
    }else if(strpos($file,".mp4")!==false || strpos($file,".avi")!==false || strpos($file,".wmv")!==false) {
        $type = "fa-file-video-o";
    }if(strpos($file,".pdf")!==false) {
        $type = "fa-file-pdf-o";
    }if(strpos($file,".zip")!==false || strpos($file,".rar")!==false || strpos($file,".7z")!==false) {
        $type = "fa-file-archive-o";
    }else {
        $type = "fa-file-text-o";
    }
    return $type;
}

