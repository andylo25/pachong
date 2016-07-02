package com.qipai.web.game;

import org.apache.log4j.Logger;

import com.andy.ext.Action;
import com.qipai.common.game.comp.Card;
import com.qipai.common.game.comp.Resp;
import com.qipai.util.ProfileUtil;
import com.qipai.util.QPC;
import com.qipai.util.StringUtil;

/**
 * IndexController
 */
@Action("/game/baida")
public class BaidaController extends AbsGameController {
	
	private static Logger logger = Logger.getLogger(BaidaController.class);
	
	// 翻牌
	public void open() {
		long start = System.currentTimeMillis();
		Integer money = getParaToInt("money");
		if(money == null || money <= 0 || money > 10000000){
			renderResp(new Resp(QPC.ECD_997));
			return;
		}
		if(gameUser.checkMoney(money)){
			Card[] cards = gameUser.getBaidaUserGameInfo().open(money);
			renderResp(new Resp().set("card", StringUtil.joinIds(cards, ",")));
		}else{
			renderResp(new Resp(QPC.ECD_120));
		}
		long hs = System.currentTimeMillis()-start;
		if(hs > 5000){
			logger.error("翻牌耗时："+hs);
		}
	}
	
	// 换牌
	public void change(){
		String keepCards = getPara("keepCard");
		if(keepCards == null)keepCards = "";
		long start = System.currentTimeMillis();
		int[] winRes = gameUser.getBaidaUserGameInfo().change(keepCards.split(","));
		if(winRes[0] < 0){
			renderResp(new Resp(QPC.ECD_997));
		}else{
			renderResp(new Resp().set("card", StringUtil.joinIds(gameUser.getBaidaUserGameInfo().getCards(), ","))
					.set("coin", winRes[0])
					.set("rwd", winRes[1]));
		}
		
		long hs = System.currentTimeMillis()-start;
		if(hs > 5000){
			logger.error("换牌耗时："+hs);
		}
		
	}
	
	
}





