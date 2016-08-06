package com.qipai.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.andy.ext.Service;
import com.andy.util.UtilDatetime;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.qipai.common.GlobalVar;
import com.qipai.common.QipaiCache;
import com.qipai.common.game.comp.PayVO;
import com.qipai.common.model.Charge;
import com.qipai.common.model.User;
import com.qipai.game.model.GameUser;
import com.qipai.service.IUserService;
import com.qipai.util.DbBatch;
import com.qipai.util.PayUtil;
import com.qipai.util.QPC;
import com.qipai.util.UserUtil;

@Service
public class UserService implements IUserService{

	@Override
	public User login(User user) {
		if(StringUtils.isBlank(user.getUserName()))return null;
		user = User.dao.findFirst("select * from user where user_Name=? and pwd=?", user.getUserName(),user.getPwd());
		if(user != null){
			user.setLoginTime(new Date());
			user.setOnline("1");
			DbBatch.up(user);
		}
		return user;
	}

	@Override
	public int regist(User user) {
		Record record = Db.findFirst("select 1 from user where user_Name=?", user.getUserName());
		if(record != null && record.getLong("1") == 1){
			return QPC.ECD_100;
		}else{
			user.setCoin(QPC.REGIST_REWARD_COIN);
			user.setBankCoin(0);
			user.setIsVip("0");
			user.setStatus(0);
			user.save();
			return QPC.ECD_0;
		}
	}
	
	public int quit(){
		return QPC.ECD_0;
	}

	@Override
	public void pay(PayVO payVO) {
		payVO.setOrder_time(UtilDatetime.getDatetimeString(new Date()));
		
		Charge charge = new Charge();
		charge.setMoney(Integer.parseInt(payVO.getOrder_amount()));
		charge.setCoin(charge.getMoney()*Integer.parseInt(QipaiCache.instance().getConfVal("money_to_coin")));
		charge.setFrom(StringUtils.isNotBlank(payVO.getService_type())?payVO.getService_type():"PHONE_APP");
		charge.setOrdreState("0");
		charge.setTxnTime(new Date());
		charge.setUserId(UserUtil.get().getId());
		
		payVO.setExtra_return_param(String.valueOf(charge.getUserId()));
		
		charge.setOrderNo(PayUtil.genOrderNo(payVO.getExtra_return_param()));
		charge.save();
		
		payVO.setOrder_no(charge.getOrderNo());
		
	}
	
	@Override
	public void addTransSerial(User user,User dest,int coin){
		Charge charge = new Charge();
		charge.setMoney(dest.getId());
		charge.setCoin(coin);
		charge.setFrom("转账");
		charge.setOrdreState("1");
		charge.setTxnTime(new Date());
		charge.setUserId(user.getId());
		
		charge.setOrderNo(PayUtil.genOrderNo(String.valueOf(user.getId())));
		charge.save();
	}

	@Override
	public void charge(PayVO payVO) {
		// 1.判断商家号、商家订单号、订单金额、交易状态、是否重复通知
		if(payVO.getMerchant_code().equals(QipaiCache.instance().getConfVal("pay_merchant_code"))
				&& "SUCCESS".equals(payVO.getTrade_status())){
			Charge charge = Charge.dao.findFirst("select * from charge where order_no=?", payVO.getOrder_no());
			if(charge != null && charge.getMoney() == Integer.parseInt(payVO.getOrder_amount())
					&& !"1".equals(charge.getOrdreState())){
				charge.setOrdreState("1");
				charge.update();
				
				User user = null;
				GameUser gameUser = GlobalVar.getUser(charge.getUserId());
				if(gameUser == null){
					user = User.dao.findById(charge.getUserId());
				}else{
					user = gameUser.getUserBO();
					gameUser.charge();
				}
				user.setCoin(user.getCoin()+charge.getCoin());
				user.update();
			}else{
				charge.setOrdreState("2");
				charge.update();
			}
		}
	}

}
