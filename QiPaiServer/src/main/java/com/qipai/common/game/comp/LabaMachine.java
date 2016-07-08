package com.qipai.common.game.comp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.qipai.common.QipaiCache;
import com.qipai.common.game.comp.Axis.CardPercent;

public class LabaMachine {
	private List<Axis> axises = new ArrayList<Axis>();
	private int showSize = 0;
	static volatile List<List<CardPercent>> cardPercentss = new ArrayList<List<CardPercent>>();
	
	private void init(){
		synchronized (cardPercentss) {
			if(cardPercentss.isEmpty()){
				for(int i=1;i<6;i++){
					int first = 0;
					String[] perItems = QipaiCache.getConfVal("laba_card_percent"+i).trim().split("\\|");
					List<CardPercent> cardPercents = new ArrayList<Axis.CardPercent>();
					for(String pi:perItems){
						String[] cp = pi.split("=");
						int cardId = Integer.parseInt(cp[0].trim());
						int percent = first+Integer.parseInt(cp[1].trim());
						cardPercents.add(new CardPercent(first,percent,LabaCard.getCardById(cardId)));
						first = percent;
					}
					cardPercentss.add(cardPercents);
				}
			}
		}
	}
	
	public LabaMachine() {
		this(4);
		
	}
	public LabaMachine(int showSize) {
		this.showSize = showSize;
		init();
		for(int i=0;i<5;i++){
			Axis axis = new Axis();
			axis.setCardPercents(cardPercentss.get(i));
			axises.add(axis );
		}
		
	}
	
	public int getAxiseNum(){
		return axises.size();
	}
	
	public LabaCard[][] go(){
		LabaCard[][] result = new LabaCard[axises.size()][showSize];
		for(int i=0;i<axises.size();i++){
			List<LabaCard> ax = axises.get(i).rotate(showSize);
			int repCount = 0;
			while (haveTwoBar(ax) && repCount < 10) {
				ax = axises.get(i).rotate(showSize);
				repCount++;
			}
			result[i] = ax.toArray(new LabaCard[0]);
		}
		return result;
	}
	private boolean haveTwoBar(List<LabaCard> ax) {
		int count = 0;
		for(LabaCard lc:ax){
			if(lc == LabaCard.Bar){
				count++;
			}
		}
		return count>1;
	}
	
}
