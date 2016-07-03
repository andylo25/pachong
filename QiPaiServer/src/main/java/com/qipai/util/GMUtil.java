package com.qipai.util;

import com.qipai.common.GlobalVar;
import com.qipai.common.model.User;
import com.qipai.game.model.GameUser;

public class GMUtil {

	public static String doAm(int userId, int coin){
		User user = null;
		GameUser gameUser = GlobalVar.getUser(userId);
		if(gameUser == null){
			user = User.dao.findById(userId);
		}else{
			user = gameUser.getUserBO();
			gameUser.charge();
		}
		user.setCoin(user.getCoin()+coin);
		user.update();
		return "SUCCESS";
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
