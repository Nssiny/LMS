<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>LMS | 首页</title>
    <link rel="shortcut icon" href="img/gt_favicon.png">

    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

    <!-- Toastr style -->
    <link href="css/plugins/toastr/toastr.min.css" rel="stylesheet">

    <!-- Gritter -->
    <!-- <link href="js/plugins/gritter/jquery.gritter.css" rel="stylesheet"> -->

    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

</head>

<body>
    <div id="wrapper">
        <jsp:include page="/public/sidebar.jsp"></jsp:include>

        <div id="page-wrapper" class="gray-bg dashbard-1">
            <jsp:include page="/public/nav-top.jsp"></jsp:include>
            <div class="wrapper wrapper-content animated fadeInRight">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="ibox float-e-margins">
                            <div class="ibox-content text-center p-md">

                                <h2><span class="text-navy">LMS - 图书馆管理系统</span></h2>

                                <p>
                                    中山大学图书馆创办于1924年，是中山大学信息资源与服务中心。近年来紧密围绕中山大学教学与科研需要，持续进行信息资源建设，不断发展和完善信息资源服务平台；拓展、完善图书馆信息服务功能，提供多层次、高水平的信息资源服务。
                                </p>


                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-6">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <h5>借阅排行榜 <small>（月）</small></h5>
                            </div>
                            <div class="ibox-content">
                                <table class="table table-hover no-margins">
                                    <thead>
                                    <tr>
                                        <th>名次</th>
                                        <th>题名</th>
                                        <th>著者</th>
                                        <th>提升名次</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td><small>1</small></td>
                                        <td>孤独是一座岛</td>
                                        <td>安逸著</td>
                                        <td class="text-navy"> <i class="fa fa-level-up"></i> 24% </td>
                                    </tr>
                                    <tr>
                                        <td><small>2</small></td>
                                        <td>少年巡抚 </td>
                                        <td>蒙莎著</td>
                                        <td class="text-navy"> <i class="fa fa-level-up"></i> 66% </td>
                                    </tr>
                                    <tr>
                                        <td><small>3</small></td>
                                        <td>路随天去 </td>
                                        <td>秦敬香著</td>
                                        <td class="text-navy"> <i class="fa fa-level-up"></i> 54% </td>
                                    </tr>
                                    <tr>
                                        <td><small>4</small></td>
                                        <td>鬼吹灯. 7, 怒晴湘西 </td>
                                        <td>天下霸唱著</td>
                                        <td class="text-navy"> <i class="fa fa-level-up"></i> 12% </td>
                                    </tr>
                                    <tr>
                                        <td><small>5</small></td>
                                        <td>近战高手. 4, 扬名云端 </td>
                                        <td>蝴蝶蓝著</td>
                                        <td class="text-navy"> <i class="fa fa-level-up"></i> 22% </td>
                                    </tr>
                                    <tr>
                                        <td><small>6</small></td>
                                        <td>故事的结局早已写在开头 </td>
                                        <td>蒋方舟著</td>
                                        <td class="text-navy"> <i class="fa fa-level-up"></i> 66% </td>
                                    </tr>
                                    <tr>
                                        <td><small>7</small></td>
                                        <td>知青</td>
                                        <td>梁晓声著</td>
                                        <td class="text-navy"> <i class="fa fa-level-up"></i> 23% </td>
                                    </tr>
                                    <tr>
                                        <td><small>8</small></td>
                                        <td>幸福咒 </td>
                                        <td>曾楚桥著</td>
                                        <td class="text-navy"> <i class="fa fa-level-up"></i> 23% </td>
                                    </tr>
                                    <tr>
                                        <td><small>9</small></td>
                                        <td>苍茫独步时 </td>
                                        <td>盛思吾著</td>
                                        <td class="text-navy"> <i class="fa fa-level-up"></i> 23% </td>
                                    </tr>
                                    <tr>
                                        <td><small>10</small></td>
                                        <td>半城风月 : 终章 </td>
                                        <td>十四郎著</td>
                                        <td class="text-navy"> <i class="fa fa-level-up"></i> 23% </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                    </div>

                    <div class="col-lg-6">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <h5>新书排行榜 </h5>
                            </div>
                            <div class="ibox-content p-md">
                                <table class="table table-hover no-margins">
                                    <thead>
                                    <tr>
                                        <th>名次</th>
                                        <th>题名</th>
                                        <th>著者</th>
                                        <th>提升名次</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td><small>1</small></td>
                                        <td>普罗旺斯的另一生 </td>
                                        <td>周星余著</td>
                                        <td class="text-navy"> <i class="fa fa-level-up"></i> 24% </td>
                                    </tr>
                                    <tr>
                                        <td><small>2</small></td>
                                        <td>古楼 </td>
                                        <td>郑新潮, 郑昀著</td>
                                        <td class="text-navy"> <i class="fa fa-level-up"></i> 66% </td>
                                    </tr>
                                    <tr>
                                        <td><small>3</small></td>
                                        <td>石狗记 </td>
                                        <td>张育斌著</td>
                                        <td class="text-navy"> <i class="fa fa-level-up"></i> 54% </td>
                                    </tr>
                                    <tr>
                                        <td><small>4</small></td>
                                        <td>回族人家 </td>
                                        <td>元康著</td>
                                        <td class="text-navy"> <i class="fa fa-level-up"></i> 12% </td>
                                    </tr>
                                    <tr>
                                        <td><small>5</small></td>
                                        <td>最初的你是我最后的爱 </td>
                                        <td>夜蔓著</td>
                                        <td class="text-navy"> <i class="fa fa-level-up"></i> 22% </td>
                                    </tr>
                                    <tr>
                                        <td><small>6</small></td>
                                        <td>债情 </td>
                                        <td>奚宝书著</td>
                                        <td class="text-navy"> <i class="fa fa-level-up"></i> 66% </td>
                                    </tr>
                                    <tr>
                                        <td><small>7</small></td>
                                        <td>水灵</td>
                                        <td>琼瑶著</td>
                                        <td class="text-navy"> <i class="fa fa-level-up"></i> 23% </td>
                                    </tr>
                                    <tr>
                                        <td><small>8</small></td>
                                        <td>六个梦</td>
                                        <td>琼瑶著</td>
                                        <td class="text-navy"> <i class="fa fa-level-up"></i> 23% </td>
                                    </tr>
                                    <tr>
                                        <td><small>9</small></td>
                                        <td>凤华无双 </td>
                                        <td>幕雪著</td>
                                        <td class="text-navy"> <i class="fa fa-level-up"></i> 23% </td>
                                    </tr>
                                    <tr>
                                        <td><small>10</small></td>
                                        <td>老街的生命 </td>
                                        <td>林家品著</td>
                                        <td class="text-navy"> <i class="fa fa-level-up"></i> 23% </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                    </div>


                </div>
            </div>
            <jsp:include page="/public/footer.jsp"></jsp:include>
        </div>
    </div>

    <!-- Mainly scripts -->
    <script src="js/jquery-2.1.1.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

    <!-- Custom and plugin javascript -->
    <script src="js/inspinia.js"></script>
    <script src="js/plugins/pace/pace.min.js"></script>

    <!-- jQuery UI -->
    <script src="js/plugins/jquery-ui/jquery-ui.min.js"></script>

    <!-- GITTER社交聊天 -->
    <!-- <script src="js/plugins/gritter/jquery.gritter.min.js"></script> -->

    <!-- Toastr -->
    <script src="js/plugins/toastr/toastr.min.js"></script>

    <script>
        $(document).ready(function(){
/*          setTimeout(function(){
                toastr.options = {
                    closeButton: true,
                    progressBar: true,
                    showMethod: 'slideDown',
                    timeout: 3000
                };
                toastr.success('Responsive Admin Theme', 'Welcome to INSPINIA');
            },1300); */
        });
    </script>


</body>
</html>