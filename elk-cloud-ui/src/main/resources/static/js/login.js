$(function (){
    $(".toLogin").click(function (){
        login();
    })
})

function login(){
    var username = $("input[name= 'User Name']").val();
    var password = $("input[name = 'Password']").val();

    $.ajax({
        type:"post",
        url:"/login",
        contentType:"application/json",
        data:JSON.stringify({"username":username,"password":password}),
        dataType:"json",
        async:false,
        complete:function (XMLHttpRequest){
            const REDIRECT = XMLHttpRequest.getResponseHeader("REDIRECT");
            //如果响应头中包含 REDIRECT 则说明是我们需要进行重定向的
            if (REDIRECT == "REDIRECT") {
                window.location.href = XMLHttpRequest.getResponseHeader("CONTEXTPATH");
            }
        },
        error:function (data){
            alert(data.message);
        }
    })
}