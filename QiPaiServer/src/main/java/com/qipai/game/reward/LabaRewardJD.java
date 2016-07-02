package com.qipai.game.reward;

import java.util.ArrayList;
import java.util.List;

import com.qipai.common.QipaiCache;
import com.qipai.common.game.comp.GameConf;
import com.qipai.common.game.comp.LabaCard;
import com.qipai.common.game.comp.LabaPos;
import com.qipai.common.model.Reward;

public class LabaRewardJD implements IReward{

	private int curLine;
	private int winTimes;
	private int freeTimes;
	private List<Integer> rwdIds = new ArrayList<Integer>();
	
	public LabaRewardJD(LabaCard[][] cards, int lineSize) {
		doReward(cards, lineSize);
	}

	private void doReward(LabaCard[][] cards,int ls){
		checkBar(cards);
		LabaPos[][] labaLines = GameConf.labaLines;
		for(int i=0;i<ls;i++){
			LabaCard[] cds = new LabaCard[labaLines[i].length];
			for(int j=0;j<labaLines[i].length;j++){
				cds[j] = cards[labaLines[i][j].getX()][labaLines[i][j].getY()];
			}
			curLine = i+1;
			checkReward(cds);
		}
	}
	
	private void checkBar(LabaCard[][] cards) {
		int count = 0;
		for(LabaCard[] cars:cards){
			for(LabaCard card:cars){
				if(card == LabaCard.Bar){
					count++;
				}
			}
		}
		getReward(count>5?5:count, LabaCard.Bar);
	}

	private void checkReward(LabaCard[] cds) {
		if(cds[0] == LabaCard.Any)return;
		LabaCard curC = cds[0];
		int sameNum = 1;
		int anyNum = 0;
		boolean lastBar = false;
		List<Integer> result = new ArrayList<Integer>();
		for(int i=1;i<cds.length;i++){
			if(cds[i] == LabaCard.Any){
				anyNum++;
				if(lastBar){
					if(sameNum > 1){
						getReward(sameNum, curC);
					}
					break;
				}
			}else if(cds[i] == curC){
				sameNum++;
			}else{
				//不同结算
				if(curC != LabaCard.Bar){
					sameNum += anyNum;
				}
				if(sameNum > 1){
					getReward(sameNum, curC);
				}
				break;
			}
			if(cds[i] == LabaCard.Bar){
				lastBar = true;
				anyNum = 0;
			}else{
				lastBar = false;
			}
			// 收尾结算
			if(i == cds.length-1){
				if(curC != LabaCard.Bar){
					sameNum += anyNum;
				}
				if(sameNum > 1){
					getReward(sameNum, curC);
				}
			}
		}
	}
	
	private void getReward(int num,LabaCard lc){
		if(lc != null){
			if(curLine>0 && lc == LabaCard.Bar)return;
			int rwd = 100+10*lc.getId()+num;
//			System.out.println(rwd+":"+curLine);
			Reward reward = QipaiCache.getReward(rwd);
			if(reward != null){
				rwdIds.add(rwd);
				if(lc == LabaCard.Bar){
					freeTimes += reward.getTimes();
				}else{
					winTimes += reward.getTimes();
				}
			}
		}
	}
	
	public int getWinTimes(){
		return winTimes;
	}
	
	public int getFreeTimes(){
		return freeTimes;
	}
	
	public List<Integer> getRwdIds(){
		return rwdIds;
	}
	
}
