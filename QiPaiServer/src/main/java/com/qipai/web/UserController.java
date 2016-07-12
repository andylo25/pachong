package com.qipai.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSON;
import com.andy.ext.Action;
import com.andy.ext.GuiceUtil;
import com.qipai.common.GlobalVar;
import com.qipai.common.QipaiCache;
import com.qipai.common.game.comp.PayVO;
import com.qipai.common.game.comp.Resp;
import com.qipai.common.game.comp.Resp.RespGame;
import com.qipai.common.model.Charge;
import com.qipai.common.model.User;
import com.qipai.common.web.BaseController;
import com.qipai.game.model.GameUser;
import com.qipai.service.IUserService;
import com.qipai.util.PayUtil;
import com.qipai.util.QPC;
import com.qipai.util.StringUtil;

import cn.zf.http.HttpClientUtil;
import cn.zf.qrcode.QrcodeDis;
import sun.org.mozilla.javascript.internal.ObjArray;

/**
 * IndexController
 */
@Action("/user")
public class UserController extends BaseController {
	
	private Logger logger = Logger.getLogger(getClass());
	
	// 登录
	public void login() {
		boolean isAdmin = getPara("admin") != null;
		User user = new User();
		user.setUserName(getPara("userName"));
		user.setPwd(getPara("pwd"));
		user = getUserSrv().login(user );
		
		if(user != null && (!isAdmin || QPC.vip_admin.equals(user.getIsVip()))){
			// 管理员登录必须vip为1
			if(logger.isInfoEnabled()){
				logger.info("登录用户信息："+user);
			}
			GameUser gameUser = initLogin(user);
			if(!isAdmin){
				renderResp(new Resp().setUser(user).set("gm", new RespGame(user)));
			}else{
				setSessionAttr("userName", user.getUserName());
				redirect("/admin/index.jsp");
			}
		}else{
			if(!isAdmin){
				renderResp(new Resp(QPC.ECD_101));
			}else{
				setAttr("error_msg", "用户名或密码错误！不是管理员？");
				user.setOnline("0");
				user.update();
				renderJsp(QPC.LOGIN_JSP);
			}
		}
	}
	
	private GameUser initLogin(User user) {
		GameUser gameUser = new GameUser(user);
		GlobalVar.clearSess(gameUser);
		getSession().setAttribute(QPC.USER_SESSION_KEY, gameUser);
		GlobalVar.addSession(getSession());
		return gameUser;
	}

	// 验证码
	public void mask(){
		
	}
	
	// 注册
	public void regist(){
		User user = new User();
		user.setUserName(getPara("userName"));
		user.setPwd(getPara("pwd"));
		user.setNickName(getPara("nickName"));
		
		if(user.getUserName().matches("^1[3|4|5|7|8]\\d{9}$")){
			int ecd = getUserSrv().regist(user);
			renderResp(new Resp(ecd));
		}else{
			renderResp(new Resp(QPC.ECD_997));
		}
		
	}
	
	// 登出
	public void logout(){
//		GlobalVar.clearSess((GameUser) getSessionAttr(QPC.USER_SESSION_KEY));
		getSession().invalidate();
		redirect(QPC.LOGIN_JSP);
	}
	
	// 玩家转账给客服
	public void trans(){
		int uid = getParaToInt("uid");
		int coin = getParaToInt("coin");
		User user = gameUser.getUserBO();
		GameUser gUser = GlobalVar.getUser(uid);
		User tuser = null;
		if(gUser != null){
			tuser = gUser.getUserBO();
		}else{
			tuser = User.dao.findById(uid);
		}
//		if(tuser != null && QPC.vip_admin_trans.equals(tuser.getIsVip())){
		if(tuser != null && coin >= 100000 && coin <= 100000000){
			if(user.getCoin() < coin){
				renderResp(new Resp(QPC.ECD_120));
			}else{
				user.addCoin(-coin);
				user.update();
				tuser.addCoin(coin);
				getUserSrv().addTransSerial(user, tuser, coin);
				tuser.update();
				renderResp(new Resp().set("coin", user.getCoin()));
			}
		}else{
			renderResp(new Resp(QPC.ECD_997));
		}
	}
	
