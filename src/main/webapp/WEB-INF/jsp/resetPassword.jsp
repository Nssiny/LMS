<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport"    content="width=device-width, initial-scale=1.0">
    <meta name="description" content="signin">
    <meta name="author"      content="org.sysu">
    
    <title>LMS</title>
    <link rel="shortcut icon" href="img/gt_favicon.png">  
    
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="css/icomoon.css" rel="stylesheet">
    <!-- Toastr style -->
    <link href="css/plugins/toastr/toastr.min.css" rel="stylesheet">
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

</head>

  <body class="gray-bg">
    <div class="middle-box text-center loginscreen animated fadeInDown">
        <div>
            <div>
    
                <!-- <h1 class="logo-name">LMS</h1> -->
                <h1><span class="icon icon-shield"></span></h1>
                
            </div>
            <h3>Welcome to LMS</h3>
            <p>

            </p>
            <p>更改口令</p>
            <form id="resetPassword" class="m-t" role="form" action="/lms/resetPassword.do" method="post">
                <div class="form-group">
                    <input type="password" name="old" class="form-control" placeholder="原密码">
                </div>
                <div class="form-group">
                    <input type="password" id="new1" name="new1" class="form-control" placeholder="新密码">
                </div>
                <div class="form-group">
                    <input type="password" name="new2" class="form-control" placeholder="新密码">
                </div>
                <button type="submit" class="btn btn-primary block full-width m-b">确定</button>
            </form>
            <p class="m-t"> <small>created by org.sysu &copy; 2017</small> </p>
        </div>
    </div>

    <!-- JavaScript libs are placed at the end of the document so the pages load faster -->
    <script src="js/jquery-2.1.1.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="js/plugins/pace/pace.min.js"></script>
    <!-- Toastr -->
    <script src="js/plugins/toastr/toastr.min.js"></script>

    <!-- jQuery UI -->
    <script src="js/plugins/jquery-ui/jquery-ui.min.js"></script>

    <!-- Jquery Validate -->
    <script src="js/plugins/validate/jquery.validate.min.js"></script>

    <script>
        $(document).ready(function() {
            $("#resetPassword").validate({
                rules: {
                    old: "required",
                    new1: "required",
                    new2: {
                        required: true,
                        equalTo: "#new1"
                    }
                }
            });
            var result = "${result}";
            if(result === "密码更改失败！" || result == "原密码不正确！") {
                setTimeout(function() {
                    toastr.options = {
                        closeButton: true,
                        progressBar: true,
                        showMethod: 'slideDown',
                        timeout: 3000
                    };
                    toastr.error('提示', result);
                },1300); 
            }
        });
    </script>
  </body>
</html>
