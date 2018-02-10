/**  
 * Date:	 - -  : :   
 * project:	   
 * fileName:	T9sample_info.h  
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

class T9sample_info :public AbsDAL {

public:
	T9sample_info():AbsDAL(&TableName){}
	~T9sample_info(){}
	
private:
	static const string TableName;
	/**
	 * 字段描述：样本编号 
	 * 字段类型：bigint  长度：null
	 */
	biginteger id;
	/**
	 * 字段描述：用户ID 
	 * 字段类型：bigint  长度：null
	 */
	biginteger userid;
	/**
	 * 字段描述：源文件编号 
	 * 字段类型：bigint  长度：null
	 */
	biginteger identifier;
	/**
	 * 字段描述：样本名称 
	 * 字段类型：varchar  长度：128
	 */
	string name_;
	/**
	 * 字段描述：样本数量 
	 * 字段类型：varchar  长度：30
	 */
	string number_;
	/**
	 * 字段描述：样本路径 
	 * 字段类型：varchar  长度：30
	 */
	string path_;
	/**
	 * 字段描述：样本类型内容 
	 * 字段类型：varchar  长度：30
	 */
	string type_content;
	/**
	 * 字段描述：样本时间 
	 * 字段类型：datetime  长度：null
	 */
	string datetime_;
public:
	
	
	T9sample_info& setId(biginteger aid){
		id = aid;
		mapSQLTokens["id"] = to_string(id);
		return *this;
	}
	biginteger getId() {
		return id;
	}
	
	
	T9sample_info& setUserid(biginteger auserid){
		userid = auserid;
		mapSQLTokens["userid"] = to_string(userid);
		return *this;
	}
	biginteger getUserid() {
		return userid;
	}
	
	
	T9sample_info& setIdentifier(biginteger aidentifier){
		identifier = aidentifier;
		mapSQLTokens["identifier"] = to_string(identifier);
		return *this;
	}
	biginteger getIdentifier() {
		return identifier;
	}
	
	
	T9sample_info& setName_(string aname_){
		name_ = aname_;
		
		mapSQLTokens["name_"] = "'"+name_+"'";
		return *this;
	}
	string getName_() {
		return name_;
	}
	
	
	T9sample_info& setNumber_(string anumber_){
		number_ = anumber_;
		
		mapSQLTokens["number_"] = "'"+number_+"'";
		return *this;
	}
	string getNumber_() {
		return number_;
	}
	
	
	T9sample_info& setPath_(string apath_){
		path_ = apath_;
		
		mapSQLTokens["path_"] = "'"+path_+"'";
		return *this;
	}
	string getPath_() {
		return path_;
	}
	
	
	T9sample_info& setType_content(string atype_content){
		type_content = atype_content;
		
		mapSQLTokens["type_content"] = "'"+type_content+"'";
		return *this;
	}
	string getType_content() {
		return type_content;
	}
	
	
	T9sample_info& setDatetime_(string adatetime_){
		datetime_ = adatetime_;
		
		mapSQLTokens["datetime_"] = "'"+datetime_+"'";
		return *this;
	}
	string getDatetime_() {
		return datetime_;
	}
	
};
const string T9sample_info::TableName = "t9sample_info";
