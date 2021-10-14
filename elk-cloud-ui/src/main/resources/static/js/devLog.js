$(function (){
    $(".to-search").click(function (){
        search();
    })
})

function search(){
    var dateStart = $("#dateStart").val();
    var dateEnd = $("#dateEnd").val();
    var appName = $("#appName").val();
    var traceId = $("#traceId").val();
    var className = $("#className").val();
    var logLevel = $("#logLevel").val();
    var message = $("#message").val();


    $.ajax({
        type:"post",
        url:"/searchDevLog",
        contentType:"application/json",
        data:JSON.stringify({"dateStart":dateStart,"dateEnd":dateEnd,"message":message}),
        dataType:"json",
        async:false,
        success:function (data) {
        },
        error:function (data) {
        }
    });
}