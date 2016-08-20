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
		long start = System.currentTimeMillis();
		Controller controller = inv.getController();
		GameUser gameUser = (GameUser) controller.getSession().getAttribute(QPC.USER_SESSION_KEY);
		if(gameUser != null){
			UserUtil.set(gameUser);
			if(controller instanceof BaseController){
				((BaseController)controller).setUser(gameUser);
			}
		}
		String akey = inv.getActionKey();
		try {
			if(("/user/login".equals(akey) || 
				"/user/regist".equals(akey) ||
				"/user/charge".equals(akey) ||
				"/game/gm".equals(akey) ||
				gameUser != null) && (!akey.startsWith("/admin") || QPC.vip_admin.equals(gameUser.getUserBO().getIsVip()))){
				inv.invoke();
			}else {
				if(akey.startsWith("/admin")){
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
			controller.getSession().setAttribute(QPC.USER_SESSION_REQID_KEY,controller.getPara(QPC.USER_SESSION_REQID_KEY));
			if(controller instanceof BaseController){
				((BaseController)controller).removeUser();
			}
			if(logger.isInfoEnabled()){
				logger.info("性能监控：action["+akey+"],wtime:"+(System.currentTimeMillis()-start));
			}
		}
	}
	
	

}
