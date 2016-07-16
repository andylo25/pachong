package com.qipai.game.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sun.org.mozilla.javascript.internal.ObjArray;

import com.jfinal.plugin.activerecord.Db;
import com.qipai.common.GlobalVar;
import com.qipai.common.QipaiCache;
import com.qipai.common.model.Game;
import com.qipai.common.model.Rank;
import com.qipai.common.model.Score;
import com.qipai.common.model.User;
import com.qipai.game.model.BaidaUserGameInfo;
import com.qipai.game.model.BaseUserGameInfo;
import com.qipai.game.model.GameInfo;
import com.qipai.game.model.GameUser;
import com.qipai.util.DbBatch;
import com.qipai.util.GM;
import com.qipai.util.GMUtil;
import com.qipai.util.QPC;
import com.qipai.util.UserUtil;

/**
 * 游戏逻辑调用
 * @author Administrator
 *
 */
public class GameAction {

	public static boolean enterGame(int gameId) {
		GameUser user = UserUtil.get();
		GameInfo game = GlobalVar.getGameInfo(gameId);
		if(game != null && user.havRight(game)){
			if(user.setGame(game)){
				game.addPlayer(user);
				return true;
			}
		}
		
		return false;
	}

	public static void logOut(GameUser gameUser) {
		gameUser.quitGame();
		GlobalVar.removeUser(gameUser.getId());
		User user = gameUser.getUserBO();
		user.setLogoutTime(new Date());
		user.setOnline("0");
		DbBatch.up(user);
	}

	public static String doCmd(GM gm, String[] params) {
		String result = "";
		switch (gm) {
		case am:
		case ac:
			result = GMUtil.doAm(Integer.parseInt(params[0]),Integer.parseInt(params[1]));
			
			break;
		case utk:
			
			break;
		default:
			break;
		}
		return result;
	}
	

	public static void recordWin(BaseUserGameInfo baseUserGameInfo) {
		GameUser gameUser = UserUtil.get();
		Score score = new Score();
		score.setGameId(gameUser.getGame().getId());
		score.setGameTime(new Date());
		score.setAwardMoney(baseUserGameInfo.getWinCoin());
		score.setUserId(gameUser.getId());
		score.setPayMoney(baseUserGameInfo.getBet());
		DbBatch.sv(score);
	}
	
	public static void recordDoub(BaseUserGameInfo baseUserGameInfo) {
		GameUser gameUser = UserUtil.get();
		Score score = new Score();
		score.setGameId(gameUser.getGame().getId());
		score.setGameTime(new Date());
		score.setAwardMoney(baseUserGameInfo.getDoubCoin());
		score.setUserId(gameUser.getId());
		score.setPayMoney(baseUserGameInfo.getWinCoin());
		DbBatch.sv(score);
	}
	
	public static void refreshRank(){
		Db.batch("delete from Rank where rank>?", new Object[][]{{0}}, 1);
		List<User> users = User.dao.find("select * from user order by coin desc limit 0,50");
		if(users != null){
			List<Rank> ranks = new ArrayList<Rank>();
			for(int i=0;i<users.size();i++){
				ranks.add(new Rank().set("coin", users.get(i).getCoin())
						.set("rank", i+1)
						.set("user_id", users.get(i).getId())
						.set("user_name", users.get(i).getNickName())
						.set("game_id", QPC.GAME_100));
			}
			Db.batchSave(ranks, 100);
			GlobalVar.clearRanks();
		}
	}

	public static String getTempRank(Integer gameId) {
		GameInfo gameInfo = GlobalVar.getGameInfo(gameId);
		if(gameInfo != null){
			return gameInfo.getRankRange(UserUtil.get(), QPC.temp_rank_num);
		}
		return null;
	}

	public static void refreshUserOffline() {
		Db.update("update user set online=0");
	}

	
}
