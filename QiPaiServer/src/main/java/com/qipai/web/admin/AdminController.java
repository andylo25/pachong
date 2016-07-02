package com.qipai.web.admin;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.andy.ext.Action;
import com.andy.ext.GuiceUtil;
import com.andy.ext.sql.EqualDynSql;
import com.jfinal.plugin.activerecord.Page;
import com.qipai.common.GlobalVar;
import com.qipai.common.QipaiCache;
import com.qipai.common.game.comp.PayVO;
import com.qipai.common.game.comp.Resp;
import com.qipai.common.game.comp.Resp.RespGame;
import com.qipai.common.game.comp.SimpleMap;
import com.qipai.common.model.Charge;
import com.qipai.common.model.Notice;
import com.qipai.common.model.Score;
import com.qipai.common.model.Tax;
import com.qipai.common.model.User;
import com.qipai.common.vo.PageVO;
import com.qipai.common.web.BaseController;
import com.qipai.game.model.GameUser;
import com.qipai.service.IUserService;
import com.qipai.util.PayUtil;
import com.qipai.util.QPC;

/**
 * IndexController
 */
@Action("/admin")
public class AdminController extends BaseController {
	
	private Logger logger = Logger.getLogger(getClass());
	
	// 充值查询
	public void chargeList() {
//		SimpleMap.init("user_id", getPara("userId"));
		Page<Charge> page = findPage(Charge.dao, "select *", "from Charge");
		renderJson(page);
	}
	
	// 充值查询
	public void upChargeState() {
		int id = getParaToInt("id");
		Charge charge = Charge.dao.findById(id);
		if(charge != null){
			charge.setOrdreState("1");
			renderResp();
		}else{
			renderResp(new Resp(QPC.ECD_997));
		}
	}

	// 下注流水记录查询
	public void scoreList(){
		Page<Score> page = findPage(Score.dao, "select *", "from Score");
		renderJson(page);
	}
	
	// 用户信息查询
	public void userList(){
		Page<User> page = findPage(User.dao, "select *", "from User");
		renderJson(page);
	}
	// 修改金币
	public void updateCoin(){
		int id = getParaToInt("id");
		GameUser gameUser = GlobalVar.getUser(id);
		User user = null;
		if(gameUser != null){
			user = gameUser.getUserBO();
		}else{
			user = User.dao.findById(id);
		}
		if(user != null){
			user.addCoin(getParaToInt("coin"));
			user.update();
			renderResp();
		}else{
			renderResp(new Resp(QPC.ECD_997));
		}
	}
	
	
	// 税收查询
	public void taxList(){
		Page<Tax> page = findPage(Tax.dao, "select *", "from Tax");
		renderJson(page);
	}
	
	// 更新税率：1-总库存，2-奖金池，3-活动金币
	public void updateStock(){
		int gameId = getParaToInt("gameId");
		int flag = getParaToInt("flag");
		int coin = getParaToInt("coin");
		
		Tax tax = QipaiCache.getTax(gameId);
		if(tax != null){
			if(flag == 1){
				tax.addTaxStock(coin);
			}else if(flag == 2){
				tax.setActivMoney(tax.getActivMoney()+coin);
			}
			tax.updateCoinPoo();
			renderResp();
		}else{
			renderResp(new Resp(QPC.ECD_997));
		}
	}
	
	// 税收查询
	public void noticeList(){
		Page<Notice> page = findPage(Notice.dao, "select *", "from Notice");
		renderJson(page);
	}
	
	// 修改公告
	public void updateNotice(){
		int id = getParaToInt("id");
		String noticeC = getPara("notice");
		Notice notice = Notice.dao.findById(id);
		if(notice != null){
			notice.setNotice(noticeC);
			QipaiCache.upNotic(noticeC);
			notice.update();
			renderResp();
		}else{
			renderResp(new Resp(QPC.ECD_997));
		}
	}
}





