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
            <c:if test="${not empty sessionScope.curr_user}">
                <ul class="unstyled inline pull-left">
                    <c:choose>
                        <c:when test="${not empty requestScope.fav}">
                            <li><a href="javascript:;" id="favtopic">取消收藏</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="javascript:;" id="favtopic">加入收藏</a></li>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${not empty requestScope.thank}">
                            <li><a href="javascript:;" id="thanktopic">取消感谢</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="javascript:;" id="thanktopic">感谢</a></li>
                        </c:otherwise>
                    </c:choose>

                    <li><a href="/topicEdit?topicid=${requestScope.topic.id}">编辑</a></li>
                </ul>
            </c:if>
            <ul class="unstyled inline pull-right muted">
                <li>${requestScope.topic.clicknum}次点击</li>
                <li><span id="favSpan">${requestScope.topic.favnum}</span>人收藏</li>
                <li><span id="thankSpan">${requestScope.topic.thankyounum}</span>人感谢</li>
            </ul>
        </div>
    </div>
    <!--box end--><%--已更改--%>

    <div class="box" style="margin-top:20px;">
        <c:if test="${not empty sessionScope.curr_user}">
            <c:if test="${fn:length(replyList)>0}">
                <div class="talk-item muted" style="font-size: 12px">
                    ${fn:length(replyList)}个回复 | 直到<span id="lastreplytime">${topic.lastreplytime}</span>
                </div>
            </c:if>
        </c:if>
        <c:forEach items="${requestScope.replyList}" var="reply" varStatus="vs">
            <div class="talk-item">
                <table class="talk-table">
                    <a name="reply${vs.count}"></a>
                    <tr>
                        <td width="50">
                            <img class="avatar" src="${reply.user.avatar}?imageView2/1/w/40/h/40" alt="">
                        </td>
                        <td width="auto" rel="${vs.count}" class="re_td">
                            <a href=""  style="font-size: 12px">${reply.user.username}</a> <span style="font-size: 12px" class="reply">${reply.replytime}</span>
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
<script src="/static/js/user/notify.js"></script>
<script>
    $(function(){
        <c:if test="${not empty sessionScope.curr_user}">
            var editor = new Simditor({
                textarea: $('#editor'),
                toolbar:false
                //optional options
                /* hljs.initHighlightingOnLoad();*/
            });
            $(".replylink").click(function () {
                var count = $(this).attr("rel");
                var html = "回复 <a href='#reply"+count+"'>"+count+"</a> 楼：";
                editor.setValue(html+editor.getValue());
                window.location.href="#reply";
            });
            //复杂的回复
           /* $(".replylink").click(function () {
               $(this).parent().parent().find(".re_td").append(function () {

                    var html = "<a href='#'>${sessionScope.curr_user.username}:</a> ";
                    /!*var html2 ="<div width='auto' class='newtr'><input type='text' class='span8' id='input_newtodo'placeholder='+添加一个回复'></div>";*!/
                    var html2 ="<div width='auto'><div><img class='avatar' src='${sessionScope.curr_user.avatar}?imageView2/1/w/20/h/20'><a href='#'>${sessionScope.curr_user.username}:</a></div><input type='text' class='span8' id='input_newreply'placeholder='+添加一个回复'></div>";
                    /!*html2 = $(html2).find("input").val(html);*!/
//
                    return html2;

                });
               $("#input_newreply").keydown(function (event) {

                   if(event.which ==13){
                    $.post("/rereply").done(function () {
                        
                    });
                   }
               })
            });*/

        </c:if>
        hljs.initHighlightingOnLoad();

        $("#createtime").text(moment($("#createtime").text()).fromNow());
        $("#lastreplytime").text(moment($("#lastreplytime").text()).format("YYYY年MM月DD日 HH:mm:ss"));
        $(".reply").text(function () {
            var time = $(this).text();
            return moment(time).fromNow();
        });
        $("#replyBtn").click(function () {
            $("#replyForm").submit();
        });

        $("#favtopic").click(function () {
            var $this = $(this);
            var action="";
            if($this.text()=="加入收藏"){
                action="fav";
            }else{
                action="unfav";
            }
            $.post("/topicFav",{"action":action,"topicid":${topic.id}})
                .done(function (json) {
                    if(json.state=="success"){
                        if(action == "fav"){
                            $this.text("取消收藏");
                        }else{
                            $this.text("加入收藏");
                        }
                        $("#favSpan").text(json.data.favnum);
                    }else{
                        alert(json.message)
                    }
                })
                .error(function () {
                        alert("服务器错误");
                });
        });
        $("#thanktopic").click(function () {
            var $this = $(this);
            var action="";
            if($this.text()=="感谢"){
                action="thank";
            }else{
                action="unthank";
            }
            $.post("/topicThank",{"action":action,"topicid":${topic.id}})
                .done(function (json) {
                    if(json.state=="success"){
                        if(action == "thank"){
                            $this.text("取消感谢");
                        }else{
                            $this.text("感谢");
                        }
                        $("#thankSpan").text(json.data.thankyounum);
                    }else{
                        alert(json.message)
                    }
                })
                .error(function () {
                    alert("服务器错误");
                });
        });
    });
</script>

</body>
</html>