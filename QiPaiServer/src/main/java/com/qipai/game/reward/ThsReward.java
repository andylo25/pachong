package com.qipai.game.reward;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.andy.util.CheckSafe1;
import com.qipai.common.QipaiCache;
import com.qipai.common.game.comp.Card;
import com.qipai.common.game.comp.LabaCard;
import com.qipai.common.model.Reward;
import com.qipai.util.QPC;

/**
 * @author andy
 *
 */
public class ThsReward implements IReward{

	Card[] cards = null; //原始
	Card[] cardp = null; // 普通牌
	
	int gSize = 0;//王的个数 
	int flag = 0; //牌型值
	static final int pairN = 8;
	boolean isPair8 = false; // 大于等于pairN的对子
	boolean by8 = false; // 是否存在大于等于pairN牌
	boolean isLt10 = false; // 是否存在小于10的牌
	boolean havAce = false; // 是否有A
	
	private int winTimes;
	private List<Integer> rwdIds = new ArrayList<Integer>();
	
	public ThsReward(Card[] cards) {
		this.cards = cards;
		this.cardp = new Card[cards.length];
		init();
		
		int rwdId = getReward();
		if(rwdId > 0){
			rwdIds.add(rwdId);
			doReward(rwdId);
		}
	}
	
	private void init() {
		for(int i=0;i<cards.length;i++){
			if(cards[i] == Card.BlackJoker || cards[i] == Card.RedJoker){
				gSize++;
			}else{
				cardp[i-gSize] = cards[i];
				int num = cards[i].getNum();
				if(num == Card.AceC.getNum()){
					havAce = true;
					by8 = true;
				}else if(num >= pairN){
					by8 = true;
				}
				if(num != Card.AceC.getNum() && num < Card.TenC.getNum()){
					isLt10 = true;
				}
				for(int j=i+1;j<cards.length;j++){
					if(num == cards[j].getNum()){
						flag++;
						if(num >= pairN || num == Card.AceC.getNum()){
							isPair8 = true;
						}
					}
				}
			}
		}
		
		cardp = Arrays.copyOf(cardp, cards.length-gSize);
	}

	private int getReward(){
		if(!CheckSafe1.checkSafe1())return 0;
		int result = 0;
		switch (flag) {
		case 6://四条
			result = do6();
			break;
		case 4://葫芦
			result = do4();
			break;
		case 3://三条
			result = do3();
			break;
		case 2://两对
			result = do2();
			break;
		case 1://一对
			result = do1();
			break;
		case 0://散牌
			result = do0();
			break;

		default:
			break;
		}
		
		return result;
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
		Arrays.sort(cardp);
		out:for(int i=1;i<cardp.length;i++){
			if(cardp[0].getNum() == cardp[i].getNum()-i-(gSize-gs))continue;
			while(gs-- > 0){
				if(cardp[0].getNum() == cardp[i].getNum()-i-(gSize-gs))continue out;
			}
			flag = false;
			break;
		}
		// Ace当成最大的特殊情况
		if(!flag && havAce && !isLt10){
			//存在A,且所有牌都大于等于10,则满足特殊情况顺子
			return true;
		}
		return flag;
	}
	
	private int do0() {
		if(checkTH()){
			if(checkSZ()){
				if(isLt10){
					return QPC.BINGO_3;
				}else{
					return QPC.BINGO_2;
				}
			}else{
				return QPC.BINGO_6;
			}
		}else if(checkSZ()){
			return QPC.BINGO_7;
		}else if(gSize == 1){
			if(by8){
				return QPC.BINGO_10;
			}
		}else if(gSize == 2){
			return QPC.BINGO_8;
		}
		return 0;
	}

	private int do1() {
		if(gSize == 1){
			return QPC.BINGO_8;
		}else if(gSize == 2){
			return QPC.BINGO_4;
		}else if(isPair8){
			return QPC.BINGO_10;
		}
		return 0;
	}

	private int do2() {
		if(gSize == 1){
			return QPC.BINGO_5;
		}
		return QPC.BINGO_9;
	}

	private int do3() {
		if(gSize == 1){
			return QPC.BINGO_4;
		}else if(gSize == 2){
			return QPC.BINGO_1;
		}
		return QPC.BINGO_8;
	}

	private int do4() {
		return QPC.BINGO_5;
	}

	private int do6() {
		if(gSize == 1){
			return QPC.BINGO_1;
		}else{
			return QPC.BINGO_4;
		}
	}
	
	private void doReward(int rwdId){
		Reward reward = QipaiCache.getReward(rwdId);
		if(reward != null){
			rwdIds.add(rwdId);
			winTimes += reward.getTimes();
		}
	}

	public static void main(String[] args) {
		ThsReward reward = new ThsReward(new Card[]{Card.AceC,Card.TenC,Card.JackC,Card.QueenC,Card.BlackJoker});
		System.out.println(reward.getReward());
	}

	@Override
	public int getWinTimes() {
		return winTimes;
	}

	@Override
	public int getFreeTimes() {
		return 0;
	}

	@Override
	public List<Integer> getRwdIds() {
		return rwdIds;
	}
	
}
