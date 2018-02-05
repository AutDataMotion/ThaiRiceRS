package com.platform.mvc.param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.platform.mvc.base.BaseModelCache;
import com.platform.plugin.ParamInitPlugin;
import com.platform.tools.ToolCache;

/**
 * 参数model
 * @author DHJ
 */
@SuppressWarnings("unused")
//@Table(tableName = "pt_param")
public class Param extends BaseModelCache<Param> {
	
	private static final long serialVersionUID = 2051998642258015518L;

	private static Logger log = Logger.getLogger(Param.class);
	
	public static final Param dao = new Param();

	/**
	 * 字段描述：主键 
	 * 字段类型 ：character varying 
	 */
	public static final String column_ids = "ids";
	
	/**
	 * 字段描述：版本号 
	 * 字段类型 ：bigint 
	 */
	public static final String column_version = "version";
	
	/**
	 * 字段描述：描述 
	 * 字段类型 ：character varying 
	 */
	public static final String column_description = "description";
	
	/**
	 * 字段描述：图标 
	 * 字段类型 ：character varying 
	 */
	public static final String column_images = "images";
	
	/**
	 * 字段描述：名称 
	 * 字段类型 ：character varying 
	 */
	public static final String column_names = "names";
	
	/**
	 * 字段描述：排序号 
	 * 字段类型 ：bigint 
	 */
	public static final String column_orderids = "orderids";
	
	/**
	 * 字段描述：tree路径 
	 * 字段类型 ：character varying 
	 */
	public static final String column_paths = "paths";
	
	/**
	 * 字段描述：状态 
	 * 字段类型 ：character 
	 */
	public static final String column_zhuangtai = "zhuangtai";
	
	/**
	 * 字段描述：上级ids 
	 * 字段类型 ：character varying 
	 */
	public static final String column_parentids = "parentids";
	
	/**
	 * 字段描述：是否上级节点 
	 * 字段类型 ：character varying 
	 */
	public static final String column_isparent = "isparent";
	
	/**
	 * 字段描述：层级级别 
	 * 字段类型 ：bigint 
	 */
	public static final String column_levels = "levels";
	
	/**
	 * 字段描述：编号 
	 * 字段类型 ：character varying 
	 */
	public static final String column_numbers = "numbers";
	
	/**
	 * 字段描述：值 
	 * 字段类型 ：character varying 
	 */
	public static final String column_val = "val";
	
	/**
	 * 字段描述：是否国际化 : 0否1是 
	 * 字段类型 ：character 
	 */
	public static final String column_i18n = "i18n";
	
	/**
	 * 字段描述：中文简体值 
	 * 字段类型 ：character varying 
	 */
	public static final String column_val_zhcn = "val_zhcn";
	
	/**
	 * 字段描述：中文香港值 
	 * 字段类型 ：character varying 
	 */
	public static final String column_val_zhhk = "val_zhhk";
	
	/**
	 * 字段描述：中文台湾值 
	 * 字段类型 ：character varying 
	 */
	public static final String column_val_zhtw = "val_zhtw";
	
	/**
	 * 字段描述：日文值 
	 * 字段类型 ：character varying 
	 */
	public static final String column_val_ja = "val_ja";
	
	/**
	 * 字段描述：英文值 
	 * 字段类型 ：character varying 
	 */
	public static final String column_val_enus = "val_enus";
	
	/**
	 * 字段描述：账号状态 
	 * 字段类型 ：character 
	 */
	public static final String column_status = "status";

	/**
	 * sqlId : platform.param.all
	 * 描述：查询所有参数
	 */
	public static final String sqlId_all = "platform.param.all";
	
	public static final String sqlId_allid = "platform.param.allid";

	/**
	 * sqlId : platform.param.treeTableNodeRoot
	 * 描述：查询参数树根节点
	 */
	public static final String sqlId_treeTableNodeRoot = "platform.param.treeTableNodeRoot";

	/**
	 * sqlId : platform.param.treeTableChildNode
	 * 描述：查询参数树子节点
	 */
	public static final String sqlId_treeTableChildNode = "platform.param.treeTableChildNode";

	/**
	 * sqlId : platform.param.treeNodeRoot
	 * 描述：查询参数树根节点
	 */
	public static final String sqlId_treeNodeRoot = "platform.param.treeNodeRoot";

	/**
	 * sqlId : platform.param.treeChildNode
	 * 描述：查询参数树子节点
	 */
	public static final String sqlId_treeChildNode = "platform.param.treeChildNode";

	/**
	 * sqlId : platform.param.idAndI18n
	 * 描述：根据id查询国际化参数值 
	 */
	public static final String sqlId_idAndI18n = "platform.param.idAndI18n";

	/**
	 * sqlId : platform.param.numbers
	 * 描述：根据编号查询参数值
	 */
	public static final String sqlId_numbers = "platform.param.numbers";

