$(function () {
    $("#emailBtn").click(function () {
        $("#emailForm").submit();
    })
    $("#emailForm").validate({
        errorElement:'span',
        errorClass:'text-error',
        rules:{
            required:true,
            email:true,
            remote:"/validate/email?type=1"
        },
        messages:{
            required:"请输入修改的邮箱",
            email:"请输入正确的邮箱格式",
            remote:"邮箱已被占用"
        },
        submitHandler:function (form) {
            $.ajax({
                url:"/setting?action=email",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function () {
                    $("#emailBtn").text("保存中...").attr("disabled","disabled");
                },
                success:function (data) {
                    if(data.state=="success"){
                        alert("修改成功");
                    }else{
                        alert(data.message);
                    }
                },
                error:function () {
                    alert("服务器错误");
                },
                complete:function () {
                    $("#emailBtn").text("保存").removeAttr("disabled");
                }
            });
        }
    });

    $("#pwdBtn").click(function () {
        $("#pwdForm").submit();
    });
    $("#pwdForm").validate({
        errorElement:'span',
        errorClass:'text-error',
        rules:{
            oldpassword:{
                required:true,
                rangelength:[6,18]

            },
            newpassword:{
                required:true,
                rangelength:[6,18]
            },
            repassword:{
                required:true,
                rangelength:[6,18],
                equalTo:"#newpassword"
            }
        },
        messages:{
            oldpassword:{
                required:"请输入原始密码",
                rangelength:"密码长度为6-18位"

            },
            newpassword:{
                required:"请输入原始密码",
                rangelength:"密码长度为6-18位"
            },
            repassword:{
                required:"请输入原始密码",
                rangelength:"密码长度为6-18位",
                equailTo:"两次输入不一致"
            }
        },
        submitHandler:function (form) {
            $.ajax({
                url:"/setting?action=password",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function () {
                    $("#pwdBtn").text("保存中...").attr("disabled","disabled");
                },
                success:function (data) {
                    if(data.state=="success"){
                        alert("修改成功,情重新登录");
                        window.location.href="/login";
                    }else{
                        alert(data.message);
                    }
                },
                error:function () {
                    alert("服务器错误");
                },
                complete:function () {
                    $("#pwdBtn").text("保存").removeAttr("disabled");
                }
            });
        }
    })
});