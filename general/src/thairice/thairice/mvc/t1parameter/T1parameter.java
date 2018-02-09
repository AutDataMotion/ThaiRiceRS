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
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_id = "id";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_userid = "userid";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_identifier = "identifier";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_type_ = "type_";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_description = "description";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_remark = "remark";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_status_ = "status_";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_open_date = "open_date";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_dac = "dac";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_busi_type = "busi_type";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_busi_code = "busi_code";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_version = "version";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_datetime_ = "datetime_";
	
	
	/**
	 * sqlId : thairice.t1parameter.splitPageFrom
	 * 描述：分页from
	 */
	public static final String sqlId_splitPage_from = "thairice.t1parameter.splitPageFrom";

	private Long id;
	private BigInteger userid;
	private String identifier;
	private String type_;
	private String description;
	private String remark;
	private Boolean status_;
	private Timestamp open_date;
	private String dac;
	private String busi_type;
	private String busi_code;
	private Integer version;
	private Timestamp datetime_;

	public void setId(Long id){
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
	public void setIdentifier(String identifier){
		set(column_identifier, identifier);
	}
	public <T> T getIdentifier() {
		return get(column_identifier);
	}
	public void setType_(String type_){
		set(column_type_, type_);
	}
	public <T> T getType_() {
		return get(column_type_);
	}
	public void setDescription(String description){
		set(column_description, description);
	}
	public <T> T getDescription() {
		return get(column_description);
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
	public void setOpen_date(Timestamp open_date){
		set(column_open_date, open_date);
	}
	public <T> T getOpen_date() {
		return get(column_open_date);
	}
	public void setDac(String dac){
		set(column_dac, dac);
	}
	public <T> T getDac() {
		return get(column_dac);
	}
	public void setBusi_type(String busi_type){
		set(column_busi_type, busi_type);
	}
	public <T> T getBusi_type() {
		return get(column_busi_type);
	}
	public void setBusi_code(String busi_code){
		set(column_busi_code, busi_code);
	}
	public <T> T getBusi_code() {
		return get(column_busi_code);
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
	
}
