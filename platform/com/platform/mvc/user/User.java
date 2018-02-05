package com.platform.mvc.user;

import org.apache.log4j.Logger;

import com.platform.mvc.base.BaseModelCache;
import com.platform.mvc.dept.Department;
import com.platform.mvc.station.Station;
import com.platform.plugin.ParamInitPlugin;
import com.platform.tools.ToolCache;

/**
 * 用户model
 * @author DHJ
 */
@SuppressWarnings("unused")
//@Table(tableName = "pt_user")
public class User extends BaseModelCache<User> {

	private static final long serialVersionUID = 6761767368352810428L;

	private static Logger log = Logger.getLogger(User.class);
	
	public static final User dao = new User();

	/**
	 * 字段描述：主键 
	 * 字段类型 ：character varying 
	 */
	public static final String column_ids = "ids";
	
	/**
	 * 字段描述：版本号 
	 * 字段类型 ：bigint 
	 */
	public static final String column_version = "version";
	
	/**
	 * 字段描述：密码连续错误次数 
	 * 字段类型 ：bigint 
	 */
	public static final String column_errorcount = "errorcount";
	
	/**
	 * 字段描述：排序号 
	 * 字段类型 ：bigint 
	 */
	public static final String column_orderids = "orderids";
	
	/**
	 * 字段描述：密码 
	 * 字段类型 ：bytea 
	 */
	public static final String column_password = "password";
	
	/**
	 * 字段描述：密钥 
	 * 字段类型 ：bytea 
	 */
	public static final String column_salt = "salt";
	
	/**
	 * 字段描述：账号状态 
	 * 字段类型 ：character 
	 */
	public static final String column_status = "status";
	
	/**
	 * 字段描述：账号停用时间 
	 * 字段类型 ：timestamp without time zone 
	 */
	public static final String column_stopdate = "stopdate";
	
	/**
	 * 字段描述：账号 
	 * 字段类型 ：character varying 
	 */
	public static final String column_username = "username";
	
	/**
	 * 字段描述：所在部门ids 
	 * 字段类型 ：character varying 
	 */
	public static final String column_departmentids = "departmentids";
	
	/**
	 * 字段描述：用户扩展信息ids 
	 * 字段类型 ：character varying 
	 */
	public static final String column_userinfoids = "userinfoids";
	
	/**
	 * 字段描述：所在岗位ids 
	 * 字段类型 ：text 
	 */
	public static final String column_stationids = "stationids";
	
	/**
	 * 字段描述：行级部门ids 
	 * 字段类型 ：text 
	 */
	public static final String column_deptids = "deptids";
	
	/**
	 * 字段描述：行级人员ids 
	 * 字段类型 ：text 
	 */
	public static final String column_userids = "userids";
	
	/**
	 * 字段描述：所在分组ids 
	 * 字段类型 ：text 
	 */
	public static final String column_groupids = "groupids";
	
	/**
	 * 字段描述：所在部门names 
	 * 字段类型 ：character varying 
	 */
	public static final String column_departmentnames = "departmentnames";
	
	/**
	 * 字段描述：所在岗位names 
	 * 字段类型 ：text 
	 */
	public static final String column_stationnames = "stationnames";
	
	/**
	 * 字段描述：行级部门names 
	 * 字段类型 ：text 
	 */
	public static final String column_deptnames = "deptnames";
	
	/**
	 * 字段描述：行级人员names 
	 * 字段类型 ：text 
	 */
	public static final String column_usernames = "usernames";
	
	/**
	 * 字段描述：所在分组names 
	 * 字段类型 ：text 
	 */
	public static final String column_groupnames = "groupnames";

	/**
	 * sqlId : platform.user.splitPageSelect
	 * 描述：分页Select
	 */
	public static final String sqlId_splitPage_select = "platform.user.splitPageSelect";

	/**
	 * sqlId : platform.user.splitPageFrom
	 * 描述：分页from
	 */
	public static final String sqlId_splitPage_from = "platform.user.splitPageFrom";

	/**
	 * sqlId : platform.user.all
	 * 描述：查询所有用户
	 */
	public static final String sqlId_all = "platform.user.all";


	public static final String sqlId_allid = "platform.user.allid";
	
	/**
	 * sqlId : platform.user.treeUserNode
	 * 描述：根据部门Id查询，人员树节点数据 
	 */
	public static final String sqlId_treeUserNode = "platform.user.treeUserNode";

	/**
	 * sqlId : platform.user.column
	 * 描述：查询用户，自定义字段和值
	 */
	public static final String sqlId_column = "platform.user.column";

	/**
	 * sqlId : platform.user.stop
	 * 描述：停用账户
	 */
	public static final String sqlId_stop = "platform.user.stop";

	/**
	 * sqlId : platform.user.start
	 * 描述：启用停用账户
	 */
	public static final String sqlId_start = "platform.user.start";

	private String ids;
	private Long version;
	private Long errorcount;
	private Long orderids;
	private byte[] password;
	private byte[] salt;
	private String status;
	private java.sql.Timestamp stopdate;
	private String username;
	private String departmentids;
	private String userinfoids;
	private String stationids;
	private String deptids;
	private String userids;
	private String groupids;
	private String departmentnames;
	private String stationnames;
	private String deptnames;
	private String usernames;
	private String groupnames;

