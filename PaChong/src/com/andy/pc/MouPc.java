package com.andy.pc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.andy.core.PCDaoUtil;
import com.andy.core.PCUtil;
import com.andy.core.PageInfo;
import com.andy.core.db.DBUtil;
import com.andy.pc.vo.DataVO;

public class MouPc implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static final String URL = "";
	private static int count = 0;
	
	private String type;
	private PageInfo curPage;

	public MouPc(String type,int from) {
		this.type = type;
		this.curPage = from(from);
	}

	public List<DataVO> getPageData() {
		System.out.println("page:"+curPage.getCurPageNo());
		Document doc=null;
		doc = PCUtil.getDoc(getConnect(getPageUrl()));
		Elements els = doc.select(".movieList a");
		if(els != null){
			List<DataVO> dataVOs = new ArrayList<>(); 
			for(int i = 0;i<els.size();i++){
				Element a = els.get(i);
				String href = a.attr("abs:href");
				Elements as = a.select("h3");
				if(!as.isEmpty()){
					Element h3 = as.get(0);
					String title = h3.text();
					if(!checkHave(title)){
						DataVO vo = getRecord(href);
						vo.setName(title);
						dataVOs.add(vo);
						PCDaoUtil.saveMou(vo);
					}
				}
//				if(i==2)break;
			}
			return dataVOs;
		}
		return null;
	}

	private boolean checkHave(String title) {
		Long count = DBUtil.getScalar("select 1 from mou_res where name=?", title);
		if(count != null){
			System.err.println("已经存在："+title);
			return true;
		}
		System.out.println("新增：第"+(++MouPc.count)+"个--"+title);
		return false;
	}

	private Connection getConnect(String url) {
		return Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36").timeout(20000);
	}

	public DataVO getRecord(String url) {
		DataVO vo = new DataVO();
		Document doc = PCUtil.getDoc(getConnect(url));
		Elements as = doc.select("#btplay .play-list > a");
		if(as != null && !as.isEmpty()){
			String href = as.get(0).attr("href");
			if(href != null && href.startsWith("http")){
				try {
					byte[] torrent = getTorrent(href);
					vo.setTorrent(torrent);
				} catch (Exception e) {
					vo.setDurl(href);
				}
			}else{
				vo.setDurl(href);
			}
		}
		
		return vo;
	}
	
	public void updateTorr(){
		List<Map<String, Object>> mous = DBUtil.getMapList("select * from mou_res t where t.torrent is null and t.desc is not null");
		if(mous != null){
			List<DataVO> dataVOs = new ArrayList<>();
			for(Map<String, Object> mou:mous){
				DataVO vo = new DataVO();
				vo.setId(((Long)mou.get("ID")).intValue());
				byte[] bs = getTorrent((String) mou.get("DESC"));
				vo.setTorrent(bs);
				dataVOs.add(vo);
			}
			PCDaoUtil.updateMou(dataVOs);
		}
	}

	public byte[] getTorrent(String href) {
		Response response = PCUtil.getResponse(getConnect(href.replace("link", "download")).ignoreContentType(true));
		
		return response.bodyAsBytes();
	}

	private PageInfo from(int curNo) {
		return new PageInfo(curNo,10);
	}

	public String getPageUrl() {
		return URL+type+"/"+this.curPage.getCurPageNo()+".htm";
	}

	public MouPc nextPage() {
		this.curPage.next();
		return this;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
