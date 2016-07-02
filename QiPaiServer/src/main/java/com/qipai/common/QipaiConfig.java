package com.qipai.common;

import javax.management.RuntimeErrorException;

import com.andy.ext.GuicePlugin;
import com.andy.ext.RouteUtil;
import com.andy.job.TaskManager;
import com.andy.job.TimerManager;
import com.andy.util.CheckSafe;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.json.FastJsonFactory;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.qipai.common.interceptor.AuthInterceptor;
import com.qipai.common.model._MappingKit;
import com.qipai.game.action.GameAction;
import com.qipai.game.task.OneDayTask;
import com.qipai.game.task.RankTask;
import com.qipai.game.task.TaskUtil;
import com.qipai.util.DbBatch;
import com.qipai.util.QPC;

/**
 * API引导式配置
 */
public class QipaiConfig extends JFinalConfig {
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("application.properties");
		me.setDevMode(PropKit.getBoolean("devMode", false));
		me.setViewType(ViewType.JSP);
		me.setVelocityViewExtension("jsp");
		me.setJsonFactory(new FastJsonFactory());
		me.setBaseViewPath("/");
		me.setError404View("/login.jsp");
		me.setJsonDatePattern("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		RouteUtil.initAnnoRoute(me);
//		me.add("/", UserController.class, "/index");	// 第三个参数为该Controller的视图存放路径
//		me.add("/blog", BlogController.class);			// 第三个参数省略时默认与第一个参数值相同，在此即为 "/blog"
		
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		
		if(!CheckSafe.isDev())return;
		// 配置C3p0数据库连接池插件
//		C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
//		me.add(c3p0Plugin);
		
		DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
		me.add(druidPlugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		_MappingKit.mapping(arp);
		me.add(arp);
		
		me.add(new GuicePlugin());
		
		me.add(QipaiCache.instance());
		
		me.add(TaskManager.getInstance());
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.add(new AuthInterceptor());
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		
	}
	
	public void afterJFinalStart(){
		DbBatch.start(2);
		TaskUtil.initTask();
		GameAction.refreshUserOffline();
	}
	
	public void beforeJFinalStop(){
		DbBatch.stopBatch();
		TimerManager.stop();
	}
	
}
