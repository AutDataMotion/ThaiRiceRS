/**  
 * Date:	 - -  : :   
 * project:	   
 * fileName:	T1parameter.h  
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

class T1parameter :public AbsDAL {

public:
	T1parameter():AbsDAL(&TableName){}
	~T1parameter(){}
	
private:
	static const string TableName;
	/**
	 * 字段描述：序列编号 
	 * 字段类型：int  长度：null
	 */
	long id;
	/**
	 * 字段描述：用户ID 
	 * 字段类型：bigint  长度：null
	 */
	biginteger userid;
	/**
	 * 字段描述：多实体标识 
	 * 字段类型：char  长度：5
	 */
	string identifier;
	/**
	 * 字段描述：业务参数类型 
	 * 字段类型：varchar  长度：6
	 */
	string type_;
	/**
	 * 字段描述：参数描述 
	 * 字段类型：varchar  长度：256
	 */
	string description;
	/**
	 * 字段描述：参数备注 
	 * 字段类型：varchar  长度：256
	 */
	string remark;
	/**
	 * 字段描述：删除标志 
	 * 字段类型：tinyint  长度：null
	 */
	bool status_;
	/**
	 * 字段描述：营业日期 
	 * 字段类型：datetime  长度：null
	 */
	string open_date;
	/**
	 * 字段描述：DAC校验 
	 * 字段类型：char  长度：16
	 */
	string dac;
	/**
	 * 字段描述：交易发起渠道类别 
	 * 字段类型：char  长度：8
	 */
	string busi_type;
	/**
	 * 字段描述：交易发起渠道编号 
	 * 字段类型：char  长度：23
	 */
	string busi_code;
	/**
	 * 字段描述：版本号 
	 * 字段类型：int  长度：null
	 */
	int version;
	/**
	 * 字段描述：时间戳 
	 * 字段类型：datetime  长度：null
	 */
	string datetime_;
public:
	
	
	T1parameter& setId(long aid){
		id = aid;
		mapSQLTokens["id"] = to_string(id);
		return *this;
	}
	long getId() {
		return id;
	}
	
	
	T1parameter& setUserid(biginteger auserid){
		userid = auserid;
		mapSQLTokens["userid"] = to_string(userid);
		return *this;
	}
	biginteger getUserid() {
		return userid;
	}
	
	
	T1parameter& setIdentifier(string aidentifier){
		identifier = aidentifier;
		
		mapSQLTokens["identifier"] = "'"+identifier+"'";
		return *this;
	}
	string getIdentifier() {
		return identifier;
	}
	
	
	T1parameter& setType_(string atype_){
		type_ = atype_;
		
		mapSQLTokens["type_"] = "'"+type_+"'";
		return *this;
	}
	string getType_() {
		return type_;
	}
	
	
	T1parameter& setDescription(string adescription){
		description = adescription;
		
		mapSQLTokens["description"] = "'"+description+"'";
		return *this;
	}
	string getDescription() {
		return description;
	}
	
	
	T1parameter& setRemark(string aremark){
		remark = aremark;
		
		mapSQLTokens["remark"] = "'"+remark+"'";
		return *this;
	}
	string getRemark() {
		return remark;
	}
	
	
	T1parameter& setStatus_(bool astatus_){
		status_ = astatus_;
		mapSQLTokens["status_"] = to_string(status_);
		return *this;
	}
	bool getStatus_() {
		return status_;
	}
	
	
	T1parameter& setOpen_date(string aopen_date){
		open_date = aopen_date;
		
		mapSQLTokens["open_date"] = "'"+open_date+"'";
		return *this;
	}
	string getOpen_date() {
		return open_date;
	}
	
	
	T1parameter& setDac(string adac){
		dac = adac;
		
		mapSQLTokens["dac"] = "'"+dac+"'";
		return *this;
	}
	string getDac() {
		return dac;
	}
	
	
	T1parameter& setBusi_type(string abusi_type){
		busi_type = abusi_type;
		
		mapSQLTokens["busi_type"] = "'"+busi_type+"'";
		return *this;
	}
	string getBusi_type() {
		return busi_type;
	}
	
	
	T1parameter& setBusi_code(string abusi_code){
		busi_code = abusi_code;
		
		mapSQLTokens["busi_code"] = "'"+busi_code+"'";
		return *this;
	}
	string getBusi_code() {
		return busi_code;
	}
	
	
	T1parameter& setVersion(int aversion){
		version = aversion;
		mapSQLTokens["version"] = to_string(version);
		return *this;
	}
	int getVersion() {
		return version;
	}
	
	
	T1parameter& setDatetime_(string adatetime_){
		datetime_ = adatetime_;
		
		mapSQLTokens["datetime_"] = "'"+datetime_+"'";
		return *this;
	}
	string getDatetime_() {
		return datetime_;
	}
	
};
const string T1parameter::TableName = "t1parameter";
