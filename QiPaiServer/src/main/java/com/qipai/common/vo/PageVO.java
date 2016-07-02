package com.qipai.common.vo;

import java.io.Serializable;
import java.util.List;

import com.andy.ext.sql.ComplexDynSql.DynItem;
import com.qipai.common.game.comp.SimpleMap;

public class PageVO implements Serializable{
	int limit;
	int page;
	int start;
	List<SortVO> sort;
	List<FilterVO> filter;
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public List<SortVO> getSort() {
		return sort;
	}
	public void setSort(List<SortVO> sort) {
		this.sort = sort;
	}
	
	public List<FilterVO> getFilter() {
		return filter;
	}
	public void setFilter(List<FilterVO> filter) {
		this.filter = filter;
	}
	
}
