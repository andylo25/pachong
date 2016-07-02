package com.andy.ext.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jfinal.plugin.activerecord.Model;
import com.qipai.common.vo.SortVO;
import com.qipai.util.DbUtil;

public class EqualDynSql implements IDynSql{
	private String sql;
	private List<Object> paras;
	private List<SortVO> sorts;
	private Map<String,?> params;
	private boolean needWhere;
	private Model model;
	
	public EqualDynSql(Model model) {
		this(model,null);
	}
	
	public EqualDynSql(Model model,Map<String,?> params) {
		this(model,params,true);
	}
	
	public EqualDynSql(Model model,Map<String,?> params,boolean needWhere) {
		this.model = model;
		this.params = params;
		this.needWhere = needWhere;
	}

	private void build() {
		StringBuilder sqlB = new StringBuilder(" ");
		if(params != null && !params.isEmpty()){
			if(needWhere)sqlB.append("where ");
			paras = new ArrayList<Object>();
			for(Entry<String, ?> entry:params.entrySet()){
				sqlB.append(toCol(entry.getKey())).append("=? and ");
				paras.add(entry.getValue());
			}
			sqlB.setLength(sqlB.length()-4);
		}
		
		if(sorts != null && !sorts.isEmpty()){
			sqlB.append(" order by ");
			for(SortVO vo:sorts){
				sqlB.append(toCol(vo.getProperty())).append(" ").append(vo.getDirection()).append(",");
			}
			sqlB.setLength(sqlB.length()-1);
		}
		sql = sqlB.toString();
	}
	
	public String toSql(){
		if(sql == null)build();
		return sql;
	}
	
	private String toCol(String attr){
		return DbUtil.getColName(model.getClass(), attr);
	}
	
	public Object[] getParams(){
		if(sql == null)build();
		if(paras == null)return new Object[0];
		return paras.toArray();
	}

	@Override
	public void setSorts(List<SortVO> sorts) {
		this.sorts = sorts;
	}
	
	public void setParas(Map<String,?> params){
		this.params = params;
	}
	
}
