package com.qipai.game.model;

import java.io.Serializable;

import com.qipai.common.GlobalVar;
import com.qipai.common.model.Game;
import com.qipai.common.model.User;
import com.qipai.util.DbBatch;
import com.qipai.util.QPC;

public class GameUser implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private User userBO;
	private GameInfo game;
	private BaseUserGameInfo userGameInfo;
	private boolean haveCharge = false;
	
	public GameUser(User user) {
		this.userBO = user;
	}
	
	public User getUserBO() {
		return userBO;
	}
	public void setUserBO(User userBO) {
		this.userBO = userBO;
	}
	
	public GameInfo getGame() {
		return game;
	}

	public boolean setGame(GameInfo game) {
		this.game = game;
		return initGameInfo();
	}

	private boolean initGameInfo() {
		if(game != null){
			switch (game.getId()) {
			case QPC.GAME_100:
				userGameInfo = new BaidaUserGameInfo(userBO);
				break;
			case QPC.GAME_101:
				userGameInfo = new LabaUserGameInfo(userBO);
				break;
			default:
				break;
			}
			return true;
		}
		return false;
	}

	public BaseUserGameInfo getUserGameInfo() {
		return userGameInfo;
	}
	public BaidaUserGameInfo getBaidaUserGameInfo() {
		return (BaidaUserGameInfo) userGameInfo;
	}
	public LabaUserGameInfo getLabaUserGameInfo() {
		return (LabaUserGameInfo) userGameInfo;
	}

	public int getId(){
		return userBO.getId();
	}

	public boolean quitGame() {
		if(game != null){
			game.removePlayer(this);
			if(userGameInfo != null){
				userGameInfo.quitGame();
				userGameInfo = null;
			}
			game = null;
		}
		return true;
	}

	public boolean havRight(GameInfo game) {
		return true;
	}

	public boolean checkMoney(Integer money) {
		if(userBO.getCoin() >= money){
			addMoney(money);
			return true;
		}
		return false;
	}
	
	public void charge(){
		haveCharge = true;
	}
	public int getCharge(){
		if(haveCharge){
			return userBO.getCoin();
		}
		return 0;
	}

	public int addMoney(int money) {
		userBO.setCoin(userBO.getCoin()-money);
		DbBatch.up(userBO);
		return userBO.getCoin();
	}

	public void onGameRefresh() {
		userGameInfo.totalWinCoin = 0;
	}
	
	
}
