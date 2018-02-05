package com.platform.mvc.dept;

import java.util.List;

import org.apache.log4j.Logger;

import com.jfinal.aop.Before;
import com.platform.dto.ZtreeNode;
import com.platform.mvc.base.BaseController;

/**
 * 部门管理
 * @author DHJ
 */
//@Controller(controllerKey = "/jf/platform/dept")
public class DepartmentController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(DepartmentController.class);
	
	private String pIds; // 上级部门ids
	private String names; // 部门名称
	private int orderIds; // 部门排序号
	private String principalIds; // 部门负责人
	
	/**
	 * tree首页
	 */
	public void index() {
		render("/platform/department/tree.html");
	}
	
	/**
	 * tree节点数据
	 */
	public void treeData()  {
		List<ZtreeNode> nodeList = DepartmentService.service.childNodeData(getCxt(), ids);
		renderJson(nodeList);
	}
	
	/**
	 * 保存
	 */
	@Before(DepartmentValidator.class)
	public void save() {
		ids = DepartmentService.service.save(pIds, names, orderIds);
		renderText(ids);
	}
	
	/**
	 * 更新
	 */
	@Before(DepartmentValidator.class)
	public void update() {
		DepartmentService.service.update(ids, pIds, names, principalIds);
		renderText(ids);
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		boolean bool = DepartmentService.service.delete(ids);
		renderText(String.valueOf(bool));
	}
	
	/**
	 * 获取部门负责人
	 */
	public void getPrincipal(){
		Department dept = Department.dao.findById(ids);
		renderJson(dept);
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


