package com.andy.pc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.andy.core.PageInfo;
import com.andy.core.db.DBUtil;
import com.andy.pc.vo.DataVO;

public class HdwanPc implements IPachong{
	
	private static final String URL = "http://www.hdwan.net/page/";
	private static int count = 0;

	@Override
	public List<DataVO> getPage(PageInfo page) {
		Document doc=null;
		try {
			doc = getConnect(getPageUrl(page.getCurPageNo())).get();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Elements els = doc.select("#post_container > li");
		if(els != null){
			List<DataVO> dataVOs = new ArrayList<>(); 
			for(int i = 0;i<els.size();i++){
				Element li = els.get(i);
				Elements as = li.select(".article a");
				for(Element a:as){
					String href = a.attr("href");
					String title = a.attr("title");
					if(!checkHave(title)){
						DataVO vo = getRecord(href);
						vo.setName(title);
						dataVOs.add(vo);
					}
				}
//				if(i==2)break;
			}
			return dataVOs;
		}
		return null;
	}

	private boolean checkHave(String title) {
		Long count = DBUtil.getScalar("select 1 from hdwan_res where name=?", title);
		if(count != null){
			System.err.println("已经存在："+title);
			return true;
		}
		System.out.println("新增：第"+(++HdwanPc.count)+"个--"+title);
		return false;
	}

	private Connection getConnect(String url) {
		return Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36").timeout(100000);
	}

	@Override
	public DataVO getRecord(String url) {
		DataVO vo = new DataVO();
		Document doc=null;
		try {
			doc = getConnect(url).get();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Elements els = doc.select("#post_content");
		if(els != null && !els.isEmpty()){
			Element content = els.get(0);
			vo.setMetadata(content.html());
			Elements as = content.child(0).lastElementSibling().select("a");
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
		}
		
		return vo;
	}

	@Override
	public byte[] getTorrent(String href) {
		Response response=null;
		try {
			response = getConnect(href).ignoreContentType(true).execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return response.bodyAsBytes();
	}

	public PageInfo buildPage(int curNo) {
		return new PageInfo(curNo,10);
	}

	public String getPageUrl(int pageNo) {
		return URL+pageNo;
	}

	@Override
	public List<DataVO> nextPage() {
		return null;
	}

	@Override
	public DataVO nextRecord() {
		return null;
	}

}
