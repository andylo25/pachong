package com.andy.job;

/**
 * 被动定时器
 * @author cuiwm
 *
 */
public class PassiveTimer {
	private long createTime;
	private long currenStartTime;
	private long period;
	private boolean isFix;
	private AbsTimerTask task;
	
	private Object mutex = new Object();
	
	public PassiveTimer(AbsTimerTask task,long period) {
		this.period = period;
		this.task = task;
		this.createTime = System.currentTimeMillis();
		reset();
	}
	
	public PassiveTimer(AbsTimerTask task,long period,boolean isFix) {
		this(task,period);
		this.isFix = isFix;
	}
	
	/**
	 * 是否到时间，只返回一次true
	 * @return
	 */
	public boolean check(){
		boolean result = false;
		long l = System.currentTimeMillis();
		synchronized (mutex) {
			if(l >= currenStartTime+period){
				if(isFix){
					currenStartTime += period;
				}else{
					currenStartTime += ((l-currenStartTime)/period)*period;
				}
				result = true;
			}
		}
		if(result){
			task.run();
		}
		return result;
	}
	
	/**
	 * 重置清零
	 */
	public void reset(){
		synchronized (mutex) {
			this.currenStartTime = System.currentTimeMillis();
		}
	}
	
	/**
	 * 获取剩余时间毫秒,负数表示超出
	 * @return
	 */
	public long getRestTime(){
		return currenStartTime+period - System.currentTimeMillis();
	}
	
	public static void main(String[] args) {
		PassiveTimer timer = new PassiveTimer(new AbsTimerTask() {
			@Override
			public void doRun() {
				System.out.println("run...");
			}
		},100);
		try {
			Thread.sleep(280);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(timer.check());
		System.out.println(timer.getRestTime());
		System.out.println(timer.check());
		System.out.println(timer.getRestTime());
		System.out.println(timer.check());
		System.out.println(timer.getRestTime());
	}
	
}
