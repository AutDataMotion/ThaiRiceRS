/**  
 * Date:	 - -  : :   
 * project:	   
 * fileName:	t16_pdt_sample.h  
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

class t16_pdt_sample :public AbsDAL {

public:
	t16_pdt_sample():AbsDAL(&TableName){}
	~t16_pdt_sample(){}
	
private:
	static const string TableName;
	/**
	 * 字段描述：id 
	 * 字段类型：bigint  长度：null
	 */
	long rank;
	/**
	 * 字段描述：rice_planting_area 
	 * 字段类型：varchar  长度：256
	 */
	string rice_planting_area;
	/**
	 * 字段描述：rice_drought 
	 * 字段类型：varchar  长度：256
	 */
	string rice_drought;
	/**
	 * 字段描述：rice_condition 
	 * 字段类型：varchar  长度：256
	 */
	string rice_condition;
	/**
	 * 字段描述：rice_production 
	 * 字段类型：varchar  长度：256
	 */
	string rice_production;
	/**
	 * 字段描述：图片路径 
	 * 字段类型：varchar  长度：256
	 */
	string pic_path;
public:
	
	
	t16_pdt_sample& setRank(long arank){
		rank = arank;
		mapSQLTokens["rank"] = to_string(rank);
		return *this;
	}
	long getRank() {
		return rank;
	}
	
	
	t16_pdt_sample& setRice_planting_area(string arice_planting_area){
		rice_planting_area = arice_planting_area;
		
		mapSQLTokens["rice_planting_area"] = "'"+rice_planting_area+"'";
		return *this;
	}
	string getRice_planting_area() {
		return rice_planting_area;
	}
	
	
	t16_pdt_sample& setRice_drought(string arice_drought){
		rice_drought = arice_drought;
		
		mapSQLTokens["rice_drought"] = "'"+rice_drought+"'";
		return *this;
	}
	string getRice_drought() {
		return rice_drought;
	}
	
	
	t16_pdt_sample& setRice_condition(string arice_condition){
		rice_condition = arice_condition;
		
		mapSQLTokens["rice_condition"] = "'"+rice_condition+"'";
		return *this;
	}
	string getRice_condition() {
		return rice_condition;
	}
	
	
	t16_pdt_sample& setRice_production(string arice_production){
		rice_production = arice_production;
		
		mapSQLTokens["rice_production"] = "'"+rice_production+"'";
		return *this;
	}
	string getRice_production() {
		return rice_production;
	}
	
	
	t16_pdt_sample& setPic_path(string apic_path){
		pic_path = apic_path;
		
		mapSQLTokens["pic_path"] = "'"+pic_path+"'";
		return *this;
	}
	string getPic_path() {
		return pic_path;
	}
	
};
const string t16_pdt_sample::TableName = "t16_pdt_sample";
