<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>hello</title>
</head>
<body>
<form action="imageUploadServlet" method="post" enctype="multipart/form-data">
    <input type="file" name="image"/>
    <input type="submit" value="上传"/>
</form>
<div id="imgPre">

</div>
</body>
</html>