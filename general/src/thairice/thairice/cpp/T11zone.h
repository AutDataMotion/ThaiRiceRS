/**  
 * Date:	 - -  : :   
 * project:	   
 * fileName:	T11zone.h  
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

class T11zone :public AbsDAL {

public:
	T11zone():AbsDAL(&TableName){}
	~T11zone(){}
	
private:
	static const string TableName;
	/**
	 * 字段描述：行政区划id 
	 * 字段类型：int  长度：null
	 */
	int id;
	/**
	 * 字段描述：行政区划代码 
	 * 字段类型：int  长度：null
	 */
	int code_;
	/**
	 * 字段描述：产品类型代码 
	 * 字段类型：enum  长度：2
	 */
	string type_;
	/**
	 * 字段描述：行政区划名称 
	 * 字段类型：varchar  长度：30
	 */
	string name_;
public:
	
	
	T11zone& setId(int aid){
		id = aid;
		mapSQLTokens["id"] = to_string(id);
		return *this;
	}
	int getId() {
		return id;
	}
	
	
	T11zone& setCode_(int acode_){
		code_ = acode_;
		mapSQLTokens["code_"] = to_string(code_);
		return *this;
	}
	int getCode_() {
		return code_;
	}
	
	
	T11zone& setType_(string atype_){
		type_ = atype_;
		
		mapSQLTokens["type_"] = "'"+type_+"'";
		return *this;
	}
	string getType_() {
		return type_;
	}
	
	
	T11zone& setName_(string aname_){
		name_ = aname_;
		
		mapSQLTokens["name_"] = "'"+name_+"'";
		return *this;
	}
	string getName_() {
		return name_;
	}
	
};
const string T11zone::TableName = "t11zone";
