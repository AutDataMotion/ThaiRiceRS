package com.platform.plugin;

import java.util.List;

import org.apache.log4j.Logger;

import com.jfinal.plugin.IPlugin;
import com.platform.mvc.dict.Dict;
import com.platform.mvc.group.Group;
import com.platform.mvc.operator.Operator;
import com.platform.mvc.param.Param;
import com.platform.mvc.role.Role;
import com.platform.mvc.station.Station;
import com.platform.mvc.user.User;
import com.platform.tools.ToolSqlXml;

/**
 * 系统初始化缓存操作类
 * @author DHJ  2015-12-16 下午1:16:56
 */
public class ParamInitPlugin implements IPlugin {
	
	private static Logger log = Logger.getLogger(ParamInitPlugin.class);
	 
    /**
     * 用户缓存key前缀
     */
	public static String cacheStart_user = "user_";

    /**
     * 分组缓存key前缀
     */
	public static String cacheStart_group = "group_";

    /**
     * 角色缓存key前缀
     */
	public static String cacheStart_role = "role_";

    /**
     * 岗位缓存key前缀
     */
	public static String cacheStart_station = "station_";
    
	/**
     * 功能缓存key前缀
     */
	public static String cacheStart_operator = "operator_";
    
	/**
     * 字典缓存key前缀
     */
	public static String cacheStart_dict = "dict_";
    
	/**
     * 字典子节点缓存key前缀
     */
	public static String cacheStart_dict_child =  "dict_child_";
    
	/**
     * 参数缓存key前缀
     */
	public static String cacheStart_param = "param_";
    
	/**
     * 参数子节点缓存key前缀
     */
	public static String cacheStart_param_child =  "param_child_";

	@Override
	public boolean start() {
		log.info("缓存参数初始化 start ...");

		// 1.缓存用户
		platform_cacheUser();

		// 2.缓存组
		platform_cacheGroup();

		// 3.缓存角色
		platform_cacheRole();

		// 4.缓存岗位
		platform_cacheStation();

		// 5.缓存功能
		platform_cacheOperator();

		// 6.缓存字典
		platform_cacheDict();

		// 6.缓存参数
		platform_cacheParam();

		log.info("缓存参数初始化 end ...");
		return true;
	}

	@Override
	public boolean stop() {
		return false;
	}

	/**
	 * 缓存所有用户
	 * @author DHJ    2015-12-16 下午1:16:48
	 */
	public static void platform_cacheUser() {
		log.info("缓存加载：User start");
		String sql = ToolSqlXml.getSql(User.sqlId_all);
		List<User> userList = User.dao.find(sql);
		for (User user : userList) {
//			User.dao.cacheAdd(user.getPKValue());
			User.dao.cacheAdd(user);
		}
		log.info("缓存加载：User end, size = " + userList.size());
		userList = null;
	}

	/**
	 * 缓存所有组
	 * @author DHJ    2015-12-16 下午1:17:20
	 */
	public static void platform_cacheGroup() {
		log.info("缓存加载：Group start");
		String sql = ToolSqlXml.getSql(Group.sqlId_all);
		List<Group> groupList = Group.dao.find(sql);
		for (Group group : groupList) {
			Group.dao.cacheAdd(group);
		}
		log.info("缓存加载：Group end, size = " + groupList.size());
		groupList = null;
	}

	/**
	 * 缓存所有角色
	 * @author DHJ    2015-12-16 下午1:17:20
	 */
	public static void platform_cacheRole() {
		log.info("缓存加载：Role start");
		String sql = ToolSqlXml.getSql(Role.sqlId_all);
		List<Role> roleList = Role.dao.find(sql);
		for (Role role : roleList) {
			Role.dao.cacheAdd(role);
		}
		log.info("缓存加载：Role end, size = " + roleList.size());
		roleList = null;
	}
	
	/**
	 * 缓存所有的岗位
	 * @author DHJ    2015-12-16 下午1:17:20
	 */
	public static void platform_cacheStation() {
		log.info("缓存加载：Station start");
		String sql = ToolSqlXml.getSql(Station.sqlId_all);
		List<Station> stationList = Station.dao.find(sql);
		for (Station station : stationList) {
			Station.dao.cacheAdd(station);
		}
		log.info("缓存加载：Station end, size = " + stationList.size());
		stationList = null;
	}

	/**
	 * 缓存操作
	 * @author DHJ    2015-12-16 下午1:17:12
	 */
	public static void platform_cacheOperator() {
		log.info("缓存加载：Operator start");
		String sql = ToolSqlXml.getSql(Operator.sqlId_all);
		List<Operator> operatorList = Operator.dao.find(sql);
		for (Operator operator : operatorList) {
//			Operator.dao.cacheAdd(operator.getPKValue());
			Operator.dao.cacheAdd(operator);
		}
		log.info("缓存加载：Operator end, size = " + operatorList.size());
		operatorList = null;
	}

	/**
	 * 缓存业务字典
	 * @author DHJ    2015-12-16 下午1:17:04
	 */
	public static void platform_cacheDict() {
		log.info("缓存加载：Dict start");
		String sql = ToolSqlXml.getSql(Dict.sqlId_all);
		List<Dict> dictList = Dict.dao.find(sql);
		for (Dict dict : dictList) {
			Dict.dao.cacheAdd(dict);
		}
		log.info("缓存加载：Dict end, size = " + dictList.size());
		dictList = null;
	}

	/**
	 * 缓存业务参数
	 * @author DHJ    2015-12-16 下午1:17:04
	 */
	public static void platform_cacheParam() {
		log.info("缓存加载：Param start");
		String sql = ToolSqlXml.getSql(Param.sqlId_all);
		List<Param> paramList = Param.dao.find(sql);
		for (Param param : paramList) {
			Param.dao.cacheAdd(param);
		}
		log.info("缓存加载：Param end, size = " + paramList.size());
		paramList = null;
	}

}
