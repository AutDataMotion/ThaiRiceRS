package thairice.mvc.t15_news_cnt;

import com.platform.annotation.Table;
import com.platform.constant.ConstantInit;
import com.platform.mvc.base.BaseModel;

import java.sql.Timestamp; 

import org.apache.log4j.Logger;

/**
 * @description：
 * @author ZW
 */
@SuppressWarnings("unused")
//@Table(tableName = "t15_news_cnt")
public class t15_news_cnt extends BaseModel<t15_news_cnt> {

	private static final long serialVersionUID = 6761767368352810428L;

	private static Logger log = Logger.getLogger(t15_news_cnt.class);
	
	public static final String  tableName = "t15_news_cnt";
	
	public static final t15_news_cnt dao = new t15_news_cnt();
	
	/**
	 * 字段描述：新闻id 
	 * 字段类型：bigint  长度：null
	 */
	public static final String column_newsid = "newsid";
	
	/**
	 * 字段描述：用户id 
	 * 字段类型：bigint  长度：null
	 */
	public static final String column_usrid = "usrid";
	
	/**
	 * 字段描述：标题 
	 * 字段类型：varchar  长度：256
	 */
	public static final String column_title = "title";
	
	/**
	 * 字段描述：内容 
	 * 字段类型：varchar  长度：3000
	 */
	public static final String column_content = "content";
	
	/**
	 * 字段描述：修改时间 
	 * 字段类型：timestamp  长度：null
	 */
	public static final String column_editTime = "editTime";
	
	/**
	 * 字段描述：时间戳 
	 * 字段类型：timestamp  长度：null
	 */
	public static final String column_tms = "tms";
	
	/**
	 * 字段描述：排序 
	 * 字段类型：int  长度：null
	 */
	public static final String column_rank = "rank";
	
	
	/**
	 * sqlId : thairice.t15_news_cnt.splitPageFrom
	 * 描述：分页from
	 */
	public static final String sqlId_splitPage_from = "thairice.t15_news_cnt.splitPageFrom";

	private Long newsid;
	private Long usrid;
	private String title;
	private String content;
	private Timestamp editTime;
	private Timestamp tms;
	private Integer rank;

	public void setNewsid(Long newsid){
		set(column_newsid, newsid);
	}
	public <T> T getNewsid() {
		return get(column_newsid);
	}
	public void setUsrid(Long usrid){
		set(column_usrid, usrid);
	}
	public <T> T getUsrid() {
		return get(column_usrid);
	}
	public void setTitle(String title){
		set(column_title, title);
	}
	public <T> T getTitle() {
		return get(column_title);
	}
	public void setContent(String content){
		set(column_content, content);
	}
	public <T> T getContent() {
		return get(column_content);
	}
	public void setEditTime(Timestamp editTime){
		set(column_editTime, editTime);
	}
	public <T> T getEditTime() {
		return get(column_editTime);
	}
	public void setTms(Timestamp tms){
		set(column_tms, tms);
	}
	public <T> T getTms() {
		return get(column_tms);
	}
	public void setRank(Integer rank){
		set(column_rank, rank);
	}
	public <T> T getRank() {
		return get(column_rank);
	}
	
}
