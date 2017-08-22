<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

    <title>LMS</title>
    <link rel="shortcut icon" href="img/gt_favicon.png">

    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

    <!-- Toastr style -->
    <link href="css/plugins/toastr/toastr.min.css" rel="stylesheet">
    <link href="css/plugins/dataTables/datatables.min.css" rel="stylesheet">

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
                        <div class="tabs-container">
                            <ul class="nav nav-tabs">
                                <li class="active"><a data-toggle="tab" href="tabs.html#tab-1"> 账号信息</a></li>
                                <li class=""><a data-toggle="tab" href="tabs.html#tab-2" onclick="currentPage()">当前流通</a></li>
                                <li class=""><a data-toggle="tab" href="tabs.html#tab-3" onclick="historyPage()">历史流通</a></li>
                                <li class=""><a data-toggle="tab" href="tabs.html#tab-4" onclick="collectionPage()">我的收藏</a></li>
                            </ul>
                            <div class="tab-content">
                                <div id="tab-1" class="tab-pane active">
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <h2 class="font-Bold m-b-xs">
                                                    基本信息
                                                </h2>
                                                <hr>
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <h4>ID：</h4>
                                                    </div>
                                                    <div class="col-md-9 text-muted" style="line-height:25px!important">
                                                        <shiro:principal/>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <h4>姓名：</h4>
                                                    </div>
                                                    <div class="col-md-9 text-muted" style="line-height:25px!important">
                                                        ${infoUser.name}
                                                    </div>
                                                </div>
                                                <div class="row">   
                                                    <div class="col-md-3">
                                                        <h4>联系电话：</h4>
                                                    </div>
                                                    <div class="col-md-9 text-muted" style="line-height:25px!important">
                                                        ${infoUser.phone}
                                                    </div>
                                                </div>
                                                <div class="row">   
                                                    <div class="col-md-3">
                                                        <h4>E-mail：</h4>
                                                    </div>
                                                    <div class="col-md-9 text-muted" style="line-height:25px!important">
                                                        ${infoUser.email}
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6 text-center">
                                                <div class="col-md-10">
                                                    <div class="widget-head-color-box navy-bg p-lg text-center">
                                                        <div class="m-b-md">
                                                        <h2 class="font-bold no-margins">借书证</h2>
                                                        </div>
                                                        <img src="img/a4.jpg" class="img-circle circle-border m-b-md" alt="profile">
                                                    </div>
                                                    <div class="widget-text-box">
                                                        <table class="table license-table">
                                                            <tbody>
                                                                <tr>
                                                                    <td>状态：</td>
                                                                    <td>${readerLicense.status}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>读者类型：</td>
                                                                    <td>${readerLicense.readerCategory.name}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>发证日期：</td>
                                                                    <td><fmt:formatDate value="${readerLicense.gmtAllocate}" pattern="yyyy-MM-dd" /></td>
                                                                </tr>
                                                                <tr>
                                                                    <td>失效日期：</td>
                                                                    <td><fmt:formatDate value="${readerLicense.gmtExpire}" pattern="yyyy-MM-dd" /></td>
                                                                </tr>
                                                                <tr>
                                                                    <td>违规次数：</td>
                                                                    <td>${readerLicense.penaltyCount}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>已借图书数量：</td>
                                                                    <td>${readerLicense.borrowed}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>最大借阅数量：</td>
                                                                    <td>${readerLicense.readerCategory.maxBookBorrow}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>最大续借次数：</td>
                                                                    <td>${readerLicense.readerCategory.bookBorrowAgain}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>最长借阅时间：</td>
                                                                    <td>${readerLicense.readerCategory.bookBorrowTime}天</td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div id="tab-2" class="tab-pane">
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <div class="ibox">
                                                    <div class="ibox-title">
                                                        <h5>当前借阅信息</h5>
                                                        <div class="ibox-tools">
                                                            <a class="collapse-link">
                                                                <i class="fa fa-chevron-up"></i>
                                                            </a>
                                                            <a class="fullscreen-link">
                                                                <i class="fa fa-expand"></i>
                                                            </a>
                                                        </div>
                                                    </div>
                                                    <div class="ibox-content" id="currentBorrowedAll">
                                                        <table class="table">
                                                            <thead>
                                                                <tr>
                                                                    <th>#</th>
                                                                    <th>条码</th>
                                                                    <th>题名</th>
                                                                    <th>借阅日期</th>
                                                                    <th>应还日期</th>
                                                                    <th>已续借次数</th>
                                                                    <th></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="currentBorrowed">
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-12">
                                                <div class="ibox">
                                                    <div class="ibox-title">
                                                        <h5>有效预约信息</h5>
                                                        <div class="ibox-tools">
                                                            <a class="collapse-link">
                                                                <i class="fa fa-chevron-up"></i>
                                                            </a>
                                                            <a class="fullscreen-link">
                                                                <i class="fa fa-expand"></i>
                                                            </a>
                                                        </div>
                                                    </div>
                                                    <div class="ibox-content" id="currentReservationAll">
                                                        <table class="table">
                                                            <thead>
                                                                <tr>
                                                                    <th>#</th>
                                                                    <th>条码</th>
                                                                    <th>题名</th>
                                                                    <th>预约日期</th>
                                                                    <th>预计图书到馆日期</th>
                                                                    <th>预约有效截止日期</th>
                                                                    <th></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="currentReservation">
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-12">
                                                <div class="ibox">
                                                    <div class="ibox-title">
                                                        <h5>当前惩罚信息</h5>
                                                        <div class="ibox-tools">
                                                            <a class="collapse-link">
                                                                <i class="fa fa-chevron-up"></i>
                                                            </a>
                                                            <a class="fullscreen-link">
                                                                <i class="fa fa-expand"></i>
                                                            </a>
                                                        </div>
                                                    </div>
                                                    <div class="ibox-content" id="currentPenaltyAll">
                                                        <table class="table">
                                                            <thead>
                                                                <tr>
                                                                    <th>#</th>
                                                                    <th>借阅记录ID</th>
                                                                    <th>图书条码</th>
                                                                    <th>题名</th>
                                                                    <th>惩罚缘由</th>
                                                                    <th>罚款日期</th>
                                                                    <th>罚款金额（RMB）</th>
                                                                    <th>是否已还款</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="currentPenalty">
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div id="tab-3" class="tab-pane">
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <div class="ibox float-e-margins">
                                                    <div class="ibox-title">
                                                        <h5>历史借阅信息</h5>
                                                        <div class="ibox-tools">
                                                            <a class="collapse-link">
                                                                <i class="fa fa-chevron-up"></i>
                                                            </a>
                                                            <a class="fullscreen-link">
                                                                <i class="fa fa-expand"></i>
                                                            </a>
                                                        </div>
                                                    </div>
                                                    <div class="ibox-content" id="historyBorrowAll">
                                                        <div class="table-responsive">
                                                            <table class="table table-striped table-bordered table-hover dataTables-example" id="historyBorrowTable" >
                                                                <thead>
                                                                    <tr>
                                                                        <th>条码</th>
                                                                        <th>题名</th>
                                                                        <th>借阅日期</th>
                                                                        <th>应还日期</th>
                                                                        <th>实际归还日期</th>
                                                                        <th>归还状态</th>
                                                                        <th>已续借次数</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody id="historyBorrow">
                                                                </tbody>
                                                                <tfoot>
                                                                    <tr>
                                                                        <th>条码</th>
                                                                        <th>题名</th>
                                                                        <th>借阅日期</th>
                                                                        <th>应还日期</th>
                                                                        <th>实际归还日期</th>
                                                                        <th>归还状态</th>
                                                                        <th>已续借次数</th>
                                                                    </tr>
                                                                </tfoot>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-12">
                                                <div class="ibox collapsed float-e-margins">
                                                    <div class="ibox-title">
                                                        <h5>历史预约信息</h5>
                                                        <div class="ibox-tools">
                                                            <a class="collapse-link">
                                                                <i class="fa fa-chevron-up"></i>
                                                            </a>
                                                            <a class="fullscreen-link">
                                                                <i class="fa fa-expand"></i>
                                                            </a>
                                                        </div>
                                                    </div>
                                                    <div class="ibox-content" id="historyReservationAll">
                                                        <div class="table-responsive">
                                                            <table class="table table-striped table-bordered table-hover dataTables-example" id="historyReservationTable" >
                                                                <thead>
                                                                    <tr>
                                                                        <th>条码</th>
                                                                        <th>题名</th>
                                                                        <th>预约日期</th>
                                                                        <th>图书到馆日期</th>
                                                                        <th>预约有效截止日期</th>
                                                                        <th>预约状态</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody id="historyReservation">
                                                                </tbody>
                                                                <tfoot>
                                                                    <tr>
                                                                        <th>条码</th>
                                                                        <th>题名</th>
                                                                        <th>预约日期</th>
                                                                        <th>图书到馆日期</th>
                                                                        <th>预约有效截止日期</th>
                                                                        <th>预约状态</th>
                                                                    </tr>
                                                                </tfoot>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-12">
                                                <div class="ibox collapsed float-e-margins">
                                                    <div class="ibox-title">
                                                        <h5>历史惩罚信息</h5>
                                                        <div class="ibox-tools">
                                                            <a class="collapse-link">
                                                                <i class="fa fa-chevron-up"></i>
                                                            </a>
                                                            <a class="fullscreen-link">
                                                                <i class="fa fa-expand"></i>
                                                            </a>
                                                        </div>
                                                    </div>
                                                    <div class="ibox-content" id="historyPenaltyAll">
                                                        <div class="table-responsive">
                                                            <table class="table table-striped table-bordered table-hover dataTables-example" id="historyPenaltyTable">
                                                                <thead>
                                                                    <tr>
                                                                        <th>借阅记录ID</th>
                                                                        <th>条码</th>
                                                                        <th>题名</th>
                                                                        <th>惩罚缘由</th>
                                                                        <th>罚款日期</th>
                                                                        <th>罚款金额</th>
                                                                        <th>还款日期</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody id="historyPenalty">
                                                                </tbody>
                                                                <tfoot>
                                                                    <tr>
                                                                        <th>借阅记录ID</th>
                                                                        <th>条码</th>
                                                                        <th>题名</th>
                                                                        <th>惩罚缘由</th>
                                                                        <th>罚款日期</th>
                                                                        <th>罚款金额</th>
                                                                        <th>还款日期</th>
                                                                    </tr>
                                                                </tfoot>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div id="tab-4" class="tab-pane">
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <div class="ibox">
                                                    <div class="ibox-title">
                                                        <h5>我的借阅</h5>
                                                        <div class="ibox-tools">
                                                            <a class="collapse-link">
                                                                <i class="fa fa-chevron-up"></i>
                                                            </a>
                                                            <a class="fullscreen-link">
                                                                <i class="fa fa-expand"></i>
                                                            </a>
                                                        </div>
                                                    </div>
                                                    <div class="ibox-content" id="myCollectionAll">
                                                        <table class="table">
                                                            <thead>
                                                                <tr>
                                                                    <th>#</th>
                                                                    <th>题名</th>
                                                                    <th>著者</th>
                                                                    <th></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="myCollection">
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>      
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
    <script src="js/plugins/dataTables/datatables.min.js"></script>

    <!-- Custom and plugin javascript -->
    <script src="js/inspinia.js"></script>
    <script src="js/plugins/pace/pace.min.js"></script>

    <!-- jQuery UI -->
    <script src="js/plugins/jquery-ui/jquery-ui.min.js"></script>
    
    <script src="js/personal.js"></script>

</body>
</html>