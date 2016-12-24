<%--
  Created by Matilda.
  Date: 2016/12/24
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--
  Created by Matilda.
  Date: 2016/12/23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
</form>
</body>
</html>

</body>
</html>
