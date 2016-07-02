package com.qipai.game.model;

import java.io.Serializable;

import com.qipai.common.game.comp.Card;
import com.qipai.common.game.comp.Deck;
import com.qipai.common.game.comp.Card.CardFace;
import com.qipai.common.model.User;
import com.qipai.game.action.GameAction;
import com.qipai.util.DbBatch;
import com.qipai.util.QPC;
import com.qipai.util.RewardUtil;
import com.qipai.util.QPC.GameState;

public class BaseUserGameInfo implements Serializable{

	protected User user;
	protected int doubTimes = QPC.fanpai_doub_times;
	protected int doubCoin;
	protected int totalWinCoin;
	protected int bet;//下注
	protected int winCoin;
	protected Deck deck;
	protected GameState gameState = GameState.Start;//进入后-start,获奖后-doub
	
	public BaseUserGameInfo(User user) {
		this.user = user;
	}
	
	public void addCoin(int coin){
		user.setCoin(user.getCoin()+coin);
		DbBatch.up(user);
	}
	
	public void quitGame() {
		this.user = null;
		this.totalWinCoin = 0;
		forceQuit();
	}
	
	public int getTotalWinCoin(){
		return totalWinCoin;
	}
	
	public int getBet(){
		return bet;
	}
	
	public int getWinCoin(){
		return this.winCoin;
	}
	
	public int getDoubCoin(){
		return doubCoin;
	}
	
	public int doub(Integer hh) {
		if(doubTimes == QPC.fanpai_doub_times){
			// 第一次先扣金币
			addCoin(-winCoin);
		}
		if(gameState == GameState.Doub && doubTimes-- > 0){
			Card card = deck.next();
			if(card.getCf().getNum() == hh){
				doubCoin *= 4;
			}else if(hh == 5 && (card.getCf()==CardFace.diamond || card.getCf()==CardFace.heart)){
				doubCoin *= 2;
			}else if(hh == 6 && (card.getCf()==CardFace.club || card.getCf()==CardFace.spade)){
				doubCoin *= 2;
			}else{
				gameState = GameState.Start;
			}
			return card.getId();
		}
		if(doubTimes == 0){
			// 最后一次结算
			doubCoin = RewardUtil.payTax(doubCoin);
			winCoin(doubCoin);
			GameAction.recordDoub(this);
			gameState = GameState.Start;
		}
		return QPC.INVALID;
	}
	
	public int quitdoub(){
		// 结算翻倍金币
		if(gameState == GameState.Doub){
			doubCoin = RewardUtil.payTax(doubCoin);
			winCoin(doubCoin);
			GameAction.recordDoub(this);
			gameState = GameState.Start;
			return doubCoin;
		}
		return QPC.INVALID;
	}
	
	/**
	 * 强制退出
	 */
	public void forceQuit(){
		
	}
	
	protected void winCoin(int winCoin) {
		if(winCoin > 0){
			this.totalWinCoin += winCoin;
		}
		addCoin(winCoin);
	}

	public void reset(){
		deck = new Deck();
		doubCoin = 0;
		doubTimes = QPC.fanpai_doub_times;
		totalWinCoin = 0;
		winCoin = 0;
	}
	
}
