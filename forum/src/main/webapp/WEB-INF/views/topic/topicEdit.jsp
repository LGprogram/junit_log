<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>修改主题</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
    <link rel="stylesheet" href="/static/css/simditor-emoji.css">
</head>
<body>
<%@include file="../include/navbar.jsp"%>
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-edit"></i> 发布新主题</span>
        </div>

        <form action="" style="padding: 20px" id="topicForm" >
            <label class="control-label">主题标题</label>
            <input type="hidden" id="topicid" name="topicid" value="${requestScope.topic.id}">
            <input type="text" name="title" style="width: 100%;box-sizing: border-box;height: 30px" value="${requestScope.topic.title}" >
            <label class="control-label">正文</label>
            <textarea name="content" id="editor">${requestScope.topic.content}</textarea>

            <select name="nodeid" id="nodeid" style="margin-top:15px;">
                <option  value="">请选择节点</option>
                <c:forEach items="${requestScope.nodeList}" var="node">
                    <option ${requestScope.topic.nodeid== node.id?'selected':''} value="${node.id}">${node.nodename}</option>
                </c:forEach>
            </select>

        </form>
        <div class="form-actions" style="text-align: right">
            <button id="topicBtn" type="button" class="btn btn-primary">发布主题</button>
        </div>


    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/simditor-emoji.js"></script>
<script src="/static/js/user/notify.js"></script>

<script>
    $(function(){
        var editor = new Simditor({
            textarea: $('#editor'),
            toolbar:['emoji', 'title', 'bold', 'italic', 'underline', 'strikethrough', '|', 'ol', 'ul', 'blockquote', 'code', 'table', '|', 'link', 'image', 'hr', '|', 'indent', 'outdent'],
            emoji:{
                imagePath:'/static/img/emoji',
                images: ['+1.png',
                    '100.png',
                    '109.png']
            },
            upload:{
                url:'http://up-z1.qiniu.com/',
                fileKey:'file',
                params:{"token":"${token}"}
            }
        });
        $("#topicBtn").click(function () {
            $("#topicForm").submit();
        })
        $("#topicForm").validate({
            errorElement:"span",
            errorClass:"text-error",
            rules:{
                title:{
                    required:true
                },
                node:{
                    required:true
                }
            },
            messages:{
                title:{
                    required:"主题不能为空"
                },
                node:{
                    required:"请选择节点"
                }
            },
            submitHandler:function (form) {
                $.ajax({
                    url:"/topicEdit",
                    type:"post",
                    data:$(form).serialize(),
                    beforeSend:function () {
                        $("#topicBtn").text("发布中...").attr("disabled","disabled");
                    },
                    success:function (json) {
                        if(json.state="success"){
                            window.location.href="/topicDetail?topicid="+json.data.id;
                        }else{
                            alert("修改主题异常");
                        }
                    },
                    error:function () {
                        alert("服务器异常");
                    },
                    complete:function () {
                        $("#topicBtn").text("发布主题").removeAttr("disabled");
                    }
                });

            }
        })
    });
</script>

</body>
</html>

