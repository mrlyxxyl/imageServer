package com.yuexin.utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片压缩处理
 */
public class ImgCompress {
    private Image img;
    private int width;
    private int height;
    private String compressPathTwo;
    private String compressPathThree;

    /**
     * 构造函数
     */
    public ImgCompress(String srcFilePath, String compressPathTwo, String compressPathThree) throws IOException {
        this.compressPathTwo = compressPathTwo;
        this.compressPathThree = compressPathThree;
        File file = new File(srcFilePath);// 读入文件
        img = ImageIO.read(file);      // 构造Image对象
        width = img.getWidth(null);   // 得到源图宽
        height = img.getHeight(null); // 得到源图长
    }

    /**
     * 强制压缩/放大图片到固定的大小
     */
    public void resizeTwo() throws IOException {
        BufferedImage image = new BufferedImage(width / 2, height / 2, BufferedImage.TYPE_INT_RGB);
        image.getGraphics().drawImage(img, 0, 0, width / 2, height / 2, null); // 绘制缩小后的图
        File outFile = new File(compressPathTwo);
        FileOutputStream out = new FileOutputStream(outFile); // 输出到文件流
        // 可以正常实现bmp、png、gif转jpg
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(image);
        out.close();
    }

    /**
     * 强制压缩/放大图片到固定的大小
     */
    public void resizeThree() throws IOException {
        BufferedImage image = new BufferedImage(width / 3, height / 3, BufferedImage.TYPE_INT_RGB);
        image.getGraphics().drawImage(img, 0, 0, width / 3, height / 3, null); // 绘制缩小后的图
        File outFile = new File(compressPathThree);
        FileOutputStream out = new FileOutputStream(outFile); // 输出到文件流
        // 可以正常实现bmp、png、gif转jpg
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(image);
        out.close();
    }
}