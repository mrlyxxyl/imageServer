package com.yuexin.test;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

import java.io.IOException;

/**
 * User: LiWenC
 * Date: 16-9-2
 */
public class HTTPTest {
    @Test
    public void testPostMethod() {
        HttpClient httpclient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://192.168.10.153:8081/img/pictures/201609/9ea6c8011ff4408494c37c4287db93dc_1474613552381.jpg");
        postMethod.setRequestHeader("Content-Secret", "hello");
        try {
            int statusCode = httpclient.executeMethod(postMethod);
            System.out.println(statusCode);
            System.out.println(postMethod.getResponseBodyAsString());
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetMethod() {
        HttpClient httpclient = new HttpClient();
        GetMethod getMethod = new GetMethod("http://localhost:8080/mvc_maven/person/testParam.do?name=get");//GEt方法的参数直接跟在地址后面
        try {
            int statusCode = httpclient.executeMethod(getMethod);
            System.out.println(statusCode);
            System.out.println(getMethod.getResponseBodyAsString());
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
