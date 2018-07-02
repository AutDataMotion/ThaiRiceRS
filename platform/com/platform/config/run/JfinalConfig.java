package com.platform.config.run;

import org.apache.log4j.Logger;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.i18n.I18nInterceptor;
import com.jfinal.plugin.activerecord.tx.TxByMethods;
import com.jfinal.plugin.activerecord.tx.TxByRegex;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.platform.beetl.render.MyBeetlRenderFactory;
import com.platform.config.mapping.PlatformMapping;
import com.platform.config.routes.PlatformRoutes;
import com.platform.constant.ConstantCache;
import com.platform.constant.ConstantInit;
import com.platform.handler.GlobalHandler;
import com.platform.interceptor.ParamPkgInterceptor;
import com.platform.plugin.ControllerPlugin;
import com.platform.plugin.FileRenamePlugin;
import com.platform.plugin.I18NPlugin;
import com.platform.plugin.ParamInitPlugin;
import com.platform.plugin.PropertiesPlugin;
import com.platform.plugin.SqlXmlPlugin;
import com.platform.tools.ToolCache;
import com.platform.tools.ToolString;

import thairice.config.ConfMain;
import thairice.config.DBMappingMy;
import thairice.constant.PropertiesInitMy;
import thairice.interceptor.AdminLoginInterceptor;
import thairice.rpcjob.PreProcessScheduleJob;
import zeroc.util.IceClientUtil;


/**
 * Jfinal API 引导式配置
 */
public class JfinalConfig extends JFinalConfig {

	private static Logger log = Logger.getLogger(JfinalConfig.class);

	/**
	 * 系统配置，常量配置
	 */
	public void configConstant(Constants constants) {
		log.info("----------configConstant 缓存 properties");
		// new
		// PropertiesPlugin(loadPropertyFile("init.properties"),true).start();
		// 平台
		com.platform.config.run.ConfMain.getInstance()
				.setPropertyes(new PropertiesPlugin(loadPropertyFile("init.properties"), true));
		com.platform.config.run.ConfMain.getInstance().initProperties();

		// 子系统配置初始化
		thairice.config.ConfMain.getInstance()
				.setPropertyes(new PropertiesInitMy(loadPropertyFile("init_rice.properties"), false));
		thairice.config.ConfMain.getInstance().initProperties();

		log.info("configConstant 设置字符集");
		constants.setEncoding(ToolString.encoding);

		log.info("configConstant 设置是否开发模式");
		constants.setDevMode(getPropertyToBoolean(ConstantInit.config_devMode, true));

		log.info("configConstant 视图Beetl设置");
		constants.setMainRenderFactory(new MyBeetlRenderFactory());
		MyBeetlRenderFactory.regiseter();

		log.info("configConstant 视图error page设置");
		constants.setError404View("/common/404.html");
		constants.setError500View("/common/500.html");

		log.info("configConstant i18n文件前缀设置设置");
		constants.setI18nDefaultBaseName("message");

	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes routes) {
		log.info("configRoute 注解注册路由");
		new ControllerPlugin(routes).start(); // jar注解路由扫描

		log.info("configRoute 手动注册路由");
		routes.add(new PlatformRoutes());

		// 子系统路由
		routes.add(new thairice.config.RoutePlugins());
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins plugins) {
		log.info("注册paltform ActiveRecordPlugin");
		// 数据库层设置初始化 ---平台
		com.platform.config.run.ConfMain.getInstance().setDBMapping(new PlatformMapping());
		com.platform.config.run.ConfMain.getInstance().initDBMapping(plugins);

		// 数据库层设置初始化 ---子系统
		thairice.config.ConfMain.getInstance().setDBMapping(new DBMappingMy());
		thairice.config.ConfMain.getInstance().initDBMapping(plugins);

		log.info("I18NPlugin 国际化键值对加载");
		plugins.add(new I18NPlugin());

		if (ToolCache.getCacheType().equals(ConstantCache.cache_type_ehcache)) {
			log.info("EhCachePlugin EhCache缓存");
			plugins.add(new EhCachePlugin());

		} else if (ToolCache.getCacheType().equals(ConstantCache.cache_type_redis)) {
			log.info("RedisPlugin Redis缓存");
			String redisIp = (String) PropertiesPlugin.getParamMapValue(ConstantInit.config_redis_ip);
			Integer redisPort = (Integer) PropertiesPlugin.getParamMapValue(ConstantInit.config_redis_port);
			RedisPlugin systemRedis = new RedisPlugin(ConstantCache.cache_name_redis_system, redisIp, redisPort);
			plugins.add(systemRedis);
		}

		log.info("SqlXmlPlugin 解析并缓存 xml sql");
		plugins.add(new SqlXmlPlugin());

		log.info("afterJFinalStart 缓存参数");
		plugins.add(new ParamInitPlugin());

		log.info("afterJFinalStart 配置文件上传命名策略插件");
		plugins.add(new FileRenamePlugin());
		// 配置定时任务插件
		Cron4jPlugin cron4 = new Cron4jPlugin("overdueRemind.properties");
		plugins.add(cron4);
		Cron4jPlugin cp = new Cron4jPlugin();
		// 测试时间，实际需调整时间
		  cp.addTask("*/1 * * * *", new PreProcessScheduleJob());
		  plugins.add(cp);
	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors interceptors) {
		// log.info("configInterceptor 支持使用session");
		// me.add(new SessionInViewInterceptor());

		// log.info("configInterceptor 权限认证拦截器");
		// interceptors.add(new AuthInterceptor());

		log.info("configInterceptor 参数封装拦截器");
		interceptors.add(new ParamPkgInterceptor());

		log.info("configInterceptor 配置开启事物规则");
		interceptors.add(new TxByMethods("save", "update", "delete"));
		interceptors.add(new TxByRegex(".*save.*"));
		interceptors.add(new TxByRegex(".*update.*"));
		interceptors.add(new TxByRegex(".*delete.*"));
		log.info("configInterceptor i18n拦截器");
		interceptors.add(new I18nInterceptor());
		
		//全局后台登陆拦截器
		interceptors.add(new AdminLoginInterceptor());
	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers handlers) {
		log.info("configHandler 全局配置处理器，主要是记录日志和request域值处理");
		handlers.add(new GlobalHandler());
	}

