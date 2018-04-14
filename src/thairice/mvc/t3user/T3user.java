package thairice.mvc.t3user;

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
//@Table(tableName = "t3user")
public class T3user extends BaseModel<T3user> {

	private static final long serialVersionUID = 6761767368352810428L;

	private static Logger log = Logger.getLogger(T3user.class);
	
	public static final T3user dao = new T3user();
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_id = "id";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_type_ = "type_";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_account = "account";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_pwd = "pwd";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_name_ = "name_";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_phone = "phone";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_email = "email";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_address = "address";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_zip_encode = "zip_encode";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_heading = "heading";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_company = "company";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_industry = "industry";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_create_time = "create_time";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_status_ = "status_";
	
	/**
	 * sqlId : thairice.t3user.column
	 * 描述：查询用户，自定义字段和值
	 */
	public static final String sqlId_column = "thairice.t3user.column";
	
	/**
	 * sqlId : thairice.t3user.splitPageFrom
	 * 描述：分页from
	 */
	public static final String sqlId_splitPage_from = "thairice.t3user.splitPageFrom";

	private BigInteger id;
	private String type_;
	private String account;
	private String pwd;
	private String name_;
	private String phone;
	private String email;
	private String address;
	private String zip_encode;
	private String heading;
	private String company;
	private String industry;
	private Timestamp create_time;
	private String status_;

	public void setId(BigInteger id){
		set(column_id, id);
	}
	public <T> T getId() {
		return get(column_id);
	}
	public void setType_(String type_){
		set(column_type_, type_);
	}
	public <T> T getType_() {
		return get(column_type_);
	}
	public void setAccount(String account){
		set(column_account, account);
	}
	public <T> T getAccount() {
		return get(column_account);
	}
	public void setPwd(String pwd){
		set(column_pwd, pwd);
	}
	public <T> T getPwd() {
		return get(column_pwd);
	}
	public void setName_(String name_){
		set(column_name_, name_);
	}
	public <T> T getName_() {
		return get(column_name_);
	}
	public void setPhone(String phone){
		set(column_phone, phone);
	}
	public <T> T getPhone() {
		return get(column_phone);
	}
	public void setEmail(String email){
		set(column_email, email);
	}
	public <T> T getEmail() {
		return get(column_email);
	}
	public void setAddress(String address){
		set(column_address, address);
	}
	public <T> T getAddress() {
		return get(column_address);
	}
	public void setZip_encode(String zip_encode){
		set(column_zip_encode, zip_encode);
	}
	public <T> T getZip_encode() {
		return get(column_zip_encode);
	}
	public void setHeading(String heading){
		set(column_heading, heading);
	}
	public <T> T getHeading() {
		return get(column_heading);
	}
	public void setCompany(String company){
		set(column_company, company);
	}
	public <T> T getCompany() {
		return get(column_company);
	}
	public void setIndustry(String industry){
		set(column_industry, industry);
	}
	public <T> T getIndustry() {
		return get(column_industry);
	}
	public void setCreate_time(Timestamp create_time){
		set(column_create_time, create_time);
	}
	public <T> T getCreate_time() {
		return get(column_create_time);
	}
	public void setStatus_(String status_){
		set(column_status_, status_);
	}
	public <T> T getStatus_() {
		return get(column_status_);
	}
	
}
