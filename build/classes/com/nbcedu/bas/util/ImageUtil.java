package com.nbcedu.bas.util;

import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
 
public class ImageUtil {
 
	/** 
     * 指定大小进行缩放 
     * @param srcUrl 源图片地址 
     * @param targetUrl 目标图片地址 
     * @param width 宽 
     * @param height 高 
     * @throws IOException 
     */  
    public static void resize(String srcUrl,String targetUrl,int width,int height) throws IOException {  
        /* 
         * size(width,height) 若图片横比200小，高比300小，不变 
         * 若图片横比200小，高比300大，高缩小到300，图片比例不变 若图片横比200大，高比300小，横缩小到200，图片比例不变 
         * 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300 
         */  
        Thumbnails.of(srcUrl).size(width, height).toFile(targetUrl);  
    }
    
    /** 
     * 按照比例进行缩放 
     * @param srcUrl 源图片地址 
     * @param targetUrl 目标图片地址 
     * @param num 质量比例如 0.8 
     * @throws IOException 
     */  
    public static void scale(String srcUrl,String targetUrl,double num) throws IOException {  
        Thumbnails.of(srcUrl).scale(num).toFile(targetUrl );  
    }
    
    /** 
     * 裁剪 
     * @param srcUrl 源图片地址 
     * @param targetUrl 目标图片地址 
     * @param width 宽 
     * @param height 高 
     * @param pos 显示位置:  Positions.BOTTOM_RIGHT   
     * @param x 区域宽度  
     * @param y 区域高度 
     * @throws IOException 
     */  
    public static void cut(String srcUrl,String targetUrl,int width,int height,Positions pos,int x,int y)throws IOException {  
        Thumbnails.of(srcUrl).sourceRegion(pos,x,y).size(width, height).keepAspectRatio(false).toFile(targetUrl);  
    }
    
    /** 
     * 裁剪 
     * @param srcUrl 源图片地址 
     * @param targetUrl 目标图片地址 
     * @param width 宽 
     * @param height 高 
     * @param pos 显示位置:  Positions.BOTTOM_RIGHT   
     * @throws IOException 
     */  
    public static void cut(String srcUrl,String targetUrl,int width,int height,Positions pos)throws IOException {  
        Thumbnails.of(srcUrl).size(width, height).keepAspectRatio(false).toFile(targetUrl);  
    }
    
    /** 
     * 转化图像格式 
     * @param srcUrl 源图片地址 
     * @param targetUrl 目标图片地址 
     * @param width 宽 
     * @param height 高 
     * @param format 格式 如png/gif/jpg 
     * @throws IOException 
     */  
    public static void format(String srcUrl,String targetUrl,int width,int height,String format) throws IOException {  
        Thumbnails.of(srcUrl).size(width, height).outputFormat(format).toFile(targetUrl);  
    } 
    
}
