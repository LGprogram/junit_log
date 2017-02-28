<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>河南功成</title>
    <%@include file="../../include/css.jsp"%>
    <link rel="stylesheet" href="/static/js/uploader/webuploader.css">
    <link rel="stylesheet" href="/static/plugins/datepicker/datepicker3.css">
    <link rel="stylesheet" href="/static/plugins/select2/select2.min.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper" id="app">
    <%@include file="../../include/header.jsp"%>
    <jsp:include page="../../include/asider.jsp">
        <jsp:param name="menu" value="sys_new"/>
    </jsp:include>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">
            <h3 style="text-align: center" class="visible-print-block">河南功成外包合同清单</h3>
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">外包合同详情</h3>
                    <div class="box-tools pull-right">
                      <button class="btn btn-default btn-sm hidden-print" id="printBtn"><i class="fa fa-print"></i> 打印 </button>
                    </div>
                </div>
                <div class="box-body">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>公司名称: </label>
                                ${deviceRent.companyName}
                            </div>
                            <div class="form-group">
                                <label>联系电话: </label>
                                ${deviceRent.tel}
                            </div>
                            <div class="form-group">
                                <label>租赁日期: </label>
                                ${deviceRent.rentDate}
                            </div>
                            <div class="form-group">
                                <label>总费用: </label>
                                ${deviceRent.totalPrice}
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>法人代表: </label>
                                ${deviceRent.linkMan}
                            </div>
                            <div class="form-group">
                                <label>地址: </label>
                                ${deviceRent.address}
                            </div>
                            <div class="form-group">
                                <label>归还日期: </label>
                               ${deviceRent.backDate}
                            </div>
                            <div class="form-group">
                                <label>预付款: </label>
                                ${deviceRent.preCost}
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>身份证号: </label>
                                ${deviceRent.cardNum}
                            </div>
                            <div class="form-group">
                                <label>传真: </label>
                                ${deviceRent.fax}
                            </div>
                            <div class="form-group">
                                <label>总天数: </label>
                                ${deviceRent.totalDays}
                            </div>
                            <div class="form-group">
                                <label>尾款: </label>
                                ${deviceRent.lastCost}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.box -->
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">外包列表</h3>
                </div>
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>工种名称</th>
                            <th>佣金/天</th>
                            <th>数量</th>
                            <th>总价</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${workOutDetails}" var="w">
                            <tr>
                                <td>${w.workType}</td>
                                <td>${w.reward}</td>
                                <td>${w.rentNum}</td>
                                <td>${w.total}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- /.box -->
            <div class="box hidden-print">
                <div class="box-header">
                    <h3 class="box-title">合同</h3>
                    <div class="box-tools pull-right">
                        <a href="/file/out/docs?id=${deviceRent.id}" class="btn btn-default btn-sm" ><i class="fa fa-compress"></i>打包下载</a>
                    </div>
                </div>
                <div class="box-body">
                    <ul id="fileList">
                        <c:forEach items="${docList}" var="doc">
                            <li><a href="/file/out/download?id=${doc.id}">${doc.sourceName}</a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <!-- /.box -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <footer class="main-footer">
        <div class="pull-right hidden-xs">
            <b>Version</b> 2.3.3
        </div>
        <strong>Copyright &copy; 2017 <a href="http://hngc.com">河南功成</a>.</strong> All rights
        reserved.
    </footer>
</div>
<!-- /.content-wrapper -->
<%@include file="../../include/js.jsp"%>
<script>
    $(function () {
        $("#printBtn").click(function () {
            window.print();
        })
    });
</script>
</body>
</html>