	/**
	 * 系统启动完成后执行
	 */
	public void afterJFinalStart() {

		System.out.println("afterJFinalStart");
		// 加载子系统
		ConfMain.init();
		ConfMain.start();

		// Zeroc Ice Util 初始化
		 IceClientUtil.init(60);
		// 初始化ftp地址
		// com.platform.config.run.ConfMain.getInstance().initFtp();
		// targrecog.config.ConfMain.getInstance().initFtp();

		// log.info("afterJFinalStart 启动操作日志入库线程");
		// ThreadSysLog.startSaveDBThread();
		//
		// log.info("afterJFinalStart 系统负载");
		// TimerResources.start();
		//
		// log.info("afterJFinalStart 数据清理");
		// DataClear.start();
		// FtpUtils.initScanFtp();

	}

	/**
	 * 系统关闭前调用
	 */
	public void beforeJFinalStop() {

		// Zeroc Ice Util 销毁
		 IceClientUtil.closeCommunicator(true);
		
		// log.info("beforeJFinalStop 释放日志入库线程");
		// ThreadSysLog.setThreadRun(false);
		//
		// log.info("beforeJFinalStop 释放系统负载抓取线程");
		// TimerResources.stop();
		//
		// log.info("beforeJFinalStop 数据清理");
		// DataClear.stop();
		
		// 释放资源
		ConfMain.stop();
		
	}

	/**
	 * 运行此 main 方法可以启动项目 说明： 1. linux 下非root账户运行端口要>1024 2. idea
	 * 中运行webAppDir路径可能需要适当调整，可以设置为WebContent的绝对路径
	 */
	public static void main(String[] args) {
		JFinal.start("WebContent", 9000, "/", 10);
		// JFinal.start("D:\\DevelopmentTool\\IntelliJIDEA14.1.4" +
		// "\\IdeaProjects\\JFinalUIBV2\\JFinalUIBV2\\WebContent", 99, "/", 5);
	}
}
