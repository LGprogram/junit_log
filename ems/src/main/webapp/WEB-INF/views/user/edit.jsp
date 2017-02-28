<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <form method="post">
        <div class="form-group">
            <input type="hidden" name="id" value="${user.id}" class="form-control">
            <label>账号</label>
            <input type="text" name="username" value="${user.username}" class="form-control">
        </div>
        <div class="form-group">
            <label>密码(若不修改请留空)</label>
            <input type="password" name="password"  class="form-control">
        </div>
        <div class="form-group">
            <label>部门</label>
            <div>
                <c:forEach items="${roleList}" var="role">
                    <c:set var="flag" value="false" scope="page"/>
                    <c:forEach items="${user.roleList}" var="userRole">
                        <c:if test="${userRole.id == role.id}">
                            <label class="checkbox-inline">
                                <input type="checkbox" name="roleIds" checked value="${role.id}"> ${role.viewName}
                            </label>
                            <c:set var="flag" value="true"/>
                        </c:if>
                    </c:forEach>
                    <c:if test="${not flag}">
                        <input type="checkbox" name="roleIds" value="${role.id}"> ${role.viewName}
                        </label>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <div class="form-group">
            <button class="btn btn-success">保存</button>
        </div>
    </form>
</div>
</body>
</html>
