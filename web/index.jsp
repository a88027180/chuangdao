<%--
  Created by Matilda.
  Date: 2016/12/20
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test file upload</title>
</head>
<body>
    <form action="/uploadFile" enctype="multipart/form-data" method="post">
        <input type="file" name="file" />
        <input type="text" name="turnUrl">
        <input type="submit" value="submit">
</form>
</body>
</html>
