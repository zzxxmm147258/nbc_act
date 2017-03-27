package com.nbcedu.bas.util;


/**
 * 图片剪切压缩任务
 * @author       作者 lqc
 * @version      版本 0.01
 * @filename     文件名 ImageCutTask.java
 * @date         创建日期 2016年12月15日
 * @description  描述
 */
public class Task implements Runnable{

	private String file_path="";
	
	public Task(String file_path){
		this.file_path = file_path;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ImageCutTask.image_cut(file_path);
	}

}
