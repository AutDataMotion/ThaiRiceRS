package thairice.mvc.t5parameter_type;

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
//@Table(tableName = "t5parameter_type")
public class T5parameter_type extends BaseModel<T5parameter_type> {

	private static final long serialVersionUID = 6761767368352810428L;

	private static Logger log = Logger.getLogger(T5parameter_type.class);
	
	public static final T5parameter_type dao = new T5parameter_type();
	
	/**
	 * 字段描述： 
	 * 字段类型：bigint  长度：null
	 */
	public static final String column_id = "id";
	
	/**
	 * 字段描述：用户ID 
	 * 字段类型：bigint  长度：null
	 */
	public static final String column_userid = "userid";
	
	/**
	 * 字段描述：参数类型名称 
	 * 字段类型：varchar  长度：256
	 */
	public static final String column_name_ = "name_";
	
	/**
	 * 字段描述：参数类型备注 
	 * 字段类型：varchar  长度：256
	 */
	public static final String column_remark = "remark";
	
	/**
	 * 字段描述：参数类型编号 
	 * 字段类型：varchar  长度：8
	 */
	public static final String column_parm_type_id = "parm_type_id";
	
	/**
	 * 字段描述：版本号 
	 * 字段类型：int  长度：null
	 */
	public static final String column_version = "version";
	
	/**
	 * 字段描述：时间戳 
	 * 字段类型：datetime  长度：null
	 */
	public static final String column_datetime_ = "datetime_";
	
	/**
	 * 字段描述：删除标志 
	 * 字段类型：tinyint  长度：null
	 */
	public static final String column_status_ = "status_";
	
	
	/**
	 * sqlId : thairice.t5parameter_type.splitPageFrom
	 * 描述：分页from
	 */
	public static final String sqlId_splitPage_from = "thairice.t5parameter_type.splitPageFrom";

	private BigInteger id;
	private BigInteger userid;
	private String name_;
	private String remark;
	private String parm_type_id;
	private Integer version;
	private Timestamp datetime_;
	private Boolean status_;

	public void setId(BigInteger id){
		set(column_id, id);
	}
	public <T> T getId() {
		return get(column_id);
	}
	public void setUserid(BigInteger userid){
		set(column_userid, userid);
	}
	public <T> T getUserid() {
		return get(column_userid);
	}
	public void setName_(String name_){
		set(column_name_, name_);
	}
	public <T> T getName_() {
		return get(column_name_);
	}
	public void setRemark(String remark){
		set(column_remark, remark);
	}
	public <T> T getRemark() {
		return get(column_remark);
	}
	public void setParm_type_id(String parm_type_id){
		set(column_parm_type_id, parm_type_id);
	}
	public <T> T getParm_type_id() {
		return get(column_parm_type_id);
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
	public void setStatus_(Boolean status_){
		set(column_status_, status_);
	}
	public <T> T getStatus_() {
		return get(column_status_);
	}
	
}
