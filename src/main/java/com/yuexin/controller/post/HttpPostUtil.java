package com.yuexin.controller.post;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpPostUtil {
    private URL url;
    private HttpURLConnection conn;
    private String boundary = "--------httpPost123";
    private Map<String, String> textParams = new HashMap<String, String>();
    private Map<String, File> fileParams = new HashMap<String, File>();
    private DataOutputStream ds;

    public HttpPostUtil(String url) throws Exception {
        this.url = new URL(url);
        conn = (HttpURLConnection) this.url.openConnection();
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setConnectTimeout(10000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        conn.connect();
    }


    //增加一个普通字符串数据到form表单数据中
    public void addTextParameter(String name, String value) {
        textParams.put(name, value);
    }

    //增加一个文件到form表单数据中
    public void addFileParameter(String name, File value) {
        fileParams.put(name, value);
    }

    // 清空所有已添加的form表单数据
    public void clearAllParameters() {
        textParams.clear();
        fileParams.clear();
    }

    // 发送数据到服务器，返回一个字节包含服务器的返回结果的数组
    public byte[] send() throws Exception {
        ds = new DataOutputStream(conn.getOutputStream());
        writeFileParams();
        writeStringParams();
        paramsEnd();
        InputStream in = conn.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int b;
        while ((b = in.read()) != -1) {
            out.write(b);
        }
        conn.disconnect();
        return out.toByteArray();
    }

    //普通字符串数据
    private void writeStringParams() throws Exception {
        for (Map.Entry<String, String> entry : textParams.entrySet()) {
            ds.writeBytes("--" + boundary + "\r\n");
            ds.writeBytes("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n");
            ds.writeBytes("\r\n");
            ds.writeBytes(encode(entry.getValue()) + "\r\n");
        }
    }

    //文件数据
    private void writeFileParams() throws Exception {
        for (Map.Entry<String, File> entry : fileParams.entrySet()) {
            String name = entry.getKey();
            File value = entry.getValue();
            ds.writeBytes("--" + boundary + "\r\n");
            ds.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + encode(value.getName()) + "\"\r\n");
            ds.writeBytes("Content-Type: " + getContentType(value) + "\r\n");
            ds.writeBytes("\r\n");
            ds.write(getBytes(value));
            ds.writeBytes("\r\n");
        }
    }

    private String getContentType(File f) throws Exception {
        ImageInputStream inputStream = ImageIO.createImageInputStream(f);
        if (inputStream == null) {
            return "application/octet-stream";
        }
        Iterator<ImageReader> it = ImageIO.getImageReaders(inputStream);
        inputStream.close();
        if (it.hasNext()) {
            return "image/" + it.next().getFormatName().toLowerCase();
        } else {
            return "application/octet-stream";
        }
    }

    //把文件转换成字节数组
    private byte[] getBytes(File f) throws Exception {
        FileInputStream in = new FileInputStream(f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        while ((n = in.read(b)) != -1) {
            out.write(b, 0, n);
        }
        in.close();
        return out.toByteArray();
    }

    //添加结尾数据
    private void paramsEnd() throws Exception {
        ds.writeBytes("--" + boundary + "--" + "\r\n");
        ds.writeBytes("\r\n");
    }

    // 对包含中文的字符串进行转码，此为UTF-8。服务器那边要进行一次解码
    private String encode(String value) throws Exception {
        return URLEncoder.encode(value, "UTF-8");
    }

    public static void main(String[] args) throws Exception {
        HttpPostUtil u = new HttpPostUtil("http://localhost:8080/imageServer/image/upload.do");
        u.addFileParameter("image", new File("D:\\index.jpg"));
        u.addTextParameter("host", "http://localhost:8080");
        byte[] b = u.send();
        u.clearAllParameters();
        String result = new String(b);
        System.out.println(result);
    }
}