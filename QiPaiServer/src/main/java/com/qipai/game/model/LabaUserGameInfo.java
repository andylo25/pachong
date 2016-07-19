package com.qipai.game.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.andy.util.RandomUtils;
import com.qipai.common.game.comp.Card;
import com.qipai.common.game.comp.Deck;
import com.qipai.common.game.comp.GameConf;
import com.qipai.common.game.comp.LabaCard;
import com.qipai.common.game.comp.LabaMachine;
import com.qipai.common.game.comp.Card.CardFace;
import com.qipai.common.model.User;
import com.qipai.game.action.GameAction;
import com.qipai.game.reward.IReward;
import com.qipai.game.reward.LabaReward;
import com.qipai.game.reward.LabaRewardJD;
import com.qipai.util.QPC;
import com.qipai.util.RewardUtil;
import com.qipai.util.UserUtil;
import com.qipai.util.QPC.GameState;

public class LabaUserGameInfo extends BaseUserGameInfo{

	private static final long serialVersionUID = 1L;
	
	private LabaCard[][] cards;
	private LabaMachine machine;
	private LabaMachine machine_f;
	private List<Integer> rwds = new ArrayList<Integer>();
	private int lineSize;//下注线数
	private int freeTimes;//免费次数

	public LabaUserGameInfo(User user) {
		super(user);
		machine = new LabaMachine();
		machine_f = new LabaMachine(true);
	}
	
	public int pull(int coin,int lineSize) {
		reset();
		if(GameConf.labaLines.length < lineSize){
			lineSize = GameConf.labaLines.length;
		}
		if(freeTimes-- >0 || UserUtil.get().checkMoney(coin*lineSize)){
			this.bet = coin*lineSize;
			this.lineSize = lineSize;
			LabaMachine machineT = machine;
			if(freeTimes >= 0)machineT  = machine_f;
			if(RandomUtils.countMyria(QPC.ALL_PERCENT)){
				cards = machineT.goSame();
			}else{
				cards = machineT.go();
			}
			IReward labaReward = new LabaRewardJD(cards,lineSize);
			int winTimes = labaReward.getWinTimes();
			if(freeTimes >= 0)winTimes *= 2;
			if(freeTimes < 0)freeTimes = 0;
			if(winTimes > 0){
				gameState = GameState.Doub;
				winCoin = RewardUtil.payTax(winTimes*coin);
				doubCoin = winCoin;
				winCoin(winCoin);
				if(winCoin > QPC.PRIZE_NOTICE_MIN){
					PopupMsg.instanceGG().addMsg(user, String.valueOf(winCoin));
				}
				GameAction.recordWin(this);
			}
			freeTimes += labaReward.getFreeTimes();
			
			rwds = labaReward.getRwdIds();
			return QPC.ECD_0;
		}
		return QPC.ECD_120;
	}
	
	public int getFreeTimes(){
		return freeTimes;
	}
	
	public LabaCard[][] getCards(){
		return cards;
	}
	
	public List<Integer> getRwds(){
		return rwds;
	}
	
	public void reset(){
		super.reset();
		cards = null;
		rwds = new ArrayList<Integer>();
		lineSize = 0;
	}

}
