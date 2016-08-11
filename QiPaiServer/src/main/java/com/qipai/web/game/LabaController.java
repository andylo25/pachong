package com.qipai.web.game;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.andy.ext.Action;
import com.qipai.common.game.comp.Resp;
import com.qipai.game.model.LabaUserGameInfo;
import com.qipai.util.QPC;
import com.qipai.util.StringUtil;

/**
 * IndexController
 */
@Action("/game/laba")
public class LabaController extends AbsGameController {
	
	private static Logger logger = Logger.getLogger(LabaController.class);
	
	// 拉下
	public void pull() {
		long start = System.currentTimeMillis();
		Integer coin = getParaToInt("coin");
		Integer lines = getParaToInt("lines");
		if(coin == null || coin <= 0 || coin>200000 || lines == null || lines <= 0){
			renderResp(new Resp(QPC.ECD_997));
			return;
		}
		LabaUserGameInfo userGameInfo = gameUser.getLabaUserGameInfo();
		int ecd = 0;
		if(!isReapet()){
			ecd = userGameInfo.pull(coin,lines);
		}
		if(ecd == 0){
			if(logger.isInfoEnabled()){
				logger.info("card:"+StringUtil.joinIds(userGameInfo.getCards(), ","));
				logger.info("rwds:"+StringUtils.join(userGameInfo.getRwds(), ","));
			}
			renderResp(new Resp().set("card", StringUtil.joinIds(userGameInfo.getCards(), ","))
					.set("winCoin", userGameInfo.getWinCoin())
					.set("ft", userGameInfo.getFreeTimes())
					.set("rwds", StringUtils.join(userGameInfo.getRwds(), ",")));
		}else{
			renderResp(new Resp(ecd));
		}
		
		long hs = System.currentTimeMillis()-start;
		if(hs > 5000){
			logger.error("拉霸耗时："+hs);
		}
	}
	
}





