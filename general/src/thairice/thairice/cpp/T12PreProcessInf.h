/**  
 * Date:	 - -  : :   
 * project:	   
 * fileName:	T12PreProcessInf.h  
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

class T12PreProcessInf :public AbsDAL {

public:
	T12PreProcessInf():AbsDAL(&TableName){}
	~T12PreProcessInf(){}
	
private:
	static const string TableName;
	/**
	 * 字段描述：预处理数据编号 
	 * 字段类型：bigint  长度：null
	 */
	long id;
	/**
	 * 字段描述：预处理数据类型 
	 * 字段类型：enum  长度：2
	 */
	string data_type;
	/**
	 * 字段描述：源文件列表 
	 * 字段类型：varchar  长度：3000
	 */
	string source_file_list;
	/**
	 * 字段描述：预处理文件路径 
	 * 字段类型：varchar  长度：80
	 */
	string file_path;
	/**
	 * 字段描述：预处理文件名 
	 * 字段类型：varchar  长度：80
	 */
	string file_name;
	/**
	 * 字段描述：数据采集时间 
	 * 字段类型：datetime  长度：null
	 */
	string data_collect_time;
	/**
	 * 字段描述：干旱监测处理状态 
	 * 字段类型：enum  长度：2
	 */
	string drought_st;
	/**
	 * 字段描述：长势监测处理状态 
	 * 字段类型：enum  长度：2
	 */
	string condition_st;
	/**
	 * 字段描述：估产处理状态 
	 * 字段类型：enum  长度：2
	 */
	string estimate_st;
	/**
	 * 字段描述：预处理数据生成时间 
	 * 字段类型：datetime  长度：null
	 */
	string generate_time;
public:
	
	
	T12PreProcessInf& setId(long aid){
		id = aid;
		mapSQLTokens["id"] = to_string(id);
		return *this;
	}
	long getId() {
		return id;
	}
	
	
	T12PreProcessInf& setData_type(string adata_type){
		data_type = adata_type;
		
		mapSQLTokens["data_type"] = "'"+data_type+"'";
		return *this;
	}
	string getData_type() {
		return data_type;
	}
	
	
	T12PreProcessInf& setSource_file_list(string asource_file_list){
		source_file_list = asource_file_list;
		
		mapSQLTokens["source_file_list"] = "'"+source_file_list+"'";
		return *this;
	}
	string getSource_file_list() {
		return source_file_list;
	}
	
	
	T12PreProcessInf& setFile_path(string afile_path){
		file_path = afile_path;
		
		mapSQLTokens["file_path"] = "'"+file_path+"'";
		return *this;
	}
	string getFile_path() {
		return file_path;
	}
	
	
	T12PreProcessInf& setFile_name(string afile_name){
		file_name = afile_name;
		
		mapSQLTokens["file_name"] = "'"+file_name+"'";
		return *this;
	}
	string getFile_name() {
		return file_name;
	}
	
	
	T12PreProcessInf& setData_collect_time(string adata_collect_time){
		data_collect_time = adata_collect_time;
		
		mapSQLTokens["data_collect_time"] = "'"+data_collect_time+"'";
		return *this;
	}
	string getData_collect_time() {
		return data_collect_time;
	}
	
	
	T12PreProcessInf& setDrought_st(string adrought_st){
		drought_st = adrought_st;
		
		mapSQLTokens["drought_st"] = "'"+drought_st+"'";
		return *this;
	}
	string getDrought_st() {
		return drought_st;
	}
	
	
	T12PreProcessInf& setCondition_st(string acondition_st){
		condition_st = acondition_st;
		
		mapSQLTokens["condition_st"] = "'"+condition_st+"'";
		return *this;
	}
	string getCondition_st() {
		return condition_st;
	}
	
	
	T12PreProcessInf& setEstimate_st(string aestimate_st){
		estimate_st = aestimate_st;
		
		mapSQLTokens["estimate_st"] = "'"+estimate_st+"'";
		return *this;
	}
	string getEstimate_st() {
		return estimate_st;
	}
	
	
	T12PreProcessInf& setGenerate_time(string agenerate_time){
		generate_time = agenerate_time;
		
		mapSQLTokens["generate_time"] = "'"+generate_time+"'";
		return *this;
	}
	string getGenerate_time() {
		return generate_time;
	}
	
};
const string T12PreProcessInf::TableName = "t12preprocessinf";
