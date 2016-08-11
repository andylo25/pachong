package com.qipai.game.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.andy.util.RandomUtils;
import com.qipai.common.game.comp.Card;
import com.qipai.common.game.comp.Deck;
import com.qipai.common.game.comp.Card.CardFace;
import com.qipai.common.interceptor.AuthInterceptor;
import com.qipai.common.model.User;
import com.qipai.game.action.GameAction;
import com.qipai.game.reward.ThsReward;
import com.qipai.util.QPC;
import com.qipai.util.QPC.GameState;
import com.qipai.util.RewardUtil;

public class BaidaUserGameInfo extends BaseUserGameInfo{

	private Logger logger = Logger.getLogger(BaidaUserGameInfo.class);
	private static final long serialVersionUID = 1L;
	
	private Card[] cards;
	private int rwd;

	public BaidaUserGameInfo(User user) {
		super(user);
	}
	
	public Card[] getCards() {
		return cards;
	}
	public int getRwd(){
		return rwd;
	}

	public Card[] open(Integer money) {
		reset();
		if(gameState == GameState.Start || gameState == GameState.Doub){
			cards = deck.nextNoJoker(QPC.FANPAI_CARD_NUM);
			bet = money;
//			addCoin(bet);//在contr里面已经扣钱了
			gameState = GameState.Deal;
		}
		return cards;
	}

	public int change(String[] keeps) {
		if(gameState == GameState.Deal){
			ThsReward reward = buildCard(keeps);
			int winTimes = reward.getWinTimes();
			int acount = 0;
			while (havAgain(winTimes) && acount < 5) {
				reward = buildCard(keeps);
				winTimes = reward.getWinTimes();
				acount++;
			}
			if(logger.isInfoEnabled()){
				logger.info("开牌："+Arrays.toString(cards));
			}
			if(winTimes > 0){
				winCoin = winTimes*bet;
				winCoin = RewardUtil.payTax(winCoin);
				doubCoin = winCoin;
				winCoin(winCoin);
				if(winCoin > QPC.PRIZE_NOTICE_MIN){
					PopupMsg.instanceGG().addMsg(user, String.valueOf(winCoin));
				}
				gameState = GameState.Doub;
				rwd = reward.getRwdIds().get(0);
			}else{
				gameState = GameState.Start;
			}
			GameAction.recordWin(this);
		}else{
			return QPC.INVALID;
		}
		return QPC.ECD_0;
	}
	
	private ThsReward buildCard(String[] keeps){
		for(int i=0;i<cards.length;i++){
			if(ArrayUtils.contains(keeps, String.valueOf(i)))continue;
			cards[i] = deck.nextNoJoker();
		}
		
		return new ThsReward(cards);
	}
	
	private boolean havAgain(int winTimes){
		if(winTimes == QPC.BINGO_1){
			if(!RandomUtils.countPercent(QPC.FIVE_PERCENT)){
				return true;
			}
		}else if(winTimes == QPC.BINGO_2){
			if(!RandomUtils.countPercent(QPC.HJTH_PERCENT)){
				return true;
			}
		}else if(winTimes == QPC.BINGO_3){
			if(!RandomUtils.countPercent(QPC.TH_PERCENT)){
				return true;
			}
		}else if(winTimes == QPC.BINGO_7){
			if(!RandomUtils.countPercent(QPC.SZ_PERCENT)){
				return true;
			}
		}else if(winTimes == QPC.BINGO_4){
			if(!RandomUtils.countPercent(QPC.SZH_PERCENT)){
				return true;
			}
		}else if(winTimes == QPC.BINGO_9){
			if(!RandomUtils.countPercent(QPC.LD_PERCENT)){
				return true;
			}
		}
		return false;
	}
	
	public void reset(){
		super.reset();
		cards = null;
		rwd = 0;
	}
	
}
