package com.andy.pc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.andy.core.PCDaoUtil;
import com.andy.core.PCUtil;
import com.andy.core.PageInfo;
import com.andy.core.db.DBUtil;
import com.andy.pc.vo.DataVO;

public class BtttPc implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static final String URL = "http://www.bttiantang.org/index-%s.html";
	private static int count = 0;
	
	private PageInfo curPage;

	public BtttPc(int from) {
		this.curPage = from(from);
	}

	public List<DataVO> getPageData() {
		System.out.println("page:"+curPage.getCurPageNo());
		Document doc=null;
		doc = PCUtil.getDoc(getConnect(getPageUrl()));
		Elements els = doc.select("div.item.cl");
		if(els != null){
			List<DataVO> dataVOs = new ArrayList<>(); 
			for(int i = 0;i<els.size();i++){
				Element item = els.get(i);
				Element a = item.select(".title a").get(0);
				String rt = item.select(".title .rt strong").get(0).text();
				Elements font = a.select("font");
				String title = font.get(0).text();
				
				String href = a.attr("abs:href");
				if(!checkHave(title)){
					DataVO vo = getRecord(href);
					vo.setName(title);
					vo.setRate(StringUtil.isBlank(rt)?null:Double.parseDouble(rt));
					dataVOs.add(vo);
					PCDaoUtil.saveBttt(vo);
				}
//				if(i==2)break;
			}
			return dataVOs;
		}
		return null;
	}

	private boolean checkHave(String title) {
		Long count = DBUtil.getScalar("select 1 from bttt_res where name=?", title);
		if(count != null){
			System.err.println("已经存在："+title);
			return true;
		}
		System.out.println("新增：第"+(++BtttPc.count)+"个--"+title);
		return false;
	}

	private Connection getConnect(String url) {
		return Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36").timeout(20000);
	}

	public DataVO getRecord(String url) {
		DataVO vo = new DataVO();
		Document doc = PCUtil.getDoc(getConnect(url));
		Elements descs = doc.select(".viewbox .sl.cl");
		if(descs != null && !descs.isEmpty()){
			vo.setDesc(descs.get(0).html());
		}
		Element detail = doc.select(".moviedteail .moviedteail_list").get(0);
		vo.setMetadata(detail.html());
		
		Elements as = doc.select("#download_links .dl_item a");
		if(as != null && !as.isEmpty()){
			for(Element a:as){
				String href = a.attr("abs:href");
				if(href != null && href.startsWith("http")){
					try {
						getTorrent(href,vo);
					} catch (Exception e) {
						vo.addDurl(href);
					}
				}
			}
		}
		
		return vo;
	}
	

	private void getTorrent(String href, DataVO vo) {
		
		Document doc = PCUtil.getDoc(getConnect(href));
		Element a = doc.select("table a").get(0);
		if(a != null){
			String ahref = a.attr("href");
			if(ahref != null && ahref.startsWith("http")){
				try {
					byte[] torrent = getTorrent(ahref);
					vo.addTorrent(torrent);
				} catch (Exception e) {
					vo.addDurl(ahref);
				}
			}else{
				vo.addDurl(ahref);
			}
		}
		
	}

	public byte[] getTorrent(String href) {
		Response response = PCUtil.getResponse(getConnect(href).ignoreContentType(true));
		
		return response.bodyAsBytes();
	}

	private PageInfo from(int curNo) {
		return new PageInfo(curNo,10);
	}

	public String getPageUrl() {
		return String.format(URL, this.curPage.getCurPageNo());
	}

	public BtttPc nextPage() {
		this.curPage.next();
		return this;
	}

}
