package com.qipai.common.game.comp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.andy.util.RandomUtils;
import com.qipai.common.QipaiCache;
import com.qipai.common.game.comp.Axis.CardPercent;

public class LabaMachine {
	private Logger logger = Logger.getLogger(getClass());
	private List<Axis> axises = new ArrayList<Axis>();
	private int showSize = 0;
	static volatile List<List<CardPercent>> cardPercentss = new ArrayList<List<CardPercent>>();
	static volatile List<List<CardPercent>> cardPercentss_f = new ArrayList<List<CardPercent>>();
	
	public static void refreshPerc(){
		List<List<CardPercent>> cardPercentssTemp = new ArrayList<List<CardPercent>>();
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
			cardPercentssTemp.add(cardPercents);
		}
		cardPercentss = cardPercentssTemp;

		List<List<CardPercent>> cardPercentss_fTemp = new ArrayList<List<CardPercent>>();
		for(int i=1;i<6;i++){
			int first = 0;
			String[] perItems = QipaiCache.getConfVal("laba_card_percent_f"+i).trim().split("\\|");
			List<CardPercent> cardPercents = new ArrayList<Axis.CardPercent>();
			for(String pi:perItems){
				String[] cp = pi.split("=");
				int cardId = Integer.parseInt(cp[0].trim());
				int percent = first+Integer.parseInt(cp[1].trim());
				cardPercents.add(new CardPercent(first,percent,LabaCard.getCardById(cardId)));
				first = percent;
			}
			cardPercentss_fTemp.add(cardPercents);
		}
		cardPercentss_f = cardPercentss_fTemp;
	}
	
	public LabaMachine() {
		this(4,false);
		
	}
	
	/**
	 * @param isFreeMod 是否免费模式
	 */
	public LabaMachine(boolean isFreeMod) {
		this(4,isFreeMod);
		
	}
	public LabaMachine(int showSize,boolean isFreeMod) {
		this.showSize = showSize;
		for(int i=0;i<5;i++){
			Axis axis = new Axis();
			if(isFreeMod){
				axis.setCardPercents(cardPercentss_f.get(i));
			}else{
				axis.setCardPercents(cardPercentss.get(i));
			}
			axises.add(axis );
		}
		
	}
	
	public int getAxiseNum(){
		return axises.size();
	}
	
	public LabaCard[][] goSame(){
		LabaCard[][] result = new LabaCard[axises.size()][showSize];
		LabaCard cardz = LabaCard.values()[RandomUtils.getRandomNum(0, 11)];
		for(int i=0;i<axises.size();i++){
			result[i] = axises.get(i).rotateSame(showSize,cardz).toArray(new LabaCard[0]);
		}
		return result;
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
			if(logger.isInfoEnabled()){
				if(repCount > 3){
					logger.info("拉霸重复计算次数："+repCount);
				}
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
