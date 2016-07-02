package com.qipai.game.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

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

	public BaidaUserGameInfo(User user) {
		super(user);
	}
	
	public Card[] getCards() {
		return cards;
	}

	public Card[] open(Integer money) {
		reset();
		if(gameState == GameState.Start || gameState == GameState.Doub){
			cards = deck.next(QPC.FANPAI_CARD_NUM);
			bet = money;
//			addCoin(bet);//在contr里面已经扣钱了
			gameState = GameState.Deal;
		}
		return cards;
	}

	public int[] change(String[] keeps) {
		int[] result = new int[2];
		if(gameState == GameState.Deal){
			for(int i=0;i<cards.length;i++){
				if(ArrayUtils.contains(keeps, String.valueOf(i)))continue;
				cards[i] = deck.next();
			}
			ThsReward reward = new ThsReward(cards);
			int winTimes = reward.getWinTimes();
			if(logger.isInfoEnabled()){
				logger.info("开牌："+Arrays.toString(cards));
			}
			if(winTimes > 0){
				winCoin = winTimes*bet;
				winCoin = RewardUtil.payTax(winCoin);
				doubCoin = winCoin;
				winCoin(winCoin);
				gameState = GameState.Doub;
				result[0] = winCoin;
				result[1] = reward.getRwdIds().get(0);
			}else{
				gameState = GameState.Start;
			}
			GameAction.recordWin(this);
		}else{
			result[0] = QPC.INVALID;
		}
		return result;
	}
	
	public void reset(){
		super.reset();
		cards = null;
	}
	
}
