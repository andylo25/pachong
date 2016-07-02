package com.qipai.common.interceptor;

import org.apache.log4j.Logger;

import com.andy.ext.GuiceAnnoModule;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.qipai.common.GlobalVar;
import com.qipai.common.game.comp.Resp;
import com.qipai.common.web.BaseController;
import com.qipai.game.model.GameUser;
import com.qipai.util.QPC;
import com.qipai.util.UserUtil;

public class AuthInterceptor implements Interceptor {

	private Logger logger = Logger.getLogger(AuthInterceptor.class);
	
	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		GameUser gameUser = (GameUser) controller.getSession().getAttribute(QPC.USER_SESSION_KEY);
		if(gameUser != null){
			UserUtil.set(gameUser);
			if(controller instanceof BaseController){
				((BaseController)controller).setUser(gameUser);
			}
		}
		try {
			if("/user/login".equals(inv.getActionKey()) || 
				"/user/regist".equals(inv.getActionKey()) ||
				"/user/charge".equals(inv.getActionKey()) ||
				gameUser != null){
				inv.invoke();
			}else {
				if("/admin".equals(inv.getActionKey())){
					inv.getController().redirect(QPC.LOGIN_JSP);
				}else{
					inv.getController().renderJson(new Resp(QPC.ECD_998).resp());
				}
			}
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			inv.getController().renderJson(new Resp(QPC.ECD_999).resp());
		}finally{
			UserUtil.remove();
			if(controller instanceof BaseController){
				((BaseController)controller).removeUser();
			}
		}
	}
	
	

}
