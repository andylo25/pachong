package com.qipai.util;

import com.qipai.common.GlobalVar;
import com.qipai.common.model.User;
import com.qipai.game.model.GameUser;

public class GMUtil {

	public static String doAm(String userId, String money){
		GameUser gameUser = getUser(Integer.valueOf(userId));
		if(gameUser != null){
			return String.valueOf(gameUser.addMoney(Integer.parseInt(money)));
		}
		return "";
	}
	
	public static GameUser getUser(int userId){
		GameUser gameUser = GlobalVar.getUser(userId);
		if(gameUser == null){
			User user = User.dao.findById(userId);
			gameUser = new GameUser(user);
		}
		return gameUser;
	}
}