	// 支付签名
	public void payout(){
		PayVO payVO = new PayVO();
		Integer pt = getParaToInt("pt");//支付方式0-web,1-app,2-weixin
		Integer index = getParaToInt("index");
		Integer num = getParaToInt("num");
		
		if(num < 0 || num > 1000000){
			renderResp(new Resp(QPC.ECD_997));
			return ;
		}
		String[] pay_product_types = QipaiCache.instance().getConfArray("pay_product_types");
		payVO.setOrder_amount(String.valueOf(Integer.parseInt(pay_product_types[index])*num));
		
		payVO.setMerchant_code(QipaiCache.instance().getConfVal("pay_merchant_code"));
		payVO.setInterface_version("V3.0");
		payVO.setSign_type(QipaiCache.instance().getConfVal("pay_sign_type"));
		payVO.setInput_charset("UTF-8");
		payVO.setRedo_flag("1");
		payVO.setNotify_url(QipaiCache.instance().getConfVal("pay_notify_url"));
		payVO.setProduct_name(QipaiCache.instance().getConfVal("pay_product_name"));
		payVO.setProduct_desc(QipaiCache.instance().getConfVal("pay_product_desc"));
		if(pt == 2){
			// 微信扫码
			payVO.setRedo_flag("");
			payVO.setService_type("wxpay");
		}else if(pt == 0){
			// web支付
			payVO.setService_type(QipaiCache.instance().getConfVal("pay_service_type"));
		}
		
		try {
			// 创建订单
			getUserSrv().pay(payVO);
			String[] signStr = PayUtil.signDinpay(payVO);
			if(pt == 2){
				buildQrcodeImg(signStr[0],signStr[1]);
			}else{
				renderResp(new Resp().set("param", signStr[0]).set("sign", signStr[1]));
			}
		} catch (Exception e) {
			logger.error("创建订单",e);
			renderResp(new Resp(QPC.ECD_999));
		}
	}
	
	private void buildQrcodeImg(String param,String sign) throws IOException, DocumentException {
		Map<String, String> reqMap = StringUtil.fromUrlParam(param);
		reqMap = new TreeMap<String, String>(reqMap);
		reqMap.put("sign", sign);
		// 向智付发送POST支付请求
		String result = HttpClientUtil.doPost(QPC.wx_reqUrl, reqMap, "utf-8");
		// 解析result，获取二维码节点qrcode的值
        Document document = DocumentHelper.parseText(result);
        Element root = document.getRootElement();
        Element res = root.element("response");
        if("SUCCESS".equals(res.element("resp_code").getText())){
        	String qrcode = res.element("trade").element("qrcode").getText();
        	
        	String webRootPath = getSession().getServletContext().getRealPath("/");
        	String logoPath = webRootPath + "resources/images/dinpay.png";
        	QrcodeDis myQrcode = new QrcodeDis();
        	myQrcode.createQRCodeBytes(qrcode, logoPath, 15,getResponse().getOutputStream());
        	completeRender();
        }else{
        	String webRootPath = getSession().getServletContext().getRealPath("/");
        	String logoPath = webRootPath + "resources/images/dinpay.png";
        	QrcodeDis myQrcode = new QrcodeDis();
        	myQrcode.createQRCodeBytes("weixin://wxpay/bizpayurl?pr=grELAoH", logoPath, 15,getResponse().getOutputStream());
        	completeRender();
//        	renderResp(new Resp(QPC.ECD_997));
        }
	}

	// 充值回调
	public void charge(){
		logger.error("充值参数get："+JSON.toJSONString(getParaMap()));
		PayVO payVO = new PayVO();
		payVO.setMerchant_code(getPara("merchant_code"));
		payVO.setNotify_type(getPara("notify_type"));
		payVO.setNotify_id(getPara("notify_id"));
		payVO.setInterface_version(getPara("interface_version"));
		payVO.setSign_type(getPara("sign_type"));
		payVO.setDinpaySign(getPara("sign"));
		payVO.setOrder_no(getPara("order_no"));
		payVO.setOrder_time(getPara("order_time"));
		payVO.setOrder_amount(getPara("order_amount"));
		payVO.setTrade_no(getPara("trade_no"));
		payVO.setTrade_time(getPara("trade_time"));
		payVO.setTrade_status(getPara("trade_status"));
		payVO.setBank_seq_no(getPara("bank_seq_no"));
		payVO.setExtra_return_param(getPara("extra_return_param"));
		
		try {
			if(PayUtil.checkDinpaySign(payVO)){
				getUserSrv().charge(payVO);
				renderText("SUCCESS");
				return;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		logger.error("充值签名验证失败");
		renderText("INVALID");
	}
	
	// coin明细
	public void detail(){
		User user = gameUser.getUserBO();
		List<Charge> charges = Charge.dao.find("select * from charge c where c.ordre_state!='0' and (c.user_id=? or c.money=?) order by id desc", new Object[]{user.getId(),user.getId()});
		if(charges != null){
			renderResp(new Resp().set("results", charges));
		}else{
			renderResp(new Resp());
		}
	}
	
	private IUserService getUserSrv(){
		return GuiceUtil.getBean(IUserService.class);
	}
	
}





