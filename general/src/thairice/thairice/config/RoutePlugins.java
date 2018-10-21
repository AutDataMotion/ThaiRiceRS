/**
 * <p>title:routePlugins.java<／p>
 * <p>Description: <／p>
 * @date:2016年1月28日下午2:15:23
 * @author：ZhongwengHao email:zhongweng.hao@qq.com
 * @version 1.0
 */
package thairice.config;
import thairice.mvc.t15_news_cnt.t15_news_cntController;
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

/**
 * <p>Title: RoutePlugins<／p>
 * <p>Description: <／p>
 * @author ZhongwengHao
 * @date 2016年1月28日
 */
public class RoutePlugins extends Routes {
	@Override
	public void config() {
		add("/jf/thairice/t15_news_cnt", t15_news_cntController.class);
	}
}
