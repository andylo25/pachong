package com.andy.util;

import java.util.List;
import java.util.Random;

public class RandomUtils {
	
	private static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String numberChar = "0123456789";
	
	public static Random random = new Random();

	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}
		return sb.toString();
	}
	
	public static String generateNumString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
		}
		return sb.toString();
	}

	public static String generateMixString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(letterChar.length())));
		}
		return sb.toString();
	}

	public static String generateLowerString(int length) {
		return generateMixString(length).toLowerCase();
	}

	public static String generateUpperString(int length) {
		return generateMixString(length).toUpperCase();
	}

	public static String generateZeroString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append('0');
		}
		return sb.toString();
	}

	public static String toFixdLengthString(long num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else {
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常!");
		}
		sb.append(strNum);
		return sb.toString();
	}

	public static String toFixdLengthString(int num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else {
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常!");
		}
		sb.append(strNum);
		return sb.toString();
	}
	
	/**
	 * 产生一个随机字符串 
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		String str = "abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
		StringBuffer sf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(62);// 0~61
			sf.append(str.charAt(number));
		}
		return sf.toString();
	}
	
	/**
	 * 产生一个随机数,大于等于min,小于max
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomNum(int min,int max){
		if(min >= max){
			return min;
		}
		return random.nextInt(max-min)+min;
	}
	
	/**
	 * 获取数组里的一个随机数
	 * @param nums
	 * @return
	 */
	public static int getRandomArray(int[] nums){ 
		if(nums == null || nums.length<1)return 0;
		return nums[random.nextInt(nums.length)];
	}
	
	/**
	 * 随机获取数组里的一个实例
	 * @param nums
	 * @return
	 */
	public static <T>T getRandomArray(T[] nums){ 
		if(nums == null || nums.length<1)return null;
		return nums[random.nextInt(nums.length)];
	}
	
	/**
	 * 获取List里的一个随机实例
	 * @param nums
	 * @return
	 */
	public static <T>T getRandomList(List<T> nums){ 
		if(nums == null || nums.size()<1)return null;
		return nums.get(random.nextInt(nums.size()));
	}
	
	/**
	 * 偶概率
	 * @return
	 */
	public static boolean countEven(){
		return random.nextBoolean();
	}
	
	/**
	 * 百分比概率
	 * @param probability
	 * @return
	 */
	public static boolean countPercent(int probability){
		if(random.nextInt(100) < probability){
			return true;
		}
		return false;
	}
	
	/**
	 * 万分比概率
	 * @param probability
	 * @return
	 */
	public static boolean countMyria(int probability){
		if(random.nextInt(10000) < probability){
			return true;
		}
		return false;
	}
	
	/**
	 * 任意概率
	 * @param probability
	 * @param max
	 * @return
	 */
	public static boolean countProbability(int probability,int max){
		if(random.nextInt(max) < probability){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println(generateString(15));
		System.out.println(generateMixString(15));
		System.out.println(generateLowerString(15));
		System.out.println(generateUpperString(15));
		System.out.println(generateZeroString(15));
		System.out.println(toFixdLengthString(123, 15));
		System.out.println(toFixdLengthString(123L, 15));
		System.out.println(generateNumString(4));
		System.out.println(UtilDatetime.getUstime());
	}
}