	public void setIds(String ids){
		set(column_ids, ids);
	}
	public <T> T getIds() {
		return get(column_ids);
	}
	public void setVersion(Long version){
		set(column_version, version);
	}
	public <T> T getVersion() {
		return get(column_version);
	}
	public void setErrorcount(Long errorcount){
		set(column_errorcount, errorcount);
	}
	public <T> T getErrorcount() {
		return get(column_errorcount);
	}
	public void setOrderids(Long orderids){
		set(column_orderids, orderids);
	}
	public <T> T getOrderids() {
		return get(column_orderids);
	}
	public void setPassword(byte[] password){
		set(column_password, password);
	}
	public <T> T getPassword() {
		return get(column_password);
	}
	public void setSalt(byte[] salt){
		set(column_salt, salt);
	}
	public <T> T getSalt() {
		return get(column_salt);
	}
	public void setStatus(String status){
		set(column_status, status);
	}
	public <T> T getStatus() {
		return get(column_status);
	}
	public void setStopdate(java.sql.Timestamp stopdate){
		set(column_stopdate, stopdate);
	}
	public <T> T getStopdate() {
		return get(column_stopdate);
	}
	public void setUsername(String username){
		set(column_username, username);
	}
	public <T> T getUsername() {
		return get(column_username);
	}
	public void setDepartmentids(String departmentids){
		set(column_departmentids, departmentids);
	}
	public <T> T getDepartmentids() {
		return get(column_departmentids);
	}
	public void setUserinfoids(String userinfoids){
		set(column_userinfoids, userinfoids);
	}
	public <T> T getUserinfoids() {
		return get(column_userinfoids);
	}
	public void setStationids(String stationids){
		set(column_stationids, stationids);
	}
	public <T> T getStationids() {
		return get(column_stationids);
	}
	public void setDeptids(String deptids){
		set(column_deptids, deptids);
	}
	public <T> T getDeptids() {
		return get(column_deptids);
	}
	public void setUserids(String userids){
		set(column_userids, userids);
	}
	public <T> T getUserids() {
		return get(column_userids);
	}
	public void setGroupids(String groupids){
		set(column_groupids, groupids);
	}
	public <T> T getGroupids() {
		return get(column_groupids);
	}
	public void setDepartmentnames(String departmentnames){
		set(column_departmentnames, departmentnames);
	}
	public <T> T getDepartmentnames() {
		return get(column_departmentnames);
	}
	public void setStationnames(String stationnames){
		set(column_stationnames, stationnames);
	}
	public <T> T getStationnames() {
		return get(column_stationnames);
	}
	public void setDeptnames(String deptnames){
		set(column_deptnames, deptnames);
	}
	public <T> T getDeptnames() {
		return get(column_deptnames);
	}
	public void setUsernames(String usernames){
		set(column_usernames, usernames);
	}
	public <T> T getUsernames() {
		return get(column_usernames);
	}
	public void setGroupnames(String groupnames){
		set(column_groupnames, groupnames);
	}
	public <T> T getGroupnames() {
		return get(column_groupnames);
	}
	
	/**
	 * 关联查询，获取用户详细信息
	 * @return
	 */
	public UserInfo getUserInfo(){
		String userinfoIds = get(column_userinfoids);
		if(null != userinfoIds && !userinfoIds.isEmpty()){
			return UserInfo.dao.findById(userinfoIds);
		}
		return null;
	}

	/**
	 * 关联查询，获取用户部门信息
	 * @return
	 */
	public Department getDepartment(){
		String departmentids = get(column_departmentids);
		if(null != departmentids && !departmentids.isEmpty()){
			return Department.dao.findById(departmentids);
		}
		return null;
	}

	/**
	 * 关联查询，获取用户岗位信息
	 * @return
	 */
	public Station getStation(){
		String stationids = get(column_stationids);
		if(null != stationids && !stationids.isEmpty()){
			return Station.dao.findById(stationids);
		}
		return null;
	}
	
	/**
	 * 添加或者更新缓存
	 */
	public void cacheAdd(String ids){
		User user = User.dao.findById(ids);
		cacheAdd(user);
	}

	/**
	 * <p>Title: cacheAdd<／p>
	 * <p>Description: <／p>
	 * @param p_user
	 */
	public void cacheAdd(User p_user){
		UserInfo userInfo = p_user.getUserInfo();
		ToolCache.set(ParamInitPlugin.cacheStart_user + p_user.getStr(column_ids), p_user);
		ToolCache.set(ParamInitPlugin.cacheStart_user + p_user.getStr(column_username), p_user);
		ToolCache.set(ParamInitPlugin.cacheStart_user + userInfo.getStr(UserInfo.column_email), p_user);
		ToolCache.set(ParamInitPlugin.cacheStart_user + userInfo.getStr(UserInfo.column_mobile), p_user);
	}
	
	/**
	 * 删除缓存
	 */
	public void cacheRemove(String ids){
		User user = User.dao.findById(ids);
		UserInfo userInfo = user.getUserInfo();
		ToolCache.remove(ParamInitPlugin.cacheStart_user + ids);
		ToolCache.remove(ParamInitPlugin.cacheStart_user + user.getStr(column_username));
		ToolCache.remove(ParamInitPlugin.cacheStart_user + userInfo.getStr(UserInfo.column_email));
		ToolCache.remove(ParamInitPlugin.cacheStart_user + userInfo.getStr(UserInfo.column_mobile));
	}

	/**
	 * 获取缓存
	 * @param ids
	 * @return
	 */
	public User cacheGet(String ids){
		User user = ToolCache.get(ParamInitPlugin.cacheStart_user + ids);
		return user;
	}
	
}
