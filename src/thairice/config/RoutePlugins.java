/**
 * <p>title:routePlugins.java<／p>
 * <p>Description: <／p>
 * @date:2016年1月28日下午2:15:23
 * @author：ZhongwengHao email:zhongweng.hao@qq.com
 * @version 1.0
 */
package thairice.config;

import com.jfinal.config.Routes;
/**
 * 创建时间：2016年1月28日 下午2:15:23
 * 项目名称：DUCPlatFromWeb
 * 文件类型：RoutePlugins.java
 * 类说明：
 *
 *  
 *修改日志：
 * Date			Author		Version		Description
 *---------------------------------------------------
 *2016年1月28日		Zhongweng	1.0			1.0Version
 */
import com.platform.mvc.login.LoginController;

import thairice.mvc.pages.pagesController;
import thairice.mvc.r4message_send.R4message_sendController;
import thairice.mvc.sysmonitor.SysmonitorController;
import thairice.mvc.t10pdt_report.T10pdt_reportController;
import thairice.mvc.t11zone.T11zoneController;
import thairice.mvc.t1parameter.T1parameterController;
import thairice.mvc.t2syslog.T2syslogController;
import thairice.mvc.t3user.BackendUserController;
import thairice.mvc.t3user.T3userController;
import thairice.mvc.t5parameter_type.T5parameter_typeController;
import thairice.mvc.t6org_data.T6org_dataController;
import thairice.mvc.t7pdt_data.T7pdt_dataController;
import thairice.mvc.t8message.T8messageController;
import thairice.mvc.t9sample_info.T9sample_infoController;
import thairice.mvc.tkvalue.TkvalueController;

/**
 * <p>
 * Title: RoutePlugins<／p>
 * <p>
 * Description: <／p>
 * 
 * @author ZhongwengHao
 * @date 2016年1月28日
 */
public class RoutePlugins extends Routes {
	@Override
	public void config() {
		add("/jf/thairice/pagesController", pagesController.class);
		add("/jf/thairice/t1parameter", T1parameterController.class);
		add("/jf/thairice/t2syslog", T2syslogController.class);
		add("/jf/thairice/t3user", T3userController.class);
		add("/jf/thairice/r4message_send", R4message_sendController.class);
		add("/jf/thairice/t5parameter_type", T5parameter_typeController.class);
		add("/jf/thairice/t6org_data", T6org_dataController.class);
		add("/jf/thairice/t7pdt_data", T7pdt_dataController.class);
		add("/jf/thairice/t8message", T8messageController.class);
		add("/jf/thairice/t9sample_info", T9sample_infoController.class);
		add("/jf/thairice/t10pdt_report", T10pdt_reportController.class);
		add("/jf/thairice/t11zone", T11zoneController.class);
		add("/jf/thairice/tkvalue", TkvalueController.class);
		add("/jf/targrecog/index/shw", LoginController.class);

		add("/jf/thairice/sysmonitor", SysmonitorController.class);
		// 系统操作员和用户管理
		add("/jf/thairice/admin/user", BackendUserController.class);
	}
}
