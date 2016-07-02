package com.qipai.web.game;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.qipai.common.game.comp.Card;
import com.qipai.common.game.comp.Resp;
import com.qipai.common.web.BaseController;
import com.qipai.game.reward.ThsReward;
import com.qipai.util.QPC;
import com.qipai.util.StringUtil;

/**
 * IndexController
 */
public abstract class AbsGameController extends BaseController {
	
	// 赢分加倍
	public void doub(){
		Integer hh = getParaToInt("hh");
		int card = gameUser.getUserGameInfo().doub(hh);
		if(card != QPC.INVALID){
			renderResp(new Resp().set("card", card).set("coin", gameUser.getUserGameInfo().getDoubCoin()));
		}else{
			renderResp(new Resp(QPC.ECD_997));
		}
	}
	
	// 退出加倍
	public void quitdoub(){
		int winCoin = gameUser.getUserGameInfo().quitdoub();
		if(winCoin != QPC.INVALID){
			renderResp(new Resp().set("coin", winCoin));
		}else{
			renderResp(new Resp(QPC.ECD_997));
		}
	}
	
	
}





