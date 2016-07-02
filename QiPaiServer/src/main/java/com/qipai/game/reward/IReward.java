package com.qipai.game.reward;

import java.util.List;

public interface IReward {

	public int getWinTimes();
	
	public int getFreeTimes();
	
	public List<Integer> getRwdIds();
}
