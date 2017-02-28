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
        <jsp:param name="menu" value="sys_out"/>
    </jsp:include>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <h1>
                    <small></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="#"><i class="fa fa-dashboard"></i> 业务</a></li>
                    <li><a href="#">设备租赁</a></li>
                    <li class="active">业务流水</li>
                </ol>
                <div class="box-header witd-border">
                    <h3 class="box-title">设备租赁流水</h3>

                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip" title="Collapse">
                            <i class="fa fa-minus"></i></button>
                        <button type="button" class="btn btn-box-tool" data-widget="remove" data-toggle="tooltip" title="Remove">
                            <i class="fa fa-times"></i></button>
                    </div>
                </div>
                <div class="box-body">
                    <div class="box">
                        <div id="filtrate-box" class="screen-condition scd01"><!-- 筛选开始 -->
                            <form action="" class="form-inline">
                                <div class="form-group form-marginR">
                                    <label>流水号:</label>
                                    <input type="text" class="form-control form-angle input-sm"  placeholder="">
                                </div>
                                <div class="form-group form-marginR">
                                    <label>用人单位:</label>
                                    <input type="text" class="form-control form-angle input-sm"  placeholder="">
                                </div>
                                <div class="form-group form-marginR">
                                    <label>状态:</label>
                                    <!-- <div class="input-group"> -->
                                    <select class="form-control form-angle input-sm" id="select_Type">
                                        <option value="1">完成</option>
                                        <option value="2">未完成</option>
                                    </select>
                                    <input type="hidden" name="workFlowType" id="workFlowType">
                                    <!-- </div> -->
                                </div>
                                <div class="form-group form-marginR">
                                    <label>起止时间:</label>
                                    <div class="input-group">
                                        <div class="input-group-addon form-angle input-sm"><i class="fa fa-calendar"></i></div>
                                        <input type="text" class="form-control form-angle form_datetime input-sm" name="createDate"  >
                                    </div> -
                                    <div class="input-group">
                                        <div class="input-group-addon form-angle input-sm"><i class="fa fa-calendar"></i></div>
                                        <input type="text" class="form-control form-angle form_datetime input-sm" name="createDate">
                                    </div>
                                </div>
                                <a type="submit" class="btn btn-default btn-sm">查询</a>
                            </form>
                        </div><!-- 筛选结束 -->
                        <div class="box-body">
                            <table class="table table-bordered">
                                <tr>
                                    <th>流水号</th>
                                    <th>设备名称</th>
                                    <th>数量</th>
                                    <th>租赁公司</th>
                                    <th>法人代表</th>
                                    <th>电话号码</th>
                                    <th>身份证号</th>
                                    <th>创建时间</th>
                                    <th>状态</th>
                                    <th>计划归还时间</th>
                                    <th>实际归还时间</th>
                                    <th>总租金</th>
                                    <th>违约金</th>
                                    <th>合同</th>
                                    <th>操作</th>
                                </tr>
                                <c:forEach items="${deviceOuts}" var="deviceOut">
                                <tr>
                                    <td>${deviceOut.serialNum}</td>
                                    <td>${deviceOut.deviceName}</td>
                                    <td>${deviceOut.number}</td>
                                    <td>${deviceOut.rentCompany}</td>
                                    <td>${deviceOut.companyLegal}</td>
                                    <td>${deviceOut.legalPhone}</td>
                                    <td>${deviceOut.legalCard}</td>
                                    <td>${deviceOut.createtime}</td>
                                    <td>${deviceOut.state}</td>
                                    <td>${deviceOut.repaytimePlan}</td>
                                    <td>${deviceOut.repaytimeActual}</td>
                                    <td>${deviceOut.rentMoney}</td>
                                    <td>${deviceOut.deditMoney}</td>
                                    <td><a href="#">下载</a></td>
                                    <td>
                                        <a href="#">续期</a>
                                        <a href="/page/device/out/${deviceOut.id}/in">入库</a>
                                    </td>
                                </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <!-- /.box-body -->
                        <div class="box-footer clearfix">
                            <ul class="pagination pagination-sm no-margin pull-right">
                                <li><a href="#">&laquo;</a></li>
                                <li><a href="#">1</a></li>
                                <li><a href="#">2</a></li>
                                <li><a href="#">3</a></li>
                                <li><a href="#">&raquo;</a></li>
                            </ul>
                        </div>
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.box-body -->
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

</body>
</html>

