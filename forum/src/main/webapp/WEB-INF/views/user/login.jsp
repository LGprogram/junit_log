<%--
  Created by IntelliJ IDEA.
  User: liu
  Date: 2016/12/15
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>用户登录</title>
    <link href="/static/css/font-awesome.min.css" rel="stylesheet">
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@ include file="../include/navbar.jsp"%>
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-sign-in"></i> 登录</span>
        </div>

        <form action="" class="form-horizontal" id="loginForm">
            <c:if test="${not empty requestScope.message}">
                <div class="alert alert-success">
                        ${requestScope.message}
                </div>
            </c:if>
            <c:if test="${not empty param.redirect}">
                <div class="alert alert-success">
                        请登录后继续
                </div>
            </c:if>
            <div class="control-group">
                <label class="control-label">账号</label>
                <div class="controls">
                    <input type="text"name="username">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">密码</label>
                <div class="controls">
                    <input type="password" name="password" id="password">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label"></label>
                <div class="controls">
                    <a href="/foundPassword">忘记密码</a>
                </div>
            </div>

            <div class="form-actions">
                <button id="loginBtn" type="button" class="btn btn-primary">登录</button>

                <a class="pull-right" href="/reg">注册账号</a>
            </div>

        </form>



    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/user/login.js"></script>
</body>
</html>
