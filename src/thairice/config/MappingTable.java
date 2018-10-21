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
import thairice.mvc.t12preprocessinf.T12PreProcessInf;
import thairice.mvc.t13region.T13Region;
import thairice.mvc.t14my_region.t14my_region;
import thairice.mvc.t15_news_cnt.t15_news_cnt;
import thairice.mvc.tkvalue.Tkvalue;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
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
 * @author ZhongwengHao
 * @date 2016年3月12日
 */
public class MappingTable {

	private static Logger log = Logger.getLogger(MappingTable.class);
	public static void mapping(ActiveRecordPlugin arp){
		log.info("thairice MappingTable 表手工注册-----begin");
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
		arp.addMapping("t12_pre_process_inf", "id", T12PreProcessInf.class);
		arp.addMapping("t13region", "id", T13Region.class);
		arp.addMapping("t14my_region", "id", t14my_region.class);
		arp.addMapping("t15_news_cnt", "newsid", t15_news_cnt.class);
		log.info("thairice MappingTable 表手工注册-----end");
		
	}
}
