package com.qipai.util;

import com.qipai.common.GlobalVar;
import com.qipai.common.QipaiCache;
import com.qipai.common.model.Reward;
import com.qipai.common.model.Tax;
import com.qipai.game.model.GameUser;

public class RewardUtil {
	
	public static int getRewardMoney(int rewardId){
		Reward reward = QipaiCache.getReward(rewardId);
		if(reward != null && reward.getMoney() != null){
			return reward.getMoney().intValue();
		}
		return 0;
		
	}
	
	public static int getRewardMoney(int rewardId,int basMoney){
		Reward reward = QipaiCache.getReward(rewardId);
		if(reward != null && reward.getTimes() != null){
			return reward.getTimes()*basMoney;
		}
		return 0;
	}

	public static int payTax(int winCoin) {
		if(winCoin > 0){
			GameUser gameUser = UserUtil.get();
			Tax tax = QipaiCache.getTax(gameUser.getGame().getId());
			int taxCoin = (int) Math.ceil(tax.getTax()*winCoin*1.0/100);
			tax.addTax(taxCoin);
			tax.addTaxStock(-winCoin);
			
			return winCoin-taxCoin;
		}
		return winCoin;
	}
	
}
