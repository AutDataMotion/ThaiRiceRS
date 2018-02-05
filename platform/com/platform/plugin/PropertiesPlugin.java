package com.platform.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.jfinal.kit.PathKit;
import com.jfinal.log.Logger;
import com.jfinal.plugin.IPlugin;
import com.platform.constant.ConstantInit;

/**
 * 读取Properties配置文件数据放入缓存
 * 
 * @author DHJ
 */
public class PropertiesPlugin implements IPlugin {

	protected final static Logger log = Logger.getLogger(PropertiesPlugin.class);

	/**
	 * 保存系统配置参数值
	 */
	private static Map<String, Object> paramMap = new HashMap<String, Object>();

	protected Properties properties;

	protected Map<String, Object> paramMapMy = new HashMap<String, Object>();
	protected boolean isComm;// 是否为公共参数
	private static boolean isInitComm = false;

	/**
	 * 初始化主配置文件
	 * 
	 * @param properties
	 */
	public PropertiesPlugin(Properties properties, boolean aisComm) {
		isComm = aisComm;
		this.properties = properties;
	}

	protected static boolean isCommnInit() {
		return isInitComm;
	}

	/**
	 * 该方法获取系统 公共配置参数
	 * 
	 * @param key
	 * @return
	 */
	public static Object getParamMapValue(String key) {
		return paramMap.get(key);
	}
	
	/**
	 * <p>
	 * Title:getparamMapMy<／p>
	 * <p>
	 * Description: 获得个性化设置参数<／p>
	 * 
	 * @param key
	 * @return
	 */
	public Object getparamMapMy(String key)  {
		if (paramMapMy.containsKey(key)) {
			// 先返回个性设置 如果有的话
			return paramMapMy.get(key);
		}
		// 否则去公共找
		return PropertiesPlugin.getParamMapValue(key);
	}
	
	protected void initDataBasePar(Map<String, Object> paramMap) {
		String db_type = properties.getProperty(ConstantInit.db_type_key);
		if (null == db_type) {
			// 没有第一条 则认为数据库采用主库的
			return;
		}
		paramMap.put(ConstantInit.db_type_key, db_type.trim());

		// 判断数据库类型
		if (db_type.equals(ConstantInit.db_type_postgresql)) { // pg 数据库连接信息
			// 读取当前配置数据库连接信息
			paramMap.put(ConstantInit.db_connection_driverClass, properties
					.getProperty("postgresql.driverClass").trim());
			paramMap.put(ConstantInit.db_connection_jdbcUrl, properties
					.getProperty("postgresql.jdbcUrl").trim());
			paramMap.put(ConstantInit.db_connection_userName, properties
					.getProperty("postgresql.userName").trim());
			paramMap.put(ConstantInit.db_connection_passWord, properties
					.getProperty("postgresql.passWord").trim());

			// 解析数据库连接URL，获取数据库名称
			String jdbcUrl = (String) getParamMapValue(ConstantInit.db_connection_jdbcUrl);
			String database = jdbcUrl.substring(jdbcUrl.indexOf("//") + 2);
			database = database.substring(database.indexOf("/") + 1);

			// 解析数据库连接URL，获取数据库地址IP
			String ip = jdbcUrl.substring(jdbcUrl.indexOf("//") + 2);
			ip = ip.substring(0, ip.indexOf(":"));

			// 解析数据库连接URL，获取数据库地址端口
			String port = jdbcUrl.substring(jdbcUrl.indexOf("//") + 2);
			port = port.substring(port.indexOf(":") + 1, port.indexOf("/"));

			// 把数据库连接信息写入常用map
			paramMap.put(ConstantInit.db_connection_ip, ip);
			paramMap.put(ConstantInit.db_connection_port, port);
			paramMap.put(ConstantInit.db_connection_dbName, database);

		} else if (db_type.equals(ConstantInit.db_type_mysql)) { // mysql
																	// 数据库连接信息
			// 读取当前配置数据库连接信息
			paramMap.put(ConstantInit.db_connection_driverClass,
					"com.mysql.jdbc.Driver");
			paramMap.put(ConstantInit.db_connection_jdbcUrl, properties
					.getProperty("mysql.jdbcUrl").trim());
			paramMap.put(ConstantInit.db_connection_userName, properties
					.getProperty("mysql.userName").trim());
			paramMap.put(ConstantInit.db_connection_passWord, properties
					.getProperty("mysql.passWord").trim());

			// 解析数据库连接URL，获取数据库名称
			String jdbcUrl = (String) getParamMapValue(ConstantInit.db_connection_jdbcUrl);
			String database = jdbcUrl.substring(jdbcUrl.indexOf("//") + 2);
			database = database.substring(database.indexOf("/") + 1,
					database.indexOf("?"));

			// 解析数据库连接URL，获取数据库地址IP
			String ip = jdbcUrl.substring(jdbcUrl.indexOf("//") + 2);
			ip = ip.substring(0, ip.indexOf(":"));

			// 解析数据库连接URL，获取数据库地址端口
			String port = jdbcUrl.substring(jdbcUrl.indexOf("//") + 2);
			port = port.substring(port.indexOf(":") + 1, port.indexOf("/"));

			// 把数据库连接信息写入常用map
			paramMap.put(ConstantInit.db_connection_ip, ip);
			paramMap.put(ConstantInit.db_connection_port, port);
			paramMap.put(ConstantInit.db_connection_dbName, database);

		} else if (db_type.equals(ConstantInit.db_type_oracle)) { // oracle
																	// 数据库连接信息
			paramMap.put(ConstantInit.db_connection_driverClass, properties
					.getProperty("oracle.driverClass").trim());
			paramMap.put(ConstantInit.db_connection_jdbcUrl, properties
					.getProperty("oracle.jdbcUrl").trim());
			paramMap.put(ConstantInit.db_connection_userName, properties
					.getProperty("oracle.userName").trim());
			paramMap.put(ConstantInit.db_connection_passWord, properties
					.getProperty("oracle.passWord").trim());

			// 解析数据库连接URL，获取数据库名称
			String jdbcUrl = (String) getParamMapValue(ConstantInit.db_connection_jdbcUrl);
			String[] prop = jdbcUrl.substring(jdbcUrl.indexOf("@") + 1).split(
					":");
			String database = prop[2];

			// 解析数据库连接URL，获取数据库地址IP
			String ip = prop[0];

			// 解析数据库连接URL，获取数据库地址端口
			String port = prop[1];

			// 把数据库连接信息写入常用map
			paramMap.put(ConstantInit.db_connection_ip, ip);
			paramMap.put(ConstantInit.db_connection_port, port);
			paramMap.put(ConstantInit.db_connection_dbName, database);
		}

		// 数据库连接池信息
		paramMap.put(
				ConstantInit.db_initialSize,
				Integer.valueOf(properties.getProperty(
						ConstantInit.db_initialSize).trim()));
		paramMap.put(ConstantInit.db_minIdle, Integer.valueOf(properties
				.getProperty(ConstantInit.db_minIdle).trim()));
		paramMap.put(
				ConstantInit.db_maxActive,
				Integer.valueOf(properties.getProperty(
						ConstantInit.db_maxActive).trim()));

	}

