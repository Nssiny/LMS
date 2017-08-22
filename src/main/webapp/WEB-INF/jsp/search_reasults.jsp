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

    <title>LMS</title>
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
            <div class="row wrapper border-bottom white-bg page-heading">
                <div class="col-lg-9">
                    <h2>检索页面</h2>
                    <ol class="breadcrumb">
                        <li><a href="/">首页</a></li>
                        <li class="active">检索页面</li>
                    </ol>
                </div>
            </div>
	        <div class="wrapper wrapper-content animated fadeInRight">
	            <div class="row">
	                <div class="col-lg-12">
	                    <div class="ibox float-e-margins">
	                        <div class="ibox-content">
	                            <h2 id="h2show">
	                                
	                            </h2>
	                            <small>Request time  (0.23 seconds)</small>
	
	                            <div class="search-form">
	                                <form name="searchForm" id="searchForm" action="ftSearch.do">
	                                    <div class="input-group">
	                                        <input type="text" value="${str_input}" id="search_input" name="search_input" class="form-control input-lg">
	                                        <div class="input-group-btn">
	                                            <button class="btn btn-lg btn-primary" type="submit">
	                                                Search
	                                            </button>
	                                        </div>
	                                    </div>
	
	                                </form>
	                            </div>
	                            <div class="ibox">
	                            </div>
	                            <div class="ibox" id="search_results">
	                            </div>
	                            <div class="hr-line-dashed"></div>
	                            <div class="text-center">
	                                <ul class="pagination" id="pageUI"></ul>
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
    <script type="text/javascript" src="js/bootstrap-paginator.js"></script>

    <!-- Custom and plugin javascript -->
   	<script src="js/inspinia.js"></script>
    <script src="js/plugins/pace/pace.min.js"></script>

    <!-- jQuery UI -->
    <script src="js/plugins/jquery-ui/jquery-ui.min.js"></script>

    <!-- GITTER社交聊天 -->
    <!-- <script src="js/plugins/gritter/jquery.gritter.min.js"></script> -->

    <!-- Toastr -->
    <script src="js/plugins/toastr/toastr.min.js"></script>

    <script type="text/javascript">
        var search_input = "${str_input}";
        var pageSize = 10;

        $(function(){
			
            paging(1);
            
        });
        
        function paging(pid) {
            $.ajax({
                url: '/lms/q.do',
                type: 'GET',
                dataType: 'json',
                contentType:'application/json;charset=UTF-8',
                data:{search_input: encodeURI(search_input), pid: pid, pageSize: pageSize},
                success: function(msg) {
                    var pages = Math.ceil(msg.size/pageSize);
                    var np = pages%pageSize;
                    var element = $('#pageUI');
                    var options = {
                        bootstrapMajorVersion:3,  
                        currentPage: pid,//当前页面  
                        numberOfPages: np,//一页显示几个按钮（在ul里面生成5个li）  
                        totalPages:pages //总页数  
                    }
                    element.bootstrapPaginator(options);
                    var count = 0;
                    $("#search_results").html("");
                    for (var i in msg.list) {
                        if(msg.list[i]) {
                            var str1 = "<div class=\"ibox-content\"><div class=\"table-responsive\"><table class=\"table search-book-table\"><tbody><tr><td><div class=\"cart-product-imitation\"></div></td><td><div class=\"search-book-info\"><h2 id=\"title"+count+"\">";
                            var str2 = "</h2><p id=\"author"+count+"\">";
                            var str3 = "</p><p id=\"publisher"+count+"\">";
                            var str4 = "</p><div class=\"m-t-sm\" id=\"num"+count+"\"></div></div></td></tr></tbody></table></div></div>";
                            var str = str1+str2+str3+str4;
                            $("#search_results").append(str);
                            count++;
                        }
                    };
                    if (count == 0) {
	                    alert("0");
                    } 
                    else{
                        $.each(msg.list, function(i, item) {
                            //注入题名
                            $("#title"+i).html();
                            var str_title = "<a href=\"/lms/bdetail.do?bid="+item.id+"\" class=\"text-navy\" target=\"_blank\">"+item.title+"</a>";
                            $("#title"+i).append(str_title);
                            //注入著者
                            $("#author"+i).html();
                            var str_author = "<strong>著者：</strong><a href=\"ecommerce-cart.html#\" class=\"text-muted\">"+item.author+"</a>";
                            $("#author"+i).append(str_author);
                            //注入出版社
                            $("#publisher"+i).html();
                            var str_publisher = "<strong>出版社：</strong><a href=\"ecommerce-cart.html#\" class=\"text-muted\">"+item.publisher+"</a>&nbsp;&nbsp;&nbsp;&nbsp;<strong>出版日期：</strong><span>"+item.publishedin+"</span>";
                            $("#publisher"+i).append(str_publisher);
                             /* iterate through array or object */
                        });
                        $.each(msg.duplicate, function(i, item) {
                            //注入复本信息
                            $("#num"+i).html();
                            var str_num = "<p class=\"text-muted\"><i class=\"fa fa-gift\"></i> 已借出复本:"+item.numBorrow+"&nbsp;&nbsp;|&nbsp;&nbsp;<i class=\"fa fa-trash\"></i> 馆藏复本:"+item.numDuplicate+"</p>";
                            $("#num"+i).append(str_num);
                        });
	                    $("#h2show").html("");
	                    var h2show = msg.size+" results found for: <span class=\"text-navy\">"+search_input+"</span>";
	                    $("#h2show").append(h2show);
                    }
                },
                error: function() {
                    alert("方法执行失败！");
                }
            });
        }
    </script>


</body>
</html>