package com.andy.pc;

import java.util.List;

import com.andy.core.PCDaoUtil;
import com.andy.core.PageInfo;
import com.andy.pc.vo.DataVO;

public class PachongMain {

	public static void main(String[] args) {
//		getHdwan();
		getDouban();
	}
	
	/**
	 * 爬取海盗湾资源
	 */
	public static void getHdwan(){
		HdwanPc hd = new HdwanPc();
		
		PageInfo page = hd.buildPage(1);//72
		List<DataVO> dataVOs = hd.getPage(page );
		
		while (!dataVOs.isEmpty()) {
			System.out.println("page:"+page.getCurPageNo());
			PCDaoUtil.saveHdwan(dataVOs);
			dataVOs = hd.getPage(page.next());
		}
		
	}
	
	/**
	 * 爬取豆瓣资源
	 */
	public static void getDouban(){
		DoubanPc hd = new DoubanPc("动作");
		
		PageInfo page = hd.buildPage(8);
		List<DataVO> dataVOs = hd.getPage(page );
		
		while (!dataVOs.isEmpty()) {
			System.out.println("page:"+page.getCurPageNo());
			PCDaoUtil.saveDouban(dataVOs);
			dataVOs = hd.getPage(page.next());
		}
		
	}
	

}
