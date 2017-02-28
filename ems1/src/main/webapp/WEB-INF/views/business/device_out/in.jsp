<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>设备入库</title>
    <%@include file="../../include/css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <%@include file="../../include/header.jsp"%>
    <jsp:include page="../../include/asider.jsp">
        <jsp:param name="menu" value="sys_in"/>
    </jsp:include>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">
            <h1>
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 业务</a></li>
                <li><a href="#">设备租赁</a></li>
                <li class="active">设备入库</li>
            </ol>
            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">设备入库</h3>
                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip" title="Collapse">
                            <i class="fa fa-minus"></i></button>
                        <button type="button" class="btn btn-box-tool" data-widget="remove" data-toggle="tooltip" title="Remove">
                            <i class="fa fa-times"></i></button>
                    </div>
                </div>
                <div class="box-body">
                    <div class="box">
                        <div class="box-body">
                            <form method="post" >
                                <div class="box-body" class="form-group">
                                    <!--设备 -->
                                    <input type="hidden"id="damagePrice" value="${deviceOut.damagePrice}" >
                                    <input type="hidden" id="missPrice" value="${deviceOut.missPrice}">
                                    <input type="hidden" id="deviceId" name="deviceId" value="${deviceOut.deviceId}">
                                    <div class="row">
                                        <div class="col-lg-3">
                                            <div >
                                                <label>设备名称：</label>
                                                <input type="text" class="" readonly  name="deviceName" value="${deviceOut.deviceName}" >
                                            </div>
                                        </div>
                                        <div class="col-lg-3">
                                            <div >
                                                <label>应还数量：</label>
                                                <input type="text" class="" readonly  name="number" id="number" value="${deviceOut.number}"><span>个</span>
                                            </div>
                                        </div>
                                        <div class="col-lg-3">
                                            <div>
                                                <label>实还数量：</label>
                                                <input type="text" class=""name="repayNum"  id="repayNum" ><span>个</span>
                                            </div>
                                        </div>
                                    </div>
                                    </br>
                                    <div class="row">
                                        <div class="col-lg-3">
                                            <div class="form-group">
                                                <label>丢失数量：</label>
                                                <input type="text" readonly class="" id="missNum" name="missNum"  >
                                            </div>
                                        </div>
                                        <div class="col-lg-3">
                                            <div >
                                                <label>应赔付：</label>
                                                <input type="text" readonly class="" name="damageMoney1" id="damageMoney1" >
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-lg-3">
                                            <div class="form-group">
                                                <label>损坏数量：</label>
                                                <input type="text"  class="" id="damageNum" name="damageNum" >
                                            </div>
                                        </div>
                                        <div class="col-lg-3">
                                            <div >
                                                <label>应赔付：</label>
                                                <input type="text" readonly class="" name="damageMoney2" id="damageMoney2" >
                                            </div>
                                        </div>
                                    </div>
                                    <!--公司 -->
                                    <div class="row">
                                        <div class="col-lg-3">
                                            <div class="" >
                                                <label>剩余租金：</label>
                                                <input type="text" readonly  class="" name="lastMoney" id="lastMoney"  value="${deviceOut.lastMoney}">
                                            </div>
                                        </div>
                                        <div class="col-lg-3">
                                            <div >
                                                <label>违约金额：</label>
                                                <input type="text" class="" name="damageMoney" id="damageMoney" readonly value="0"  >
                                            </div>
                                        </div>
                                        <div class="col-lg-3">
                                            <div >
                                                <label>结算金额：</label>
                                                <input type="text" class="" name="totalMoney" id="totalMoney" readonly value="0" >
                                            </div>
                                        </div>
                                    </div>
                                    <div> <br/></div>
                                    <div class="row">
                                        <div class="col-lg-3">
                                        </div>
                                        <div class="col-lg-3">
                                            <div class="box-footer">
                                                <button type="submit" class="btn btn-primary">结算</button>
                                                <button type="button" id="resetBtn" class="btn btn-primary">重置</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <!-- /.box-body -->
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
<script>
    $(function () {
        $("#resetBtn").click(function () {
          window.history.go(0);
        })
        $(document).delegate("#repayNum",'change',function () {
            var num = $("#number")[0].value;
            var repayNum = $("#repayNum")[0].value;
            if(repayNum>num){
                alert("请输入正确的数量");
            }else{
                var missNum = num-repayNum;
                $("#missNum").val(missNum);
                var missPrice = $("#missPrice")[0].value;
                var damageMoney1 = missNum*missPrice;
                $("#damageMoney1").val(damageMoney1);
            }
        });
        $(document).delegate("#damageNum",'change',function () {
            var damageNum = $("#damageNum")[0].value;
            var number = $("#number")[0].value;
            var missNum = $("#missNum")[0].value;
            var damagePrice = $("#damagePrice")[0].value;
            var damageMoney1 = $("#damageMoney1")[0].value;
            if(damageNum>(number-missNum)){
                alert("请核对损坏数量");
            }else{
                $("#damageMoney2").val(damageNum*damagePrice);
                var damageMoney2 = $("#damageMoney2")[0].value;
                var damageMoney = parseFloat(damageMoney1+damageMoney2);
                $("#damageMoney").val(damageMoney);

                var lastMoney = parseFloat($("#lastMoney")[0].value);
                var totalMoney = lastMoney+damageMoney;
                $("#totalMoney").val(totalMoney);
            }
        });
    });
</script>
</body>
</html>


