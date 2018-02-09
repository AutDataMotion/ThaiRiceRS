/**  
 * Date:	 - -  : :   
 * project:	   
 * fileName:	T6org_data.h  
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

class T6org_data :public AbsDAL {

public:
	T6org_data():AbsDAL(&TableName){}
	~T6org_data(){}
	
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
	biginteger userid;
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	string name_;
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	float size_;
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	string download_path;
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	string storage_path;
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	string type_;
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	string row_column;
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	string band_number;
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	string status_;
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	string fail_code;
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	string collect_time;
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	string dload_start_time;
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	string dload_end_time;
public:
	
	
	T6org_data& setId(biginteger aid){
		id = aid;
		mapSQLTokens["id"] = to_string(id);
		return *this;
	}
	biginteger getId() {
		return id;
	}
	
	
	T6org_data& setUserid(biginteger auserid){
		userid = auserid;
		mapSQLTokens["userid"] = to_string(userid);
		return *this;
	}
	biginteger getUserid() {
		return userid;
	}
	
	
	T6org_data& setName_(string aname_){
		name_ = aname_;
		
		mapSQLTokens["name_"] = "'"+name_+"'";
		return *this;
	}
	string getName_() {
		return name_;
	}
	
	
	T6org_data& setSize_(float asize_){
		size_ = asize_;
		mapSQLTokens["size_"] = to_string(size_);
		return *this;
	}
	float getSize_() {
		return size_;
	}
	
	
	T6org_data& setDownload_path(string adownload_path){
		download_path = adownload_path;
		
		mapSQLTokens["download_path"] = "'"+download_path+"'";
		return *this;
	}
	string getDownload_path() {
		return download_path;
	}
	
	
	T6org_data& setStorage_path(string astorage_path){
		storage_path = astorage_path;
		
		mapSQLTokens["storage_path"] = "'"+storage_path+"'";
		return *this;
	}
	string getStorage_path() {
		return storage_path;
	}
	
	
	T6org_data& setType_(string atype_){
		type_ = atype_;
		
		mapSQLTokens["type_"] = "'"+type_+"'";
		return *this;
	}
	string getType_() {
		return type_;
	}
	
	
	T6org_data& setRow_column(string arow_column){
		row_column = arow_column;
		
		mapSQLTokens["row_column"] = "'"+row_column+"'";
		return *this;
	}
	string getRow_column() {
		return row_column;
	}
	
	
	T6org_data& setBand_number(string aband_number){
		band_number = aband_number;
		
		mapSQLTokens["band_number"] = "'"+band_number+"'";
		return *this;
	}
	string getBand_number() {
		return band_number;
	}
	
	
	T6org_data& setStatus_(string astatus_){
		status_ = astatus_;
		
		mapSQLTokens["status_"] = "'"+status_+"'";
		return *this;
	}
	string getStatus_() {
		return status_;
	}
	
	
	T6org_data& setFail_code(string afail_code){
		fail_code = afail_code;
		
		mapSQLTokens["fail_code"] = "'"+fail_code+"'";
		return *this;
	}
	string getFail_code() {
		return fail_code;
	}
	
	
	T6org_data& setCollect_time(string acollect_time){
		collect_time = acollect_time;
		
		mapSQLTokens["collect_time"] = "'"+collect_time+"'";
		return *this;
	}
	string getCollect_time() {
		return collect_time;
	}
	
	
	T6org_data& setDload_start_time(string adload_start_time){
		dload_start_time = adload_start_time;
		
		mapSQLTokens["dload_start_time"] = "'"+dload_start_time+"'";
		return *this;
	}
	string getDload_start_time() {
		return dload_start_time;
	}
	
	
	T6org_data& setDload_end_time(string adload_end_time){
		dload_end_time = adload_end_time;
		
		mapSQLTokens["dload_end_time"] = "'"+dload_end_time+"'";
		return *this;
	}
	string getDload_end_time() {
		return dload_end_time;
	}
	
};
const string T6org_data::TableName = "t6org_data";
