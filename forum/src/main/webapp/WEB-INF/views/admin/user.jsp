<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@include file="../include/adminNavbar.jsp"%>
<!--header-bar end-->
<div class="container-fluid" style="margin-top:20px">
    <table class="table">
        <thead>
        <tr>
            <th>账号</th>
            <th>注册时间</th>
            <th>最后登录时间</th>
            <th>最后登录IP</th>
            <th>用户状态</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.items}" var="userVo">
            <tr>
                <td>${userVo.username}</td>
                <td>${userVo.createtime}</td>
                <td>${userVo.lastLoginTime}</td>
                <td>${userVo.loginIP}</td>
                <td>
                    <a class="userstate" rel="${userVo.userId}" href="javascript:;">${userVo.userState == 1?"激活":"禁用"}</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagination pagination-mini pagination-centered">
        <ul id="pagination" style="margin-bottom:20px;"></ul>
    </div>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script>
    $(function () {
        $("#pagination").twbsPagination({
            totalPages:${page.totalPage},
            visiblePages:5,
            first:'首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href: '?p={{number}}'
        });
        $(".userstate").click(function () {

            var $this = $(this);
            var userId =$this.attr("rel");
            var action="";
            if($this.text()=="禁用"){
                action="unable";
            }else{
                action="able";
            }
            $.post("/admin/user",{"action":action,"userId":userId})
                .done(function (json) {
                    if(json.state=="success"){
                        if(action == "unable"){
                            $this.text("激活");
                        }else{
                            $this.text("禁用");
                        }

                    }else{
                        alert(json.message)
                    }
                })
                .error(function () {
                    alert("服务器错误");
                });
        });
    });
</script>
</body>
</html>

