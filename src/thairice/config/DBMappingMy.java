package thairice.config;

import org.apache.log4j.Logger;

import thairice.autotask.autoInitFtpScan;
import thairice.constant.ConstantInitMy;
import thairice.utils.FtpUtils;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Plugins;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.platform.config.mapping.BaseMapping;
import com.platform.constant.ConstantInit;
import com.platform.plugin.PropertiesPlugin;

/**  
 * 创建时间：2016年6月21日 下午9:00:11  
 * 项目名称：DUCPlatFormWeb   
 * 文件名称：DBMappingMy.java  
 * 类说明：  
 *
 * Modification History:   
 * Date        Author         Version      Description   
 * ----------------------------------------------------------------- 
 * 2016年6月21日     Zhongweng       1.0         1.0 Version   
 */

public class DBMappingMy extends BaseMapping{
	private static Logger log = Logger.getLogger(DBMappingMy.class);
	
	public DBMappingMy(){}
	/* (non-Javadoc)
	 * <p>Description: <／p>
	 * @param plugins
	 * @param aproperty
	 * @see com.platform.config.mapping.BaseMapping#start(com.jfinal.config.Plugins, com.platform.plugin.PropertiesPlugin)
	 */
	@Override
	public void start(Plugins plugins, PropertiesPlugin aproperity) {
		// TODO Auto-generated method stub
		log.info("-------targetMapping-------configPlugin 配置Druid数据库连接池连接属性");
		log.info("------db_connection_jdbcUrl-------"+(String)aproperity.getparamMapMy(ConstantInit.db_connection_jdbcUrl));
		DruidPlugin druidPlugin = new DruidPlugin(
				(String)aproperity.getparamMapMy(ConstantInit.db_connection_jdbcUrl), 
				(String)aproperity.getparamMapMy(ConstantInit.db_connection_userName), 
				(String)aproperity.getparamMapMy(ConstantInit.db_connection_passWord), 
				(String)aproperity.getparamMapMy(ConstantInit.db_connection_driverClass));

		log.info("configPlugin 配置Druid数据库连接池大小");
		druidPlugin.set(
				(Integer)aproperity.getparamMapMy(ConstantInit.db_initialSize), 
				(Integer)aproperity.getparamMapMy(ConstantInit.db_minIdle), 
				(Integer)aproperity.getparamMapMy(ConstantInit.db_maxActive));		
		log.info("configPlugin 配置Druid数据库连接池过滤器配制");
		druidPlugin.addFilter(new StatFilter());
		WallFilter wall = new WallFilter();
		wall.setDbType((String) aproperity.getparamMapMy(ConstantInit.db_type_key));
		WallConfig config = new WallConfig();
		config.setFunctionCheck(false); // 支持数据库函数
		wall.setConfig(config);
		druidPlugin.addFilter(wall);
		
		log.info("configPlugin 配置ActiveRecordPlugin插件");
		configName = ConstantInitMy.db_dataSource_main;
		arp = new ActiveRecordPlugin(configName, druidPlugin);
		//arp.setTransactionLevel(4);//事务隔离级别
		boolean devMode = Boolean.parseBoolean((String) aproperity.getparamMapMy(ConstantInit.config_devMode));
		arp.setDevMode(devMode); // 设置开发模式
		arp.setShowSql(devMode); // 是否显示SQL
		arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));// 大小写不敏感
		
		log.info("configPlugin 数据库类型判断");
		String db_type = (String) aproperity.getparamMapMy(ConstantInit.db_type_key);
		if(db_type.equals(ConstantInit.db_type_postgresql)){
			log.info("configPlugin 使用数据库类型是 postgresql");
			arp.setDialect(new PostgreSqlDialect());
			
		}else if(db_type.equals(ConstantInit.db_type_mysql)){
			log.info("configPlugin 使用数据库类型是 mysql");
			arp.setDialect(new MysqlDialect());
			
		}else if(db_type.equals(ConstantInit.db_type_oracle)){
			log.info("configPlugin 使用数据库类型是 oracle");
			druidPlugin.setValidationQuery("select 1 FROM DUAL"); //连接验证语句
			arp.setDialect(new OracleDialect());
		}

		log.info("configPlugin 添加druidPlugin插件");
		plugins.add(druidPlugin); // 多数据源继续添加

		log.info("configPlugin 表自动扫描注册");
		scan(aproperity);

		log.info("configPlugin 表手工注册");

		//数据库表映射
		thairice.config.MappingTable.mapping(arp);
		log.info("configPlugin 注册ActiveRecordPlugin插件");
		plugins.add(arp);
		
		// 添加任务调度插件
		// 定时扫描初始化ftp文件
//		FtpUtils.initScanFtp();
//		plugins.add(new Cron4jPlugin(PropKit.use("autoInitFtpScan.properties")));
//		// 定时扫描检测远程ftp文件
//		plugins.add(new Cron4jPlugin(PropKit.use("autoRemoteFtpScan.properties")));
//		// 定时自动扫描待下载文件并启动ftp下载
//		plugins.add(new Cron4jPlugin(PropKit.use("autoFtpDownload.properties")));
	}
}