	public void initScanPackagePar(Map<String, Object> paramMap) {
		// 把常用配置信息写入map
		String scan_package = properties.getProperty(
				ConstantInit.config_scan_package).trim();
		if (null != scan_package && !scan_package.isEmpty()) {
			List<String> list = new ArrayList<String>();
			String[] pkgs = scan_package.split(",");
			for (String pkg : pkgs) {
				list.add(pkg.trim());
			}
			paramMap.put(ConstantInit.config_scan_package, list);
		} else {
			paramMap.put(ConstantInit.config_scan_package,
					new ArrayList<String>());
		}

	}

	public void initScanJarPar(Map<String, Object> paramMap) {
		String scan_jar = properties.getProperty(ConstantInit.config_scan_jar)
				.trim();
		if (null != scan_jar && !scan_jar.isEmpty()) {
			List<String> list = new ArrayList<String>();
			String[] jars = scan_jar.split(",");
			for (String jar : jars) {
				list.add(jar.trim());
			}
			paramMap.put(ConstantInit.config_scan_jar, list);
		} else {
			paramMap.put(ConstantInit.config_scan_jar, new ArrayList<String>());
		}
	}

	public void  initMail(Map<String, Object> paramMap) {
		String value = properties.getProperty(ConstantInit.config_mail_host);
		if (null == value || value.isEmpty()) {
			return ;
		}
		paramMap.put(ConstantInit.config_mail_host,
				properties.getProperty(ConstantInit.config_mail_host).trim());
		paramMap.put(ConstantInit.config_mail_port,
				properties.getProperty(ConstantInit.config_mail_port).trim());
		paramMap.put(ConstantInit.config_mail_from,
				properties.getProperty(ConstantInit.config_mail_from).trim());
		paramMap.put(ConstantInit.config_mail_userName,
				properties.getProperty(ConstantInit.config_mail_userName)
						.trim());
		paramMap.put(ConstantInit.config_mail_password,
				properties.getProperty(ConstantInit.config_mail_password)
						.trim());
		paramMap.put(ConstantInit.config_mail_to,
				properties.getProperty(ConstantInit.config_mail_to).trim());
	}
	