	/**
	 * sqlId : platform.param.numbersAndI18n
	 * 描述：根据编号查询国际化参数值
	 */
	public static final String sqlId_numbersAndI18n = "platform.param.numbersAndI18n";

	/**
	 * sqlId : platform.param.child
	 * 描述：查询子节点参数值
	 */
	public static final String sqlId_child = "platform.param.child";

	/**
	 * sqlId : platform.param.childAndI8n
	 * 描述：查询子节点国际化参数值
	 */
	public static final String sqlId_childAndI8n = "platform.param.childAndI8n";

	/**
	 * sqlId : platform.param.parent
	 * 描述：查询父节点参数值
	 */
	public static final String sqlId_parent = "platform.param.parent";

	/**
	 * sqlId : platform.param.parentAndI18n
	 * 描述：查询父节点国际化参数值
	 */
	public static final String sqlId_parentAndI18n = "platform.param.parentAndI18n";

	/**
	 * sqlId : platform.param.childCount
	 * 描述：查询子节点数量
	 */
	public static final String sqlId_childCount = "platform.param.childCount";

	private String ids;
	private Long version;
	private String description;
	private String images;
	private String names;
	private Long orderids;
	private String paths;
	private String zhuangtai;
	private String parentids;
	private String isparent;
	private Long levels;
	private String numbers;
	private String val;
	private String i18n;
	private String val_zhcn;
	private String val_zhhk;
	private String val_zhtw;
	private String val_ja;
	private String val_enus;
	private String status;

	public void setIds(String ids){
		set(column_ids, ids);
	}
	public <T> T getIds() {
		return get(column_ids);
	}
	public void setVersion(Long version){
		set(column_version, version);
	}
	public <T> T getVersion() {
		return get(column_version);
	}
	public void setDescription(String description){
		set(column_description, description);
	}
	public <T> T getDescription() {
		return get(column_description);
	}
	public void setImages(String images){
		set(column_images, images);
	}
	public <T> T getImages() {
		return get(column_images);
	}
	public void setNames(String names){
		set(column_names, names);
	}
	public <T> T getNames() {
		return get(column_names);
	}
	public void setOrderids(Long orderids){
		set(column_orderids, orderids);
	}
	public <T> T getOrderids() {
		return get(column_orderids);
	}
	public void setPaths(String paths){
		set(column_paths, paths);
	}
	public <T> T getPaths() {
		return get(column_paths);
	}
	public void setZhuangtai(String zhuangtai){
		set(column_zhuangtai, zhuangtai);
	}
	public <T> T getZhuangtai() {
		return get(column_zhuangtai);
	}
	public void setParentids(String parentids){
		set(column_parentids, parentids);
	}
	public <T> T getParentids() {
		return get(column_parentids);
	}
	public void setIsparent(String isparent){
		set(column_isparent, isparent);
	}
	public <T> T getIsparent() {
		return get(column_isparent);
	}
	public void setLevels(Long levels){
		set(column_levels, levels);
	}
	public <T> T getLevels() {
		return get(column_levels);
	}
	public void setNumbers(String numbers){
		set(column_numbers, numbers);
	}
	public <T> T getNumbers() {
		return get(column_numbers);
	}
	public void setVal(String val){
		set(column_val, val);
	}
	public <T> T getVal() {
		return get(column_val);
	}
	public void setI18n(String i18n){
		set(column_i18n, i18n);
	}
	public <T> T getI18n() {
		return get(column_i18n);
	}
	public void setVal_zhcn(String val_zhcn){
		set(column_val_zhcn, val_zhcn);
	}
	public <T> T getVal_zhcn() {
		return get(column_val_zhcn);
	}
	public void setVal_zhhk(String val_zhhk){
		set(column_val_zhhk, val_zhhk);
	}
	public <T> T getVal_zhhk() {
		return get(column_val_zhhk);
	}
	public void setVal_zhtw(String val_zhtw){
		set(column_val_zhtw, val_zhtw);
	}
	public <T> T getVal_zhtw() {
		return get(column_val_zhtw);
	}
	public void setVal_ja(String val_ja){
		set(column_val_ja, val_ja);
	}
	public <T> T getVal_ja() {
		return get(column_val_ja);
	}
	public void setVal_enus(String val_enus){
		set(column_val_enus, val_enus);
	}
	public <T> T getVal_enus() {
		return get(column_val_enus);
	}
	public void setStatus(String status){
		set(column_status, status);
	}
	public <T> T getStatus() {
		return get(column_status);
	}
	
	/**
	 * 根据主键查询参数，带国际化
	 * @param ids 主键
	 * @param i18n 国际化参数
	 * @return
	 */
	public Param getByIds(String ids, String i18n){
		String val = "val" + i18n(i18n);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("val", val);
		
		String sql = getSqlByBeetl(sqlId_idAndI18n, paramMap);
		
		Param param = dao.findFirst(sql, ids);
		return param;
	}
	
