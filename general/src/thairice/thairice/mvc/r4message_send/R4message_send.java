package thairice.mvc.r4message_send;

import com.platform.annotation.Table;
import com.platform.constant.ConstantInit;
import com.platform.mvc.base.BaseModel;

import java.math.BigInteger; 

import org.apache.log4j.Logger;

/**
 * @description：
 * @author ZW
 */
@SuppressWarnings("unused")
//@Table(tableName = "r4message_send")
public class R4message_send extends BaseModel<R4message_send> {

	private static final long serialVersionUID = 6761767368352810428L;

	private static Logger log = Logger.getLogger(R4message_send.class);
	
	public static final R4message_send dao = new R4message_send();
	
	/**
	 * 字段描述：系统消息编号 
	 * 字段类型：bigint  长度：null
	 */
	public static final String column_id = "id";
	
	/**
	 * 字段描述： 
	 * 字段类型：bigint  长度：null
	 */
	public static final String column_message_id = "message_id";
	
	/**
	 * 字段描述：接收人ID 
	 * 字段类型：bigint  长度：null
	 */
	public static final String column_receive_userid = "receive_userid";
	
	/**
	 * 字段描述：消息状态代码 
	 * 字段类型：enum  长度：2
	 */
	public static final String column_status_ = "status_";
	
	
	/**
	 * sqlId : thairice.r4message_send.splitPageFrom
	 * 描述：分页from
	 */
	public static final String sqlId_splitPage_from = "thairice.r4message_send.splitPageFrom";

	private BigInteger id;
	private Long message_id;
	private BigInteger receive_userid;
	private String status_;

	public void setId(BigInteger id){
		set(column_id, id);
	}
	public <T> T getId() {
		return get(column_id);
	}
	public void setMessage_id(Long message_id){
		set(column_message_id, message_id);
	}
	public <T> T getMessage_id() {
		return get(column_message_id);
	}
	public void setReceive_userid(BigInteger receive_userid){
		set(column_receive_userid, receive_userid);
	}
	public <T> T getReceive_userid() {
		return get(column_receive_userid);
	}
	public void setStatus_(String status_){
		set(column_status_, status_);
	}
	public <T> T getStatus_() {
		return get(column_status_);
	}
	
}
