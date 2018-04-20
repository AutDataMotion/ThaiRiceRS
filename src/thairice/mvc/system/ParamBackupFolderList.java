/**
 * ThaiRiceRS
 * create by zw at 2018年4月17日
 * version: v1.0
 */
package thairice.mvc.system;

import java.io.Serializable;

/**
 * @author zw
 *
 */
public class ParamBackupFolderList implements Serializable {
	private String dataName;
	private String dataType;
	
	public ParamBackupFolderList(){}
	/**
	 * @return the dataName
	 */
	public String getDataName() {
		return dataName;
	}
	/**
	 * @param dataName the dataName to set
	 */
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}
	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	

}
