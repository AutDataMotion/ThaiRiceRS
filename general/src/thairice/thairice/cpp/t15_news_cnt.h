/**  
 * Date:	 - -  : :   
 * project:	   
 * fileName:	t15_news_cnt.h  
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

class t15_news_cnt :public AbsDAL {

public:
	t15_news_cnt():AbsDAL(&TableName){}
	~t15_news_cnt(){}
	
private:
	static const string TableName;
	/**
	 * 字段描述：新闻id 
	 * 字段类型：bigint  长度：null
	 */
	long newsid;
	/**
	 * 字段描述：用户id 
	 * 字段类型：bigint  长度：null
	 */
	long usrid;
	/**
	 * 字段描述：标题 
	 * 字段类型：varchar  长度：256
	 */
	string title;
	/**
	 * 字段描述：内容 
	 * 字段类型：varchar  长度：3000
	 */
	string content;
	/**
	 * 字段描述：修改时间 
	 * 字段类型：timestamp  长度：null
	 */
	string editTime;
	/**
	 * 字段描述：时间戳 
	 * 字段类型：timestamp  长度：null
	 */
	string tms;
	/**
	 * 字段描述：排序 
	 * 字段类型：int  长度：null
	 */
	int rank;
public:
	
	
	t15_news_cnt& setNewsid(long anewsid){
		newsid = anewsid;
		mapSQLTokens["newsid"] = to_string(newsid);
		return *this;
	}
	long getNewsid() {
		return newsid;
	}
	
	
	t15_news_cnt& setUsrid(long ausrid){
		usrid = ausrid;
		mapSQLTokens["usrid"] = to_string(usrid);
		return *this;
	}
	long getUsrid() {
		return usrid;
	}
	
	
	t15_news_cnt& setTitle(string atitle){
		title = atitle;
		
		mapSQLTokens["title"] = "'"+title+"'";
		return *this;
	}
	string getTitle() {
		return title;
	}
	
	
	t15_news_cnt& setContent(string acontent){
		content = acontent;
		
		mapSQLTokens["content"] = "'"+content+"'";
		return *this;
	}
	string getContent() {
		return content;
	}
	
	
	t15_news_cnt& setEditTime(string aeditTime){
		editTime = aeditTime;
		
		mapSQLTokens["editTime"] = "'"+editTime+"'";
		return *this;
	}
	string getEditTime() {
		return editTime;
	}
	
	
	t15_news_cnt& setTms(string atms){
		tms = atms;
		
		mapSQLTokens["tms"] = "'"+tms+"'";
		return *this;
	}
	string getTms() {
		return tms;
	}
	
	
	t15_news_cnt& setRank(int arank){
		rank = arank;
		mapSQLTokens["rank"] = to_string(rank);
		return *this;
	}
	int getRank() {
		return rank;
	}
	
};
const string t15_news_cnt::TableName = "t15_news_cnt";
