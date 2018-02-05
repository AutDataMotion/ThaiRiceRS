/**
 * <p>title:MappingTable.java<／p>
 * <p>Description: <／p>
 * @date:2016年3月12日上午10:36:54
 * @author：ZhongwengHao email:zhongweng.hao@qq.com
 * @version 1.0
 */
package com.platform.config.mapping;

import org.apache.log4j.Logger;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
/**
 * 创建时间：2016年3月12日 上午10:36:54
 * 项目名称：DUCPlatFromWeb
 * 文件类型：MappingTable.java
 * 类说明：
 *
 *  
 *修改日志：
 * Date			Author		Version		Description
 *---------------------------------------------------
 *2016年3月12日		Zhongweng	1.0			1.0Version
 */
import com.platform.mvc.dept.Department;
import com.platform.mvc.dict.Dict;
import com.platform.mvc.group.Group;
import com.platform.mvc.menu.Menu;
import com.platform.mvc.module.Module;
import com.platform.mvc.operator.Operator;
import com.platform.mvc.param.Param;
import com.platform.mvc.resources.Resources;
import com.platform.mvc.role.Role;
import com.platform.mvc.station.Station;
import com.platform.mvc.syslog.Syslog;
import com.platform.mvc.systems.Systems;
import com.platform.mvc.upload.Upload;
import com.platform.mvc.user.User;
import com.platform.mvc.user.UserInfo;

/**
 * <p>Title: MappingTable<／p>
 * <p>Description: <／p>
 * @author ZhongwengHao
 * @date 2016年3月12日
 */
public class MappingTable {
	private static Logger log = Logger.getLogger(MappingTable.class);
	public static void mapping(ActiveRecordPlugin arp){
		log.info(" MappingTable 表手工注册-----begin");
		
		arp.addMapping("pt_department", "ids", Department.class);
		arp.addMapping("pt_dict", "ids", Dict.class);
		arp.addMapping("pt_group", "ids", Group.class);
		arp.addMapping("pt_menu", "ids", Menu.class);
		arp.addMapping("pt_module", "ids", Module.class);
		arp.addMapping("pt_operator", "ids", Operator.class);
		arp.addMapping("pt_param", "ids", Param.class);
		arp.addMapping("pt_resources", "ids", Resources.class);
		arp.addMapping("pt_role", "ids", Role.class);
		arp.addMapping("pt_station", "ids", Station.class);
		arp.addMapping("pt_syslog", "ids", Syslog.class);
		arp.addMapping("pt_systems", "ids", Systems.class);
		arp.addMapping("pt_upload", "ids", Upload.class);
		arp.addMapping("pt_user", "ids", User.class);
		arp.addMapping("pt_userinfo", "ids", UserInfo.class);
		
		log.info(" MappingTable 表手工注册-----end");
		
	}
}
