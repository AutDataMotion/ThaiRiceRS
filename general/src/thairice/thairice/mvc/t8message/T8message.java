package thairice.mvc.t8message;

import com.platform.annotation.Table;
import com.platform.constant.ConstantInit;
import com.platform.mvc.base.BaseModel;

import java.sql.Timestamp; 
import java.math.BigInteger; 

import org.apache.log4j.Logger;

/**
 * @description：
 * @author ZW
 */
@SuppressWarnings("unused")
//@Table(tableName = "t8message")
public class T8message extends BaseModel<T8message> {

	private static final long serialVersionUID = 6761767368352810428L;

	private static Logger log = Logger.getLogger(T8message.class);
	
	public static final T8message dao = new T8message();
	
	/**
	 * 字段描述：系统消息编号 
	 * 字段类型：bigint  长度：null
	 */
	public static final String column_id = "id";
	
	/**
	 * 字段描述：发送人ID 
	 * 字段类型：bigint  长度：null
	 */
	public static final String column_send_userid = "send_userid";
	
	/**
	 * 字段描述：系统消息时间 
	 * 字段类型：datetime  长度：null
	 */
	public static final String column_add_time = "add_time";
	
	/**
	 * 字段描述：系统消息内容 
	 * 字段类型：text  长度：65535
	 */
	public static final String column_content = "content";
	
	/**
	 * 字段描述：消息类型代码 
	 * 字段类型：enum  长度：2
	 */
	public static final String column_type_ = "type_";
	
	/**
	 * 字段描述：群发标志 
	 * 字段类型：tinyint  长度：null
	 */
	public static final String column_flag = "flag";
	
	
	/**
	 * sqlId : thairice.t8message.splitPageFrom
	 * 描述：分页from
	 */
	public static final String sqlId_splitPage_from = "thairice.t8message.splitPageFrom";

	private BigInteger id;
	private BigInteger send_userid;
	private Timestamp add_time;
	private String content;
	private String type_;
	private Boolean flag;

	public void setId(BigInteger id){
		set(column_id, id);
	}
	public <T> T getId() {
		return get(column_id);
	}
	public void setSend_userid(BigInteger send_userid){
		set(column_send_userid, send_userid);
	}
	public <T> T getSend_userid() {
		return get(column_send_userid);
	}
	public void setAdd_time(Timestamp add_time){
		set(column_add_time, add_time);
	}
	public <T> T getAdd_time() {
		return get(column_add_time);
	}
	public void setContent(String content){
		set(column_content, content);
	}
	public <T> T getContent() {
		return get(column_content);
	}
	public void setType_(String type_){
		set(column_type_, type_);
	}
	public <T> T getType_() {
		return get(column_type_);
	}
	public void setFlag(Boolean flag){
		set(column_flag, flag);
	}
	public <T> T getFlag() {
		return get(column_flag);
	}
	
}
