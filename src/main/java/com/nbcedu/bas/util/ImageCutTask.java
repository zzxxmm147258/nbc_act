package com.nbcedu.bas.util;

import java.io.IOException;

import net.coobird.thumbnailator.geometry.Positions;

/**
 * 图片剪切压缩任务
 * @author       作者 lqc
 * @version      版本 0.01
 * @filename     文件名 ImageCutTask.java
 * @date         创建日期 2016年12月15日
 * @description  描述
 */
public class ImageCutTask{

	public static void image_cut(String file_path) {
		// CUT001 --> s1 177*137
		// CUT002 --> s2 468*383
		// CUT003 --> s3 870*713
		String s1 = file_path.replace(".", "_s1.");
		String s2 = file_path.replace(".", "_s2.");
		String s3 = file_path.replace(".", "_s3.");
		try {
			ImageUtil.cut(file_path, s1, 177, 137, Positions.CENTER);
			ImageUtil.cut(file_path, s2, 468, 383, Positions.CENTER);
			ImageUtil.resize(file_path, s3, 870, 713);
		} catch (IOException e) {
			System.out.println("ex:"+file_path);
		}
		
	}

}
