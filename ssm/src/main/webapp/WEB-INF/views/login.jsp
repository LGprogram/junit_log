<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <c:if test="${not empty message}">
        <div class="alert-info">${message}</div>
    </c:if>
    <form method="post">
        <div class="form-group">
            <label>账号</label>
            <input type="text" name="username" class="form-control">
        </div>
        <div class="form-group">
            <label>密码</label>
            <input type="password" name="password" class="form-control">
        </div>
        <div class="form-group">

            <button class="btn-primary">提交</button>
        </div>
    </form>
</div>
</body>
</html>
