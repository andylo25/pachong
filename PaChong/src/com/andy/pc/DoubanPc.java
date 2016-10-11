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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.andy.core.PageInfo;
import com.andy.core.db.DBUtil;
import com.andy.pc.vo.DataVO;

public class DoubanPc implements IPachong{

	private static final String URL = "https://movie.douban.com/j/search_subjects";
	private static int count = 0;
	private static int page_limit = 20;
	
	private String tag;
	
	public DoubanPc(String tag) {
		this.tag = tag;
	}

	@Override
	public List<DataVO> getPage(PageInfo page) {
		Response response=null;
		count = page.getStart();
		try {
			response = getPageConnect(page).execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		JSONObject json = JSON.parseObject(response.body());
		JSONArray subjects = (JSONArray) json.get("subjects");
		
		
		if(subjects != null){
			List<DataVO> dataVOs = new ArrayList<>(); 
			for(Object subject:subjects){
				DataVO vo = buildDataVO((JSONObject)subject);
				if(vo != null && !checkHave(vo.getName())){
					DataVO dataVO = getRecord(vo.getUrl());
					if(dataVO != null){
						vo.setDesc(dataVO.getDesc());
						vo.setMetadata(dataVO.getMetadata());
					}
					
					if(vo.getName() != null){
						byte[] bs = getTorrent(vo.getName());
						vo.setTorrent(bs);
					}
					
					dataVOs.add(vo);
				}
			}
			return dataVOs;
		}
		return null;
	}
	
	private boolean checkHave(String title) {
		Long count = DBUtil.getScalar("select 1 from douban_res where name=?", title);
		if(count != null){
			System.err.println("已经存在："+title);
			return true;
		}
		System.out.println("新增：第"+(DoubanPc.count++)+"个--"+title);
		return false;
	}

	private DataVO buildDataVO(JSONObject subject) {
		DataVO vo = new DataVO();
		vo.setRate(Double.parseDouble(subject.getString("rate")));
		vo.setUrl(subject.getString("url").replace("\\", ""));
		vo.setSubject(Integer.parseInt(subject.getString("id")));
		vo.setName(subject.getString("title"));
		
		return vo;
	}

	@Override
	public List<DataVO> nextPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataVO getRecord(String url) {
		DataVO vo = new DataVO();
		Document doc=null;
		try {
			doc = getSubjectConnect(url).get();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Elements els = doc.select("#info");
		if(els != null && !els.isEmpty()){
			Element content = els.get(0);
			vo.setMetadata(content.html());
		}
		Elements descs = doc.select("#link-report");
		if(descs != null && !descs.isEmpty()){
			Element desc = descs.get(0);
			vo.setDesc(desc.html());
		}
		
		return vo;
	}

	@Override
	public DataVO nextRecord() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getTorrent(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public PageInfo buildPage(int curNo) {
		return new PageInfo(curNo,page_limit);
	}
	
	private Connection getPageConnect(PageInfo page) {
		return Jsoup.connect(URL).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36")
				.header("Upgrade-Insecure-Requests", "1")
				.cookie("pgv_pvi", "6947461120").cookie("gr_user_id", "6ef6cad7-272a-4678-a1eb-e3cf5c36e8cb")
				.cookie("bid", "E0UEYMhYhvA")
				.data("type","movie","tag",this.tag,"sort","recommend")
				.data("page_limit",""+page.getPageSize())
				.data("page_start",""+page.getStart())
				.ignoreContentType(true)
				.timeout(100000);
	}
	
	private Connection getSubjectConnect(String url) {
		return Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36")
				.cookie("pgv_pvi", "6947461120").cookie("gr_user_id", "6ef6cad7-272a-4678-a1eb-e3cf5c36e8cb")
				.cookie("bid", "E0UEYMhYhvA")
				.timeout(100000);
	}
	
}
