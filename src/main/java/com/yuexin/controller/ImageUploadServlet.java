package com.yuexin.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * User: LiWenC
 * Date: 16-9-18
 */
@WebServlet
@MultipartConfig
public class ImageUploadServlet extends HttpServlet {

    private static String key = "hello";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(403);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Part part = req.getPart("image");
        String disposition = part.getHeader("content-disposition");
        String fileName = disposition.substring(disposition.lastIndexOf("=") + 2, disposition.length() - 1);
        String uploadPath = req.getServletContext().getRealPath("/upload");
        File file = new File(uploadPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        InputStream is = part.getInputStream();
        FileOutputStream fos = new FileOutputStream(uploadPath + "/" + fileName);
        int len;
        fos.write(key.getBytes());
        byte[] buffer = new byte[1024];
        while ((len = is.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
        }
        fos.close();
        resp.getOutputStream().print("upload success!");
    }
}
