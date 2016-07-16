package com.qipai.common.game.comp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.andy.util.CollectionsUtil;
import com.andy.util.CollectionsUtil.Getter;
import com.qipai.common.GlobalVar;
import com.qipai.common.model.Rank;
import com.qipai.common.model.User;
import com.qipai.game.model.GameInfo;
import com.qipai.util.QPC;

public class Resp {
	
	private Map<String,Object> data;
	
	public Resp() {
		this(QPC.ECD_0);
	}
	
	public Resp(int msg){
		data = new HashMap<String, Object>();
		data.put("ecd", msg);
	}
	
	public Object resp(){
		return data;
	}
	
	public Object get(String key){
		return data.get(key);
	}
	public Resp set(String key,Object val){
		data.put(key, val);
		return this;
	}
	public Resp setAll(Map<String,Object>m){
		data.putAll(m);
		return this;
	}
	
	public Resp setUser(User user){
		data.put("user", new RespUser(user));
		return this;
	}
	
	
	public static class RespUser implements Serializable{
		private User user;
		public RespUser(User user) {
			this.user = user;
			if(user == null){
				this.user = new User();
			}
		}
		
		public String getUserName() {
			return user.getUserName();
		}
		public void setUserName(String userName) {
			user.setUserName(userName);
		}
		public Integer getCoin() {
			return user.getCoin();
		}
		public void setCoin(int money) {
			user.setCoin(money);
		}
		public int getScore() {
			return 0;
		}
		public void setScore(int score) {
			
		}
		public String getVip() {
			return user.getIsVip();
		}
		public void setVip(String vip) {
			user.setIsVip(vip);
		}
		public Integer getStatus() {
			return user.getStatus();
		}
		public void setStatus(int status) {
			user.setStatus(status);
		}
		public void setId(int id){
			user.setId(id);
		}
		public int getId(){
			return user.getId();
		}
		public String getNickName(){
			return user.getNickName();
		}
	}

	public static class RespGame{
		
		private User user;

		public RespGame(User user) {
			this.user = user;
		}

		public String getGames(){
			return StringUtils.join(GlobalVar.getGameIds(),",");
		}
		
		public String getPrize(){
			return CollectionsUtil.join(GlobalVar.getGames(),",",new Getter<GameInfo>(){
				@Override
				public String get(GameInfo t) {
					return String.valueOf(t.getTotleCoin());
				}
			});
		}
		
		public String getReward(){
			String rew = "";
			Rank rank = GlobalVar.getRanks().get(user.getId());
			if(rank != null && (rank.getScore() == null || rank.getScore() == 0)){
				rew += "1";//排行榜奖励
			}
			return rew;
		}
	}
	
	
}
