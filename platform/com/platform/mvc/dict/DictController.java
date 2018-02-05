package com.platform.mvc.dict;

import java.util.List;

import org.apache.log4j.Logger;

import com.jfinal.aop.Before;
import com.platform.dto.ZtreeNode;
import com.platform.mvc.base.BaseController;

/**
 * 字典管理
 * @author DHJ
 */
//@Controller(controllerKey = "/jf/platform/dict")
public class DictController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(DictController.class);
	
	/**
	 * 首页
	 */
	public void index() {
		String sql = DictService.service.getSql(Dict.sqlId_treeTableNodeRoot);
		list = Dict.dao.find(sql);
		render("/platform/dict/treeTable.html");
	}
	
	/**
	 * treeTable子节点数据获取
	 */
	public void treeTable() {
		String sql = DictService.service.getSql(Dict.sqlId_treeTableChildNode);
		list = Dict.dao.find(sql, ids);
		render("/platform/dict/treeTableSub.html");
	}

	/**
	 * ztree子节点数据获取
	 */
	public void treeData()  {
		List<ZtreeNode> nodeList = DictService.service.childNodeData(getCxt(), ids);
		renderJson(nodeList);
	}
	
	/**
	 * 保存
	 */
	@Before(DictValidator.class)
	public void save() {
		DictService.service.save(getModel(Dict.class));
		redirect("/jf/platform/dict/toUrl?toUrl=/platform/dict/treeTableIframe.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		Dict dict = Dict.dao.findById(getPara());
		String pIds = dict.getStr(Dict.column_parentids);
		Dict parent = Dict.dao.findById(pIds);
		setAttr("dict", dict.put("parentnames", parent.getStr(Dict.column_names)));
		render("/platform/dict/update.html");
	}
	
	/**
	 * 更新
	 */
	@Before(DictValidator.class)
	public void update() {
		DictService.service.update(getModel(Dict.class));
		redirect("/jf/platform/dict/toUrl?toUrl=/platform/dict/treeTableIframe.html", true);
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		DictService.service.delete(getPara() == null ? ids : getPara());
		redirect("/jf/platform/dict/toUrl?toUrl=/platform/dict/treeTableIframe.html");
	}

	/**
	 * 查看
	 */
	public void view() {
		Dict dict = Dict.dao.findById(getPara());
		String pIds = dict.getStr(Dict.column_parentids);
		Dict parent = Dict.dao.findById(pIds);
		setAttr("dict", dict.put("parentnames", parent.getStr(Dict.column_names)));
		render("/platform/dict/view.html");
	}

	 /* (non-Javadoc)
	 * <p>Description: <／p>
	 * @see com.platform.mvc.base.BaseController#setViewPath()
	 */
	@Override
	protected void setViewPath() {
		// TODO Auto-generated method stub
		
	}

}


