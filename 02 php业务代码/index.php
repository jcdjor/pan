<!--  
Created by phpstrom
USER: 冷魅蘇
Date: 2019/4/29
Time: 17:57
-->
<!-- head -->
<?php
    @session_start();
    if (!isset($_SESSION['user'])){
        echo '<script>alert("对不起，请先登录");window.location.href = "login.html"</script>';
    }
?>
<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="jcdjor">
    <meta name="keyword" content="jcdjor">
    <link rel="shortcut icon" href="img/favicon.ico">

    <title>个人网盘</title>
    <!--  注意顺序  -->
    <!-- DataTables CSS -->

    <link rel="stylesheet" href="lib/view/assets/DataTables-1.10.15/media/css/dataTables.bootstrap4.min.css">
    <!-- Bootstrap CSS -->
    <link href="lib/view/css/bootstrap.min.css" rel="stylesheet">
    <link href="lib/view/css/bootstrap-reset.css" rel="stylesheet">

    <!--字体图标库 external css-->
    <link href="lib/view/assets/font-awesome/css/font-awesome.css" rel="stylesheet" />

    <!-- 主页CSS -->
    <link href="lib/view/css/style.css" rel="stylesheet">
    <link href="lib/view/css/index.css" rel="stylesheet" />

    <!-- HTML5 shiv and Respond.js 是让 IE8 支持 HTML5 的提示工具和媒体查询 -->
    <!--[if lt IE 9]>
    <script src="lib/view/js/html5shiv.js"></script>
    <script src="lib/view/js/respond.min.js"></script>
    <![endif]-->

</head>
<body >

<section id="container">
    <!--header start-->
    <header class="header white-bg">
        <div class="sidebar-toggle-box">
            <div data-original-title="收起菜单" data-placement="right" class="icon-reorder tooltips"></div>
        </div>
        <!--logo start-->
        <div class="pull-left logo" title="jcdjor" style="color: #ffffff;"><i class="fa fa-cloud fa-4x" aria-hidden="true"></i></div>
        <!--logo end-->
        <div class="top-nav">
            <!-- user menu start-->
            <ul class="nav pull-right top-menu">
                <li class="dropdown">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <img width="40" height="40" alt="root" src="img/favicon.ico">
                        <span class="username" id="username"><?php echo @$_SESSION['user']; ?></span>
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu extended logout">
                        <div class="log-arrow-up"></div>
                        <li><a href="#"><i class="icon-edit"></i>编辑</a></li>
                        <li><a href="#"><i class="icon-eye-open"></i>查看</a></li>
                        <li><a href="#"><i class="icon-cog"></i>设置头像</a></li>
                        <li><a href="logout.php"><i class="icon-key"></i>退出</a></li>
                    </ul>
                </li>
            </ul>
            <!-- user menu start end -->
        </div>
    </header>
    <!--header end-->

    <!-- 工具栏开始 -->
    <aside>
        <div id="sidebar"  class="nav-collapse">
            <!-- sidebar menu start-->
            <ul class="sidebar-menu" id="nav-accordion">
                <li class="on">
                    <a href="javascript:void(0)" onclick="backPath()">
                        <i class="fa fa-cloud"></i>
                        <span>我的网盘</span>
                    </a>
                </li>
                <li class="inMenu" onclick="fileTpye('all')">
                    <a href="javascript:void(0)">
                        <i class="fa fa-folder-o"></i>
                        <span>所有资料</span>
                    </a>
                </li>
                <li class="inMenu" onclick="fileTpye('text')">
                    <a href="javascript:void(0)">
                        &nbsp;<i class="fa fa-file-text"></i>
                        <span>&nbsp;我的文档</span>
                    </a>
                </li>
                <li class="inMenu" onclick="fileTpye('image')">
                    <a href="javascript:void(0)">
                        <i class="fa fa-picture-o"></i>
                        <span>我的图片</span>
                    </a>
                </li>
                <li class="inMenu" onclick="fileTpye('audio')">
                    <a href="javascript:void(0)">
                        <i class="fa fa-music"></i>
                        <span>&nbsp;我的音乐</span>
                    </a>
                </li>
                <li class="inMenu" onclick="fileTpye('video')">
                    <a href="javascript:void(0)">
                        <i class="fa fa-film"></i>
                        <span>我的视频</span>
                    </a>
                </li>
                <!--
                <li>
                    <a href="javascript:void(0)">
                        <i class="fa fa-star"></i>
                        <span>我的收藏(xxx)</span>
                    </a>
                </li>
                -->
                <li onclick="myShare()">
                    <a href="javascript:void(0)">
                        <i class="fa fa-share-square-o"></i>
                        <span>我的分享</span>
                    </a>
                </li>
                <li onclick="publicAssets()">
                    <a href="javascript:void(0)">
                        <i class="fa fa-rss"></i>
                        <span>公共资源</span>
                    </a>
                </li>
                <!--
                <li >
                    <a href="javascript:void(0)">
                        <i class="fa fa-trash-o"></i>
                        <span>回收站(xxx)</span>
                    </a>
                </li>
                -->
            </ul>
            <!-- 工具栏结束-->
        </div>
        <!-- 存储空间显示 -->
        <div class="capacity">
            <div class="progress progress-xs">
                <div style="width: 50%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="7.2403183937073" role="progressbar" class="progress-bar">
                    <span class="sr-only">50% Complete (success)</span>
                </div>
            </div>
            <div class="pull-left">
                <span>0.49</span> / 0.98 G
            </div>
        </div>
    </aside>
    <!-- head end-->
    <!-- main content start -->
    <section id="main-content">
        <section class="wrapper">
            <!-- 导航主题开始 -->
            <div class="row state-overview">
                <!-- 操作的大型按钮开始 -->
                <ul class="buttons pull-left">
                    <li>
                        <button class="btn btn-info" type="button" data-toggle="modal" data-target="#uploadModal">
                            <i class="fa fa-cloud-upload"></i>
                            上传文件                                </button>
                    </li>
                    <li>
                        <button class="btn btn-success" type="button" data-toggle="modal" data-target="#mkdirsModal">
                            <i class="fa fa-folder"></i>
                            新建文件夹                            </button>
