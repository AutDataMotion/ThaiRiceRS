/**
 * 
 */
package thairice.entity;

/**
 * @author Admin
 *
 */
public class Result {

	private int rc;
	private String errorMsg ;
	public int getRc() {
		return rc;
	}
	public void setRc(int rc) {
		this.rc = rc;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Result(int rc, String errorMsg) {
		this.rc = rc;
		this.errorMsg = errorMsg;
	}
	
	
}
