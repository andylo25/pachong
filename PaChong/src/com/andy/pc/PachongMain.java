package com.andy.pc;

import java.util.List;

import com.andy.core.PageInfo;
import com.andy.core.db.DBUtil;
import com.andy.pc.vo.DataVO;

public class PachongMain {

	public static void main(String[] args) {
		getHdwan();
	}
	
	/**
	 * 爬取海盗湾资源
	 */
	public static void getHdwan(){
		HdwanPc hd = new HdwanPc();
		
		PageInfo page = hd.buildPage(65);//72
		List<DataVO> dataVOs = hd.getPage(page );
		
		while (!dataVOs.isEmpty()) {
			System.out.println("page:"+page.getCurPageNo());
			DBUtil.save(dataVOs);
			dataVOs = hd.getPage(page.next());
		}
		
	}
	

}
