package com.andy.ext.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jfinal.plugin.activerecord.Model;
import com.qipai.common.vo.FilterVO;
import com.qipai.common.vo.SortVO;
import com.qipai.util.DbUtil;

public class ComplexDynSql implements IDynSql{
	private String sql;
	private List<Object> paras;
	private List<SortVO> sorts;
	private List<FilterVO> filterVOs;
	private boolean needWhere;
	private Model model;
	
	public ComplexDynSql(Model model) {
		this(model,null);
	}
	
	public ComplexDynSql(Model model,List<FilterVO> filterVOs) {
		this(model,filterVOs,true);
	}
	
	public void setSorts(List<SortVO> vos){
		this.sorts = vos;
	}
	
	public ComplexDynSql(Model model,List<FilterVO> filterVOs,boolean needWhere) {
		this.model = model;
		this.filterVOs = filterVOs;
		this.needWhere = needWhere;
	}

	private void build() {
		StringBuilder sqlB = new StringBuilder();
		if(filterVOs != null && !filterVOs.isEmpty()){
			sqlB.append(needWhere?" where ":" ");
			paras = new ArrayList<Object>();
			for(FilterVO item:filterVOs){
				sqlB.append(toCol(item.getField())).append(item.getComparison()).append(" and ");
				paras.add(item.getValue());
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
	
	public Object[] getParams(){
		if(sql == null)build();
		if(paras == null)return new Object[0];
		return paras.toArray();
	}
	
	private String toCol(String attr){
		return DbUtil.getColName(model.getClass(), attr);
	}
	
	public static class DynItem{
		String name;
		String compare;
		Object para;
		public DynItem(String name,String compare,Object para) {
			this.name = name;
			this.compare = compare;
			this.para = para;
		}
		
		public DynItem(String name,Object para) {
			this(name,"=",para);
		}
	}
}
