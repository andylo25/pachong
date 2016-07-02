package com.qipai.game.reward;

import java.util.Arrays;
import java.util.Map;

import com.qipai.common.game.comp.Card;

/**
 * @author andy
 *
 */
public class ThsRewardBak {

	Card[] cards = null; //原始
	Card[] cardp = null; // 普通牌
//	Card[] cardSize = null; //相同牌个数
	
	int gSize = 0;//王的个数 
	int flag = 0; //牌型值
	
	public ThsRewardBak(Card[] cards) {
		this.cards = cards;
		init();
	}
	
	private void init() {
		Arrays.sort(cards);
		int maxi = cards.length-1;
		if(cards[maxi-1] == Card.BlackJoker){
			gSize = 2;
			cardp = Arrays.copyOfRange(cards, 0, maxi-1);
		}else if(cards[maxi] == Card.BlackJoker || cards[maxi] == Card.RedJoker){
			cardp = Arrays.copyOfRange(cards, 0, maxi);
			gSize = 1;
		}else{
			cardp = cards;
			gSize = 0;
		}
		
		for(int i=0;i<cardp.length;i++){
			if(cardp[i] == Card.BlackJoker || cardp[i] == Card.RedJoker){
				gSize++;
			}else{
				
				for(int j=i+1;j<cardp.length;j++){
					if(cardp[i].getNum() == cardp[j].getNum()){
						flag++;
					}
				}
			}
		}
		
		
	}

	/**
	 * 5同
	 * @param cards
	 * @return
	 */
	private boolean checkSL(){
		if(gSize == 1){
			if(cards[0].getNum() == cards[1].getNum() && cards[0].getNum() == cards[2].getNum() && cards[0].getNum() == cards[3].getNum()){
				return true;
			}
		}else if(gSize == 2){
			if(cards[0].getNum() == cards[1].getNum() && cards[0].getNum() == cards[2].getNum()){
				return true;
			}
		}
		return false;
	}
	
	// 皇家同花顺
	private boolean checkFJ(){
		if(checkTHS()){
			if(cardp[0].getNum() == 10){
				return true;
			}
		}
		return false;
	}
	
	// 同花顺
	private boolean checkTHS(){
		return checkTH() && checkSZ();
	}
	
	// 4同
	private boolean checkTZ(){
		int gs = gSize;
		int inc = 0;
		boolean flag = true;
		out:for(int i=1;i<cardp.length;i++){
			if(cardp[0].getNum() == cardp[i].getNum()-(gSize-gs))continue;
			while(gs-- > 0){
				if(cardp[0].getNum() == cardp[i].getNum()-i-(gSize-gs))continue out;
			}
			flag = false;
			break;
		}
		return flag;
	}
	
	// 3D2
	private boolean checkHL(){
		
		return false;
	}
	
	// 同花
	private boolean checkTH(){
		for(int i=1;i<cardp.length;i++){
			if(cardp[0].getCf() != cardp[i].getCf()){
				return false;
			}
		}
		return true;
	}
	
	// 顺子
	private boolean checkSZ(){
		int gs = gSize;
		int inc = 0;
		boolean flag = true;
		out:for(int i=1;i<cardp.length;i++){
			if(cardp[0].getNum() == cardp[i].getNum()-i-(gSize-gs))continue;
			while(gs-- > 0){
				if(cardp[0].getNum() == cardp[i].getNum()-i-(gSize-gs))continue out;
			}
			flag = false;
			break;
		}
		return flag;
	}
	
	// 3同
	private boolean checkST(){
		
		return false;
	}
	
	// 两对
	private boolean checkLD(){
		
		return false;
	}
	
	// 对8
	private boolean checkDZ(){
		
		return false;
	}
	
	public int getReward(){
		int result = 0;
		if(checkSL()){
			result = 10;
		}else if(checkFJ()){
			result = 9;
		}else if(checkTHS()){
			result = 8;
		}else if(checkTZ()){
			result = 7;
		}else if(checkHL()){
			result = 6;
		}else if(checkTH()){
			result = 5;
		}else if(checkSZ()){
			result = 4;
		}else if(checkST()){
			result = 3;
		}else if(checkLD()){
			result = 2;
		}else if(checkDZ()){
			result = 1;
		}
		return result;
	}
	
	public static void main(String[] args) {
		ThsRewardBak reward = new ThsRewardBak(new Card[]{Card.AceC,Card.TenC,Card.JackC,Card.QueenC,Card.BlackJoker});
		System.out.println(reward.getReward());
	}
	
}
