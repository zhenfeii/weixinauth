<%-- 微信登录 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>微信登录</title>
</head>
<body>

<h1 style="color: #0a628f">需要关注公众号才能登录成功</h1>
<table>
    <thead align="center">
        <tr>
            <td>公众号</td>
            <td>扫码登录</td>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>
                <div><img src="<%=request.getContextPath()%>/statics/img/account.jpg" alt="" style="height: 200px;width: 200px"></div>
            </td>
            <td>
                <div id="qrcode"></div>
            </td>
        </tr>
    </tbody>
</table>

<script src="<%=request.getContextPath()%>/statics/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/statics/js/jquery.qrcode.min.js"></script>
<script>

    var uuid = "${uuid}"
    var base = "<%=request.getContextPath()%>"
    console.log("uuid: " + uuid)

    var reUrl = "${reUrl}"
    console.log("reUrl: " + reUrl)

    //生成二维码
    $(function () {

        //生成二维码
        var text = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf7f7bd216a1d35c3&redirect_uri=http://vps.wiwikiky.top:8080/" + base +
            "/front/weixin/auth?uuid=" + uuid +
            "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect"
        $("#qrcode").qrcode({
            width: 200,
            height: 200,
            text: text
        })


        //ajax轮询: 询问微信是否确认登录
        var t = setInterval(queryWeixinisAuth, 1000)

        function queryWeixinisAuth() {
            $.ajax({
                url: base + '/front/login',
                method: 'post',
                data: {
                    uuid: uuid
                },
                dataType: 'json',
                success: function (res) {
                    if (res.errcode == 0) {
                        if (res.data.isAuth == true) {
                            alert("登录成功")
                            clearInterval(t)
                            //重定向
                            if(reUrl != ""){
                                window.location.href = reUrl
                            }else {
                                window.location.href = base
                            }
                        } else if (res.data.isAuth == false) {

                        } else {
                            alert("服务器出错")
                            clearInterval(t)
                        }
                    }
                }
            })
        }
    })
</script>
</body>
</html>
