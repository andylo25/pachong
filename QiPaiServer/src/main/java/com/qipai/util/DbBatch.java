package com.qipai.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import com.andy.util.FileUtil;
import com.andy.util.UtilDatetime;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.qipai.common.QipaiCache;

/**
 * 批量更新数据库
 * @author Administrator
 *
 */
public class DbBatch{

	private static Logger logger = Logger.getLogger(DbBatch.class);
	
	private static List<DbBatch> dbBatchs = new ArrayList<DbBatch>();
	private static final int batchSize = 100;
	
	private List<Model> modelSaveList = new CopyOnWriteArrayList<Model>();
	private List<Model> modelUpdateList = new CopyOnWriteArrayList<Model>();
	
	public static void up(Model<? extends Model> m){
		DbBatch batch = dbBatchs.get(Math.abs(m.hashCode())%dbBatchs.size());
		if(batch.thread.isAlive()){
			synchronized (batch.modelUpdateList) {
				if(!batch.modelUpdateList.contains(m)){
					batch.modelUpdateList.add(m);
				}
			}
		}else{
			batch.stop();
			batch.start();
		}
	}
	
	public static void sv(Model<? extends Model> m){
		DbBatch batch = dbBatchs.get(Math.abs(m.hashCode())%dbBatchs.size());
		if(batch.thread.isAlive()){
			synchronized (batch.modelSaveList) {
				batch.modelSaveList.add(m);
			}
		}else{
			batch.stop();
			batch.start();
		}
	}
	
	private DbBatch(int i) {start();this.index = i;}
	
	private void saveBatch(){
		if(!modelSaveList.isEmpty()){
			List<Model> modelListT = new ArrayList<Model>();
			synchronized (modelSaveList) {
				modelListT.addAll(modelSaveList);
				modelSaveList.clear();
			}
			try {
				Db.batchSave(modelListT, modelListT.size());
			} catch (Exception e) {
				logger.error("",e);
				saveException(modelListT,"saving");
			}
			logger.error("保存数据："+modelListT.size());
		}
		
	}
	
	private void updateBatch(){
		if(!modelUpdateList.isEmpty()){
			List<Model> modelListT = new ArrayList<Model>();
			synchronized (modelUpdateList) {
				modelListT.addAll(modelUpdateList);
				modelUpdateList.clear();
			}
			try {
				Db.batchUpdate(modelListT,modelListT.size());
			} catch (Exception e) {
				logger.error("",e);
				saveException(modelListT,"updating");
			}
			logger.error("更新数据："+modelListT.size());
		}
	}
	
	private void updateTax(){
		QipaiCache.getTax(QPC.GAME_100).updateCoinPoo();
		QipaiCache.getTax(QPC.GAME_101).updateCoinPoo();
	}
	
	private void saveException(List<Model> modelListT, String path) {
		if(modelListT.isEmpty())return;
		StringBuilder sb = new StringBuilder();
		for(Model mod:modelListT){
			sb.append(mod.toJson()).append("|@|");
		}
		try {
			FileUtil.saveToFile("/backdata/"+path+"/"+UtilDatetime.getDatetimeFromText(new Date()), sb.toString().getBytes());
		} catch (IOException e1) {
			logger.error("没有数据文件夹："+sb.toString(),e1);
		}
	}

	private int index;
	private boolean run;
	private Thread thread;
	private boolean start() {
		run = true;
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(run){
					try {
						saveBatch();
						updateBatch();
						updateTax();
					} catch (Exception e) {
						logger.error(e.getMessage(),e);
					}
					try {
						Thread.currentThread().sleep(3000);
					} catch (InterruptedException e) {
						logger.error(e.getMessage(),e);
					}
				}
			}
		});
		thread.setName("DbBatch"+index);
		thread.setDaemon(true);
		thread.start();
		return true;
	}

	public boolean stop() {
		run = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
		}
		saveException(modelSaveList,"saving");
		saveException(modelUpdateList,"updating");
		thread = null;
		return true;
	}
	
	public static void stopBatch(){
		logger.error("开始停止数据落地线程");
		for(DbBatch dbBatch:dbBatchs){
			dbBatch.stop();
		}
	}
	
	/**
	 * 启动线程
	 * @param num
	 */
	public static void start(int num) {
		for(int i=0;i<num;i++){
			dbBatchs.add(new DbBatch(i));
		}
	}
	
	
	
	
}
