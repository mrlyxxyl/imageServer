package com.yuexin.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * User: LiWenC
 * Date: 16-9-18
 */
@WebServlet
public class ImageAccessServlet extends HttpServlet {

    private static String key = "hello";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletContext().getRealPath("/upload");
        RandomAccessFile randomFile = new RandomAccessFile(path + "/" + "242dd42a2834349bc403d0a3cbea15ce36d3be8b.jpg", "r");
        randomFile.seek(key.length());
        resp.setContentType("image/jpg");
        OutputStream os = resp.getOutputStream();
        int len;
        byte[] bytes = new byte[1024];
        while ((len = randomFile.read(bytes)) > 0) {
            os.write(bytes, 0, len);
        }
        os.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
