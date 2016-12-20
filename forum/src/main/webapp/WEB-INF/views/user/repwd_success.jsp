<%--
  Created by IntelliJ IDEA.
  User: liu
  Date: 2016/12/17
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>重置密码</title>
    <link href="/static/css/font-awesome.min.css" rel="stylesheet">
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="../include/navbar.jsp"%>
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-sign-in"></i> 重置密码</span>
        </div>

        <form action="" class="form-horizontal" id="repwdForm">
            <input type="hidden" name="id" value="${requestScope.user.id}">
            <input type="hidden" name="token" value="${requestScope.token}">
            <div class="control-group">
                <label class="control-label">账号</label>
                <div class="controls">
                    <input id="name" type="text" name="username" value="${requestScope.user.username}">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">新密码</label>
                <div class="controls">
                    <input type="password" name="password" id="password">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">重复密码</label>
                <div class="controls">
                    <input type="password" name="repassword">
                </div>
            </div>


            <div class="form-actions">
                <button id="repwdBtn" type="button" class="btn btn-primary">提交</button>

                <a class="pull-right" href="/reg">注册账号</a>
            </div>

        </form>



    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/user/repwd_success.js"></script>
</body>
</html>
