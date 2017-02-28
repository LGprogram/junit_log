<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <%@include file="../../include/css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <%@include file="../../include/header.jsp"%>
    <jsp:include page="../../include/asider.jsp">
        <jsp:param name="menu" value="sys_new"/>
    </jsp:include>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">
            <div class="box box-solid box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">新增设备</h3>
                </div>
                <div class="box-body">
                    <form method="post">
                        <div class="form-group">
                            <label>名称</label>
                            <input type="text" name="name" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>单位</label>
                            <input type="text" name="unit" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>库存</label>
                            <input type="text" name="currentNum" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>总数</label>
                            <input type="text" name="totalNum" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>租赁价格（元/天）</label>
                            <input type="text" name="price" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>损坏赔偿单价</label>
                            <input type="text" name="damagePrice" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>丢失赔偿单价</label>
                            <input type="text" name="missPrice" class="form-control">
                        </div>
                        <div class="form-group">
                            <button class="btn btn-success">保存</button>
                        </div>
                    </form>
                </div>
            </div>
        </section>
    </div>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->
<%@include file="../../include/js.jsp"%>
</body>
</html>
