$(function (){
    $(".to-search").click(function (){
        search();
    })
})

function search(){
    $(".search-result").html("");

    var dateStart = $("#dateStart").val();
    var dateEnd = $("#dateEnd").val();
    var appName = $("#appName").val();
    var traceId = $("#traceId").val();
    var className = $("#className").val();
    var level = $("#logLevel").val();
    var message = $("#message").val();


    $.ajax({
        type:"post",
        url:"/searchDevLog",
        contentType:"application/json",
        data:JSON.stringify({"dateStart":dateStart,"dateEnd":dateEnd,"level":level,"message":message}),
        dataType:"json",
        async:false,
        success:function (data) {
            if(data){
                for(var i=0;i<data.length;i++){
                    var message = data[i].message.length>100?data[i].message.substring(0,100):data[i].message;
                    var tr = "<tr><td>"+data[i].appName
                        +"</td><td>"+data[i].className
                        +"</td><td>"+data[i].host
                        +"</td><td>"+data[i].port
                        +"</td><td>"+data[i].level
                        +"</td><td>"+message
                        +"</td><td>"+data[i].timestamp+"</td></tr>";
                    $(".search-result").append(tr);
                }

            }
        },
        error:function (data) {
            $(".search-result").html(JSON.stringify(data));
        }
    });
}