/**
 * ThaiRiceRS
 * create by zw at 2018年3月17日
 * version: v1.0
 */
package thairice.mvc.t2syslog;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * @author zw
 *
 */
public class ResT2syslogSearch implements Serializable {
	private BigInteger id;
	private String type_;
	private BigInteger userid;
	private String userName;
	private String action_;
	private String content;
	private Timestamp add_time;
	
	public ResT2syslogSearch(){
	
	}

	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}


	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}


	/**
	 * @return the id
	 */
	public BigInteger getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(BigInteger id) {
		this.id = id;
	}

	/**
	 * @return the type_
	 */
	public String getType_() {
		return type_;
	}

	/**
	 * @param type_ the type_ to set
	 */
	public void setType_(String type_) {
		this.type_ = type_;
	}

	/**
	 * @return the userid
	 */
	public BigInteger getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(BigInteger userid) {
		this.userid = userid;
	}

	/**
	 * @return the action_
	 */
	public String getAction_() {
		return action_;
	}

	/**
	 * @param action_ the action_ to set
	 */
	public void setAction_(String action_) {
		this.action_ = action_;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the add_time
	 */
	public Timestamp getAdd_time() {
		return add_time;
	}

	/**
	 * @param add_time the add_time to set
	 */
	public void setAdd_time(Timestamp add_time) {
		this.add_time = add_time;
	}
	
	
}
