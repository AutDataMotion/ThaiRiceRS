/**
 * <p>title:MdlKeyValue.java<／p>
 * <p>Description: <／p>
 * @date:2016年5月25日下午3:21:18
 * @author：ZhongwengHao email:zhongweng.hao@qq.com
 * @version 1.0
 */
package com.platform.common.mdl;

import java.io.Serializable;

/**  
 * 创建时间：2016年5月25日 下午3:21:18  
 * 项目名称：DUCPlatFormWeb   
 * 文件名称：MdlKeyValue.java  
 * 类说明：  
 *
 * Modification History:   
 * Date        Author         Version      Description   
 * ----------------------------------------------------------------- 
 * 2016年5月25日     Zhongweng       1.0         1.0 Version   
 */
/**
 * <p>Title: MdlKeyValue<／p>
 * <p>Description: <／p>
 * @author ZhongwengHao
 * @date 2016年5月25日
 */
public class MdlKeyValue implements Serializable{

	private String key;
	private String value;
	
	public MdlKeyValue(){}

	public MdlKeyValue(String akey, String avalue){
		this.key = akey;
		this.value = avalue;
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
