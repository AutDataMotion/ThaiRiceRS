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
	
	DOWNLOAD_SUCCE(1, "01","Download success"),
	DOWNLOAD_FAIL(2, "02","Download failed"),
	DOWNLOAD_ING(3, "03","Downloading"),
	DOWNLOAD_NOT(7, "07","Un download"),
	
	PROCESS_UN(11, "11","Un Process"),
	PROCESS_SUCCE(12, "12","Process success"),
	PROCESS_FAIL(13, "13","Process failed"),
	PROCESS_ING(14, "14","Processing"),
	
	
	
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
