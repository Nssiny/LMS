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
                    <h2>图书管理</h2>
                    <ol class="breadcrumb">
                        <li><a href="/">业务管理</a></li>
                        <li class="active">图书管理</li>
                    </ol>
                </div>
            </div>
            <div class="wrapper wrapper-content animated fadeInRight">
                <div class="row">
                    <div class="jqGrid_wrapper">
                        <table id="bookTable"></table>
                        <div id="pager_bookTable"></div>
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
                                添加馆藏书目
                            </h4>
                        </div>
                        <div class="modal-body" id="myModalBody">
                            <form id="infoCollectionForm" action="/lms/addInfoCollection.do">
                                <div class="row">
                                    <div class="form-group">
                                        <label for="icBarCode" class="control-label">图书条码 *</label>
                                        <input id="icBarCode" name="icBarCode" type="text" class="form-control" value="" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="icBid" class="control-label">Bid *</label>
                                        <input id="icBid" name="icBid" type="text" class="form-control" value="" readonly required>
                                    </div>
                                    <div class="form-group">
                                        <label for="icCallno" class="control-label">索书号 *</label>
                                        <input id="icCallno" name="icCallno" type="text" class="form-control" value="" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="icLocated" class="control-label">馆藏位置 *</label>
                                        <input id="icLocated" name="icLocated" type="text" class="form-control" value="" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="icType" class="control-label">馆藏类型 *</label>
                                        <div id="icType" class="i-checks">
                                            <label style="margin-left: 10px;padding-left: 20px;font-weight: 400;"> <input type="radio" name="icType" value="外借本" style="display: inline-block;" checked> <i></i> 外借本 </label>
                                            <label style="margin-left: 10px;padding-left: 20px;font-weight: 400;"> <input type="radio" name="icType" value="珍藏本" style="display: inline-block;"> <i></i> 珍藏本 </label>
                                        </div>
                                    </div>
                                    <div class="pull-right">
                                    	<input type="submit" class="btn btn-primary" id="cancelReservation" value="提交">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭
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
    
    <!-- Jquery form js -->
    <script src="js/jquery.form.js"></script>
    
    <!-- datatimepicker -->
    <script src="js/bootstrap-datetimepicker.min.js"></script>
    <script src="js/bootstrap-datetimepicker.zh-CN.js"></script>
    
    <script>
        $(document).ready(function() {
            var rmFormOP = {
                type: 'POST',
                dataType: 'json',
                url: '/lms/addInfoCollection.do',
                success: function(msg) {
                    if(msg.reasult == "添加馆藏书目失败！") {
                        alert(msg.reasult);
                    } else {
                        alert(msg.reasult);
                        $("#myModal").modal('hide');
                    }
                },
                error: function() {
                    alert("提交失败！");
                }
            };
            $('#infoCollectionForm').ajaxForm(rmFormOP).submit(function(){return false;});
            $("#bookTable").jqGrid({
                url: "/lms/jqGridData"+"?table=infoBook",
                editurl: '/lms/infoBookChange',
                //mtype: 'POST',
                datatype: "json",
                height: 450,
                autowidth: true,
                shrinkToFit: true,
                rowNum: 20,
                rowList: [10, 20, 30],
                colNames:['id','ISBN', '题名', '著者','译者','绘者','出版社','出版年','简介','clc','卷册说明','语种','单价','gmt_create','gmt_modified'],
                colModel:[
                    {name:'id',index:'id', editable: false, width:30, sorttype:"int"},
                    {name:'isbn',index:'isbn', editable: true, width:120, sorttype:"text", editrules: {required:true}},
                    {name:'title',index:'title', editable: true, width:160, sorttype:"text"},
                    {name:'author',index:'author', editable: true, width:120, sorttype:"text"},
                    {name:'translator',index:'translator', editable: true, width:80, sorttype:"text"},
                    {name:'painter',index:'painter', editable: true, width:80, sorttype:"text"},
                    {name:'publisher',index:'publisher', editable: true, width:160, sorttype:"text"},
                    {name:'publishedin',index:'publishedin', editable: true, width:60, sorttype:"date", formatter:"date", formatoptions: {srcformat:'Y',newformat:'Y'}, editoptions:{
                            dataInit: function(element) {
                                $(element).datetimepicker({
                                    language: 'zh-CN',
                                    format: 'yyyy',
                                    autoclose: true,
                                    minView: 4,
                                    startView: 4
                                });
                            }
                        }},
                    {name:'description',index:'description', editable: true, width:200, sorttype:"text", edittype: 'textarea'},
                    {name:'clc',index:'clc', editable: true, width:70, sorttype:"text"},
                    {name:'volume',index:'volume', editable: true, width:60, sorttype:"text"},
                    {name:'languages',index:'languages', editable: true, width:35, sorttype:"text", edittype:'select', editoptions: {value:'cn:cn;en:en'}},
                    {name:'price',index:'price', editable: true, width:50, sorttype:"float", formatter:"number", editrules: {number:true, minValue:0}},
                    {name:'gmtCreate',index:'gmt_create', editable: false, width:100, sorttype:"date", formatter:"date", formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}},
                    {name:'gmtModified',index:'gmt_modified', editable: false, width:100, sorttype:"date", formatter:"date", formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}}
                ],
                pager: "#pager_bookTable",
                viewrecords: true,
                caption: "书目信息",
                multiselect: true,
                multiboxonly: true,
                add: true,
                edit: true,
                addtext: 'Add',
                edittext: 'Edit',
                hidegrid: false
            });
            $("#bookTable").jqGrid('navGrid', '#pager_bookTable',
                {edit: true, add: true, del: true, search: true},
                {},// edit options 
                {},// add options
                {reloadAfterSubmit: true},// del options
                {multipleSearch: true}// search options
            );
            $("#bookTable").jqGrid('navButtonAdd', '#pager_bookTable', {
                caption: '添加馆藏书目',
                title: '添加馆藏书目',
                buttonicon: 'ui-icon-tag',
                onClickButton: function() {
                    var bid = $("#bookTable").jqGrid('getGridParam', 'selrow');
                    if(bid) {
                        document.getElementById("icBarCode").value = "";
                        document.getElementById("icBid").value = bid;
                        document.getElementById("icCallno").value = "";
                        document.getElementById("icLocated").value = "";
                        $("#myModal").modal('show');
                    } else {
                        alert("请选择行！");
                    }
                }
            });
            // Add responsive to jqGrid
            $(window).bind('resize', function () {
                var width = $('.jqGrid_wrapper').width();
                $('#bookTable').setGridWidth(width);
            });
        });
    </script>

</body>
</html>