package com.yuexin.controller.post;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpPostUtil {

    public static String upload(List<String> filePaths) {
        try {
            String BOUNDARY = "---------7d4a6d158c9"; // 定义数据分隔线
            URL url = new URL("http://localhost:8081/img/image/upload.do");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
            int size = filePaths.size();
            for (int i = 0; i < size; i++) {
                String filePath = filePaths.get(i);
                File file = new File(filePath);
                StringBuilder sb = new StringBuilder();
                sb.append("--").append(BOUNDARY).append("\r\n");
                sb.append("Content-Disposition: form-data;name=\"image\";filename=\"" + file.getName() + "\"\r\n");//注意此处的name = image 要和服务器的接收参数保持一致
                sb.append("Content-Type:application/octet-stream\r\n\r\n");

                byte[] data = sb.toString().getBytes();
                out.write(data);
                DataInputStream in = new DataInputStream(new FileInputStream(file));
                int bytes;
                byte[] bufferOut = new byte[1024];
                while ((bytes = in.read(bufferOut)) != -1) {
                    out.write(bufferOut, 0, bytes);
                }
                out.write("\r\n".getBytes()); //多个文件时，二个文件之间加入这个
                in.close();
            }
            out.write(end_data);
            out.flush();
            out.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuffer result = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        List<String> filePaths = new ArrayList<String>();
        filePaths.add("d:/aa.jpg");
        System.out.println(upload(filePaths));
    }
}