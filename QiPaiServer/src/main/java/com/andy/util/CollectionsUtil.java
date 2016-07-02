package com.andy.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CollectionsUtil {

	public static <T> List<T> union(List<T> srcList, List<T> destList) {
		
		for(T prop:destList){
			if(!srcList.contains(prop)){
				srcList.add(prop);
			}
		}
		return srcList;
	}
	
	public static <T> String join(Collection<T> list,String separator,Getter<T> getter){
		if(separator == null || separator.length() < 1)separator = ",";
		if(list != null && list.size()>0){
			StringBuffer sb = new StringBuffer();
			for(T l:list){
				sb.append(getter.get(l)).append(separator);
			}
			if(sb.length() > 0){
				return sb.substring(0, sb.length()-1);
			}
		}
		return "";
	}
	
	public static <T> List<T> toList(T[] array){
		if(array != null){
			List<T> list = new ArrayList<T>();
			for(T a:array){
				list.add(a);
			}
			return list;
		}
		return null;
	}
	
	public static <K,V> List<V> mapToList(Map<K, V> map){
		if(map != null){
			List<V> list = new ArrayList<V>();
			Set<K> set = map.keySet();
			for(K k:set ){
				list.add(map.get(k));
			}
			return list;
		}
		return null;
	}

	public static <T> void filter(Collection<T> list,ListPredicate<T> predicate){
		for (Iterator<T> itr = list.iterator(); itr.hasNext();) {  
			T t = itr.next();
			if(!predicate.apply(t)){
				itr.remove();
			}
		} 
	}
	
	/**
	 * 映射list的值
	 * @param list
	 * @param listMap
	 */
	public static <T> void map(List<T> list, ListMap<T> listMap) {
		if(list != null){
			for(int i=0;i<list.size();i++){
				T value = listMap.map(list.get(i));
				if(value != null){
					list.set(i,listMap.map(list.get(i)));
				}
			}
		}
	}
	/**
	 * 映射数组的值
	 * @param list
	 * @param listMap
	 */
	public static <T> void map(T[] array, ListMap<T> listMap) {
		if(array != null){
			for(int i=0;i<array.length;i++){
				T value = listMap.map(array[i]);
				if(value != null){
					array[i] = listMap.map(array[i]);
				}
			}
		}
	}
	
	public static int[] strs2Ints(String[] split) {
		if(split != null){
			int[] arrInt = new int[split.length];
			for(int i=0;i<arrInt.length;i++){
				arrInt[i] = Integer.parseInt(split[i]);
			}
			return arrInt;
		}
		return null;
	}
	
	public static List<Integer> str2ListI(String str) {
		if(str != null){
			String[] split = str.split(",");
			List<Integer> lbyte = new ArrayList<Integer>();
			for(int i=0;i<split.length;i++){
				lbyte.add(Integer.parseInt(split[i]));
			}
			return lbyte;
		}
		return null;
	}

	public static <T> boolean contains(T[] usecup, T value) {
		if(usecup != null){
			for(T t:usecup){
				if(t == value || t.equals(value)){
					return true;
				}
			}
		}
		return false;
	}
	
	public static <T> int indexOf(T[] usecup, T value) {
		if(usecup != null){
			for(int i=0;i<usecup.length;i++){
				if(usecup[i] == value || usecup[i].equals(value)){
					return i;
				}
			}
		}
		return -1;
	}
	public static <T> int indexOf(int[] usecup, int value) {
		if(usecup != null){
			for(int i=0;i<usecup.length;i++){
				if(usecup[i] == value){
					return i;
				}
			}
		}
		return -1;
	}
	
	public static boolean contains(int[] usecup, int value) {
		if(usecup != null){
			for(int t:usecup){
				if(t == value){
					return true;
				}
			}
		}
		return false;
	}
	
	public static <T> T[] contactArray(T[] one,T[] two){
		List<T> aL = Arrays.asList(one);  
		   List<T> bL = Arrays.asList(two);   
		   List<T> resultList = new ArrayList<T>();  
		   resultList.addAll(aL);
		   resultList.addAll(bL);
		   return resultList.toArray(one);
	}
	
	public static <K,V> String map2String(Map<K,V> map){
		if(map != null){
			StringBuilder sb = new StringBuilder();
			for(Entry<K, V> en:map.entrySet()){
				sb.append(en.getKey()).append("=").append(en.getValue()).append(",");
			}
			if(sb.length()>0){
				sb.deleteCharAt(sb.length()-1);
			}
			return sb.toString();
		}
		return null;
	}
	
	public static void insertArrayByte(byte[] array,int index,byte elem){
		if(array == null)return;
		int size = array.length;
		array = Arrays.copyOf(array, size+1);
		System.arraycopy(array, index, array, index + 1,
				 size - index);
		array[index] = elem;
	}
	
	public static interface Getter<T> {
		public String get(T t);
	}
	
	/**
	 * 过滤map值
	 * false-剔除,true-不变
	 */
	public static interface ListPredicate<T> {
		/**
		 * 过滤值
		 * @param t
		 * @return false 剔除,true 不变
		 */
		boolean apply(T t);
	}
	
	/**
	 * 映射list值
	 * 
	 */
	public static interface ListMap<T> {
		T map(T t);
	}
}
