/**
 * ThaiRiceRS
 * create by zw at 2018年4月6日
 * version: v1.0
 */
package thairice.mvc.sysmonitor;

import java.io.Serializable;

/**
 * @author zw
 *
 */
public class ResSysmonitor implements Serializable {
	private Double[] cpus;
	private Long[] mems;

	public ResSysmonitor() {
	}

	/**
	 * @return the cpus
	 */
	public Double[] getCpus() {
		return cpus;
	}

	/**
	 * @param cpus
	 *            the cpus to set
	 */
	public void setCpus(Double[] cpus) {
		this.cpus = cpus;
	}

	/**
	 * @return the mems
	 */
	public Long[] getMems() {
		return mems;
	}

	/**
	 * @param mems
	 *            the mems to set
	 */
	public void setMems(Long[] mems) {
		this.mems = mems;
	}

}
