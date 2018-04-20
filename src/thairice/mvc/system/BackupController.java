/**
 * ThaiRiceRS
 * create by zw at 2018年4月17日
 * version: v1.0
 */
package thairice.mvc.system;

import com.jfinal.aop.Clear;
import com.jfinal.log.Logger;
import com.platform.mvc.base.BaseController;

import csuduc.platform.util.FileUtils;
import csuduc.platform.util.JsonUtils;

/**
 * @author zw
 *
 */
public class BackupController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(BackupController.class);

	// 获取文件目录接口
	@Clear
	public void ajaxGetFolderList() {
		// 获取检索条件
		String strvalue = getPara("v");
		if (null == strvalue || strvalue.isEmpty()) {
			renderText("-1");
			return;
		}
		log.debug(strvalue);
		// 转化为Model
		ParamBackupFolderList paramMdl = null;
		try {
			paramMdl = JsonUtils.deserialize(strvalue, ParamBackupFolderList.class);
			if (null == paramMdl) {
				renderText("-1");
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			renderText("-1");// 错误
			return;
		}
		// 判断什么数据，获得对应的文件目录路径

		// 根据目录路径 获得其子目录 限定2级

		// 返回json子目录名称对象

	}

	// 备份文件接口
	@Clear
	public void ajaxBackupFolder() {
		// 获取检索条件
		String strvalue = getPara("v");
		if (null == strvalue || strvalue.isEmpty()) {
			renderText("-1");
			return;
		}
		log.debug(strvalue);
		// 转化为Model
		ParamBackupFolder paramMdl = null;
		try {
			paramMdl = JsonUtils.deserialize(strvalue, ParamBackupFolder.class);
			if (null == paramMdl) {
				renderText("-1");
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			renderText("-1");// 错误
			return;
		}
		
		// 文件夹 备份，并记录进度
		
	}

	// 查询备份进度接口
	@Clear
	public void ajaxBackupProgress() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.platform.mvc.base.BaseController#setViewPath()
	 */
	@Override
	protected void setViewPath() {
		// TODO Auto-generated method stub

	}

}
