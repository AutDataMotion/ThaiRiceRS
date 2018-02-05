package com.platform.mvc.dict;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.platform.mvc.base.BaseModelCache;
import com.platform.plugin.ParamInitPlugin;
import com.platform.tools.ToolCache;

/**
 * 字典model
 * @author DHJ
 */
@SuppressWarnings("unused")
//@Table(tableName = "pt_dict")
public class Dict extends BaseModelCache<Dict> {
	
	private static final long serialVersionUID = 2051998642258015518L;

	private static Logger log = Logger.getLogger(Dict.class);
	
	public static final Dict dao = new Dict();

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
	 * sqlId : platform.dict.all
	 * 描述：查询所有字典
	 */
	public static final String sqlId_all = "platform.dict.all";
	
	public static final String sqlId_allid = "platform.dict.allid";

	/**
	 * sqlId : platform.dict.treeTableNodeRoot
	 * 描述：查询字典树根节点
	 */
	public static final String sqlId_treeTableNodeRoot = "platform.dict.treeTableNodeRoot";

	/**
	 * sqlId : platform.dict.treeTableChildNode
	 * 描述：查询字典树子节点
	 */
	public static final String sqlId_treeTableChildNode = "platform.dict.treeTableChildNode";

	/**
	 * sqlId : platform.dict.treeNodeRoot
	 * 描述：查询字典树根节点
	 */
	public static final String sqlId_treeNodeRoot = "platform.dict.treeNodeRoot";

	/**
	 * sqlId : platform.dict.treeChildNode
	 * 描述：查询字典树子节点
	 */
	public static final String sqlId_treeChildNode = "platform.dict.treeChildNode";

	/**
	 * sqlId : platform.dict.idAndI18n
	 * 描述：根据id查询国际化字典值
	 */
	public static final String sqlId_idAndI18n = "platform.dict.idAndI18n";

	/**
	 * sqlId : platform.dict.numbers
	 * 描述：根据编号查询字典值
	 */
	public static final String sqlId_numbers = "platform.dict.numbers";

	/**
	 * sqlId : platform.dict.numbersAndI18n
	 * 描述：根据编号查询国际化字典值
	 */
	public static final String sqlId_numbersAndI18n = "platform.dict.numbersAndI18n";

	/**
	 * sqlId : platform.dict.child
	 * 描述：查询子节点字典值
	 */
	public static final String sqlId_child = "platform.dict.child";

	/**
	 * sqlId : platform.dict.childAndI8n
	 * 描述：查询子节点国际化字典值
	 */
	public static final String sqlId_childAndI8n = "platform.dict.childAndI8n";

	/**
	 * sqlId : platform.dict.parent
	 * 描述：查询父节点字典值
	 */
	public static final String sqlId_parent = "platform.dict.parent";

	/**
	 * sqlId : platform.dict.parentAndI18n
	 * 描述：查询父节点国际化字典值
	 */
	public static final String sqlId_parentAndI18n = "platform.dict.parentAndI18n";

	/**
	 * sqlId : platform.dict.childCount
	 * 描述：查询子节点数量
	 */
	public static final String sqlId_childCount = "platform.dict.childCount";

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
	 * 根据主键查询字典，带国际化
	 * @param ids 主键
	 * @param i18n 国际化参数
	 * @return
	 */
	public Dict getByIds(String ids, String i18n){
		String val = "val" + i18n(i18n);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("val", val);
		
		String sql = getSqlByBeetl(sqlId_idAndI18n, param);
		
		Dict dict = dao.findFirst(sql, ids);
		
		return dict;
	}
	
	/**
	 * 根据编号查询字典
	 * @param number
	 * @return
	 */
	public Dict getByNumber(String number){
		String sql = getSql(sqlId_numbers);
		Dict dict = dao.findFirst(sql, number);
		return dict;
	}
	
