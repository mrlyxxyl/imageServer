<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 16-9-30
  Time: 上午10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("img").on("error", function () {
                $(this).attr("src", $(this).attr("src") + "!abc");
            });
        });
    </script>
</head>
<body>
<img src="http://imgcom.static.suishenyun.net/2b37625a4d0f118120037aa62a6875f1.jpg"/>
<img src="http://imgcom.static.suishenyun.net/2b37625a4d0f118120037aa62a6875f1.jpg"/>
<img src="http://imgcom.static.suishenyun.net/2b37625a4d0f118120037aa62a6875f1.jpg"/>
</body>
</html>