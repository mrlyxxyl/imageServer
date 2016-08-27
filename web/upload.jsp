<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>hello</title>
    <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript">

        $(function () {
            $("#host").val(getRootPath());

            $(':file').change(function (event) {
                // 根据这个 <input> 获取文件的 HTML5 js 对象
                var files = event.target.files, file;
                if (files && files.length > 0) {
                    // 获取目前上传的文件
                    file = files[0];
                    // 那么我们可以做一下诸如文件大小校验的动作
                    if (file.size > 1024 * 1024 * 2) {
                        alert('图片大小不能超过 2MB!');
                        return false;
                    }
                    // !!!!!!
                    // 下面是关键的关键，通过这个 file 对象生成一个可用的图像 URL
                    // 获取 window 的 URL 工具
                    var URL = window.URL || window.webkitURL;
                    // 通过 file 生成目标 url
                    var imgURL = URL.createObjectURL(file);
                    // 用这个 URL 产生一个 <img> 将其显示出来
                    $(this).parent("td").next("td").empty().append($('<img/>').attr('src', imgURL).css("width", "50%"));
                    // 使用下面这句可以在内存中释放对此 url 的伺服，跑了之后那个 URL 就无效了
                    // URL.revokeObjectURL(imgURL);
                }
            });
        });

        function getRootPath() {
            var curWwwPath = document.location.href;
            var pathName = document.location.pathname;
            var pos = curWwwPath.indexOf(pathName);
            var localPath = curWwwPath.substring(0, pos);
            return localPath;
        }
    </script>
</head>
<body>
<form action="image/upload.do" method="post" enctype="multipart/form-data">
    <input id="host" type="hidden" name="host"/>
    <table width="600">
        <tr>
            <td><input type="file" name="image"/></td>
            <td></td>
        </tr>
        <tr>
            <td><input type="file" name="image"/></td>
            <td></td>
        </tr>
        <tr>
            <td><input type="file" name="image"/></td>
            <td></td>
        </tr>
        <tr>
            <td><input type="file" name="image"/></td>
            <td></td>
        </tr>
        <tr>
            <td><input type="submit" value="上传"/></td>
            <td></td>
        </tr>
    </table>
</form>
</body>
</html>