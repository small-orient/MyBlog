//根据参数name传递获取值
function getParameter(name) {
    //定义正则
    var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)","i");
    var r = location.search.substr(1).match(reg);
    if (r != null) return(r[2]);return null;
}