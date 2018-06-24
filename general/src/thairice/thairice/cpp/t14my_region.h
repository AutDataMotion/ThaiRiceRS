/**  
 * Date:	 - -  : :   
 * project:	   
 * fileName:	t14my_region.h  
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

class t14my_region :public AbsDAL {

public:
	t14my_region():AbsDAL(&TableName){}
	~t14my_region(){}
	
private:
	static const string TableName;
	/**
	 * 字段描述： 
	 * 字段类型：int  长度：null
	 */
	int id;
	/**
	 * 字段描述： 
	 * 字段类型：int  长度：null
	 */
	int userId;
	/**
	 * 字段描述： 
	 * 字段类型：int  长度：null
	 */
	int provinceId;
	/**
	 * 字段描述： 
	 * 字段类型：int  长度：null
	 */
	int cityId;
	/**
	 * 字段描述： 
	 * 字段类型：int  长度：null
	 */
	int areaId;
public:
	
	
	t14my_region& setId(int aid){
		id = aid;
		mapSQLTokens["id"] = to_string(id);
		return *this;
	}
	int getId() {
		return id;
	}
	
	
	t14my_region& setUserId(int auserId){
		userId = auserId;
		mapSQLTokens["userId"] = to_string(userId);
		return *this;
	}
	int getUserId() {
		return userId;
	}
	
	
	t14my_region& setProvinceId(int aprovinceId){
		provinceId = aprovinceId;
		mapSQLTokens["provinceId"] = to_string(provinceId);
		return *this;
	}
	int getProvinceId() {
		return provinceId;
	}
	
	
	t14my_region& setCityId(int acityId){
		cityId = acityId;
		mapSQLTokens["cityId"] = to_string(cityId);
		return *this;
	}
	int getCityId() {
		return cityId;
	}
	
	
	t14my_region& setAreaId(int aareaId){
		areaId = aareaId;
		mapSQLTokens["areaId"] = to_string(areaId);
		return *this;
	}
	int getAreaId() {
		return areaId;
	}
	
};
const string t14my_region::TableName = "t14my_region";
