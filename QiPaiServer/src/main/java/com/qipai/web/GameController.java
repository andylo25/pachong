package com.qipai.web;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.andy.ext.Action;
import com.andy.util.MD5Util;
import com.qipai.common.GlobalVar;
import com.qipai.common.QipaiCache;
import com.qipai.common.game.comp.Resp;
import com.qipai.common.game.comp.Resp.RespGame;
import com.qipai.common.game.comp.RespMap;
import com.qipai.common.model.Rank;
import com.qipai.common.model.User;
import com.qipai.common.web.BaseController;
import com.qipai.game.action.GameAction;
import com.qipai.game.model.PopupMsg;
import com.qipai.util.GM;
import com.qipai.util.QPC;
import com.qipai.util.RewardUtil;

/**
 * IndexController
 */
@Action("/game")
public class GameController extends BaseController {
	
	// 进入游戏
	public void enterGame() {
		Integer gameId = getParaToInt("gameId");
		if(gameId != null){
			if(gameUser.getGame() != null){
				// 存在游戏
				renderResp(new Resp(QPC.ECD_997));
				return; 
			}
			if(GameAction.enterGame(gameId)){
				renderResp(new Resp().set("player",GlobalVar.getGameInfo(gameId).getPlayerNum()).set("rank", ""));
			}else{
				renderResp(new Resp(QPC.ECD_110));
			}
		}else{
			renderResp(new Resp(QPC.ECD_999));
		}
	}
	
	// 退出游戏
	public void quitGame(){
		if(gameUser.quitGame()){
			renderResp(new Resp().set("gm", new RespGame(gameUser.getUserBO())).set("coin", gameUser.getUserBO().getCoin()));
		}
	}
	
	// 公告
	public void notice(){
		renderResp(new Resp().set("content", QipaiCache.getNotic()));
	}
	
	// 分享
	public void share(){
		renderResp(new Resp());
	}
	
	// 排行榜
	public void rank(){
		Integer gameId = getParaToInt("gameId");
		if(gameId != null){
			List<RespMap<String, Object>> ranks = GlobalVar.getRespRanks();
			renderResp(new Resp().set("ranks", ranks));
		}else{
			renderResp(new Resp(QPC.ECD_999));
		}
	}
	
	// 临时排行榜
	public void tempRank(){
		Integer gameId = getParaToInt("gameId");
		if(gameId != null){
			String rank = GameAction.getTempRank(gameId);
			renderResp(new Resp().set("rank", rank).set("player",GlobalVar.getGameInfo(gameId).getPlayerNum()));
		}else{
			renderResp(new Resp(QPC.ECD_999));
		}
	}
	
	// 发送弹屏消息
	public void popup(){
		String msg = getPara("msg");
		if(StringUtils.isNotBlank(msg)){
			PopupMsg.instance().addMsg(gameUser.getUserBO(), msg);
			renderResp(new Resp());
		}else{
			renderResp(new Resp(QPC.ECD_999));
		}
	}
	
	// 轮询
	public void poll(){
		renderResp(new Resp().set("content", PopupMsg.instance().getMsgs(gameUser.getId()))
				.set("money", gameUser.getUserBO().getCoin()));
	}
	
	// 领取奖励
	public void prize(){
		String ptype = getPara("ptype");
		if("1".equals(ptype)){
			Rank rank = GlobalVar.getRanks().get(gameUser.getId());
			User user = gameUser.getUserBO();
			if(rank != null && (rank.getScore() == null || rank.getScore() == 0)){
				rank.setScore(1);
				rank.update();
				user.setCoin(user.getCoin()+RewardUtil.getRewardMoney(500+rank.getRank()));
				user.update();
				renderResp(new Resp().set("coin", user.getCoin()));
			}else{
				renderResp(new Resp(QPC.ECD_997));
			}
		}else{
			renderResp(new Resp(QPC.ECD_997));
		}
	}
	
	public void gm(){
		String ticket = getPara("tk");
		String cmd = getPara("cmd");
		if(StringUtils.isBlank(ticket) || StringUtils.isBlank(cmd)){
			renderResp(new Resp(QPC.ECD_999));
			return ;
		}
		if(ticket.equals(MD5Util.getEncryption(QipaiCache.getConfVal("gm_pwd")))){
			String[] cmds = cmd.split("\\|");
			String res = GameAction.doCmd(GM.valueOf(cmds[0]),Arrays.copyOfRange(cmds, 1, cmds.length));
			renderResp(new Resp().set("data", res));
		}else{
			renderResp(new Resp(QPC.ECD_101));
		}
	}
	
}





