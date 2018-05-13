/**  
 * Date:	 - -  : :   
 * project:	   
 * fileName:	T2syslog.h  
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

class T2syslog :public AbsDAL {

public:
	T2syslog():AbsDAL(&TableName){}
	~T2syslog(){}
	
private:
	static const string TableName;
	/**
	 * 字段描述：系统日志编号 
	 * 字段类型：bigint  长度：null
	 */
	biginteger id;
	/**
	 * 字段描述：系统日志类型代码 
	 * 字段类型：char  长度：3
	 */
	string type_;
	/**
	 * 字段描述：用户ID 
	 * 字段类型：bigint  长度：null
	 */
	biginteger userid;
	/**
	 * 字段描述：用户名 
	 * 字段类型：varchar  长度：60
	 */
	string username;
	/**
	 * 字段描述：操作 
	 * 字段类型：varchar  长度：256
	 */
	string action_;
	/**
	 * 字段描述：系统日志内容 
	 * 字段类型：text  长度：65535
	 */
	string content;
	/**
	 * 字段描述：系统日志生成日期 
	 * 字段类型：datetime  长度：null
	 */
	string add_time;
public:
	
	
	T2syslog& setId(biginteger aid){
		id = aid;
		mapSQLTokens["id"] = to_string(id);
		return *this;
	}
	biginteger getId() {
		return id;
	}
	
	
	T2syslog& setType_(string atype_){
		type_ = atype_;
		
		mapSQLTokens["type_"] = "'"+type_+"'";
		return *this;
	}
	string getType_() {
		return type_;
	}
	
	
	T2syslog& setUserid(biginteger auserid){
		userid = auserid;
		mapSQLTokens["userid"] = to_string(userid);
		return *this;
	}
	biginteger getUserid() {
		return userid;
	}
	
	
	T2syslog& setUsername(string ausername){
		username = ausername;
		
		mapSQLTokens["username"] = "'"+username+"'";
		return *this;
	}
	string getUsername() {
		return username;
	}
	
	
	T2syslog& setAction_(string aaction_){
		action_ = aaction_;
		
		mapSQLTokens["action_"] = "'"+action_+"'";
		return *this;
	}
	string getAction_() {
		return action_;
	}
	
	
	T2syslog& setContent(string acontent){
		content = acontent;
		
		mapSQLTokens["content"] = "'"+content+"'";
		return *this;
	}
	string getContent() {
		return content;
	}
	
	
	T2syslog& setAdd_time(string aadd_time){
		add_time = aadd_time;
		
		mapSQLTokens["add_time"] = "'"+add_time+"'";
		return *this;
	}
	string getAdd_time() {
		return add_time;
	}
	
};
const string T2syslog::TableName = "t2syslog";
