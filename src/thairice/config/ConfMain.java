package thairice.config;

import org.apache.log4j.Logger;

import com.jfinal.plugin.activerecord.DbPro;
import com.platform.config.run.BaseConfMain;

import thairice.constant.ConstantInitMy;
import thairice.mvc.t2syslog.T2syslogService;

/**  
 * 创建时间：2016年1月26日 上午11:13:45  
 * 项目名称：DUCPlatFormWeb   
 * 文件名称：ConfMain.java  
 * 类说明：  
 *
 * Modification History:   
 * Date        Author         Version      Description   
 * ----------------------------------------------------------------- 
 * 2016年1月26日     Zhongweng       1.0         1.0 Version   
 */

public class ConfMain extends BaseConfMain{
	private static Logger log = Logger.getLogger(ConfMain.class);
	private final static ConfMain single = new ConfMain();
	
	public static ConfMain getInstance(){
		return single;
	}
	
	public static boolean init(){
		return true;
	}
	public static  boolean start(){
		// 启动日志线程
		T2syslogService.startLogThread();
		return true;
	}
	
	public static  void stop(){
		T2syslogService.setThreadRun(false);
	}
	
	public static DbPro db() {
		return DbPro.use(ConstantInitMy.db_dataSource_main);
	}
}
