package com.qipai.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.qipai.common.game.comp.Card;
import com.qipai.common.game.comp.LabaCard;
import com.qipai.common.model.User;

public class StringUtil {
	
	private static Logger logger = Logger.getLogger(StringUtil.class);
	
	public static String joinIds(List<Card> list,String spara){
		String result = "";
		if(list != null){
			for(int i=0;i<list.size();i++){
				if(i == 0){
					result += list.get(i).getId();
				}else{
					result += spara + list.get(i).getId();
				}
			}
		}
		return result;
	}
	
	public static String joinIds(Card[] array,String spara){
		String result = "";
		if(array != null){
			for(int i=0;i<array.length;i++){
				if(i == 0){
					result += array[i].getId();
				}else{
					result += spara + array[i].getId();
				}
			}
		}
		return result;
	}
	
	public static String joinIds(LabaCard[][] cards,String spara){
		String result = "";
		if(cards != null){
			for(int i=0;i<cards.length;i++){
				for(int j=0;j< cards[i].length;j++){
					if(i==0 && j==0){
						result += cards[i][j].getId();
					}else{
						result += spara + cards[i][j].getId();
					}
				}
			}
		}
		return result;
	}
	
	public static Map<String,String> fromUrlParam(String param){
		Map<String,String> result = new HashMap<String, String>();
		if(StringUtils.isNotBlank(param)){
			String[] para = param.split("&");
			for(String par:para){
				String[] pa = par.split("=");
				result.put(pa[0], pa[1]);
			}
		}
		return result;
	}
	
}
