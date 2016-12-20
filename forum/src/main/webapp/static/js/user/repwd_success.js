$(function () {
    $("#repwdBtn").click(function () {
        $("#repwdForm").submit();
    });
    $("#repwdForm").validate({
        errorElement:'span',
        errorClass:'text-error',
        rules:{
            password:{
                required:true,
                rangelength:[6,18]
            },
            repassword:{
                required:true,
                rangelength:[6,18],
                equalTo:"#password"
            }
        },
        messages:{
            required:"请输入新密码",
            rangelength:"密码长度6-18位",
            equalTo:"两次输入不一致"
        },
        submitHandler:function (form) {
            $.ajax({
                url:"/user/foundPwd",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function () {
                    $("#repwdBtn").text("提交中...").attr("disabled","disabled");
                },
                success:function (data) {
                    if(data.state=="success"){
                        alert("提交成功");
                        window.location.href="/login";
                    }else{
                        alert(data.message);

                    }
                },
                error:function () {
                    alert("服务器错误");
                },
                complete:function () {
                    $("#repwdBtn").text("提交").removeAttr("disabled");
                }

            });
        }
    })
});