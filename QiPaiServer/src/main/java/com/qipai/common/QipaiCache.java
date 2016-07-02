package com.qipai.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.jfinal.plugin.IPlugin;
import com.qipai.common.model.Game;
import com.qipai.common.model.Notice;
import com.qipai.common.model.Reward;
import com.qipai.common.model.Sysconf;
import com.qipai.common.model.Tax;
import com.qipai.game.model.GameInfo;
import com.qipai.game.model.GameUser;
import com.qipai.util.QPC;

public class QipaiCache implements IPlugin,Serializable{
	
	private Logger logger = Logger.getLogger(getClass());

	private static QipaiCache qipaiCache = new QipaiCache();
	
	// sysConf
	private Map<String, String> sysConfCache = new HashMap<String, String>();
	// game
	private Map<Integer, Game> gameCache = new HashMap<Integer, Game>();
	// gameId:Tax
	private Map<Integer, Tax> taxCache = new HashMap<Integer, Tax>();
	// rewardId:Reward
	private Map<Integer, Reward> rewardCache = new HashMap<Integer, Reward>();
	
	private String NOTIC_CONTENT = "";
	
	public static QipaiCache instance(){
		return qipaiCache;
	}
	private QipaiCache() {}
	
	@Override
	public boolean start() {
		// 加载系统配置
		List<Sysconf> sysconfs = Sysconf.dao.find("select * from sysconf");
		if(sysconfs != null){
			for(Sysconf conf:sysconfs){
				sysConfCache.put(conf.getKey(), conf.getVal());
				initConstants(conf);
			}
		}
		
		// 加载收税配置,需要优先游戏信息*
		List<Tax> taxs = Tax.dao.find("select * from Tax");
		if(taxs != null){
			for(Tax t:taxs){
				taxCache.put(t.getGameId(),t.setTaxCoin(t.getCoinPoo()));
			}
		}
		
		// 游戏信息
		List<Game> games = Game.dao.find("select * from game");
		if(games != null){
			for(Game g:games){
				gameCache.put(g.getId(),g);
				GlobalVar.addGame(new GameInfo(g));
			}
		}
		
		// 公告信息
		Notice notice = Notice.dao.findFirst("select * from Notice where flag=0 limit 0,1");
		if(notice != null){
			NOTIC_CONTENT = notice.getNotice();
		}
		
		// 奖励配置
		List<Reward> rewards = Reward.dao.find("select * from Reward");
		if(rewards != null){
			for(Reward r:rewards){
				rewardCache.put(r.getRewardId(),r);
			}
		}
		
		GlobalVar.getRespRanks();
		
//		startThread();
		
		return true;
	}

	// 常量初始化
	private void initConstants(Sysconf conf) {
		if(conf.getKey().equals("fanpai_doub_times")){
			QPC.fanpai_doub_times = Integer.valueOf(conf.getVal());
		}
	}

	private boolean run;
	private void startThread() {
		run = false;
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(run){
					try {
						refresh();
						Thread.currentThread().sleep(1000*60);
					} catch (Exception e) {
						logger.error(e.getMessage(),e);
					}
				}
			}
		});
		thread.setName("QipaiCache");
		thread.setDaemon(true);
		thread.start();
	}
	
	private void refresh() {
		
	}
	
	@Override
	public boolean stop() {
		sysConfCache.clear();
		gameCache.clear();
		taxCache.clear();
		run = false;
		return true;
	}
	
	public static String getConfVal(String key){
		return qipaiCache.sysConfCache.get(key);
	}
	public static String[] getConfArray(String key){
		String value = getConfVal(key);
		if(value != null){
			return value.split(",");
		}
		return null;
	}
	
	public static int getConfInt(String key){
		return Integer.parseInt(qipaiCache.sysConfCache.get(key));
	}
	
	public static Game getGame(int gameId){
		return qipaiCache.gameCache.get(gameId);
	}
	
	public static Tax getTax(int gameId){
		return qipaiCache.taxCache.get(gameId);
	}
	public static Reward getReward(int rewardId){
		return qipaiCache.rewardCache.get(rewardId);
	}
	
	public static String getNotic(){
		return qipaiCache.NOTIC_CONTENT;
	}
	public static void upNotic(String content){
		qipaiCache.NOTIC_CONTENT = content;
	}
}
