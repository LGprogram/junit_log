$(function () {
    /*获取URL中类似 redirect =/login 中redirect键的值/login*/
    function getParameterByName(name, url) {
        if (!url) {
            url = window.location.href;
        }
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }

    $("#loginBtn").click(function () {
        $("#loginForm").submit();
    });
    $("#loginForm").validate({
        errorElement:'span',
        errorClass:'text-error',
        rules:{
            username:{
                required:true,
                minlength:3
            },
            password:{
                required:true,
                rangelength:[6,18]
            }
        },
        messages:{
            username:{
                required:"请输入账号",
                minlength:"账号最少3个字符",

            },
            password:{
                required:"请输入密码",
                rangelength:"密码长度6-18个字符"
            },
        },
        submitHandler:function (form) {
            $.ajax({
                url:"/login",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function () {
                    $("#loginBtn").text("登录中...").attr("disabled","disabled");
                },
                success:function (data) {
                    if(data.state=="success"){
                        alert("登录成功");


                        var redirect =getParameterByName("redirect");
                        if(redirect) {
                            /*设置或者获取页面的标签值*/
                            var hash = window.location.hash;
                            console.log(hash);
                            if(hash){
                                window.location.href = redirect+hash;
                            }else{
                                window.location.href = redirect;
                            }

                        } else {
                            window.location.href = "/home";
                        }
                    }else{
                        alert(data.message);

                    }
                },
                error:function () {
                    alert("服务器错误");
                },
                complete:function () {
                    $("#loginBtn").text("登录").removeAttr("disabled");
                }

            });
        }
    });
});