	/**
	 * 根据编号查询字典，带国际化
	 * @param number 编号
	 * @param i18n 国际化参数
	 * @return
	 */
	public Dict getByNumber(String number, String i18n){
		String val = "val" + i18n(i18n);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("val", val);
		
		String sql = getSqlByBeetl(sqlId_numbersAndI18n, param);
		
		Dict dict = dao.findFirst(sql, number);
		
		return dict;
	}

	/**
	 * 查询子节点字典
	 * @return
	 */
	public List<Dict> getChild(){
		String sql = getSql(sqlId_child);
		return dao.find(sql, getPKValue());
	}

	/**
	 * 查询子节点字典
	 * @param prentIds
	 * @return
	 */
	public List<Dict> getChild(String prentIds){
		String sql = getSql(sqlId_child);
		return dao.find(sql, prentIds);
	}

	/**
	 * 查询子节点字典，国际化
	 * @param prentIds
	 * @param i18n
	 * @return
	 */
	public List<Dict> getChild(String prentIds, String i18n){
		String val = "val" + i18n(i18n);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("val", val);
		
		String sql = getSqlByBeetl(sqlId_childAndI8n, param);
		
		return dao.find(sql, prentIds);
	}

	/**
	 * 查询父节点字典
	 * @return
	 */
	public Dict getParent(){
		String sql = getSql(sqlId_parent);
		return dao.findFirst(sql, get(column_parentids));
	}

	/**
	 * 查询父节点字典，国际化
	 * @param i18n
	 * @return
	 */
	public Dict getParent(String i18n){
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
		Dict dict = Dict.dao.findById(ids);
		cacheAdd(dict);
	}

	public void cacheAdd(Dict dict){
		List<Dict> dictList = dict.getChild();
		ToolCache.set(ParamInitPlugin.cacheStart_dict + dict.getStr(column_ids), dict);
		ToolCache.set(ParamInitPlugin.cacheStart_dict + dict.getStr(column_numbers), dict);
		ToolCache.set(ParamInitPlugin.cacheStart_dict_child + dict.getStr(column_ids), dictList);
		ToolCache.set(ParamInitPlugin.cacheStart_dict_child + dict.getStr(column_numbers), dictList);

		String parentIds = dict.getStr("parentids");
		if(null != parentIds){
			Dict parent = Dict.dao.findById(parentIds);
			if(null != parent){
				List<Dict> parentList = parent.getChild();
				ToolCache.set(ParamInitPlugin.cacheStart_dict_child + parent.getPKValue(), parentList);
				ToolCache.set(ParamInitPlugin.cacheStart_dict_child + parent.getStr(column_numbers), parentList);
			}
		}
	}
	
	/**
	 * 删除缓存
	 * @param ids
	 */
	public void cacheRemove(String ids){
		Dict dict = Dict.dao.findById(ids);
		
		ToolCache.remove(ParamInitPlugin.cacheStart_dict + ids);
		ToolCache.remove(ParamInitPlugin.cacheStart_dict + dict.getStr(column_numbers));
		ToolCache.remove(ParamInitPlugin.cacheStart_dict_child + ids);
		ToolCache.remove(ParamInitPlugin.cacheStart_dict_child + dict.getStr(column_numbers));

		String parentIds = dict.getStr("parentids");
		if(null != parentIds){
			Dict parent = Dict.dao.findById(parentIds);
			if(null != parent){
				List<Dict> parentList = parent.getChild();
				ToolCache.set(ParamInitPlugin.cacheStart_dict_child + parent.getPKValue(), parentList);
				ToolCache.set(ParamInitPlugin.cacheStart_dict_child + parent.getStr(column_numbers), parentList);
			}
		}
	}

	/**
	 * 获取缓存
	 * @param key
	 * @return
	 */
	public Dict cacheGet(String key){
		Dict dict = ToolCache.get(ParamInitPlugin.cacheStart_dict + key);
		return dict;
	}
	
	/**
	 * 获取缓存
	 * @param key
	 * @return
	 */
	public List<Dict> cacheGetChild(String key){
		List<Dict> dictList = ToolCache.get(ParamInitPlugin.cacheStart_dict_child + key);
		return dictList;
	}
	
}
