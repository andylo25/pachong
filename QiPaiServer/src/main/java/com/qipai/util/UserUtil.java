package com.qipai.util;

import com.qipai.common.model.User;
import com.qipai.game.model.GameUser;

public class UserUtil {

	static ThreadLocal<GameUser> users = new ThreadLocal<GameUser>();
	
	public static GameUser get(){
		return users.get();
	}
	public static void set(GameUser gameUser){
		users.set(gameUser);
	}
	public static void remove(){
		users.remove();
	}
}
