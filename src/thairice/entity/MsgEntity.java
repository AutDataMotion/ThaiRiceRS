package thairice.entity;

import java.math.BigInteger;
import java.sql.Timestamp;

public class MsgEntity {
	private BigInteger id;
	private BigInteger send_userid;
	private Timestamp add_time;
	private String content;
	private String type_;
	private Boolean flag;
	private BigInteger receive_userid;
	private String status_;
	private String templateId;
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public BigInteger getSend_userid() {
		return send_userid;
	}
	public void setSend_userid(BigInteger send_userid) {
		this.send_userid = send_userid;
	}
	public Timestamp getAdd_time() {
		return add_time;
	}
	public void setAdd_time(Timestamp add_time) {
		this.add_time = add_time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType_() {
		return type_;
	}
	public void setType_(String type_) {
		this.type_ = type_;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public BigInteger getReceive_userid() {
		return receive_userid;
	}
	public void setReceive_userid(BigInteger receive_userid) {
		this.receive_userid = receive_userid;
	}
	public String getStatus_() {
		return status_;
	}
	public void setStatus_(String status_) {
		this.status_ = status_;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

}
