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
                        <label>选择要查看的月份</label>
                        <input type="text" class="form-control" value="${param.month}" id="month">
                    </form>
                </div>
            </div>

            <!-- Default box -->
            <div class="box box-primary box-solid">
                <div class="box-header with-border">
                    <h3 class="box-title">财务月报</h3>

                    <div class="box-tools pull-right">
                        <a href="javascript:;" class="btn btn-default" id="exportExcel"><i class="fa fa-file-o">导出Excel</i></a>
                    </div>
                </div>
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>日期</th>
                            <th>当日收入</th>
                            <th>当日支出</th>
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
                    <div id="monthforEchart" style="width:auto;height:800px;"></div>
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
        if($("#month").val()==""){
            $("#month").val(moment().format("YYYY-MM"));
        }
        var monthChartLoad = function () {
            var monthChart = echarts.init(document.getElementById('monthforEchart'));
            var days=[];    //
            var incomes=[];    //
            var expends=[];
            var month = $("#month").val();
            /*var num = month.split("-")[1] ;*/
            monthChart.setOption({
                title : {
                    text: ''+month+'收入支出统计图',
                    subtext: ''
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['收入','支出']
                },
                toolbox: {
                    show : true,
                    feature : {
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [ {
                    type : 'category',
                    data:days
                }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'收入',
                        type:'bar',
                        data:incomes,
                        markPoint : {
                            data : [
                                {type : 'max', name: '最大值'},
                                {type : 'min', name: '最小值'}
                            ]
                        },
                        markLine : {
                            data : [
                                {type : 'average', name: '平均值'}
                            ]
                        }
                    },
                    {
                        name:'支出',
                        type:'bar',
                        data:expends,
                        markPoint : {
                            data : [
                                 {name : '年最高', value : 9000},
                                 {name : '年最低', value : 230}
                            ]
                        },
                        markLine : {
                            data : [
                                {type : 'average', name : '平均值'}
                            ]
                        }
                    }
                ]
            });
            $.ajax({
                type : "post",
                async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url : "/page/france/echarts/month/load",
                data : {"month":month},
                dataType : "json",//返回数据形式为json
                success : function(resp) {
                    if(resp.state =="success"){
                        for(var i=0;i<resp.data.length;i++){
                            var result = resp.data;
                            var date = result[i].date;
                            var numArray = date.split("-");
                            var day = numArray[numArray.length-1];
                            days.push(day);
                            incomes.push(result[i].income);
                            expends.push(result[i].expend);
                        }
                        monthChart.hideLoading(); //隐藏加载动画
                        monthChart.setOption({
                            xAxis : [ {
                                data:days
                            }],
                            series : [
                                {data:incomes},
                                {data:expends}
                            ]
                        })   //加载数据图表
                    }else{
                        layer.msg(resp.message);
                    }
                },
                error : function() {
                    alert("图表请求数据失败!");
                    monthChart.hideLoading();
                }
            });

        }
        monthChartLoad();

        $("#month").datepicker({
            format:"yyyy-mm",
            language:"zh-CN",
            autoclose:"true",
            /*todayBtn: true,*/
            startView: 'year',
            minView:'month',
            maxView:'decade',

            /* endDate:moment().format("YYYY-MM")*/
        }).on("changeDate",function (e) {

            var month = e.format(0,"yyyy-mm");
            table.ajax.reload();
            monthChartLoad();

        });

        var table = $(".table").DataTable({
            "lengthChange": false,
            /*"lengthMenu": [10,50,100],*/
            "pageLength": 25,
            "serverSide":true,//表示所有操作都在服务端进行
            "ordering": false,
            "autoWidth":false,
            "ajax":{
                "url":"/page/france/month/load",
                "type":"post",
                "data":function (obj) {
                    obj.month = $("#month").val();
                }
            },
            "searching":false,//不使用自带的搜索
            "order":[[0,'desc']],//默认排序顺序
            "columns":[
                {"data":"date"},
                {"data":"income"},
                {"data":"expend"},
                {"data":"remark"},
                {"data":function (row) {
                    return"<a href='/page/france/day/"+row.date+"/detail'>详情</a>";
                }}

            ],
            "columnDefs":[
                /*{targets:[0],visible:false},*/

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




    });
</script>
</body>
</html>

