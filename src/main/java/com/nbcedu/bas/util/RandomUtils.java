package com.nbcedu.bas.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 6位ID随机数
 * @author       作者 lqc
 * @version      版本 0.01
 * @filename     文件名 RandomUtils.java
 * @date         创建日期 2016年12月19日
 * @description  描述
 */
public class RandomUtils {

	private static final int min_num = 100000;
	private static final int max_num = 999999;
	private static final int max_valid_num = 500000; // 只生成50w数据多了抛异常
	// 需要填充历史数据ID
	private static Set<Integer> nums = Collections.synchronizedSet(new HashSet<Integer>());
	
	public static void init(Set<Integer> set){
		RandomUtils.nums = set;
	}
	
	/**
	 * 获取随机数
	 * 方法名称:get_random_num
	 * 作者:lqc
	 * 创建日期:2016年12月19日
	 * 方法描述:  
	 * @return int
	 * @throws Exception 
	 */
	public synchronized static int get_random_num(){
		Random random = new Random();    
		int random_num =  random.nextInt(max_num)%(max_num-min_num+1) + min_num;
		if(nums.size()>max_valid_num){
			return -1;
		}
		if(nums.contains(random_num)){
			get_random_num();//重新调用
		}else{
			nums.add(random_num);
		}
		return random_num;
	}	
}
