package com.qipai.common.game.comp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.andy.util.RandomUtils;

public class Axis {

	private List<LabaCard> labaCards = new ArrayList<LabaCard>();
	
	private List<CardPercent> cardPercents = new ArrayList<CardPercent>();
	
	public void setCardPercents(List<CardPercent> cardPercents){
		this.cardPercents = cardPercents;
	}
	
	public List<LabaCard> getLabaCards(){
		return labaCards;
	}
	
	public List<LabaCard> rotate(int showSize){
//		Collections.rotate(labaCards, new Random().nextInt());
		labaCards.clear();
		for(int i=0;i<showSize;i++){
			labaCards.add(selectOne());
		}
		
		return labaCards;
	}
	
	private LabaCard selectOne() {
		int interval = new Random().nextInt(100);//0-99
		for(CardPercent cp:cardPercents){
			if(cp.rwdIntervalLower <= interval && cp.rwdIntervalUpper > interval){
				return cp.card;
			}
		}
		return RandomUtils.getRandomList(cardPercents).card;
	}

	public static class CardPercent{
		private int rwdIntervalLower;
		private int rwdIntervalUpper;
		private LabaCard card;
		
		public CardPercent(int lower,int upper,LabaCard card) {
			this.rwdIntervalLower = lower;
			this.rwdIntervalUpper = upper;
			this.card = card;
		}
		
	}
	
	
}