	protected void initRedisPra(Map<String, Object> paramMap) {
		String value = properties.getProperty(ConstantInit.config_redis_ip);
		if (null == value || value.isEmpty()) {
			return ;
		}
		paramMap.put(ConstantInit.config_redis_ip,
				properties.getProperty(ConstantInit.config_redis_ip).trim());
		paramMap.put(
				ConstantInit.config_redis_port,
				Integer.valueOf(properties.getProperty(
						ConstantInit.config_redis_port).trim()));
	}
	
	protected void iniFtpPra(Map<String, Object> paramMap) {
		String value = properties.getProperty(ConstantInit.ftp_ip);
		if (null == value || value.isEmpty()) {
			return ;
		}
		String pathRoot = PathKit.getWebRootPath();
		paramMap.put(ConstantInit.ftp_ip,
				properties.getProperty(ConstantInit.ftp_ip).trim());
		paramMap.put(ConstantInit.ftp_port, Integer.valueOf(properties
				.getProperty(ConstantInit.ftp_port).trim()));
		paramMap.put(ConstantInit.ftp_username,
				properties.getProperty(ConstantInit.ftp_username).trim());
		paramMap.put(ConstantInit.ftp_pwd,
				properties.getProperty(ConstantInit.ftp_pwd).trim());
		paramMap.put(ConstantInit.ftp_path_remote,
				properties.getProperty(ConstantInit.ftp_path_remote).trim());
		paramMap.put(ConstantInit.ftp_path_local, pathRoot
				+ properties.getProperty(ConstantInit.ftp_path_local).trim());
	}
	
	protected boolean putParamMap(Map<String, Object> paramMap, String paramName) {
		String paramValue = properties.getProperty(paramName);
		if (null == paramValue || paramValue.isEmpty()) {
			log.warn("null == paramValue || paramValue.isEmpty():---"+paramName);
			return false;
		}
		paramMap.put(paramName, paramValue.trim());
		return true;
	}
	
	protected boolean putParamMapInteger(Map<String, Object> paramMap, String paramName) {
		String paramValue = properties.getProperty(paramName);
		if (null == paramValue || paramValue.isEmpty()) {
			log.warn("null == paramValue || paramValue.isEmpty():---"+paramName);
			return false;
		}
		paramMap.put(paramName, Integer.valueOf(paramValue.trim()));
		return true;
	}
	
	@Override
	public boolean start() {
		log.info("----Common properties start ");
		if (!isComm) {
			log.warn("properties not common,ignore");
			return true;
		}
		isInitComm = true;
		//数据库
		initDataBasePar(paramMap);
		//扫描package
		initScanPackagePar(paramMap);
		//扫描jar
		initScanJarPar(paramMap);
		
		putParamMap(paramMap, ConstantInit.config_devMode);
		putParamMap(paramMap, ConstantInit.config_luceneIndex);		
		putParamMap(paramMap, ConstantInit.config_securityKey_key);	
		putParamMapInteger(paramMap, ConstantInit.config_passErrorCount_key);
		putParamMapInteger(paramMap, ConstantInit.config_passErrorHour_key);
		putParamMapInteger(paramMap, ConstantInit.config_maxPostSize_key);
		putParamMapInteger(paramMap, ConstantInit.config_maxAge_key);
		putParamMapInteger(paramMap, ConstantInit.config_session_key);
		putParamMap(paramMap, ConstantInit.config_domain_key);
	
		// mail 配置
		initMail(paramMap);

		// 缓存类型配置
		putParamMap(paramMap, ConstantInit.config_cache_type);

		// redis 配置
		initRedisPra(paramMap);

		// ftp配置
		iniFtpPra(paramMap);
		// 图片上传路径
		String pathRoot = PathKit.getWebRootPath();
		paramMap.put(ConstantInit.upload_img_path, pathRoot
				+ properties.getProperty(ConstantInit.upload_img_path).trim());
		
		printProperty(paramMap);
		return true;
	}

	public static void printProperty(Map<String, Object> map){

		for (String key : map.keySet()) {
			log.info("---common全局参数配置：" + key + " = " + map.get(key));
		}
	}
	

	@Override
	public boolean stop() {
		paramMap.clear();
		paramMapMy.clear();
		isInitComm = false;
		return true;
	}

}
