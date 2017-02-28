<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
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
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">新增租赁流水</h3>
                    <div class="box-tools pull-right">
                        <a href="#" class="btn btn-default btn-sm"><i class="fa fa-reply"></i></a>
                    </div>
                </div>
                <div class="box-body">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>公司名称</label>
                                <input type="text" class="form-control" id="companyName">
                            </div>
                            <div class="form-group">
                                <label>联系电话</label>
                                <input type="text" class="form-control" id="tel">
                            </div>
                            <div class="form-group">
                                <label>租赁日期</label>
                                <input type="text" class="form-control" id="rentDate" readonly>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>法人代表</label>
                                <input type="text" class="form-control" id="linkMan">
                            </div>
                            <div class="form-group">
                                <label>地址</label>
                                <input type="text" class="form-control" id="address">
                            </div>
                            <div class="form-group">
                                <label>归还日期</label>
                                <input type="text" class="form-control" id="backDate">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>身份证号</label>
                                <input type="text" class="form-control" id="cardNum">
                            </div>
                            <div class="form-group">
                                <label>传真</label>
                                <input type="text" class="form-control" id="fax">
                            </div>
                            <div class="form-group">
                                <label>总天数</label>
                                <input type="text" class="form-control" id="totalDays">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.box -->
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">设备列表</h3>
                    <div class="box-tools pull-right">
                        <button class="btn btn-default btn-sm" data-toggle="modal" data-target="#myModal"><i class="fa fa-plus"></i></button>
                    </div>
                </div>
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>设备名称</th>
                            <th>单位</th>
                            <th>租赁单价</th>
                            <th>数量</th>
                            <th>总价</th>
                            <th>#</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="device in deviceArray">
                            <td>{{device.name}}</td>
                            <td>{{device.unit}}</td>
                            <td>{{device.price}}</td>
                            <td>{{device.num}}</td>
                            <td>{{device.total}}</td>
                            <td><a href="javascript:;" @click="remove(device)"><i class="fa fa-trash text-danger" ></i></a></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="box-footer" style="text-align: right">
                    租赁费合计 {{total}} 元/天
                </div>
            </div>
            <!-- /.box -->
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">合同扫描件</h3>
                </div>
                <div class="box-body">
                    <div id="picker">选择文件</div>
                    <p>注意：上传合同扫描件要求清晰可见 合同必须公司法人签字盖章</p>
                    <ul id="fileArray">
                    </ul>
                    <button class="btn btn-primary pull-right" type="button" @click="rentJson">保存合同</button>
                </div>
            </div>
            <!-- /.box -->
        </section>
        <!-- /.content -->
    </div>

    <div class="modal fade" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">选择设备</h4>
                </div>
                <div class="modal-body">
                    <form action="">
                        <div class="form-group">
                            <input type="hidden" id="deviceName">
                            <label>设备名称</label>
                            <select id="deviceId" style="width: 300px;" class="form-control">
                                <option value="">选择设备</option>
                                <c:forEach items="${deviceList}" var="device">
                                    <option value="${device.id}">${device.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>库存数量</label>
                            <input type="text" class="form-control" id="currNum" readonly>
                        </div>
                        <div class="form-group">
                            <label>单位</label>
                            <input type="text" class="form-control" id="unit" readonly>
                        </div>
                        <div class="form-group">
                            <label>租赁单价</label>
                            <input type="text" class="form-control" id="rentPrice" readonly>
                        </div>
                        <div class="form-group">
                            <label>租赁数量</label>
                            <input type="text" class="form-control" id="rentNum">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" v-on:click="addDevice">加入列表</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
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
<script src="/static/js/uploader/webuploader.min.js"></script>
<script src="/static/plugins/moment.js"></script>
<script src="/static/plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="/static/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script src="/static/plugins/select2/select2.full.min.js"></script>
<script src="/static/plugins/vue.js"></script>
<script src="/static/plugins/layer/layer/layer.js" ></script>
<script>
    var fileArray =[];
    $(function () {

        //select2插件的调用
        $("#deviceId").select2();
        //模态框选择完设备，自动填写设备信息
        $("#deviceId").change(function () {
            var id = $("#deviceId").val();
            $.post("/page/device/out/add/price",{"id":id}).done(function (resp) {
                if(resp.state =="success"){
                    var device = resp.data;
                    $("#deviceName").val(device.name);
                    $("#currNum").val(device.currentNum);
                    $("#unit").val(device.unit);
                    $("#rentPrice").val(device.price);
                }else{
                    alert(resp.message)
                }
            }).error(function () {
                alert("服务器异常，请稍后再试");
            });
        });
        //租赁日期：默认系统时间
        $("#rentDate").val(moment().format("YYYY-MM-DD"));
        //归还时间
        $("#backDate").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            //clearBtn: true,//清除按钮
            //todayBtn: true,//今日按钮
            format: "yyyy-mm-dd",//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
            startDate:moment().add(1,"days").format("YYYY-MM-DD")//开始日期
        }).on('changeDate',function (e) {//租赁天数计算
            var rentDate = moment();
            var backDate = moment(e.format(0,"yyyy-mm-dd"));//
            var days = backDate.diff(rentDate,'days')+1;
            $("#totalDays").val(days);
        });
        //文件上传
        var uploder = WebUploader.create({
            swf : "js/uploader/Uploader.swf",
            server: "/file/upload",
            pick: '#picker',
            auto : true,
            fileVal:'file',
            /*accept: {
             title: 'Images',
             extensions: 'gif,jpg,jpeg,bmp,png',
             mimeTypes: 'image/!*'
             }*/
        });
        uploder.on("uploadSuccess",function (file,resp) {

            layer.msg("上传成功");
            var html = "<li>"+resp.data.originalFilename+"</li>";
            $("#fileArray").append(html);
            var json = {
                sourceName:resp.data.originalFilename,
                newName:resp.data.newFileName
            }
            fileArray.push(json);
        });
        uploder.on("uploadError",function () {
            layer.msg("服务器忙，请稍候");
        });
    });


    //Vue独立于Jquery

    var app = new Vue({
        el: "#app",
        data: {
            deviceArray:[]
        },
        methods:{
            addDevice:function () {
                var id = $("#deviceId").val();
                //判断数组中是否存在当前的设备，如果有则数量累加，更新总价
                var flag = false;
                for(var i=0;i<app.$data.deviceArray.length;i++){
                    var item = app.$data.deviceArray[i];
                    if(id==item.id){
                        item.num = parseFloat(item.num) + parseFloat($("#rentNum").val());
                        item.total = parseFloat(item.num) * parseFloat($("#rentPrice").val());
                        flag=true;
                        break;
                    }
                }
                //如果没有则添加新JSON对象
                if(!flag){
                    var json= {};
                    json.id = id;
                    json.name = $("#deviceName").val();
                    json.unit = $("#unit").val();
                    json.price = $("#rentPrice").val();
                    json.num = $("#rentNum").val();
                    json.total = parseFloat(json.price) * parseFloat(json.num);

                    this.$data.deviceArray.push(json);
                }
            },
            remove:function (device) {
                layer.confirm("确定要删除吗",function (index) {
                    app.$data.deviceArray.splice(app.$data.deviceArray.indexOf(device),1);
                    layer.close(index);
                })

            },
            rentJson:function () {
                var json = {
                    deviceArray:app.$data.deviceArray,
                    fileArray:fileArray,
                    companyName:$("#companyName").val(),
                    tel:$("#tel").val(),
                    rentDate:$("#rentDate").val(),
                    linkMan:$("#linkMan").val(),
                    address:$("#address").val(),
                    backDate:$("#backDate").val(),
                    cardNum:$("#cardNum").val(),
                    fax:$("#fax").val(),
                    totalDays:$("#totalDays").val()

                };
                $.ajax({
                    url:"/page/device/out/add",
                    type:"post",
                    data:JSON.stringify(json),
                    contentType: "application/json;charset=UTF-8",
                    success:function (data) {
                        if(data.state=="success"){
                        layer.confirm("保存成功",{btn:['继续添加','打印合同']},function () {
                            window.history.go(0);
                        },function () {
                            window.location.href="/page/device/out/"+data.data;
                        });
                        }else{
                            layer.msg(data.message)
                        }
                    },
                    error:function () {
                        layer.msg("系统繁忙，请稍候");
                    }
                    
                });
            }
        },
        computed:{
            total:function () {
                var result = 0;
                for(var i=0;i<this.$data.deviceArray.length;i++){
                    var item = this.$data.deviceArray[i];
                    result+=item.total;
                }
                return result;
            }
        }


    });


</script>
</body>
</html>
