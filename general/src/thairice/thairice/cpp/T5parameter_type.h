/**  
 * Date:	 - -  : :   
 * project:	   
 * fileName:	T5parameter_type.h  
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

class T5parameter_type :public AbsDAL {

public:
	T5parameter_type():AbsDAL(&TableName){}
	~T5parameter_type(){}
	
private:
	static const string TableName;
	/**
	 * 字段描述：序号 
	 * 字段类型：bigint  长度：null
	 */
	biginteger id;
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
	 * 字段描述：参数类型名称 
	 * 字段类型：varchar  长度：240
	 */
	string name_;
	/**
	 * 字段描述：参数备注 
	 * 字段类型：varchar  长度：240
	 */
	string remark;
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
	
	
	T5parameter_type& setId(biginteger aid){
		id = aid;
		mapSQLTokens["id"] = to_string(id);
		return *this;
	}
	biginteger getId() {
		return id;
	}
	
	
	T5parameter_type& setUserid(biginteger auserid){
		userid = auserid;
		mapSQLTokens["userid"] = to_string(userid);
		return *this;
	}
	biginteger getUserid() {
		return userid;
	}
	
	
	T5parameter_type& setIdentifier(string aidentifier){
		identifier = aidentifier;
		
		mapSQLTokens["identifier"] = "'"+identifier+"'";
		return *this;
	}
	string getIdentifier() {
		return identifier;
	}
	
	
	T5parameter_type& setName_(string aname_){
		name_ = aname_;
		
		mapSQLTokens["name_"] = "'"+name_+"'";
		return *this;
	}
	string getName_() {
		return name_;
	}
	
	
	T5parameter_type& setRemark(string aremark){
		remark = aremark;
		
		mapSQLTokens["remark"] = "'"+remark+"'";
		return *this;
	}
	string getRemark() {
		return remark;
	}
	
	
	T5parameter_type& setOpen_date(string aopen_date){
		open_date = aopen_date;
		
		mapSQLTokens["open_date"] = "'"+open_date+"'";
		return *this;
	}
	string getOpen_date() {
		return open_date;
	}
	
	
	T5parameter_type& setDac(string adac){
		dac = adac;
		
		mapSQLTokens["dac"] = "'"+dac+"'";
		return *this;
	}
	string getDac() {
		return dac;
	}
	
	
	T5parameter_type& setBusi_type(string abusi_type){
		busi_type = abusi_type;
		
		mapSQLTokens["busi_type"] = "'"+busi_type+"'";
		return *this;
	}
	string getBusi_type() {
		return busi_type;
	}
	
	
	T5parameter_type& setBusi_code(string abusi_code){
		busi_code = abusi_code;
		
		mapSQLTokens["busi_code"] = "'"+busi_code+"'";
		return *this;
	}
	string getBusi_code() {
		return busi_code;
	}
	
	
	T5parameter_type& setVersion(int aversion){
		version = aversion;
		mapSQLTokens["version"] = to_string(version);
		return *this;
	}
	int getVersion() {
		return version;
	}
	
	
	T5parameter_type& setDatetime_(string adatetime_){
		datetime_ = adatetime_;
		
		mapSQLTokens["datetime_"] = "'"+datetime_+"'";
		return *this;
	}
	string getDatetime_() {
		return datetime_;
	}
	
};
const string T5parameter_type::TableName = "t5parameter_type";
