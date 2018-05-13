/**  
 * Date:	 - -  : :   
 * project:	   
 * fileName:	T7pdt_data.h  
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

class T7pdt_data :public AbsDAL {

public:
	T7pdt_data():AbsDAL(&TableName){}
	~T7pdt_data(){}
	
private:
	static const string TableName;
	/**
	 * 字段描述：数据产品编号 
	 * 字段类型：bigint  长度：null
	 */
	biginteger id;
	/**
	 * 字段描述：产品类型代码 
	 * 字段类型：enum  长度：2
	 */
	string type_;
	/**
	 * 字段描述：源文件列表 
	 * 字段类型：varchar  长度：256
	 */
	string source_file_list;
	/**
	 * 字段描述：样本路径 
	 * 字段类型：varchar  长度：256
	 */
	string sample_path;
	/**
	 * 字段描述：产品路径 
	 * 字段类型：varchar  长度：256
	 */
	string product_path;
	/**
	 * 字段描述：数据采集时间 
	 * 字段类型：datetime  长度：null
	 */
	string collect_time;
	/**
	 * 字段描述：产品生成时间 
	 * 字段类型：datetime  长度：null
	 */
	string generate_time;
	/**
	 * 字段描述：行政区域代码 
	 * 字段类型：char  长度：4
	 */
	string zone_code;
	/**
	 * 字段描述：文件下载失败原因代码 
	 * 字段类型：enum  长度：2
	 */
	string fail_code;
public:
	
	
	T7pdt_data& setId(biginteger aid){
		id = aid;
		mapSQLTokens["id"] = to_string(id);
		return *this;
	}
	biginteger getId() {
		return id;
	}
	
	
	T7pdt_data& setType_(string atype_){
		type_ = atype_;
		
		mapSQLTokens["type_"] = "'"+type_+"'";
		return *this;
	}
	string getType_() {
		return type_;
	}
	
	
	T7pdt_data& setSource_file_list(string asource_file_list){
		source_file_list = asource_file_list;
		
		mapSQLTokens["source_file_list"] = "'"+source_file_list+"'";
		return *this;
	}
	string getSource_file_list() {
		return source_file_list;
	}
	
	
	T7pdt_data& setSample_path(string asample_path){
		sample_path = asample_path;
		
		mapSQLTokens["sample_path"] = "'"+sample_path+"'";
		return *this;
	}
	string getSample_path() {
		return sample_path;
	}
	
	
	T7pdt_data& setProduct_path(string aproduct_path){
		product_path = aproduct_path;
		
		mapSQLTokens["product_path"] = "'"+product_path+"'";
		return *this;
	}
	string getProduct_path() {
		return product_path;
	}
	
	
	T7pdt_data& setCollect_time(string acollect_time){
		collect_time = acollect_time;
		
		mapSQLTokens["collect_time"] = "'"+collect_time+"'";
		return *this;
	}
	string getCollect_time() {
		return collect_time;
	}
	
	
	T7pdt_data& setGenerate_time(string agenerate_time){
		generate_time = agenerate_time;
		
		mapSQLTokens["generate_time"] = "'"+generate_time+"'";
		return *this;
	}
	string getGenerate_time() {
		return generate_time;
	}
	
	
	T7pdt_data& setZone_code(string azone_code){
		zone_code = azone_code;
		
		mapSQLTokens["zone_code"] = "'"+zone_code+"'";
		return *this;
	}
	string getZone_code() {
		return zone_code;
	}
	
	
	T7pdt_data& setFail_code(string afail_code){
		fail_code = afail_code;
		
		mapSQLTokens["fail_code"] = "'"+fail_code+"'";
		return *this;
	}
	string getFail_code() {
		return fail_code;
	}
	
};
const string T7pdt_data::TableName = "t7pdt_data";
