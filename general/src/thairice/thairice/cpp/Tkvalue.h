/**  
 * Date:	 - -  : :   
 * project:	   
 * fileName:	Tkvalue.h  
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

class Tkvalue :public AbsDAL {

public:
	Tkvalue():AbsDAL(&TableName){}
	~Tkvalue(){}
	
private:
	static const string TableName;
	/**
	 * 字段描述：id 
	 * 字段类型：bigint  长度：null
	 */
	biginteger id;
	/**
	 * 字段描述：key 
	 * 字段类型：varchar  长度：64
	 */
	string key_;
	/**
	 * 字段描述：名称 
	 * 字段类型：varchar  长度：256
	 */
	string value_;
	/**
	 * 字段描述：备注 
	 * 字段类型：varchar  长度：128
	 */
	string info;
	/**
	 * 字段描述：状态 （0未/ 1已发布,2已结束） 
	 * 字段类型：int  长度：null
	 */
	int status_;
	/**
	 * 字段描述：添加时间 
	 * 字段类型：datetime  长度：null
	 */
	string add_time;
public:
	
	
	Tkvalue& setId(biginteger aid){
		id = aid;
		mapSQLTokens["id"] = to_string(id);
		return *this;
	}
	biginteger getId() {
		return id;
	}
	
	
	Tkvalue& setKey_(string akey_){
		key_ = akey_;
		
		mapSQLTokens["key_"] = "'"+key_+"'";
		return *this;
	}
	string getKey_() {
		return key_;
	}
	
	
	Tkvalue& setValue_(string avalue_){
		value_ = avalue_;
		
		mapSQLTokens["value_"] = "'"+value_+"'";
		return *this;
	}
	string getValue_() {
		return value_;
	}
	
	
	Tkvalue& setInfo(string ainfo){
		info = ainfo;
		
		mapSQLTokens["info"] = "'"+info+"'";
		return *this;
	}
	string getInfo() {
		return info;
	}
	
	
	Tkvalue& setStatus_(int astatus_){
		status_ = astatus_;
		mapSQLTokens["status_"] = to_string(status_);
		return *this;
	}
	int getStatus_() {
		return status_;
	}
	
	
	Tkvalue& setAdd_time(string aadd_time){
		add_time = aadd_time;
		
		mapSQLTokens["add_time"] = "'"+add_time+"'";
		return *this;
	}
	string getAdd_time() {
		return add_time;
	}
	
};
const string Tkvalue::TableName = "tkvalue";
