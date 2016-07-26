package com.qipai.common.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qipai.game.model.GameUser;
import com.qipai.util.QPC;

public class AdminFilter implements Filter{

	
	private int contextPathLength;

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		request.setCharacterEncoding("UTF-8");
		String target = request.getRequestURI();
		if (contextPathLength != 0)
			target = target.substring(contextPathLength);
		
		if(doAuthCheck(request,response,target)){
			chain.doFilter(req, res);
		}else{
			response.sendRedirect(request.getContextPath()+QPC.LOGIN_JSP);
		}
		
	}

	private boolean doAuthCheck(HttpServletRequest request, HttpServletResponse response, String target) {
		if(!target.endsWith(".jsp"))return true;
		GameUser gameUser = (GameUser) request.getSession().getAttribute(QPC.USER_SESSION_KEY);
		if((gameUser == null || !QPC.vip_admin.equals(gameUser.getUserBO().getIsVip())) && !target.equals(QPC.LOGIN_JSP) && !target.startsWith("/resources") 
				&& !target.startsWith("/public")){
			return false;
		}
		return true;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String contextPath = config.getServletContext().getContextPath();
		contextPathLength = (contextPath == null || "/".equals(contextPath) ? 0 : contextPath.length());
	}

}
