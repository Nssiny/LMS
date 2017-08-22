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
                    <h2>图书预约管理</h2>
                    <ol class="breadcrumb">
                        <li><a href="/">业务管理</a></li>
                        <li class="active">图书预约管理</li>
                    </ol>
                </div>
            </div>
            <div class="wrapper wrapper-content animated fadeInRight">
                <div class="row">
                    <div class="jqGrid_wrapper">
                        <table id="reservationTable"></table>
                        <div id="pager_reservationTable"></div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                &times;
                            </button>
                            <h4 class="modal-title" id="myModalLabel">
                                确认取消预约？
                            </h4>
                        </div>
                        <div class="modal-body" id="myModalBody">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消
                            </button>
                            <button type="button" class="btn btn-primary" id="cancelReservation">
                                确定
                            </button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal -->
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
            $("#reservationTable").jqGrid({
                url: "/lms/jqGridData"+"?table=readerReservation",
                //mtype: 'POST',
                datatype: "json",
                height: 450,
                autowidth: true,
                shrinkToFit: true,
                rowNum: 20,
                rowList: [10, 20, 30],
                colNames:['id','图书条码', '读者账号', '预约日期','预计图书到馆日期','预约有效截止日期','预约状态','gmt_create','gmt_modified'],
                colModel:[
                    {name:'id',index:'id', editable: false, width:30, sorttype:"int"},
                    {name:'barCode',index:'bar_code', editable: false, width:120, sorttype:"text"},
                    {name:'account',index:'account', editable: false, width:160, sorttype:"text"},
                    {name:'dateReservation',index:'date_reservation', editable: false, width:100, sorttype:"date", formatter:"date", formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}},
                    {name:'dateBack',index:'date_back', editable: false, width:100, sorttype:"date", formatter:"date", formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}},
                    {name:'dateEffective',index:'date_effective', editable: false, width:100, sorttype:"date", formatter:"date", formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}},
                    {name:'isEffective',index:'is_effective', editable: false, width:100, sorttype:"number", formatter:"integer"},
                    {name:'gmtCreate',index:'gmt_create', editable: false, width:100, sorttype:"date", formatter:"date", formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}},
                    {name:'gmtModified',index:'gmt_modified', editable: false, width:100, sorttype:"date", formatter:"date", formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}}
                ],
                pager: "#pager_reservationTable",
                viewrecords: true,
                caption: "读者预约记录",
                multiselect: true,
                multiboxonly: true,
                hidegrid: false
            });
            $("#reservationTable").jqGrid('navGrid', '#pager_reservationTable',
                {edit: false, add: false, del: false, search: true},
                {},// edit options 
                {},// add options
                {},// del options
                {multipleSearch: true}// search options
            );
            $("#reservationTable").jqGrid('navButtonAdd', '#pager_reservationTable', {
                caption: '取消预约',
                title: '取消预约',
                buttonicon: 'ui-icon-close',
                onClickButton: function() {
                    var rrid = $("#reservationTable").jqGrid('getGridParam', 'selarrrow');
                    $("#myModalBody").html("")
                    $("#myModalBody").append("确认取消以下预约记录：")
                    for(var i = 0; i < rrid.length; i++) {
                        if(i === rrid.length-1) {
                            $("#myModalBody").append(rrid[i]);
                        } else {
                            $("#myModalBody").append(rrid[i]+", ");
                        }
                    }
                    $("#myModal").modal('show');
                }
            });
            // Add responsive to jqGrid
            $(window).bind('resize', function () {
                var width = $('.jqGrid_wrapper').width();
                $('#reservationTable').setGridWidth(width);
            });
        });
        $("#cancelReservation").click(function(){
            var rrids = $("#reservationTable").jqGrid('getGridParam', 'selarrrow');
            $("#myModal").modal('hide');
            $.ajax({
                url: '/lms/acancelReservation.do',
                type: 'POST',
                dataType: 'json',
                data: {rrids: rrids},
                success: function(msg) {
                    alert(msg.reasult);
                    $("#reservationTable").trigger("reloadGrid");
                },
                error: function() {
                    alert("管理员取消预约执行失败！");
                }
            });
        });
    </script>

</body>
</html>