<!--                        （文件夹合法检验未完成）-->
                    </li>
                    <!--
                    <li>
                        <button class="btn btn-danger" type="button" disabled>
                            <i class="fa fa-times (remove)"></i>
                            删除                          </button>
                    </li>
                    <li id="down">
                        <button class="btn btn-success" type="button" disabled></i>
                            下载                          </button>
                    </li>
                    <li>
                        <button class="btn btn-warning" type="button" disabled>
                            <i class="fa fa-random"></i>
                            移动                           </button>
                    </li>
                    <li id="share">
                        <button class="btn btn-info" type="button" disabled>
                            <i class="fa fa-share-square-o"></i>
                            分享                            </button>
                    </li>
                    -->
                </ul>
                <!-- 操作的大型按钮结束 -->
                <!-- 模态框（Modal）开始 -->
                <!-- 上传文件模态框 -->
                <div class="modal fade" id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header" style="background-color: #58c9f3;">
                                <h4 class="modal-title" id="myModalLabel">
                                    上传文件
                                </h4>
                            </div>
                            <div class="modal-body">
                                <form id="uploadForm" enctype="multipart/form-data" class="form-inline" role="form">
                                    <div class="form-group">
                                        <label class="sr-only" for="myfile">文件输入</label>
                                        <input type="file" id="myfile" name="myfile"/>
                                    </div>
                                    <div class="alert" style="margin-top: 20px;"></div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">取消
                                </button>
                                <button type="button" class="btn btn-primary" style="background-color: #58c9f3; border-color: #58c9f3;" onclick="upload()">
                                    上传文件
                                </button>

                            </div>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal -->
                </div>
                <!-- 创建文件夹模态框 -->
                <div class="modal fade" id="mkdirsModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header" style="background-color: #78CD51;">
                                <h4 class="modal-title">
                                    新建文件夹
                                </h4>
                            </div>
                            <div class="modal-body">
                                <form role="form">
                                    <div class="form-group">
                                        <label for="dirPath"><b>文件夹名称（请不要输入不合法的符号）</b></label>
                                        <input type="text" class="form-control" id="dirPath" placeholder="请输入文件夹名称" style="color: #000000">
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">取消
                                </button>
                                <button type="button" class="btn btn-primary" style="background-color: #78CD51; border-color: #78CD51;"  onclick="mkdirs()">
                                    新建文件夹
                                </button>
                            </div>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal -->
                </div>
                <!-- 模态框（Modal）结束 -->
                <!--  资料的大型按钮开始  -->
                <div class="toggle pull-right">
                    <button class="listBtn active"><i class="fa fa-list-ul"></i></button>
                    <button class="blockBtn "><i class="fa fa-th-large"></i></button>
                </div>
                <div class="mainMenu">
                    <a href="javascript:void(0)" onclick="backPath()">
                        <section class="panel">
                            <div class="symbol terques">
                                <i class="fa fa-cloud"></i>
                            </div>
                            <div class="value">
                                <h4>我的网盘</h4>
                            </div>
                        </section>
                    </a>
                </div>
                <div class="mainMenu" onclick="fileTpye('all')">
                    <a href="javascript:void(0)">
                        <section class="panel">
                            <div class="symbol yellow">
                                <i class="fa fa-folder-o"></i>
                            </div>
                            <div class="value">
                                <h4>所以资料</h4>
                            </div>
                        </section>
                    </a>
                </div>
                <div class="mainMenu" onclick="fileTpye('text')">
                    <a href="javascript:void(0)">
                        <section class="panel">
                            <div class="symbol blue">
                                <i class="fa fa-file-text"></i>
                            </div>
                            <div class="value">
                                <h4>我的文档</h4>
                            </div>
                        </section>
                    </a>
                </div>
                <div class="mainMenu" onclick="fileTpye('image')">
                    <a href="javascript:void(0)">
                        <section class="panel">
                            <div class="symbol red">
                                <i class="fa fa-picture-o"></i>
                            </div>
                            <div class="value">
                                <h4>我的图片</h4>
                            </div>
                        </section>
                    </a>
                </div>
                <div class="mainMenu" onclick="fileTpye('audio')">
                    <a href="javascript:void(0)">
                        <section class="panel">
                            <div class="symbol yellow">
                                <i class="fa fa-music"></i>
                            </div>
                            <div class="value">
                                <h4>我的音乐</h4>
                            </div>
                        </section>
                    </a>
                </div>
                <div class="mainMenu" onclick="fileTpye('video')">
                    <a href="javascript:void(0)">
                        <section class="panel">
                            <div class="symbol blue">
                                <i class="fa fa-film"></i>
                            </div>
                            <div class="value">
                                <h4>我的视频</h4>
                            </div>
                        </section>
                    </a>
                </div>
                <!--  资料的大型按钮开始  -->
            </div>
            <!-- 导航主题结束 -->
            <!--  files or dir view end -->
            <ol id="breadcrumb" class="breadcrumb" style="font-size: 1.5em">
                <!-- 通过技术生成 -->
            </ol>
            <div class="row">
                <table id="example" border="0" class="table .table-hover" width="100%">
                    <thead>
                        <tr>
                            <th width="73%">文件名</th>
                            <th>大小</th>
                            <th>上传时间</th>
                        </tr>
                    </thead>
                    <tfoot>
                        <tr>
                            <th width="73%">文件名</th>
                            <th>大小</th>
                            <th>上传时间</th>
                        </tr>
                    </tfoot>
                </table>
            </div>
            <!--  files or dir view end -->
        </section>
    </section>
    <!--main content end-->
