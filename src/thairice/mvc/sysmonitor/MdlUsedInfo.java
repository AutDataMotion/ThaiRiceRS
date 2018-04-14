/**
 * ThaiRiceRS
 * create by zw at 2018年4月7日
 * version: v1.0
 */
package thairice.mvc.sysmonitor;

import java.io.Serializable;

/**
 * @author zw
 *
 */
public class MdlUsedInfo implements Serializable {

	private Long total;
	private Long used;
	private Long idle;
	private Integer rate;
	private String path; // 挂在点

	public MdlUsedInfo() {
	}

	/**
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}

	/**
	 * @return the used
	 */
	public Long getUsed() {
		return used;
	}

	/**
	 * @param used
	 *            the used to set
	 */
	public void setUsed(Long used) {
		this.used = used;
	}

	/**
	 * @return the idle
	 */
	public Long getIdle() {
		return idle;
	}

	/**
	 * @param idle
	 *            the idle to set
	 */
	public void setIdle(Long idle) {
		this.idle = idle;
	}

	/**
	 * @return the rate
	 */
	public Integer getRate() {
		return rate;
	}

	/**
	 * @param rate
	 *            the rate to set
	 */
	public void setRate(Integer rate) {
		this.rate = rate;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("total:%d used:%d idle:%d  rate:%d  path:%s", total, used, idle, rate, path);
	}

}
