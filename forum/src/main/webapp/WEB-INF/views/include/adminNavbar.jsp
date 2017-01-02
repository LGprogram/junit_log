<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="navbar navbar-inverse  navbar-static-top">
    <div class="navbar-inner">
        <a class="brand" href="#">BBS 管理系统</a>
        <ul class="nav">
            <li class="active"><a href="/admin/home">首页</a></li>
            <li><a href="/admin/topic">主题管理</a></li>
            <li><a href="/admin/node">节点管理</a></li>
            <li><a href="/admin/user">用户管理</a></li>
        </ul>
        <ul class="nav pull-right">
            <li><a href="/admin/loginOut">安全退出</a></li>
        </ul>
    </div>
</div>