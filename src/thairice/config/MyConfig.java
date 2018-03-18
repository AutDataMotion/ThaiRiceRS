/**
 * <p>title:MappingTable.java<／p>
 * <p>Description: <／p>
 * @date:2016年3月12日上午10:11:42
 * @author：ZhongwengHao email:zhongweng.hao@qq.com
 * @version 1.0
 */
package thairice.config;

import org.apache.log4j.Logger;
import thairice.mvc.t1parameter.T1parameter;
import thairice.mvc.t2syslog.T2syslog;
import thairice.mvc.t3user.T3user;
import thairice.mvc.r4message_send.R4message_send;
import thairice.mvc.t5parameter_type.T5parameter_type;
import thairice.mvc.t6org_data.T6org_data;
import thairice.mvc.t7pdt_data.T7pdt_data;
import thairice.mvc.t8message.T8message;
import thairice.mvc.t9sample_info.T9sample_info;
import thairice.mvc.t10pdt_report.T10pdt_report;
import thairice.mvc.t11zone.T11zone;
import thairice.mvc.tkvalue.Tkvalue;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
/**
 * 创建时间：2016年3月12日 上午10:11:42
 * 项目名称：
 * 文件类型：MappingTable.java
 * 类说明：
 *
 *  
 *修改日志：
 * Date			Author		Version		Description
 *---------------------------------------------------
 *2016年3月12日		Zhongweng	1.0			1.0Version
 */

/**
 * <p>Title: MappingTable<／p>
 * <p>Description: <／p>
 * @author zhuchaobin
 * @date 2018年2月24日
 */
public class MyConfig extends JFinalConfig {
	@Override
		public void configPlugin(Plugins me) {
		C3p0Plugin cp = new C3p0Plugin("jdbc:mysql://127.0.0.1:3306/thairice?characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&zeroDateTimeBehavior=convertToNull",
		"postgres", "123456");
		me.add(cp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		me.add(arp);
		arp.addMapping("t1parameter", "id", T1parameter.class);
		arp.addMapping("t2syslog", "id", T2syslog.class);
		arp.addMapping("t3user", "id", T3user.class);
		arp.addMapping("r4message_send", "id", R4message_send.class);
		arp.addMapping("t5parameter_type", "id", T5parameter_type.class);
		arp.addMapping("t6org_data", "id", T6org_data.class);
		arp.addMapping("t7pdt_data", "id", T7pdt_data.class);
		arp.addMapping("t8message", "id", T8message.class);
		arp.addMapping("t9sample_info", "id", T9sample_info.class);
		arp.addMapping("t10pdt_report", "id", T10pdt_report.class);
		arp.addMapping("t11zone", "id", T11zone.class);
		arp.addMapping("tkvalue", "id", Tkvalue.class);	
		System.out.println("test");
	}
	@Override
	public void configConstant(Constants me) {
		// TODO Auto-generated method stub
		me.setDevMode(true);
	}
	@Override
	public void configRoute(Routes me) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 * 
	 * 使用本方法启动过第一次以后，会在开发工具的 debug、run config 中自动生成
	 * 一条启动配置，可对该自动生成的配置再添加额外的配置项，例如 VM argument 可配置为：
	 * -XX:PermSize=64M -XX:MaxPermSize=256M
	 */
	public static void main(String[] args) {
		/**
		 * 特别注意：Eclipse 之下建议的启动方式
		 */
//		JFinal.start("WebRoot", 80, "/", 5);

		/**
		 * 特别注意：IDEA 之下建议的启动方式，仅比 eclipse 之下少了最后一个参数
		 */
		 JFinal.start("WebRoot", 80, "/", 5);
//		MailKit.send("nanjing2007@163.com",Arrays.asList("抄送1","抄送2"), "测试标题", "测试邮件内容");
	}
	
}
