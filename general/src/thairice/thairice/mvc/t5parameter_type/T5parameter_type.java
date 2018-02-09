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
	public static final String column_name_ = "name_";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_remark = "remark";
	
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
	 * sqlId : thairice.t5parameter_type.splitPageFrom
	 * 描述：分页from
	 */
	public static final String sqlId_splitPage_from = "thairice.t5parameter_type.splitPageFrom";

	private BigInteger id;
	private BigInteger userid;
	private String identifier;
	private String name_;
	private String remark;
	private Timestamp open_date;
	private String dac;
	private String busi_type;
	private String busi_code;
	private Integer version;
	private Timestamp datetime_;

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
	public void setIdentifier(String identifier){
		set(column_identifier, identifier);
	}
	public <T> T getIdentifier() {
		return get(column_identifier);
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
