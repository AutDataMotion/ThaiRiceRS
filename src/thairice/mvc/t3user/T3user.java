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
	 * 字段描述：用户ID 
	 * 字段类型：bigint  长度：null
	 */
	public static final String column_id = "id";
	
	/**
	 * 字段描述：用户类型代码 
	 * 字段类型：enum  长度：2
	 */
	public static final String column_type_ = "type_";
	
	/**
	 * 字段描述：username 
	 * 字段类型：varchar  长度：180
	 */
	public static final String column_account = "account";
	
	/**
	 * 字段描述：密码 
	 * 字段类型：varchar  长度：60
	 */
	public static final String column_pwd = "pwd";
	
	/**
	 * 字段描述：姓名 
	 * 字段类型：varchar  长度：180
	 */
	public static final String column_name_ = "name_";
	
	/**
	 * 字段描述：手机号码 
	 * 字段类型：varchar  长度：30
	 */
	public static final String column_phone = "phone";
	
	/**
	 * 字段描述：电子邮箱地址 
	 * 字段类型：varchar  长度：240
	 */
	public static final String column_email = "email";
	
	/**
	 * 字段描述：通讯地址 
	 * 字段类型：varchar  长度：1500
	 */
	public static final String column_address = "address";
	
	/**
	 * 字段描述：邮政编码 
	 * 字段类型：varchar  长度：30
	 */
	public static final String column_zip_encode = "zip_encode";
	
	/**
	 * 字段描述：头像 
	 * 字段类型：varchar  长度：255
	 */
	public static final String column_heading = "heading";
	
	/**
	 * 字段描述：公司名称 
	 * 字段类型：varchar  长度：240
	 */
	public static final String column_company = "company";
	
	/**
	 * 字段描述：行业 
	 * 字段类型：varchar  长度：80
	 */
	public static final String column_industry = "industry";
	
	/**
	 * 字段描述：创建时间 
	 * 字段类型：datetime  长度：null
	 */
	public static final String column_create_time = "create_time";
	
	/**
	 * 字段描述：用户审核状态代码 
	 * 字段类型：enum  长度：2
	 */
	public static final String column_status_ = "status_";
	
	/**
	 * 字段描述：忘记密码校验码 
	 * 字段类型：varchar  长度：6
	 */
	public static final String column_identiCode = "identiCode";
	
	/**
	 * 字段描述：校验码到期时间 
	 * 字段类型：datetime  长度：null
	 */
	public static final String column_expirTime = "expirTime";
	
	/**
	 * 字段描述：产品生效日期 
	 * 字段类型：datetime  长度：null
	 */
	public static final String column_Prdt_EfDt = "Prdt_EfDt";
	
	/**
	 * 字段描述：产品到期日期 
	 * 字段类型：datetime  长度：null
	 */
	public static final String column_PD_ExDat = "PD_ExDat";
	
	/**
	 * 字段描述：账号是否激活 
	 * 字段类型：int  长度：null
	 */
	public static final String column_activation = "activation";
	
	/**
	 * 字段描述： 
	 * 字段类型：varchar  长度：200
	 */
	public static final String column_area = "area";
	
	/**
	 * 字段描述：产品类型代码 
	 * 字段类型：varchar  长度：50
	 */
	public static final String column_PD_TpCd = "PD_TpCd";
	
	
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
	private String identiCode;
	private Timestamp expirTime;
	private Timestamp Prdt_EfDt;
	private Timestamp PD_ExDat;
	private Integer activation;
	private String area;
	private String PD_TpCd;

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
	public void setIdentiCode(String identiCode){
		set(column_identiCode, identiCode);
	}
	public <T> T getIdentiCode() {
		return get(column_identiCode);
	}
	public void setExpirTime(Timestamp expirTime){
		set(column_expirTime, expirTime);
	}
	public <T> T getExpirTime() {
		return get(column_expirTime);
	}
	public void setPrdt_EfDt(Timestamp Prdt_EfDt){
		set(column_Prdt_EfDt, Prdt_EfDt);
	}
	public <T> T getPrdt_EfDt() {
		return get(column_Prdt_EfDt);
	}
	public void setPD_ExDat(Timestamp PD_ExDat){
		set(column_PD_ExDat, PD_ExDat);
	}
	public <T> T getPD_ExDat() {
		return get(column_PD_ExDat);
	}
	public void setActivation(Integer activation){
		set(column_activation, activation);
	}
	public <T> T getActivation() {
		return get(column_activation);
	}
	public void setArea(String area){
		set(column_area, area);
	}
	public <T> T getArea() {
		return get(column_area);
	}
	public void setPD_TpCd(String PD_TpCd){
		set(column_PD_TpCd, PD_TpCd);
	}
	public <T> T getPD_TpCd() {
		return get(column_PD_TpCd);
	}
	
}
