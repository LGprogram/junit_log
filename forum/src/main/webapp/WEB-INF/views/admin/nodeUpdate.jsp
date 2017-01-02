<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>编辑节点</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@include file="../include/adminNavbar.jsp"%>
<!--header-bar end-->
<div class="container-fluid" style="margin-top:20px">
    <form action="" id="updateForm">
        <legend>编辑节点</legend>
        <label>节点名称</label>
        <input type="hidden"name="nodeid" id="nodeid" value="${node.id}">
        <input type="text" name="nodename" id="nodename" value="${node.nodename}">
        <div class="form-actions">
            <button type="button" id="updateBtn" class="btn btn-primary">保存</button>
        </div>
    </form>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script>
    $(function () {
        $("#updateBtn").click(function () {
            $("#updateForm").submit();
        });
        $("#updateForm").validate({
            errorElement:"span",
            errorClass:"text-error",
            rules:{
                nodename:{
                    required :true
                }
            },
            messages:{
                nodename:{
                    required:"请输入修改的节点名称"
                }

            },
            submitHandler:function (form) {
                $.ajax({
                    url:"/admin/node/update",
                    type:"post",
                    data:$(form).serialize(),
                    success:function (data) {
                        if(data.state=="success"){
                            alert("修改成功");
                            window.location.href="/admin/node";
                        }else{
                            alert(data.message);
                        }
                    },
                    error:function () {
                        alert("服务器错误");
                    }
                });
            }
        });
    });
</script>
</body>
</html>

