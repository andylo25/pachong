package com.andy.ext.sql;

import java.util.List;

import com.qipai.common.vo.SortVO;

public interface IDynSql {

	public String toSql();
	
	public Object[] getParams();
	
	public void setSorts(List<SortVO> sorts);
}