</section>

<!-- js放在最后使页面加载速度更快 -->
<!-- 注意顺序 -->
<!-- DataTables JS -->
<script src="lib/view/js/jquery.js"  type="text/javascript"></script>
<script src="lib/view/js/jquery.cookie.js"  type="text/javascript"></script>
<script src="lib/view/assets/DataTables-1.10.15/media/js/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="lib/view/assets/DataTables-1.10.15/media/js/dataTables.bootstrap4.min.js" type="text/javascript"></script>
<script src="lib/view/js/bootstrap.min.js" type="text/javascript"></script>

<!-- 自定义js -->
<script src="lib/js/ai.js" type="text/javascript"></script>
<script src="lib/js/table.js" type="text/javascript"></script>
<script src="lib/js/tool.js" type="text/javascript"></script>
<script src="lib/js/sql.js" type="text/javascript"></script>
<script>
    $(document).ready(function(){
        //得到用户的目录所在的状态的 cookie，防止刷新
        var userPath = $.cookie("userPath");
        //生成表格，防止刷新后我就目录的改变
        getData(userPath);
        //通过函数生成面包屑导航[id=breadcrumb]
        breadcrumbCahnge(userPath);
        window.console.log(userPath);
    });
</script>

<!-- index js -->

</body>
</html>
<!--footer end-->