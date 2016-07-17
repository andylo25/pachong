package com.qipai.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.andy.util.RandomUtils;
import com.qipai.common.game.comp.GameConf;
import com.qipai.common.game.comp.LabaCard;
import com.qipai.common.game.comp.LabaMachine;
import com.qipai.common.game.comp.LabaPos;
import com.qipai.game.reward.IReward;
import com.qipai.game.reward.LabaReward;
import com.qipai.game.reward.LabaRewardJD;

public class LabaGame {

	private static LabaMachine machine;
	private static List<Integer> rwdIds = new ArrayList<Integer>();
	private static LabaPos[][] labaLines = new LabaPos[50][5];
	private static Map<Integer,Integer> rewards = new HashMap<Integer, Integer>();
	private static int winTimes;
	
	public static LabaCard[][] cards = new LabaCard[5][4];
	static{
		cards[0][0] = LabaCard.Seven;
		cards[0][1] = LabaCard.Ele;
		cards[0][2] = LabaCard.Ten;
		cards[0][3] = LabaCard.Ele;
		cards[1][0] = LabaCard.Six;
		cards[1][1] = LabaCard.Any;
		cards[1][2] = LabaCard.Ten;
		cards[1][3] = LabaCard.Five;
		cards[2][0] = LabaCard.Seven;
		cards[2][1] = LabaCard.Two;
		cards[2][2] = LabaCard.Four;
		cards[2][3] = LabaCard.Five;
		cards[3][0] = LabaCard.Six;
		cards[3][1] = LabaCard.One;
		cards[3][2] = LabaCard.Ele;
		cards[3][3] = LabaCard.Two;
		cards[4][0] = LabaCard.Four;
		cards[4][1] = LabaCard.Three;
		cards[4][2] = LabaCard.Three;
		cards[4][3] = LabaCard.One;
	}
	
	private static int curLine;
	public static void main(String[] args) {
		initLine();
		initReward();
//		test2();
//		test1();
		test3();
	}

	private static void test2() {
		machine = new LabaMachine(4);
		LabaCard[][] cards = machine.go();
		for(LabaCard[] cds:cards){
			System.out.println(Arrays.toString(cds));
		}
		getReward(cards);
		System.out.println("赢取分数:"+winTimes);
		System.out.println(rwdIds);
	}
	
	public static void test1(){
		LabaCard[] cards1 = new LabaCard[]{
				LabaCard.Bar,
				LabaCard.Bar,
				LabaCard.Any,
				LabaCard.Any,
				LabaCard.Any
			};
		System.out.println(Arrays.toString(cards1));
		checkReward(cards1);
//		System.out.println(winTimes);

	}
	
	public static void test3(){
		
		
		LabaCard[][] cards = new LabaCard[5][4];
		cards[0][0] = LabaCard.Seven;
		cards[0][1] = LabaCard.Ele;
		cards[0][2] = LabaCard.Ten;
		cards[0][3] = LabaCard.Ele;
		cards[1][0] = LabaCard.Six;
		cards[1][1] = LabaCard.Any;
		cards[1][2] = LabaCard.Ten;
		cards[1][3] = LabaCard.Five;
		cards[2][0] = LabaCard.Seven;
		cards[2][1] = LabaCard.Two;
		cards[2][2] = LabaCard.Four;
		cards[2][3] = LabaCard.Five;
		cards[3][0] = LabaCard.Six;
		cards[3][1] = LabaCard.One;
		cards[3][2] = LabaCard.Ele;
		cards[3][3] = LabaCard.Two;
		cards[4][0] = LabaCard.Four;
		cards[4][1] = LabaCard.Three;
		cards[4][2] = LabaCard.Three;
		cards[4][3] = LabaCard.One;
		int lineSize = 50;
		cards = toCards("4,5,3,8,2,4,9,4,3,4,3,1,10,9,6,4,2,6,13,2");
		IReward reward = new LabaRewardJD(cards , lineSize);
		reward.getRwdIds();
	}
	
	private static void getReward(LabaCard[][] cards) {
		for(int i=0;i<labaLines.length;i++){
			LabaCard[] cds = new LabaCard[labaLines[i].length];
			for(int j=0;j<labaLines[i].length;j++){
				cds[j] = cards[labaLines[i][j].getX()][labaLines[i][j].getY()];
			}
			curLine = i+1;
			checkReward(cds);
		}
	}

	private static void checkReward(LabaCard[] cds) {
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
	
	public static void getReward(int num,LabaCard lc){
		if(lc == null)return;
		int rwd = 100+10*lc.getId()+num;
		Integer times = rewards.get(rwd);
		if(times != null){
			rwdIds.add(rwd);
			winTimes += times;
//			System.out.println("line ["+curLine +"] bingo rwd->"+rwd+":"+times);
		}
		System.out.println("line ["+curLine +"] bingo num->"+num+":"+lc);
	}

	public static void initLine(){
		labaLines = GameConf.labaLines;
	}
	
	public static void initReward(){
		
		rewards.put(15, 500);
		rewards.put(14, 100);
		rewards.put(13, 40);
		rewards.put(12, 10);
		
		rewards.put(25, 300);
		rewards.put(24, 80);
		rewards.put(23, 40);
		rewards.put(22, 4);
		
		rewards.put(35, 240);
		rewards.put(34, 60);
		rewards.put(33, 30);
		rewards.put(32, 4);
		
		rewards.put(45, 200);
		rewards.put(44, 50);
		rewards.put(43, 20);
		
		rewards.put(55, 150);
		rewards.put(54, 40);
		rewards.put(53, 20);
		
		rewards.put(65, 100);
		rewards.put(64, 30);
		rewards.put(63, 20);
		
		rewards.put(75, 100);
		rewards.put(74, 20);
		rewards.put(73, 10);
		
		rewards.put(85, 80);
		rewards.put(84, 20);
		rewards.put(83, 10);
		
		rewards.put(95, 80);
		rewards.put(94, 20);
		rewards.put(93, 10);
		
		rewards.put(105, 60);
		rewards.put(104, 20);
		rewards.put(103, 10);
		
		rewards.put(115, 60);
		rewards.put(114, 20);
		rewards.put(113, 10);
		
	}
	
	public static LabaCard[][] toCards(String str){
		String[] cardss = str.split(",");
		LabaCard[][] cards = new LabaCard[5][4];
		for(int i=0;i<5;i++){
			for(int j=0;j<4;j++){
				cards[i][j] = LabaCard.getCardById(Integer.parseInt(cardss[i*4+j]));
			}
		}
		return cards;
	}

}
