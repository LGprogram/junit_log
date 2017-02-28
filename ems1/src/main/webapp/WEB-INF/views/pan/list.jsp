<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>网盘系统</title>
    <%@include file="../include/css.jsp"%>
    <link rel="stylesheet" href="/static/js/uploader/webuploader.css">
    <style>
        #picker{
            float:left;
            margin-right: 10px;
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <%@include file="../include/header.jsp"%>
    <jsp:include page="../include/asider.jsp">
        <jsp:param name="menu" value="sys_out"/>
    </jsp:include>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box box-primary box-solid">
                <div class="box-header with-border">
                    <h3 class="box-title">网盘系统</h3>
                </div>
                <div id="picker">上传文件</div>
                <button class="btn btn-primary" id="newBtn">新建文件夹</button>
                <div class="box-body">
                    <table class="table">
                        <thead>
                            <tr>
                                <th></th>
                                <th>名称</th>
                                <th>大小</th>
                                <th>创建时间</th>
                                <th>创建人</th>
                                <th>#</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty diskList}">
                            <tr>
                                <td colspan="6">暂无文件</td>
                            </tr>
                        </c:if>
                        <c:forEach items="${diskList}" var="disk">

                            <tr>
                                <td>
                                    <c:choose>
                                        <c:when test="${disk.type== 'file'}">
                                            <i class="fa fa-file-o"></i>
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fa fa-folder-o"></i>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${disk.type=='file'}">
                                            <a href="/pan/download/${disk.id}">${disk.sourceName}</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="/pan?path=${disk.id}">${disk.sourceName}</a>
                                        </c:otherwise>
                                    </c:choose>

                                </td>
                                <td>${disk.size}</td>
                                <td>${disk.createTime}</td>
                                <td>${disk.createUser}</td>
                                <td><a href="javascript:;" class="remove" rel="${disk.id}"><i class="fa fa-trash text-danger"></i></a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
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
<%@include file="../include/js.jsp"%>
<script src="/static/js/uploader/webuploader.min.js"></script>
<script src="/static/plugins/layer/layer/layer.js"></script>
<script>
    $(function () {
        var fid = ${fid};
        //文件上传
        var uploader = WebUploader.create({
            swf:"/static/js/uploader/Uploader.swf",
            server:"/pan/upload",
            pick:"#picker",
            fieVal:"file",
            auto:"true",
            formData:{"fid":fid}
        });
        uploader.on("uploadSuccess",function(file,resp){
            if(resp.state == 'success') {
                window.history.go(0);
            } else {
                layer.msg(resp.message);
            }
        });
        uploader.on("uploadError",function(){
            layer.msg("服务器异常");
        });
        //新建文件夹
        $("#newBtn").click(function () {
            layer.prompt({title:"输入新建文件名"},function (text,index) {
                layer.close(index);
                $.post("/pan/new",{"fid":fid,"sourceName":text}).done(function (resp) {
                    if(resp.state=="success"){
                        window.history.go(0);

                    }else{
                        layer.msg(resp.message);
                    }
                }).error(function () {
                    layer.msg("服务器忙，请稍候");
                })
            });
        });

        //删除文件或文件夹
        $(".remove").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("确定要删除吗",function (index) {
                layer.close(index);
                $.get("/pan/del",{"id":id}).done(function (resp) {
                    if(resp.state =="success"){
                        layer.msg("删除成功");
                        window.history.go(0);
                    }else {
                        layer.msg(resp.message);
                    }
                }).error(function () {
                    layer.msg("服务器忙，请稍候")
                });
            });

        });

    });
</script>
</body>
</html>

