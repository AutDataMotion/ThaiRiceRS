/**
 * PureSport
 * create by zw at 2018年4月19日
 * version: v1.0
 */
package thairice.rpcjob;

/**
 * @author zw
 *
 */
public enum EnumDataStatus {
	
	// 01下载成功、02下载失败、03下载中、04处理成功、
	// 05处理失败、06处理中 07未下载
	DOWNLOAD_SUCCE(1, "01","Download success"),
	DOWNLOAD_FAIL(2, "02","Download failed"),
	DOWNLOAD_ING(3, "03","Downloading"),
	DOWNLOAD_NOT(7, "07","Un download"),
	
	PROCESS_UN(11, "11","Un Process"),
	PROCESS_SUCCE(12, "12","Process success"),
	PROCESS_FAIL(13, "13","Process failed"),
	PROCESS_ING(14, "14","Processing"),
	
	// 01NDVI_1（干旱）、02NDVI_02(长势和估产）、03LST（干旱）
	// 04CLASS（长势、干旱和估产）、05LANDSAT（面积）
	DATA_TYPE_NDVI_1(1, "01","NDVI_1"),
	DATA_TYPE_NDVI_02(2, "02","NDVI_02"),
	DATA_TYPE_LST(3, "03","LST"),
	DATA_TYPE_CLASS(4, "04","CLASS"),
	DATA_TYPE_LANDSAT(5, "05","LANDSAT"),
	
	PDT_TYPE_Area(1, "01","Area Monitoring"), // 面积
	PDT_TYPE_Drought(2, "02","Land Drought"), // 干旱
	PDT_TYPE_Yield(3, "03","Land Yield"),// 估产
	PDT_TYPE_Growth(4, "04","Growth Monitor"), // 长势
	
	
	
	;
	private int id;
	private String idStr;
	private String name;
	
	private EnumDataStatus(int aid, String aidStr,  String aname){
		id = aid;
		idStr = aidStr;
		name = aname;
	}

	public static String fetchDataTypeName(String typeIdStr){
		if (DATA_TYPE_NDVI_1.idStr.equals(typeIdStr)) {
			return DATA_TYPE_NDVI_1.getName();
		}
		if (DATA_TYPE_NDVI_02.idStr.equals(typeIdStr)) {
			return DATA_TYPE_NDVI_02.getName();
		}
		if (DATA_TYPE_LST.idStr.equals(typeIdStr)) {
			return DATA_TYPE_LST.getName();
		}
		if (DATA_TYPE_CLASS.idStr.equals(typeIdStr)) {
			return DATA_TYPE_CLASS.getName();
		}
		if (DATA_TYPE_LANDSAT.idStr.equals(typeIdStr)) {
			return DATA_TYPE_LANDSAT.getName();
		}
		return null;
	}
	public String getIdText(){
		return String.valueOf(id);
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the idStr
	 */
	public String getIdStr() {
		return idStr;
	}

	/**
	 * @param idStr the idStr to set
	 */
	public void setIdStr(String idStr) {
		this.idStr = idStr;
	} 
	
}
