package com.qipai.common.game.comp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.qipai.common.QipaiCache;
import com.qipai.common.game.comp.Axis.CardPercent;

public class LabaMachine {
	private List<Axis> axises = new ArrayList<Axis>();
	private int showSize = 0;
	public LabaMachine() {
		this(4);
	}
	public LabaMachine(int showSize) {
		this.showSize = showSize;
		for(int i=1;i<6;i++){
			String[] perItems = QipaiCache.getConfVal("laba_card_percent"+i).trim().split("\\|");
			int first = 0;
			Axis axis = new Axis();
			List<CardPercent> cardPercents = new ArrayList<Axis.CardPercent>();
			for(String pi:perItems){
				String[] cp = pi.split("=");
				int cardId = Integer.parseInt(cp[0].trim());
				int percent = first+Integer.parseInt(cp[1].trim());
				cardPercents.add(new CardPercent(first,percent,LabaCard.getCardById(cardId)));
				first = percent;
			}
			axis.setCardPercents(cardPercents );
			axises.add(axis );
		}
		
	}
	
	public int getAxiseNum(){
		return axises.size();
	}
	
	public LabaCard[][] go(){
		LabaCard[][] result = new LabaCard[axises.size()][showSize];
		for(int i=0;i<axises.size();i++){
			result[i] = axises.get(i).rotate(showSize).toArray(new LabaCard[0]);
		}
		return result;
	}
	
}
