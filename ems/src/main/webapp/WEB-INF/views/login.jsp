<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>系统登录</title>
    <%@include file="include/css.jsp"%>
</head>
<body class="hold-transition login-page" style="background-image: url(/static/img/bg.jpg);">
    <div class="login-box">
        <div class="login-logo">
            <h2>功成企业管理系统</h2>
        </div>
        <c:if test="${not empty message}">
            <div class="alert-info">${message}</div>
        </c:if>
        <!-- /.login-logo -->
        <div class="login-box-body" style="background-color: #ffe;">
            <p class="login-box-msg">请输入帐号密码</p>
            <form method="post" >
                <div class="form-group has-feedback">
                    <input type="text" class="form-control" name="username" placeholder="帐号/邮箱/手机号码">
                    <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
                </div>
                <div class="form-group has-feedback">
                    <input type="password" class="form-control" name="password" placeholder="密码">
                    <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                </div>
                <div class="row">
                    <div class="col-xs-8">
                        <div class="checkbox icheck">
                            <label style="margin-left:20px"><input type="checkbox">记住我</label>
                        </div>
                    </div>
                    <!-- /.col -->
                    <div class="col-xs-4">
                        <button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
                    </div>
                    <!-- /.col -->
                </div>
            </form>
            <!-- /.social-auth-links -->
            <a href="#">忘记密码</a><br>
        </div>
        <!-- /.login-box-body -->

    </div>
<%@include file="include/js.jsp"%>
</body>
</html>
