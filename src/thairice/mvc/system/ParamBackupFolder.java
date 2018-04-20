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
public class ParamBackupFolder implements Serializable {
	private String sourcePath;
	private String targetPath;
	
	public ParamBackupFolder(){}

	/**
	 * @return the sourcePath
	 */
	public String getSourcePath() {
		return sourcePath;
	}

	/**
	 * @param sourcePath the sourcePath to set
	 */
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	/**
	 * @return the targetPath
	 */
	public String getTargetPath() {
		return targetPath;
	}

	/**
	 * @param targetPath the targetPath to set
	 */
	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}
	
	
}
