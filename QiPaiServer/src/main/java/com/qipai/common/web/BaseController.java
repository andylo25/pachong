package com.qipai.common.web;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.andy.ext.sql.ComplexDynSql;
import com.andy.ext.sql.EqualDynSql;
import com.andy.ext.sql.IDynSql;
import com.andy.ext.sql.ComplexDynSql.DynItem;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.qipai.common.game.comp.Resp;
import com.qipai.common.game.comp.SimpleMap;
import com.qipai.common.model.Charge;
import com.qipai.common.vo.FilterVO;
import com.qipai.common.vo.PageVO;
import com.qipai.common.vo.SortVO;
import com.qipai.game.model.GameUser;
import com.qipai.util.QPC;

public class BaseController extends Controller {

	protected GameUser gameUser = null;
	private boolean isRendered = false;
	private PageVO page;
	
	public void setUser(GameUser gameUser){
		this.gameUser = gameUser;
	}
	
	public void removeUser() {
		this.gameUser = null;
	}
	
	protected void renderResp(Resp resp){
		renderJson(resp.resp());
	}
	
	protected void renderResp(){
		renderJson(new Resp().resp());
	}
	
	public void index() {
		renderJsp("/index.jsp");
	}
	
	public String getContextPath(){
		return getRequest().getContextPath();
	}
	
	public boolean isRendered(){
		return isRendered;
	}
	public void completeRender(){
		isRendered = true;
	}
	
	/**
	 * 是否重发
	 * @return
	 */
	public boolean isReapet(){
		String rid = getPara(QPC.USER_SESSION_REQID_KEY);
		String rido = (String) getSession().getAttribute(QPC.USER_SESSION_REQID_KEY);
		if(rid != null && rid.equals(rido)){
			return true;
		}
		return false;
	}

	public PageVO getPage(){
		if(page == null){
			page = new PageVO();
			page.setLimit(getParaToInt("limit"));
			page.setPage(getParaToInt("page"));
			page.setStart(getParaToInt("start"));
			page.setSort(JSON.parseArray(getPara("sort"), SortVO.class));
			page.setFilter(JSON.parseArray(getPara("filter"), FilterVO.class));
		}
		return page;
	}
	
	public <T> Page<T> findPage(Model model,String select,String sqlExceptSelect,SimpleMap<String, String> simpleMap){
		IDynSql	dynSql = new EqualDynSql(model,simpleMap);
		PageVO pageT = getPage();
		dynSql.setSorts(pageT.getSort());
		return model.paginate(pageT.getPage(), pageT.getLimit(), select, sqlExceptSelect+dynSql.toSql(),dynSql.getParams());
	}
	
	public <T> Page<T> findPage(Model model,String select,String sqlExceptSelect,List<FilterVO> filterVOs){
		IDynSql dynSql = new ComplexDynSql(model,filterVOs);
		PageVO pageT = getPage();
		dynSql.setSorts(pageT.getSort());
		return model.paginate(pageT.getPage(), pageT.getLimit(), select, sqlExceptSelect+dynSql.toSql(),dynSql.getParams());
	}
	
	public <T> Page<T> findPage(Model model,String select,String sqlExceptSelect){
		return findPage(model, select, sqlExceptSelect,getPage().getFilter());
	}
	
}
