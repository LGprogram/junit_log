$(function () {
    var loadNotify = function () {
        $.post("/notify",function (json) {
            if(json.state=="success"&&json.data>0){

                $("#unReadNotifyNum").text(json.data);
            }
        });
    };
    loadNotify();
    var $login = $("#isLogin").text();
    if($login=="login"){
        setInterval(loadNotify,10*1000);
    }

});
