<%--
  Created by Matilda.
  Date: 2016/12/23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="/uploadFile" method="post" enctype="multipart/form-data">
        上传视频:<input type="file" name="file">
        <input type="hidden" name="type" value="0">
        <input type="submit" value="提交">
    </form>
    <form action="/uploadFile" method="post" enctype="multipart/form-data">
        上传图片:<input type="file" name="file">
        <input type="hidden" name="type" value="1">
        <input type="submit" value="提交">
    </form>
    <form action="/uploadFile" method="post" enctype="multipart/form-data">
        上传pdf:<input type="file" name="file">
        <input type="hidden" name="type" value="2">
        <input type="submit" value="提交">
    </form>

    <form action="/check" method="post">
        验证码：<input type="text" name="validateCode" />
        <img alt="验证码看不清，换一张" src="/getCode?type=ch" id="validateCodeImg" onclick="changeImg();">
        <%--alt:alternative 当图片无法显示的时候显示的替代文字--%>
        <a href="javascript:void(0)" onclick="changeImg();">看不清，换一张</a>
        <br/>
        <input type="submit" value="提交">
    </form>
</body>

<script type="text/javascript">
    // 刷新验证码
    function changeImg() {
        // 只刷新了图片，没有刷新整个页面
        document.getElementById("validateCodeImg").src="/getCode?type=ch&"+Math.random();
    }
</script>
</html>
