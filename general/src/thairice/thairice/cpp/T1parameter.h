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
	 * 字段描述： 
	 * 字段类型：bigint  长度：null
	 */
	biginteger id;
	/**
	 * 字段描述：用户ID 
	 * 字段类型：bigint  长度：null
	 */
	long userid;
	/**
	 * 字段描述：参数明细备注 
	 * 字段类型：varchar  长度：256
	 */
	string remark;
	/**
	 * 字段描述：删除标志 
	 * 字段类型：tinyint  长度：null
	 */
	bool status_;
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
	/**
	 * 字段描述：参数类型编号 
	 * 字段类型：varchar  长度：8
	 */
	string parm_type_id;
	/**
	 * 字段描述：参数明细名称 
	 * 字段类型：varchar  长度：256
	 */
	string name_;
	/**
	 * 字段描述：参数值 
	 * 字段类型：varchar  长度：256
	 */
	string value_;
	/**
	 * 字段描述：参数明细编号 
	 * 字段类型：varchar  长度：3
	 */
	string parm__id;
public:
	
	
	T1parameter& setId(biginteger aid){
		id = aid;
		mapSQLTokens["id"] = to_string(id);
		return *this;
	}
	biginteger getId() {
		return id;
	}
	
	
	T1parameter& setUserid(long auserid){
		userid = auserid;
		mapSQLTokens["userid"] = to_string(userid);
		return *this;
	}
	long getUserid() {
		return userid;
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
	
	
	T1parameter& setParm_type_id(string aparm_type_id){
		parm_type_id = aparm_type_id;
		
		mapSQLTokens["parm_type_id"] = "'"+parm_type_id+"'";
		return *this;
	}
	string getParm_type_id() {
		return parm_type_id;
	}
	
	
	T1parameter& setName_(string aname_){
		name_ = aname_;
		
		mapSQLTokens["name_"] = "'"+name_+"'";
		return *this;
	}
	string getName_() {
		return name_;
	}
	
	
	T1parameter& setValue_(string avalue_){
		value_ = avalue_;
		
		mapSQLTokens["value_"] = "'"+value_+"'";
		return *this;
	}
	string getValue_() {
		return value_;
	}
	
	
	T1parameter& setParm__id(string aparm__id){
		parm__id = aparm__id;
		
		mapSQLTokens["parm__id"] = "'"+parm__id+"'";
		return *this;
	}
	string getParm__id() {
		return parm__id;
	}
	
};
const string T1parameter::TableName = "t1parameter";
