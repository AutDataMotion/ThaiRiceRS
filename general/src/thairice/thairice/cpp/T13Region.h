/**  
 * Date:	 - -  : :   
 * project:	   
 * fileName:	T13Region.h  
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

class T13Region :public AbsDAL {

public:
	T13Region():AbsDAL(&TableName){}
	~T13Region(){}
	
private:
	static const string TableName;
	/**
	 * 字段描述： 
	 * 字段类型：int  长度：null
	 */
	int Id;
	/**
	 * 字段描述： 
	 * 字段类型：varchar  长度：200
	 */
	string name;
	/**
	 * 字段描述： 
	 * 字段类型：int  长度：null
	 */
	int parentId;
public:
	
	
	T13Region& setId(int aId){
		Id = aId;
		mapSQLTokens["Id"] = to_string(Id);
		return *this;
	}
	int getId() {
		return Id;
	}
	
	
	T13Region& setName(string aname){
		name = aname;
		
		mapSQLTokens["name"] = "'"+name+"'";
		return *this;
	}
	string getName() {
		return name;
	}
	
	
	T13Region& setParentId(int aparentId){
		parentId = aparentId;
		mapSQLTokens["parentId"] = to_string(parentId);
		return *this;
	}
	int getParentId() {
		return parentId;
	}
	
};
const string T13Region::TableName = "t13region";
