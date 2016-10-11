package com.andy.pc;

import java.util.List;

import com.andy.core.PageInfo;
import com.andy.pc.vo.DataVO;

public interface IPachong {

	
	public List<DataVO> getPage(PageInfo page);
	
	public List<DataVO> nextPage();
	
	public DataVO getRecord(String idstr);
	
	public DataVO nextRecord();
	
	public byte[] getTorrent(String href);
	
}
