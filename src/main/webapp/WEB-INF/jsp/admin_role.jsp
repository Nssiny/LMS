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
    <link href="css/plugins/ztree/zTreeStyle.css" rel="stylesheet">
    
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

</head>

<body>
    <div id="wrapper">
    	<%-- <%@ include file="/public/sidebar2.jsp" %> --%>
        <jsp:include page="/public/sidebar.jsp"></jsp:include>

        <div id="page-wrapper" class="gray-bg dashbard-1">
            <jsp:include page="/public/nav-top.jsp"></jsp:include>
            <div class="row wrapper border-bottom white-bg page-heading">
                <div class="col-lg-9">
                    <h2>角色管理</h2>
                    <ol class="breadcrumb">
                        <li><a href="/">RBAC权限管理</a></li>
                        <li class="active">角色管理</li>
                    </ol>
                </div>
            </div>
            <div class="wrapper wrapper-content animated fadeInRight">
                <div class="row">
                    <div class="jqGrid_wrapper">
                        <table id="roleTable"></table>
                        <div id="pager_roleTable"></div>
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
                                分配权限
                            </h4>
                        </div>
                        <div class="modal-body" id="myModalBody">
                            <div id="tree" class="ztree">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                            </button>
                            <button type="button" class="btn btn-primary" id="permissionSubmit">
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
    
    <!-- Jquery form js -->
    <script src="js/jquery.form.js"></script>

    <!-- ztree -->
    <script src="js/plugins/ztree/jquery.ztree.all.min.js"></script>
    
    <!-- datatimepicker -->
    <script src="js/bootstrap-datetimepicker.min.js"></script>
    <script src="js/bootstrap-datetimepicker.zh-CN.js"></script>
    
    <script>
        $(document).ready(function() {
            var tree = "";
            var setting = {
                check: {
                    chkboxType:{"Y":"ps","N":"s"},
                    chkStyle:"checkbox",
                    enable : true
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                }
            };
            var rmFormOP = {
                type: 'POST',
                dataType: 'json',
                url: '/lms/addPerms.do',
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
            $('#addPermsForm').ajaxForm(rmFormOP).submit(function(){return false;});
            $("#roleTable").jqGrid({
                url: "/lms/jqGridData"+"?table=role",
                editurl: '/lms/roleChange',
                //mtype: 'POST',
                datatype: "json",
                height: 450,
                autowidth: true,
                shrinkToFit: true,
                rowNum: 20,
                rowList: [10, 20, 30],
                colNames:['id', '父角色ID', '角色名称', '角色描述','gmt_create','gmt_modified'],
                colModel:[
                    {name:'id',index:'id', editable: false, width:30, sorttype:"int"},
                    {name:'parentId',index:'parent_id', editable: true, width:40, sorttype:"number", formatter:"integer", editrules: {integer:true, minValue:0}},
                    {name:'name',index:'name', editable: true, width:60, sorttype:"text"},
                    {name:'description',index:'description', editable: true, width:200, sorttype:"text", edittype: 'textarea'},
                    {name:'gmtCreate',index:'gmt_create', editable: false, width:100, sorttype:"date", formatter:"date", formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}},
                    {name:'gmtModified',index:'gmt_modified', editable: false, width:100, sorttype:"date", formatter:"date", formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}}
                ],
                pager: "#pager_roleTable",
                viewrecords: true,
                caption: "角色信息",
                multiselect: true,
                multiboxonly: true,
                add: true,
                edit: true,
                addtext: 'Add',
                edittext: 'Edit',
                hidegrid: false
            });
            $("#roleTable").jqGrid('navGrid', '#pager_roleTable',
                {edit: true, add: true, del: true, search: true},
                {},// edit options 
                {},// add options
                {reloadAfterSubmit: true},// del options
                {multipleSearch: true}// search options
            );
            $("#roleTable").jqGrid('navButtonAdd', '#pager_roleTable', {
                caption: '分配权限',
                title: '分配权限',
                buttonicon: 'ui-icon-tag',
                onClickButton: function() {
                    var rid = $("#roleTable").jqGrid('getGridParam', 'selrow');
                    if(rid) {
                        $.post('/lms/permission/treedata', {rid: rid}, function(zNodes) {
                            tree = $.fn.zTree.init($("#tree"), setting, zNodes);
                            tree.expandAll(true);
                        }, 'json');
                        $("#myModal").modal('show');
                    } else {
                        alert("请选择行！");
                    }
                }
            });
            // Add responsive to jqGrid
            $(window).bind('resize', function () {
                var width = $('.jqGrid_wrapper').width();
                $('#roleTable').setGridWidth(width);
            });
        });
        $("#permissionSubmit").click(function(){
            onCheck();
        });

        function onCheck() {
            var rid = $("#roleTable").jqGrid('getGridParam', 'selrow');
            var treeObj = $.fn.zTree.getZTreeObj("tree");
            var nodes = treeObj.getCheckedNodes(true);
            var ids = new Array();
            for(var i = 0; i < nodes.length; i++) {
                ids.push(nodes[i].id);
            }
            ajaxSubmit(rid, ids);
        }

        function ajaxSubmit(rid, idstr) {
            $.post('/lms/rolePermission/change', {rid: rid,ids: idstr}, function(obj) {
                alert(obj.reasult);
            },'json');
        }
    </script>

</body>
</html>