$(function () {
    $("#type").change(function () {
        var value = $(this).val();
        if(value=="email"){
            $("#typename").text("电子邮件");
        }else{
            $("#typename").text("手机号码");
        }
    });
    $("#btn").click(function () {
        $("#form").submit();
    });
    $("#form").validate({
        errorElement:'span',
        errorClass:'text-error',
        rules:{
            value:{
                required:true
            }
        },
        messages:{
            value:{
                required:"此项必填"
            }
        },
        submitHandler:function (form) {
          $.ajax({
              url:"/foundPassword",
              type:"post",
              data:$(form).serialize(),
              berforeSend:function () {
                  $("#btn").text("提交中...").attr("disabled","disabled");
              },
              success:function (data) {
                   if(data.state=="success"){
                       alert("请去邮箱进行找回密码的下一步操作")
                   }else{
                       alert(data.message);
                   }
              },
              error:function () {
                  alert("服务器错误");
              },
              complete:function () {
                  $("#btn").text("提交").removeAttr("disabled");
              }
          })

        }
    });
});