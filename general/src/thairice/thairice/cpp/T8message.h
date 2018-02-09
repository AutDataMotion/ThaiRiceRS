/**  
 * Date:	 - -  : :   
 * project:	   
 * fileName:	T8message.h  
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

class T8message :public AbsDAL {

public:
	T8message():AbsDAL(&TableName){}
	~T8message(){}
	
private:
	static const string TableName;
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	biginteger id;
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	biginteger send_userid;
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	string add_time;
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	string content;
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	string type_;
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	bool flag;
public:
	
	
	T8message& setId(biginteger aid){
		id = aid;
		mapSQLTokens["id"] = to_string(id);
		return *this;
	}
	biginteger getId() {
		return id;
	}
	
	
	T8message& setSend_userid(biginteger asend_userid){
		send_userid = asend_userid;
		mapSQLTokens["send_userid"] = to_string(send_userid);
		return *this;
	}
	biginteger getSend_userid() {
		return send_userid;
	}
	
	
	T8message& setAdd_time(string aadd_time){
		add_time = aadd_time;
		
		mapSQLTokens["add_time"] = "'"+add_time+"'";
		return *this;
	}
	string getAdd_time() {
		return add_time;
	}
	
	
	T8message& setContent(string acontent){
		content = acontent;
		
		mapSQLTokens["content"] = "'"+content+"'";
		return *this;
	}
	string getContent() {
		return content;
	}
	
	
	T8message& setType_(string atype_){
		type_ = atype_;
		
		mapSQLTokens["type_"] = "'"+type_+"'";
		return *this;
	}
	string getType_() {
		return type_;
	}
	
	
	T8message& setFlag(bool aflag){
		flag = aflag;
		mapSQLTokens["flag"] = to_string(flag);
		return *this;
	}
	bool getFlag() {
		return flag;
	}
	
};
const string T8message::TableName = "t8message";
