$(function () {

    $(".bing").click(function () {
       var keyWord = $(".search-input").val();
       search(keyWord,"bing");
    });

    $(".baidu").click(function () {
        var keyWord = $(".search-input").val();
        search(keyWord,"baidu");
    })

})


function search(keyWord,type) {
    $.ajax({
       type:"post",
        url:"/search",
        contentType:"application/json",
        data:JSON.stringify({"keyWord":keyWord,"type":type}),
        dataType:"json",
        async:false,
        success:function (data) {
            $("#search-content").html(JSON.stringify(data));
            var results=data.resuts;
            for(var i = 0;i<results.length;i++){
                if(results[i].imgUrl){
                    $(".bing-img").append("<img src='"+results[i].imgUrl+"'>");
                }

            }
        },
        error:function (data) {
            $("#search-content").html(JSON.stringify(data));
        }
    });
}