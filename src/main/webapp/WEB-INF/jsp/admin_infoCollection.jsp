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

    <title>LMS | 系统管理</title>
    <link rel="shortcut icon" href="img/gt_favicon.png">

    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
    <link href="css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
    <link href="css/bootstrap-datetimepicker.css" rel="stylesheet">
    
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

</head>

<body>
    <div id="wrapper">
        <jsp:include page="/public/sidebar.jsp"></jsp:include>

        <div id="page-wrapper" class="gray-bg dashbard-1">
            <jsp:include page="/public/nav-top.jsp"></jsp:include>
            <div class="row wrapper border-bottom white-bg page-heading">
                <div class="col-lg-9">
                    <h2>馆藏信息管理</h2>
                    <ol class="breadcrumb">
                        <li><a href="/">业务管理</a></li>
                        <li class="active">馆藏信息管理</li>
                    </ol>
                </div>
            </div>
            <div class="wrapper wrapper-content animated fadeInRight">
                <div class="row">
                    <div class="jqGrid_wrapper">
                        <table id="infoCollectionTable"></table>
                        <div id="pager_infoCollectionTable"></div>
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

    <!-- Peity -->
    <script src="js/plugins/peity/jquery.peity.min.js"></script>

    <!-- Custom and plugin javascript -->
    <script src="js/inspinia.js"></script>
    <script src="js/plugins/pace/pace.min.js"></script>
    
    <!-- jqGrid -->
    <script src="js/plugins/jqGrid/i18n/grid.locale-cn.js"></script>
    <script src="js/plugins/jqGrid/jquery.jqGrid.src.js"></script>

    <!-- jQuery UI -->
    <script src="js/plugins/jquery-ui/jquery-ui.min.js"></script>
    
    <!-- datatimepicker -->
    <script src="js/bootstrap-datetimepicker.min.js"></script>
    <script src="js/bootstrap-datetimepicker.zh-CN.js"></script>
    
    <script>
        $(document).ready(function() {
            $("#infoCollectionTable").jqGrid({
                url: "/lms/jqGridData"+"?table=infoCollection",
                editurl: '/lms/infoCollectionChange',
                //mtype: 'POST',
                datatype: "json",
                height: 450,
                autowidth: true,
                shrinkToFit: true,
                rowNum: 20,
                rowList: [10, 20, 30],
                colNames:['id','图书条码', 'ibid', '索书号','馆藏位置','馆藏类型','预约状态','借阅状态','gmt_create','gmt_modified','馆藏状态'],
                colModel:[
                    {name:'id',index:'id', editable: false, width:30, sorttype:"int"},
                    {name:'barCode',index:'bar_code', editable: true, width:120, sorttype:"text", editrules: {required:true}},
                    {name:'bid',index:'bid', editable: true, width:40, sorttype:"text", editrules: {required:true}},
                    {name:'callno',index:'callno', editable: true, width:120, sorttype:"text"},
                    {name:'located',index:'located', editable: true, width:120, sorttype:"text"},
                    {name:'type',index:'type', editable: true, width:80, sorttype:"text", editrules: {required:true}, edittype:'select', editoptions: {value:'外借本:外借本;珍藏本:珍藏本'}},
                    {name:'isReservation',index:'is_reservation', editable: false, width:50, sorttype:"int"},
                    {name:'isBorrowing',index:'is_borrowing', editable: false, width:50, sorttype:"int"},
                    {name:'gmtCreate',index:'gmt_create', editable: false, width:100, sorttype:"date", formatter:"date", formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}},
                    {name:'gmtModified',index:'gmt_modified', editable: false, width:100, sorttype:"date", formatter:"date", formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}},
                    {name:'status',index:'status', editable: false, width:50, sorttype:"int"}
                ],
                pager: "#pager_infoCollectionTable",
                viewrecords: true,
                caption: "馆藏记录",
                multiselect: true,
                multiboxonly: true,
                edit: true,
                edittext: 'Edit',
                hidegrid: false
            });
            $("#infoCollectionTable").jqGrid('navGrid', '#pager_infoCollectionTable',
                {edit: true, add: false, del: false, search: true},
                {},// edit options 
                {},// add options
                {},// del options
                {multipleSearch: true}// search options
            );
            // Add responsive to jqGrid
            $(window).bind('resize', function () {
                var width = $('.jqGrid_wrapper').width();
                $('#infoCollectionTable').setGridWidth(width);
            });
        });
    </script>

</body>
</html>