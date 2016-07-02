package com.qipai.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpSession;

import com.qipai.common.game.comp.RespMap;
import com.qipai.common.model.Rank;
import com.qipai.common.model.Tax;
import com.qipai.game.model.GameInfo;
import com.qipai.game.model.GameUser;
import com.qipai.util.QPC;

/**
 * 全局变量
 * @author Administrator
 *
 */
public class GlobalVar {
	
	// userId:GameUser
	private static Map<Integer, GameUser> users = new ConcurrentHashMap<Integer, GameUser>();
	// userid:session
	private static Map<Integer, HttpSession> sessions = new ConcurrentHashMap<Integer, HttpSession>();
	// gameId:gameInfo
	private static Map<Integer, GameInfo> games = new ConcurrentHashMap<Integer, GameInfo>();
	
	private static Map<Integer,Rank> ranks = null;
	
	public static void addUser(GameUser gameUser){
		users.put(gameUser.getId(),gameUser);
	}
	public static GameUser getUser(int userId){
		return users.get(userId);
	}
	public static void removeUser(int userId){
		users.remove(userId);
	}
	
	public static void addSession(HttpSession session){
		GameUser user = (GameUser) session.getAttribute(QPC.USER_SESSION_KEY);
		if(user != null){
			sessions.put(user.getId(), session);
			addUser(user);
		}
	}
	public static void clearSess(GameUser user){
		if(user != null){
			HttpSession oldSession = sessions.remove(user.getId());
			if(oldSession != null){
				oldSession.invalidate();
			}
		}
	}
	
	public static GameInfo getGameInfo(int gameId){
		GameInfo gameInfo = games.get(gameId);
		return gameInfo;
	}
	public static Collection<Integer> getGameIds(){
		return games.keySet();
	}
	public static Collection<GameInfo> getGames(){
		return games.values();
	}
	
	public static void addGame(GameInfo gameInfo) {
		games.put(gameInfo.getId(), gameInfo);
	}
	
	public static void setRanks(List<Rank> ranks2){
		if(ranks2 != null){
			if(ranks == null){
				ranks = new LinkedHashMap<Integer, Rank>();
			}
			ranks.clear();
			for(Rank rk:ranks2){
				ranks.put(rk.getUserId(), rk);
			}
			respRanks = null;
		}
	}
	public static void clearRanks(){
		ranks = null;
		respRanks = null;
	}
	
	public static Map<Integer,Rank> getRanks(){
		if(ranks == null){
			ranks = new LinkedHashMap<Integer, Rank>();
			List<Rank> ranksT = Rank.dao.find("select * from rank order by rank limit 0,50");
			for(Rank rk:ranksT){
				ranks.put(rk.getUserId(), rk);
			}
		}
		return ranks;
	}
	private static List<RespMap<String, Object>> respRanks;
	public static List<RespMap<String, Object>> getRespRanks(){
		if(respRanks == null){
			respRanks = new ArrayList<RespMap<String,Object>>();
			Map<Integer, Rank> ranks = getRanks();
			for(Rank rk:ranks.values()){
				respRanks.add(new RespMap<String, Object>().set("nickName",rk.getUserName()).set("money", rk.getCoin()));
			}
		}
		return respRanks;
	}
	
}
