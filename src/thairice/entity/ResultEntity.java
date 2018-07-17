package thairice.entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

import thairice.utils.FtpUtils;

/**  
 * 创建时间：2018年3月15日  
 * 项目名称：   
 * 文件名称：ResultEntity.java  
 * 类说明：  后台返给界面html的结果代码及描述
 *
 * Modification History:   
 * Date        Author         Version      Description   
 * ----------------------------------------------------------------- 
 * 2018年3月15日     zhuchaobin      1.0         1.0 Version 
 */
public class ResultEntity {
	private static Logger LOG = Logger.getLogger(ResultEntity.class);
	// 错误码
	private String code;
	// 错误描述
	private String desc;
public ResultEntity(String initCcode) {
		// TODO Auto-generated constructor stub
		// 返回错误码
		this.code = initCcode;
		this.desc = "";
		// 获取错误码配置
		Prop p =PropKit.use("ErrMsg.properties");
		if(null == p) {
			LOG.error("获取错误码配置信息失败");
			this.desc = "获取错误码配置信息失败";
		} else {
			String initDesc = p.get(initCcode);
			if(StringUtils.isBlank(initDesc)) {
				LOG.error("错误码描述在错误码配置文件中未定义");
				this.desc = "错误码描述在错误码配置文件中未定义";
			} else {
				// 返回错误描述
				LOG.debug("获取错误码描述成功：" + initDesc);
				this.desc = initDesc;
			}
		}
	}

public ResultEntity(String initCcode, String initDesc) {
	// TODO Auto-generated constructor stub
	// 返回错误码
	this.code = initCcode;
	// 返回错误描述
	this.desc = initDesc;
}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
