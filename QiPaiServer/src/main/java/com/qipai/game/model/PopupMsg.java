package com.qipai.game.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.qipai.common.model.User;

public class PopupMsg implements Serializable{

	private static Logger logger = Logger.getLogger(PopupMsg.class);
	
	private String popups = "";
	private String popups1 = "";// 消息（旧）
	private List<Integer> readedUsers = new ArrayList<Integer>();//已经读取过的人
	private List<Integer> readedUsers1 = new ArrayList<Integer>();//已经读取过的人(旧)
	private List<PopupMsgItem> popupsCur = new ArrayList<PopupMsgItem>();
	private static PopupMsg popupMsg = new PopupMsg();//弹屏信息
	private static PopupMsg popupMsgGG = new PopupMsg();//奖励公告
	
	public static PopupMsg instance(){
		return popupMsg;
	}
	public static PopupMsg instanceGG(){
		return popupMsgGG;
	}
	
	private PopupMsg() {
	}
	
	public void addMsg(User user,String msg){
		synchronized (popupsCur) {
			popupsCur.add(new PopupMsgItem(user, msg));
		}
	}
	
	public String getMsgs(int userId){
		if(!readedUsers1.contains(userId)){
			readedUsers1.add(userId);
			return popups1;
		}else if(!readedUsers.contains(userId)){
			readedUsers.add(userId);
			return popups;
		}
		return "";
	}

	public void refreshPopup() {
		popups1 = popups;
		readedUsers1 = readedUsers;
		popups = "";
		readedUsers = new ArrayList<Integer>();
		
		boolean isFirst = true;
		for(PopupMsgItem entry:popupsCur){
			User user = entry.user;
			String msg = entry.msg;
			try {
				msg = URLDecoder.decode(msg,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("弹屏异常", e);
			}
			if(isFirst){
				popups += user.getId()+"#"+user.getNickName()+"#"+msg;
			}else{
				popups += "&"+user.getId()+"#"+user.getNickName()+"#"+msg;
			}
			isFirst = false;
		}
		
		popupsCur.clear();
	}
	
	public static class PopupMsgItem{
		User user;
		String msg;
		
		public PopupMsgItem(User user,String msg) {
			this.user = user;
			this.msg = msg;
		}
	}
	
}
