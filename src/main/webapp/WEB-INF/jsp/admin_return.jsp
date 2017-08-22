<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
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

    <title>LMS | 系统管理</title>
    <link rel="shortcut icon" href="img/gt_favicon.png">

    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="css/plugins/select2/select2.min.css" rel="stylesheet">

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
                    <h2>图书归还管理</h2>
                    <ol class="breadcrumb">
                        <li><a href="/">业务管理</a></li>
                        <li class="active">图书归还管理</li>
                    </ol>
                </div>
            </div>
            <div class="wrapper wrapper-content animated fadeInRight">
                <div class="row">
                    <div class="ibox">
                        <div class="ibox-title">
                            <h5>业务介绍 <small>修改相关借阅记录</small></h5>
                            <div class="ibox-tools">
                                <a href="javascript:void(0)" onclick="reset()">
                                    <i class="fa fa-undo"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content">
                            <form id="returnManagementForm" action="/lms/readerReturn.do">
                                <div class="row">
                                    <div class="col-md-6 col-sm-offset-3">
                                        <p class="font-bold">图书条码 *</p>
                                        <select name="sbarCode" id="sbarCode" class="select2_demo_2 form-control"></select>
                                        <hr>
                                        <div class="row" id="showBook">
                                        </div>
                                    </div>
                                </div>
                                <hr>
                                <div class="row form-group">
                                    <div class="col-sm-4 pull-right">
                                        <button id="ReturnSubmit" class="btn btn-primary" type="submit" onclick="disabled()">归还</button>
                                    </div>
                                </div>
                            </form>
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

    <!-- Select2 -->
    <script src="js/plugins/select2/select2.full.min.js"></script>
    
    <!-- Jquery form js -->
    <script src="js/jquery.form.js"></script>

    <script>
        $(document).ready(function() {
            $("#sbarCode").select2({
                multiple: true,
                ajax: {
                    url: '/lms/returnBorrow.showBarCode',
                    dataType: 'json',
                    delay: 250,
                    data: function(params) {
                        return {
                            q: params.term,
                            page: params.page
                        };
                    },
                    processResults: function(data, params) {
                        params.page = params.page || 1;
                        return {
                            results: data.items,
                            pagination: {
                                more: (params.page * 3) < data.total_count
                            }
                        };
                    },
                    cache: true
                },
                escapeMarkup: function (markup) {
                    return markup;
                },
                
                minimumInputLength: 5,
                templateResult: formatRepo,
                templateSelection: formatRepoSelection
            });
            var FormOP = {
                type: 'POST',
                dataType: 'json',
                url: '/lms/readerReturn.do',
                success: function(msg) {
                    if(msg.reasult == "归还失败！") {
                        alert(msg.reasult);
                        $("#ReturnSubmit").removeAttr('disabled');
                    } else {
                        alert(msg.reasult);
                        location.reload();
                    }
                },
                error: function() {
                    alert("提交失败！");
                    $("#ReturnSubmit").removeAttr('disabled');
                }
            };
            $('#returnManagementForm').ajaxForm(FormOP).submit(function(){return false;});
        });
        function formatRepo(repo){return repo.text};
        function formatRepoSelection(repo){return repo.text};
        var $sbarCodeSelect = $("#sbarCode");
        $sbarCodeSelect.on('change', function() {
            var reslist = $("#sbarCode").select2("data");
            var arr = new Array();
            for(var i = 0; i < reslist.length; i++) {
                arr.push(reslist[i].icid);
            }
            if(arr.length != 0) {
                $.ajax({
                    url: '/lms/sbarCodeSelect',
                    type: 'GET',
                    dataType: 'json',
                    data: {reslist: arr},
                    success: function(msg) {
                        $("#showBook").html("");
                        for (var i in msg.item) {
                            if(msg.item[i]) {
                                var str1 = "<div class=\"ibox-content\"><div class=\"table-responsive\"><table class=\"table search-book-table\"><tbody><tr><td><div class=\"cart-product-imitation\"></div></td><td><div class=\"search-book-info\"><h2 id=\"title"+i+"\">";
                                var str2 = "</h2><p id=\"barCode"+i+"\">";
                                var str3 = "</p><p id=\"author"+i+"\">";
                                var str4 = "</p><p id=\"publisher"+i+"\"></p></div></td></tr></tbody></table></div></div>";
                                var str = str1+str2+str3+str4;
                                $("#showBook").append(str);
                            }
                        };
                        $.each(msg.item, function(i, item) {
                            //注入馆藏状态
                            var str_status = "";
                            if(item.status === 2) {
                                str_status = "<span class=\"label label-warning\">损坏</span>";
                            } else if(item.status === 3) {
                                str_status = "<span class=\"label label-warning\">遗失</span>";
                            } else if(item.status === 0) {
                                str_status = "<span class=\"label label-danger\">销毁</span>"
                            }
                            //注入图书状态
                            var str_sta = "";
                            if(item.isReservation === 1) {
                                str_sta += "<span class=\"label label-warning\">预约</span>";
                            }
                            if(item.isBorrowing === 1) {
                                str_sta += "<span class=\"label label-warning\">借阅</span>";
                            }
                            //注入题名
                            var str_title = "<a class=\"text-navy\">"+item.infoBook.title+"</a>"+str_status + str_sta;
                            $("#title"+i).append(str_title);
                            //注入barCode
                            var str_barCode = "<strong>条码：</strong><a class=\"text-muted\">"+item.barCode+"</a>";
                            $("#barCode"+i).append(str_barCode);
                            //注入著者
                            var str_author = "<strong>著者：</strong><a class=\"text-muted\">"+item.infoBook.author+"</a>";
                            $("#author"+i).append(str_author);
                            //注入出版社
                            var str_publisher = "<strong>出版社：</strong><a class=\"text-muted\">"+item.infoBook.publisher+"</a>&nbsp;&nbsp;&nbsp;&nbsp;<strong>出版日期：</strong><span>"+item.infoBook.publishedin+"</span>";
                            $("#publisher"+i).append(str_publisher);
                             /* iterate through array or object */
                        });
                    },
                    error: function() {
                        alert("sbarCodeSelect.on执行失败")
                    }
                });
            } else {
                $("#showBook").html("");
            }
        });
        function reset() {
            $("#sbarCode").val(null).trigger("change");
            $("#showBook").html("");
        };
        function disabled() {
            $("#ReturnSubmit").attr('disabled', true);
            alert("dis");
        };
    </script>

</body>
</html>