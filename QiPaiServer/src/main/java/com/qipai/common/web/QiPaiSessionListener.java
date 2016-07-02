package com.qipai.common.web;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.qipai.common.GlobalVar;
import com.qipai.game.action.GameAction;
import com.qipai.game.model.GameUser;
import com.qipai.util.QPC;

public class QiPaiSessionListener implements HttpSessionListener {

	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		logger.info("创建session:"+event.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession httpSession = event.getSession();
		logger.info("销毁session:"+httpSession.getId());
		GameUser gameUser = (GameUser) httpSession.getAttribute(QPC.USER_SESSION_KEY);
		if(gameUser != null){
			GameAction.logOut(gameUser);
			GlobalVar.clearSess(gameUser);
		}
		httpSession.removeAttribute(QPC.USER_SESSION_KEY);
	}

}
