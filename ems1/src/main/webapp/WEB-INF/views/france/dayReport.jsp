<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <%@include file="../include/css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="/static/plugins/datepicker/datepicker3.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <%@include file="../include/header.jsp"%>
    <jsp:include page="../include/asider.jsp">
        <jsp:param name="menu" value="sys_day"/>
    </jsp:include>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">
            <div class="box">
                <div class="box-body">

                    <form class="form-inline">
                        <label>选择要查看的日期</label>
                        <input type="text" class="form-control" value="${param.date}" id="date">
                    </form>
                </div>
            </div>
            <!-- Default box -->
            <div class="box box-primary box-solid">
                <div class="box-header with-border">
                    <h3 class="box-title">财务日报</h3>

                    <div class="box-tools pull-right">
                        <a href="javascript:;" class="btn btn-default" id="exportExcel"><i class="fa fa-file-o">导出Excel</i></a>
                    </div>
                </div>
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th></th>
                            <th>流水号</th>
                            <th>创建日期</th>
                            <th>类型</th>
                            <th>金额</th>
                            <th>业务模块</th>
                            <th>业务流水</th>
                            <th>状态</th>
                            <th>备注</th>
                            <th>#</th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
            <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
            <div class="form-group row">
                <div class="col-lg-12">
                    <div id="in" style="padding-left: 20px;width: 1200px;height:400px;"></div>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

</div>
<%@include file="../include/js.jsp"%>
<script src="/static/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="/static/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="/static/plugins/layer/layer/layer.js"></script>
<script src="/static/plugins/moment.js"></script>
<script src="/static/plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="/static/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script src="/static/plugins/echarts.min.js"></script>
<script>
    $(function () {
        if($("#date").val()==""){
            $("#date").val(moment().format("YYYY-MM-DD"));
        }


        var echartsLoad = function () {
            //echart饼状图:基于准备好的dom，初始化echarts实例
            var inChart = echarts.init(document.getElementById("in"));
            // 指定图表的配置项和数据
            inChart.setOption({
                title: {
                    text: '当日收入饼状图',
                    subtext: '只统计收入',
                    x:'center'
                },
                tooltip: {
                    trigger: 'item',//触发类型
                    formatter: "{a} <br/>{b} : {c} ({d}%)"//{a}（系列名称），{b}（数据项名称），{c}（数值）, {d}（百分比）
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data:[]
                },
                series: [{
                    name: '收入',
                    type: 'pie',
                    radius : '55%',
                    data:[]
                }]
            });
            var names=[];    //
            var nums=[];    //
            var date = $("#date").val();
            $.ajax({
                type : "post",
                async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url : "/page/france/echarts/in/load",
                data : {"date":date},
                dataType : "json",//返回数据形式为json
                success : function(resp) {
                    if(resp.state =="success"){
                        for(var i=0;i<resp.data.length;i++){
                            var result = resp.data
                            names.push(result[i].name)
                            nums.push(result[i])
                        }
                        inChart.hideLoading(); //隐藏加载动画
                        inChart.setOption({    //加载数据图表
                            legend: {
                                data: names
                            },
                            series: [{
                                data: nums
                            }]
                        });
                    }else{
                        layer.msg(resp.message);
                    }
                },
                error : function() {
                    //请求失败时执行该函数
                    alert("图表请求数据失败!");
                    inChart.hideLoading();
                }
            });
        }

        echartsLoad();

        $("#date").datepicker({
            format:"yyyy-mm-dd",
            language:"zh-CN",
            autoclose:"true",
            endDate:moment().format("YYYY-MM-DD")
        }).on("changeDate",function (e) {

            var date = e.format(0,"yyyy-mm-dd");
            table.ajax.reload();
            echartsLoad();

        });
        var table = $(".table").DataTable({
            "lengthChange": false,
            /*"lengthMenu": [10,50,100],*/
            "pageLength": 25,
            "serverSide":true,//表示所有操作都在服务端进行
            "ordering": false,
            "autoWidth":false,
            "ajax":{
                "url":"/page/france/day/load",
                "type":"post",
                "data":function (obj) {
                    obj.day = $("#date").val();
                }

            },
            "searching":false,//不使用自带的搜索
            "order":[[0,'desc']],//默认排序顺序
            "columns":[
                {"data":"id","name":"id"},
                {"data":"serialNum"},
                {"data":"createDate"},
                {"data":"type"},
                {"data":"money"},
                {"data":"module"},
                {"data":"rentSerialNum"},
                {"data":"state"},
                {"data":"remark"},
                {"data":function (row) {
                    if(row.state =="未确认"){
                        return"<a href='javascript:;' class='confirm_btn' rel='"+row.id+"'>确认</a>";
                    }else{
                        return"";
                    }
                }}

            ],
            "columnDefs":[
                {targets:[0],visible:false},

            ],
            "language":{ //定义中文
                "search": "搜索:",
                "zeroRecords":    "没有匹配的数据",
                "lengthMenu":     "显示 _MENU_ 条数据",
                "info":           "显示从 _START_ 到 _END_ 条数据 共 _TOTAL_ 条数据",
                "infoFiltered":   "(从 _MAX_ 条数据中过滤得来)",
                "loadingRecords": "加载中...",
                "processing":     "处理中...",
                "paginate": {
                    "first":      "首页",
                    "last":       "末页",
                    "next":       "下一页",
                    "previous":   "上一页"
                }
            }

        });
        $(document).delegate(".confirm_btn","click",function () {
            var id = $(".confirm_btn").attr("rel");
            layer.confirm("确认修改吗？",function (index) {
                layer.close(index);
                $.post("/page/france/income" ,{"id":id}).done(function (resp) {
                    if(resp.state =="success"){
                        layer.msg("确认成功");
                        table.ajax.reload(false, null);
                        echartsLoad();
                    }else{layer.msg(resp.message)}
                }).error(function () {
                    layer.msg("服务器忙，请稍候")
                });
            });
        });
        //导出Excel文档
        $("#exportExcel").click(function () {
            var day = $("#date").val();
            window.location.href = "/page/france/day/"+day+"/data.xls";
        });

    });
</script>
</body>
</html>

