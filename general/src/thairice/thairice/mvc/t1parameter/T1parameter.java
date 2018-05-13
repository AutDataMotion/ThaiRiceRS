package thairice.mvc.t1parameter;

import com.platform.annotation.Table;
import com.platform.constant.ConstantInit;
import com.platform.mvc.base.BaseModel;

import java.sql.Timestamp; 
import java.math.BigInteger; 

import org.apache.log4j.Logger;

/**
 * @description：
 * @author ZW
 */
@SuppressWarnings("unused")
//@Table(tableName = "t1parameter")
public class T1parameter extends BaseModel<T1parameter> {

	private static final long serialVersionUID = 6761767368352810428L;

	private static Logger log = Logger.getLogger(T1parameter.class);
	
	public static final T1parameter dao = new T1parameter();
	
	/**
	 * 字段描述：用户ID 
	 * 字段类型：bigint  长度：null
	 */
	public static final String column_userid = "userid";
	
	/**
	 * 字段描述：#参数明细备注 
	 * 字段类型：varchar  长度：256
	 */
	public static final String column_remark = "remark";
	
	/**
	 * 字段描述：#删除标志 
	 * 字段类型：tinyint  长度：null
	 */
	public static final String column_status_ = "status_";
	
	/**
	 * 字段描述：#版本号 
	 * 字段类型：int  长度：null
	 */
	public static final String column_version = "version";
	
	/**
	 * 字段描述：#时间戳 
	 * 字段类型：datetime  长度：null
	 */
	public static final String column_datetime_ = "datetime_";
	
	/**
	 * 字段描述：参数类型编号 
	 * 字段类型：varchar  长度：8
	 */
	public static final String column_parm_type_id = "parm_type_id";
	
	/**
	 * 字段描述：参数明细名称 
	 * 字段类型：varchar  长度：256
	 */
	public static final String column_name_ = "name_";
	
	/**
	 * 字段描述：参数值 
	 * 字段类型：varchar  长度：2048
	 */
	public static final String column_value_ = "value_";
	
	/**
	 * 字段描述：参数明细编号 
	 * 字段类型：varchar  长度：3
	 */
	public static final String column_parm__id = "parm__id";
	
	/**
	 * 字段描述： 
	 * 字段类型：bigint  长度：null
	 */
	public static final String column_id = "id";
	
	
	/**
	 * sqlId : thairice.t1parameter.splitPageFrom
	 * 描述：分页from
	 */
	public static final String sqlId_splitPage_from = "thairice.t1parameter.splitPageFrom";

	private Long userid;
	private String remark;
	private Boolean status_;
	private Integer version;
	private Timestamp datetime_;
	private String parm_type_id;
	private String name_;
	private String value_;
	private String parm__id;
	private BigInteger id;

	public void setUserid(Long userid){
		set(column_userid, userid);
	}
	public <T> T getUserid() {
		return get(column_userid);
	}
	public void setRemark(String remark){
		set(column_remark, remark);
	}
	public <T> T getRemark() {
		return get(column_remark);
	}
	public void setStatus_(Boolean status_){
		set(column_status_, status_);
	}
	public <T> T getStatus_() {
		return get(column_status_);
	}
	public void setVersion(Integer version){
		set(column_version, version);
	}
	public <T> T getVersion() {
		return get(column_version);
	}
	public void setDatetime_(Timestamp datetime_){
		set(column_datetime_, datetime_);
	}
	public <T> T getDatetime_() {
		return get(column_datetime_);
	}
	public void setParm_type_id(String parm_type_id){
		set(column_parm_type_id, parm_type_id);
	}
	public <T> T getParm_type_id() {
		return get(column_parm_type_id);
	}
	public void setName_(String name_){
		set(column_name_, name_);
	}
	public <T> T getName_() {
		return get(column_name_);
	}
	public void setValue_(String value_){
		set(column_value_, value_);
	}
	public <T> T getValue_() {
		return get(column_value_);
	}
	public void setParm__id(String parm__id){
		set(column_parm__id, parm__id);
	}
	public <T> T getParm__id() {
		return get(column_parm__id);
	}
	public void setId(BigInteger id){
		set(column_id, id);
	}
	public <T> T getId() {
		return get(column_id);
	}
	
}
