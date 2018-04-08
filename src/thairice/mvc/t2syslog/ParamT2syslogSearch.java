/**
 * ThaiRiceRS
 * create by zw at 2018年3月17日
 * version: v1.0
 */
package thairice.mvc.t2syslog;

import java.io.Serializable;
import java.util.Map;

import csuduc.platform.util.ReflectionUtils;

/**
 * @author zw
 *
 */
public class ParamT2syslogSearch implements Serializable{
	private Long userId;
	private String userName;
	private String dateTimeBeg;
	private String dateTimeEnd;
	
	private Integer pageIndex;
	private Integer pageSize;
	
	ParamT2syslogSearch(){}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
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
	 * @return the dateTimeBeg
	 */
	public String getDateTimeBeg() {
		return dateTimeBeg;
	}

	/**
	 * @param dateTimeBeg the dateTimeBeg to set
	 */
	public void setDateTimeBeg(String dateTimeBeg) {
		dateTimeBeg = dateTimeBeg;
	}

	/**
	 * @return the dateTimeEnd
	 */
	public String getDateTimeEnd() {
		return dateTimeEnd;
	}

	/**
	 * @param dateTimeEnd the dateTimeEnd to set
	 */
	public void setDateTimeEnd(String dateTimeEnd) {
		dateTimeEnd = dateTimeEnd;
	}

	/**
	 * @return the pageIndex
	 */
	public Integer getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public static void main(String[] args){
		ParamT2syslogSearch obj = new ParamT2syslogSearch();
		obj.setUserId(123L);
		obj.setUserName("name");
		obj.setPageIndex(1);
		@SuppressWarnings("unchecked")
		Map<String, Object> map = ReflectionUtils.convertBean2Map(obj);
		map.entrySet().forEach(e->System.out.println(""+e.getKey()+" "+e.getValue()));
	}
	
}
