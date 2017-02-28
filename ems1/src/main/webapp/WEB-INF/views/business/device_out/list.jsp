<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <%@include file="../../include/css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/datatables/jquery.dataTables.css">
    <link rel="stylesheet" href="/static/plugins/datatables/extensions/FixedHeader/css/dataTables.fixedHeader.min.css">
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
            <div class="box box-primary box-solid">
                <div class="box-header with-border">
                    <h3 class="box-title">设备租赁合同列表</h3>

                    <div class="box-tools pull-right">
                        <a href="#" class="btn btn-primary"><i class="fa fa-plus"></i></a>
                    </div>
                </div>
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>流水号</th>
                            <th>租赁公司</th>
                            <th>电话</th>
                            <th>租赁日期</th>
                            <th>归还日期</th>
                            <th>状态</th>
                            <th>租金</th>
                            <th>#</th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

</div>
<%@include file="../../include/js.jsp"%>
<script src="/static/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="/static/plugins/datatables/extensions/FixedHeader/js/dataTables.fixedHeader.min.js"></script>
<script src="/static/plugins/layer/layer/layer.js"></script>
<script>
    $(function () {
        var table= $(".table").DataTable({
            "lengthChange": false,
            /*"lengthMenu": [10,50,100],*/
            "pageLength": 25,
            "serverSide":true,//表示所有操作都在服务端进行
            "ordering": false,
            "autoWidth":false,
            "ajax":{
                "url":"/page/device/out/load",
                "type":"post",

            },
            "searching":false,//不使用自带的搜索
            "order":[[0,'desc']],//默认排序顺序
            "columns":[
                {"data":"id","name":"id"},
                {"data":function (row) {
                        return"<a href='/page/device/out/"+row.serialNum+"'>"+row.serialNum+"</a>"
                }},
                {"data":"companyName"},
                {"data":"tel"},
                {"data":"rentDate"},
                {"data":"backDate"},
                {"data":"state"},
                {"data":"totalPrice"},
                {"data":function (row) {
                if(row.state){
                    return"";
                }else {
                    return"<a href='javascript:;' rel='"+row.id+"' class='btn btn-xs btn-default checkBtn'><i class='fa fa-check'></i>完成</a>";
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
        new $.fn.dataTable.FixedHeader(table);
        $(document).delegate(".checkBtn","click",function () {
            var id = $(this).attr("rel");
            layer.confirm("确定要将合同修改为完成吗？",function (index) {
                $.post("/page/device/in",{"id":id}).done(function (resp) {
                    if(resp.data =="success" ){
                        /*重新加载页面*/
                       table.ajax.reload();
                    }
                }).error(function () {
                    layer.msg("服务器忙，请稍候")
                });
                layer.close(index);
            });
        });
    });
</script>
</body>
</html>

