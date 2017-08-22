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
    <!-- <link href="css/plugins/ztree/demo.css" rel="stylesheet"> -->
    <link href="css/plugins/ztree/zTreeStyle.css" rel="stylesheet">

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
                    <h2>用户管理</h2>
                    <ol class="breadcrumb">
                        <li><a href="/">RBAC权限管理</a></li>
                        <li class="active">用户管理</li>
                    </ol>
                </div>
            </div>
            <div class="wrapper wrapper-content animated fadeInRight">
                <div class="row">
                    <div class="jqGrid_wrapper">
                        <table id="userTable"></table>
                        <div id="pager_userTable"></div>
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
                                配置角色
                            </h4>
                        </div>
                        <div class="modal-body" id="myModalBody">
                            <div id="tree" class="ztree">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                            </button>
                            <button type="button" class="btn btn-primary" id="roleSubmit">
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
                url: '/lms/addRoles.do',
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
            $('#addRolesForm').ajaxForm(rmFormOP).submit(function(){return false;});
            $("#userTable").jqGrid({
                url: "/lms/jqGridData"+"?table=user",
                editurl: '/lms/userChange',
                //mtype: 'POST',
                datatype: "json",
                height: 450,
                autowidth: true,
                shrinkToFit: true,
                rowNum: 20,
                rowList: [10, 20, 30],
                colNames:['id','账号', '密码', '本次登录时间','上次登录时间','gmt_create','gmt_modified'],
                colModel:[
                    {name:'id',index:'id', editable: false, width:30, sorttype:"int"},
                    {name:'loginId',index:'login_id', editable: true, width:120, sorttype:"text", editrules: {required:true}},
                    {name:'password',index:'password', search:false, sortable:false, editable: true, width:120, edittype:"password",hidden:true,editrules:{edithidden:true,required:true}},
                    {name:'gmtLogin',index:'gmt_login', editable: false, width:100, sorttype:"date", formatter:"date", formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}},
                    {name:'gmtLastLogin',index:'gmt_last_login', editable: false, width:100, sorttype:"date", formatter:"date", formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}},
                    {name:'gmtCreate',index:'gmt_create', editable: false, width:100, sorttype:"date", formatter:"date", formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}},
                    {name:'gmtModified',index:'gmt_modified', editable: false, width:100, sorttype:"date", formatter:"date", formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}}
                ],
                pager: "#pager_userTable",
                viewrecords: true,
                caption: "账号信息",
                multiselect: true,
                multiboxonly: true,
                add: true,
                edit: true,
                addtext: 'Add',
                edittext: 'Edit',
                hidegrid: false
            });
            $("#userTable").jqGrid('navGrid', '#pager_userTable',
                {edit: true, add: true, del: true, search: true},
                {},// edit options 
                {},// add options
                {reloadAfterSubmit: true},// del options
                {multipleSearch: true}// search options
            );
            $("#userTable").jqGrid('navButtonAdd', '#pager_userTable', {
                caption: '分配角色',
                title: '分配角色',
                buttonicon: 'ui-icon-tag',
                onClickButton: function() {
                    var uid = $("#userTable").jqGrid('getGridParam', 'selrow');
                    if(uid) {
                        //TODO
                        $.post('/lms/role/treedata', {uid: uid}, function(zNodes) {
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
                $('#userTable').setGridWidth(width);
            });
        });
        $("#roleSubmit").click(function(){
            onCheck();
        });

        function onCheck() {
            var uid = $("#userTable").jqGrid('getGridParam', 'selrow');
            var treeObj = $.fn.zTree.getZTreeObj("tree");
            var nodes = treeObj.getCheckedNodes(true);
            var ids = new Array();
            for(var i = 0; i < nodes.length; i++) {
                ids.push(nodes[i].id);
            }
            ajaxSubmit(uid, ids);
        }

        function ajaxSubmit(uid, idstr) {
            $.post('/lms/userRole/change', {uid: uid,ids: idstr}, function(obj) {
                alert(obj.reasult);
            },'json');
        }
    </script>

</body>
</html>