	/**
	 * 根据编号查询参数
	 * @param number
	 * @return
	 */
	public Param getByNumber(String number){
		String sql = getSql(sqlId_numbers);
		Param param = dao.findFirst(sql, number);
		return param;
	}
	
	/**
	 * 根据编号查询参数，带国际化
	 * @param number 编号
	 * @param i18n 国际化参数
	 * @return
	 */
	public Param getByNumber(String number, String i18n){
		String val = "val" + i18n(i18n);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("val", val);
		
		String sql = getSqlByBeetl(sqlId_numbersAndI18n, paramMap);
		
		Param param = dao.findFirst(sql, number);
		return param;
	}

	/**
	 * 查询子节点参数
	 * @return
	 */
	public List<Param> getChild(){
		String sql = getSql(sqlId_child);
		return dao.find(sql, getPKValue());
	}

	/**
	 * 查询子节点参数
	 * @param prentIds
	 * @return
	 */
	public List<Param> getChild(String prentIds){
		String sql = getSql(sqlId_child);
		return dao.find(sql, prentIds);
	}

	/**
	 * 查询子节点参数，国际化
	 * @param prentIds
	 * @param i18n
	 * @return
	 */
	public List<Param> getChild(String prentIds, String i18n){
		String val = "val" + i18n(i18n);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("val", val);
		
		String sql = getSqlByBeetl(sqlId_childAndI8n, param);
		
		return dao.find(sql, prentIds);
	}

	/**
	 * 查询父节点参数
	 * @return
	 */
	public Param getParent(){
		String sql = getSql(sqlId_parent);
		return dao.findFirst(sql, get(column_parentids));
	}

	/**
	 * 查询父节点参数，国际化
	 * @param i18n
	 * @return
	 */
	public Param getParent(String i18n){
		String val = "val" + i18n(i18n);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("val", val);
		
		String sql = getSqlByBeetl(sqlId_parentAndI18n, param);
		
		return dao.findFirst(sql, get(column_parentids));
	}

	/**
	 * 添加或者更新缓存
	 */
	public void cacheAdd(String ids){
		Param param = Param.dao.findById(ids);
		cacheAdd(param);
	}
	
	public void cacheAdd(Param param){
		List<Param> paramList = param.getChild();
		ToolCache.set(ParamInitPlugin.cacheStart_param + param.getStr(column_ids), param);
		ToolCache.set(ParamInitPlugin.cacheStart_param + param.getStr(column_numbers), param);
		ToolCache.set(ParamInitPlugin.cacheStart_param_child + param.getStr(column_ids), paramList);
		ToolCache.set(ParamInitPlugin.cacheStart_param_child + param.getStr(column_numbers), paramList);
		
		String paramIds = param.getStr("parentids");
		if(null != paramIds){
			Param parent = Param.dao.findById(param.getStr("parentids"));
			if(null != parent){
				List<Param> parentList = parent.getChild();
				ToolCache.set(ParamInitPlugin.cacheStart_param_child + parent.getPKValue(), parentList);
				ToolCache.set(ParamInitPlugin.cacheStart_param_child + parent.getStr(column_numbers), parentList);
			}
		}
	}
	

	/**
	 * 删除缓存
	 * @param ids
	 */
	public void cacheRemove(String ids){
		Param param = Param.dao.findById(ids);
		ToolCache.remove(ParamInitPlugin.cacheStart_param + ids);
		ToolCache.remove(ParamInitPlugin.cacheStart_param + param.getStr(column_numbers));
		ToolCache.remove(ParamInitPlugin.cacheStart_param_child + ids);
		ToolCache.remove(ParamInitPlugin.cacheStart_param_child + param.getStr(column_numbers));

		String paramIds = param.getStr("parentids");
		if(null != paramIds){
			Param parent = Param.dao.findById(param.getStr("parentids"));
			if(null != parent){
				List<Param> parentList = parent.getChild();
				ToolCache.set(ParamInitPlugin.cacheStart_param_child + parent.getPKValue(), parentList);
				ToolCache.set(ParamInitPlugin.cacheStart_param_child + parent.getStr(column_numbers), parentList);
			}
		}
	}

	/**
	 * 获取缓存
	 * @param key
	 * @return
	 */
	public Param cacheGet(String key){
		Param param = ToolCache.get(ParamInitPlugin.cacheStart_param + key);
		return param;
	}
	
	/**
	 * 获取缓存
	 * @param key
	 * @return
	 */
	public List<Param> cacheGetChild(String key){
		List<Param> paramList = ToolCache.get(ParamInitPlugin.cacheStart_param_child + key);
		return paramList;
	}
	
}
