/**
 * <p>title:ConfMain.java<／p>
 * <p>Description: <／p>
 * @date:2016年6月21日上午9:52:27
 * @author：ZhongwengHao email:zhongweng.hao@qq.com
 * @version 1.0
 */
package com.platform.config.run;

import org.apache.log4j.Logger;

import com.platform.tools.ToolFtpUtil;

/**  
 * 创建时间：2016年6月21日 上午9:52:27  
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
public class ConfMain extends BaseConfMain{
	private static Logger log = Logger.getLogger(ConfMain.class);
	private final static ConfMain single = new ConfMain();
	
	
	public static ConfMain getInstance(){
		return single;
	}

}
