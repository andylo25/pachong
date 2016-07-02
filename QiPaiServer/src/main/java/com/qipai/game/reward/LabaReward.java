package com.qipai.game.reward;

import java.util.ArrayList;
import java.util.List;

import com.qipai.common.QipaiCache;
import com.qipai.common.game.comp.GameConf;
import com.qipai.common.game.comp.LabaCard;
import com.qipai.common.game.comp.LabaPos;
import com.qipai.common.model.Reward;

public class LabaReward implements IReward{

	private int curLine;
	private int winTimes;
	private int freeTimes;
	private List<Integer> rwdIds = new ArrayList<Integer>();
	
	public LabaReward(LabaCard[][] cards, int lineSize) {
		getReward(cards, lineSize);
	}

	private void getReward(LabaCard[][] cards,int ls){
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
	
	private void checkReward(LabaCard[] cds) {
		LabaCard curC = null;
		int sameNum = 1;
		int anyNum = 0;
		int lastTNum = 0;
		boolean lastBar = false;
		List<Integer> result = new ArrayList<Integer>();
		for(int i=0;i<cds.length;i++){
			if(cds[i] == LabaCard.Any){
				anyNum++;
				lastTNum++;
				if(lastBar){
					if(sameNum > 1){
						getReward(sameNum, curC);
					}
					curC = null;
					sameNum = 1;
				}
			}else if(null == curC){
				//第一次非Any元素
				curC = cds[i];
				lastTNum = 0;
			}else if(cds[i] == curC){
				sameNum++;
				lastTNum = 0;
			}else{
				//不同结算
				if(curC != LabaCard.Bar){
					sameNum += anyNum;
				}
				if(sameNum > 1){
					getReward(sameNum, curC);
				}
				curC = cds[i];
				if(cds[i] != LabaCard.Bar){
					sameNum = lastTNum+1;
				}else{
					sameNum = 1;
				}
				anyNum = 0;
				lastTNum = 0;
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
