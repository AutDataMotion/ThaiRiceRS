package com.platform.mvc.operator;

import java.util.List;

import org.apache.log4j.Logger;

import com.jfinal.aop.Before;
import com.platform.constant.ConstantInit;
import com.platform.dto.ZtreeNode;
import com.platform.mvc.base.BaseController;

/**
 * 功能管理
 * @author DHJ
 */
//@Controller(controllerKey = "/jf/platform/operator")
public class OperatorController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(OperatorController.class);
	
	private String moduleIds; // 功能对应的模块
	
	/**
	 * 功能管理列表页
	 */
	public void index() {
		paging(ConstantInit.db_dataSource_main, splitPage, Operator.sqlId_splitPage_select, Operator.sqlId_splitPage_from);
		render("/platform/operator/list.html");
	}

	/**
	 * 保存功能
	 */
	@Before(OperatorValidator.class)
	public void save() {
		
		Operator oper = getModel(Operator.class);
		//判断是否一起生成 list save edit update delete view add.html
		String rowfilterFlag = oper.getRowfilter();
		if (null== rowfilterFlag) {
			//数据为空
			log.error("-----null== rowfilterFlag");
		}else if("1".equals(rowfilterFlag)){
			//这个rowfilter 只是暂时使用，现在恢复其默认值 0
			oper.setRowfilter("0");
			//批量生成
			OperatorService.service.saveGener(oper);
		}else{
			//只生成当前的
			ids = OperatorService.service.save(oper);
		}
		
		redirect("/jf/platform/operator");
	}

	/**
	 * 准备更新功能
	 */
	public void edit() {
		setAttr("operator", Operator.dao.findById(getPara()));
		render("/platform/operator/update.html");
	}

	/**
	 * 更新功能
	 */
	@Before(OperatorValidator.class)
	public void update() {
		OperatorService.service.update(getModel(Operator.class));
		redirect("/jf/platform/operator");
	}

	/**
	 * 查看功能
	 */
	public void view() {
		setAttr("operator", Operator.dao.findById(getPara()));
		render("/platform/operator/view.html");
	}

	/**
	 * 删除功能
	 */
	public void delete() {
		OperatorService.service.delete(getPara() == null ? ids : getPara());
		redirect("/jf/platform/operator");
	}

	/**
	 * 功能treeData
	 */
	public void treeData() {
		List<ZtreeNode> nodeList = OperatorService.service.treeData(getCxt(), moduleIds);
		renderJson(nodeList);
	}
	
	/**
	 * 功能treeData，一次性加载
	 */
	public void tree() {
		List<ZtreeNode> nodeList = OperatorService.service.tree(getCxt());
		renderJson(nodeList);
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


