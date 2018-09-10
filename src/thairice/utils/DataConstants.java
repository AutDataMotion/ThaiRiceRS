package thairice.utils;

import java.math.BigInteger;
import java.util.HashMap;

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

	// 用户ID：系统自动执行的动作日志使用
	public static final BigInteger SYS_USER_ID = new BigInteger("99999999");
	
	// 用户名：系统自动执行的动作日志使用
	public static final String SYS_USER_NM = "System";
	
	// 泰国全境，条带行列号
	public static final String STR_H26V06 = "h26v06";
	public static final String STR_H27V06 = "h27v06";
	public static final String STR_H27V07 = "h27v07";
	public static final String STR_H27V08 = "h27v08";
	public static final String STR_H28V07 = "h28v07";
	public static final String STR_H28V08 = "h28v08";
	
	// 原始数据状态
	public static final HashMap<String, String> ORG_DATA_STATUS = new HashMap<String, String>();
	static {
		ORG_DATA_STATUS.put("01", "Download success");
		ORG_DATA_STATUS.put("02", "Download failed");
		ORG_DATA_STATUS.put("03", "Downloading");
		ORG_DATA_STATUS.put("04", "Process success");
		ORG_DATA_STATUS.put("05", "Process failed");
		ORG_DATA_STATUS.put("06", "Processing");
		ORG_DATA_STATUS.put("07", "Not download");
	}
	
	// 原始数据类型
	public static final HashMap<String, String> ORG_DATA_TYPE = new HashMap<String, String>();
	static {
		ORG_DATA_TYPE.put("01","NDVI_1"); 
		ORG_DATA_TYPE.put("02","NDVI_02");
		ORG_DATA_TYPE.put("03","LST");    
		ORG_DATA_TYPE.put("04","CLASS");  
		ORG_DATA_TYPE.put("05","LANDSAT");
	}
	
	// 文件路径标志
	public static final String ORIG_DATA_02_FLAG = "MOD13Q1"; //NDVI_02(长势和估产）
	public static final String ORIG_DATA_01_FLAG = "MOD13A2"; //NDVI_1（干旱）
	public static final String ORIG_DATA_03_FLAG = "MOD11A2"; //LST（干旱）
	public static final String ORIG_DATA_04_FLAG = "MOD12Q1"; //(长势和估产）
	
	// 产品类型
	public static final HashMap<String, String> PDT_DATA_TYPE = new HashMap<String, String>();
	static {
		PDT_DATA_TYPE.put("01","Area monitoring"); 
		PDT_DATA_TYPE.put("02","Drought monitoring");
		PDT_DATA_TYPE.put("03","Production estimates");    
		PDT_DATA_TYPE.put("04","Condition monitoring");
	}
	// 一年中有数据的加利洛日
//	String[] DAY_OF_JL = {"001","009","017","025","033","041","049","057","065","073","081","089","097","105","113","121","129","137","145","153","161","169","177","185","193","201","209","217","225","233","241","249","257","265","273","281","289","297","305","313","321","329","337","345","353","361"};
	// 03:MOD11A2
	public static final String DAYS_OF_JL_03 = "001,009,017,025,033,041,049,057,065,073,081,089,097,105,113,121,129,137,145,153,161,169,177,185,193,201,209,217,225,233,241,249,257,265,273,281,289,297,305,313,321,329,337,345,353,361";
	// 02:MOD13Q1 (长势和估产）和 01:MOD13A2（干旱）
	public static final String DAYS_OF_JL_01_02 = "001,017,033,049,065,081,097,113,129,145,161,177,193,209,225,241,257,273,289,305,321,337,353";
	// NASA网址前缀
	public static final String NASA_RUL_PRE = "https://ladsweb.modaps.eosdis.nasa.gov/archive";
	
	// 下载方式，01：ftp  02：wget
	public static final String DOWNLOAD_TYPE_FTP = "01";
	public static final String DOWNLOAD_TYPE_WGET = "02";
	
}
