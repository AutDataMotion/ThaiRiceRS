package thairice.utils;

import java.math.BigInteger;

public class DataConstants {

	// FTP下载：文件状态代码
	// 01下载成功、02下载失败、03下载中、04处理成功、05处理失败、06处理中 07未下载
	public static final String DOWNLOAD_SUCCE = "01";
	public static final String DOWNLOAD_FAIL = "02";
	public static final String DOWNLOAD_ING = "03";
	public static final String PROCESS_SUCCE = "04";
	public static final String PROCESS_FAIL = "05";
	public static final String PROCESS_ING = "06";
	public static final String NOT_DOWNLOAD = "07";
	
	// 文件路径类型
	public static final String PATH_TYPE01 = "01";
	public static final String PATH_TYPE02 = "02";
	
	// 开关
	public static final String FLAGE_ON = "1";
	public static final String FLAGE_OFF = "0";
	
	// 波段数编码
	// MODIS的产品波段数统一为1
	public static final String MODIS_BANDNUM = "1";
	
	public static final String FLAGE_OFF2 = "00";

	// 用户ID：系统自动
	public static final BigInteger SYS_USER_ID = new BigInteger("99999999");
	
	// 泰国全境，条带行列号
	public static final String STR_H26V06 = "h26v06";
	public static final String STR_H27V06 = "h27v06";
	public static final String STR_H27V07 = "h27v07";
	public static final String STR_H27V08 = "h27v08";
	public static final String STR_H28V07 = "h28v07";
	public static final String STR_H28V08 = "h28v08";
}
