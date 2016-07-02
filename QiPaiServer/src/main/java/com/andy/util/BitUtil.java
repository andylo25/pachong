package com.andy.util;

public class BitUtil {

	//-----------------------------------
	// 每位组成一个标志位，byte可表示7个标志，2种状态,flag-(0~6)
	//-----------------------------------
	public static boolean isFlag(byte options,int flag) {
		if(options < 0)return false;
		int fv = 1<<flag;
		return isSet(options, fv);
	}
	public static byte setFlag(byte options, int flag,boolean value) {
		if(options < 0)return 0;
		int fv = 1<<flag;
		return (byte) setValue(options, fv, value);
	}
	
	//-----------------------------------
	// 每位组成一个标志位，int可表示15个标志，2种状态,flag-(0~14)
	//-----------------------------------
	public static boolean isFlagInt(int options,int flag) {
		if(options < 0)return false;
		int fv = 1<<flag;
		return isSet(options, fv);
	}
	public static int setFlagInt(int options, int flag,boolean value) {
		if(options < 0)return 0;
		int fv = 1<<flag;
		return setValue(options, fv, value);
	}

	//-----------------------------------
	// 每位组成一个标志位，byte可表示7个标志，2种状态,fv-二进制对应位为1其他位为0，
	// 1,2,4,8,16,32,64对应1,2,3,4,5,6,7标志
	//-----------------------------------
	public static boolean isSet(int options,int fv) {
		return (options & fv) == fv;
	}
	public static byte setValue(byte options, int fv,boolean value) {
		if(value)
			options |= fv;
		else
			options &= ~fv;
		return options;
	}
	public static int setValue(int options, int fv,boolean value) {
		if(value)
			options |= fv;
		else
			options &= ~fv;
		return options;
	}
	
	//-----------------------------------
	// 2位组成一个标志位，byte可表示3个标志，4种状态
	//-----------------------------------
	/**
	 * 获取序列的状态
	 * @param setting 源值
	 * @param index 对应列
	 * @return 对应列状态
	 */
	public static byte get2Bit(byte setting,int index){
		return (byte) ((setting>>(2*index))&3);
	}
	/**
	 * 设置序列的状态
	 * @param setting 源值
	 * @param index 对应列
	 * @param val 对应状态
	 * @return 变化后的目标值
	 */
	public static byte set2Bit(byte setting,int index,byte val){
		int temp = ~(3<<(2*index));
		setting = (byte) (setting&temp|(val<<(2*index)));
		return setting;
	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		byte a = 23;
		System.out.println(Integer.toBinaryString(a));
		System.out.println(isFlag(a, 0));
//		a = setFlag(a, 3, true);
		System.out.println(isFlag(a, 1));
		System.out.println(isFlag(a, 2));
		System.out.println(isFlag(a, 3));
		System.out.println(isFlag(a, 4));
		System.out.println(isFlag(a, 8));
		byte b=0;
		System.out.println(b = set2Bit(b, 0, (byte) 1));
		System.out.println(get2Bit(b, 1));
		System.out.println(b = set2Bit(b, 1, (byte) 1));
		System.out.println(get2Bit(b, 0));
		System.out.println(b = set2Bit(b, 2, (byte) 2));
		System.out.println(get2Bit(b, 2));
		System.out.println(b = set2Bit(b, 3, (byte) 3));
//		System.out.println(b = set2Bit(b, 3, (byte) 3));
		System.out.println(get2Bit(b, 1));
		System.out.println(get2Bit(b, 3));
	}

}
