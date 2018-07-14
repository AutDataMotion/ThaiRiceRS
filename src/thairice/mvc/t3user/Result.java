package thairice.mvc.t3user;
/**  
 * 创建时间：2018年3月15日  
 * 项目名称：   
 * 文件名称：Result.java  
 * 类说明：  后台返给界面html的结果代码及描述
 *
 * Modification History:   
 * Date        Author         Version      Description   
 * ----------------------------------------------------------------- 
 * 2018年3月15日     zhuchaobin      1.0         1.0 Version 
 */
public class Result {
	private int code;
	private String desc;
	public Result(int code, String desc) {
		// TODO Auto-generated constructor stub
		// 返回错误码
		this.code = code;
		// 返回错误描述
		this.desc = desc;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
