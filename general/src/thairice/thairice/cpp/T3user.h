/**  
 * Date:	 - -  : :   
 * project:	   
 * fileName:	T3user.h  
 * description:	
 * author:	zhongweng.hao@qq.com
 * Modification History:   
 * Date        Author         Version      Description   
 * ----------------------------------------------------------------- 
 *  - -   : :        Zhongweng       1.0         1.0 Version   
 */
#pragma once
#include <string>
#include "AbsDAL.h"
using namespace std;

class T3user :public AbsDAL {

public:
	T3user():AbsDAL(&TableName){}
	~T3user(){}
	
private:
	static const string TableName;
	/**
	 * 字段描述：用户ID 
	 * 字段类型：bigint  长度：null
	 */
	biginteger id;
	/**
	 * 字段描述：用户类型代码 
	 * 字段类型：enum  长度：5
	 */
	string type_;
	/**
	 * 字段描述：用户名 
	 * 字段类型：varchar  长度：180
	 */
	string account;
	/**
	 * 字段描述：密码 
	 * 字段类型：varchar  长度：60
	 */
	string pwd;
	/**
	 * 字段描述：姓名 
	 * 字段类型：varchar  长度：180
	 */
	string name_;
	/**
	 * 字段描述：手机号码 
	 * 字段类型：varchar  长度：30
	 */
	string phone;
	/**
	 * 字段描述：电子邮箱地址 
	 * 字段类型：varchar  长度：240
	 */
	string email;
	/**
	 * 字段描述：通讯地址 
	 * 字段类型：varchar  长度：1500
	 */
	string address;
	/**
	 * 字段描述：邮政编码 
	 * 字段类型：varchar  长度：30
	 */
	string zip_encode;
	/**
	 * 字段描述：头像 
	 * 字段类型：varchar  长度：255
	 */
	string heading;
	/**
	 * 字段描述：公司名称 
	 * 字段类型：varchar  长度：240
	 */
	string company;
	/**
	 * 字段描述：行业 
	 * 字段类型：varchar  长度：80
	 */
	string industry;
	/**
	 * 字段描述：创建时间 
	 * 字段类型：datetime  长度：null
	 */
	string create_time;
	/**
	 * 字段描述：用户审核状态代码 
	 * 字段类型：enum  长度：5
	 */
	string status_;
public:
	
	
	T3user& setId(biginteger aid){
		id = aid;
		mapSQLTokens["id"] = to_string(id);
		return *this;
	}
	biginteger getId() {
		return id;
	}
	
	
	T3user& setType_(string atype_){
		type_ = atype_;
		
		mapSQLTokens["type_"] = "'"+type_+"'";
		return *this;
	}
	string getType_() {
		return type_;
	}
	
	
	T3user& setAccount(string aaccount){
		account = aaccount;
		
		mapSQLTokens["account"] = "'"+account+"'";
		return *this;
	}
	string getAccount() {
		return account;
	}
	
	
	T3user& setPwd(string apwd){
		pwd = apwd;
		
		mapSQLTokens["pwd"] = "'"+pwd+"'";
		return *this;
	}
	string getPwd() {
		return pwd;
	}
	
	
	T3user& setName_(string aname_){
		name_ = aname_;
		
		mapSQLTokens["name_"] = "'"+name_+"'";
		return *this;
	}
	string getName_() {
		return name_;
	}
	
	
	T3user& setPhone(string aphone){
		phone = aphone;
		
		mapSQLTokens["phone"] = "'"+phone+"'";
		return *this;
	}
	string getPhone() {
		return phone;
	}
	
	
	T3user& setEmail(string aemail){
		email = aemail;
		
		mapSQLTokens["email"] = "'"+email+"'";
		return *this;
	}
	string getEmail() {
		return email;
	}
	
	
	T3user& setAddress(string aaddress){
		address = aaddress;
		
		mapSQLTokens["address"] = "'"+address+"'";
		return *this;
	}
	string getAddress() {
		return address;
	}
	
	
	T3user& setZip_encode(string azip_encode){
		zip_encode = azip_encode;
		
		mapSQLTokens["zip_encode"] = "'"+zip_encode+"'";
		return *this;
	}
	string getZip_encode() {
		return zip_encode;
	}
	
	
	T3user& setHeading(string aheading){
		heading = aheading;
		
		mapSQLTokens["heading"] = "'"+heading+"'";
		return *this;
	}
	string getHeading() {
		return heading;
	}
	
	
	T3user& setCompany(string acompany){
		company = acompany;
		
		mapSQLTokens["company"] = "'"+company+"'";
		return *this;
	}
	string getCompany() {
		return company;
	}
	
	
	T3user& setIndustry(string aindustry){
		industry = aindustry;
		
		mapSQLTokens["industry"] = "'"+industry+"'";
		return *this;
	}
	string getIndustry() {
		return industry;
	}
	
	
	T3user& setCreate_time(string acreate_time){
		create_time = acreate_time;
		
		mapSQLTokens["create_time"] = "'"+create_time+"'";
		return *this;
	}
	string getCreate_time() {
		return create_time;
	}
	
	
	T3user& setStatus_(string astatus_){
		status_ = astatus_;
		
		mapSQLTokens["status_"] = "'"+status_+"'";
		return *this;
	}
	string getStatus_() {
		return status_;
	}
	
};
const string T3user::TableName = "t3user";
