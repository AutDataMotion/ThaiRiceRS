package thairice.constant;

import java.util.Map;
import java.util.Properties;

import com.jfinal.kit.PathKit;
import com.platform.constant.ConstantInit;
import com.platform.plugin.PropertiesPlugin;

/**  
 * 创建时间：2016年6月17日 上午11:05:40  
 * 项目名称：DUCPlatFormWeb   
 * 文件名称：PropertiesInitMy.java  
 * 类说明：  
 *
 * Modification History:   
 * Date        Author         Version      Description   
 * ----------------------------------------------------------------- 
 * 2016年6月17日     Zhongweng       1.0         1.0 Version   
 */

public final class PropertiesInitMy extends PropertiesPlugin {
	private static Map<String, Object> paramMapCur;

	/**
	 * @param properties
	 * @param aisComm
	 */
	public PropertiesInitMy(Properties properties, boolean aisComm) {
		super(properties, aisComm);
		// TODO Auto-generated constructor stub
	}

	public static Object getParamMapValue(String key) {
		if (paramMapCur.containsKey(key)) {
			// 先返回个性设置 如果有的话
			return paramMapCur.get(key);
		}
		// 否则去公共找
		return PropertiesPlugin.getParamMapValue(key);
	}

	/*
	 * (non-Javadoc) <p>Description: <／p>
	 * 
	 * @return
	 * 
	 * @see com.platform.plugin.PropertiesPlugin#start()
	 */
	@Override
	public boolean start() {
		log.info("-----property init special !!");
		// TODO Auto-generated method stub
		// 私有个性化设置 前提是需要先初始化公共配置
		if (!isCommnInit()) {
			log.error("common property should be inited first!!");
			return false;
		}
		// 设置数据库配置
		initDataBasePar(paramMapMy);

		// 扫描package
		initScanPackagePar(paramMapMy);
		// 扫描jar
		initScanJarPar(paramMapMy);

		putParamMap(paramMapMy, ConstantInit.config_devMode);
		putParamMap(paramMapMy, ConstantInit.config_luceneIndex);
		putParamMap(paramMapMy, ConstantInit.config_securityKey_key);
		putParamMapInteger(paramMapMy, ConstantInit.config_passErrorCount_key);
		putParamMapInteger(paramMapMy, ConstantInit.config_passErrorHour_key);
		putParamMapInteger(paramMapMy, ConstantInit.config_maxPostSize_key);
		putParamMapInteger(paramMapMy, ConstantInit.config_maxAge_key);
		putParamMapInteger(paramMapMy, ConstantInit.config_session_key);
		putParamMap(paramMapMy, ConstantInit.config_domain_key);

		// mail 配置
		initMail(paramMapMy);

		// 缓存类型配置
		putParamMap(paramMapMy, ConstantInit.config_cache_type);

		// redis 配置
		initRedisPra(paramMapMy);

		// ftp配置
		iniFtpPra(paramMapMy);
		// 图片上传路径
		String pathRoot = PathKit.getWebRootPath();
		paramMapMy.put(ConstantInit.upload_img_path, pathRoot
				+ properties.getProperty(ConstantInit.upload_img_path).trim());

		//打印
		printProperty(paramMapMy);
		//设为静态
		paramMapCur = super.paramMapMy;
		return true;

	}

	/*
	 * (non-Javadoc) <p>Description: <／p>
	 * 
	 * @return
	 * 
	 * @see com.platform.plugin.PropertiesPlugin#stop()
	 */
	@Override
	public boolean stop() {
		// TODO Auto-generated method stub
		return super.stop();
	}
}
