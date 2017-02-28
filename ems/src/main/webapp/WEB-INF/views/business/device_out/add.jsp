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
                    <h3 class="box-title">新增租赁流水</h3>
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
                            <form role="form" method="post" >
                                <div class="box-body" class="form-group">
                                    <!--设备 -->
                                    <div class="row">
                                        <div class="col-lg-3">
                                            <div >
                                                <label>设备名称：</label>
                                                <select name="deviceId" id="deviceId"  style="height:25px;width:160px">
                                                    <option value="">请选择设备</option>
                                                    <c:forEach items="${deviceList}" var="device" >
                                                        <option rel="${devcie.price}" value="${device.id}">${device.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-3">
                                            <div >
                                                <label>单位：</label>
                                                <input type="text" class="" id="unit" name="unit" placeholder="" >
                                            </div>
                                        </div>
                                        <div class="col-lg-3">
                                            <div>
                                                <label>租赁单价：</label>
                                                <input type="text" disabled="disabled" name="price" id="price">
                                            </div>
                                        </div>
                                    </div>
                                    </br>
                                    <div class="row">
                                        <div class="col-lg-3">
                                            <div class="form-group">
                                                <label>归还时间：</label>
                                                <input type="text" id="datepicker" name="repaytimePlan" >
                                            </div>
                                        </div>
                                        <div class="col-lg-3">
                                            <div >
                                                <label>数 量：</label>
                                                <input type="text" class="" id="number" name="number" placeholder="" >
                                            </div>
                                        </div>
                                        <div class="col-lg-3">
                                            <div>
                                                <label>天 数：</label>
                                                <input type="text" disabled="disabled" class="" name="daynum" id="daynum"  placeholder="根据归还时间自动生成">
                                            </div>
                                        </div>
                                    </div>
                                    <!--公司 -->
                                    <div class="row">
                                        <div class="col-lg-3">
                                            <div class="" >
                                                <label>租赁公司：</label>
                                                <input type="text"  class="" name="rentCompany"  placeholder="">
                                            </div>
                                        </div>
                                        <div class="col-lg-3">
                                            <div >
                                                <label>地 址：</label>
                                                <input type="text" class="" name="companyAddress" placeholder="" >
                                            </div>
                                        </div>
                                        <div class="col-lg-3">
                                            <div class="form-group">
                                                <label>公司电话：</label>
                                                <input type="text" class="" name="companyTel" placeholder="" >
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-lg-3">
                                            <div class="" >
                                                <label>法人代表：</label>
                                                <input type="text"  class="" name="companyLegal" id="companyLegal" placeholder="">
                                            </div>
                                        </div>
                                        <div class="col-lg-3">
                                            <div class="form-group">
                                                <label >电 话：</label>
                                                <input type="text" class="" name="legalPhone" placeholder="" >
                                            </div>
                                        </div>
                                        <div class="col-lg-3">
                                            <div>
                                                <label>身份证号：</label>
                                                <input type="text" class="" name="legalCard" id="legalCard" placeholder="">
                                            </div>
                                        </div>
                                    </div>
                                    <!--金额 -->
                                    <div class="row">
                                        <div class="col-lg-3">
                                            <div class="form-group" >
                                                <label>租金金额：</label>
                                                <input type="text" readonly = "readonly"  class="" name="rentMoney" class="rentMoney" id="sumMoney">
                                            </div>
                                        </div>
                                        <div class="col-lg-3">
                                            <div class="form-group">
                                                <label>预付款：</label>
                                                <input type="text" readonly = "readonly"  class=""  id="firstMoney" name="advanceMoney" >
                                            </div>
                                        </div>
                                        <div class="col-lg-3">
                                            <div class="form-group">
                                                <label>尾 款：</label>
                                                <input type="text" readonly = "readonly"  class=""  name="lastMoney" id="lastMoney" >
                                            </div>
                                        </div>
                                        <div class="col-lg-3">
                                            <div>
                                            </div>
                                        </div>
                                    </div>
                                    <div> <br/></div>
                                    <div class="box" style="padding-left: 20px">
                                        <div class="box-header">
                                            <span class="title"><i class="fa fa-user"></i> 合同上传</span>
                                        </div>
                                            <hr>
                                            <p style="padding-left: 20px">注意事项</p>
                                            <ul>
                                                <li>上传合同扫描件要求清晰可见</li>
                                                <li>合同必须公司法人签字盖章</li>
                                            </ul>
                                            <div class="form-actions">
                                                <div id="picker">上传合同</div>
                                            </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-lg-3">
                                        </div>
                                        <div class="col-lg-3">
                                            <div class="box-footer">
                                                <button type="submit" class="btn btn-primary">提交</button>
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
<!-- datepicker -->
<script src="/static/js/bootstrap-datepicker.min.js"></script>
<script src="/static/js/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="/static/js/uploader/webuploader.min.js"></script>
<script>
    $(function () {
        /*重置按钮刷新页面*/
        $("#resetBtn").click(function () {
            window.history.go(0);
        });
        /*自动填写单位和价格*/
        $("#deviceId").change(function () {
            var x =$("#deviceId")[0];



            var id = x.options[x.selectedIndex].value;
            $.post("/page/device/out/add/price",{"id":id}).done(function (data) {
                $("#price").val(data.price);
                $("#unit").val(data.unit);
            }).error(function () {
                alert("系统繁忙，请稍候");
            });

        });
        /*自动填写租赁天数*/
        $(document).delegate('#datepicker','change',function () {
            var day = $("#datepicker")[0].value;
            var backDate = new Date(day);/*Javascript中日期的构造还可以支持 new Date("yyyy/MM/dd");*/
            $("#datepicker").val(backDate);
            var currentDate = new Date();
            var daynum =  parseInt((backDate-currentDate)/(1000*60*60*24))+1;
            if(daynum<0){
                alert("请选择今天以后的日期");
                $("#datepicker").val("");
                $("#daynum").val("");
            }else{
                $("#daynum").val(daynum);
            }

        });
        /*自动填写租金、预付款和尾款*/
        $(document).delegate("#number",'change',function () {
            var number = $("#number")[0].value;

            var x =$("#deviceId")[0];
            var id = x.options[x.selectedIndex].value;
            var price = $("#price")[0].value;
            var daynum = $("#daynum")[0].value;
            if(price!=""&&daynum!=""){
                $.post("/page/device/out/add/number",{"number":number,"id":id}).done(function (data) {
                    if(data<0){
                        alert("库存不足，请重新输入");
                        $("#number").val("");
                    }else{
                        var rent_money = number*price*daynum;

                        $("#sumMoney").val(rent_money);
                        $("#firstMoney").val(rent_money*0.2);
                        $("#lastMoney").val(rent_money*0.8);
                    }
                }).error(function () {
                    alert("系统繁忙请稍候");
                });
            }else{
                alert("请选择设备和归还日期");
                $("#number").val("");
                $("#sumMoney").val("");
                $("#firstMoney").val("");
                $("#lastMoney").val("");
            }

        });


        $("#datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            //clearBtn: true,//清除按钮
            //todayBtn: true,//今日按钮
            format: "yyyy-mm-dd 00:00:00"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
        //文件上传
        var uploder = WebUploader.create({
            swf : "js/uploader/Uploader.swf",
            server: "#",
            pick: '#picker',
            auto : true,
            fileVal:'file',
            /*accept: {
             title: 'Images',
             extensions: 'gif,jpg,jpeg,bmp,png',
             mimeTypes: 'image/!*'
             }*/
        });

    });

</script>
</body>
</html>
