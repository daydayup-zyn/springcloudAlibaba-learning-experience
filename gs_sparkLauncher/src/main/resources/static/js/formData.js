$(function () {
    $('#submit').click(function () {
        var u5_input = $("#u5_input").val()
        var u6_input = $("#u6_input").val()
        var u7_input = $("#u7_input").val()
        // var u8_input = $("#u8_input").val()
        // var u9_input = $("#u9_input").val()
        // var u10_input = $("#u10_input").val()
        // var u11_input = $("#u11_input").val()

        if (u5_input==""||u6_input==""||u7_input==""){
            return alert("参数不完整!");
        }else{
            getDataByType(u5_input,u6_input,u7_input)
        }

        $("#u5_input").remove()
        $("#u6_input").remove()
        $("#u7_input").remove()
    })

})
function getDataByType(a,b,c){
    var chartData = "";
    $.ajax({
        url:'submit.do',
        type:'post',
        async:false,
        data:{
            appName : a,
            jarPath : b,
            mainClass : c,
        },
        dataType:'json',
        success:function(data){
            console.warn(data);
            chartData = data;
        }
    });
    return chartData;
}