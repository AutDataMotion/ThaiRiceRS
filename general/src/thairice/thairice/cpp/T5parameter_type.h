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
	 * 字段描述： 
	 * 字段类型：bigint  长度：null
	 */
	biginteger id;
	/**
	 * 字段描述：用户ID 
	 * 字段类型：bigint  长度：null
	 */
	biginteger userid;
	/**
	 * 字段描述：参数类型名称 
	 * 字段类型：varchar  长度：256
	 */
	string name_;
	/**
	 * 字段描述：参数类型备注 
	 * 字段类型：varchar  长度：256
	 */
	string remark;
	/**
	 * 字段描述：参数类型编号 
	 * 字段类型：varchar  长度：8
	 */
	string parm_type_id;
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
	 * 字段描述：删除标志 
	 * 字段类型：tinyint  长度：null
	 */
	bool status_;
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
	
	
	T5parameter_type& setParm_type_id(string aparm_type_id){
		parm_type_id = aparm_type_id;
		
		mapSQLTokens["parm_type_id"] = "'"+parm_type_id+"'";
		return *this;
	}
	string getParm_type_id() {
		return parm_type_id;
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
	
	
	T5parameter_type& setStatus_(bool astatus_){
		status_ = astatus_;
		mapSQLTokens["status_"] = to_string(status_);
		return *this;
	}
	bool getStatus_() {
		return status_;
	}
	
};
const string T5parameter_type::TableName = "t5parameter_type";
