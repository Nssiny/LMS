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
                    <h2>惩罚管理</h2>
                    <ol class="breadcrumb">
                        <li><a href="/">业务管理</a></li>
                        <li class="active">惩罚管理</li>
                    </ol>
                </div>
            </div>
            <div class="wrapper wrapper-content animated fadeInRight">
                <div class="row">
                    <div class="ibox">
                        <div class="ibox-title">
                            <h5>业务介绍 <small>欠费缴费管理</small></h5>
                            <div class="ibox-tools">
                                <a href="javascript:void(0)" onclick="reset()">
                                    <i class="fa fa-undo"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content">
                            <form id="infoPenaltyManagementForm" action="/lms/infoPenalty.do">
                                <div class="row">
                                    <div class="col-md-6 col-sm-offset-3">
                                        <p class="font-bold">读者账号 *</p>
                                        <select name="saccount" id="saccount" class="select2_demo_2 form-control"></select>
                                        <hr>
                                        <div class="row" id="showDetail">
                                        </div>
                                    </div>
                                    <div class="ibox-content" id="currentPenaltyAll">
                                    </div>
                                </div>
                                <hr>
                                <div class="row form-group">
                                    <div class="col-sm-4 pull-right">
                                        <button id="infoPenaltySubmit" class="btn btn-primary" type="submit" onclick="disabled()">提交</button>
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
            $("#saccount").select2({
                ajax: {
                    url: '/lms/infoPenalty.showAccount',
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
                
                minimumInputLength: 2,
                templateResult: formatRepo,
                templateSelection: formatRepoSelection
            });
            var FormOP = {
                type: 'POST',
                dataType: 'json',
                url: '/lms/infoPenalty.do',
                success: function(msg) {
                    if(msg.reasult == "缴费失败！") {
                        alert(msg.reasult);
                        $("#infoPenaltySubmit").removeAttr('disabled');
                    } else {
                        alert(msg.reasult);
                        location.reload();
                    }
                },
                error: function() {
                    alert("提交失败！");
                    $("#infoPenaltySubmit").removeAttr('disabled');
                }
            };
            $('#infoPenaltyManagementForm').ajaxForm(FormOP).submit(function(){return false;});
        });
        function formatRepo(repo){return repo.text};
        function formatRepoSelection(repo){return repo.text};
        var $saccountSelect = $("#saccount");
        $saccountSelect.on('select2:select', function() {
            var res = $("#saccount").select2("data")[0].text;
            $.ajax({
                url: '/lms/saccountSelectShowPenalty',
                type: 'GET',
                dataType: 'json',
                data: {res: res},
                success: function(msg) {
                    if(msg.size == 0) {
                        $("#currentPenaltyAll").html("");
                        var str = "该读者当前没有任何惩罚记录！";
                        $("#currentPenaltyAll").append(str);
                    } else {
                        $("#showDetail").html("");
                        var str_detail = "<p>共"+msg.size+"条记录，"+"共计"+msg.rmb+"元</p>";
                        $("#showDetail").append(str_detail);
                        $("#currentPenaltyAll").html("");
                        var str = "<table class=\"table\"><thead><tr><th><a class=\"text-muted\" href=\"javascript:void(0)\" onclick=\"allSelect()\">全选</a></th><th>借阅记录ID</th><th>读者账号</th><th>图书条码</th><th>题名</th><th>惩罚缘由</th><th>罚款日期</th><th>罚款金额（RMB）</th><th>是否已还款</th></tr></thead><tbody id=\"currentPenalty\"></tbody></table>";
                        $("#currentPenaltyAll").append(str);
                        $.each(msg.infoPenaltyList, function(i, item) {
                            var colume1 = "<input type=\"checkbox\" value=\""+item.id+"\" name=\"ipid\">";
                            var colume2 = item.rbid;
                            var colume3 = item.account;
                            var colume4 = item.readerBorrow.barCode;
                            var colume5 = item.infoBook.title;
                            var colume6 = item.readerBorrow.penaltyFlag;
                            var colume7 = item.datePenalty;
                            var colume8 = item.pricePenalty;
                            var colume9 = "否";
                            var str1 = "<tr><td>"+colume1+"</td><td>"+colume2+"</td><td>"+colume3+"</td><td>"+colume4+"</td><td>"+colume5+"</td><td>"+colume6+"</td><td>"+colume7+"</td><td>"+colume8+"</td><td>"+colume9+"</td></tr>";
                            $("#currentPenalty").append(str1);
                        });
                    }
                    
                },
                error: function() {
                    alert("saccountSelect.on执行失败")
                }
            });
        });
        function reset() {
            $("#saccount").val(null).trigger("change");
            $("#showDetail").html("");
            $("#currentPenaltyAll").html("");
        };
        function disabled() {
            $("#infoPenaltySubmit").attr('disabled', true);
            alert("dis");
        };
        function allSelect() {
        	if ($("input[name='ipid']").attr("checked") != "checked") {
        		$("input[name='ipid']").prop("checked", true);
        		$("input[name='ipid']").attr("checked", true);
        	} else {
        		$("input[name='ipid']").removeAttr("checked");
        	}
        };
    </script>

</body>
</html>