package com.platform.mvc.operator;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jfinal.aop.Enhancer;
import com.platform.dto.ZtreeNode;
import com.platform.mvc.base.BaseService;
import com.platform.mvc.module.Module;

public class OperatorService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(OperatorService.class);

	public static final OperatorService service = Enhancer.enhance(OperatorService.class);
	
	/**
	 * 保存
	 * @param operator
	 * @return
	 */
	public String save(Operator operator){
		// 保存
		operator.save();
		String ids = operator.getPKValue();
		
		// 缓存
		Operator.dao.cacheAdd(ids);
		
		return ids;
	}

	/**
	 * <p>Title: saveGener<／p>
	 * <p>Description:一次性插入多条记录
	 * 省去手工录入的麻烦
	 * 只支持从list的url开始
	 * action 路径和view路径严格遵守规定格式
	 *  <／p>
	 * @param operator
	 * @return
	 */
	public boolean saveGener(Operator operator){
		
		//检查输入的url地址默认从list开始生成
		String urllist = operator.getUrl();
		String operName = operator.getNames();
		if(urllist==null){
			log.error("urllist==null");
			return false;
		}
		save(operator);
		
		//此后 分页为否
		operator.setSplitpage("0");
		
		//save
		operator.setUrl(urllist+"/save");
		operator.setNames(operName+"save");
		save(operator);
		//edit
		operator.setUrl(urllist+"/edit");
		operator.setNames(operName+"edit");
		save(operator);
		//update
		operator.setUrl(urllist+"/update");
		operator.setNames(operName+"update");
		save(operator);
		// delete
		operator.setUrl(urllist+"/delete");
		operator.setNames(operName+"delete");
		save(operator);
		//view
		operator.setUrl(urllist+"/view");
		operator.setNames(operName+"view");
		save(operator);
		//add.html 去掉前面的
		operator.setUrl(getViewPath(urllist)+"/add.html");
		operator.setNames(operName+"add.html");
		save(operator);
		return true;
	}
	private static String getViewPath(String actionPath){
		int index = 0;
		int len = actionPath.length();
		int cnt = 0;
		for (int i = 0; i < len; i++) {
			if ('/'==actionPath.charAt(i)) {
				cnt++;
				//找到第二个
				if (2==cnt) {
					index = i;
					break;
				}
			}
			
		}
		if (0==index) {//没有找到
			return actionPath;
		}
		return actionPath.substring(index);
	}
	/**
	 * 更新
	 * @param operator
	 */
	public void update(Operator operator){
		// 更新
		operator.update();
		String ids = operator.getPKValue();
		
		// 缓存
		Operator.dao.cacheAdd(ids);
	}

	/**
	 * 删除
	 * @param ids
	 */
	public void delete(String ids){
		String[] idsArr = splitByComma(ids);
		for (String operatorIds : idsArr) {
			// 缓存
			Operator.dao.cacheRemove(operatorIds);
			
			// 删除
			Operator.dao.deleteById(operatorIds);
		}
		
	}
	
	/**
	 * 获取子节点数据
	 * @param cxt
	 * @param moduleIds
	 * @return
	 */
	public List<ZtreeNode> treeData(String cxt, String moduleIds){
		List<Module> listModule = new ArrayList<Module>();
		List<Operator> operatorList = new ArrayList<Operator>();
		
		if (null == moduleIds) {
			// 1.模块功能初始化调用
			String sql = getSql(Operator.sqlId_rootModule);
			listModule = Module.dao.find(sql);
			
		} else if (null != moduleIds) {
			moduleIds = moduleIds.replace("module_", "");
			// 2.通用子节点查询
			String sqlModule = getSql(Operator.sqlId_childModule);
			listModule = Module.dao.find(sqlModule, moduleIds);
			
			String sqlOperator = getSql(Operator.sqlId_byModuleIds);
			operatorList = Operator.dao.find(sqlOperator, moduleIds);
		}

		List<ZtreeNode> nodeList = new ArrayList<ZtreeNode>();
		ZtreeNode node = null;

		for (Module module : listModule) {
			node = new ZtreeNode();
			node.setId("module_" + module.getPKValue());
			node.setName(module.getStr(Module.column_names));
			node.setIsParent(true);
			//node.setChecked(false);
			node.setNocheck(true);
			node.setIcon(cxt + "/jsFile/zTree/css/zTreeStyle/img/diy/" + module.getStr(Module.column_images));
			nodeList.add(node);
		}
		
		for (Operator operator : operatorList) {
			node = new ZtreeNode();
			node.setId("operator_" + operator.getPKValue());
			node.setName(operator.getStr(Operator.column_names));
			node.setIsParent(false);
			node.setChecked(false);
			node.setIcon(cxt + "/jsFile/zTree/css/zTreeStyle/img/diy/5.png");
			nodeList.add(node);
		}

		return nodeList;
	}

	/**
	 * 获取节点数据
	 * @param cxt
	 * @return
	 */
	public List<ZtreeNode> tree(String cxt){
		// 1.根模块
		String sql = getSql(Operator.sqlId_rootModule);
		List<Module> rootModuleList = Module.dao.find(sql);

		List<ZtreeNode> nodeList = new ArrayList<ZtreeNode>();
		
		for (Module module : rootModuleList) {
			ZtreeNode node = new ZtreeNode();
			
			node.setId(module.getPKValue());
			node.setName(module.getStr(Module.column_names));
			node.setIsParent(true);
			//node.setChecked(false);
			node.setNocheck(true);
			node.setIcon(cxt + "/jsFile/zTree/css/zTreeStyle/img/diy/" + module.getStr(Module.column_images));
			
			recursion(cxt, node, module);
			
			nodeList.add(node);
		}
		
		return nodeList;
	}
	
	/**
	 * 递归获取节点信息
	 * @param cxt
	 * @param treeNode
	 * @param pModule
	 */
	private void recursion(String cxt, ZtreeNode treeNode, Module pModule){
		List<ZtreeNode> children = new ArrayList<ZtreeNode>();

		// 功能
		String sqlOperator = getSql(Operator.sqlId_byModuleIds);
		List<Operator> operatorList = Operator.dao.find(sqlOperator, pModule.getPKValue());
		for (Operator operator : operatorList) {
			ZtreeNode node = new ZtreeNode();
			
			node.setId(operator.getPKValue());
			node.setName(operator.getStr(Operator.column_names));
			node.setIsParent(false);
			node.setChecked(false);
			node.setIcon(cxt + "/jsFile/zTree/css/zTreeStyle/img/diy/5.png");
			
			children.add(node);
		}
		
		// 模块
		String sqlModule = getSql(Operator.sqlId_childModule);
		List<Module> moduleList = Module.dao.find(sqlModule, pModule.getPKValue());
		for (Module module : moduleList) {
			ZtreeNode node = new ZtreeNode();
			
			node.setId(module.getPKValue());
			node.setName(module.getStr(Module.column_names));
			node.setIsParent(true);
			//node.setChecked(false);
			node.setNocheck(true);
			node.setIcon(cxt + "/jsFile/zTree/css/zTreeStyle/img/diy/" + module.getStr(Module.column_images));
			
			recursion(cxt, node, module);
			
			children.add(node);
		}
		
		treeNode.setChildren(children);
	}
	
}
