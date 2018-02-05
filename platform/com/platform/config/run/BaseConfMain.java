/**
 * <p>title:ConfMain.java<／p>
 * <p>Description: <／p>
 * @date:2016年6月21日上午9:45:46
 * @author：ZhongwengHao email:zhongweng.hao@qq.com
 * @version 1.0
 */
package com.platform.config.run;

import org.apache.log4j.Logger;

import com.jfinal.config.Plugins;
import com.platform.config.mapping.BaseMapping;
import com.platform.constant.ConstantInit;
import com.platform.plugin.PropertiesPlugin;
import com.platform.tools.ToolFtpUtil;

/**  
 * 创建时间：2016年6月21日 上午9:45:46  
 * 项目名称：DUCPlatFormWeb   
 * 文件名称：ConfMain.java  
 * 类说明：  
 *
 * Modification History:   
 * Date        Author         Version      Description   
 * ----------------------------------------------------------------- 
 * 2016年6月21日     Zhongweng       1.0         1.0 Version   
 */
/**
 * <p>Title: ConfMain<／p>
 * <p>Description: <／p>
 * @author ZhongwengHao
 * @date 2016年6月21日
 */
public abstract class BaseConfMain {
	private static Logger log = Logger.getLogger(BaseConfMain.class);
	//存放自己的配置文件 集成并重写主配置文件
	public PropertiesPlugin properityMdl = null;
	//数据映射类
	public BaseMapping dbMapping = null;
	
	//ftp工具类
	public ToolFtpUtil ftpTool ;
	/**
	 * <p>Title: setPropertyes<／p>
	 * <p>Description: 设置本地配置参数<／p>
	 * @param pro
	 * @param isComm
	 * @return
	 */
	protected void setPropertyes(PropertiesPlugin pro) {
		properityMdl = pro;
		return ;
	}
	protected PropertiesPlugin getProperty() {
		return properityMdl;
	}
	protected void setDBMapping(BaseMapping adbMapping) {
		dbMapping = adbMapping;
		return;
	}
	protected BaseMapping getDBMapping() {
		return dbMapping;
	}
	protected boolean initProperties() {
		if (null == properityMdl) {
			log.error("null == properityMdl");
			return false;
		}
		//从配置文件读取参数
		properityMdl.start();

		return true;
	}
	protected boolean initDBMapping(Plugins plugins) {
		if (null == dbMapping) {
			return false;
		}
		//初始化Database设置
		dbMapping.start(plugins, properityMdl);
		
		return true;
	}
	
	
	public void initFtp() {
		ftpTool = new ToolFtpUtil();
		ftpTool.init((String)properityMdl.getparamMapMy(ConstantInit.ftp_ip), 
				((Integer) properityMdl.getparamMapMy(ConstantInit.ftp_port)).intValue(),
				(String)properityMdl.getparamMapMy(ConstantInit.ftp_username),
				(String)properityMdl.getparamMapMy(ConstantInit.ftp_pwd),
				(String)properityMdl.getparamMapMy(ConstantInit.ftp_path_remote),
				(String)properityMdl.getparamMapMy(ConstantInit.ftp_path_local));
		
	}
	 
	
}
