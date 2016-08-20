package com.qipai.game.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;

import com.andy.job.AbsTimerTask;
import com.andy.job.PassiveTimer;
import com.qipai.common.GlobalVar;
import com.qipai.common.QipaiCache;
import com.qipai.common.model.Game;
import com.qipai.common.model.Tax;

public class GameInfo implements Serializable{
	
	private Game gameDb;
	private List<GameUser> players;
	private PassiveTimer rankTimer;
	private PassiveTimer gameTimer;
	private Tax tax;
	
	public GameInfo(Game gameDb) {
		this.gameDb = gameDb;
		tax = QipaiCache.getTax(gameDb.getId());
		players = new ArrayList<GameUser>();
		
		rankTimer = new PassiveTimer(new AbsTimerTask() {
			@Override
			public void doRun() {
				refreshRank();
			}
		}, 30*1000);
		gameTimer = new PassiveTimer(new AbsTimerTask() {
			@Override
			public void doRun() {
				resetGame();
			}
		}, 10*60*1000);
	}
	
	private void resetGame() {
		synchronized (players) {
			for(GameUser user:players){
				user.onGameRefresh();
			}
		}
	}

	public Game getGameDb() {
		return gameDb;
	}
	public void setGameDb(Game gameDb) {
		this.gameDb = gameDb;
	}
	public int getTotleCoin() {
		if(tax != null){
			tax.getTaxCoin();
		}
		return 0;
	}
	public void addPlayer(GameUser user) {
		synchronized (players) {
			if(!players.contains(user)){
				players.add(user);
			}
		}
	}
	public void removePlayer(GameUser user){
		synchronized (players) {
			players.remove(user);
		}
	}
	public int getPlayerNum(){
		return players.size();
	}
	public int getId(){
		return gameDb.getId();
	}
	
	public void refreshRank(){
		synchronized (players) {
			for(GameUser u:players){
				u.getUserGameInfo().refreshTotalWin();
			}
			Collections.sort(players,new Comparator<GameUser>() {
				@Override
				public int compare(GameUser user1, GameUser user2) {
					if(user1 == null || user2 == null)return 0;
					return user2.getUserGameInfo().getTotalWinRank() - user1.getUserGameInfo().getTotalWinRank();
				}
			});
		}
	}
	
	/**
	 * 获取排行附近的玩家信息
	 * @param num 个数
	 * @return
	 */
	public String getRankRange(GameUser user,int num){
//		gameTimer.check();
		rankTimer.check();
		int index = players.indexOf(user);
		int start = index-(num-1)/2;
		if(start < 0){
			start = 0;
		}
		int end = start+num;
		if(players.size() < end){
			end = players.size();
		}
		List<String> result = new ArrayList<String>();
		int rank = start+1;
		for(GameUser u:players.subList(start, end)){
			result.add(rank+","+u.getUserBO().getNickName()+","+u.getUserGameInfo().getTotalWinRank());
			rank++;
		};
		return StringUtils.join(result, "|"); 
	}
	
}
