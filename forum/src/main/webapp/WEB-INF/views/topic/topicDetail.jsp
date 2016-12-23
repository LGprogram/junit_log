<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>凯盛论坛-${requestScope.topic.title}</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
    <link rel="stylesheet" href="/static/css/styles/default.css">
    <style>
        body{
            background-image: url(/static/img/bg.jpg);
        }
        .simditor .simditor-body {
            min-height: 100px;
        }
    </style>
</head>
<body>
<%@include file="../include/navbar.jsp"%>
<div class="container">
    <div class="box">
        <ul class="breadcrumb" style="background-color: #fff;margin-bottom: 0px;">
            <li><a href="/home">首页</a> <span class="divider">/</span></li>
            <li class="active">${requestScope.topic.node.nodename}</li>
        </ul>
        <div class="topic-head">
            <img class="img-rounded avatar" src="${requestScope.topic.user.avatar}?imageView2/1/w/60/h/60" alt="">
            <h3 class="title">${requestScope.topic.title}</h3>
            <p class="topic-msg muted" ><a href="">${requestScope.topic.user.username}</a> ·<span id="createtime"> ${requestScope.topic.createtime}</span></p>
        </div>
        <div class="topic-body">
            ${requestScope.topic.content}
        </div>
        <div class="topic-toolbar">
            <ul class="unstyled inline pull-left">
                <li><a href="">加入收藏</a></li>
                <li><a href="">感谢</a></li>
                <li><a href=""></a></li>
            </ul>
            <ul class="unstyled inline pull-right muted">
                <li>${requestScope.topic.clicknum}次点击</li>
                <li>${requestScope.topic.favnum}人收藏</li>
                <li>${requestScope.topic.thankyounum}人感谢</li>
            </ul>
        </div>
    </div>
    <!--box end--><%--已更改--%>

    <div class="box" style="margin-top:20px;">
        <div class="talk-item muted" style="font-size: 12px">
            ${fn:length(replyList)}个回复 | 直到<span id="lastreplytime">${topic.lastreplytime}</span>
        </div>
        <c:forEach items="${requestScope.replyList}" var="reply" varStatus="vs">
            <div class="talk-item">
                <table class="talk-table">
                    <a name="reply${vs.count}"></a>
                    <tr>
                        <td width="50">
                            <img class="avatar" src="${reply.user.avatar}?imageView2/1/w/40/h/40" alt="">
                        </td>
                        <td width="auto">
                            <a href="" rel="xxx" style="font-size: 12px">${reply.user.username}</a> <span style="font-size: 12px" class="reply">${reply.replytime}</span>
                            <br>
                            <p style="font-size: 14px">${reply.content}</p>
                        </td>
                        <td width="70" align="right" style="font-size: 12px">
                            <a href="javascript:;" rel="${vs.count}" class="replylink" title="回复"><i class="fa fa-reply"></i></a>&nbsp;
                            <span class="badge">${vs.count}</span>
                        </td>
                    </tr>
                </table>
            </div>
        </c:forEach>





    </div>

    <c:choose>
        <c:when test="${not empty sessionScope.curr_user}">
            <div class="box" style="margin:20px 0px;">
                <a name="reply"></a>
                <div class="talk-item muted" style="font-size: 12px"><i class="fa fa-plus"></i> 添加一条新回复</div>
                <form action="/reply" method="post" id="replyForm" style="padding: 15px;margin-bottom:0px;">
                    <input type="hidden" name="topicid" value="${requestScope.topic.id}">
                    <textarea name="content" id="editor"></textarea>
                </form>
                <div class="talk-item muted" style="text-align: right;font-size: 12px">
                    <span class="pull-left">请尽量让自己的回复能够对别人有帮助回复</span>
                    <button id="replyBtn" class="btn btn-primary">发布</button>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="box" style="margin:20px 0px;">
                <div class="talk-item">请<a href="/login?redirect=topicDetail?topicid=${topic.id}#reply">登录</a>才能回复</div>
            </div>
        </c:otherwise>
    </c:choose>


</div>
<!--container end-->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>
<script src="/static/js/highlight.pack.js"></script>
<script src="//cdn.bootcss.com/moment.js/2.10.6/moment.min.js"></script>
<script src="//cdn.bootcss.com/moment.js/2.10.6/locale/zh-cn.js"></script>
<script>
    $(function(){
        var editor = new Simditor({
            textarea: $('#editor'),
            toolbar:false
            //optional options
           /* hljs.initHighlightingOnLoad();*/
        });
        hljs.initHighlightingOnLoad();

        $("#createtime").text(moment($("#createtime").text()).fromNow());
        $("#lastreplytime").text(moment($("#lastreplytime").text()).format("YYYY年MM月DD日 HH:mm:ss"));
        $(".reply").text(function () {
            var time = $(this).text();
            return moment(time).fromNow();
        });

        $(".replylink").click(function () {
            var count = $(this).attr("rel");
            var html = "回复 <a href='#reply"+count+"'>"+count+"</a> 楼：";
            editor.setValue(html+editor.getValue());
            window.location.href="#reply";
        });


        $("#replyBtn").click(function () {
            $("#replyForm").submit();
        });
    });
</script>

</body>
</html>