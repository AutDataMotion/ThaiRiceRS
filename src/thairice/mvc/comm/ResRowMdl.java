/**
 * PureSport
 * create by zw at 2018年4月28日
 * version: v1.0
 */
package thairice.mvc.comm;

import java.io.Serializable;

/**
 * @author zw
 *
 */
public class ResRowMdl implements Serializable{
	private Object data;
	
	public ResRowMdl(){}
	
	public ResRowMdl(Object data){
		this.data = data;
	}

	/**
	 * @return the data
	 */
	public <T> T getData() {
		return (T) data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	
}
