package com.yuexin.controller;

import com.yuexin.bean.FileUrl;
import com.yuexin.utils.DateUtil;
import com.yuexin.utils.GenResult;
import com.yuexin.utils.HttpPostUtil;
import com.yuexin.utils.ImgCompress;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "image")
public class ImageController {

    private static Logger log = Logger.getLogger(ImageController.class);

    /**
     * 上传图片
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "upload")
    @ResponseBody
    public Map<String, Object> upload(HttpServletRequest request, @RequestParam(required = false) MultipartFile[] image, String host) {
        String contextPath = request.getContextPath();
        try {
            if (image != null && image.length > 0) {
                List<String> imageUrls = new ArrayList<String>();
                for (int i = 0; i < image.length; i++) {
                    MultipartFile file = image[i];
                    String fileName = file.getOriginalFilename();
                    String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
                    String fileEName = UUID.randomUUID().toString().replaceAll("-", "") + "_" + System.currentTimeMillis() + fileSuffix;
                    InputStream is = file.getInputStream();
                    String currDate = DateUtil.getYearMonth();
                    String filePath = request.getServletContext().getRealPath("/pictures/" + currDate + "/");
                    File fileTmp = new File(filePath);
                    if (!fileTmp.exists()) {
                        fileTmp.mkdirs();
                    }
                    FileOutputStream fos = new FileOutputStream(filePath + "/" + fileEName);
                    byte[] buf = new byte[1024];
                    int j;
                    while ((j = is.read(buf)) > 0) {
                        fos.write(buf, 0, j);
                    }
                    fos.flush();
                    fos.close();
                    imageUrls.add(host + contextPath + "/pictures/" + currDate + "/" + fileEName);
                }
                return GenResult.SUCCESS.genResult(imageUrls);
            } else {
                return GenResult.PARAMS_ERROR.genResult();
            }
        } catch (Exception e) {
            log.error(e, e);
            return GenResult.FAILED.genResult();
        }
    }

    /**
     * 上传图片并且压缩
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "imageCompress")
    @ResponseBody
    public Map<String, Object> imageCompress(HttpServletRequest request, @RequestParam(required = false) MultipartFile[] image, String host) {
        String contextPath = request.getContextPath();
        try {
            if (image != null && image.length > 0) {
                List<FileUrl> imageUrls = new ArrayList<FileUrl>();
                for (int i = 0; i < image.length; i++) {
                    MultipartFile file = image[i];
                    FileUrl fileUrl = new FileUrl();
                    String currDate = DateUtil.getYearMonth();
                    String fileName = file.getOriginalFilename();
                    String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
                    String fileEName = UUID.randomUUID().toString().replaceAll("-", "") + "_" + System.currentTimeMillis();
                    String compressFileENameTwo = fileEName + "_com2";
                    String compressFileENameThree = fileEName + "_com3";

                    fileUrl.setSrcUrl(host + contextPath + "/pictures/" + currDate + "/" + fileEName + fileSuffix);
                    fileUrl.setCompressUrlTwo(host + contextPath + "/pictures/" + currDate + "/" + compressFileENameTwo + fileSuffix);
                    fileUrl.setCompressUrlThree(host + contextPath + "/pictures/" + currDate + "/" + compressFileENameThree + fileSuffix);
                    String filePath = request.getServletContext().getRealPath("/pictures/" + currDate + "/");
                    File fileTmp = new File(filePath);
                    if (!fileTmp.exists()) {
                        fileTmp.mkdirs();
                    }
                    uploadFile(file, fileSuffix, fileEName, filePath);
                    ImgCompress imgCom = new ImgCompress(filePath + "/" + fileEName + fileSuffix, filePath + "/" + compressFileENameTwo + fileSuffix, filePath + "/" + compressFileENameThree + fileSuffix);
                    imgCom.resizeTwo();
                    imgCom.resizeThree();
                    imageUrls.add(fileUrl);
                }
                return GenResult.SUCCESS.genResult(imageUrls);
            } else {
                return GenResult.PARAMS_ERROR.genResult();
            }
        } catch (Exception e) {
            log.error(e, e);
            return GenResult.FAILED.genResult();
        }
    }

    private void uploadFile(MultipartFile file, String fileSuffix, String fileEName, String filePath) throws IOException {
        InputStream is = file.getInputStream();
        FileOutputStream fos = new FileOutputStream(filePath + "/" + fileEName + fileSuffix);
        byte[] buf = new byte[1024];
        int j;
        while ((j = is.read(buf)) > 0) {
            fos.write(buf, 0, j);
        }
        fos.flush();
        fos.close();
    }

    /**
     * 上传到第三方图片服务器
     *
     * @return
     */
    @RequestMapping(value = "uploadFile")
    @ResponseBody
    public Map<String, Object> uploadFile(@RequestParam(required = false) MultipartFile[] image) {
        try {
            if (image != null && image.length > 0) {
                List<String> imageUrls = HttpPostUtil.upload(image);
                return GenResult.SUCCESS.genResult(imageUrls);
            } else {
                return GenResult.PARAMS_ERROR.genResult();
            }
        } catch (Exception e) {
            log.error(e, e);
            return GenResult.FAILED.genResult();
        }
    }
}
